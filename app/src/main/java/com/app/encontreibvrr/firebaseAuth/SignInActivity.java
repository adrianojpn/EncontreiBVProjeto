package com.app.encontreibvrr.firebaseAuth;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.encontreibvrr.MainActivity;
import com.app.encontreibvrr.R;
import com.app.encontreibvrr.activity.MyDatabaseUtil;
import com.app.encontreibvrr.firebaseAuth.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class SignInActivity extends BaseActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.CancelableCallback,
        GoogleMap.OnCameraChangeListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN_GOOGLE = 7859;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mSignInButton;
    private Button mSignUpButton;

    private User user;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SignInButton signInButton;


    private GoogleMap googleMAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Além disso, ele deve ser adicionado antes de fazer qualquer inicialização ref
        MyDatabaseUtil.getDatabase();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ((MapFragment) (getFragmentManager().findFragmentById(R.id.map))).getMapAsync(this);
        /*
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
         */

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mSignInButton = (Button) findViewById(R.id.button_sign_in);
        mSignUpButton = (Button) findViewById(R.id.button_sign_up);

        // Click listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);


        // GOOGLE SIGN IN //////////////////////////////////////////////////////////////////////////////////////
        /*
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("955249609151-e9b7p5smf2scp7sfi6qgn0etvec3jf24.apps.googleusercontent.com")
                .requestEmail()
                .build();
        */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }



    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());

            //Google //////////////////////////////////////////////////////////////////////////////////////////
            mAuth.addAuthStateListener(mAuthListener);
        }
    }

               //Google ////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivityStorageFire
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_in:
                signIn();
                break;
            case R.id.button_sign_up:
                signUp();
                break;
            case R.id.sign_in_button:
                sendLoginGoogleData();
                break;
        }
    }

    public void sendLoginGoogleData( ){
        FirebaseCrash.log("LoginActivity:clickListener:button:sendLoginGoogleData()");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        FirebaseCrash.report( new Exception( connectionResult.getErrorCode()+": "+connectionResult.getErrorMessage() ) );
        showSnackbar( connectionResult.getErrorMessage() );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == RC_SIGN_IN_GOOGLE ){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }

            /*
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();

            //Log.i("SignIn", "Id: "+ account.getId());
            //Log.i("SignIn", "Email: "+ account.getEmail());
            //Log.i("SignIn", "Token: "+ account.getIdToken());

            if( account == null ){
                //showSnackbar("Login com Google falhou. Tente novamente...");
                Toast.makeText(SignInActivity.this, "Login relizado com sucesso...",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            //accessGoogleLoginData(account.getIdToken() );
            mAuth.signInWithEmailAndPassword(account.getEmail(), account.getIdToken())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                            hideProgressDialog();

                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                                Toast.makeText(SignInActivity.this, "Login relizado com sucesso...",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignInActivity.this, "O Login falhou",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            */
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Autenticação falhou...",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d(TAG, "Login realizado com sucesso...");
                            Toast.makeText(SignInActivity.this, "Autenticação realizada com sucesso...",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id  == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String text = String.format("(%.4f, %.4f)", latLng.latitude, latLng.longitude);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> enderecos = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    if (enderecos!=null && enderecos.size()>0) {
                        String s = "";
                        Address endereco = enderecos.get(0);
                        for (int i = 0; i<endereco.getMaxAddressLineIndex(); i++) {
                            String line = endereco.getAddressLine(i);
                            s += line + "\n";
                        }

                        final String finalS = s;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), finalS, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMAP = googleMap;

        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng UFRR = new LatLng(2.820750, -60.672508);
        //googleMap.addMarker(new MarkerOptions()
        //        .position(UFRR)
        //        .title("Marker in Sydney"));

        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(UFRR), 13);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UFRR, 13));

        //OnClick
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);

        googleMap.setOnCameraChangeListener(this);

        /*
        CameraPosition.Builder builder = new CameraPosition.Builder();
        //bearing é o angulo de visualização e o tilt é o ângulo de cima em relação a superfície da terra
        builder.target(UFRR).bearing(360).tilt(45);
        //builder.target(latLng).zoom(8).bearing(180).tilt(45);
        CameraPosition cameraPosition = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        //map.moveCamera(cameraUpdate);
        //map.animateCamera(cameraUpdate);
        googleMap.animateCamera(cameraUpdate, 6000, this);
        */

        CameraPosition.Builder builder = new CameraPosition.Builder();
            builder.target(UFRR).bearing(360).zoom(14);
        CameraPosition cameraPosition = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate, 3000, this);

    }

    @Override
    public void onFinish() {

        LatLng UFRR = new LatLng(2.820750, -60.672508);
        googleMAP.moveCamera(CameraUpdateFactory.newLatLngZoom(UFRR, 13));
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }
}

package com.app.encontreibvrr.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.encontreibvrr.R;
import com.app.encontreibvrr.activity.FirebaseHelper;
import com.app.encontreibvrr.activity.PostDocumentosAchados;
import com.app.encontreibvrr.activity.PostViewHolderDocumentoAchados;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

/**
 * Created by adria on 22/07/2016.
 */

public class Fragment_05Perfil extends Fragment {

    public Fragment_05Perfil() {}
    private String mTitle;

    public static Fragment_05Perfil getInstance(String title) {
        Fragment_05Perfil sf = new Fragment_05Perfil();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static final String TAG = "Fragment_05Perfil";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextView mTextViewProfile, mTextViewProvider;
    private  ProgressBar progressBar;
    private ImageView ivBasicImage;
    private String urlImagemPerfil = "";
    private FrameLayout frameLayout;

    private String UIDPerfil = "";

    //Meus Posts
    private DatabaseReference databaseReference;
    private FirebaseHelper helper;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<PostDocumentosAchados, PostViewHolderDocumentoAchados> mAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View rootView = inflater.inflate(R.layout.fragment_04, container, false);

        mTextViewProfile = (TextView) rootView.findViewById(R.id.profile);
        mTextViewProvider = (TextView) rootView.findViewById(R.id.provider);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        ivBasicImage = (ImageView) rootView.findViewById(R.id.URL_ImagemPicasso);
        frameLayout = (FrameLayout) rootView.findViewById(R.id.frame_imagem_pregress);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user, rootView);
            }
        };

        //INITIALIZE FIREBASE DB
        databaseReference = FirebaseDatabase.getInstance().getReference();

        /////////////Meu posts
        //SETUP RECYCLER
        mRecycler = (RecyclerView) rootView.findViewById(R.id.rv);
        mRecycler.setHasFixedSize(true);


        return rootView;
    }



    private void updateUI(FirebaseUser user, View rootView) {
        if (user != null) {
            // User's profile
            mTextViewProfile.setText("Firebase ID: " + user.getUid());
            mTextViewProfile.append("\n");
            mTextViewProfile.append("DisplayName: " + user.getDisplayName());
            mTextViewProfile.append("\n");
            mTextViewProfile.append("Email: " + user.getEmail());
            mTextViewProfile.append("\n");
            mTextViewProfile.append("Photo URL: " + user.getPhotoUrl());

            // User's provider
            mTextViewProvider.setText(null);
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                mTextViewProvider.append("providerId: " + profile.getProviderId());
                mTextViewProvider.append("\n");
                // UID specific to the provider
                mTextViewProvider.append("UID: " + profile.getUid());
                mTextViewProvider.append("\n");
                mTextViewProvider.append("name: " + profile.getDisplayName());
                mTextViewProvider.append("\n");
                mTextViewProvider.append("email: " + profile.getEmail());
                mTextViewProvider.append("\n");
                mTextViewProvider.append("photoUrl: " + profile.getPhotoUrl());

                urlImagemPerfil = "" + profile.getPhotoUrl();
                Log.d(TAG, "getPhotoUrl():" + profile.getPhotoUrl());
                CarregarFotoPerfil();

                if (!"password".equals(profile.getProviderId())) {
                    //mTextViewProvider.append("\n\n");
                }
            }
            //rootView.findViewById(R.id.provider_fields).setVisibility(View.VISIBLE);
            //rootView.findViewById(R.id.update_profile_fields).setVisibility(View.VISIBLE);
            //rootView.findViewById(R.id.update_email_fields).setVisibility(View.VISIBLE);
            //rootView.findViewById(R.id.update_password_fields).setVisibility(View.VISIBLE);
            //rootView.findViewById(R.id.send_password_reset_fields).setVisibility(View.VISIBLE);
            //rootView.findViewById(R.id.delete_fields).setVisibility(View.VISIBLE);

        } else {
            mTextViewProfile.setText(R.string.signed_out);
            mTextViewProvider.setText(null);
            //rootView.findViewById(R.id.provider_fields).setVisibility(View.GONE);
            //rootView.findViewById(R.id.update_profile_fields).setVisibility(View.GONE);
            //rootView.findViewById(R.id.update_email_fields).setVisibility(View.GONE);
            //rootView.findViewById(R.id.update_password_fields).setVisibility(View.GONE);
            //rootView.findViewById(R.id.send_password_reset_fields).setVisibility(View.GONE);
            //rootView.findViewById(R.id.delete_fields).setVisibility(View.GONE);
        }
        hideProgressDialog();
    }


    //Leitura do Uid do usuario
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void CarregarFotoPerfil(){
        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        if (urlImagemPerfil.equals("")){
            frameLayout.setVisibility(View.GONE);
        } else {
            Picasso.with(getActivity().getApplicationContext()).load(urlImagemPerfil)
                    .into(ivBasicImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (progressBar != null) {
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void onError() {

                        }
                    });
        }
    }


    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loadingPerfil));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getContext().getApplicationContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);


        String myUserId = getUid();

        // Set up FirebaseRecyclerAdapter with the Query
        //Query postsQuery = getQuery(mDatabase);
        //Query postsQuery = databaseReference.child("Spacecraft").child(myUserId).orderByChild("starCount");
        //Query postsQuery = databaseReference.child("DocumentosAchados").child("usuariouid").equalTo(myUserId);
        Query postsQuery = databaseReference.child("DocumentosAchados").orderByChild("usuariouid").equalTo(myUserId);

        mAdapter = new FirebaseRecyclerAdapter<
                PostDocumentosAchados,
                PostViewHolderDocumentoAchados>(

                PostDocumentosAchados.class, R.layout.item_recycler,
                PostViewHolderDocumentoAchados.class, postsQuery) {

            @Override
            protected void populateViewHolder(final PostViewHolderDocumentoAchados viewHolder,
                                              final PostDocumentosAchados model, final int position) {

                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        //Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        //intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        //startActivity(intent);
                    }
                });

                //Efeito animate
                try {
                    YoYo.with(Techniques.BounceIn)
                            .duration(500)
                            .playOn(viewHolder.itemView);
                } catch (Exception e) {

                }

                // Show progress bar
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                // Hide progress bar on successful load
                // .networkPolicy(NetworkPolicy.OFFLINE)
                Log.d("Picasso", model.imagemurl);
                if (model.imagemurl.equals("NENHUMA")){
                    viewHolder.frameLayout.setVisibility(View.GONE);
                } else {
                    Picasso.with(getActivity().getApplicationContext()).load(model.imagemurl)
                            .into(viewHolder.ivBasicImage, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    if (viewHolder.progressBar != null) {
                                        viewHolder.progressBar.setVisibility(View.GONE);
                                    }
                                }
                                @Override
                                public void onError() {

                                }
                            });
                }

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                // Bind Publicar ViewHolder , estabelecendo OnClickListener para o botão de estrela
                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        // Necessidade de escrever para ambos os lugares o post é armazenado
                        //DatabaseReference globalPostRef = databaseReference.child("Spacecraft").child(postRef.getKey());
                        //DatabaseReference userPostRef = databaseReference.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        // Executar duas operações
                        //onStarClicked(globalPostRef);
                        //onStarClicked(userPostRef);
                    }
                });
            }
        };

        mRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}

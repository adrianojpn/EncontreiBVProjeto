package com.app.encontreibvrr.activitysUploadInfor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.encontreibvrr.R;
import com.app.encontreibvrr.activity.FirebaseHelper;
import com.app.encontreibvrr.activity.GetSetDocumAchado;
import com.app.encontreibvrr.activity.MyDatabaseUtil;
import com.app.encontreibvrr.firebaseStore.UploadActivity;
import com.app.encontreibvrr.firebaseStore.util.Helper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityPerdidos extends AppCompatActivity implements View.OnClickListener{

	private static final int RC_UPLOAD_FILE = 102;
	private StorageReference folderRef;
	private TextView mTextView;
	private UploadTask mUploadTask;

	//////////////////////////////////////////
	private EditText Nome;
	private EditText DescricaoDoItem;
	private EditText LocalAchado;
	private EditText Email;
	private EditText Telefone;
	private EditText Observacoes;
	private TextView tipoConexaoTextView;
	private DatabaseReference databaseReference;
	private Boolean saved = null;
	private String urlImagem = null;

	private String uidusuarioPasta;

	private Handler handler = new Handler();
	private Toolbar toolbar;

	private LinearLayout tipoColorLayout;
	private LinearLayout layoutUploadImagem;

	private Button buttonUploadFile;

	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	private static final String TAG = "ActivityAchados";

	private FirebaseHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_achados);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("PERDIDOS");
		toolbar.setSubtitle("Adicionar documentos/objetos");
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		//A FirebaseApp é inicializado por um ContentProvider por isso não é inicializado no
		// momento onCreate () é chamado. Em seguida, chamar Utils.getDatabase () de qualquer atividade
		// de usar o banco de dados. Obtenha seu FirebaseDatabase como este: método de classe
		MyDatabaseUtil.getDatabase();

		bindWidget();

		mAuth = FirebaseAuth.getInstance();
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

		//Verifica o Status da conexao com a Internet
		AtualizaActivity();

		//INITIALIZE FIREBASE DB
		databaseReference = FirebaseDatabase.getInstance().getReference();
		helper = new FirebaseHelper(databaseReference);
		uidusuarioPasta = getUid();

		FirebaseStorage storage = FirebaseStorage.getInstance();
		StorageReference storageRef = storage.getReference();
		//folderRef = storageRef.child("photos");
		//Nome da pasta vai ser o numero do usuario - unico
		folderRef = storageRef.child(uidusuarioPasta);

		//O banco de dados Firebase Realtime sincroniza e armazena uma cópia local dos dados para
		// ouvintes ativos . Além disso, você pode manter ActivityLocais específicos em sincronia .
		databaseReference.keepSynced(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		if (id == R.id.home){
			startActivity(new Intent(this, UploadActivity.class));
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		mAuth.addAuthStateListener(mAuthListener);
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mAuthListener != null) {
			mAuth.removeAuthStateListener(mAuthListener);
		}
	}

	@Override
	public void onClick(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		switch (view.getId()) {
			case R.id.salvarDadosFire:
				getDadosInformados();
				break;
			case R.id.button_upload_from_file:
				startActivityForResult(intent, RC_UPLOAD_FILE);
				break;
			case R.id.button_upload_resume:
				Helper.mProgressDialog.show();
				mUploadTask.resume();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			String path = Helper.getPath(this, Uri.parse(data.getData().toString()));
			switch (requestCode) {
				case RC_UPLOAD_FILE:
					uploadFromFile(path);
					break;
			}
		}
	}

	public void getDadosInformados(){
		//GET DATA
		String getNome = Nome.getText().toString();
		String getDescricaoDoItem = DescricaoDoItem.getText().toString();
		String getLocalAchado = LocalAchado.getText().toString();
		String getEmail = Email.getText().toString();
		String getTelefone = Telefone.getText().toString();
		String getInformacoesObser = Observacoes.getText().toString();

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
		String dataHora = df.format(calendar.getTime());

		final String uid = getUid();
		String authorNameuid = uid;

		//SET DATA
		GetSetDocumAchado getSet = new GetSetDocumAchado();
		getSet.setNome(getNome);
		getSet.setDescricadodumento(getDescricaoDoItem);
		getSet.setLocalachado(getLocalAchado);

		if (getEmail.equals("")){
			getEmail = "Não informado";
		}
		getSet.setEmail(getEmail);

		if (getTelefone.equals("")){
			getTelefone = "Não informado";
		}
		getSet.setTelefone(getTelefone);
		getSet.setUsuariouid(authorNameuid);
		getSet.setDatahora(dataHora);

		if (urlImagem == null){
			urlImagem = "NENHUMA";
		}
		getSet.setImagemurl(urlImagem);

		if (getInformacoesObser.equals("")){
			getInformacoesObser = "Não informado";
		}
		getSet.setInformacoes(getInformacoesObser);


		//SIMPLE VALIDATION
		if (getNome.length() != 0 && getDescricaoDoItem.length() != 0) {
			//Salavo os dados
			if (saveFirebaseDados(getSet)) {
				//Finaliza a activity
				Toast.makeText(getApplicationContext(), "Dados enviados com Sucesso!",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Nome do item e/ou descrição do documento NÃO devem ficar vazios",
					Toast.LENGTH_SHORT).show();
		}
	}

	//Leitura do Uid do usuario
	public String getUid() {
		return FirebaseAuth.getInstance().getCurrentUser().getUid();
	}

	//WRITE IF NOT NULL
	public Boolean saveFirebaseDados(GetSetDocumAchado getSet) {
		if(getSet == null) {
			saved = false;

		} else {
			try {
				databaseReference.child("DocumentosPerdidos").push().setValue(getSet);
				saved = true;

			}
			catch (DatabaseException e) {
				e.printStackTrace();
				//FirebaseCrash.report(e);
				saved=false;
			}
		}

		return saved;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Helper.dismissProgressDialog();
		Helper.dismissDialog();
	}

	private void bindWidget() {
		findViewById(R.id.salvarDadosFire).setOnClickListener(this);
		findViewById(R.id.button_upload_from_file).setOnClickListener(this);
		Nome = (EditText) findViewById(R.id.Nome);
		DescricaoDoItem = (EditText) findViewById(R.id.DescricaoDoItem);
		LocalAchado = (EditText) findViewById(R.id.LocalAchado);
		Email = (EditText) findViewById(R.id.Email);
		Telefone = (EditText) findViewById(R.id.Telefone);
		mTextView = (TextView) findViewById(R.id.textview);
		Observacoes = (EditText) findViewById(R.id.Observacoes);

		layoutUploadImagem = (LinearLayout) findViewById(R.id.layoutUploadImagem);

		tipoConexaoTextView = (TextView) findViewById(R.id.tipoConexaoTextView);
		tipoColorLayout = (LinearLayout) findViewById(R.id.tipoColorLayout);

		buttonUploadFile = (Button) findViewById(R.id.button_upload_from_file);
	}


	private void uploadFromFile(String path) {
		Uri file = Uri.fromFile(new File(path));
		StorageReference imageRef = folderRef.child(file.getLastPathSegment());
		mUploadTask = imageRef.putFile(file);

		Helper.initProgressDialog(this);
		Helper.mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCELAR", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				mUploadTask.cancel();
			}
		});
		Helper.mProgressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "PAUSAR", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				mUploadTask.pause();
			}
		});
		Helper.mProgressDialog.show();

		//StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpg").build();
		//UploadTask uploadTask = imageRef.putFile(file, metadata);

		mUploadTask.addOnFailureListener(new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception exception) {
				Helper.dismissProgressDialog();
				mTextView.setText(String.format("Failure: %s", exception.getMessage()));
				//urlImagem = String.format("Failure: %s", exception.getMessage());
				urlImagem = String.format("NENHUMA");
			}
		}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
				Helper.dismissProgressDialog();
				//findViewById(R.id.button_upload_resume).setVisibility(View.GONE);
				mTextView.setText(taskSnapshot.getDownloadUrl().toString());
				mTextView.setVisibility(View.VISIBLE);
				urlImagem = taskSnapshot.getDownloadUrl().toString();
					Log.d("ActivityUpload", taskSnapshot.getDownloadUrl().toString());
					Log.d("ActivityUpload", "String urlImagem: " + urlImagem);
				layoutUploadImagem.setVisibility(View.VISIBLE);
				buttonUploadFile.setClickable(false);
				buttonUploadFile.setText("IMAGEM ENVIADA COM SUCESSO");
			}
		}).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
				int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
				Helper.setProgress(progress);
			}
		}).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
				//findViewById(R.id.button_upload_resume).setVisibility(View.VISIBLE);
				//mTextView.setText(R.string.upload_paused);
			}
		});
	}



	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected())
			return true;
		else
			return false;
	}

	public static boolean isNetworkConnected(ConnectivityManager connectivityManager, int type){
		final NetworkInfo network = connectivityManager.getNetworkInfo(type);
		if (network != null && network.isAvailable() && network.isConnected()){

			return true;
		} else {
			return false;
		}
	}


	private void AtualizaActivity() {

		new Thread() {

			@Override
			public void run() {
				///Tarefa a ser executada em segundo plano
				AtualizaInformacoes();
				//* Retorna true se o Runnable foi colocado com sucesso em ao
				//* Fila de mensagens. Retorna false em caso de falha , normalmente porque o
				//* Looper processar a fila de mensagens está saindo . Note-se que um
				//* Resultado de verdade não significa que o Runnable serão processados ​​-
				//* Se o looper é sair antes do tempo de entrega da mensagem
				//* Ocorre , em seguida, a mensagem será descartada.
				handler.postDelayed(getRunnableDownload(), 1000); //1 Segundo
			}
		}.start();
	}

	//Somente a UI Thread pode atualizar a view
	private void AtualizaInformacoes() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//Atualiza activity: seta as novas informações novamente
				//Atualiza as novas informações
				//textvil.setText(formatBytes(getRuntimeMaxMemory()));

				Boolean conecatado = isOnline(getApplicationContext());

				if (conecatado == true){
					tipoConexaoTextView.setText("CONECTADO");
					tipoColorLayout.setBackgroundColor(getResources().getColor(R.color.conectadoVerde));
				} else {
					tipoConexaoTextView.setText("AGUARDANDO CONEXÃO");
					tipoColorLayout.setBackgroundColor(getResources().getColor(R.color.conexaoAguardando));
				}
			}
		});
	}

	private Runnable getRunnableDownload() {
		return new Runnable() {
			@Override
			public void run() {
				AtualizaActivity();
			}
		};
	}
}
# EncontreiBVProjeto
DCC917A -TÓPICOS ESPECIAIS III: DESENVOLVIMENTO DE APLICATIVOS MÓVEIS (2016 .1 - T01)
UFRR - Universidade Federal de Roraima
DCC -  Departamento de Ciência da Computação/ Prof. Orientador: FILIPE DWAN PEREIRA
Aplicativo Android desenvolvido na disciplina de DCC/UFRR destinado ao armazenamento de informações, por meio de usuário e empresas de documentos ou objetos perdidos na Cidade de Boa Vista/RR. (AdrianoJPN Adriano J. P. Nascimento)

Objetivo

Desenvolvimento de uma aplicação na Plataforma Android, utilizando informações aprendidas na Disciplina de TÓPICOS ESPECIAIS III: DESENVOLVIMENTO DE APLICATIVOS MÓVEIS. Além de outros recursos adicionais.

Google Maps
Console Firebase
Autenticação usuários


Objetivo do Aplicativo
Um aplicativo onde os usuário possam cadastrar gratuitamente informações a respeito de objetos e/ou documentos que foram perdidos ou achados na cidade de Boa Vista/RR.

Documentos (Identidade, CPF, etc.)
Objetos (carteira, celular, etc.)


Delimitação do Problema
O projeto foi escolhido pela necessidade da utilização de um aplicativo do gênero, possam registrar de forma CENTRALIZADA, informações a respeito de achados e perdidos na Cidade de Boa Vista/Roraima

CENTRALIZAÇÃO DE INFORMAÇÕES
BANCO DE DADOS OLINE
ACESSO À DADOS REGISTRADOS
LOCALIZAÇÃO
ORIENTAÇÃO


Usuários do Aplicativo
O protótipo é destinado:
Empresas de pequeno e Grande porte que atendem muitos clientes (Pelo número de documentos/objetos perdidos/esquecidos); 
Usuários que acharam e/ou perderam algo.

REQUISITOS DA APLICAÇÃO
Requisitos Funcionais
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Sem%20t%C3%ADtulo-11313163.png" 
alt="Smiley face" height="300">

Requisitos não Funcionais

O aplicativo requer processamento e armazenamento podendo rodar em qualquer dispositivo com uma versão mínima 4.1.2 (16) do sistema Android, necessitando das seguintes permissões 

INTERNET
ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION,
ACCESS_NETWORK_STATE
MAPS_RECEIVE
READ_GSERVICES
WRITE_EXTERNAL_STORAGE
CAMERA

Interface e Layouts

A aplicação possui layouts simplificados e intuitivos, com itens de manipulação na tela principal, opções de edição texto e inserção de Imagem.

<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Logo%20App%2001.png" 
alt="Smiley face" height="150">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Logo%20Gifnigif%2001.gif" 
alt="Smiley face" height="150">

Interface e Layouts – Tela de Login

Autenticação do usuário é realizada por dois métodos:
- Conta Email
- Conta do Google

<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-36-07.png" 
alt="Smiley face" height="400">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-36-56.png" 
alt="Smiley face" height="400">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-37-04.png" 
alt="Smiley face" height="400">

Interface e Layouts – Tela de Início

<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-37-47.png" 
alt="Smiley face" height="500">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-37-50.png" 
alt="Smiley face" height="500">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-38-05.png" 
alt="Smiley face" height="500">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-38-14.png" 
alt="Smiley face" height="500">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-25-08-13-31.png" 
alt="Smiley face" height="500">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-38-20.png" 
alt="Smiley face" height="500">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-25-08-18-24.png" 
alt="Smiley face" height="500">


Interface e Layouts – INSERIR INFORMAÇÕES

<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-38-35.png" 
alt="Smiley face" height="500">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-24-20-38-39.png" 
alt="Smiley face" height="500">


Interface e Layouts – INSERIR INFORMAÇÕES

```javascript
private static final int RC_UPLOAD_FILE = 102;
private StorageReference folderRef;
private TextView mTextView;
private UploadTask mUploadTask;
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

@Override
protected void onCreate(Bundle savedInstanceState) [...]
//Inicializa o Database DB
databaseReference = FirebaseDatabase.getInstance().getReference();
helper = new FirebaseHelper(databaseReference);
uidusuarioPasta = getUid();
```


```javascript
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mAuth = FirebaseAuth.getInstance();
		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();

				//Adrianojpn
				databaseReference = FirebaseDatabase.getInstance().getReference();

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
		
		//INITIALIZE FIREBASE DB
		databaseReference = FirebaseDatabase.getInstance().getReference();
		helper = new FirebaseHelper(databaseReference);
		uidusuarioPasta = getUid();

		FirebaseStorage storage = FirebaseStorage.getInstance();
		StorageReference storageRef = storage.getReference();
		//folderRef = storageRef.child("photos");
		//Nome da pasta vai ser o numero do usuario - unico
		folderRef = storageRef.child(uidusuarioPasta);
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
```


```javascript
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

```


```javascript
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
```

Inserir informações no Firebase

```javascript
//WRITE IF NOT NULL
	public Boolean saveFirebaseDados(GetSetDocumAchado getSet) {
		if(getSet == null) {
			saved = false;

		} else {
			try {
				databaseReference.child("DocumentosAchados").push().setValue(getSet);
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
```

Interface e Layouts – INSERIR INFORMAÇÕES
Classe construtor das informações inseridas

```javascript
  public class GetSetDocumAchado {

    String nome;
    String descricadodumento;
    String localachado;
    String email;
    String telefone;
    String usuariouid;
    String datahora;
    String imagemurl;
    String informacoes;

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getImagemurl() {
        return imagemurl;
    }

    public void setImagemurl(String imagemurl) {
        this.imagemurl = imagemurl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricadodumento() {
        return descricadodumento;
    }

    public void setDescricadodumento(String descricadodumento) {
        this.descricadodumento = descricadodumento;
    }

    public String getLocalachado() {
        return localachado;
    }

    public void setLocalachado(String localachado) {
        this.localachado = localachado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

    public String getUsuariouid() {
        return usuariouid;
    }

    public void setUsuariouid(String usuariouid) {
        this.usuariouid = usuariouid;
    }
}
```

Interface e Layouts – Tela de Início

CÓDIGO RECYCLER  VIEW DOS ITENS ACHADOS

```javascript
 private DatabaseReference databaseReference;
    private FirebaseHelper helper;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<PostDocumentosAchados, PostViewHolderDocumentoAchados> mAdapter;
    private static final String TAG = "FragmentAchados";
    
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_achados, container, false);

        //A FirebaseApp é inicializado por um ContentProvider por isso não é inicializado no
        // momento onCreate () é chamado. Em seguida, chamar Utils.getDatabase () de qualquer atividade
        // de usar o banco de dados. Obtenha seu FirebaseDatabase como este: método de classe
        MyDatabaseUtil.getDatabase();
        
        //INITIALIZE FIREBASE DB
        databaseReference = FirebaseDatabase.getInstance().getReference();
        
         //SETUP RECYCLER
        mRecycler = (RecyclerView) view.findViewById(R.id.rv);
        mRecycler.setHasFixedSize(true);
        
        
        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = databaseReference.child("DocumentosAchados");
        
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
                
                
                }
        };

        mRecycler.setAdapter(mAdapter);
        
        
        @Override
        public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
```


CÓDIGO RECYCLER  VIEW DOS ITENS ACHADOS - Post

```javascript
@IgnoreExtraProperties
public class PostDocumentosAchados {

    public String nome;
    public String descricadodumento;
    public String localachado;
    public String email;
    public String telefone;
    public String usuariouid;
    public String datahora;
    public String imagemurl;
    public String informacoes;

    public Map<String, Boolean> stars = new HashMap<>();

    public PostDocumentosAchados() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("descricadodumento", descricadodumento);
        result.put("localachado", localachado);
        result.put("email", email);
        result.put("telefone", telefone);
        result.put("usuariouid", usuariouid);
        result.put("datahora", datahora);
        result.put("imagemurl", imagemurl);
        result.put("informacoes", informacoes);

        return result;
    }

}
```

CÓDIGO RECYCLER  VIEW DOS ITENS ACHADOS - PostViewHolder

```javascript
public class PostViewHolderDocumentoAchados extends RecyclerView.ViewHolder {

    public TextView nome;
    public TextView descricadodumento;
    public TextView localachado;
    public TextView telefone;
    public TextView email;
    public TextView usuariouid;
    public TextView datahora;
    public ImageView ivBasicImage;
    public ProgressBar progressBar;
    public FrameLayout frameLayout;

    public PostViewHolderDocumentoAchados(View itemView) {
        super(itemView);

        nome = (TextView) itemView.findViewById(R.id.nome);
        descricadodumento = (TextView) itemView.findViewById(R.id.descricadodumento);
        localachado = (TextView) itemView.findViewById(R.id.localachado);
        telefone = (TextView) itemView.findViewById(R.id.telefone);
        email = (TextView) itemView.findViewById(R.id.email);
        usuariouid = (TextView) itemView.findViewById(R.id.usuariouid);
        datahora = (TextView) itemView.findViewById(R.id.datahora);
        ivBasicImage = (ImageView) itemView.findViewById(R.id.URL_ImagemPicasso);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_imagem_pregress);
    }

    //ligar para postar
    public void bindToPost(PostDocumentosAchados post, View.OnClickListener starClickListener) {

        nome.setText(post.nome);
        descricadodumento.setText(post.descricadodumento);
        localachado.setText("Local encontrado: " + post.localachado);
        telefone.setText("Telefone: " + post.telefone);
        email.setText("Email: " + post.email);
        usuariouid.setText("Usuário uid: " + post.usuariouid);
        datahora.setText(post.datahora);
    }
```

<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-25-08-18-24.png" 
alt="Smiley face" height="300">
<img src="https://github.com/topicosdcc/EncontreiBVProjeto/blob/master/Screenshot/Screenshot_2016-08-25-08-18-22.png" 
alt="Smiley face" height="300">



```javascript
Referências Bibliográficas

Docs Firebase Google. Disponível em <https://firebase.google.com/docs/> .Acesso em 21 de Agosto de 2016.
Adicionar o Firebase ao projeto Android. Disponível em <https://firebase.google.com/docs/android/setup>. Acessado em 21 de Agosto de 2016.
Set up Firebase Realtime Database for Android. Disponível em <https://firebase.google.com/docs/database/android/start/> . Acessado em 21 de Agosto de 2016.
Adicione o Firebase Storage ao aplicativo. Disponível em <https://firebase.google.com/docs/storage/android/start>. Acessado em 21 de Agosto de 2016.
Enviar uma notificação para um segmento de usuários. Disponível em <https://firebase.google.com/docs/notifications/android/console-audience>.Acessado em 21 de Agosto de 2016.
Bibliotecas Firebase. Disponível em <https://firebase.google.com/docs/libraries/> .Acessado em 21 de Agosto de 2016.

Android: Offine image caching with Picasso. Disponível em <https://newfivefour.com/android-image-caching-picasso.html> .Acesso em 21 de Agosto de 2016.
Picasso — Influencing Image Caching. Disponível em <https://futurestud.io/blog/picasso-influencing-image-caching>. Acessado em 21 de Agosto de 2016.
Load images from disk cache with Picasso if offline. Disponível em <http://www.unknownerror.org/opensource/square/picasso/q/stackoverflow/23391523/load-images-from-disk-cache-with-picasso-if-offline> . Acessado em 21 de Agosto de 2016.
Android image loading – Picasso!. Disponível em <http://zeroturnaround.com/rebellabs/picking-my-next-android-image-loading-library-picasso/>. Acessado em 21 de Agosto de 2016.


Persistência Realm no Android . Disponível em https://www.youtube.com/playlist?list=PLBA57K2L2RIJz9eAlODBynIfWQgckZaXc. Acessado 25 de Agosto de 2016

Persistência Com Firebase Android. Disponível em https://www.youtube.com/playlist?list=PLBA57K2L2RIJICFUSbL0SPhiYTFDYDQo7. Acessado 25 de Agosto de 2016


Android Updated Firebase . Disponível em https://www.youtube.com/playlist?list=PLfBjz1j1UV9m3MofrgMivwaGjRjfhMQfL . Acessado 25 de Agosto de 2016


Retrieving Data. Disponível em <https://www.firebase.com/docs/android/guide/retrieving-data.html> .Acesso em 21 de Agosto de 2016.
How to search for a value in firebase Android. Disponível em <http://stackoverflow.com/questions/34537369/how-to-search-for-a-value-in-firebase-android>. Acessado em 21 de Agosto de 2016.
How to retrieve specific node from firebase database in android. Disponível em <http://stackoverflow.com/questions/28601663/how-to-retrieve-specific-node-from-firebase-database-in-android > . Acessado em 21 de Agosto de 2016.

```

```javascript

```

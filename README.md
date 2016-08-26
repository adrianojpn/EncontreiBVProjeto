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
@Overrideprotected void onCreate(Bundle savedInstanceState) 

[...]

FirebaseStorage storage = FirebaseStorage.getInstance();
StorageReference storageRef = storage.getReference();
//folderRef = storageRef.child("photos");//Nome da pasta vai ser o numero do usuario - unicofolderRef = storageRef.child(uidusuarioPasta);
@Overridepublic void onClick(View view) {
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
break;   }}


```




```javascript

```





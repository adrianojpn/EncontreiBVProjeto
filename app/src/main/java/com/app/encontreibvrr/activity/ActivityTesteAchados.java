package com.app.encontreibvrr.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.app.encontreibvrr.R;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ActivityTesteAchados extends AppCompatActivity {

    private RecyclerView rv;

    private DatabaseReference databaseReference;
    private FirebaseHelper helper;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseRecyclerAdapter<PostDocumentosAchados, PostViewHolderDocumentoAchados> mAdapter;
    private static final String TAG = "MainActivityFireRecyclerTeste";

    //private String myUserId;

    private EditText TittuloEvento;
    private EditText DataEvento;
    private EditText Palestrante;
    private EditText Horario;
    private EditText DescricaoIntrocao;
    private EditText DescricaoFull;
    private EditText LocalEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chadosteste);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //A FirebaseApp é inicializado por um ContentProvider por isso não é inicializado no
        // momento onCreate () é chamado. Em seguida, chamar Utils.getDatabase () de qualquer atividade
        // de usar o banco de dados. Obtenha seu FirebaseDatabase como este: método de classe
        // final FirebaseDatabase database = MyDatabaseUtil.getDatabase();
        //MyDatabaseUtil.getDatabase();

        // Aplicativos Firebase tratar automaticamente as interrupções temporárias da rede para você.
        // dados em cache ainda estará disponível enquanto estiver offline e suas gravações será
        // reenviado quando a conectividade de rede é recuperada . Permitindo a persistência de disco
        // permite que nosso aplicativo também para manter todos os seus estados , mesmo após uma
        // reinicialização do aplicativo. Podemos permitir que a persistência de disco com apenas uma
        // linha de código .
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        //INITIALIZE FIREBASE DB
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.push();
        helper = new FirebaseHelper(databaseReference);

        ///////////////////////////////////////////////////////////////////////////////////////////////
        // Ppedaço de código em nosso aplicativo que consulta para os últimos quatro itens na nossa base
        // de dados Firebase Realtime de partituras
                //databaseReference = FirebaseDatabase.getInstance().getReference("Spacecraft");
        //O banco de dados Firebase Realtime sincroniza e armazena uma cópia local dos dados para
        // ouvintes ativos . Além disso, você pode manter ActivityLocais específicos em sincronia .
                //databaseReference.keepSynced(true);
                databaseReference.keepSynced(true);


        //SETUP RECYCLER
        //rv = (RecyclerView) findViewById(R.id.rv);
        //rv.setLayoutManager(new LinearLayoutManager(this));
        mRecycler = (RecyclerView) findViewById(R.id.rv);
        mRecycler.setHasFixedSize(true);

        //ADAPTER
        //adapter = new MyAdapter(this, helper.retrieve());
        //rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //displayInputDialog();
            }
        });


        //myUserId = getUid();

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getApplicationContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        //Query postsQuery = getQuery(mDatabase);
        //Query postsQuery = databaseReference.child("Spacecraft").child(myUserId).orderByChild("starCount");
        Query postsQuery = databaseReference.child("SemanaMinicurso");

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

                // Determine if the current user has liked this post and set UI accordingly
                // Determinar se o usuário atual tem gostou deste post e definir UI conformidade
                /*
                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }
                */


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

}

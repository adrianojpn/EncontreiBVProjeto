package com.app.encontreibvrr.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.encontreibvrr.R;

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
}

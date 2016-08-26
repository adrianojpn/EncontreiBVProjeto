package com.app.encontreibvrr.activity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

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

    //public PostMinicrusos(String uid, String author, String title) {
    //   this.description = uid;
    //    this.name = author;
    //    this.propellant = author;
    //}

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

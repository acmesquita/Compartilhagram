package com.catharina.compartilhagram.dominio.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by catharina on 22/08/16.
 */
public class Post extends RealmObject {

    @PrimaryKey
    private String id;
    private String autor;
    private String curtidas;
    private Date dataPost;
    private String descricao;

    public Post(){
        this.id = UUID.randomUUID().toString();
        this.curtidas = 0+"";
    }


    public Post(String autor, Date dataPost, String descricao) {
        super();
        this.autor = autor;
        this.dataPost = dataPost;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataPost() {
        return dataPost;
    }

    public void setDataPost(Date dataPost) {
        this.dataPost = dataPost;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(String curtidas) {
        this.curtidas = curtidas;
    }

    public String getDataString(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if(dataPost != null){
            return sdf.format(dataPost);
        }
        return "";
    }

}

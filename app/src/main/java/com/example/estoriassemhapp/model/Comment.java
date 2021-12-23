package com.example.estoriassemhapp.model;

import android.graphics.Bitmap;

public class Comment {
    String id;
    String usucom;
    String nomusu;
    String comentario;
    Bitmap foto;

    public Comment(String id, String usucom, String nomusu, String comentario) {
        this.id = id;
        this.usucom = usucom;
        this.nomusu = nomusu;
        this.comentario = comentario;
    }

    public Comment(String id, String usucom, String nomusu, String comentario, Bitmap foto) {
        this.id = id;
        this.usucom = usucom;
        this.nomusu = nomusu;
        this.comentario = comentario;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsucom() {
        return usucom;
    }

    public String getNomusu() {
        return nomusu;
    }

    public void setUsucom(String usucom) {
        this.usucom = usucom;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}

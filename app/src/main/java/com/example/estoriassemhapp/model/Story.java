package com.example.estoriassemhapp.model;

import android.graphics.Bitmap;

public class Story {
    String id;
    String title;
    String text;
    String sinopse;
    String nota;

    String idusuario;
    String autor;
    String indicacao;
    Bitmap capa;

    public Story(String id, String title, String sinopse) {
        this.id = id;
        this.title = title;
        this.sinopse = sinopse;
    }

    public Story(String id, String title, String sinopse, String indicacao, String check) {
        this.id = id;
        this.title = title;
        this.sinopse = sinopse;
        this.indicacao = indicacao;
    }

    public Story(String id, String title, String text, String autor, String idusuario, String check) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.autor = autor;
        this.idusuario = idusuario;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getAutor() {
        return autor;
    }

    public String getIndicacao() {
        return indicacao;
    }
}

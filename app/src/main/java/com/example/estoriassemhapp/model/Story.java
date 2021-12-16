package com.example.estoriassemhapp.model;

import android.graphics.Bitmap;

public class Story {
    String id;
    String title;
    String text;
    String sinopse;
    String nota;
    Bitmap capa;


    public Story(String id, String title, String sinopse) {
        this.id = id;
        this.title = title;
        this.sinopse = sinopse;
    }

    public Story(String id, String title, String text, String nota) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.nota = nota;
    }



    public Story(String id, String title, String text, String sinopse, String nota, Bitmap capa) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.sinopse = sinopse;
        this.nota = nota;
        this.capa = capa;
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

    public String getNota() {
        return nota;
    }

    public Bitmap getCapa() {
        return capa;
    }
}

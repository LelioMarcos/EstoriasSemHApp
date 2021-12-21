package com.example.estoriassemhapp.model;

import android.graphics.Bitmap;

public class Story {
    String id;
    String title;
    String text;
    String sinopse;
    String nota;
    String autor;
    Bitmap capa;


    public Story(String id, String title, String sinopse) {
        this.id = id;
        this.title = title;
        this.sinopse = sinopse;
    }

    public Story(String id, String title, String text, String autor) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.autor = autor;
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
}

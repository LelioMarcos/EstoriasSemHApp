package com.example.estoriassemhapp.model;

import android.graphics.Bitmap;

public class User {
    String id;
    String nome;
    String bio;
    Bitmap foto;

    public User(String id, String nome, String bio, Bitmap foto) {
        this.id = id;
        this.nome = nome;
        this.bio = bio;
        this.foto = foto;
    }

    public User(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}

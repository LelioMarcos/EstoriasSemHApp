package com.example.estoriassemhapp.model;

public class Tag {
    String id;
    String genero;

    public Tag(String id, String genero) {
        this.id = id;
        this.genero = genero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
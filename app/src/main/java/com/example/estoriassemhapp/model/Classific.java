package com.example.estoriassemhapp.model;

public class Classific {
    String id;
    String classif;

    public Classific(String id, String classif) {
        this.id = id;
        this.classif = classif;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassif() {
        return classif;
    }

    public void setClassif(String classif) {
        this.classif = classif;
    }
}
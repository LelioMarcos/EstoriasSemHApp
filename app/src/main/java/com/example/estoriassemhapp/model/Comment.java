package com.example.estoriassemhapp.model;

public class Comment {
    String id;
    String usucom;
    String comentario;

    public Comment(String id, String usucom, String comentario) {
        this.id = id;
        this.usucom = usucom;
        this.comentario = comentario;
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

    public void setUsucom(String usucom) {
        this.usucom = usucom;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}

package com.example.mistareas.noticias;

import java.util.Date;

public class Noticia {

    private String noticiasContenido;
    private String noticiasTitulo;
    private String noticiasUrl;
    private long noticiasTime;

    public String getNoticiasUrl() {
        return noticiasUrl;
    }

    public void setNoticiasUrl(String noticiasUrl) {
        this.noticiasUrl = noticiasUrl;
    }

    public Noticia(String noticiasContenido, String noticiasTitulo) {
        this.noticiasContenido = noticiasContenido;
        this.noticiasTitulo = noticiasTitulo;
        this.noticiasUrl = "";

        // Initialize to current time
        noticiasTime = new Date().getTime();
    }

    public Noticia(){

    }

    public String getNoticiasContenido() {
        return noticiasContenido;
    }

    public void setNoticiasContenido(String noticiasContenido) {
        this.noticiasContenido = noticiasContenido;
    }

    public String getNoticiasTitulo() {
        return noticiasTitulo;
    }

    public void setNoticiasTitulo(String noticiasTitulo) {
        this.noticiasTitulo = noticiasTitulo;
    }

    public long getNoticiasTime() {
        return noticiasTime;
    }

    public void setNoticiasTime(long noticiasTime) {
        this.noticiasTime = noticiasTime;
    }
}
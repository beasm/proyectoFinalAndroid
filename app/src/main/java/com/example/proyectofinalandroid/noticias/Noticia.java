package com.example.proyectofinalandroid.noticias;

public class Noticia {
    // declaramos la variable de la clase
    private String noticiasContenido;
    private String noticiasTitulo;
    private String noticiasUrl;
    private String noticiasImagen;
    private long noticiasTime;

    // contructor vacio
    public Noticia(){
    }

    // setter y getter
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

    public String getNoticiasImagen() {
        return noticiasImagen;
    }

    public void setNoticiasImagen(String noticiasImagen) {
        this.noticiasImagen = noticiasImagen;
    }

    public String getNoticiasUrl() {
        return noticiasUrl;
    }

    public void setNoticiasUrl(String noticiasUrl) {
        this.noticiasUrl = noticiasUrl;
    }
}
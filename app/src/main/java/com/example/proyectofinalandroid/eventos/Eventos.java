package com.example.proyectofinalandroid.eventos;

import java.util.Date;

public class Eventos {

    private String eventosContenido;
    private String eventosTitulo;
    private String eventosGeo;
    private long eventosTime;

    public Eventos(String eventosContenido, String eventosTitulo) {
        this.eventosContenido = eventosContenido;
        this.eventosTitulo = eventosTitulo;
        this.eventosGeo = "geo:1,1";//"geo:" + latitude + "," + longitude ;

        // Initialize to current time
        eventosTime = new Date().getTime();
    }

    public Eventos(){

    }

    public String getEventosContenido() {
        return eventosContenido;
    }

    public void setEventosContenido(String eventosContenido) {
        this.eventosContenido = eventosContenido;
    }

    public String getEventosTitulo() {
        return eventosTitulo;
    }

    public void setEventosTitulo(String eventosTitulo) {
        this.eventosTitulo = eventosTitulo;
    }

    public String getEventosGeo() {
        return eventosGeo;
    }

    public void setEventosGeo(String eventosGeo) {
        this.eventosGeo = eventosGeo;
    }

    public long getEventosTime() {
        return eventosTime;
    }

    public void setEventosTime(long eventosTime) {
        this.eventosTime = eventosTime;
    }
}
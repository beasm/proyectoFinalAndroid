package com.example.proyectofinalandroid.eventos;

public class Eventos {
    // Declaramos  variables de la clase
    private String eventosContenido;
    private String eventosTitulo;
    private String eventosGeo;
    private long eventosTime;

    // Constructor vacío
    public Eventos(){
    }

    // Setter y Getter
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
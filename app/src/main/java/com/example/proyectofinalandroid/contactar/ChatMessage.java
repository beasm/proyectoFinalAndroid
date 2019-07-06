package com.example.proyectofinalandroid.contactar;

import java.util.Date;

public class ChatMessage {
    // Declaramos las variables de la clase
    private String messageText;
    private String messageUser;
    private long messageTime;

    // Constructor con los parámetros
    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Se inicializa la fecha
        messageTime = new Date().getTime();
    }

    // Constructor vacío
    public ChatMessage(){

    }

    // Setter y Getter
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
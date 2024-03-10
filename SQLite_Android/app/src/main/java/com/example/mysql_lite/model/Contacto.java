package com.example.mysql_lite.model;

public class Contacto {
    private String nombre;
    private String movil;
    private String mail;

    public Contacto(String nombre, String movil, String mail) {
        this.nombre = nombre;
        this.movil = movil;
        this.mail = mail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}

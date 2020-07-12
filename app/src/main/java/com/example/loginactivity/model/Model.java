package com.example.loginactivity.model;

public class Model {
    String citaID, userID, especialidad, doctor, fecha, hora,estado;

    public Model(String docString, String string, String doctor, String fecha, String id, String especialidad) {
    }

    public Model(String citaID, String userID, String especialidad, String doctor, String fecha, String hora, String estado) {
        this.citaID = citaID;
        this.userID = userID;
        this.especialidad = especialidad;
        this.doctor = doctor;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public String getCitaID() {
        return citaID;
    }

    public void setCitaID(String citaID) {
        this.citaID = citaID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

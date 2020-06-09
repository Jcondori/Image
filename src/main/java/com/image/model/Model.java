package com.image.model;

public class Model {

    private String DNI;
    private String Nombres;
    private String Apellidos;

    public Model() {
    }

    public Model(String DNI, String nombres, String apellidos) {
        this.DNI = DNI;
        Nombres = nombres;
        Apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Model{" +
                "DNI='" + DNI + '\'' +
                ", Nombres='" + Nombres + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                '}';
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }
}

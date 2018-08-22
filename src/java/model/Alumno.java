package model;


public class Alumno {
    
    private String DNI;
    private String Nombres;

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    @Override
    public String toString() {
        return "Alumno{" + "DNI=" + DNI + ", Nombres=" + Nombres + '}';
    }
    
}

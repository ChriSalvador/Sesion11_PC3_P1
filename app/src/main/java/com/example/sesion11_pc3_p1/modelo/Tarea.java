package com.example.sesion11_pc3_p1.modelo;

public class Tarea {

    private String descripcion;
    private String fechaCreacion;

    public Tarea() {
    }

    public Tarea(String descripcion, String fechaCreacion) {
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "* " + descripcion + " -> " + fechaCreacion;
    }
}

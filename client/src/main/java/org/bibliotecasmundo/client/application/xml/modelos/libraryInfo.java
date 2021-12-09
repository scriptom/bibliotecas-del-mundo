package org.bibliotecasmundo.client.application.xml.modelos;

public class libraryInfo {

    private String nombre;

    private String direccion;

    private String puerto;

    public libraryInfo(String nombre, String direccion, String puerto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.puerto = puerto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPuerto() {
        return puerto;
    }
}

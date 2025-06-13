/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
public class Traspaso {
    private String placa;
    private String dpiAnterior;
    private String nombreAnterior;
    private String fechaAnterior;
    private String dpiNuevo;
    private String nombreNuevo;
    private String departamento; 

    public Traspaso(String placa, String dpiAnterior, String nombreAnterior, String fechaAnterior, String dpiNuevo, String nombreNuevo, String departamento) {
        this.placa = placa;
        this.dpiAnterior = dpiAnterior;
        this.nombreAnterior = nombreAnterior;
        this.fechaAnterior = fechaAnterior;
        this.dpiNuevo = dpiNuevo;
        this.nombreNuevo = nombreNuevo;
        this.departamento = departamento;  // <-- asignar departamento
    }

    // Getters
    public String getPlaca() {
        return placa != null ? placa.trim().toUpperCase() : "";
    }

    public String getDpiAnterior() { 
        return dpiAnterior; 
    }

    public String getNombreAnterior() { 
        return nombreAnterior; 
    }

    public String getFechaAnterior() { 
        return fechaAnterior; 
    }

    public String getDpiNuevo() { 
        return dpiNuevo; 
    }

    public String getNombreNuevo() { 
        return nombreNuevo; 
    }

    public String getDepartamento() {
        return departamento != null ? departamento.trim().toUpperCase() : "";
    }   


    // Métodos opcionales, puedes implementar o eliminar si no usas
    /*
    public Object[] toArray() {
        // Implementa si quieres devolver un arreglo con todos los campos
    }

    public int getBoleta() {
        // Si usas un campo boleta interno, implementa aquí
    }
    */
}


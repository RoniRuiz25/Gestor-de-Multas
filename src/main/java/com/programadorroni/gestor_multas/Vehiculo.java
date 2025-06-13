/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
public class Vehiculo {
    private String placa;
    private String dpi;
    private String nombre;
    private String departamento;
    private String marca;
    private String modelo;
    private String año;
    private String multas;
    private String traspasos;

    // Constructor completo con DEPARTAMENTO
    public Vehiculo(String placa, String dpi, String nombre, String departamento,
                    String marca, String modelo, String año, String multas, String traspasos) {
        this.placa = placa;
        this.dpi = dpi;
        this.nombre = nombre;
        this.departamento = departamento;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.multas = multas;
        this.traspasos = traspasos;
    }

    // Getters
    public String getPlaca() {
        return placa;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getAño() {
        return año;
    }

    public String getMultas() {
        return multas;
    }

    public String getTraspasos() {
        return traspasos;
    }

    // Representación en JTable
    public Object[] toRow() {
        return new Object[]{placa, dpi, nombre, departamento, marca, modelo, año, multas, traspasos};
    }

    Object getAnio() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
public class NodoMulta {
     public String id;
    public String placa;
    public String fecha;
    public String departamento;
    public String descripcion;
    public String monto;

    public NodoMulta siguiente;
    public NodoMulta anterior;
    Multa dato;

    public NodoMulta(String id, String placa, String fecha, String departamento, String descripcion, String monto) {
        this.id = id;
        this.placa = placa;
        this.fecha = fecha;
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.monto = monto;
        this.siguiente = null;
        this.anterior = null;
    }
}
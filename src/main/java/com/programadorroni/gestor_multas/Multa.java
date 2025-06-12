/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;
/**
 *
 * @author isaia
 */
public class Multa implements Comparable<Multa> {
    public int id;
    public String placa;
    public String fecha;
    public String departamento;
    public String descripcion;
    public double monto;
    public String estado;

    public Multa(int id, String placa, String fecha, String departamento, String descripcion, double monto, String estado) {
        this.id = id;
        this.placa = placa;
        this.fecha = fecha;
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.monto = monto;
        this.estado = estado;
    }

    public Object[] toArray() {
        return new Object[]{id, placa, fecha, departamento, descripcion, monto, estado};
    }

    @Override
    public int compareTo(Multa otra) {
        return Integer.compare(this.id, otra.id); // Orden por ID
    }

    // Getters
    public String getPlaca() {
        return placa;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
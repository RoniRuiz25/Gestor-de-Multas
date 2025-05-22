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
    public String placa, fecha, departamento, descripcion, monto;

    public Multa(int id, String placa, String fecha, String departamento, String descripcion, String monto) {
        this.id = id;
        this.placa = placa;
        this.fecha = fecha;
        this.departamento = departamento;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public Object[] toArray() {
        return new Object[]{id, placa, fecha, departamento, descripcion, monto};
    }

    @Override
    public int compareTo(Multa o) {
        return this.placa.compareToIgnoreCase(o.placa);
    }
}

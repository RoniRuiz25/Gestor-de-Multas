/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.programadorroni.gestor_multas;

import java.util.List;

/**
 *
 * @author isaia
 */
public interface Arbol {
    void insertar(Vehiculo vehiculo);
    boolean buscar(String placa);
    List<Vehiculo> inOrden();
    List<Vehiculo> preOrden();
    List<Vehiculo> postOrden();

    public List<Vehiculo> posOrden();
}
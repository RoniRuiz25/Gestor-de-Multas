/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
public interface Arbol {
    void insertar(String placa);
    boolean buscar(String placa);
    String[] inOrden();
    String[] preOrden();
    String[] postOrden();
}
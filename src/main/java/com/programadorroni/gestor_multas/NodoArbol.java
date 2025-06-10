/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
class NodoArbol {

   Vehiculo dato;
    NodoArbol izquierdo;
    NodoArbol derecho;

    public NodoArbol(Vehiculo dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }
}

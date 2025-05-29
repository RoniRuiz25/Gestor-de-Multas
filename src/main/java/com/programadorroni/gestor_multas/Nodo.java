/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
public class Nodo {
     public Multa dato;
    public Nodo siguiente;
    public Nodo anterior;

    public Nodo(Multa dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    public Multa getDato() {
    return dato;
}

public Nodo getSiguiente() {
    return siguiente;
}
}
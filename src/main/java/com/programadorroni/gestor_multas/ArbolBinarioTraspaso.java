/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isaia
 */
public class ArbolBinarioTraspaso {
    private static class NodoArbol {
        Traspaso dato;
        NodoArbol izquierdo, derecho;

        public NodoArbol(Traspaso dato) {
            this.dato = dato;
        }
    }

    private NodoArbol raiz;

    public void insertar(Traspaso dato) {
        raiz = insertarRec(raiz, dato);
    }

    private NodoArbol insertarRec(NodoArbol actual, Traspaso dato) {
        if (actual == null) return new NodoArbol(dato);
        if (dato.getPlaca().compareToIgnoreCase(actual.dato.getPlaca()) < 0)
            actual.izquierdo = insertarRec(actual.izquierdo, dato);
        else
            actual.derecho = insertarRec(actual.derecho, dato);
        return actual;
    }

    public List<Traspaso> recorrerPreOrden() {
        List<Traspaso> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return resultado;
    }

    private void preOrden(NodoArbol nodo, List<Traspaso> lista) {
        if (nodo != null) {
            lista.add(nodo.dato);
            preOrden(nodo.izquierdo, lista);
            preOrden(nodo.derecho, lista);
        }
    }

    public List<Traspaso> recorrerInOrden() {
        List<Traspaso> resultado = new ArrayList<>();
        inOrden(raiz, resultado);
        return resultado;
    }

    private void inOrden(NodoArbol nodo, List<Traspaso> lista) {
        if (nodo != null) {
            inOrden(nodo.izquierdo, lista);
            lista.add(nodo.dato);
            inOrden(nodo.derecho, lista);
        }
    }

    public List<Traspaso> recorrerPosOrden() {
        List<Traspaso> resultado = new ArrayList<>();
        posOrden(raiz, resultado);
        return resultado;
    }

    private void posOrden(NodoArbol nodo, List<Traspaso> lista) {
        if (nodo != null) {
            posOrden(nodo.izquierdo, lista);
            posOrden(nodo.derecho, lista);
            lista.add(nodo.dato);
        }
    }    
}

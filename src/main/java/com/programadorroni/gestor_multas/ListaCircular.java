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
public class ListaCircular {
    private Nodo inicio = null;
    private Nodo fin = null;

   public List<Traspaso> recorrerPreOrden() {
        NodoArbol raiz = construirArbolDesdeLista();
        List<Traspaso> resultado = new ArrayList<>();
        preOrden(raiz, resultado);
        return resultado;
    }

    public List<Traspaso> recorrerInOrden() {
        NodoArbol raiz = construirArbolDesdeLista();
        List<Traspaso> resultado = new ArrayList<>();
        inOrden(raiz, resultado);
        return resultado;
    }

    public List<Traspaso> recorrerPosOrden() {
        NodoArbol raiz = construirArbolDesdeLista();
        List<Traspaso> resultado = new ArrayList<>();
        posOrden(raiz, resultado);
        return resultado;
    }
    
    

    private NodoArbol construirArbolDesdeLista() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void preOrden(NodoArbol raiz, List<Traspaso> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void inOrden(NodoArbol raiz, List<Traspaso> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void posOrden(NodoArbol raiz, List<Traspaso> resultado) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class Nodo {
        Traspaso dato;
        Nodo siguiente;

        Nodo(Traspaso dato) {
            this.dato = dato;
        }
    }

    public void insertar(Traspaso t) {
        Nodo nuevo = new Nodo(t);
        if (inicio == null) {
            inicio = nuevo;
            fin = nuevo;
            nuevo.siguiente = inicio;
        } else {
            fin.siguiente = nuevo;
            nuevo.siguiente = inicio;
            fin = nuevo;
        }
    }

    public java.util.List<Traspaso> obtenerTodos() {
        java.util.List<Traspaso> lista = new java.util.ArrayList<>();
        if (inicio != null) {
            Nodo actual = inicio;
            do {
                lista.add(actual.dato);
                actual = actual.siguiente;
            } while (actual != inicio);
        }
        return lista;
    }
    
}

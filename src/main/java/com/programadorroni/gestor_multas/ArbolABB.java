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
public class ArbolABB implements Arbol {
    class Nodo {
        String placa;
        Nodo izq, der;
        Nodo(String placa) { this.placa = placa; }
    }

    private Nodo raiz;

    @Override
    public void insertar(String placa) {
        raiz = insertarRec(raiz, placa);
    }

    private Nodo insertarRec(Nodo nodo, String placa) {
        if (nodo == null) return new Nodo(placa);
        if (placa.compareTo(nodo.placa) < 0) nodo.izq = insertarRec(nodo.izq, placa);
        else nodo.der = insertarRec(nodo.der, placa);
        return nodo;
    }

    @Override
    public boolean buscar(String placa) {
        return buscarRec(raiz, placa);
    }

    private boolean buscarRec(Nodo nodo, String placa) {
        if (nodo == null) return false;
        if (nodo.placa.equals(placa)) return true;
        return placa.compareTo(nodo.placa) < 0 ? buscarRec(nodo.izq, placa) : buscarRec(nodo.der, placa);
    }

    @Override
    public String[] inOrden() {
        List<String> result = new ArrayList<>();
        inOrdenRec(raiz, result);
        return result.toArray(new String[0]);
    }

    private void inOrdenRec(Nodo nodo, List<String> lista) {
        if (nodo != null) {
            inOrdenRec(nodo.izq, lista);
            lista.add(nodo.placa);
            inOrdenRec(nodo.der, lista);
        }
    }

    @Override
    public String[] preOrden() {
        List<String> result = new ArrayList<>();
        preOrdenRec(raiz, result);
        return result.toArray(new String[0]);
    }

    private void preOrdenRec(Nodo nodo, List<String> lista) {
        if (nodo != null) {
            lista.add(nodo.placa);
            preOrdenRec(nodo.izq, lista);
            preOrdenRec(nodo.der, lista);
        }
    }

    @Override
    public String[] postOrden() {
        List<String> result = new ArrayList<>();
        postOrdenRec(raiz, result);
        return result.toArray(new String[0]);
    }

    private void postOrdenRec(Nodo nodo, List<String> lista) {
        if (nodo != null) {
            postOrdenRec(nodo.izq, lista);
            postOrdenRec(nodo.der, lista);
            lista.add(nodo.placa);
        }
    }
}

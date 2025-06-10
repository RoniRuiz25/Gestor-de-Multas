/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

import java.util.*;

/**
 *
 * @author isaia
 */
public class ArbolAVL implements Arbol {
    class Nodo {
        String placa;
        int altura;
        Nodo izq, der;

        Nodo(String placa) {
            this.placa = placa;
            this.altura = 1;
        }
    }

    private Nodo raiz;

    // ==== MÉTODO PÚBLICO ====

    @Override
    public void insertar(String placa) {
        raiz = insertarRec(raiz, placa);
    }

    @Override
    public boolean buscar(String placa) {
        return buscarRec(raiz, placa);
    }

    @Override
    public String[] inOrden() {
        List<String> lista = new ArrayList<>();
        inOrdenRec(raiz, lista);
        return lista.toArray(new String[0]);
    }

    @Override
    public String[] preOrden() {
        List<String> lista = new ArrayList<>();
        preOrdenRec(raiz, lista);
        return lista.toArray(new String[0]);
    }

    @Override
    public String[] postOrden() {
        List<String> lista = new ArrayList<>();
        postOrdenRec(raiz, lista);
        return lista.toArray(new String[0]);
    }

    // ==== LÓGICA INTERNA ====

    private Nodo insertarRec(Nodo nodo, String placa) {
        if (nodo == null) return new Nodo(placa);

        if (placa.compareTo(nodo.placa) < 0) {
            nodo.izq = insertarRec(nodo.izq, placa);
        } else if (placa.compareTo(nodo.placa) > 0) {
            nodo.der = insertarRec(nodo.der, placa);
        } else {
            return nodo; // no permite duplicados
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    private boolean buscarRec(Nodo nodo, String placa) {
        if (nodo == null) return false;
        if (placa.equals(nodo.placa)) return true;
        return placa.compareTo(nodo.placa) < 0 ? buscarRec(nodo.izq, placa) : buscarRec(nodo.der, placa);
    }

    private void inOrdenRec(Nodo nodo, List<String> lista) {
        if (nodo != null) {
            inOrdenRec(nodo.izq, lista);
            lista.add(nodo.placa);
            inOrdenRec(nodo.der, lista);
        }
    }

    private void preOrdenRec(Nodo nodo, List<String> lista) {
        if (nodo != null) {
            lista.add(nodo.placa);
            preOrdenRec(nodo.izq, lista);
            preOrdenRec(nodo.der, lista);
        }
    }

    private void postOrdenRec(Nodo nodo, List<String> lista) {
        if (nodo != null) {
            postOrdenRec(nodo.izq, lista);
            postOrdenRec(nodo.der, lista);
            lista.add(nodo.placa);
        }
    }

    // ==== MÉTODOS DE BALANCEO ====

    private void actualizarAltura(Nodo nodo) {
        nodo.altura = 1 + Math.max(altura(nodo.izq), altura(nodo.der));
    }

    private int altura(Nodo nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private int factorBalance(Nodo nodo) {
        return nodo == null ? 0 : altura(nodo.izq) - altura(nodo.der);
    }

    private Nodo balancear(Nodo nodo) {
        int balance = factorBalance(nodo);

        if (balance > 1) {
            if (factorBalance(nodo.izq) < 0) {
                nodo.izq = rotarIzquierda(nodo.izq);
            }
            return rotarDerecha(nodo);
        }

        if (balance < -1) {
            if (factorBalance(nodo.der) > 0) {
                nodo.der = rotarDerecha(nodo.der);
            }
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izq;
        Nodo T2 = x.der;

        x.der = y;
        y.izq = T2;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.der;
        Nodo T2 = y.izq;

        y.izq = x;
        x.der = T2;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }
}

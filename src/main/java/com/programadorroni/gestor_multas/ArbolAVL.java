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

    @Override
    public List<Vehiculo> posOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
public Vehiculo buscarVehiculo(String placa) {
    return buscarVehiculoRec(raiz, placa);
}

private Vehiculo buscarVehiculoRec(Nodo nodo, String placa) {
    if (nodo == null) return null;

    int cmp = placa.compareToIgnoreCase(nodo.vehiculo.getPlaca());

    if (cmp == 0) {
        return nodo.vehiculo;
    } else if (cmp < 0) {
        return buscarVehiculoRec(nodo.izq, placa);
    } else {
        return buscarVehiculoRec(nodo.der, placa);
    }
}


    class Nodo {
        Vehiculo vehiculo;
        int altura;
        Nodo izq, der;

        Nodo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
            this.altura = 1;
        }
    }

    private Nodo raiz;

    @Override
    public void insertar(Vehiculo vehiculo) {
        raiz = insertarRec(raiz, vehiculo);
    }

    private Nodo insertarRec(Nodo nodo, Vehiculo vehiculo) {
        if (nodo == null) return new Nodo(vehiculo);

        int cmp = vehiculo.getPlaca().compareTo(nodo.vehiculo.getPlaca());
        if (cmp < 0) {
            nodo.izq = insertarRec(nodo.izq, vehiculo);
        } else if (cmp > 0) {
            nodo.der = insertarRec(nodo.der, vehiculo);
        } else {
            return nodo; // duplicado
        }

        actualizarAltura(nodo);
        return balancear(nodo);
    }

    @Override
    public boolean buscar(String placa) {
        return buscarRec(raiz, placa);
    }

    private boolean buscarRec(Nodo nodo, String placa) {
        if (nodo == null) return false;
        int cmp = placa.compareTo(nodo.vehiculo.getPlaca());
        if (cmp == 0) return true;
        else if (cmp < 0) return buscarRec(nodo.izq, placa);
        else return buscarRec(nodo.der, placa);
    }

    @Override
    public List<Vehiculo> inOrden() {
        List<Vehiculo> lista = new ArrayList<>();
        inOrdenRec(raiz, lista);
        return lista;
    }

    private void inOrdenRec(Nodo nodo, List<Vehiculo> lista) {
        if (nodo != null) {
            inOrdenRec(nodo.izq, lista);
            lista.add(nodo.vehiculo);
            inOrdenRec(nodo.der, lista);
        }
    }

    @Override
    public List<Vehiculo> preOrden() {
        List<Vehiculo> lista = new ArrayList<>();
        preOrdenRec(raiz, lista);
        return lista;
    }

    private void preOrdenRec(Nodo nodo, List<Vehiculo> lista) {
        if (nodo != null) {
            lista.add(nodo.vehiculo);
            preOrdenRec(nodo.izq, lista);
            preOrdenRec(nodo.der, lista);
        }
    }

    public List<Vehiculo> postOrden() {
        List<Vehiculo> lista = new ArrayList<>();
        postOrdenRec(raiz, lista);
        return lista;
    }

    private void postOrdenRec(Nodo nodo, List<Vehiculo> lista) {
        if (nodo != null) {
            postOrdenRec(nodo.izq, lista);
            postOrdenRec(nodo.der, lista);
            lista.add(nodo.vehiculo);
        }
    }

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

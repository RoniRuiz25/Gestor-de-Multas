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

    @Override
    public List<Vehiculo> posOrden() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

  public Vehiculo buscarVehiculo(String placaBuscada) {
    return buscarVehiculoRec(raiz, placaBuscada);
}

private Vehiculo buscarVehiculoRec(Nodo nodo, String placaBuscada) {
    if (nodo == null) return null;

    int cmp = placaBuscada.compareTo(nodo.vehiculo.getPlaca());
    if (cmp == 0) return nodo.vehiculo;
    else if (cmp < 0) return buscarVehiculoRec(nodo.izq, placaBuscada);
    else return buscarVehiculoRec(nodo.der, placaBuscada);
}

   public boolean eliminar(String placa) {
    if (buscarVehiculo(placa) != null) {
        raiz = eliminarRec(raiz, placa); // usa el método recursivo real
        return true;
    }
    return false;
}

    private Nodo eliminarRec(Nodo raiz, String placa) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   class Nodo {
        Vehiculo vehiculo;
        Nodo izq, der;

        Nodo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
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
        if (cmp < 0) nodo.izq = insertarRec(nodo.izq, vehiculo);
        else if (cmp > 0) nodo.der = insertarRec(nodo.der, vehiculo);
        return nodo;
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
    posOrdenRec(raiz, lista);
    return lista;
    }

    private void posOrdenRec(Nodo nodo, List<Vehiculo> lista) {
        if (nodo != null) {
            posOrdenRec(nodo.izq, lista);
            posOrdenRec(nodo.der, lista);
            lista.add(nodo.vehiculo);
        }
    }
}   
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

import java.util.ArrayList;
import java.util.List;

public class ListaDobleMulta {
    public Nodo cabeza;

    public void insertarOrdenado(Multa multa) {
        Nodo nuevo = new Nodo(multa);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo actual = cabeza;
            Nodo anterior = null;
            while (actual != null && actual.dato.compareTo(multa) < 0) {
                anterior = actual;
                actual = actual.siguiente;
            }
            if (anterior == null) {
                nuevo.siguiente = cabeza;
                cabeza.anterior = nuevo;
                cabeza = nuevo;
            } else {
                nuevo.siguiente = actual;
                nuevo.anterior = anterior;
                anterior.siguiente = nuevo;
                if (actual != null) actual.anterior = nuevo;
            }
        }
    }
    
    public Multa buscarPorBoleta(int boleta) {
    Nodo actual = cabeza;
    while (actual != null) {
        if (actual.dato.id == boleta) {
            return actual.dato;
        }
        actual = actual.siguiente;
    }
    return null;
}

    public Multa buscarPorPlaca(String placa) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.placa.equalsIgnoreCase(placa)) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    public Multa buscarPorID(int id) {
    Nodo actual = cabeza;
    while (actual != null) {
        if (actual.dato.id == id) return actual.dato;
        actual = actual.siguiente;
    }
    return null;
}
    
    public Object[][] buscarMultasPorPlaca(String placa) {
        List<Object[]> lista = new ArrayList<>();
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.placa.equalsIgnoreCase(placa)) {
                lista.add(actual.dato.toArray());
            }
            actual = actual.siguiente;
        }
        return lista.toArray(new Object[0][]);
    }

    public boolean eliminarPorID(int id) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.id == id) {
                if (actual.anterior != null) actual.anterior.siguiente = actual.siguiente;
                else cabeza = actual.siguiente;
                if (actual.siguiente != null) actual.siguiente.anterior = actual.anterior;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public void limpiar() {
        cabeza = null;
    }

    public Object[][] obtenerDatos() {
        int size = contar();
        Object[][] datos = new Object[size][7]; // Incluye campo 'estado'
        Nodo actual = cabeza;
        int i = 0;
        while (actual != null) {
            datos[i++] = actual.dato.toArray();
            actual = actual.siguiente;
        }
        return datos;
    }
     
    public int contar() {
        int count = 0;
        Nodo actual = cabeza;
        while (actual != null) {
            count++;
            actual = actual.siguiente;
        }
        return count;
    }

    void insertarAlFinal(Multa m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Nodo getCabeza() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void insertarFinal(Multa nueva) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void vaciar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
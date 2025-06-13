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

   private Nodo raiz;

    // Clase interna Nodo con getter necesario
    class Nodo {
        Vehiculo vehiculo;
        Nodo izq, der;

        Nodo(Vehiculo vehiculo) {
            this.vehiculo = vehiculo;
        }

        public Vehiculo getVehiculo() {
            return vehiculo;
        }
    }

    // Inserción
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

    // Búsqueda (booleano)
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

    // Búsqueda (devolver objeto)
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

    // Eliminar
    public boolean eliminar(String placa) {
        if (buscarVehiculo(placa) != null) {
            raiz = eliminarRec(raiz, placa);
            return true;
        }
        return false;
    }

    private Nodo eliminarRec(Nodo nodo, String placa) {
        if (nodo == null) return null;

        int cmp = placa.compareTo(nodo.vehiculo.getPlaca());

        if (cmp < 0) {
            nodo.izq = eliminarRec(nodo.izq, placa);
        } else if (cmp > 0) {
            nodo.der = eliminarRec(nodo.der, placa);
        } else {
            // Nodo con un solo hijo o sin hijos
            if (nodo.izq == null) return nodo.der;
            else if (nodo.der == null) return nodo.izq;

            // Nodo con dos hijos: obtener el sucesor
            Nodo sucesor = obtenerMin(nodo.der);
            nodo.vehiculo = sucesor.vehiculo;
            nodo.der = eliminarRec(nodo.der, sucesor.vehiculo.getPlaca());
        }
        return nodo;
    }

    private Nodo obtenerMin(Nodo nodo) {
        while (nodo.izq != null) nodo = nodo.izq;
        return nodo;
    }

    // Recorridos
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

    @Override
    public List<Vehiculo> posOrden() {
        return postOrden(); // Llama al método corregido
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

    // Generación de DOT para visualizar el árbol
    public String generarDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph G {\n");
        sb.append("node [shape=box, style=filled, color=lightblue];\n");
        generarDotRecursivo(raiz, sb);
        sb.append("}\n");
        return sb.toString();
    }

    private void generarDotRecursivo(Nodo nodo, StringBuilder sb) {
        if (nodo == null) return;

        Vehiculo v = nodo.getVehiculo();
        String id = v.getPlaca();

        String label = String.format("%s\\n%s\\n%s", v.getPlaca(), v.getNombre(), v.getDepartamento());
        sb.append("\"").append(id).append("\" [label=\"").append(label).append("\"];\n");

        if (nodo.izq != null) {
            sb.append("\"").append(id).append("\" -> \"").append(nodo.izq.getVehiculo().getPlaca()).append("\";\n");
            generarDotRecursivo(nodo.izq, sb);
        }

        if (nodo.der != null) {
            sb.append("\"").append(id).append("\" -> \"").append(nodo.der.getVehiculo().getPlaca()).append("\";\n");
            generarDotRecursivo(nodo.der, sb);
        }
    } 
}
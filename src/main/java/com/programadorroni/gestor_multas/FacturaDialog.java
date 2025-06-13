/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadorroni.gestor_multas;

/**
 *
 * @author isaia
 */
import javax.swing.*;
import java.awt.*;

public class FacturaDialog extends JDialog {

    public FacturaDialog(JFrame parent, int boleta, String placa, String dpiAnt, String nombreAnt, String fecha, String dpiNuevo, String nombreNuevo, String departamento) {
        super(parent, "Factura - Boleta #" + boleta, true);
        setSize(480, 450);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Título
        JLabel titulo = new JLabel("FACTURA DE TRASPASO VEHICULAR", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setForeground(new Color(0, 70, 130));
        panel.add(titulo, BorderLayout.NORTH);

        // Panel con datos
        JPanel datosPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        datosPanel.setBackground(Color.WHITE);

        datosPanel.add(crearFila("Boleta:", String.valueOf(boleta)));
        datosPanel.add(crearFila("Placa:", placa));
        datosPanel.add(crearFila("DPI Anterior:", dpiAnt));
        datosPanel.add(crearFila("Nombre Anterior:", nombreAnt));
        datosPanel.add(crearFila("Fecha de Traspaso:", fecha));
        datosPanel.add(crearFila("DPI Nuevo:", dpiNuevo));
        datosPanel.add(crearFila("Nombre Nuevo:", nombreNuevo));
        datosPanel.add(crearFila("Departamento:", departamento));  // Nuevo campo

        panel.add(datosPanel, BorderLayout.CENTER);

        // Pie de página
        JLabel footer = new JLabel("Gracias por usar nuestro sistema de Traspasos", SwingConstants.CENTER);
        footer.setFont(new Font("Tahoma", Font.ITALIC, 12));
        footer.setForeground(new Color(100, 100, 100));
        panel.add(footer, BorderLayout.SOUTH);

        add(panel);
    }

    private JPanel crearFila(String etiqueta, String valor) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(Color.WHITE);

        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Tahoma", Font.BOLD, 14));
        label.setForeground(new Color(0, 60, 120));

        JLabel valorLabel = new JLabel(valor);
        valorLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        valorLabel.setForeground(Color.DARK_GRAY);

        fila.add(label, BorderLayout.WEST);
        fila.add(valorLabel, BorderLayout.EAST);

        return fila;
    }
}
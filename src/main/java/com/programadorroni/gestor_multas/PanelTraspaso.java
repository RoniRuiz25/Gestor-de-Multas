/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.programadorroni.gestor_multas;

import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author isaia
 */
public class PanelTraspaso extends javax.swing.JPanel {

    private ListaCircular listaCircular = new ListaCircular();
    private File archivoActual;
    
    /**
     * Creates new form PanelTraspaso
     */
    public PanelTraspaso() {
        initComponents(); 
    }
    
    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Prueba PanelTraspaso");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new PanelTraspaso());
        frame.setSize(800, 600);
        frame.setVisible(true);
    });
}
   
    private void guardarCambiosEnArchivo() {
    if (archivoActual == null) return;

    try (java.io.PrintWriter writer = new java.io.PrintWriter(archivoActual)) {
        for (Traspaso t : listaCircular.obtenerTodos()) {
            writer.println(t.getBoleta() + "," + t.getPlaca() + "," + t.getDpiAnterior() + "," +
                           t.getNombreAnterior() + "," + t.getFechaAnterior() + "," +
                           t.getDpiNuevo() + "," + t.getNombreNuevo() + "," + t.getDepartamento());
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al guardar el archivo.");
    }
}
    
    private void setArchivoActual(File file) {
     archivoActual = file;
    listaCircular = new ListaCircular(); // Reiniciar
    try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
        String linea;
        int contadorBoleta = 1; // BOLETA interna para UI

        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length >= 6) {
                String departamento = partes.length >= 7 ? partes[6].trim() : "";

                Traspaso t = new Traspaso(
                    contadorBoleta++,           // BOLETA generado para lista/tablas, NO para archivo
                    partes[0].trim(),           // PLACA
                    partes[1].trim(),           // DPI ANTERIOR
                    partes[2].trim(),           // NOMBRE ANTERIOR
                    partes[3].trim(),           // FECHA
                    partes[4].trim(),           // DPI NUEVO
                    partes[5].trim(),           // NOMBRE NUEVO
                    departamento                // DEPARTAMENTO
                );

                listaCircular.insertar(t);
            }
        }

        llenarTablaConListaCircular();

    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error al leer el archivo");
    }
}

    private void guardarArchivo() {
    if (archivoActual == null) {
        JOptionPane.showMessageDialog(this, "No hay archivo para guardar.");
        return;
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(archivoActual))) {
        for (Traspaso t : listaCircular.obtenerTodos()) {
            // Aquí decides si quieres guardar la boleta en archivo o no
            // Por ejemplo, si no quieres guardarla, no la agregues al texto:
            String linea = String.join(",",
                t.getPlaca(),
                t.getDpiAnterior(),
                t.getNombreAnterior(),
                t.getFechaAnterior(),
                t.getDpiNuevo(),
                t.getNombreNuevo(),
                t.getDepartamento()
            );
            writer.println(linea);
        }
        JOptionPane.showMessageDialog(this, "Archivo guardado correctamente.");
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al guardar el archivo.");
    }
}
    
private void llenarTablaConListaCircular() {
    // Agregamos la columna DEPARTAMENTO en el modelo
    String[] columnas = {"BOLETA", "PLACA", "DPI ANTERIOR", "NOMBRE ANTERIOR", "FECHA", "DPI", "NOMBRE", "DEPARTAMENTO"};
    javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(columnas, 0);

    int boleta = 1;
    for (Traspaso t : listaCircular.obtenerTodos()) {
        Object[] fila = {
            boleta++,
            t.getPlaca(),
            t.getDpiAnterior(),
            t.getNombreAnterior(),
            t.getFechaAnterior(),
            t.getDpiNuevo(),
            t.getNombreNuevo(),
            t.getDepartamento()  // Nueva columna agregada
        };
        modelo.addRow(fila);
    }

    Tabla_Dat_Traspaso.setModel(modelo);
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Desplegable = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Home = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        Titulo1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Titulo3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        TextPlaca1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Bus_Placa_3 = new javax.swing.JButton();
        TextBoleta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        Bus_Boleta_2 = new javax.swing.JButton();
        Refresh_Placa1 = new javax.swing.JButton();
        Refresh_Boleta = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        Buscar_Fch_2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Desplegable1 = new javax.swing.JPanel();
        Home1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        TextPlaca = new javax.swing.JTextField();
        Bus_Placa_2 = new javax.swing.JButton();
        Refresh_Placa = new javax.swing.JButton();
        Titulo2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TextBoleta1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        Bus_Boleta_3 = new javax.swing.JButton();
        Refresh_Boleta1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        Factura = new javax.swing.JButton();
        Eliminar_1 = new javax.swing.JButton();
        Editar_Traspaso = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        Bt_Traspaso = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Dat_Traspaso = new javax.swing.JTable();
        Guardar = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        Usuario = new javax.swing.JButton();
        User_Indicator = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Buscar_Fch_3 = new javax.swing.JButton();

        Desplegable.setBackground(new java.awt.Color(31, 49, 73));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LOGOTIPO");

        Home.setBackground(new java.awt.Color(31, 49, 73));
        Home.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-casa-32.png")); // NOI18N
        Home.setBorder(null);
        Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HomeActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("HOME");

        javax.swing.GroupLayout DesplegableLayout = new javax.swing.GroupLayout(Desplegable);
        Desplegable.setLayout(DesplegableLayout);
        DesplegableLayout.setHorizontalGroup(
            DesplegableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesplegableLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(DesplegableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DesplegableLayout.createSequentialGroup()
                        .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        DesplegableLayout.setVerticalGroup(
            DesplegableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DesplegableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(DesplegableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Titulo1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        Titulo1.setForeground(new java.awt.Color(255, 255, 255));
        Titulo1.setText("Pago de Multas");

        jPanel3.setBackground(new java.awt.Color(18, 38, 70));

        Titulo3.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        Titulo3.setForeground(new java.awt.Color(255, 255, 255));
        Titulo3.setText("Pago de Multas");

        jButton1.setBackground(new java.awt.Color(18, 38, 70));
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-pago-48.png")); // NOI18N
        jButton1.setBorder(null);

        TextPlaca1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextPlaca1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Placa");

        Bus_Placa_3.setBackground(new java.awt.Color(18, 38, 70));
        Bus_Placa_3.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-buscar-244.png")); // NOI18N
        Bus_Placa_3.setBorder(null);
        Bus_Placa_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bus_Placa_3ActionPerformed(evt);
            }
        });

        TextBoleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoletaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Boleta");

        Bus_Boleta_2.setBackground(new java.awt.Color(18, 38, 70));
        Bus_Boleta_2.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-buscar-244.png")); // NOI18N
        Bus_Boleta_2.setBorder(null);
        Bus_Boleta_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bus_Boleta_2ActionPerformed(evt);
            }
        });

        Refresh_Placa1.setBackground(new java.awt.Color(18, 38, 70));
        Refresh_Placa1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-recargar-16.png")); // NOI18N
        Refresh_Placa1.setBorder(null);
        Refresh_Placa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Refresh_Placa1ActionPerformed(evt);
            }
        });

        Refresh_Boleta.setBackground(new java.awt.Color(18, 38, 70));
        Refresh_Boleta.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-recargar-16.png")); // NOI18N
        Refresh_Boleta.setBorder(null);
        Refresh_Boleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Refresh_BoletaActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Agregar Fichero Existente");

        Buscar_Fch_2.setBackground(new java.awt.Color(17, 34, 61));
        Buscar_Fch_2.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-folder-48.png")); // NOI18N
        Buscar_Fch_2.setBorder(null);
        Buscar_Fch_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar_Fch_2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Titulo3))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextPlaca1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(Bus_Placa_3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Refresh_Placa1))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(Bus_Boleta_2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Refresh_Boleta))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Buscar_Fch_2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Titulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Refresh_Placa1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(TextPlaca1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Bus_Placa_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(Bus_Boleta_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(Refresh_Boleta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(Buscar_Fch_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel1.setBackground(new java.awt.Color(17, 34, 61));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        Desplegable1.setBackground(new java.awt.Color(31, 49, 73));

        Home1.setBackground(new java.awt.Color(31, 49, 73));
        Home1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-casa-32.png")); // NOI18N
        Home1.setBorder(null);
        Home1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Home1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("HOME");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Gestor de Multas");

        javax.swing.GroupLayout Desplegable1Layout = new javax.swing.GroupLayout(Desplegable1);
        Desplegable1.setLayout(Desplegable1Layout);
        Desplegable1Layout.setHorizontalGroup(
            Desplegable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Desplegable1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(Desplegable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Desplegable1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addGroup(Desplegable1Layout.createSequentialGroup()
                        .addComponent(Home1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Desplegable1Layout.setVerticalGroup(
            Desplegable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Desplegable1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Desplegable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Home1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(18, 38, 70));

        TextPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextPlacaActionPerformed(evt);
            }
        });

        Bus_Placa_2.setBackground(new java.awt.Color(18, 38, 70));
        Bus_Placa_2.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-buscar-244.png")); // NOI18N
        Bus_Placa_2.setBorder(null);
        Bus_Placa_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bus_Placa_2ActionPerformed(evt);
            }
        });

        Refresh_Placa.setBackground(new java.awt.Color(18, 38, 70));
        Refresh_Placa.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-recargar-16.png")); // NOI18N
        Refresh_Placa.setBorder(null);
        Refresh_Placa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Refresh_PlacaActionPerformed(evt);
            }
        });

        Titulo2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        Titulo2.setForeground(new java.awt.Color(255, 255, 255));
        Titulo2.setText("Traspasos");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Placa");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));

        TextBoleta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextBoleta1ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Boleta");

        Bus_Boleta_3.setBackground(new java.awt.Color(18, 38, 70));
        Bus_Boleta_3.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-buscar-244.png")); // NOI18N
        Bus_Boleta_3.setBorder(null);
        Bus_Boleta_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bus_Boleta_3ActionPerformed(evt);
            }
        });

        Refresh_Boleta1.setBackground(new java.awt.Color(18, 38, 70));
        Refresh_Boleta1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-recargar-16.png")); // NOI18N
        Refresh_Boleta1.setBorder(null);
        Refresh_Boleta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Refresh_Boleta1ActionPerformed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\Traspaso-1.png")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(TextBoleta1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bus_Boleta_3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Refresh_Boleta1))
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(TextPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bus_Placa_2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Refresh_Placa))
                            .addComponent(jLabel18)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(Titulo2)))
                .addContainerGap(123, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(205, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addContainerGap(206, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Titulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(28, 28, 28)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bus_Placa_2)
                    .addComponent(Refresh_Placa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Bus_Boleta_3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextBoleta1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Refresh_Boleta1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(171, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addContainerGap(171, Short.MAX_VALUE)))
        );

        jPanel5.setBackground(new java.awt.Color(18, 38, 70));

        Titulo.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo.setText("Nuevo Traspaso");

        Factura.setBackground(new java.awt.Color(242, 242, 242));
        Factura.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\factura.png")); // NOI18N
        Factura.setToolTipText("");
        Factura.setBorder(null);
        Factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FacturaActionPerformed(evt);
            }
        });

        Eliminar_1.setBackground(new java.awt.Color(242, 242, 242));
        Eliminar_1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-eliminar-24.png")); // NOI18N
        Eliminar_1.setBorder(null);
        Eliminar_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Eliminar_1ActionPerformed(evt);
            }
        });

        Editar_Traspaso.setBackground(new java.awt.Color(242, 242, 242));
        Editar_Traspaso.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\Editar-1.png")); // NOI18N
        Editar_Traspaso.setBorder(null);
        Editar_Traspaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Editar_TraspasoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Editar_Traspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Eliminar_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Factura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Factura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Editar_Traspaso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Eliminar_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\transferancia-1.png")); // NOI18N

        Bt_Traspaso.setBackground(new java.awt.Color(242, 242, 242));
        Bt_Traspaso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Bt_Traspaso.setText("TRASPASO");
        Bt_Traspaso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bt_TraspasoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Titulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(Bt_Traspaso)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(Bt_Traspaso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Tabla_Dat_Traspaso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "BOLETA", "PLACA", "DPI ANTERIOR", "NOMBRE ANTERIOR", "FECHA", "DPI", "NOMBRE", "DEPARTAMENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla_Dat_Traspaso.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Tabla_Dat_Traspaso);

        Guardar.setBackground(new java.awt.Color(242, 242, 242));
        Guardar.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-guardar-16.png")); // NOI18N
        Guardar.setText("GUARDAR");
        Guardar.setBorder(null);
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setText("DATOS GENERALES DE TRASPASOS");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        Usuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-usuario-16.png")); // NOI18N
        Usuario.setBorder(null);
        Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioActionPerformed(evt);
            }
        });

        User_Indicator.setText("Roni Ruiz");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(1005, Short.MAX_VALUE)
                .addComponent(User_Indicator, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Usuario)
                .addGap(22, 22, 22))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(User_Indicator)))
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Agregar Fichero Existente");

        Buscar_Fch_3.setBackground(new java.awt.Color(17, 34, 61));
        Buscar_Fch_3.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-folder-48.png")); // NOI18N
        Buscar_Fch_3.setBorder(null);
        Buscar_Fch_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar_Fch_3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Desplegable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Buscar_Fch_3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Buscar_Fch_3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Desplegable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        // TODO add your handling code here:
    
    }//GEN-LAST:event_HomeActionPerformed

    private void Home1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Home1ActionPerformed
        // TODO add your handling code here:
       java.awt.Container parent = this.getParent();
        // Reemplazar el contenido del contenedor con el PanelPrincipal
        if (parent instanceof javax.swing.JPanel) {
            parent.removeAll();
            parent.add(new PanelPrincipal()); // Asegúrate de importar la clase si está en otro paquete
            parent.revalidate();
            parent.repaint();
        }
    }//GEN-LAST:event_Home1ActionPerformed

    private void TextPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextPlacaActionPerformed

    private void Bus_Placa_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bus_Placa_2ActionPerformed
       String placaBuscar = TextPlaca.getText().trim();
    if (placaBuscar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese una placa para buscar.");
        return;
    }

    // Iniciar cronómetro
    long inicio = System.nanoTime();

    // Buscar en la lista circular los registros con esa placa
    List<Traspaso> resultados = new java.util.ArrayList<>();
    for (Traspaso t : listaCircular.obtenerTodos()) {
        if (t.getPlaca().equalsIgnoreCase(placaBuscar)) {
            resultados.add(t);
        }
    }

    long fin = System.nanoTime(); // Termina cronómetro
    long duracionNano = fin - inicio;
    long duracionMili = duracionNano / 1_000_000;

    if (resultados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No se encontraron registros con esa placa.");
        return;
    }

    // Llenar la tabla con los resultados encontrados
    String[] columnas = {"BOLETA", "PLACA", "DPI ANTERIOR", "NOMBRE ANTERIOR", "FECHA", "DPI NUEVO", "NOMBRE NUEVO", "DEPARTAMENTO"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    for (Traspaso t : resultados) {
        Object[] fila = {
            t.getBoleta(),
            t.getPlaca(),
            t.getDpiAnterior(),
            t.getNombreAnterior(),
            t.getFechaAnterior(),
            t.getDpiNuevo(),
            t.getNombreNuevo(),
            t.getDepartamento()
        };
        modelo.addRow(fila);
    }

    Tabla_Dat_Traspaso.setModel(modelo);

    // Mostrar tiempo de búsqueda
    JOptionPane.showMessageDialog(this,
        "Búsqueda completada.\n" +
        "Tiempo: " + duracionNano + " nanosegundos\n" +
        "        " + duracionMili + " milisegundos"
    );
    }//GEN-LAST:event_Bus_Placa_2ActionPerformed

    private void Refresh_PlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refresh_PlacaActionPerformed
        // TODO add your handling code here:
        // Limpiar campos de búsqueda
    TextPlaca.setText("");
    TextBoleta1.setText("");

    long inicio = System.nanoTime();

    // Actualizar tabla con los datos actuales en la lista circular (memoria)
    llenarTablaConListaCircular();

    long fin = System.nanoTime();

    long duracionMs = (fin - inicio) / 1_000_000;
    JOptionPane.showMessageDialog(this, "Tabla actualizada.\nDuración: " + duracionMs + " ms");
    }//GEN-LAST:event_Refresh_PlacaActionPerformed

    private void TextPlaca1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextPlaca1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextPlaca1ActionPerformed

    private void Bus_Placa_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bus_Placa_3ActionPerformed
        
    }//GEN-LAST:event_Bus_Placa_3ActionPerformed

    private void TextBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoletaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoletaActionPerformed

    private void Bus_Boleta_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bus_Boleta_2ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_Bus_Boleta_2ActionPerformed

    private void Refresh_Placa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refresh_Placa1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_Refresh_Placa1ActionPerformed

    private void Refresh_BoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refresh_BoletaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_Refresh_BoletaActionPerformed

    private void Buscar_Fch_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar_Fch_2ActionPerformed
        // TODO add your handling code here: NO ESTA ESTE BOTON
    }//GEN-LAST:event_Buscar_Fch_2ActionPerformed

    private void FacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FacturaActionPerformed
        // TODO add your handling code here:
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de BOLETA para generar la factura:");

    if (input != null && !input.trim().isEmpty()) {
        try {
            int numBoleta = Integer.parseInt(input.trim());
            DefaultTableModel modelo = (DefaultTableModel) Tabla_Dat_Traspaso.getModel();
            boolean encontrada = false;

            // Inicia medición de tiempo
            long inicio = System.nanoTime();

            for (int i = 0; i < modelo.getRowCount(); i++) {
                int boletaFila = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                if (boletaFila == numBoleta) {
                    encontrada = true;

                    String placa = modelo.getValueAt(i, 1).toString();
                    String dpiAnterior = modelo.getValueAt(i, 2).toString();
                    String nombreAnterior = modelo.getValueAt(i, 3).toString();
                    String fecha = modelo.getValueAt(i, 4).toString();
                    String dpiNuevo = modelo.getValueAt(i, 5).toString();
                    String nombreNuevo = modelo.getValueAt(i, 6).toString();
                    String departamento = modelo.getValueAt(i, 7).toString(); // Nueva columna

                    // Obtener JFrame padre para pasar al diálogo
                    Component comp = (Component) evt.getSource();
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(comp);

                    FacturaDialog factura = new FacturaDialog(frame, numBoleta, placa, dpiAnterior, nombreAnterior, fecha, dpiNuevo, nombreNuevo, departamento);
                    factura.setVisible(true);

                    break;
                }
            }

            // Fin de tiempo
            long fin = System.nanoTime();
            long duracionNano = fin - inicio;
            long duracionMili = duracionNano / 1_000_000;

            if (encontrada) {
                JOptionPane.showMessageDialog(this,
                    "Factura generada correctamente.\n" +
                    "Tiempo de búsqueda: " + duracionNano + " nanosegundos\n" +
                    "                     " + duracionMili + " milisegundos");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la boleta #" + numBoleta,
                        "Boleta no encontrada", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de boleta inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_FacturaActionPerformed

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        // TODO add your handling code here:
       long inicio = System.nanoTime(); // Iniciar conteo de tiempo

    if (archivoActual == null) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            archivoActual = fileChooser.getSelectedFile();
            if (!archivoActual.getName().toLowerCase().endsWith(".txt")) {
                archivoActual = new File(archivoActual.getAbsolutePath() + ".txt");
            }
        } else {
            return; // Cancelado por el usuario
        }
    }

    try (PrintWriter writer = new PrintWriter(archivoActual)) {
        DefaultTableModel model = (DefaultTableModel) Tabla_Dat_Traspaso.getModel();
        int filas = model.getRowCount();
        int columnas = model.getColumnCount();

        for (int i = 0; i < filas; i++) {
            StringBuilder linea = new StringBuilder();
            for (int j = 1; j < columnas; j++) { // Comenzamos desde j = 1 para evitar la columna BOLETA
                linea.append(model.getValueAt(i, j));
                if (j < columnas - 1) {
                    linea.append(",");
                }
            }
            writer.println(linea.toString());
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al guardar el archivo");
        return;
    }

    long fin = System.nanoTime(); // Fin de tiempo
    long tiempoNano = fin - inicio;
    long tiempoMili = tiempoNano / 1_000_000;

    JOptionPane.showMessageDialog(this,
        "Archivo guardado correctamente.\n" +
        "Tiempo de guardado:\n" +
        "- " + tiempoMili + " milisegundos\n" +
        "- " + tiempoNano + " nanosegundos");
    }//GEN-LAST:event_GuardarActionPerformed

    private void TextBoleta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoleta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoleta1ActionPerformed

    private void Bus_Boleta_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bus_Boleta_3ActionPerformed
        // TODO add your handling code here:   
        String textoBoleta = TextBoleta1.getText().trim();

    if (!textoBoleta.isEmpty()) {
        try {
            int boletaBuscada = Integer.parseInt(textoBoleta);

            // Iniciar cronómetro
            long inicio = System.nanoTime();

            // Buscar en la lista circular
            Traspaso encontrado = null;
            for (Traspaso t : listaCircular.obtenerTodos()) {
                if (t.getBoleta() == boletaBuscada) {
                    encontrado = t;
                    break;
                }
            }

            long fin = System.nanoTime(); // Fin del cronómetro
            long duracionNano = fin - inicio;
            long duracionMili = duracionNano / 1_000_000;

            if (encontrado != null) {
                // Mostrar resultado en la tabla
                String[] columnas = {"BOLETA", "PLACA", "DPI ANTERIOR", "NOMBRE ANTERIOR", "FECHA", "DPI NUEVO", "NOMBRE NUEVO", "DEPARTAMENTO"};
                DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

                Object[] fila = {
                    encontrado.getBoleta(),
                    encontrado.getPlaca(),
                    encontrado.getDpiAnterior(),
                    encontrado.getNombreAnterior(),
                    encontrado.getFechaAnterior(),
                    encontrado.getDpiNuevo(),
                    encontrado.getNombreNuevo(),
                    encontrado.getDepartamento()
                };
                modelo.addRow(fila);

                Tabla_Dat_Traspaso.setModel(modelo);

                JOptionPane.showMessageDialog(this,
                    "Búsqueda completada.\n" +
                    "Tiempo: " + duracionNano + " nanosegundos\n" +
                    "        " + duracionMili + " milisegundos"
                );
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la boleta: " + boletaBuscada);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La boleta debe ser un número entero.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Ingrese un número de boleta para buscar.");
    }
    }//GEN-LAST:event_Bus_Boleta_3ActionPerformed

    private void Refresh_Boleta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refresh_Boleta1ActionPerformed
        // TODO add your handling code here:
        // Limpiar campos de búsqueda
    TextPlaca.setText("");
    TextBoleta1.setText("");

    long inicio = System.nanoTime();

    // Actualizar tabla con los datos actuales en la lista circular (memoria)
    llenarTablaConListaCircular();

    long fin = System.nanoTime();

    long duracionMs = (fin - inicio) / 1_000_000;
    JOptionPane.showMessageDialog(this, "Tabla actualizada.\nDuración: " + duracionMs + " ms");
    }//GEN-LAST:event_Refresh_Boleta1ActionPerformed

    private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsuarioActionPerformed

    private void Buscar_Fch_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar_Fch_3ActionPerformed
        // TODO add your handling code here:
     JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        setArchivoActual(file);
    }
    }//GEN-LAST:event_Buscar_Fch_3ActionPerformed

    private void Bt_TraspasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bt_TraspasoActionPerformed
        // TODO add your handling code here:
         JTextField campoPlaca = new JTextField(10);
    JTextField campoDpiAnt = new JTextField(10);
    JTextField campoNombreAnt = new JTextField(10);
    JTextField campoFecha = new JTextField(10);
    JTextField campoDpiNuevo = new JTextField(10);
    JTextField campoNombreNuevo = new JTextField(10);
    JTextField campoDepartamento = new JTextField(10);

    JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
    panel.add(new JLabel("PLACA:"));
    panel.add(campoPlaca);
    panel.add(new JLabel("DPI ANTERIOR:"));
    panel.add(campoDpiAnt);
    panel.add(new JLabel("NOMBRE ANTERIOR:"));
    panel.add(campoNombreAnt);
    panel.add(new JLabel("FECHA:"));
    panel.add(campoFecha);
    panel.add(new JLabel("DPI NUEVO:"));
    panel.add(campoDpiNuevo);
    panel.add(new JLabel("NOMBRE NUEVO:"));
    panel.add(campoNombreNuevo);
    panel.add(new JLabel("DEPARTAMENTO:"));
    panel.add(campoDepartamento);

    int resultado = JOptionPane.showConfirmDialog(this, panel, "Nuevo Traspaso",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (resultado == JOptionPane.OK_OPTION) {
        String placa = campoPlaca.getText().trim();
        String dpiAnt = campoDpiAnt.getText().trim();
        String nombreAnt = campoNombreAnt.getText().trim();
        String fecha = campoFecha.getText().trim();
        String dpiNuevo = campoDpiNuevo.getText().trim();
        String nombreNuevo = campoNombreNuevo.getText().trim();
        String departamento = campoDepartamento.getText().trim();

        if (placa.isEmpty() || dpiAnt.isEmpty() || nombreAnt.isEmpty() ||
            fecha.isEmpty() || dpiNuevo.isEmpty() || nombreNuevo.isEmpty() ||
            departamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos.");
            return;
        }

        // Comenzar a medir tiempo
        long inicio = System.nanoTime();

        // Obtener nuevo número de boleta
        int nuevaBoleta = 1;
        for (Traspaso t : listaCircular.obtenerTodos()) {
            if (t.getBoleta() >= nuevaBoleta) {
                nuevaBoleta = t.getBoleta() + 1;
            }
        }

        // Crear e insertar el nuevo Traspaso
        Traspaso nuevoTraspaso = new Traspaso(nuevaBoleta, placa, dpiAnt, nombreAnt, fecha, dpiNuevo, nombreNuevo, departamento);
        listaCircular.insertar(nuevoTraspaso);

        // Guardar cambios en archivo si deseas aquí
        // guardarCambiosEnArchivo();

        // Refrescar la tabla
        llenarTablaConListaCircular();

        // Finalizar tiempo
        long fin = System.nanoTime();
        long duracionNano = fin - inicio;
        long duracionMili = duracionNano / 1_000_000;

        JOptionPane.showMessageDialog(this,
            "Traspaso agregado exitosamente.\n" +
            "Tiempo: " + duracionNano + " nanosegundos\n" +
            "        " + duracionMili + " milisegundos"
        );
    }
    }//GEN-LAST:event_Bt_TraspasoActionPerformed

    private void Eliminar_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Eliminar_1ActionPerformed
        // TODO add your handling code here:
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de boleta a eliminar:");
    
    if (input != null && !input.trim().isEmpty()) {
        try {
            int boleta = Integer.parseInt(input.trim());

            // Iniciar cronómetro
            long inicio = System.nanoTime();

            boolean eliminado = listaCircular.eliminarPorBoleta(boleta);

            long fin = System.nanoTime(); // Fin cronómetro
            long duracionNano = fin - inicio;
            long duracionMili = duracionNano / 1_000_000;

            if (eliminado) {
                llenarTablaConListaCircular(); // Actualiza la JTable
                JOptionPane.showMessageDialog(this, 
                    "Registro eliminado correctamente.\n" +
                    "Tiempo: " + duracionNano + " nanosegundos\n" +
                    "        " + duracionMili + " milisegundos"
                );
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró una boleta con ese número.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número de boleta válido.");
        }
    }
    }//GEN-LAST:event_Eliminar_1ActionPerformed

    private void Editar_TraspasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Editar_TraspasoActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = Tabla_Dat_Traspaso.getSelectedRow();

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor selecciona un traspaso para editar.");
        return;
    }

    // Obtener datos actuales de la fila seleccionada
    int boleta = Integer.parseInt(Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 0).toString());
    String placa = Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 1).toString();
    String dpiAnt = Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 2).toString();
    String nombreAnt = Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 3).toString();
    String fecha = Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 4).toString();
    String dpiNuevo = Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 5).toString();
    String nombreNuevo = Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 6).toString();
    String departamento = Tabla_Dat_Traspaso.getValueAt(filaSeleccionada, 7).toString();

    // Crear campos de edición
    JTextField campoPlaca = new JTextField(placa, 10);
    JTextField campoDpiAnt = new JTextField(dpiAnt, 10);
    JTextField campoNombreAnt = new JTextField(nombreAnt, 10);
    JTextField campoFecha = new JTextField(fecha, 10);
    JTextField campoDpiNuevo = new JTextField(dpiNuevo, 10);
    JTextField campoNombreNuevo = new JTextField(nombreNuevo, 10);
    JTextField campoDepartamento = new JTextField(departamento, 10);

    // Crear panel del formulario
    JPanel panel = new JPanel(new GridLayout(0, 2, 10, 5));
    panel.add(new JLabel("PLACA:"));
    panel.add(campoPlaca);
    panel.add(new JLabel("DPI ANTERIOR:"));
    panel.add(campoDpiAnt);
    panel.add(new JLabel("NOMBRE ANTERIOR:"));
    panel.add(campoNombreAnt);
    panel.add(new JLabel("FECHA:"));
    panel.add(campoFecha);
    panel.add(new JLabel("DPI NUEVO:"));
    panel.add(campoDpiNuevo);
    panel.add(new JLabel("NOMBRE NUEVO:"));
    panel.add(campoNombreNuevo);
    panel.add(new JLabel("DEPARTAMENTO:"));
    panel.add(campoDepartamento);

    int resultado = JOptionPane.showConfirmDialog(this, panel, "Editar Traspaso",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (resultado == JOptionPane.OK_OPTION) {
        long inicioTiempo = System.nanoTime(); // Inicio tiempo

        // Crear el nuevo objeto actualizado
        Traspaso actualizado = new Traspaso(
            boleta,
            campoPlaca.getText().trim(),
            campoDpiAnt.getText().trim(),
            campoNombreAnt.getText().trim(),
            campoFecha.getText().trim(),
            campoDpiNuevo.getText().trim(),
            campoNombreNuevo.getText().trim(),
            campoDepartamento.getText().trim()
        );

        // Reemplazar el traspaso en la lista circular
        boolean editado = listaCircular.editarTraspasoPorBoleta(boleta, actualizado);

        if (editado) {
            guardarCambiosEnArchivo(); // Guarda el archivo actualizado
            llenarTablaConListaCircular(); // Refresca tabla

            long finTiempo = System.nanoTime(); // Fin tiempo
            long duracion = finTiempo - inicioTiempo;

            JOptionPane.showMessageDialog(this,
                "Traspaso editado exitosamente.\nTiempo: " + duracion + " nanosegundos");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el traspaso para editar.");
        }
    }
    }//GEN-LAST:event_Editar_TraspasoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bt_Traspaso;
    private javax.swing.JButton Bus_Boleta_2;
    private javax.swing.JButton Bus_Boleta_3;
    private javax.swing.JButton Bus_Placa_2;
    private javax.swing.JButton Bus_Placa_3;
    private javax.swing.JButton Buscar_Fch_2;
    private javax.swing.JButton Buscar_Fch_3;
    private javax.swing.JPanel Desplegable;
    private javax.swing.JPanel Desplegable1;
    private javax.swing.JButton Editar_Traspaso;
    private javax.swing.JButton Eliminar_1;
    private javax.swing.JButton Factura;
    private javax.swing.JButton Guardar;
    private javax.swing.JButton Home;
    private javax.swing.JButton Home1;
    private javax.swing.JButton Refresh_Boleta;
    private javax.swing.JButton Refresh_Boleta1;
    private javax.swing.JButton Refresh_Placa;
    private javax.swing.JButton Refresh_Placa1;
    private javax.swing.JTable Tabla_Dat_Traspaso;
    private javax.swing.JTextField TextBoleta;
    private javax.swing.JTextField TextBoleta1;
    private javax.swing.JTextField TextPlaca;
    private javax.swing.JTextField TextPlaca1;
    private javax.swing.JLabel Titulo;
    private javax.swing.JLabel Titulo1;
    private javax.swing.JLabel Titulo2;
    private javax.swing.JLabel Titulo3;
    private javax.swing.JLabel User_Indicator;
    private javax.swing.JButton Usuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void llenarTablaConLista(List<Traspaso> ordenados) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void llenarTablaConDatos(Object[][] datos) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

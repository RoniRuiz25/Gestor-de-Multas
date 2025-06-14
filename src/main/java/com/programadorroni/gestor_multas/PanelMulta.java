/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.programadorroni.gestor_multas;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class PanelMulta extends javax.swing.JPanel {

  private ListaDobleMulta listaMultas = new ListaDobleMulta();
    private File archivoActual;

    public PanelMulta() {
        initComponents();  
    }

    public void setArchivoActual(File archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        DefaultTableModel modelo = (DefaultTableModel) Tabla_Dat_Multa.getModel();
        modelo.setRowCount(0); // Limpia la tabla visual
        listaMultas.vaciar(); // Limpia lista en memoria (si tienes método vaciar)

        String linea;
        int contadorBoleta = 1; // BOLETA auto generada

        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(",");

            if (datos.length >= 6) {
                String placa = datos[0].trim();
                String fecha = datos[1].trim();
                String depto = datos[2].trim();
                String descripcion = datos[3].trim();
                double monto = Double.parseDouble(datos[4].trim());
                String estado = datos[5].trim();

                // Crea la multa con BOLETA autogenerada
                Multa nueva = new Multa(contadorBoleta, placa, fecha, depto, descripcion, monto, estado);
                listaMultas.insertarFinal(nueva); // Guardas en lista

                // Agrega a la tabla
                modelo.addRow(nueva.toArray());

                contadorBoleta++;
            }
        }

        JOptionPane.showMessageDialog(null, "Archivo cargado correctamente.");
    } catch (IOException | NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Error al leer el archivo:\n" + e.getMessage());
    }
    }
      
    private void guardarCambiosEnArchivo() {
      String archivo = null;
   try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
        Nodo actual = listaMultas.getCabeza();
        while (actual != null) {
            Multa m = actual.getDato();
            String linea = m.getPlaca() + "," +
                           m.getFecha() + "," +
                           m.getDepartamento() + "," +
                           m.getDescripcion() + "," +
                           m.getMonto() + "," +
                           m.getEstado();  // Asegúrate de que esto sea el nuevo estado

            writer.write(linea);
            writer.newLine();
            actual = actual.getSiguiente();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage());
    }
}
       
    private void cargarDesdeArchivoPanelMulta(File archivo) {
    listaMultas.limpiar(); // Borra la lista enlazada antes de cargar

    int contadorID = 1; // ID autogenerado para la columna BOLETA
    int lineasMalFormateadas = 0;

    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(",");
            if (datos.length == 6) {
                try {
                    double monto = Double.parseDouble(datos[4].trim());
                    Multa m = new Multa(
                        contadorID++,           // BOLETA generado automáticamente
                        datos[0].trim(),        // PLACA
                        datos[1].trim(),        // FECHA
                        datos[2].trim(),        // DEPARTAMENTO
                        datos[3].trim(),        // DESCRIPCION
                        monto,                  // MONTO
                        datos[5].trim()         // ESTADO
                    );
                    listaMultas.insertarOrdenado(m);
                } catch (NumberFormatException e) {
                    lineasMalFormateadas++;
                }
            } else {
                lineasMalFormateadas++;
            }
        }

        // Refresca la tabla con los datos cargados
        mostrarEnTablaPanelMulta(listaMultas.obtenerDatos());

        if (lineasMalFormateadas > 0) {
            JOptionPane.showMessageDialog(this, 
                "Carga completada con advertencias.\n" + 
                "Líneas ignoradas por formato incorrecto: " + lineasMalFormateadas,
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Archivo cargado correctamente.");
        }

    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error al leer el archivo:\n" + e.getMessage());
    }
}
    
    private void mostrarEnTablaPanelMulta(Object[][] datos) {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{"BOLETA", "Placa", "Fecha", "Departamento", "Descripción", "Monto", "Estado"});

    for (Object[] fila : datos) {
        modelo.addRow(fila);
    }

    Tabla_Dat_Multa.setModel(modelo);
}
    
        public void cargarDesdeArchivo(File archivo) {
        DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[] {
        "BOLETA", "PLACA", "FECHA", "DEPARTAMENTO", "DESCRIPCIÓN", "MONTO", "ESTADO"
    });

    int contadorID = 1;

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");

            if (datos.length == 6) {
                modelo.addRow(new Object[]{
                    contadorID++, 
                    datos[0].trim(), 
                    datos[1].trim(), 
                    datos[2].trim(), 
                    datos[3].trim(), 
                    Double.parseDouble(datos[4].trim()), 
                    datos[5].trim()
                });
            }
        }

        Tabla_Dat_Multa.setModel(modelo);
        JOptionPane.showMessageDialog(this, "Archivo cargado correctamente.");
    } catch (IOException | NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar archivo: " + e.getMessage());
    }
        }

    private void mostrarEnTabla(Object[][] datos) {
         DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"BOLETA", "Placa", "Fecha", "Departamento", "Descripción", "Monto", "Estado"});
        for (Object[] fila : datos) {
            modelo.addRow(fila);
        }
        Tabla_Dat_Multa.setModel(modelo);
    }

    private void actualizarTabla(JTable tabla) {
    Object[][] datos = listaMultas.obtenerDatos();
    String[] columnas = {"BOLETA", "PLACA", "FECHA", "DEPARTAMENTO", "DESCRIPCIÓN", "MONTO", "ESTADO"};
    tabla.setModel(new DefaultTableModel(datos, columnas));
}
    public void cargarArchivoConBoleta(File archivo) {
      listaMultas.limpiar();

    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
        String linea;
        int id = 1;
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length == 6) {
                Multa multa = new Multa(id++, partes[0], partes[1], partes[2], partes[3],
                                        Double.parseDouble(partes[4]), partes[5]);
                listaMultas.insertarOrdenado(multa);
            }
        }
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }

    actualizarTabla(Tabla_Dat_Multa);
}
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        Usuario = new javax.swing.JButton();
        User_Indicator = new javax.swing.JLabel();
        Desplegable = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Home = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Titulo1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        TextPlaca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Bus_Placa_2 = new javax.swing.JButton();
        TextBoleta = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        Bus_Boleta_2 = new javax.swing.JButton();
        Refresh_Placa = new javax.swing.JButton();
        Refresh_Boleta = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        Buscar_Fch_2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        L_Multas = new javax.swing.JLabel();
        L_Total_P = new javax.swing.JLabel();
        L_Pagadas = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        Emitir_Pago = new javax.swing.JButton();
        Factura = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Dat_Multa = new javax.swing.JTable();
        Guardar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(17, 34, 61));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        Usuario.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-usuario-16.png")); // NOI18N
        Usuario.setBorder(null);
        Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioActionPerformed(evt);
            }
        });

        User_Indicator.setText("Roni Ruiz");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(1002, Short.MAX_VALUE)
                .addComponent(User_Indicator, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Usuario)
                .addGap(22, 22, 22))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(User_Indicator)))
                .addContainerGap())
        );

        Desplegable.setBackground(new java.awt.Color(31, 49, 73));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Gestor de Multas");

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1252, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(18, 38, 70));

        Titulo1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        Titulo1.setForeground(new java.awt.Color(255, 255, 255));
        Titulo1.setText("Pago de Multas");

        jButton1.setBackground(new java.awt.Color(18, 38, 70));
        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-pago-48.png")); // NOI18N
        jButton1.setBorder(null);

        TextPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextPlacaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Placa");

        Bus_Placa_2.setBackground(new java.awt.Color(18, 38, 70));
        Bus_Placa_2.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-buscar-244.png")); // NOI18N
        Bus_Placa_2.setBorder(null);
        Bus_Placa_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Bus_Placa_2ActionPerformed(evt);
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

        Refresh_Placa.setBackground(new java.awt.Color(18, 38, 70));
        Refresh_Placa.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-recargar-16.png")); // NOI18N
        Refresh_Placa.setBorder(null);
        Refresh_Placa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Refresh_PlacaActionPerformed(evt);
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

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Agregar Fichero Existente");

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
                                .addComponent(Titulo1))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TextPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(Bus_Placa_2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Refresh_Placa))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(Bus_Boleta_2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Refresh_Boleta))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Buscar_Fch_2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(109, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Titulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Refresh_Placa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(TextPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Bus_Placa_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TextBoleta, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(Bus_Boleta_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(Refresh_Boleta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(Buscar_Fch_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4.setBackground(new java.awt.Color(18, 38, 70));

        Titulo.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo.setText("Resumen");

        jPanel6.setBackground(new java.awt.Color(18, 38, 70));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Multas:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total a Pagar:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Pagadas:");
        jLabel6.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DETALLE");

        L_Multas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        L_Multas.setForeground(new java.awt.Color(255, 255, 255));

        L_Total_P.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        L_Total_P.setForeground(new java.awt.Color(255, 255, 255));

        L_Pagadas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        L_Pagadas.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(L_Pagadas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(L_Multas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(L_Total_P, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(L_Multas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(L_Pagadas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(L_Total_P, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        Emitir_Pago.setBackground(new java.awt.Color(242, 242, 242));
        Emitir_Pago.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-card-payment-30.png")); // NOI18N
        Emitir_Pago.setBorder(null);
        Emitir_Pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Emitir_PagoActionPerformed(evt);
            }
        });

        Factura.setBackground(new java.awt.Color(242, 242, 242));
        Factura.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\factura.png")); // NOI18N
        Factura.setToolTipText("");
        Factura.setBorder(null);
        Factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FacturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Emitir_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Factura)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Factura, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(Emitir_Pago, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\facturación-1.png")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(214, 214, 214))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Tabla_Dat_Multa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "BOLETA", "PLACA", "FECHA", "DEPARTAMENTO", "DESCRIPCION", "MONTO", "ESTADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla_Dat_Multa.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Tabla_Dat_Multa);
        if (Tabla_Dat_Multa.getColumnModel().getColumnCount() > 0) {
            Tabla_Dat_Multa.getColumnModel().getColumn(0).setResizable(false);
            Tabla_Dat_Multa.getColumnModel().getColumn(1).setResizable(false);
            Tabla_Dat_Multa.getColumnModel().getColumn(2).setResizable(false);
            Tabla_Dat_Multa.getColumnModel().getColumn(3).setResizable(false);
            Tabla_Dat_Multa.getColumnModel().getColumn(4).setResizable(false);
            Tabla_Dat_Multa.getColumnModel().getColumn(5).setResizable(false);
            Tabla_Dat_Multa.getColumnModel().getColumn(6).setResizable(false);
        }

        Guardar.setBackground(new java.awt.Color(242, 242, 242));
        Guardar.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-guardar-16.png")); // NOI18N
        Guardar.setText("GUARDAR");
        Guardar.setBorder(null);
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setText("DATOS GENERALES DE MULTAS");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Desplegable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Desplegable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsuarioActionPerformed

    private void Bus_Placa_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bus_Placa_2ActionPerformed
    String placa = TextPlaca.getText().trim();

    if (!placa.isEmpty()) {
        // Inicio de la medición
        long startMillis = System.currentTimeMillis();
        long startNano = System.nanoTime();

        Object[][] resultados = listaMultas.buscarMultasPorPlaca(placa);

        // Fin de la medición
        long endMillis = System.currentTimeMillis();
        long endNano = System.nanoTime();

        long elapsedMillis = endMillis - startMillis;
        long elapsedNano = endNano - startNano;

        if (resultados.length == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron registros con esa placa.");
        } else {
            mostrarEnTabla(resultados); // Este método debería cargar los datos en la JTable

            // Contadores de estados y total de pendientes
            int pendientes = 0;
            int pagadas = 0;
            double totalPendientes = 0.0;

            for (Object[] fila : resultados) {
                if (fila.length >= 7) {
                    String estado = fila[6].toString().trim().toUpperCase();
                    if (estado.equals("PENDIENTE")) {
                        pendientes++;
                        try {
                            // Sumar el monto si es válido
                            double monto = Double.parseDouble(fila[5].toString());
                            totalPendientes += monto;
                        } catch (NumberFormatException e) {
                            System.err.println("Error al convertir monto: " + fila[5]);
                        }
                    } else if (estado.equals("PAGADO")) {
                        pagadas++;
                    }
                }
            }

            // Actualizar los JLabels con los valores contados
            L_Multas.setText(String.valueOf(pendientes));
            L_Pagadas.setText(String.valueOf(pagadas));

            // Mostrar el total de montos pendientes en el JLabel
            L_Total_P.setText("Q. " + String.format("%.2f", totalPendientes));
        }

        // Mostrar tiempo transcurrido en un mensaje (opcional)
        JOptionPane.showMessageDialog(this,
            "Tiempo de búsqueda:\n" +
            elapsedMillis + " milisegundos\n" +
            elapsedNano + " nanosegundos"
        );

    } else {
        JOptionPane.showMessageDialog(this, "Ingrese una placa para buscar.");
    }
    }//GEN-LAST:event_Bus_Placa_2ActionPerformed

    private void Bus_Boleta_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bus_Boleta_2ActionPerformed
        // TODO add your handling code here:
        String textoBoleta = TextBoleta.getText().trim();

    if (!textoBoleta.isEmpty()) {
        try {
            int boleta = Integer.parseInt(textoBoleta);

            // Inicio medición
            long startMillis = System.currentTimeMillis();
            long startNano = System.nanoTime();

            Multa resultado = listaMultas.buscarPorBoleta(boleta);

            // Fin medición
            long endMillis = System.currentTimeMillis();
            long endNano = System.nanoTime();

            long elapsedMillis = endMillis - startMillis;
            long elapsedNano = endNano - startNano;

            if (resultado == null) {
                JOptionPane.showMessageDialog(this, "No se encontraron registros con esa boleta.");
            } else {
                mostrarEnTabla(new Object[][]{resultado.toArray()});
            }

            // Mostrar tiempo transcurrido en un mensaje (opcional)
            JOptionPane.showMessageDialog(this,
                "Tiempo de búsqueda:\n" +
                elapsedMillis + " milisegundos\n" +
                elapsedNano + " nanosegundos"
            );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La boleta debe ser un número entero.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Ingrese un número de boleta para buscar");
    }
    }//GEN-LAST:event_Bus_Boleta_2ActionPerformed

    private void TextPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextPlacaActionPerformed

    private void TextBoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextBoletaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextBoletaActionPerformed

    private void Refresh_PlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refresh_PlacaActionPerformed
        // TODO add your handling code here:
        TextPlaca.setText("");
    TextBoleta.setText("");

    long inicio = System.nanoTime();
    actualizarTabla(Tabla_Dat_Multa);  // usa los datos en memoria, no el archivo
    long fin = System.nanoTime();

    long duracionMs = (fin - inicio) / 1_000_000;
    JOptionPane.showMessageDialog(this, "Tabla actualizada.\nDuración: " + duracionMs + " ms");
    }//GEN-LAST:event_Refresh_PlacaActionPerformed

    private void Refresh_BoletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refresh_BoletaActionPerformed
        TextBoleta.setText("");             // Limpia campo de búsqueda por boleta
    TextPlaca.setText("");              // Limpia campo de búsqueda por placa

    long inicio = System.nanoTime();    // Tiempo inicial
    actualizarTabla(Tabla_Dat_Multa);   // Recarga desde los datos en memoria
    long fin = System.nanoTime();       // Tiempo final

    long duracionMs = (fin - inicio) / 1_000_000;
    JOptionPane.showMessageDialog(this, "Tabla recargada.\nDuración: " + duracionMs + " ms");
    }//GEN-LAST:event_Refresh_BoletaActionPerformed

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        // TODO add your handling code here:
      JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar archivo de multas");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));

    int userSelection = fileChooser.showSaveDialog(this);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();

        // Asegura que tenga la extensión .txt
        if (!archivo.getName().toLowerCase().endsWith(".txt")) {
            archivo = new File(archivo.getAbsolutePath() + ".txt");
        }

        long inicio = System.nanoTime(); // Tiempo inicial

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            DefaultTableModel modelo = (DefaultTableModel) Tabla_Dat_Multa.getModel();
            int filas = modelo.getRowCount();
            int columnas = modelo.getColumnCount();

            for (int i = 0; i < filas; i++) {
                StringBuilder linea = new StringBuilder();
                for (int j = 1; j < columnas; j++) { // Saltamos la columna BOLETA (índice 0)
                    linea.append(modelo.getValueAt(i, j).toString());
                    if (j < columnas - 1) {
                        linea.append(",");
                    }
                }
                writer.println(linea.toString());
            }

            long fin = System.nanoTime(); // Tiempo final
            long duracion = fin - inicio;
            long duracionMs = duracion / 1_000_000; // Convertir a milisegundos

            JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente en:\n" + archivo.getAbsolutePath()
                    + "\nTiempo de guardado: " + duracionMs + " milisegundos.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_GuardarActionPerformed

    private void Emitir_PagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Emitir_PagoActionPerformed
        // TODO add your handling code here:
         DefaultTableModel modelo = (DefaultTableModel) Tabla_Dat_Multa.getModel();
    int filaSeleccionada = Tabla_Dat_Multa.getSelectedRow();

    if (filaSeleccionada >= 0) {
        long startMillis = System.currentTimeMillis();
        long startNano = System.nanoTime();

        // Actualiza el modelo visual
        modelo.setValueAt("PAGADO", filaSeleccionada, 6);

        // Obtener número de boleta
        int boleta = Integer.parseInt(modelo.getValueAt(filaSeleccionada, 0).toString());

        // Actualiza el estado en la lista enlazada
        Multa multa = listaMultas.buscarPorBoleta(boleta);
        if (multa != null) {
            multa.setEstado("PAGADO");
        }

        // Sobrescribe el archivo con los datos actualizados
        guardarCambiosEnArchivo();

        long endMillis = System.currentTimeMillis();
        long endNano = System.nanoTime();

        long elapsedMillis = endMillis - startMillis;
        long elapsedNano = endNano - startNano;

        JOptionPane.showMessageDialog(this,
            "Estado cambiado a PAGADO.\n" +
            "Tiempo del proceso:\n" +
            elapsedMillis + " milisegundos\n" +
            elapsedNano + " nanosegundos"
        );
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione una fila para emitir el pago.");
    }
    }//GEN-LAST:event_Emitir_PagoActionPerformed

    private void FacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FacturaActionPerformed
        // TODO add your handling code here:
             String input = JOptionPane.showInputDialog(this, "Ingrese el número de BOLETA para generar la factura:");

    if (input != null && !input.trim().isEmpty()) {
        try {
            int numBoleta = Integer.parseInt(input.trim());

            long startMillis = System.currentTimeMillis();
            long startNano = System.nanoTime();

            DefaultTableModel modelo = (DefaultTableModel) Tabla_Dat_Multa.getModel();
            boolean encontrada = false;

            for (int i = 0; i < modelo.getRowCount(); i++) {
                int boletaFila = Integer.parseInt(modelo.getValueAt(i, 0).toString());
                if (boletaFila == numBoleta) {
                    encontrada = true;
                    String estado = modelo.getValueAt(i, 6).toString().trim().toUpperCase();

                    if (estado.equals("PENDIENTE")) {
                        JOptionPane.showMessageDialog(this,
                            "La multa con boleta #" + numBoleta + " está pendiente de pago.\n" +
                            "Por favor, realice el pago para generar la factura.",
                            "Multa Pendiente",
                            JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Si no está pendiente, mostrar factura
                        String placa = modelo.getValueAt(i, 1).toString();
                        String fecha = modelo.getValueAt(i, 2).toString();
                        String depto = modelo.getValueAt(i, 3).toString();
                        String desc = modelo.getValueAt(i, 4).toString();
                        String monto = modelo.getValueAt(i, 5).toString();

                        String factura = 
                            "=============================\n" +
                            "         FACTURA DE PAGO      \n" +
                            "=============================\n" +
                            "Boleta: " + numBoleta + "\n" +
                            "Placa: " + placa + "\n" +
                            "Fecha: " + fecha + "\n" +
                            "Departamento: " + depto + "\n" +
                            "Descripción: " + desc + "\n" +
                            "Monto: Q" + monto + "\n" +
                            "-----------------------------\n" +
                            "Estado: " + estado + "\n" +
                            "-----------------------------\n" +
                            "Gracias por su atención.\n" +
                            "=============================";

                        JOptionPane.showMessageDialog(this, factura, "Factura", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;
                }
            }

            long endMillis = System.currentTimeMillis();
            long endNano = System.nanoTime();

            long elapsedMillis = endMillis - startMillis;
            long elapsedNano = endNano - startNano;

            if (!encontrada) {
                JOptionPane.showMessageDialog(this, "No se encontró la boleta #" + numBoleta);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Tiempo total de proceso:\n" +
                    elapsedMillis + " milisegundos\n" +
                    elapsedNano + " nanosegundos",
                    "Tiempo de ejecución",
                    JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de boleta inválido.");
        }
    }
    }//GEN-LAST:event_FacturaActionPerformed

    private void HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HomeActionPerformed
        // TODO add your handling code here:
        java.awt.Container parent = this.getParent();

    // Reemplazar el contenido del contenedor con el PanelPrincipal
    if (parent instanceof javax.swing.JPanel) {
        parent.removeAll();
        parent.add(new PanelPrincipal()); // Asegúrate de importar la clase si está en otro paquete
        parent.revalidate();
        parent.repaint();
    }
    }//GEN-LAST:event_HomeActionPerformed

    private void Buscar_Fch_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar_Fch_2ActionPerformed
        // TODO add your handling code here:
       JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de texto", "txt"));
    int resultado = fileChooser.showOpenDialog(this);

    if (resultado == JFileChooser.APPROVE_OPTION) {
        File archivoActual = fileChooser.getSelectedFile();
        cargarDesdeArchivoPanelMulta(archivoActual);  // Versión adaptada
    }
    }//GEN-LAST:event_Buscar_Fch_2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bus_Boleta_2;
    private javax.swing.JButton Bus_Placa_2;
    private javax.swing.JButton Buscar_Fch_2;
    private javax.swing.JPanel Desplegable;
    private javax.swing.JButton Emitir_Pago;
    private javax.swing.JButton Factura;
    private javax.swing.JButton Guardar;
    private javax.swing.JButton Home;
    private javax.swing.JLabel L_Multas;
    private javax.swing.JLabel L_Pagadas;
    private javax.swing.JLabel L_Total_P;
    private javax.swing.JButton Refresh_Boleta;
    private javax.swing.JButton Refresh_Placa;
    private javax.swing.JTable Tabla_Dat_Multa;
    private javax.swing.JTextField TextBoleta;
    private javax.swing.JTextField TextPlaca;
    private javax.swing.JLabel Titulo;
    private javax.swing.JLabel Titulo1;
    private javax.swing.JLabel User_Indicator;
    private javax.swing.JButton Usuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}

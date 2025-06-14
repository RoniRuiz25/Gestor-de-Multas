/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.programadorroni.gestor_multas;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author isaia
 */
public class PanelVehiculos extends javax.swing.JPanel {

    private Arbol arbolSeleccionado;
    private File archivoActual;
    private ArbolAVL ArbolAVL = new ArbolAVL();
    private ArbolABB ArbolABB = new ArbolABB();
    
    /**
     * Creates new form PanelVehiculos
     */
    public PanelVehiculos() {
        initComponents();
        
    }
private void setArchivoActual(File file) {
    this.archivoActual = file;
}

private void mostrarImagenConZoom(String rutaImagen) {
    JFrame ventana = new JFrame("Árbol en Alta Resolución");
    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    ventana.setSize(1000, 700);

    ImageIcon originalIcon = new ImageIcon(rutaImagen);
    JLabel label = new JLabel(originalIcon);
    JScrollPane scrollPane = new JScrollPane(label);

    ventana.add(scrollPane);
    ventana.setLocationRelativeTo(null);
    ventana.setVisible(true);

    scrollPane.addMouseWheelListener(new MouseWheelListener() {
        double zoom = 1.0;

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int notches = e.getWheelRotation();
            if (notches < 0) zoom *= 1.1;
            else zoom /= 1.1;

            ImageIcon zoomedIcon = new ImageIcon(
                new ImageIcon(rutaImagen).getImage().getScaledInstance(
                    (int) (originalIcon.getIconWidth() * zoom),
                    (int) (originalIcon.getIconHeight() * zoom),
                    Image.SCALE_SMOOTH
                )
            );
            label.setIcon(zoomedIcon);
            label.revalidate();
        }
    });
}

private void actualizarTotalMultasPorDepartamento() {
    DefaultTableModel model = (DefaultTableModel) Tabla_Dat_Traspaso.getModel();

    int indiceDepartamento = 8;  // Ajusta según tu tabla
    int indiceMultas = 6;        // Ajusta según tu tabla

    int totalMultasPeten = 0;
    int totalMultasQuetzaltenango = 0;
    int totalMultasSanMarcos = 0;
    int totalMultasSuchitequez = 0;
    int totalMultasHuehuetenango = 0;
    int totalMultasGuatemala = 0;
    int totalMultasAntiguaGuatemala = 0;
    int totalMultasChimaltenango = 0;
    int totalMultasChiquimula = 0;
    int totalMultasEscuintla = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        Object deptObj = model.getValueAt(i, indiceDepartamento);
        Object multasObj = model.getValueAt(i, indiceMultas);

        if (deptObj != null && multasObj != null) {
            String departamento = deptObj.toString().trim();
            String multasStr = multasObj.toString().trim();

            int multas = 0;
            try {
                multas = Integer.parseInt(multasStr);
            } catch (NumberFormatException e) {
                System.err.println("Valor de multas inválido en fila " + i + ": " + multasStr);
                continue; // Ignora esta fila si no se puede parsear
            }

            if (departamento.equalsIgnoreCase("Peten")) {
                totalMultasPeten += multas;
            } else if (departamento.equalsIgnoreCase("Quetzaltenango")) {
                totalMultasQuetzaltenango += multas;
            } else if (departamento.equalsIgnoreCase("San Marcos")) {
                totalMultasSanMarcos += multas;
            } else if (departamento.equalsIgnoreCase("Suchitequez") || departamento.equalsIgnoreCase("Suchitepequez")) {
                totalMultasSuchitequez += multas;
            } else if (departamento.equalsIgnoreCase("Huehuetenango")) {
                totalMultasHuehuetenango += multas;
            } else if (departamento.equalsIgnoreCase("Guatemala")) {
                totalMultasGuatemala += multas;
            } else if (departamento.equalsIgnoreCase("Antigua Guatemala")) {
                totalMultasAntiguaGuatemala += multas;
            } else if (departamento.equalsIgnoreCase("Chimaltenango")) {
                totalMultasChimaltenango += multas;
            } else if (departamento.equalsIgnoreCase("Chiquimula")) {
                totalMultasChiquimula += multas;
            } else if (departamento.equalsIgnoreCase("Escuintla")) {
                totalMultasEscuintla += multas;
            }
        }
    }

    // Actualiza los JLabel con los totales
    Peten.setText("" + totalMultasPeten);
    Quetzaltenango.setText("" + totalMultasQuetzaltenango);
    San_Marcos.setText("" + totalMultasSanMarcos);
    Suchitepequez.setText("" + totalMultasSuchitequez);
    Huehuetenango.setText("" + totalMultasHuehuetenango);
    Guatemala.setText("" + totalMultasGuatemala);
    Antigua_Guatemala.setText("" + totalMultasAntiguaGuatemala);
    Chimaltenango.setText("" + totalMultasChimaltenango);
    Chiquimula.setText("" + totalMultasChiquimula);
    Escuintla.setText("" + totalMultasEscuintla);
}

private void guardarArbolEnArchivo(String rutaArchivo, List<Vehiculo> listaVehiculos) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
        for (Vehiculo v : listaVehiculos) {
            String linea = String.join(",",
                    v.getPlaca(), v.getDpi(), v.getNombre(), v.getDepartamento(),
                    v.getMarca(), v.getModelo(), v.getAño(), v.getMultas(), v.getTraspasos()
            );
            bw.write(linea);
            bw.newLine();
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + e.getMessage());
    }
}


private void actualizarTablaDesdeLista(List<Vehiculo> lista) {
    String[] columnas = {"PLACA", "DPI", "NOMBRE", "MARCA", "MODELO", "AÑO", "MULTAS", "TRASPASOS", "DEPARTAMENTO"};
    DefaultTableModel modelo = new DefaultTableModel(null, columnas);

    for (Vehiculo v : lista) {
        modelo.addRow(v.toRow());  // Asegúrate de que toRow() incluya DEPARTAMENTO
    }

    Tabla_Dat_Traspaso.setModel(modelo);
    Tabla_Dat_Traspaso.revalidate();
    Tabla_Dat_Traspaso.repaint();
    
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Titulo2 = new javax.swing.JLabel();
        Suchitepequez = new javax.swing.JLabel();
        San_Marcos = new javax.swing.JLabel();
        Quetzaltenango = new javax.swing.JLabel();
        Peten = new javax.swing.JLabel();
        Huehuetenango = new javax.swing.JLabel();
        Guatemala = new javax.swing.JLabel();
        Escuintla = new javax.swing.JLabel();
        Chiquimula = new javax.swing.JLabel();
        Chimaltenango = new javax.swing.JLabel();
        Antigua_Guatemala = new javax.swing.JLabel();
        Desplegable1 = new javax.swing.JPanel();
        Home1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Incriptar = new javax.swing.JButton();
        DES_INCRIPTAR = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        Usuario = new javax.swing.JButton();
        User_Indicator = new javax.swing.JLabel();
        Barra = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Dat_Traspaso = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        Guardar = new javax.swing.JButton();
        EscribirBus = new javax.swing.JTextField();
        Buscar_Placa_1 = new javax.swing.JButton();
        Refresh_Placa = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        TextPlaca = new javax.swing.JTextField();
        Bus_Placa_2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Titulo3 = new javax.swing.JLabel();
        Buscar_Fch_2 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        PosOrden = new javax.swing.JButton();
        InOrden = new javax.swing.JButton();
        PreOrden = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        Asignar = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        BOTON_AVL = new javax.swing.JRadioButton();
        BOTON_ABB = new javax.swing.JRadioButton();
        jLabel25 = new javax.swing.JLabel();
        Eliminar_1 = new javax.swing.JButton();

        jLabel10.setText("Tiempo de Carga");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(17, 34, 61));

        jPanel2.setBackground(new java.awt.Color(18, 38, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\MAPA_3.png")); // NOI18N

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Resumen Multas");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Suchitepequez");

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("San Marcos");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Quetzaltenango");

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Huehuetenango");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Peten");

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Guatemala");

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Escuintla");

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Chiquimula");

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Chimaltenango");

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Antigua Guatemala");

        Titulo2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        Titulo2.setForeground(new java.awt.Color(255, 255, 255));
        Titulo2.setText("Gestion Geografica");

        Suchitepequez.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Suchitepequez.setForeground(new java.awt.Color(255, 255, 255));
        Suchitepequez.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        San_Marcos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        San_Marcos.setForeground(new java.awt.Color(255, 255, 255));
        San_Marcos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Quetzaltenango.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Quetzaltenango.setForeground(new java.awt.Color(255, 255, 255));
        Quetzaltenango.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Peten.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Peten.setForeground(new java.awt.Color(255, 255, 255));
        Peten.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Huehuetenango.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Huehuetenango.setForeground(new java.awt.Color(255, 255, 255));
        Huehuetenango.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Guatemala.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Guatemala.setForeground(new java.awt.Color(255, 255, 255));
        Guatemala.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Escuintla.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Escuintla.setForeground(new java.awt.Color(255, 255, 255));
        Escuintla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Chiquimula.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Chiquimula.setForeground(new java.awt.Color(255, 255, 255));
        Chiquimula.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Chimaltenango.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Chimaltenango.setForeground(new java.awt.Color(255, 255, 255));
        Chimaltenango.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Antigua_Guatemala.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Antigua_Guatemala.setForeground(new java.awt.Color(255, 255, 255));
        Antigua_Guatemala.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(Titulo2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(San_Marcos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Quetzaltenango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Peten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Huehuetenango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Guatemala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Escuintla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Chiquimula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Chimaltenango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Suchitepequez, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Antigua_Guatemala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Suchitepequez, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(San_Marcos, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Quetzaltenango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Peten, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Huehuetenango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Guatemala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Escuintla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Chiquimula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Chimaltenango, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(Antigua_Guatemala, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(Titulo2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
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
        jLabel2.setText("LOGOTIPO");

        jButton1.setText("Graphviz");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Incriptar.setText("Incriptar");
        Incriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IncriptarActionPerformed(evt);
            }
        });

        DES_INCRIPTAR.setText("Des Incriptar");
        DES_INCRIPTAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DES_INCRIPTARActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Desplegable1Layout = new javax.swing.GroupLayout(Desplegable1);
        Desplegable1.setLayout(Desplegable1Layout);
        Desplegable1Layout.setHorizontalGroup(
            Desplegable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Desplegable1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(Desplegable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Desplegable1Layout.createSequentialGroup()
                        .addComponent(Home1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Desplegable1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(Desplegable1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Desplegable1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DES_INCRIPTAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Incriptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(Incriptar)
                .addGap(18, 18, 18)
                .addComponent(DES_INCRIPTAR)
                .addContainerGap(560, Short.MAX_VALUE))
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

        Barra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Barra.setText("=");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Barra, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(User_Indicator, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Usuario)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Usuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(User_Indicator)
                        .addComponent(Barra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        Tabla_Dat_Traspaso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PLACA", "DPI", "NOMBRE", "MARCA", "MODELO", "AÑO", "MULTAS", "TRASPASOS", "DEPARTAMENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla_Dat_Traspaso.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(Tabla_Dat_Traspaso);

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setText("DATOS GENERALES DE VEHICULOS");

        Guardar.setBackground(new java.awt.Color(242, 242, 242));
        Guardar.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-guardar-16.png")); // NOI18N
        Guardar.setBorder(null);
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });

        EscribirBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EscribirBusActionPerformed(evt);
            }
        });

        Buscar_Placa_1.setBackground(new java.awt.Color(242, 242, 242));
        Buscar_Placa_1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-buscar-24.png")); // NOI18N
        Buscar_Placa_1.setBorder(null);
        Buscar_Placa_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar_Placa_1ActionPerformed(evt);
            }
        });

        Refresh_Placa.setBackground(new java.awt.Color(242, 242, 242));
        Refresh_Placa.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-recargar-16.png")); // NOI18N
        Refresh_Placa.setBorder(null);
        Refresh_Placa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Refresh_PlacaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(Buscar_Placa_1)
                        .addGap(8, 8, 8)
                        .addComponent(Refresh_Placa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EscribirBus, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Guardar)
                        .addGap(15, 15, 15))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EscribirBus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addComponent(Buscar_Placa_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Refresh_Placa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(18, 38, 70));

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

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Placa");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));

        Titulo3.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        Titulo3.setForeground(new java.awt.Color(255, 255, 255));
        Titulo3.setText("Registro Vehicular");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(TextPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Bus_Placa_2))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(Titulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(235, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addContainerGap(236, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titulo3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Bus_Placa_2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(212, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addContainerGap(211, Short.MAX_VALUE)))
        );

        Buscar_Fch_2.setBackground(new java.awt.Color(17, 34, 61));
        Buscar_Fch_2.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-folder-48.png")); // NOI18N
        Buscar_Fch_2.setBorder(null);
        Buscar_Fch_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar_Fch_2ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Agregar Fichero Existente");

        PosOrden.setText("Pos Orden");
        PosOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PosOrdenActionPerformed(evt);
            }
        });

        InOrden.setText("In Orden");
        InOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InOrdenActionPerformed(evt);
            }
        });

        PreOrden.setText("Pre Orden");
        PreOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreOrdenActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Tipos de Ordenamientos");

        Asignar.setText("Asignar Nuevo Vehiculo");
        Asignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AsignarActionPerformed(evt);
            }
        });

        Edit.setText("Editar Vehiculo");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        BOTON_AVL.setBackground(new java.awt.Color(17, 34, 61));
        BOTON_AVL.setForeground(new java.awt.Color(255, 255, 255));
        BOTON_AVL.setText("AVL");
        BOTON_AVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOTON_AVLActionPerformed(evt);
            }
        });

        BOTON_ABB.setBackground(new java.awt.Color(17, 34, 61));
        BOTON_ABB.setForeground(new java.awt.Color(255, 255, 255));
        BOTON_ABB.setText("ABB");
        BOTON_ABB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOTON_ABBActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Gestion Vehicular");

        Eliminar_1.setBackground(new java.awt.Color(17, 34, 61));
        Eliminar_1.setIcon(new javax.swing.ImageIcon("C:\\Users\\isaia\\Documents\\NetBeansProjects\\Gestor_Multas\\Iconos\\icons8-eliminar-24.png")); // NOI18N
        Eliminar_1.setBorder(null);
        Eliminar_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Eliminar_1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Desplegable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(Buscar_Fch_2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(BOTON_AVL)
                                .addGap(18, 18, 18)
                                .addComponent(BOTON_ABB)
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(PreOrden)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(PosOrden)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(InOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(73, 73, 73)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(Asignar)
                                        .addGap(18, 18, 18)
                                        .addComponent(Edit)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Eliminar_1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Desplegable1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel18)
                                                .addComponent(BOTON_AVL)
                                                .addComponent(BOTON_ABB))
                                            .addComponent(Buscar_Fch_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel24))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(PreOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(PosOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(InOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Asignar, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(Eliminar_1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 788, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

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

    private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsuarioActionPerformed

    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        // TODO add your handling code here:
      if (!BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(null, "Seleccione un árbol (AVL o ABB).");
        return;
    }

    // Elegir ubicación para guardar
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar archivo de vehículos");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));

    int userSelection = fileChooser.showSaveDialog(this);

    if (userSelection != JFileChooser.APPROVE_OPTION) {
        return; // Usuario canceló
    }

    File archivoGuardar = fileChooser.getSelectedFile();

    // Asegurar extensión .txt
    if (!archivoGuardar.getAbsolutePath().endsWith(".txt")) {
        archivoGuardar = new File(archivoGuardar.getAbsolutePath() + ".txt");
    }

    long inicio = System.nanoTime(); // Tiempo de inicio

    List<Vehiculo> listaVehiculos = BOTON_AVL.isSelected()
            ? ArbolAVL.inOrden()
            : ArbolABB.inOrden();

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoGuardar))) {
        for (Vehiculo v : listaVehiculos) {
            String linea = CifradoAES.encriptar(v.getPlaca()) + "," +
                           CifradoAES.encriptar(v.getDpi()) + "," +
                           CifradoAES.encriptar(v.getNombre()) + "," +
                           CifradoAES.encriptar(v.getDepartamento()) + "," +
                           CifradoAES.encriptar(v.getMarca()) + "," +
                           CifradoAES.encriptar(v.getModelo()) + "," +
                           CifradoAES.encriptar(v.getAño()) + "," +
                           CifradoAES.encriptar(v.getMultas()) + "," +
                           CifradoAES.encriptar(v.getTraspasos());

            writer.write(linea);
            writer.newLine();
        }
        writer.flush();

        long fin = System.nanoTime();
        long duracionNs = fin - inicio;
        long duracionMs = duracionNs / 1_000_000;

        JOptionPane.showMessageDialog(null, "Archivo encriptado guardado correctamente en:\n" +
                archivoGuardar.getAbsolutePath() +
                "\nTiempo: " + duracionMs + " ms (" + duracionNs + " ns)");

    } catch (IOException ex) {
        JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + ex.getMessage());
        ex.printStackTrace();
    }

    }//GEN-LAST:event_GuardarActionPerformed

    private void TextPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextPlacaActionPerformed

    private void Bus_Placa_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Bus_Placa_2ActionPerformed
      String placaBuscada = TextPlaca.getText().trim();

    if (placaBuscada.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingrese una placa para buscar.");
        return;
    }

    if (BOTON_AVL.isSelected() && BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(null, "Seleccione solo un árbol (AVL o ABB).");
        return;
    }

    if (!BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(null, "Seleccione un árbol (AVL o ABB).");
        return;
    }

    Vehiculo resultado = null;

    long inicio = System.nanoTime();

    if (BOTON_AVL.isSelected()) {
        resultado = ArbolAVL.buscarVehiculo(placaBuscada);
    } else if (BOTON_ABB.isSelected()) {
        resultado = ArbolABB.buscarVehiculo(placaBuscada);
    }

    long fin = System.nanoTime();
    long duracion = fin - inicio;

    if (resultado != null) {
        JOptionPane.showMessageDialog(null,
                "Vehículo encontrado:\n\n"
                + "Placa: " + resultado.getPlaca() + "\n"
                + "DPI: " + resultado.getDpi() + "\n"
                + "Nombre: " + resultado.getNombre() + "\n"
                + "Marca: " + resultado.getMarca() + "\n"
                + "Modelo: " + resultado.getModelo() + "\n"
                + "Año: " + resultado.getAño() + "\n"
                + "Multas: " + resultado.getMultas() + "\n"
                + "Traspasos: " + resultado.getTraspasos() + "\n\n"
                + "Tiempo de búsqueda: " + duracion + " ns");
    } else {
        JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
    }
    }//GEN-LAST:event_Bus_Placa_2ActionPerformed

    private void Buscar_Fch_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar_Fch_2ActionPerformed
         JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto", "txt"));
    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
        long inicio = System.nanoTime(); // Tiempo inicial

        File file = fileChooser.getSelectedFile();
        setArchivoActual(file);

        if (BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
            arbolSeleccionado = ArbolAVL;
            ArbolAVL = new ArbolAVL();  // Reiniciar árbol AVL
            arbolSeleccionado = ArbolAVL;
        } else if (BOTON_ABB.isSelected() && !BOTON_AVL.isSelected()) {
            arbolSeleccionado = ArbolABB;
            ArbolABB = new ArbolABB();  // Reiniciar árbol ABB
            arbolSeleccionado = ArbolABB;
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione solo un árbol (AVL o ABB).");
            return;
        }

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{
            "PLACA", "DPI", "NOMBRE", "MARCA", "MODELO", "AÑO", "MULTAS", "TRASPASOS", "DEPARTAMENTO"
        });

        List<Vehiculo> listaVehiculos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length == 9) {
                    Vehiculo vehiculo = new Vehiculo(
                        partes[0].trim(), // PLACA
                        partes[1].trim(), // DPI
                        partes[2].trim(), // NOMBRE
                        partes[3].trim(), // MARCA
                        partes[4].trim(), // MODELO
                        partes[5].trim(), // AÑO
                        partes[6].trim(), // MULTAS
                        partes[7].trim(), // TRASPASOS
                        partes[8].trim()  // DEPARTAMENTO
                    );
                    listaVehiculos.add(vehiculo);
                    modelo.addRow(vehiculo.toRow());
                } else {
                    JOptionPane.showMessageDialog(this, "Línea con formato incorrecto:\n" + linea);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage());
            ex.printStackTrace();
        }

        for (Vehiculo v : listaVehiculos) {
            arbolSeleccionado.insertar(v);
        }

        Tabla_Dat_Traspaso.setModel(modelo);
        actualizarTotalMultasPorDepartamento();

        long fin = System.nanoTime();
        long duracion = fin - inicio;
        JOptionPane.showMessageDialog(this, "Carga finalizada en " + (duracion / 1_000_000) + " ms (" + duracion + " ns)");
    }
    }//GEN-LAST:event_Buscar_Fch_2ActionPerformed

    private void PosOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PosOrdenActionPerformed
        // TODO add your handling code here:
        if (arbolSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione AVL o ABB");
        return;
    }
    long inicio = System.nanoTime();
    List<Vehiculo> lista = arbolSeleccionado.postOrden();
    actualizarTablaDesdeLista(lista);
    long fin = System.nanoTime();

    JOptionPane.showMessageDialog(this, "PosOrden ejecutado en " + (fin - inicio) / 1_000_000 + " ms (" + (fin - inicio) + " ns)");
    }//GEN-LAST:event_PosOrdenActionPerformed

    private void InOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InOrdenActionPerformed
        // TODO add your handling code here:
       if (arbolSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione AVL o ABB");
        return;
    }

    long inicio = System.nanoTime();
    List<Vehiculo> lista = arbolSeleccionado.inOrden();
    actualizarTablaDesdeLista(lista);
    long fin = System.nanoTime();

    JOptionPane.showMessageDialog(this, "InOrden ejecutado en " + (fin - inicio) / 1_000_000 + " ms (" + (fin - inicio) + " ns)");
    }//GEN-LAST:event_InOrdenActionPerformed

    private void PreOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreOrdenActionPerformed
        // TODO add your handling code here:
       if (arbolSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione AVL o ABB");
        return;
    }

    long inicio = System.nanoTime();
    List<Vehiculo> lista = arbolSeleccionado.preOrden();
    actualizarTablaDesdeLista(lista);
    long fin = System.nanoTime();

    JOptionPane.showMessageDialog(this, "PreOrden ejecutado en " + (fin - inicio) / 1_000_000 + " ms (" + (fin - inicio) + " ns)");
    }//GEN-LAST:event_PreOrdenActionPerformed

    private void AsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AsignarActionPerformed
        // TODO add your handling code here:
        if (BOTON_AVL.isSelected() && BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(null, "Seleccione solo un árbol (AVL o ABB).");
        return;
    }

    if (!BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(null, "Seleccione un árbol (AVL o ABB).");
        return;
    }

    // Crear campos de entrada
    JTextField campoPlaca = new JTextField();
    JTextField campoDPI = new JTextField();
    JTextField campoNombre = new JTextField();
    JTextField campoMarca = new JTextField();
    JTextField campoModelo = new JTextField();
    JTextField campoAnio = new JTextField();
    JTextField campoMultas = new JTextField();
    JTextField campoTraspasos = new JTextField();
    JTextField campoDepartamento = new JTextField();

    // Armar panel con campos
    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(new JLabel("PLACA:"));
    panel.add(campoPlaca);
    panel.add(new JLabel("DPI:"));
    panel.add(campoDPI);
    panel.add(new JLabel("NOMBRE:"));
    panel.add(campoNombre);
    panel.add(new JLabel("MARCA:"));
    panel.add(campoMarca);
    panel.add(new JLabel("MODELO:"));
    panel.add(campoModelo);
    panel.add(new JLabel("AÑO:"));
    panel.add(campoAnio);
    panel.add(new JLabel("MULTAS:"));
    panel.add(campoMultas);
    panel.add(new JLabel("TRASPASOS:"));
    panel.add(campoTraspasos);
    panel.add(new JLabel("DEPARTAMENTO:"));
    panel.add(campoDepartamento);

    // Mostrar cuadro de diálogo
    int result = JOptionPane.showConfirmDialog(null, panel, "Ingresar Vehículo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        try {
            // Leer datos como String
            String placa = campoPlaca.getText().trim();
            String dpi = campoDPI.getText().trim();
            String nombre = campoNombre.getText().trim();
            String marca = campoMarca.getText().trim();
            String modelo = campoModelo.getText().trim();
            String anio = campoAnio.getText().trim();
            String multas = campoMultas.getText().trim();
            String traspasos = campoTraspasos.getText().trim();
             String departamento = campoDepartamento.getText().trim();

            // Crear objeto Vehiculo con nuevo campo
            Vehiculo nuevoVehiculo = new Vehiculo(
                placa, dpi, nombre, marca, modelo, anio, multas, traspasos, departamento
            );

            long inicio = System.nanoTime();

            if (BOTON_AVL.isSelected()) {
                ArbolAVL.insertar(nuevoVehiculo);
            } else if (BOTON_ABB.isSelected()) {
                ArbolABB.insertar(nuevoVehiculo);
            }

            long fin = System.nanoTime();
            long duracion = fin - inicio;

            JOptionPane.showMessageDialog(null, "Vehículo ingresado correctamente.\nTiempo: " + duracion + " ns");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al ingresar los datos. Asegúrese de llenar todos los campos correctamente.");
        }
    }
    }//GEN-LAST:event_AsignarActionPerformed

    private final String rutaArchivoVehiculos = "vehiculos.txt";
    
    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        // TODO add your handling code here:
        String placaBuscada = TextPlaca.getText().trim();

    if (placaBuscada.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingrese una placa para editar.");
        return;
    }

    if (!BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(null, "Seleccione un árbol (AVL o ABB).");
        return;
    }

    long inicio = System.nanoTime();

    Vehiculo resultado = BOTON_AVL.isSelected()
            ? ArbolAVL.buscarVehiculo(placaBuscada)
            : ArbolABB.buscarVehiculo(placaBuscada);

    if (resultado == null) {
        JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
        return;
    }

    // Crear campos prellenados
    JTextField campoPlaca = new JTextField(resultado.getPlaca());
    JTextField campoDPI = new JTextField(resultado.getDpi());
    JTextField campoNombre = new JTextField(resultado.getNombre());
    JTextField campoMarca = new JTextField(resultado.getMarca());
    JTextField campoModelo = new JTextField(resultado.getModelo());
    JTextField campoAnio = new JTextField(resultado.getAño());
    JTextField campoMultas = new JTextField(resultado.getMultas());
    JTextField campoTraspasos = new JTextField(resultado.getTraspasos());
    JTextField campoDepartamento = new JTextField(resultado.getDepartamento());

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(new JLabel("PLACA:")); panel.add(campoPlaca);
    panel.add(new JLabel("DPI:")); panel.add(campoDPI);
    panel.add(new JLabel("NOMBRE:")); panel.add(campoNombre);
    panel.add(new JLabel("MARCA:")); panel.add(campoMarca);
    panel.add(new JLabel("MODELO:")); panel.add(campoModelo);
    panel.add(new JLabel("AÑO:")); panel.add(campoAnio);
    panel.add(new JLabel("MULTAS:")); panel.add(campoMultas);
    panel.add(new JLabel("TRASPASOS:")); panel.add(campoTraspasos);
    panel.add(new JLabel("DEPARTAMENTO:")); panel.add(campoDepartamento);

    int result = JOptionPane.showConfirmDialog(null, panel, "Editar Vehículo",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        // Eliminar el anterior y agregar el actualizado
        if (BOTON_AVL.isSelected()) {
            ArbolAVL.eliminar(placaBuscada);
        } else {
            ArbolABB.eliminar(placaBuscada);
        }

        Vehiculo nuevo = new Vehiculo(
                campoPlaca.getText().trim(),
                campoDPI.getText().trim(),
                campoNombre.getText().trim(),
                campoMarca.getText().trim(),
                campoModelo.getText().trim(),
                campoAnio.getText().trim(),
                campoMultas.getText().trim(),
                campoTraspasos.getText().trim(),
                campoDepartamento.getText().trim()
        );

        if (BOTON_AVL.isSelected()) {
            ArbolAVL.insertar(nuevo);
        } else {
            ArbolABB.insertar(nuevo);
        }

        // Obtener lista actualizada
        List<Vehiculo> listaActualizada = BOTON_AVL.isSelected()
                ? ArbolAVL.inOrden()
                : ArbolABB.inOrden();

        // Actualizar JTable
        actualizarTablaDesdeLista(listaActualizada);

        // Guardar en archivo
        guardarArbolEnArchivo(rutaArchivoVehiculos, listaActualizada);

        long fin = System.nanoTime();
        long tiempoMs = (fin - inicio) / 1_000_000;

        JOptionPane.showMessageDialog(null, "Vehículo actualizado correctamente.\nTiempo de ejecución: " + tiempoMs + " ms");
    }
    }//GEN-LAST:event_EditActionPerformed

    private void BOTON_AVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOTON_AVLActionPerformed
        // TODO add your handling code here:
        if (BOTON_AVL.isSelected() && BOTON_ABB.isSelected()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar solo un tipo de árbol.");
        BOTON_AVL.setSelected(false);
        arbolSeleccionado = null;
    } else if (BOTON_AVL.isSelected()) {
        arbolSeleccionado = new ArbolAVL(); // Asegúrate de tener esta clase implementada
        BOTON_ABB.setSelected(false);
    } else {
        arbolSeleccionado = null;
    }
    }//GEN-LAST:event_BOTON_AVLActionPerformed

    private void BOTON_ABBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOTON_ABBActionPerformed
        // TODO add your handling code here:
         if (BOTON_ABB.isSelected() && BOTON_AVL.isSelected()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Debe seleccionar solo un tipo de árbol.");
        BOTON_ABB.setSelected(false);
        arbolSeleccionado = null;
    } else if (BOTON_ABB.isSelected()) {
        arbolSeleccionado = (Arbol) new ArbolABB();
        BOTON_AVL.setSelected(false);
    } else {
        arbolSeleccionado = null;
    }
    }//GEN-LAST:event_BOTON_ABBActionPerformed

    private void EscribirBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EscribirBusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EscribirBusActionPerformed

    private void Buscar_Placa_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar_Placa_1ActionPerformed
        // TODO add your handling code here:
       String placaBuscar = EscribirBus.getText().trim();

    if (placaBuscar.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese una placa para buscar.");
        return;
    }

    long inicio = System.nanoTime(); // Tiempo de inicio

    Vehiculo encontrado = null;

    if (BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        if (arbolSeleccionado instanceof ArbolAVL) {
            encontrado = ((ArbolAVL) arbolSeleccionado).buscarVehiculo(placaBuscar);
        }
    } else if (BOTON_ABB.isSelected() && !BOTON_AVL.isSelected()) {
        if (arbolSeleccionado instanceof ArbolABB) {
            encontrado = ((ArbolABB) arbolSeleccionado).buscarVehiculo(placaBuscar); // Asegúrate de implementar este método
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione solo un árbol (AVL o ABB).");
        return;
    }

    long fin = System.nanoTime(); // Tiempo final
    long duracion = fin - inicio;

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{"PLACA", "DPI", "NOMBRE", "MARCA", "MODELO", "AÑO", "MULTAS", "TRASPASOS", "DEPARTAMENTO"});

    if (encontrado != null) {
        modelo.addRow(encontrado.toRow());
        JOptionPane.showMessageDialog(this, "Búsqueda completada en " + duracion / 1_000_000 + " ms (" + duracion + " ns)");
    } else {
        JOptionPane.showMessageDialog(this, "Vehículo no encontrado. Tiempo: " + duracion / 1_000_000 + " ms (" + duracion + " ns)");
    }

    Tabla_Dat_Traspaso.setModel(modelo);
    
    actualizarTotalMultasPorDepartamento();
    }//GEN-LAST:event_Buscar_Placa_1ActionPerformed

    private void Eliminar_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Eliminar_1ActionPerformed
        // TODO add your handling code here:
       String placaEliminar = TextPlaca.getText().trim();

    if (placaEliminar.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Ingrese la placa del vehículo a eliminar.");
        return;
    }

    if (!BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(null, "Seleccione un árbol (AVL o ABB).");
        return;
    }

    boolean eliminado = false;
    long inicio = System.nanoTime();

    if (BOTON_AVL.isSelected()) {
        eliminado = ArbolAVL.eliminar(placaEliminar);  // Instancia, no clase
    } else if (BOTON_ABB.isSelected()) {
        eliminado = ArbolABB.eliminar(placaEliminar);
    }

    long fin = System.nanoTime();
    long tiempo = fin - inicio;

    if (eliminado) {
        List<Vehiculo> listaActualizada = BOTON_AVL.isSelected()
                ? ArbolAVL.inOrden()
                : ArbolABB.inOrden();

        actualizarTablaDesdeLista(listaActualizada);  // Refresca la JTable

        // Mostrar los dos mensajes separados
        JOptionPane.showMessageDialog(null,
            "Vehículo eliminado correctamente.\nTiempo: " + (tiempo / 1_000_000.0) + " ms.");

        JOptionPane.showMessageDialog(null,
            "✅ La información se ha eliminado del árbol y la tabla ha sido actualizada.");

        TextPlaca.setText("");
    } else {
        JOptionPane.showMessageDialog(null, "❌ Vehículo no encontrado o ya eliminado.");
    }
    }//GEN-LAST:event_Eliminar_1ActionPerformed

    private void Refresh_PlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Refresh_PlacaActionPerformed
        // TODO add your handling code here:
        if (BOTON_AVL.isSelected() && BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione solo un árbol (AVL o ABB).");
        return;
    }

    if (!BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un árbol para refrescar.");
        return;
    }

    long inicio = System.nanoTime(); // Tiempo de inicio

    List<Vehiculo> listaVehiculos = null;

    if (BOTON_AVL.isSelected()) {
        if (arbolSeleccionado instanceof ArbolAVL) {
            listaVehiculos = ((ArbolAVL) arbolSeleccionado).inOrden();  // obtiene toda la info ordenada
        } else {
            JOptionPane.showMessageDialog(this, "Error: árbol AVL no encontrado.");
            return;
        }
    } else if (BOTON_ABB.isSelected()) {
        if (arbolSeleccionado instanceof ArbolABB) {
            listaVehiculos = ((ArbolABB) arbolSeleccionado).inOrden();
        } else {
            JOptionPane.showMessageDialog(this, "Error: árbol ABB no encontrado.");
            return;
        }
    }

    long fin = System.nanoTime(); // Tiempo final
    long duracion = fin - inicio;

    if (listaVehiculos == null || listaVehiculos.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay datos para mostrar. Tiempo: " + duracion / 1_000_000 + " ms (" + duracion + " ns)");
        return;
    }

    // Crear modelo y actualizar tabla
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[]{"PLACA", "DPI", "NOMBRE", "MARCA", "MODELO", "AÑO", "MULTAS", "TRASPASOS", "DEPARTAMENTO"});

    for (Vehiculo v : listaVehiculos) {
        modelo.addRow(v.toRow());  // asumo que tienes un método toRow() que devuelve Object[]
    }

    Tabla_Dat_Traspaso.setModel(modelo);

    // Mostrar mensaje con tiempo
    JOptionPane.showMessageDialog(this, "Refrescado completado en " + duracion / 1_000_000 + " ms (" + duracion + " ns)");
    }//GEN-LAST:event_Refresh_PlacaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (!BOTON_AVL.isSelected() && !BOTON_ABB.isSelected()) {
        JOptionPane.showMessageDialog(this, "Seleccione un árbol (AVL o ABB) para generar el diagrama.");
        return;
    }

    try {
        long inicio = System.nanoTime();

        // Generar contenido .dot del árbol
        String dotContent = BOTON_AVL.isSelected() ? ArbolAVL.generarDot() : ArbolABB.generarDot();  // usa las instancias, no clases

        // Guardar archivo DOT
        File dotFile = new File("arbol.dot");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFile))) {
            writer.write(dotContent);
        }

        // Convertir a imagen PNG usando Graphviz
        String outputImage = "arbol.png";
        ProcessBuilder pb = new ProcessBuilder("dot", "-Tpng", "-Gdpi=300", "arbol.dot", "-o", outputImage);

        Process p = pb.start();
        p.waitFor();

        long fin = System.nanoTime();
        long duracion = (fin - inicio) / 1_000_000;

        // Mostrar imagen en una ventana con zoom
        mostrarImagenConZoom(outputImage);

        JOptionPane.showMessageDialog(this, "Diagrama generado correctamente en " + duracion + " ms");

    } catch (IOException | InterruptedException ex) {
        JOptionPane.showMessageDialog(this, "Error al generar el diagrama: " + ex.getMessage());
        ex.printStackTrace();
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void IncriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IncriptarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) Tabla_Dat_Traspaso.getModel();

    for (int row = 0; row < model.getRowCount(); row++) {
        for (int col = 0; col < model.getColumnCount(); col++) {
            Object valor = model.getValueAt(row, col);
            if (valor != null) {
                String texto = valor.toString();
                String encriptado = CifradoAES.encriptar(texto);
                model.setValueAt(encriptado, row, col);
            }
        }
    }

    JOptionPane.showMessageDialog(this, "Datos encriptados correctamente.");
    }//GEN-LAST:event_IncriptarActionPerformed

    private void DES_INCRIPTARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DES_INCRIPTARActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) Tabla_Dat_Traspaso.getModel();

    // Validar si hay datos
    if (model.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "La tabla no contiene datos para desencriptar.");
        return;
    }

    try {
        for (int fila = 0; fila < model.getRowCount(); fila++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                String datoEncriptado = (String) model.getValueAt(fila, col);
                String datoDesencriptado = CifradoAES.desencriptar(datoEncriptado);
                model.setValueAt(datoDesencriptado, fila, col);
            }
        }

        JOptionPane.showMessageDialog(this, "Datos desencriptados correctamente.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al desencriptar los datos: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_DES_INCRIPTARActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Antigua_Guatemala;
    private javax.swing.JButton Asignar;
    private javax.swing.JRadioButton BOTON_ABB;
    private javax.swing.JRadioButton BOTON_AVL;
    private javax.swing.JButton Barra;
    private javax.swing.JButton Bus_Placa_2;
    private javax.swing.JButton Buscar_Fch_2;
    private javax.swing.JButton Buscar_Placa_1;
    private javax.swing.JLabel Chimaltenango;
    private javax.swing.JLabel Chiquimula;
    private javax.swing.JButton DES_INCRIPTAR;
    private javax.swing.JPanel Desplegable1;
    private javax.swing.JButton Edit;
    private javax.swing.JButton Eliminar_1;
    private javax.swing.JTextField EscribirBus;
    private javax.swing.JLabel Escuintla;
    private javax.swing.JButton Guardar;
    private javax.swing.JLabel Guatemala;
    private javax.swing.JButton Home1;
    private javax.swing.JLabel Huehuetenango;
    private javax.swing.JButton InOrden;
    private javax.swing.JButton Incriptar;
    private javax.swing.JLabel Peten;
    private javax.swing.JButton PosOrden;
    private javax.swing.JButton PreOrden;
    private javax.swing.JLabel Quetzaltenango;
    private javax.swing.JButton Refresh_Placa;
    private javax.swing.JLabel San_Marcos;
    private javax.swing.JLabel Suchitepequez;
    private javax.swing.JTable Tabla_Dat_Traspaso;
    private javax.swing.JTextField TextPlaca;
    private javax.swing.JLabel Titulo2;
    private javax.swing.JLabel Titulo3;
    private javax.swing.JLabel User_Indicator;
    private javax.swing.JButton Usuario;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private void guardarArbolEnArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

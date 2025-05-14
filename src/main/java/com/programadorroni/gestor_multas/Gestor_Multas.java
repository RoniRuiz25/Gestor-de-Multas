
package com.programadorroni.gestor_multas;
/**
 *
 * @author isaia
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Gestor_Multas {

    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestor de Multas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Agregar tu PanelPrincipal al frame
            PanelPrincipal panel = new PanelPrincipal();
            frame.setContentPane(panel);

            // Ajustar el tamaño y mostrar
            frame.pack();
            frame.setLocationRelativeTo(null); // Centrar la ventana
            frame.setVisible(true);
        });
    }
}

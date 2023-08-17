package bo.edu.ucb.sis213;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import bo.edu.ucb.sis213.Objetos.Operacion;
import bo.edu.ucb.sis213.Objetos.OperacionesDAO;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class HistorialInterfaz extends JFrame {
    private JTable historialTable;
    private JButton volverButton;

    public HistorialInterfaz(int usuarioId) {
        setTitle("Historial de Operaciones");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.LIGHT_GRAY);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("Número de Operación");
        columnNames.add("Tipo de Operación");
        columnNames.add("Monto");

        DefaultTableModel model = new DefaultTableModel(null, columnNames);

        try {
            List<Operacion> historial = OperacionesDAO.obtenerHistorial(usuarioId);

            for (Operacion operacion : historial) {
                Vector<String> rowData = new Vector<>();
                rowData.add(String.valueOf(operacion.getId()));
                rowData.add(operacion.getTipo_operacion());
                rowData.add(String.format("$%.2f", operacion.getCantidad()));
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        historialTable = new JTable(model);
        historialTable.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(historialTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        volverButton = new JButton("Volver al Menú");
        stylizeButton(volverButton);
        volverButton.addActionListener(e -> dispose());
        panel.add(volverButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void stylizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Supongamos que ya tienes el ID del usuario que ha iniciado sesión
            int usuarioId = 1;
            new HistorialInterfaz(usuarioId);
        });
    }
}

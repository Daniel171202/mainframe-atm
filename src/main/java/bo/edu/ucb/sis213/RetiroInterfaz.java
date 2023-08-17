package bo.edu.ucb.sis213;

import javax.swing.*;

import bo.edu.ucb.sis213.Objetos.Cuenta;
import bo.edu.ucb.sis213.Objetos.OperacionesDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RetiroInterfaz extends JFrame {
    private JTextField montoTextField;
    private JButton retirarButton;
    private JButton cancelarButton;

    public RetiroInterfaz(Cuenta cuenta) {
        setTitle("Retiro de Dinero");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel montoLabel = new JLabel("Monto a Retirar:");
        montoLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(montoLabel);

        montoTextField = new JTextField();
        panel.add(montoTextField);

        retirarButton = new JButton("Retirar");
        stylizeButton(retirarButton);
        retirarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String montoStr = montoTextField.getText();

                try {
                    double monto = Double.parseDouble(montoStr);
                    if (monto > 0) {
                        // Lógica para realizar el retiro

                        try {
                            OperacionesDAO.registrarRetiro(cuenta, monto);
                            dispose();
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(null, "Retiro exitoso: $" + monto);
                    } else {
                        JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(retirarButton);

        cancelarButton = new JButton("Cancelar");
        stylizeButton(cancelarButton);
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelarButton);

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Verificar el inicio de sesión aquí (no mostrar si no se ha iniciado sesión)
                // Si el inicio de sesión es exitoso, mostrar la ventana de retiro:
                Cuenta cuenta;
                try {
                    cuenta = OperacionesDAO.obtenerCuenta(1);
                    new RetiroInterfaz(cuenta);
        
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }            }
        });
    }
}

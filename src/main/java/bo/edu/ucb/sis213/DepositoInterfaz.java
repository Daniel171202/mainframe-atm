package bo.edu.ucb.sis213;

import javax.swing.*;

import bo.edu.ucb.sis213.Objetos.Cuenta;
import bo.edu.ucb.sis213.Objetos.OperacionesDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DepositoInterfaz extends JFrame {
    private JTextField montoTextField;
    private JButton depositarButton;
    private JButton cancelarButton;

    public DepositoInterfaz(Cuenta cuenta) {
        setTitle("Depósito de Dinero");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel montoLabel = new JLabel("Monto a Depositar:");
        montoLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(montoLabel);

        montoTextField = new JTextField();
        panel.add(montoTextField);

        depositarButton = new JButton("Depositar");
        stylizeButton(depositarButton);
        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String montoStr = montoTextField.getText();

                try {
                    double monto = Double.parseDouble(montoStr);
                    if (monto > 0) {
                        // Lógica para realizar el depósito
                        JOptionPane.showMessageDialog(null, "Depósito exitoso: $" + monto);

                        try {
                            OperacionesDAO.registrarDeposito(cuenta, monto);
                            dispose();

                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        

                    } else {
                        JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(depositarButton);

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
                // Si el inicio de sesión es exitoso, mostrar la ventana de depósito:
        Cuenta cuenta;
        try {
            cuenta = OperacionesDAO.obtenerCuenta(1);
            new DepositoInterfaz(cuenta);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }            }
        });
    }
}

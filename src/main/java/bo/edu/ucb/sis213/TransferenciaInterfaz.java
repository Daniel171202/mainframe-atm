package bo.edu.ucb.sis213;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bo.edu.ucb.sis213.Objetos.Cuenta;
import bo.edu.ucb.sis213.Objetos.OperacionesDAO;

public class TransferenciaInterfaz extends JFrame {
    private JTextField destinatarioTextField;
    private JTextField montoTextField;
    private JButton transferirButton;
    private JButton cancelarButton;

    public TransferenciaInterfaz(Cuenta cuenta) {
        setTitle("Transferencia de Dinero");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel destinatarioLabel = new JLabel("Cuenta Destinataria(CI):");
        destinatarioLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(destinatarioLabel);

        destinatarioTextField = new JTextField();
        panel.add(destinatarioTextField);

        JLabel montoLabel = new JLabel("Monto a Transferir:");
        montoLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(montoLabel);

        montoTextField = new JTextField();
        panel.add(montoTextField);

        transferirButton = new JButton("Transferir");
        stylizeButton(transferirButton);
        transferirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String destinatario = destinatarioTextField.getText();
                String montoStr = montoTextField.getText();

                try {

                    double monto = Double.parseDouble(montoStr);
                    if (monto > 0) {
                        // Lógica para realizar la transferencia
                        try {
                            Cuenta cuentaDestinatario = OperacionesDAO.obtenerCuentabyCi(destinatario);
                            OperacionesDAO.registrarTransferencia(cuenta, cuentaDestinatario.getId() ,monto);
                            dispose();
                        } catch (SQLException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(null, "Transferencia exitosa: $" + monto + " a " + destinatario);
                    } else {
                        JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Monto inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(transferirButton);

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
                // Si el inicio de sesión es exitoso, mostrar la ventana de transferencia:
        Cuenta cuenta;
        try {
            cuenta = OperacionesDAO.obtenerCuenta(1);
            new TransferenciaInterfaz(cuenta);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }            }
        });
    }
}

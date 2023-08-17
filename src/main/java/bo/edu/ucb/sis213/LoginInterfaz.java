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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bo.edu.ucb.sis213.Objetos.Cuenta;
import bo.edu.ucb.sis213.Objetos.OperacionesDAO;

public class LoginInterfaz extends JFrame {
    private JTextField ciTextField;
    private JPasswordField pinPasswordField;
    private JButton entrarButton;
    private JButton cerrarButton;
    private int intentos = 3; // Número de intentos permitidos

    public LoginInterfaz() {
        setTitle("Inicio de Sesión");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel ciLabel = new JLabel("CI:");
        ciLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(ciLabel);

        ciTextField = new JTextField();
        panel.add(ciTextField);

        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(pinLabel);

        pinPasswordField = new JPasswordField();
        panel.add(pinPasswordField);

        entrarButton = new JButton("Entrar");
        stylizeButton(entrarButton);

        entrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ci = ciTextField.getText();
                String pin = new String(pinPasswordField.getPassword());



                try {
                    OperacionesDAO operacionesDAO = new OperacionesDAO();

                    Cuenta cuenta=operacionesDAO.obtenerCuentabyCi(ci);
                    if (cuenta != null) {
                        if (cuenta.getPin().equals(pin)) {
                            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
                            new Menu(cuenta);
                            dispose(); // Cerrar ventana de inicio de sesión
                        } else {
                            intentos--;
                            if (intentos > 0) {
                                JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Intentos restantes: " + intentos,
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Has agotado tus intentos.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                dispose(); // Cerrar ventana de inicio de sesión después de agotar los intentos
                            }
                        }
                    }
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

            }
        });
        panel.add(entrarButton);

        cerrarButton = new JButton("Cerrar");
        stylizeButton(cerrarButton);
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cerrarButton);

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
                new LoginInterfaz();
            }
        });
    }
}

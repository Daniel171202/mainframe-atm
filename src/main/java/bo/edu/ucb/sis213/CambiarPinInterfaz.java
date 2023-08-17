package bo.edu.ucb.sis213;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import bo.edu.ucb.sis213.Objetos.Cuenta;
import bo.edu.ucb.sis213.Objetos.OperacionesDAO;

public class CambiarPinInterfaz extends JFrame {
    private JPasswordField pinActualField;
    private JPasswordField nuevoPinField;
    private JPasswordField confirmarPinField;
    private JButton cambiarPinButton;

    private Cuenta cuenta;

    public CambiarPinInterfaz(Cuenta cuenta) {
        this.cuenta = cuenta;

        setTitle("Cambiar PIN");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel pinActualLabel = new JLabel("PIN Actual:");
        pinActualLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(pinActualLabel);

        pinActualField = new JPasswordField();
        panel.add(pinActualField);

        JLabel nuevoPinLabel = new JLabel("Nuevo PIN:");
        nuevoPinLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(nuevoPinLabel);

        nuevoPinField = new JPasswordField();
        panel.add(nuevoPinField);

        JLabel confirmarPinLabel = new JLabel("Confirmar PIN:");
        confirmarPinLabel.setHorizontalAlignment(JLabel.RIGHT);
        panel.add(confirmarPinLabel);

        confirmarPinField = new JPasswordField();
        panel.add(confirmarPinField);

        cambiarPinButton = new JButton("Cambiar PIN");
        cambiarPinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarPin();
            }
        });
        panel.add(new JLabel()); // Espacio vacío para alinear el botón
        panel.add(cambiarPinButton);

        add(panel);
        setVisible(true);
    }

    private void cambiarPin() {
        String pinActual = new String(pinActualField.getPassword());
        String nuevoPin = new String(nuevoPinField.getPassword());
        String confirmarPin = new String(confirmarPinField.getPassword());

        if (!pinActual.equals(cuenta.getPin())) {
            JOptionPane.showMessageDialog(null, "El PIN actual es incorrecto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nuevoPin.equals(confirmarPin)) {
            JOptionPane.showMessageDialog(null, "Los nuevos PIN no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            OperacionesDAO operacionesDAO = new OperacionesDAO();
            operacionesDAO.actualizarPin(cuenta, nuevoPin);
            JOptionPane.showMessageDialog(null, "PIN cambiado exitosamente.");
            dispose(); // Cerrar la ventana después de cambiar el PIN
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al cambiar el PIN.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crear una cuenta de ejemplo (puedes adaptar esto según tu lógica)

                OperacionesDAO operacionesDAO = new OperacionesDAO();
                Cuenta cuenta;
                try {
                    cuenta = operacionesDAO.obtenerCuenta(1);
                    new CambiarPinInterfaz(cuenta);
        
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            }
        });
    }
}

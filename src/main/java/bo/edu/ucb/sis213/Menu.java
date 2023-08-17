package bo.edu.ucb.sis213;

import javax.swing.*;

import bo.edu.ucb.sis213.Objetos.Cuenta;
import bo.edu.ucb.sis213.Objetos.OperacionesDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Menu extends JFrame {
    private JButton cambiarPinButton;
    private JButton retiroButton;
    private JButton depositoButton;
    private JButton transferenciaButton;
    private JButton historialButton;
    private JButton salirButton;

    public Menu(Cuenta cuenta) {
        setTitle("Menú Principal");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.LIGHT_GRAY);

      

        retiroButton = new JButton("Realizar Retiro");
        stylizeButton(retiroButton);
        retiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RetiroInterfaz(cuenta);
            }
        });
        panel.add(retiroButton);

        depositoButton = new JButton("Realizar Depósito");
        stylizeButton(depositoButton);
        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepositoInterfaz(cuenta);
            }
        });
        panel.add(depositoButton);

        transferenciaButton = new JButton("Realizar Transferencia");
        stylizeButton(transferenciaButton);
        transferenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransferenciaInterfaz(cuenta);
            }
        });
        panel.add(transferenciaButton);

        historialButton = new JButton("Ver Historial");
        stylizeButton(historialButton);
        historialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistorialInterfaz(cuenta.getId());
            }
        });
        panel.add(historialButton);
        
        cambiarPinButton = new JButton("Cambiar PIN");
        stylizeButton(cambiarPinButton);
        cambiarPinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CambiarPinInterfaz(cuenta);


            }
        });
        panel.add(cambiarPinButton);

        salirButton = new JButton("Cerrar Sesión");
        stylizeButton(salirButton);
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginInterfaz();
                dispose();
            }
        });
        panel.add(salirButton);

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
        Cuenta cuenta;
        try {
            cuenta = OperacionesDAO.obtenerCuenta(1);
            new Menu(cuenta);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    
}

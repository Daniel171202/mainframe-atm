package bo.edu.ucb.sis213.Objetos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OperacionesDAO {

    public static Cuenta obtenerCuenta(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String pin = resultSet.getString("pin");
                    double saldo = resultSet.getDouble("saldo");
                    String ci = resultSet.getString("ci");
                    
                    return new Cuenta(id, nombre, pin, saldo, ci);
                }
            }
        }

        return null;
    }

    public static Cuenta obtenerCuentabyCi(String ci)throws SQLException{
        String sql = "SELECT * FROM usuarios WHERE ci = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, ci);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String pin = resultSet.getString("pin");
                    double saldo = resultSet.getDouble("saldo");
                    
                    return new Cuenta(id, nombre, pin, saldo, ci);
                }
            }
        }

        return null;
    }

    public static void registrarDeposito(Cuenta cuenta, double monto) throws SQLException {
        registrarOperacion(cuenta, "Depósito", monto);
    }

    public static void registrarRetiro(Cuenta cuenta, double monto) throws SQLException {
        registrarOperacion(cuenta, "Retiro", -monto);
    }

    public static void registrarTransferencia(Cuenta cuenta, int usuarioIdDestino, double monto) throws SQLException {
        registrarOperacion(cuenta, "Transferencia enviada", -monto);
        Cuenta cuentaDestino = obtenerCuenta(usuarioIdDestino);
        registrarOperacion(cuentaDestino, "Transferencia recibida", monto);
    }

    private static void registrarOperacion(Cuenta cuenta, String tipoOperacion, double cantidad) throws SQLException {

        String sql1 = "UPDATE usuarios SET saldo = saldo + ? WHERE ci = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql1)) {

            preparedStatement.setDouble(1, cantidad);
            preparedStatement.setString(2, cuenta.getCi());

            preparedStatement.executeUpdate();
        }


        String sql = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad, fecha) VALUES (?, ?, ?,?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, cuenta.getId());
            preparedStatement.setString(2, tipoOperacion);
            preparedStatement.setDouble(3, cantidad);
            preparedStatement.setDate(4, new java.sql.Date(System.currentTimeMillis()));
                System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    public static List<Operacion> obtenerHistorial(int usuarioId) throws SQLException {
        List<Operacion> historial = new ArrayList<>();

        String sql = "SELECT * FROM historico WHERE usuario_id = ?";
        
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setInt(1, usuarioId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String tipoOperacion = resultSet.getString("tipo_operacion");
                    double cantidad = resultSet.getDouble("cantidad");
                    String fecha = resultSet.getString("fecha");
                    
                    Operacion operacion = new Operacion(id, usuarioId, tipoOperacion, cantidad, fecha);
                    historial.add(operacion);
                }
            }
        }

        return historial;
    }

    public void actualizarPin(Cuenta cuenta, String nuevoPin) throws SQLException {
        String sql = "UPDATE usuarios SET pin = ? WHERE ci = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement preparedStatement = conexion.prepareStatement(sql)) {

            preparedStatement.setString(1, nuevoPin);
            preparedStatement.setString(2, cuenta.getCi());

            preparedStatement.executeUpdate();
        }
    }
}

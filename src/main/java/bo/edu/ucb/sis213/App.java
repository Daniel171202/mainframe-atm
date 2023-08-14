package bo.edu.ucb.sis213;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class App {
    private static int usuarioId;
    private static double saldo;
    private static int pinActual;

    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static final String DATABASE = "atm";

    public static Connection getConnection() throws SQLException {
        String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DATABASE);
        try {
            // Asegúrate de tener el driver de MySQL agregado en tu proyecto
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found.", e);
        }

        return DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
    }

    public static void main(String[] args) {
        clearConsole();

        Scanner scanner = new Scanner(System.in);
        int intentos = 3;
        System.out.println("Bienvenido al Cajero Automático.");

        Connection connection = null;
        try {
            connection = getConnection(); // Reemplaza esto con tu conexión real
        } catch (SQLException ex) {
            System.err.println("No se puede conectar a Base de Datos");
            ex.printStackTrace();
            System.exit(1);
        }
        //ask for the ci
        System.out.print("Ingrese su CI: ");
        String ci = scanner.nextLine();
        //validate ci, the query return the id and password of the user

        String query = "SELECT id FROM usuarios WHERE ci = ? ";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ci);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                usuarioId = resultSet.getInt("id");
            }else{
                System.out.println("Usuario no encontrado.");
                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        

        while (intentos > 0) {
            clearConsole();
            System.out.print("Ingrese su PIN de 4 dígitos: ");
            int pinIngresado = scanner.nextInt();
            if (validarPIN(connection, pinIngresado, usuarioId)) {
                pinActual = pinIngresado;
                bienvenida(connection);

                mostrarMenu(connection);
                break;
            } else {
                intentos--;
                if (intentos > 0) {
                    System.out.println("PIN incorrecto. Le quedan " + intentos + " intentos.");
                } else {
                    System.out.println("PIN incorrecto. Ha excedido el número de intentos.");
                    System.exit(0);
                }
            }
            waitForEnter();
        }
    }

    public static boolean validarPIN(Connection connection, int pin, int usuarioId) {
        String query = "SELECT pin,saldo FROM usuarios WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int pinBD = resultSet.getInt("pin");
                if (pin == pinBD) {
                    saldo = resultSet.getDouble("saldo");

                    return true;
                }else{
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void mostrarMenu(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Consultar saldo.");
            System.out.println("2. Realizar un depósito.");
            System.out.println("3. Realizar un retiro.");
            System.out.println("4. Cambiar PIN.");
            System.out.println("5. Realizar una transferencia.");
            System.out.println("6. Salir.");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    clearConsole();
                    consultarSaldo();
                    waitForEnter();
                    clearConsole();
                    break;
                case 2:
                    clearConsole();
                    realizarDeposito();
                    waitForEnter();
                    clearConsole();
                    break;
                case 3:
                    clearConsole();
                    realizarRetiro();
                    waitForEnter();
                    clearConsole();
                    break;
                case 4:
                    clearConsole();
                    cambiarPIN();
                    waitForEnter();
                    clearConsole();
                    break;
                case 5:
                    clearConsole();
                    realizarTransferencia();
                    waitForEnter();
                    clearConsole();
                    break;
                case 6:
                    clearConsole();
                    mostrarHistorico();
                    waitForEnter();
                    clearConsole();
                    break;
                case 7:
                    System.out.println("Gracias por usar el cajero. ¡Hasta luego!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    public static void consultarSaldo() {
        System.out.println("Su saldo actual es: $" + saldo);
    }

    public static void realizarDeposito() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a depositar: $");
        double cantidad = scanner.nextDouble();

        if (cantidad <= 0) {
            System.out.println("Cantidad no válida.");
        } else {

            saldo += cantidad;
            //update saldo in bd
            String query = "UPDATE usuarios SET saldo = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setDouble(1, saldo);
                preparedStatement.setInt(2, usuarioId);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //update historico
            actualizarHistorico("deposito",cantidad);
            System.out.println("Depósito realizado con éxito. Su nuevo saldo es: $" + saldo);
        }
    }

    public static void realizarRetiro() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a retirar: $");
        double cantidad = scanner.nextDouble();

        if (cantidad <= 0) {
            System.out.println("Cantidad no válida.");
        } else if (cantidad > saldo) {
            System.out.println("Saldo insuficiente.");
        } else {
            saldo -= cantidad;
            //uptade saldo in bd
            String query = "UPDATE usuarios SET saldo = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setDouble(1, saldo);
                preparedStatement.setInt(2, usuarioId);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //update historico
            actualizarHistorico("retiro",-cantidad);
            System.out.println("Retiro realizado con éxito. Su nuevo saldo es: $" + saldo);
        }
    }

    public static void cambiarPIN() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su PIN actual: ");
        int pinIngresado = scanner.nextInt();

        if (pinIngresado == pinActual) {
            System.out.print("Ingrese su nuevo PIN: ");
            int nuevoPin = scanner.nextInt();
            System.out.print("Confirme su nuevo PIN: ");
            int confirmacionPin = scanner.nextInt();

            if (nuevoPin == confirmacionPin) {
                pinActual = nuevoPin;
                //update pin in bd
                String query = "UPDATE usuarios SET pin = ? WHERE id = ?";
                try {
                    PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                    preparedStatement.setInt(1, pinActual);
                    preparedStatement.setInt(2, usuarioId);
                    preparedStatement.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("PIN actualizado con éxito.");
            } else {
                System.out.println("Los PINs no coinciden.");
            }
        } else {
            System.out.println("PIN incorrecto.");
        }
    }




    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\u001b[H\u001b[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
    public static void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // This will wait for the user to press Enter
    }
    public static void bienvenida(Connection connection){
        clearConsole();
        String query = "SELECT nombre FROM usuarios WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String Nombre = resultSet.getString("nombre");
                System.out.println("Bienvenido: "+Nombre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void realizarTransferencia(){
        // do a transfer between accounts
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el monto a transferir: $");
        double cantidad = scanner.nextDouble();
        System.out.print("Ingrese el CI de cuenta a transferir: ");
        int ci = scanner.nextInt();
        //do the transfer
        if (cantidad <= 0) {
            System.out.println("Cantidad no válida.");
        } else if (cantidad > saldo) {
            System.out.println("Saldo insuficiente.");
        } else {
            saldo -= cantidad;
            //uptade saldo in bd
            String query = "UPDATE usuarios SET saldo = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setDouble(1, saldo);
                preparedStatement.setInt(2, usuarioId);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //update saldo in the other account
            query = "UPDATE usuarios SET saldo = saldo + ? WHERE ci = ?";
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setDouble(1, cantidad);
                preparedStatement.setInt(2, ci);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //update historico
            actualizarHistorico("transferencia",-cantidad);
            System.out.println("Transferencia realizada con éxito. Su nuevo saldo es: $" + saldo);
            
        }
    }
    
    
    
    public static void actualizarHistorico(String tipo,Double cantidad){
            String query = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad,fecha) VALUES (?, ?, ?,?)";
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, usuarioId);
                preparedStatement.setString(2, tipo);
                
                preparedStatement.setDouble(3, cantidad);
                // actual date

                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                preparedStatement.setDate(4, sqlDate);
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public static void mostrarHistorico(){
        
    }    
}

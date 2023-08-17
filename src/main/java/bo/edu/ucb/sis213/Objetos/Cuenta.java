package bo.edu.ucb.sis213.Objetos;


public class Cuenta {
    String nombre;
    String pin;
    double saldo;
    String ci;
    int id;




    // constructor
    public Cuenta(int id, String nombre, String pin, double saldo, String ci) {
        this.nombre = nombre;
        this.pin = pin;
        this.saldo = saldo;
        this.ci = ci;
        this.id = id;
    }




    //getters and setters


    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    

    public String getNombre() {
        return nombre;
    }
    

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        if (pin.length() == 4) {
            this.pin = pin;
        } else {
            System.out.println("Pin invalido");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo > 0) {
            this.saldo = saldo;
        } else {
            System.out.println("Saldo invalido");
        }
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        if (ci.length() == 7) {
            this.ci = ci;
        } else {
            System.out.println("CI invalido");
        }
    }


    //toString
    @Override
    public String toString() {
        return "Cuenta{" +
                "nombre='" + nombre + '\'' +
                ", pin='" + pin + '\'' +
                ", saldo=" + saldo +
                ", ci='" + ci + '\'' +
                '}';
    }

    //metodos

    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        } else {
            System.out.println("Monto invalido");
        }
    }

    public void retirar(double monto) {
        if (monto > 0 && monto <= this.saldo) {
            this.saldo -= monto;
        } else {
            System.out.println("Monto invalido");
        }
    }

    public void transferir(Cuenta cuentaDestino, double monto) {
        if (monto > 0 && monto <= this.saldo) {
            this.saldo -= monto;
            cuentaDestino.depositar(monto);
        } else {
            System.out.println("Monto invalido");
        }
    }




    

   


}


package bo.edu.ucb.sis213.Objetos;

public class Operacion {
    int id;
    int usuario_id;
    String tipo_operacion;
    double cantidad;
    String fecha;

    public Operacion(int id, int usuario_id, String tipo_operacion, double cantidad, String fecha) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.tipo_operacion = tipo_operacion;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Operacion(int usuario_id, String tipo_operacion, double cantidad, String fecha) {
        this.usuario_id = usuario_id;
        this.tipo_operacion = tipo_operacion;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }


    public int getId() {
        return id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }   

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}

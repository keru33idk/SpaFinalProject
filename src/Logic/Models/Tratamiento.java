package Logic.Models;

import Logic.ConnectionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tratamiento {
    private UUID codTratamiento;
    private String nombreTratamiento;
    private String descripcion;
    private int frecuenciaSolicitudMensual;
    private int duracion;
    private double precio;
    private UUID codCategoria;
    private List<Material> materiales;

    // Constructor para inserción
    public Tratamiento(String nombreTratamiento, String descripcion, 
                      int frecuenciaSolicitudMensual, int duracion, double precio, UUID codCategoria) {
        codTratamiento = UUID.randomUUID();
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
        this.frecuenciaSolicitudMensual = frecuenciaSolicitudMensual;
        this.duracion = duracion;
        this.precio = precio;
        this.codCategoria = codCategoria;
        materiales = new ArrayList<Material>();

    }

    // Constructor para actualización/eliminación
    public Tratamiento(UUID codTratamiento, String nombreTratamiento, String descripcion, 
                      int frecuenciaSolicitudMensual, int duracion, double precio, UUID codCategoria) {
        this.codTratamiento = codTratamiento;
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
        this.frecuenciaSolicitudMensual = frecuenciaSolicitudMensual;
        this.duracion = duracion;
        this.precio = precio;
        this.codCategoria = codCategoria;
        materiales = new ArrayList<Material>();
    }

    // Getters y setters
    public UUID getCodTratamiento() { return codTratamiento; }
    public String getNombreTratamiento() { return nombreTratamiento; }
    public void setNombreTratamiento(String nombreTratamiento) { this.nombreTratamiento = nombreTratamiento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getFrecuenciaSolicitudMensual() { return frecuenciaSolicitudMensual; }
    public void setFrecuenciaSolicitudMensual(int frecuenciaSolicitudMensual) { this.frecuenciaSolicitudMensual = frecuenciaSolicitudMensual; }
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public List<Material> getMateriales() {
        return materiales;
    }

    public UUID getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(UUID codCategoria) {
        this.codCategoria = codCategoria;
    }


    @Override
    public String toString(){
        return nombreTratamiento;
    }

    // Métodos para relaciones
    public ResultSet buscarCategoria() throws SQLException {
        String sql = "SELECT c.* FROM CATEGORIA c JOIN TRATAMIENTO t ON c.CODCATEGORIA = t.CATEGORIA_CODCATEGORIA " +
                     "WHERE t.CODTRATAMIENTO = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codTratamiento);
        return pstmt.executeQuery();
    }

    public ResultSet buscarMaterialesNecesarios() throws SQLException {
        String sql = "SELECT m.*, mt.CANTMATERIAL FROM MATERIAL m JOIN MAT_TRAT mt ON m.CODMATERIAL = mt.MATERIAL__CODMATERIAL " +
                     "WHERE mt.TRATAMIENTO__CODTRATAMIENTO = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codTratamiento);
        return pstmt.executeQuery();
    }

    public ResultSet buscarPaquetesQueLoIncluyen() throws SQLException {
        String sql = "SELECT p.* FROM PAQUETE p JOIN PAQ_TRAT pt ON p.CODPAQUETE = pt.PAQUETE__CODPAQUETE " +
                     "WHERE pt.TRATAMIENTO__CODTRATAMIENTO = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codTratamiento);
        return pstmt.executeQuery();
    }

    public ResultSet buscarCitas() throws SQLException {
        String sql = "SELECT * FROM CITA WHERE TRATAMIENTO__CODTRATAMIENTO = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codTratamiento);
        return pstmt.executeQuery();
    }
}
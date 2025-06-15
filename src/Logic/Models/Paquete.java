package Logic.Models;

import Logic.ConnectionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Paquete {
    private UUID codPaquete;
    private String nombrePaquete;
    private double precioPaquete;
    private int duracionTotal;
    private ArrayList<Tratamiento> tratamientos;

    // Constructor para inserción
    public Paquete(String nombrePaquete, double precioPaquete, int duracionTotal) {
        codPaquete = UUID.randomUUID();
        this.nombrePaquete = nombrePaquete;
        this.precioPaquete = precioPaquete;
        this.duracionTotal = duracionTotal;
        tratamientos = new ArrayList<>();
    }

    // Constructor para actualización/eliminación
    public Paquete(UUID codPaquete, String nombrePaquete, double precioPaquete, int duracionTotal) {
        this.codPaquete = codPaquete;
        this.nombrePaquete = nombrePaquete;
        this.precioPaquete = precioPaquete;
        this.duracionTotal = duracionTotal;
        tratamientos = new ArrayList<>();
    }

    // Getters y setters
    public UUID getCodPaquete() { return codPaquete; }
    public String getNombrePaquete() { return nombrePaquete; }
    public void setNombrePaquete(String nombrePaquete) { this.nombrePaquete = nombrePaquete; }
    public double getPrecioPaquete() { return precioPaquete; }
    public void setPrecioPaquete(double precioPaquete) { this.precioPaquete = precioPaquete; }
    public int getDuracionTotal() { return duracionTotal; }
    public void setDuracionTotal(int duracionTotal) { this.duracionTotal = duracionTotal; }

    public ArrayList<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public String toString(){
        return  nombrePaquete;
    }

    // Métodos para relaciones
    public ResultSet buscarTratamientosIncluidos() throws SQLException {
        String sql = "SELECT t.* FROM TRATAMIENTO t JOIN PAQ_TRAT pt ON t.CODTRATAMIENTO = pt.TRATAMIENTO__CODTRATAMIENTO " +
                     "WHERE pt.PAQUETE__CODPAQUETE = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codPaquete);
        return pstmt.executeQuery();
    }

    public ResultSet buscarClientesQueLoCompraron() throws SQLException {
        String sql = "SELECT c.* FROM CLIENTE c JOIN PAQUETEVENDIDO pv ON c.IDCLIENTE = pv.CLIENTE__IDCLIENTE " +
                     "WHERE pv.PAQUETE__CODPAQUETE = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codPaquete);
        return pstmt.executeQuery();
    }
}
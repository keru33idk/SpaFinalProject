package Logic.Models;

import Logic.ConnectionBD;

import java.sql.*;
import java.util.UUID;

public class Cliente {
    private UUID idCliente;
    private String nombreCliente;

    // Constructor para inserción
    public Cliente(String nombreCliente) {
        idCliente = UUID.randomUUID();
        this.nombreCliente = nombreCliente;
    }

    // Constructor para actualización/eliminación
    public Cliente(UUID idCliente, String nombreCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
    }

    // Getters y setters
    public UUID getIdCliente() { return idCliente; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public  String toString(){
        return nombreCliente;
    }

    // Métodos para relaciones
    public ResultSet buscarCitas() throws SQLException {
        String sql = "SELECT * FROM CITA WHERE CLIENTE__IDCLIENTE = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, idCliente);
        return pstmt.executeQuery();
    }

    public ResultSet buscarPaquetesComprados() throws SQLException {
        String sql = "SELECT p.* FROM PAQUETE p JOIN PAQUETEVENDIDO pv ON p.CODPAQUETE = pv.PAQUETE__CODPAQUETE WHERE pv.CLIENTE__IDCLIENTE = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, idCliente);
        return pstmt.executeQuery();
    }
}
package Logic.Models;

import Logic.ConnectionBD;

import java.sql.*;
import java.util.UUID;

public class Cita {
    private UUID codSolicitud;
    private UUID codTratamiento;
    private UUID codCategoria;
    private UUID idCliente;
    private java.sql.Date fecha;
    private java.sql.Time horaCita;
    private String observaciones;

    // Constructor para inserción
    public Cita(UUID codTratamiento, UUID codCategoria, UUID idCliente, java.sql.Date fecha, java.sql.Time horaCita, String observaciones) {
        codSolicitud = UUID.randomUUID();
        this.codTratamiento = codTratamiento;
        this.codCategoria = codCategoria;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.horaCita = horaCita;
        this.observaciones = observaciones;

    }

    // Constructor para actualización/eliminación
    public Cita(UUID codSolicitud, UUID codTratamiento, UUID codCategoria, UUID idCliente, java.sql.Date fecha, java.sql.Time horaCita, String observaciones) {
        this.codSolicitud = codSolicitud;
        this.codTratamiento = codTratamiento;
        this.codCategoria = codCategoria;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.horaCita = horaCita;
        this.observaciones = observaciones;
    }

    // Getters y setters
    public UUID getCodSolicitud() { return codSolicitud; }
    public UUID getCodTratamiento() { return codTratamiento; }
    public void setCodTratamiento(UUID codTratamiento) { this.codTratamiento = codTratamiento; }
    public UUID getIdCliente() { return idCliente; }
    public void setIdCliente(UUID idCliente) { this.idCliente = idCliente; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public UUID getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(UUID codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Time getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(Time horaCita) {
        this.horaCita = horaCita;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Métodos para relaciones
    public ResultSet buscarTratamiento() throws SQLException {
        String sql = "SELECT * FROM TRATAMIENTO WHERE CODTRATAMIENTO = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codTratamiento);
        return pstmt.executeQuery();
    }

    public ResultSet buscarCliente() throws SQLException {
        String sql = "SELECT * FROM CLIENTE WHERE IDCLIENTE = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, idCliente);
        return pstmt.executeQuery();
    }
}
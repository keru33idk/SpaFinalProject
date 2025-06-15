package Logic.Models;

import java.sql.*;
import java.util.UUID;
import java.sql.Timestamp;

public class PaqueteVendido {
    private UUID idCliente;
    private UUID codPaquete;
    private Timestamp fechaCompra;
    private Date fechaInicio;
    private Date fechaFin;

    // Constructor
    public PaqueteVendido(UUID idCliente, UUID codPaquete, Timestamp fechaCompra, Date fechaInicio, Date fechaFin) {
        this.idCliente = idCliente;
        this.codPaquete = codPaquete;
        this.fechaCompra = fechaCompra;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y setters
    public UUID getIdCliente() { return idCliente; }
    public UUID getCodPaquete() { return codPaquete; }
    public Timestamp getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(Timestamp fechaCompra) { this.fechaCompra = fechaCompra; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}
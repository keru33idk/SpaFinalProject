package Logic.services;

import Logic.ConnectionBD;
import Logic.Models.Cliente;
import utils.ManejadorExcepcionesSQL;

import java.sql.*;
import java.util.UUID;

public class PaqueteVendidoService {
    
    public  void insertarPaqueteVendido(
        UUID clienteId,
        UUID paqueteId,
        java.sql.Timestamp fechaCompra, 
        java.sql.Date fechaInicio, 
        java.sql.Date fechaFin
    ) throws SQLException {
        String sql = "call insertar_paquete_vendido(?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, clienteId, Types.OTHER);
            cstmt.setObject(2, paqueteId, Types.OTHER);
            cstmt.setTimestamp(3, fechaCompra);
            cstmt.setDate(4, fechaInicio);
            cstmt.setDate(5, fechaFin);
            cstmt.execute();
        }
    }
    
    public  void actualizarPaqueteVendido(
        UUID clienteId,
        UUID paqueteId,
        java.sql.Timestamp fechaCompra, 
        java.sql.Date fechaInicio, 
        java.sql.Date fechaFin
    ) throws SQLException {
        String sql = "call actualizar_paquete_vendido(?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, clienteId, Types.OTHER);
            cstmt.setObject(2, paqueteId, Types.OTHER);
            cstmt.setTimestamp(3, fechaCompra);
            cstmt.setDate(4, fechaInicio);
            cstmt.setDate(5, fechaFin);
            cstmt.execute();
        }
    }
    
    public  void eliminarPaqueteVendido(UUID clienteId, UUID paqueteId, Date fechaInicio) throws SQLException {
        String sql = "call eliminar_paquete_vendido(?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, clienteId, Types.OTHER);
            cstmt.setObject(2, paqueteId, Types.OTHER);
            cstmt.setDate(3, fechaInicio);
            cstmt.execute();
        }
    }

    public ResultSet PaquetesVendidosReporte(int mes) throws SQLException {
        String sql = "SELECT  * FROM reporte_vetas_mes_paquetes(?)";
        ResultSet rs = null;
        try {
            Connection conn = ConnectionBD.getConn();
            PreparedStatement cstmt = conn.prepareStatement(sql);

            cstmt.setInt(1, mes);
            rs = cstmt.executeQuery();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
        }
        return rs;
    }
    public ResultSet paquetesDeUnCliente(Cliente cliente){
        ResultSet rs = null;
        String sql = "SELECT paquete.nombrepaquete,\n" +
                "paquetevendido.fechainicio,\n" +
                "paquetevendido.fechafin,\n" +
                "paquete.preciopaquete AS precio\n" +
                "FROM paquete JOIN paquetevendido ON paquete.codpaquete = paquetevendido.paquete__codpaquete\n" +
                "JOIN cliente ON paquetevendido.cliente__idcliente = cliente.idcliente\n" +
                "WHERE cliente__idcliente = ?;";
        try {
            if(ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
            pstmt.setObject(1,cliente.getIdCliente(), Types.OTHER);
            rs =  pstmt.executeQuery();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
        }
        return rs;
    }
}
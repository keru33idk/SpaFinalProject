package Logic.services;

import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;

import java.sql.*;
import java.util.UUID;

public class CitaService {
    
    public void insertarCita(UUID id, UUID tratamientoId, UUID categoriaId, UUID clienteId, Date fecha, Time horacita, String observaciones) throws SQLException {
        String sql = " call insertar_cita(?, ?, ?, ?, ?, ?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setObject(2, tratamientoId, Types.OTHER);
            cstmt.setObject(3, categoriaId, Types.OTHER);
            cstmt.setObject(4, clienteId, Types.OTHER);
            cstmt.setDate(5, fecha);
            cstmt.setTime(6, horacita);
            cstmt.setString(7, observaciones);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void actualizarCita(UUID id, UUID tratamientoId, UUID categoriaId, UUID clienteId, Date fecha, Time horacita, String observaciones) throws SQLException {
        String sql = " call actualizar_cita(?, ?, ?, ?, ?, ?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setObject(2, tratamientoId, Types.OTHER);
            cstmt.setObject(3, categoriaId, Types.OTHER);
            cstmt.setObject(4, clienteId, Types.OTHER);
            cstmt.setDate(5, fecha);
            cstmt.setTime(6, horacita);
            cstmt.setString(7, observaciones);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void eliminarCita(UUID id) throws SQLException {
        String sql = "call eliminar_cita(?)";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, id, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

    public ResultSet tratamientoVendidosReporte(int mes) throws SQLException {
        String sql = "SELECT  * FROM reporte_vetas_tratamientos_mes(?)";
        ResultSet rs = null;
        try{
            Connection conn = ConnectionBD.getConn();
            PreparedStatement cstmt = conn.prepareStatement(sql);

            cstmt.setInt(1, mes);
            rs = cstmt.executeQuery();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
        return rs;
    }
}
package Logic.services;

import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;

import java.sql.*;
import java.util.UUID;

public class ClienteService {
    
    public void insertarCliente(UUID id, String nombre) throws SQLException {
        String sql = " call insertar_cliente(?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nombre);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void actualizarCliente(UUID id, String nuevoNombre) throws SQLException {
        String sql = " call actualizar_cliente(?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nuevoNombre);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void eliminarCliente(UUID id) throws SQLException {
        String sql = " call eliminar_cliente(?) ";
        
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
}
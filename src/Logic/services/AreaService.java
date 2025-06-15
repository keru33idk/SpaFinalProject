package Logic.services;

import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;

import java.sql.*;

public class AreaService {
    
    public void insertarArea(String nombre, int cantPersonal) throws SQLException {
        String sql = " call insertar_area(?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, nombre);
            cstmt.setInt(2, cantPersonal);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void actualizarArea(String nombre, int nuevaCantPersonal, String area) throws SQLException {
        String sql = " call actualizar_area(?, ?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, area);
            cstmt.setInt(3, nuevaCantPersonal);
            cstmt.setString(2, nombre);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void eliminarArea(String nombre) throws SQLException {
        String sql = " call eliminar_area(?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setString(1, nombre);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
}
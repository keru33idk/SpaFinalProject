package Logic.services;

import Logic.ConnectionBD;
import Logic.Models.Material;
import Logic.Models.Paquete;
import Logic.Models.Tratamiento;
import utils.ManejadorExcepcionesSQL;

import java.math.BigDecimal;
import java.sql.*;
import java.util.UUID;

public class PaqueteService {
    
    public  void insertarPaquete(UUID id, String nombre, double precio, int duracion) throws SQLException {
        String sql = " call insertar_paquete(?, ?, ?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nombre);
            cstmt.setBigDecimal(3, new BigDecimal(precio));
            cstmt.setInt(4, duracion);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public  void actualizarPaquete(UUID id, String nombre, double precio, int duracion) throws SQLException {
        String sql = " call actualizar_paquete(?, ?, ?, ?) ";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nombre);
            cstmt.setBigDecimal(3, new BigDecimal(precio));
            cstmt.setInt(4, duracion);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public  void eliminarPaquete(UUID id) throws SQLException {
        String sql = " call eliminar_paquete(?) ";
        
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

    public  void añadirPaquetesTratamiento(Paquete paquete) throws SQLException {
        String sql = "INSERT INTO paq_trat VALUES(?, ?, ?)";
        try {
            for(Tratamiento m : paquete.getTratamientos()){
                if(ConnectionBD.getConn().isClosed())
                    ConnectionBD.connect();
                PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
                pstmt.setObject(1, m.getCodTratamiento(), Types.OTHER);
                pstmt.setObject(2, m.getCodCategoria(), Types.OTHER);
                pstmt.setObject(3, paquete.getCodPaquete(), Types.OTHER);
                pstmt.executeUpdate();
            }
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

    public  void eliminarPaquetesTratamiento(Paquete paquete) throws SQLException {
        String sql = "DELETE FROM paq_trat WHERE paquete__codpaquete = ?";
        try {
            if(ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
            pstmt.setObject(1, paquete.getCodPaquete(), Types.OTHER);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
}
package Logic.services;

import Logic.ConnectionBD;
import Logic.Models.Cliente;
import Logic.Models.Material;
import Logic.Models.Tratamiento;
import utils.ManejadorExcepcionesSQL;
import java.math.BigDecimal;


import java.sql.*;
import java.util.UUID;

public class TratamientoService {
    
    public  void insertarTratamiento(UUID id,
        String nombre, 
        String descripcion, 
        int frecuencia, 
        int duracion, 
        double precio, 
        UUID categoriaId
    ) throws SQLException {
        String sql = " call insertar_tratamiento(?, ?, ?, ?, ?, ?, ?) ";

        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nombre);
            cstmt.setString(3, descripcion);
            cstmt.setInt(4, frecuencia);
            cstmt.setInt(5, duracion);
            cstmt.setBigDecimal(6, new BigDecimal(precio));
            cstmt.setObject(7, categoriaId, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public  void actualizarTratamiento(
        UUID id,
        String nombre, 
        String descripcion, 
        int frecuencia, 
        int duracion, 
        double precio, 
        UUID categoriaId
    ) throws SQLException {
        String sql = " call actualizar_tratamiento(?, ?, ?, ?, ?, ?, ?) ";

        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();

        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nombre);
            cstmt.setString(3, descripcion);
            cstmt.setInt(4, frecuencia);
            cstmt.setInt(5, duracion);
            cstmt.setBigDecimal(6, new BigDecimal(precio));
            cstmt.setObject(7, categoriaId, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public  void eliminarTratamiento(UUID id, UUID cat) throws SQLException {
        String sql = " call eliminar_tratamiento(?, ?) ";
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setObject(2, cat, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

    public  void añadirMaterialesTratamiento(Tratamiento trat) throws SQLException {
        String sql = "INSERT INTO mat_trat VALUES(?, ?, ?)";
        try {
            for(Material m : trat.getMateriales()){
                if(ConnectionBD.getConn().isClosed())
                    ConnectionBD.connect();
                 PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
                 pstmt.setObject(1, trat.getCodTratamiento(), Types.OTHER);
                 pstmt.setObject(2, trat.getCodCategoria(), Types.OTHER);
                 pstmt.setObject(3, m.getCodMaterial(), Types.OTHER);
                 pstmt.executeUpdate();
            }
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    public  void eliminarMaterialesTratamiento(Tratamiento trat) throws SQLException {
        String sql = "DELETE FROM mat_trat WHERE tratamiento__codtratamiento = ? AND tratamiento__categoria_codcategoria = ?";
        try {
                if(ConnectionBD.getConn().isClosed())
                    ConnectionBD.connect();
                PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
                pstmt.setObject(1, trat.getCodTratamiento(), Types.OTHER);
                pstmt.setObject(2, trat.getCodCategoria(), Types.OTHER);
                pstmt.executeUpdate();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

    public ResultSet tratamientosMasSolicitados(){
        ResultSet rs = null;
        String sql = "SELECT tratamiento.nombretratamiento, categoria.nombrecategoria\n" +
                "FROM tratamiento JOIN categoria ON tratamiento.categoria_codcategoria = categoria.codcategoria\n" +
                "ORDER BY tratamiento.frecuenciadesolicitudmensual DESC\n" +
                "LIMIT 3;";
        try {
            if(ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);

            rs =  pstmt.executeQuery();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
        }
        return rs;
    }

    public ResultSet tratamientosDeUnCliente(Cliente cliente){
        ResultSet rs = null;
        String sql = "SELECT tratamiento.nombretratamiento AS nombreTratamiento,\n" +
                "categoria.nombrecategoria AS categoria,\n" +
                "cita.fecha, tratamiento.precio\n" +
                "FROM cita \n" +
                "JOIN tratamiento ON cita.tratamiento__codtratamiento = tratamiento.codtratamiento \n" +
                "AND cita.tratamiento__categoria_codcategoria = tratamiento.categoria_codcategoria\n" +
                "JOIN categoria ON tratamiento.categoria_codcategoria = categoria.codcategoria\n" +
                "JOIN cliente ON cita.cliente__idcliente = cliente.idcliente\n" +
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
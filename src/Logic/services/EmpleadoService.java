package Logic.services;

import Logic.ConnectionBD;
import Logic.Models.Cliente;
import utils.ManejadorExcepcionesSQL;

import java.sql.*;
import java.util.UUID;

public class EmpleadoService {
    
    public void insertarEmpleado(UUID id, String nombre, String especialidad,
                                        int horasSemanales, String dni, String direccion, String telefono,
                                        String distrito, String area, UUID tratamiento, UUID categoria) throws SQLException
    {
        String sql = "call insertar_empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nombre);
            cstmt.setString(3, especialidad);
            cstmt.setInt(4, horasSemanales);
            cstmt.setString(5, dni);
            cstmt.setString(6, direccion);
            cstmt.setString(7, telefono);
            cstmt.setString(8, distrito);
            cstmt.setString(9, area);
            cstmt.setObject(10, tratamiento, Types.OTHER);
            cstmt.setObject(11, categoria, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void actualizarEmpleado(
        UUID id,
        String nombre, 
        String especialidad, 
        int horasSemanales, 
        String dni, 
        String direccion, 
        String telefono, 
        String distrito,
        String area, UUID tratamiento, UUID categoria
    ) throws SQLException {
        String sql = "call actualizar_empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            
            cstmt.setObject(1, id, Types.OTHER);
            cstmt.setString(2, nombre);
            cstmt.setString(3, especialidad);
            cstmt.setInt(4, horasSemanales);
            cstmt.setString(5, dni);
            cstmt.setString(6, direccion);
            cstmt.setString(7, telefono);
            cstmt.setString(8, distrito);
            cstmt.setString(9, area);
            cstmt.setObject(10, tratamiento, Types.OTHER);
            cstmt.setObject(11, categoria, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    
    public void eliminarEmpleado(UUID id) throws SQLException {
        String sql = "call eliminar_empleado(?)";
        
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

    public ResultSet EmpleadosDeUnCliente(Cliente cliente){
        ResultSet rs = null;
        String sql = "SELECT empleado.nombreempleado,\n" +
                "empleado.dni,\n" +
                "empleado.especialidad,\n" +
                "empleado.telefono\n" +
                "FROM empleado JOIN cita ON empleado.tratamiento__codtratamiento = cita.tratamiento__codtratamiento \n" +
                "AND empleado.tratamiento__categoria_codcategoria = cita.tratamiento__categoria_codcategoria JOIN cliente ON cita.cliente__idcliente = cliente.idcliente\n" +
                "WHERE cliente__idcliente = ?\n" +
                "GROUP BY empleado.nombreempleado, empleado.dni,empleado.especialidad,empleado.telefono\n" +
                "\n" +
                "UNION\n" +
                "\n" +
                "SELECT empleado.nombreempleado,\n" +
                "empleado.dni,\n" +
                "empleado.especialidad,\n" +
                "empleado.telefono\n" +
                "FROM empleado JOIN paq_trat ON empleado.tratamiento__codtratamiento = paq_trat.tratamiento__codtratamiento \n" +
                "AND empleado.tratamiento__categoria_codcategoria = paq_trat.tratamiento__categoria_codcategoria JOIN paquetevendido ON paq_trat.paquete__codpaquete = paquetevendido.paquete__codpaquete JOIN cliente ON paquetevendido.cliente__idcliente = cliente.idcliente\n" +
                "WHERE cliente__idcliente = ?\n" +
                "GROUP BY empleado.nombreempleado, empleado.dni,empleado.especialidad,empleado.telefono";
        try {
            if(ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
            pstmt.setObject(1,cliente.getIdCliente(), Types.OTHER);
            pstmt.setObject(2,cliente.getIdCliente(), Types.OTHER);
            rs =  pstmt.executeQuery();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
        }
        return rs;
    }
}
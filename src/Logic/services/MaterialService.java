package Logic.services;

import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;
import utils.TratamientoDiscrepanciaReporte;

import java.sql.*;
import java.util.UUID;

public class MaterialService {
    
    public void insertarMaterial(UUID id, String nombre) throws SQLException {
        String sql = "call insertar_material(?, ?) ";
        
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
    
    public void actualizarMaterial(UUID id, String nuevoNombre) throws SQLException {
        String sql = " call actualizar_material(?, ?) ";
        
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
    
    public void eliminarMaterial(UUID id) throws SQLException {
        String sql = " call eliminar_material(?) ";
        
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
    public ResultSet buscarTratamientos(TratamientoDiscrepanciaReporte t) throws SQLException {
        String sql = "SELECT tratamiento.codtratamiento, \n" +
                "categoria.codcategoria, \n" +
                "tratamiento.nombretratamiento, material.nombrematerial,\n" +
                "COUNT(*) AS realizados, \n" +
                "SUM(CASE WHEN cita.fecha > CURRENT_DATE THEN 1 ELSE 0 END) AS planificados\n" +
                "\n" +
                "FROM cita JOIN tratamiento ON cita.tratamiento__codtratamiento = tratamiento.codtratamiento \n" +
                "JOIN categoria ON tratamiento.categoria_codcategoria = categoria.codcategoria\n" +
                "JOIN mat_trat ON mat_trat.tratamiento__codtratamiento = tratamiento.codtratamiento \n" +
                "AND mat_trat.tratamiento__categoria_codcategoria = tratamiento.categoria_codcategoria\n" +
                "JOIN material ON mat_trat.material__codmaterial = material.codmaterial\n" +
                "WHERE EXTRACT(MONTH from fecha) = EXTRACT(MONTH from CURRENT_DATE)\n" +
                "AND cita.tratamiento__codtratamiento = ? AND cita.tratamiento__categoria_codcategoria = ?\n" +
                "GROUP BY tratamiento.codtratamiento, tratamiento.nombretratamiento, categoria.codcategoria, \n" +
                "material.nombrematerial;\n";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, t.getCodTratamiento());
        pstmt.setObject(2, t.getCodCategoria());
        return pstmt.executeQuery();
    }
}
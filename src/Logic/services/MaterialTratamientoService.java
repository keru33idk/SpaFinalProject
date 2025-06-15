package Logic.services;

import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

public class MaterialTratamientoService {

    public void insertarMaterialTratamiento(UUID idMat, UUID idTrat, UUID idCat) throws SQLException {
        String sql = " call insertar_mat_trat(?, ?, ?) ";

        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, idTrat, Types.OTHER);
            cstmt.setObject(2, idCat, Types.OTHER);
            cstmt.setObject(3, idMat, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

    public void eliminarMaterialTratamiento(UUID idMat, UUID idTrat, UUID idCat) throws SQLException {
        String sql = " call eliminar_mat_trat(?,?,?) ";

        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, idTrat, Types.OTHER);
            cstmt.setObject(2, idCat, Types.OTHER);
            cstmt.setObject(3, idMat, Types.OTHER);

            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
}

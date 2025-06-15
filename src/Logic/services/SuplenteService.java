package Logic.services;

import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

public class SuplenteService {

    public  void insertarSuplente(UUID suplente,UUID fijo) throws SQLException {
        String sql = " call insertar_suplente(?, ?) ";

        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, suplente, Types.OTHER);
            cstmt.setObject(2, fijo, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    public  void eliminarSuplente(UUID suplente,UUID fijo) throws SQLException {
        String sql = " call eliminar_suplente(?, ?) ";

        try (Connection conn = ConnectionBD.getConn();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setObject(1, suplente, Types.OTHER);
            cstmt.setObject(2, fijo, Types.OTHER);
            cstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

}

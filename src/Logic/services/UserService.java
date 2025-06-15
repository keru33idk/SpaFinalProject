package Logic.services;

import Logic.ConnectionBD;
import Logic.Models.Material;
import Logic.Models.Tratamiento;
import utils.ManejadorExcepcionesSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class UserService {

    public static void añadirUser(String nombre, String pass, String rol) throws SQLException {
        String sql = "INSERT INTO users (username, password_hash, role)\n" +
                "VALUES (\n" +
                "    ?,\n" +
                "    crypt(?, gen_salt('bf')),\n" +
                "    ?\n" +
                ");";
        try {
                if(ConnectionBD.getConn().isClosed())
                    ConnectionBD.connect();
                PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
                pstmt.setString(1, nombre);
                pstmt.setString(2, pass);
                pstmt.setString(3, rol);
                pstmt.execute();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

    public static void UpdateUser(int id, String nombre, String pass, String rol) throws SQLException {
        String sql = "UPDATE users SET " +
                "username = ?, " +
                "password_hash = crypt(?, gen_salt('bf')), " +
                "role = ?\n" +
                "WHERE id = ?";
        try {
            if(ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, pass);
            pstmt.setString(3, rol);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }
    public static void DeleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?;";
        try {
            if(ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            throw new SQLException(e);
        }
    }

    public static String getUser(String nombre, String pass) throws SQLException {
        String role = "";
        String sql = "SELECT * FROM users \n" +
                "WHERE username = ? \n" +
                "AND password_hash = crypt(?, password_hash);";
        try {
            if(ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                role = rs.getString(4);
            }
        }
        catch (SQLException e){
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
        }
        return role;
    }
}

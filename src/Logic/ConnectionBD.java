package Logic;

import utils.ManejadorExcepcionesSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String url = "jdbc:postgresql://localhost:1234/SPA";
    private static String user = "postgres";
    private static String password = "12345678";
    private static Connection conn;
    private static String userSimple;
    private static String role;

    public static Connection connect() {
        conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado a PostgreSQL correctamente");
        } catch (SQLException e) {
            ManejadorExcepcionesSQL.mostrarErrorSQL(e);
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        ConnectionBD.role = role;
    }

    public static void setUser(String Nuser){
        userSimple = Nuser;
    }

    public static Connection getConn() {
        return conn;
    }

    public static String user() {
        return userSimple;
    }
}

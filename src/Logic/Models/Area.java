package Logic.Models;

import Logic.ConnectionBD;

import java.sql.*;

public class Area {
    private String area;
    private int cantPersonalFijo;

    // Constructor para inserción
    public Area(String area, int cantPersonalFijo) {
        this.area = area;
        this.cantPersonalFijo = cantPersonalFijo;
    }

    // Getters y setters
    public String getArea() { return area; }
    public int getCantPersonalFijo() { return cantPersonalFijo; }
    public void setCantPersonalFijo(int cantPersonalFijo) { this.cantPersonalFijo = cantPersonalFijo; }

    public String toString(){
        return area;
    }

    // Método para buscar empleados de esta área
    public ResultSet buscarEmpleados() throws SQLException {
        String sql = "SELECT * FROM EMPLEADO WHERE AREA__AREA = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setString(1, area);
        return pstmt.executeQuery();
    }
}
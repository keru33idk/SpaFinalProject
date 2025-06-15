package Logic.Models;

import Logic.ConnectionBD;

import java.sql.*;
import java.util.UUID;

public class Categoria {
    private UUID codCategoria;
    private String nombreCategoria;

    // Constructor para inserción (sin clave primaria)
    public Categoria(String nombreCategoria) {
        codCategoria = UUID.randomUUID();
        this.nombreCategoria = nombreCategoria;
    }

    // Constructor para actualización/eliminación (con clave primaria)
    public Categoria(UUID codCategoria, String nombreCategoria) {
        this.codCategoria = codCategoria;
        this.nombreCategoria = nombreCategoria;
    }


    public String toString(){
        return nombreCategoria;
    }

    // Getters y setters
    public UUID getCodCategoria() { return codCategoria; }
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }


    // Método para buscar tratamientos de esta categoría
    public ResultSet buscarTratamientos() throws SQLException {
        String sql = "SELECT * FROM TRATAMIENTO WHERE CATEGORIA_CODCATEGORIA = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codCategoria);
        return pstmt.executeQuery();
    }
}
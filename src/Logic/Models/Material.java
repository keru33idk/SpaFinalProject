package Logic.Models;

import Logic.ConnectionBD;

import java.sql.*;
import java.util.UUID;

public class Material {
    private UUID codMaterial;
    private String nombreMaterial;

    // Constructor para inserción
    public Material(String nombreMaterial) {
        codMaterial = UUID.randomUUID();
        this.nombreMaterial = nombreMaterial;
    }

    // Constructor para actualización/eliminación
    public Material(UUID codMaterial, String nombreMaterial) {
        this.codMaterial = codMaterial;
        this.nombreMaterial = nombreMaterial;
    }

    // Getters y setters
    public UUID getCodMaterial() { return codMaterial; }
    public String getNombreMaterial() { return nombreMaterial; }
    public void setNombreMaterial(String nombreMaterial) { this.nombreMaterial = nombreMaterial; }

    public String toString(){
        return nombreMaterial;
    }


    // Método para buscar tratamientos que usan este material
    public ResultSet buscarTratamientos() throws SQLException {
        String sql = "SELECT t.* FROM TRATAMIENTO t JOIN MAT_TRAT mt ON t.CODTRATAMIENTO = mt.TRATAMIENTO__CODTRATAMIENTO WHERE mt.MATERIAL__CODMATERIAL = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, codMaterial);
        return pstmt.executeQuery();
    }
}
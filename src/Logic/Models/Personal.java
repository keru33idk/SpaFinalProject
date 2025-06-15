package Logic.Models;

import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Personal {
    private UUID idEmpleado;
    private String nombreEmpleado;
    private String especialidad;
    private int horasTrabajoSemanal;
    private String dni;
    private String direccion;
    private String telefono;
    private String distrito;
    private String area;
    private UUID tratamiento;
    private UUID categoriaTratamiento;
    private boolean isEmpleado; //suplente

    private ArrayList<Personal> suplentes;

    // Constructor para inserción
    public Personal(String nombreEmpleado, String especialidad, int horasTrabajoSemanal,
                    String dni, String direccion, String telefono, String distrito) {
        idEmpleado = UUID.randomUUID();
        this.nombreEmpleado = nombreEmpleado;
        this.especialidad = especialidad;
        this.horasTrabajoSemanal = horasTrabajoSemanal;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.distrito = distrito;
        isEmpleado = false;
        suplentes = new ArrayList<>();
    }

    // Constructor para actualización/eliminación
    public Personal(UUID idEmpleado, String nombreEmpleado, String especialidad,
                    int horasTrabajoSemanal, String dni, String direccion,
                    String telefono, String distrito) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.especialidad = especialidad;
        this.horasTrabajoSemanal = horasTrabajoSemanal;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.distrito = distrito;
        isEmpleado = false;
        suplentes = new ArrayList<>();
    }

    public Personal(String nombre, String especialidad, int horas, String dni, String direcc, String telefono, String distrito, boolean isSuplente, String area, UUID tratamiento, UUID codCategoria) {
        this.idEmpleado = UUID.randomUUID();
        this.nombreEmpleado = nombre;
        this.especialidad = especialidad;
        this.horasTrabajoSemanal = horas;
        this.dni = dni;
        this.direccion = direcc;
        this.telefono = telefono;
        this.distrito = distrito;
        this.isEmpleado = isSuplente;
        this.area = area;
        this.tratamiento = tratamiento;
        this.categoriaTratamiento = codCategoria;
        suplentes = new ArrayList<>();
    }
    public Personal(UUID idEmpleado, String nombre, String especialidad, int horas, String dni, String direcc, String telefono, String distrito, boolean isSuplente, String area, UUID tratamiento, UUID codCategoria) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombre;
        this.especialidad = especialidad;
        this.horasTrabajoSemanal = horas;
        this.dni = dni;
        this.direccion = direcc;
        this.telefono = telefono;
        this.distrito = distrito;
        this.isEmpleado = isSuplente;
        this.area = area;
        this.tratamiento = tratamiento;
        this.categoriaTratamiento = codCategoria;
        suplentes = new ArrayList<>();
    }

    // Getters y setters
    public UUID getIdEmpleado() { return idEmpleado; }
    public String getNombreEmpleado() { return nombreEmpleado; }
    public void setNombreEmpleado(String nombreEmpleado) { this.nombreEmpleado = nombreEmpleado; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public int getHorasTrabajoSemanal() { return horasTrabajoSemanal; }
    public void setHorasTrabajoSemanal(int horasTrabajoSemanal) { this.horasTrabajoSemanal = horasTrabajoSemanal; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public boolean isEmpleado() {
        return isEmpleado;
    }

    public void setEmpleado(boolean empleado) {
        isEmpleado = empleado;
    }

    public ArrayList<Personal> getSuplentes() {
        return suplentes;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public UUID getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(UUID tratamiento) {
        this.tratamiento = tratamiento;
    }

    public UUID getCategoriaTratamiento() {
        return categoriaTratamiento;
    }

    public void setCategoriaTratamiento(UUID categoriaTratamiento) {
        this.categoriaTratamiento = categoriaTratamiento;
    }

    @Override
    public String toString(){
        return nombreEmpleado;
    }
    // Métodos para relaciones
    public void asignarArea(String area) throws SQLException {
        String sql = "UPDATE EMPLEADO SET AREA__AREA = ? WHERE IDEMPLEADO = ?";
        try (PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql)) {
            pstmt.setString(1, area);
            pstmt.setObject(2, idEmpleado);
            pstmt.executeUpdate();
        }
    }

    public ResultSet buscarArea() throws SQLException {
        String sql = "SELECT a.* FROM AREA a JOIN EMPLEADO e ON a.AREA = e.AREA__AREA WHERE e.IDEMPLEADO = ?";
        PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
        pstmt.setObject(1, idEmpleado);
        return pstmt.executeQuery();
    }
}
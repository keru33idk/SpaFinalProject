package Logic;

import Logic.Models.*;
import Logic.services.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class ControllerSpa {
    private ArrayList<Personal> empleados = new ArrayList<Personal>();
    private ArrayList<Area> areas = new ArrayList<>();
    private ArrayList<Categoria> categorias = new ArrayList<>();
    private ArrayList<Cita> citas = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Material> materiales = new ArrayList<>();
    private ArrayList<Paquete> paquetes = new ArrayList<>();
    private ArrayList<Tratamiento> tratamientos = new ArrayList<>();
    private ArrayList<PaqueteVendido> paqueteVendidos = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();


    public ArrayList<Personal> getEmpleados() {
        return empleados;
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }

    public ArrayList<Cita> getCitas() {
        return citas;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Material> getMateriales() {
        return materiales;
    }

    public ArrayList<Paquete> getPaquetes() {
        return paquetes;
    }

    public ArrayList<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public ArrayList<PaqueteVendido> getPaqueteVendidos() {
        return paqueteVendidos;
    }

    public void cargarAreas() throws SQLException {
        areas.clear();
        String sql = "SELECT area, cantpersonalfijo FROM area";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Area area = new Area(
                        rs.getString("area"),
                        rs.getInt("cantpersonalfijo")
                );
                areas.add(area);
            }
        }
    }

    public void cargarCategorias() throws SQLException {
        categorias.clear();
        String sql = "SELECT * FROM categoria";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        (UUID) rs.getObject(1),
                        rs.getString("nombrecategoria")
                );
                categorias.add(categoria);
            }
        }
    }

    public void cargarClientes() throws SQLException {
        clientes.clear();
        String sql = "SELECT * FROM cliente ORDER BY nombrecliente ASC";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        (UUID) rs.getObject(1),
                        rs.getString("nombrecliente")
                );
                clientes.add(cliente);
            }
        }
    }

    public void cargarMateriales() throws SQLException {
        materiales.clear();
        String sql = "SELECT * FROM material";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Material material = new Material(
                        (UUID) rs.getObject(1),
                        rs.getString("nombrematerial")
                );
                materiales.add(material);
            }
        }
    }

    public void cargarPaquetes() throws SQLException {
        paquetes.clear();
        String sql = "SELECT * FROM paquete";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Paquete paquete = new Paquete(
                        (UUID) rs.getObject(1),
                        rs.getString("nombrepaquete"),
                        rs.getDouble("preciopaquete"),
                        rs.getInt("duraciontotal")
                );
                paquete.getTratamientos().addAll(cargarTratamientosPaquete(paquete.getCodPaquete()));
                paquetes.add(paquete);
            }
        }
    }

    public void cargarTratamientos() throws SQLException {
        tratamientos.clear();
        String sql = "SELECT * FROM tratamiento";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento(
                        (UUID) rs.getObject(1),
                        rs.getString("nombretratamiento"),
                        rs.getString("descripcion"),
                        rs.getInt("frecuenciadesolicitudmensual"),
                        rs.getInt("duracion"),
                        rs.getDouble("precio"),
                    (UUID) rs.getObject(7)
                );
                tratamientos.add(tratamiento);
            }
        }

        for(Tratamiento t : tratamientos) {
            String nsql = "SELECT material__codmaterial FROM mat_trat WHERE tratamiento__codtratamiento = ? AND tratamiento__categoria_codcategoria = ?";
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(nsql);
            pstmt.setObject(1, t.getCodTratamiento());
            pstmt.setObject(2, t.getCodCategoria());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                UUID idMAt = (UUID) rs.getObject("material__codmaterial");
                for(Material m : materiales){
                    if(m.getCodMaterial().equals(idMAt)){
                        t.getMateriales().add(m);
                        System.out.println(t.getNombreTratamiento() +" : "+ m.getNombreMaterial());
                    }

                }
            }
        }
    }

    public void cargarEmpleados() throws SQLException {
        empleados.clear();
        String sql = "SELECT * FROM empleado ORDER BY nombreempleado ASC";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Personal empleado = new Personal(
                        (UUID) rs.getObject(1),
                        rs.getString("nombreempleado"),
                        rs.getString("especialidad"),
                        rs.getInt("horastrabajosemanal"),
                        rs.getString("dni"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("distrito"),
                        rs.getBoolean("issuplente"),
                        rs.getString(9),
                        (UUID) rs.getObject(10),
                        (UUID) rs.getObject(11)
                );
                empleados.add(empleado);
            }


        }

        for(Personal p : empleados) {
            String nsql = "SELECT empleado_suplente_idempleado FROM suplente WHERE empleado_fijo_idempleado = ?";
            PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(nsql);
            pstmt.setObject(1, p.getIdEmpleado(), Types.OTHER);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                UUID idSuplente = (UUID) rs.getObject("empleado_suplente_idempleado");
                for(Personal m : empleados){
                    if(m.getIdEmpleado().equals(idSuplente)){
                        p.getSuplentes().add(m);
                    }

                }
            }
        }
    }

    public void cargarCitas() throws SQLException {
        citas.clear();
        String sql = "SELECT * FROM cita ORDER BY fecha ASC";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cita cita = new Cita((UUID) rs.getObject(1),
                        (UUID) rs.getObject(2),
                        (UUID) rs.getObject(3),
                        (UUID) rs.getObject(4),
                        rs.getDate("fecha"),
                        rs.getTime("horacita"),
                        rs.getString("observaciones")
                );
                citas.add(cita);
            }
        }
    }
    public void cargarPaquetesVendidos() throws SQLException {
        paqueteVendidos.clear();
        String sql = "SELECT * FROM paquetevendido";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PaqueteVendido paqVend = new PaqueteVendido((UUID) rs.getObject(1),
                        (UUID) rs.getObject(2),
                        rs.getTimestamp(3),
                        rs.getDate(4),
                        rs.getDate(5)
                );
                paqueteVendidos.add(paqVend);
            }
        }
    }

    public void cargarUsers() throws SQLException {
        users.clear();
        String sql = "SELECT * FROM users";
        try (Statement stmt = ConnectionBD.getConn().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(4)
                );
                users.add(user);
            }
        }
    }

    public void CRUDEliminarSelector(int indice, String name) throws SQLException {
        switch (name){
            case "Area":
                Area a = getAreas().get(indice);
                ServicesLocator.areaService().eliminarArea(a.getArea());
                break;
            case "Categoria":
                Categoria c = getCategorias().get(indice);
                ServicesLocator.categoriaService().eliminarCategoria(c.getCodCategoria());
                break;
            case "Cita":
                Cita ci = getCitas().get(indice);
                ServicesLocator.citaService().eliminarCita(ci.getCodSolicitud());
                break;
            case "Cliente":
                Cliente cl = getClientes().get(indice);
                ServicesLocator.clienteService().eliminarCliente(cl.getIdCliente());
                break;
            case "Material":
                Material m = getMateriales().get(indice);
                ServicesLocator.materialService().eliminarMaterial(m.getCodMaterial());
                break;
            case "Paquete":
                Paquete p = getPaquetes().get(indice);
                ServicesLocator.paqueteService().eliminarPaquete(p.getCodPaquete());
                break;
            case "Personal":
                Personal pe = getEmpleados().get(indice);
                ServicesLocator.empleadoService().eliminarEmpleado(pe.getIdEmpleado());
                break;
            case "Tratamiento":
                Tratamiento t = getTratamientos().get(indice);
                ServicesLocator.tratamientoService().eliminarTratamiento(t.getCodTratamiento(), t.getCodCategoria());
                break;
            case "PaqueteVendido":
                PaqueteVendido paV = getPaqueteVendidos().get(indice);
                ServicesLocator.paqueteVendidoService().eliminarPaqueteVendido(paV.getIdCliente(), paV.getCodPaquete(), paV.getFechaInicio());
                break;
            case "Usuario":
                User us = getUsers().get(indice);
                UserService.DeleteUser(us.getId());
                break;
            case "":
                break;
        }
    }

    public ArrayList<Tratamiento> cargarTratamientosPaquete(UUID codPaquete) throws SQLException {
        ArrayList<Tratamiento> tratamientosX = new ArrayList<>();
        String sql = "SELECT * FROM paq_trat WHERE paquete__codpaquete = ? ";
        try {
            if (ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement stmt = ConnectionBD.getConn().prepareStatement(sql);
            stmt.setObject(1, codPaquete, Types.OTHER);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Tratamiento trata = getTratamientos().stream().filter(t -> {
                    try {
                        return t.getCodCategoria().equals((UUID) rs.getObject(2)) &&
                                t.getCodTratamiento().equals((UUID) rs.getObject(1));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).findAny().orElse(null);

                tratamientosX.add(trata);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tratamientosX;

    }

    public ArrayList<Material> cargarTratamientosMaterial(UUID codTratamiento, UUID codCategoria) throws SQLException {
        ArrayList<Material> materialesX = new ArrayList<>();
        String sql = "SELECT * FROM mat_trat WHERE tratamiento__codtratamiento = ? AND tratamiento__categoria_codcategoria = ?";
        try {
            if (ConnectionBD.getConn().isClosed())
                ConnectionBD.connect();
            PreparedStatement stmt = ConnectionBD.getConn().prepareStatement(sql);
            stmt.setObject(1, codTratamiento, Types.OTHER);
            stmt.setObject(2, codCategoria, Types.OTHER);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Material mat = getMateriales().stream().filter(t -> {
                    try {
                        return t.getCodMaterial().equals((UUID) rs.getObject("material__codmaterial"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).findAny().orElse(null);

                materialesX.add(mat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return materialesX;

    }

    public ArrayList<User> getUsers() {
        return users;
    }
}

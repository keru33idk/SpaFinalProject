package utils;

import javax.swing.table.DefaultTableModel;

public class TableModelBD extends DefaultTableModel {
    public TableModelBD(String name){
        switch(name){
            case "Personal":
                String[] columnNamesPersonal = {"Nombre", "Especialidad", "Horas Semanales", "DNI", "Dirección", "Teléfono", "Distrito"};
                this.setColumnIdentifiers(columnNamesPersonal);
                break;
            case "Categoria":
                String[] columnNamesCategoria = {"Nombre de Categoría"};
                this.setColumnIdentifiers(columnNamesCategoria);
                break;

            case "Material":
                String[] columnNamesMaterial = {"Nombre de Material"};
                this.setColumnIdentifiers(columnNamesMaterial);
                break;
            case "Area":
                String[] columnNamesArea = {"Área", "Cantidad de Personal Fijo"};
                this.setColumnIdentifiers(columnNamesArea);
                break;
            case "Cliente":
                String[] columnNamesCliente = {"Nombre del Cliente"};
                this.setColumnIdentifiers(columnNamesCliente);
                break;
            case "Paquete":
                String[] columnNamesPaquete = {
                        "Nombre del Paquete",
                        "Precio",
                        "Duración Total (min)"
                };
                this.setColumnIdentifiers(columnNamesPaquete);
                break;
            case "Tratamiento":
                String[] columnNamesTratamiento = {
                        "Nombre",
                        "Descripción",
                        "Frecuencia Mensual",
                        "Duración (min)",
                        "Precio",
                        "Categoría"
                };
                this.setColumnIdentifiers(columnNamesTratamiento);
                break;
            case "Cita":
                String[] columnNamesCita = {
                        "Cliente",
                        "Tratamiento",
                        "Fecha",
                        "Hora Cita"
                };
                this.setColumnIdentifiers(columnNamesCita);
                break;
            case "PaqueteVendido":
                String[] columnNamesPaqueteVendido = {
                        "Cliente",
                        "Paquete",
                        "Fecha Compra",
                        "Fecha Inicio",
                        "Fecha Fin",
                };
                this.setColumnIdentifiers(columnNamesPaqueteVendido);
                break;
            case "Usuario":
                String[] columnNamesUser = {"Nombre", "Rol"};
                this.setColumnIdentifiers(columnNamesUser);
                break;
        }
    }
    public void adicionarPersonal(String nombre, String especialidad ,int horas, String
            DNI, String direccion, String telefono, String distrito){
        Object[] newRow = new Object[]{nombre , especialidad, horas, DNI, direccion,telefono,distrito};
        addRow(newRow);
    }

    public void adicionarCategoria(String nombreCategoria) {
        Object[] newRow = new Object[]{nombreCategoria};
        addRow(newRow);
    }

    public void adicionarMaterial(String nombreMaterial) {
        Object[] newRow = new Object[]{nombreMaterial};
        addRow(newRow);
    }

    public void adicionarArea(String area, int cantPersonalFijo) {
        Object[] newRow = new Object[]{area, cantPersonalFijo};
        addRow(newRow);
    }
    public void adicionarCliente(String nombreCliente) {
        Object[] newRow = new Object[]{nombreCliente};
        addRow(newRow);
    }

    public void adicionarPaquete(String nombrePaquete, double precio, int duracion) {
        Object[] newRow = new Object[]{nombrePaquete, precio, duracion};
        addRow(newRow);
    }
    public void adicionarUsuario(String nombre, String rol) {
        Object[] newRow = new Object[]{nombre, rol};
        addRow(newRow);
    }
    public void adicionarTratamiento(String nombre, String descripcion, int frecuencia,
                                     int duracion, double precio, String categoria) {
        Object[] newRow = new Object[]{
                nombre,
                descripcion,
                frecuencia,
                duracion,
                precio,
                categoria
        };
        addRow(newRow);
    }

    public void adicionarCita(String cliente, String tratamiento, String fecha, String horacita) {
        Object[] newRow = new Object[]{cliente, tratamiento, fecha, horacita};
        addRow(newRow);
    }
    public void adicionarPaqVendido(String Cliente, String Paquete, String fechaC, String fechaI, String fechaF) {
        Object[] newRow = new Object[]{Cliente, Paquete, fechaC,fechaI,fechaF};
        addRow(newRow);
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return false;
    }
}

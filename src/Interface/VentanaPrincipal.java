package Interface;

import Interface.Reportes.ReportesMenu;
import Logic.*;
import Logic.Models.*;
import utils.ManejadorExcepcionesSQL;
import utils.TableModelBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VentanaPrincipal {
    private String CRUDcod = "";
    private JButton Logout;
    private JPanel VentanaPanel;
    private JButton AreasButton;
    private JButton PersonalButton;
    private JButton TratamientosButton;
    private JButton citasButton;
    private JButton paquetesButton;
    private JTable TablaDeBaseDatos;
    private JButton insertarButton;
    private JButton eliminarButton;
    private JButton actualizarButton;
    private JButton CategoriaButton;
    private JButton materialesButton;
    private JButton reportesButton;
    private JButton clientesButton;
    private JPanel PanelTable;
    private JScrollPane TableScroll;
    private JButton paquetesVendidosButton;
    private JButton usersButton;
    private static ControllerSpa BDConect;
    private static JFrame interfaz;
    private JFrame reporteMenu;

    public VentanaPrincipal() {
        privilegiosButton(ConnectionBD.user());
        configurarMenuConHeader();

        TablaDeBaseDatos.getTableHeader().setReorderingAllowed(false);
        TablaDeBaseDatos.setAutoCreateRowSorter(true);
        // En tu constructor VentanaPrincipal()
        TablaDeBaseDatos.setShowGrid(false); // Eliminar líneas de grid
        TablaDeBaseDatos.setIntercellSpacing(new Dimension(0, 0)); // Espaciado compacto
        TablaDeBaseDatos.setSelectionBackground(new Color(0x3399FF)); // Color de selección moderno
        TablaDeBaseDatos.setSelectionForeground(Color.WHITE);
        TablaDeBaseDatos.getTableHeader().setReorderingAllowed(false);
        TablaDeBaseDatos.putClientProperty("JTable.showHorizontalLines", true);
        TablaDeBaseDatos.putClientProperty("JTable.showVerticalLines", false);
        TablaDeBaseDatos.putClientProperty("JTable.rowHeight", 30);
        TablaDeBaseDatos.putClientProperty("JTable.alternateRowColor", new Color(0xFAFAFA)); // Color alterno para filas



        Logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(ConnectionBD.getConn() != null && !ConnectionBD.getConn().isClosed()){
                        ConnectionBD.getConn().close();
                        System.out.println("Logout a PostgreSQL correctamente");
                        Login.getWindow(BDConect, interfaz);
                        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        interfaz.pack();
                        interfaz.setLocationRelativeTo(null);
                        interfaz.setVisible(true);

                    }

                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                //System.exit(0);
            }
        });
        AreasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Area");
                CRUDcod = "Area";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }

                try {
                    BDConect.cargarAreas();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for (Area area : BDConect.getAreas()) {
                    modelo.adicionarArea(
                            area.getArea(), area.getCantPersonalFijo()
                    );
                }
                TablaDeBaseDatos.setModel(modelo);
            }
        });

        citasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                citasButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                citasButton.setBackground(Color.DARK_GRAY);
                citasButton.setContentAreaFilled(true);
            }
        });
        CategoriaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                CategoriaButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                CategoriaButton.setBackground(Color.DARK_GRAY);
                CategoriaButton.setContentAreaFilled(true);
            }
        });
        PersonalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                PersonalButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                PersonalButton.setBackground(Color.DARK_GRAY);
                PersonalButton.setContentAreaFilled(true);
            }
        });
        TratamientosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                TratamientosButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                TratamientosButton.setBackground(Color.DARK_GRAY);
                TratamientosButton.setContentAreaFilled(true);
            }
        });
        AreasButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                AreasButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                AreasButton.setBackground(Color.DARK_GRAY);
                AreasButton.setContentAreaFilled(true);
            }
        });
        paquetesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                paquetesButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                paquetesButton.setBackground(Color.DARK_GRAY);
                paquetesButton.setContentAreaFilled(true);
            }
        });
        materialesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                materialesButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                materialesButton.setBackground(Color.DARK_GRAY);
                materialesButton.setContentAreaFilled(true);
            }
        });
        reportesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                reportesButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                reportesButton.setBackground(Color.DARK_GRAY);
                reportesButton.setContentAreaFilled(true);
            }
        });
        clientesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                clientesButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                clientesButton.setBackground(Color.DARK_GRAY);
                clientesButton.setContentAreaFilled(true);
            }
        });
        paquetesVendidosButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                paquetesVendidosButton.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                paquetesVendidosButton.setBackground(Color.DARK_GRAY);
                paquetesVendidosButton.setContentAreaFilled(true);
            }
        });
        Logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                Logout.setContentAreaFilled(false);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                Logout.setBackground(Color.DARK_GRAY);
                Logout.setContentAreaFilled(true);
            }
        });

        PersonalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Personal");
                CRUDcod = "Personal";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarEmpleados();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for(Personal p : BDConect.getEmpleados()){
                    modelo.adicionarPersonal(p.getNombreEmpleado(), p.getEspecialidad(),p.getHorasTrabajoSemanal(),
                           p.getDni() ,p.getDireccion(),p.getTelefono(),p.getDistrito());
                }
                TablaDeBaseDatos.setModel(modelo);
            }
        });
        interfaz.addWindowListener(new WindowAdapter() {
                                       @Override
                                       public void windowClosing(WindowEvent e) {
                                           try{
                                               if(ConnectionBD.getConn() != null && !ConnectionBD.getConn().isClosed()){
                                                   ConnectionBD.getConn().close();
                                                   System.out.println("Logout a PostgreSQL correctamente");
                                               }

                                           } catch (SQLException ex) {
                                               ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                                           }
                                           System.exit(0);
                                       }
                                   }

        );
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indice = TablaDeBaseDatos.convertRowIndexToModel(TablaDeBaseDatos.getSelectedRow());
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(indice != -1) {
                    int Option = JOptionPane.showConfirmDialog(null,
                            "¿Está seguro que desea eliminar este elemento",
                            "Confirnación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (Option == JOptionPane.YES_OPTION) {
                        try {
                            if(CRUDcod.equalsIgnoreCase("Usuario") && BDConect.getUsers().get(indice).getNombre().equalsIgnoreCase("admin")){
                                JOptionPane.showMessageDialog(
                                        null,
                                        "No puedes borrar a administrador del sistema",
                                        "Error de Base de Datos",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                throw new SQLException();
                            }

                            if(CRUDcod.equalsIgnoreCase("Usuario") && BDConect.getUsers().get(indice).getNombre().equalsIgnoreCase(ConnectionBD.getRole())){
                                JOptionPane.showMessageDialog(
                                        null,
                                        "No puedes borrarte a ti mismo",
                                        "Error de Base de Datos",
                                        JOptionPane.ERROR_MESSAGE
                                );
                                throw new SQLException();
                            }
                            BDConect.CRUDEliminarSelector(indice, CRUDcod);
                            System.out.println("NNNNNN");
                            //((DefaultTableModel) TablaDeBaseDatos.getModel()).removeRow(indice);
                            ActualizarTabla(CRUDcod);
                        } catch (SQLException ex) {
                            System.out.println("Excepcional");
                        }

                    }
                }
            }
        });
        TablaDeBaseDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        citasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Cita");
                CRUDcod = "Cita";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarCitas();
                    BDConect.cargarTratamientos();
                    BDConect.cargarClientes();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for (Cita cita : BDConect.getCitas()) {
                    modelo.adicionarCita(
                            BDConect.getClientes().stream().filter(cliente -> cliente.getIdCliente().equals(cita.getIdCliente())).findAny().orElse(null).getNombreCliente(),
                            BDConect.getTratamientos().stream().filter(trat -> trat.getCodTratamiento().equals(cita.getCodTratamiento())).findAny().orElse(null).getNombreTratamiento(),

                            cita.getFecha().toString(),
                            cita.getHoraCita().toString()
                    );
                }
                TablaDeBaseDatos.setModel(modelo);

            }
        });
        paquetesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarCategorias();
                    BDConect.cargarTratamientos();
                    BDConect.cargarPaquetes();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                TableModelBD modelo = new TableModelBD("Paquete");
                CRUDcod = "Paquete";
                for (Paquete paquete : BDConect.getPaquetes()) {
                    modelo.adicionarPaquete(
                            paquete.getNombrePaquete(),
                            paquete.getPrecioPaquete(),
                            paquete.getDuracionTotal()
                    );
                }
                TablaDeBaseDatos.setModel(modelo);

            }
        });
        TratamientosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Tratamiento");
                CRUDcod = "Tratamiento";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                    try {
                        BDConect.cargarMateriales();
                        BDConect.cargarTratamientos();
                    } catch (SQLException ex) {
                        ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                    }
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }

                    try {
                        for (Tratamiento tratamiento : BDConect.getTratamientos()) {
                        ResultSet rs = tratamiento.buscarCategoria();
                        rs.next();
                        modelo.adicionarTratamiento(
                                tratamiento.getNombreTratamiento(),
                                tratamiento.getDescripcion(),
                                tratamiento.getFrecuenciaSolicitudMensual(),
                                tratamiento.getDuracion(),
                                tratamiento.getPrecio(),
                                rs.getString(2) // Aquí deberías obtener el nombre de la categoría
                        );
                        }
                    } catch (SQLException ex) {
                        ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                    }

                TablaDeBaseDatos.setModel(modelo);

            }
        });
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Cliente");
                CRUDcod = "Cliente";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarClientes();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for (Cliente cliente : BDConect.getClientes()) {
                    modelo.adicionarCliente(
                            cliente.getNombreCliente()
                    );
                }
                TablaDeBaseDatos.setModel(modelo);

            }
        });
        materialesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Material");
                CRUDcod = "Material";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarMateriales();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for (Material material : BDConect.getMateriales()) {
                    modelo.adicionarMaterial(
                            material.getNombreMaterial()
                    );
                }
                TablaDeBaseDatos.setModel(modelo);

            }
        });
        paquetesVendidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("PaqueteVendido");
                CRUDcod = "PaqueteVendido";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarPaquetesVendidos();
                    BDConect.cargarPaquetes();
                    BDConect.cargarClientes();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for (PaqueteVendido paqueteVendido : BDConect.getPaqueteVendidos()) {
                    modelo.adicionarPaqVendido(
                            BDConect.getClientes().stream().filter(cliente -> cliente.getIdCliente().equals(paqueteVendido.getIdCliente())).findAny().orElse(null).getNombreCliente(),
                            BDConect.getPaquetes().stream().filter(paq -> paq.getCodPaquete().equals(paqueteVendido.getCodPaquete())).findAny().orElse(null).getNombrePaquete(),
                            paqueteVendido.getFechaCompra().toString(),
                            paqueteVendido.getFechaInicio().toString(),
                            paqueteVendido.getFechaFin().toString()

                    );
                }
                TablaDeBaseDatos.setModel(modelo);
                
            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try{
                    CRUDInsertarSelector(CRUDcod);
                    ActualizarTabla(CRUDcod);
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }


            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indice = TablaDeBaseDatos.getSelectedRow();
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(indice != -1) {
                    int Option = JOptionPane.showConfirmDialog(null,
                            "¿Está seguro que desea actualizar este elemento",
                            "Confirnación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (Option == JOptionPane.YES_OPTION) {
                        try{
                            CRUDUpdateSelector(CRUDcod, indice);
                            ActualizarTabla(CRUDcod);
                        } catch (SQLException ex) {
                            ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                        }
                    }
                }
            }
        });
        CategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Categoria");
                CRUDcod = "Categoria";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarCategorias();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for (Categoria cat : BDConect.getCategorias()) {
                    modelo.adicionarCliente(
                            cat.getNombreCategoria()
                    );
                }
                TablaDeBaseDatos.setModel(modelo);

            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(reporteMenu == null){
                    JFrame reportes = new JFrame("Menú Reportes");
                    reporteMenu = reportes;
                    ReportesMenu.setInterfaz(reportes);
                    reportes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    reportes.pack();
                    reportes.setLocationRelativeTo(null);
                    reportes.setVisible(true);
                }else{
                    reporteMenu.dispose();
                    JFrame reportes = new JFrame("Menú Reportes");
                    reporteMenu = reportes;
                    ReportesMenu.setInterfaz(reportes);
                    reportes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    reportes.pack();
                    reportes.setLocationRelativeTo(null);
                    reportes.setVisible(true);
                }

            }
        });
        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModelBD modelo = new TableModelBD("Usuario");
                CRUDcod = "Usuario";
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                try {
                    BDConect.cargarUsers();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                for (User user : BDConect.getUsers()) {
                    modelo.adicionarUsuario(user.getNombre(), user.getRol()
                    );
                }
                TablaDeBaseDatos.setModel(modelo);

            }
        });
    }
    public void CRUDInsertarSelector(String name) throws SQLException {
        switch (name){
            case "Area":
                InsertUpdateArea.getWindow(BDConect);
                InsertUpdateArea insertarA = new InsertUpdateArea(interfaz,"Insertar Area");
                insertarA.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarA.pack();
                insertarA.setLocationRelativeTo(null);
                insertarA.setVisible(true);
                break;
            case "Categoria":
                InsertUpdateCategoria.getWindow(BDConect);
                InsertUpdateCategoria insertarCat = new InsertUpdateCategoria(interfaz,"Insertar Categoria");
                insertarCat.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarCat.pack();
                insertarCat.setLocationRelativeTo(null);
                insertarCat.setVisible(true);
                break;
            case "Cita":
                try {
                    BDConect.cargarClientes();
                    BDConect.cargarTratamientos();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                InsertUpdateCita.getWindow(BDConect);
                InsertUpdateCita insertarCita = new InsertUpdateCita(interfaz,"Insertar Cita");
                insertarCita.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarCita.pack();
                insertarCita.setLocationRelativeTo(null);
                insertarCita.setVisible(true);
                break;
            case "Cliente":
                InsertUpdateCliente.getWindow(BDConect);
                InsertUpdateCliente insertarCliente = new InsertUpdateCliente(interfaz,"Insertar Cliente");
                insertarCliente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarCliente.pack();
                insertarCliente.setLocationRelativeTo(null);
                insertarCliente.setVisible(true);
                break;
            case "Material":
                InsertUpdateMaterial.getWindow(BDConect);
                InsertUpdateMaterial insertar = new InsertUpdateMaterial(interfaz,"Insertar Material");
                insertar.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertar.pack();
                insertar.setLocationRelativeTo(null);
                insertar.setVisible(true);
                break;
            case "Paquete":
                try {
                    BDConect.cargarTratamientos();
                    BDConect.cargarCategorias();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                InsertPaquete.getWindow(BDConect);
                InsertPaquete insertarPaquete = new InsertPaquete(interfaz,"Insertar Paquete");
                insertarPaquete.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarPaquete.pack();
                insertarPaquete.setLocationRelativeTo(null);
                insertarPaquete.setVisible(true);
                break;
            case "Personal":
                try {
                    BDConect.cargarAreas();
                    BDConect.cargarTratamientos();
                    BDConect.cargarCategorias();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                InsertUpdateEmpleado.getWindow(BDConect);
                InsertUpdateEmpleado insertarMat = new InsertUpdateEmpleado(interfaz,"Insertar Empleado");
                insertarMat.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarMat.pack();
                insertarMat.setLocationRelativeTo(null);
                insertarMat.setVisible(true);
                break;
            case "Tratamiento":
                try {
                    BDConect.cargarMateriales();
                    BDConect.cargarCategorias();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                InsertUpdateTratamiento.getWindow(BDConect);
                InsertUpdateTratamiento insertarTrat = new InsertUpdateTratamiento(interfaz,"Insertar Tratamiento");
                insertarTrat.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarTrat.pack();
                insertarTrat.setLocationRelativeTo(null);
                insertarTrat.setVisible(true);
                break;
            case "PaqueteVendido":
                try {
                    BDConect.cargarClientes();
                    BDConect.cargarPaquetes();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                InsertUpdatePaqueteVendido.getWindow(BDConect);
                InsertUpdatePaqueteVendido insertarPaqVend = new InsertUpdatePaqueteVendido(interfaz,"Insertar Paquete Vendido");
                insertarPaqVend.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarPaqVend.pack();
                insertarPaqVend.setLocationRelativeTo(null);
                insertarPaqVend.setVisible(true);
                break;
            case "Usuario":
                InsertUpdateUsers.getWindow(BDConect);
                InsertUpdateUsers insertarU = new InsertUpdateUsers(interfaz,"Insertar Usuario");
                insertarU.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarU.pack();
                insertarU.setLocationRelativeTo(null);
                insertarU.setVisible(true);
                break;
            case "":
                break;
        }
    }
    public void CRUDUpdateSelector(String name, int i) throws SQLException {
        switch (name){
            case "Area":
                Area ar = BDConect.getAreas().get(i);
                InsertUpdateArea.getWindow(BDConect);
                InsertUpdateArea insertarA = new InsertUpdateArea(interfaz,"Actualizar Area");
                insertarA.setAreaUp(ar);
                insertarA.getCampo_texto_Nombre().setText(ar.getArea());
                insertarA.getCampo_Texto_Cantidad().setText(String.valueOf(ar.getCantPersonalFijo()));
                insertarA.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarA.pack();
                insertarA.setLocationRelativeTo(null);
                insertarA.setVisible(true);
                break;
            case "Categoria":
                Categoria cat = BDConect.getCategorias().get(i);
                InsertUpdateCategoria.getWindow(BDConect);
                InsertUpdateCategoria insertarCat = new InsertUpdateCategoria(interfaz,"Actualizar Categoria");
                insertarCat.setCatUp(cat);
                insertarCat.gettFCategoria().setText(cat.getNombreCategoria());
                insertarCat.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarCat.pack();
                insertarCat.setLocationRelativeTo(null);
                insertarCat.setVisible(true);
                break;
            case "Cita":
                try {
                    BDConect.cargarClientes();
                    BDConect.cargarTratamientos();
                    BDConect.cargarCategorias();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                Cita citaUp = BDConect.getCitas().get(i);
                InsertUpdateCita.getWindow(BDConect);
                InsertUpdateCita insertarCita = new InsertUpdateCita(interfaz,"Actualizar Cita");
                insertarCita.setCitaUp(citaUp);
                insertarCita.getSpinner1().setValue( new java.util.Date(citaUp.getFecha().getTime()));
                insertarCita.getSpinner2().setValue( new java.util.Date(citaUp.getHoraCita().getTime()));
                insertarCita.getComboBox1().setSelectedItem(
                        BDConect.getClientes().stream().filter(a -> a.getIdCliente().equals(citaUp.getIdCliente())).findAny().orElse(null)
                );
                insertarCita.getComboBox2().setSelectedItem(
                        BDConect.getTratamientos().stream().filter(a -> a.getCodTratamiento().equals(citaUp.getCodTratamiento()) && a.getCodCategoria().equals(citaUp.getCodCategoria())).findAny().orElse(null)
                );
                insertarCita.getComboBox1().setEnabled(false);
                insertarCita.getTxtFiltroCliente().setEnabled(false);

                insertarCita.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarCita.pack();
                insertarCita.setLocationRelativeTo(null);
                insertarCita.setVisible(true);
                break;
            case "Cliente":
                Cliente client = BDConect.getClientes().get(i);
                InsertUpdateCliente.getWindow(BDConect);
                InsertUpdateCliente insertarCliente = new InsertUpdateCliente(interfaz,"Actualizar Cliente");
                insertarCliente.setClienteUP(client);
                insertarCliente.getCampo_Texto_Nombre().setText(client.getNombreCliente());
                insertarCliente.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarCliente.pack();
                insertarCliente.setLocationRelativeTo(null);
                insertarCliente.setVisible(true);
                break;
            case "Material":
                Material m = BDConect.getMateriales().get(i);
                InsertUpdateMaterial.getWindow(BDConect);
                InsertUpdateMaterial insertarM = new InsertUpdateMaterial(interfaz,"Actualizar Material");
                insertarM.setMActu(m);
                insertarM.gettFMaterial().setText(m.getNombreMaterial());
                insertarM.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarM.pack();
                insertarM.setLocationRelativeTo(null);
                insertarM.setVisible(true);
                break;
            case "Paquete":
                try {
                    BDConect.cargarTratamientos();
                    BDConect.cargarCategorias();
                    BDConect.cargarPaquetes();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                Paquete paq = BDConect.getPaquetes().get(i);
                InsertPaquete.getWindow(BDConect);
                InsertPaquete.setPaqActu(paq);
                InsertPaquete insertarPaquete = new InsertPaquete(interfaz,"Actualizar Paquete");
                insertarPaquete.gettFNombre().setText(paq.getNombrePaquete());
                insertarPaquete.gettFPrecio().setText(String.valueOf(paq.getPrecioPaquete()));
                insertarPaquete.gettFDuracion().setText(String.valueOf(paq.getDuracionTotal()));
                insertarPaquete.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarPaquete.pack();
                insertarPaquete.setLocationRelativeTo(null);
                insertarPaquete.setVisible(true);
                break;
            case "Personal":
                try {
                    BDConect.cargarAreas();
                    BDConect.cargarTratamientos();
                    BDConect.cargarCategorias();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                Personal p = BDConect.getEmpleados().get(i);
                InsertUpdateEmpleado.getWindow(BDConect);
                InsertUpdateEmpleado.setPActu(p);
                InsertUpdateEmpleado insertar = new InsertUpdateEmpleado(interfaz,"Actualizar Empleado");


                insertar.getTextNombre().setText(p.getNombreEmpleado());
                insertar.getTextFieldEspecial().setText(p.getEspecialidad());
                insertar.getTextFieldHoras().setText(String.valueOf(p.getHorasTrabajoSemanal()));
                insertar.getTextFieldDNI().setText(p.getDni());
                insertar.getTextFieldDistrito().setText(p.getDistrito());
                insertar.getTextFieldTelefono().setText(p.getTelefono());
                insertar.getTextFieldDirecc().setText(p.getDireccion());
                if(p.isEmpleado()){
                    insertar.getCheckBox1().setSelected(true);
                    insertar.getAreacomboBox1().setEnabled(false);
                    insertar.getTratcomboBox().setEnabled(false);
                }else{
                    insertar.getAreacomboBox1().setSelectedItem(
                            BDConect.getAreas().stream().filter(a -> a.getArea().equalsIgnoreCase(p.getArea())).findAny().orElse(null)
                            );
                    insertar.getTratcomboBox().setSelectedItem(
                            BDConect.getTratamientos().stream().filter(a -> a.getCodTratamiento().equals(p.getTratamiento()) &&
                                            a.getCodCategoria().equals(p.getCategoriaTratamiento())).findAny().orElse(null)
                    );
                }

                insertar.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertar.pack();
                insertar.setLocationRelativeTo(null);
                insertar.setVisible(true);
                break;
            case "Tratamiento":
                try {
                    BDConect.cargarCategorias();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                Tratamiento trat = BDConect.getTratamientos().get(i);
                InsertUpdateTratamiento.getWindow(BDConect);
                InsertUpdateTratamiento.setTratActu(trat);
                InsertUpdateTratamiento insertarTrat = new InsertUpdateTratamiento(interfaz,"Actualizar Tratamiento");

                insertarTrat.gettFNombre().setText(trat.getNombreTratamiento());
                insertarTrat.gettFDuracion().setText(String.valueOf(trat.getDuracion()));
                insertarTrat.gettFFrecuencia().setText(String.valueOf(trat.getFrecuenciaSolicitudMensual()));
                insertarTrat.gettFPrecio().setText(String.valueOf(trat.getPrecio()));
                insertarTrat.getTextAreaDescripcion().setText(trat.getDescripcion());

                insertarTrat.getComboBoxCategoria().setSelectedItem(
                        BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(trat.getCodCategoria())).findAny().orElse(null)
                );

                insertarTrat.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarTrat.pack();
                insertarTrat.setLocationRelativeTo(null);
                insertarTrat.setVisible(true);
                break;
            case "PaqueteVendido":
                try {
                    BDConect.cargarClientes();
                    BDConect.cargarPaquetes();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                PaqueteVendido paqVen = BDConect.getPaqueteVendidos().get(i);
                InsertUpdatePaqueteVendido.getWindow(BDConect);
                InsertUpdatePaqueteVendido insertarPaqVend = new InsertUpdatePaqueteVendido(interfaz,"Actualizar Paquete Vendido");
                insertarPaqVend.setPaqVendidoActu(paqVen);
                insertarPaqVend.getSpinner1FechaInicio().setValue( new java.util.Date(paqVen.getFechaInicio().getTime()));
                insertarPaqVend.getSpinner2FechaFin().setValue( new java.util.Date(paqVen.getFechaFin().getTime()));
                insertarPaqVend.getComboBoxCliente().setEnabled(false);
                insertarPaqVend.getComboBoxPaquetes().setEnabled(false);
                insertarPaqVend.getTextcliente().setEnabled(false);
                insertarPaqVend.getTextPaquete().setEnabled(false);
                insertarPaqVend.getComboBoxCliente().setSelectedItem(
                        BDConect.getClientes().stream().filter(a -> a.getIdCliente().equals(paqVen.getIdCliente())).findAny().orElse(null)
                );
                insertarPaqVend.getComboBoxPaquetes().setSelectedItem(
                        BDConect.getPaquetes().stream().filter(a -> a.getCodPaquete().equals(paqVen.getCodPaquete())).findAny().orElse(null)
                );

                insertarPaqVend.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarPaqVend.pack();
                insertarPaqVend.setLocationRelativeTo(null);
                insertarPaqVend.setVisible(true);
                break;
            case "Usuario":

                User u = BDConect.getUsers().get(i);
                String rol = "";
                switch(u.getRol()){
                    case "admin":
                        rol ="Administracion";
                        break;
                    case "aten":
                        rol ="Atencion al Cliente";
                        break;
                    case "recur":
                        rol ="Recursos Humanos";
                        break;
                }
                InsertUpdateUsers.getWindow(BDConect);
                InsertUpdateUsers insertarU = new InsertUpdateUsers(interfaz,"Actualizar Usuario");
                insertarU.setUsUpd(u);
                insertarU.getNombretextField1().setText(u.getNombre());
                insertarU.getComboBox1().setSelectedItem(rol);
                insertarU.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                insertarU.pack();
                insertarU.setLocationRelativeTo(null);
                insertarU.setVisible(true);
                break;
            case "":
                break;
        }
    }

    public void ActualizarTabla(String name) throws SQLException {
        switch (name){
            case "Area":
                AreasButton.doClick();
                break;
            case "Categoria":
                CategoriaButton.doClick();
                break;
            case "Cita":
                citasButton.doClick();
                break;
            case "Cliente":
                clientesButton.doClick();
                break;
            case "Material":
                materialesButton.doClick();
                break;
            case "Paquete":
                paquetesButton.doClick();
                break;
            case "Personal":
                PersonalButton.doClick();
                break;
            case "Tratamiento":
                TratamientosButton.doClick();
                break;
            case "PaqueteVendido":
                paquetesVendidosButton.doClick();
                break;
            case "Usuario":
                usersButton.doClick();
                break;
            case "":
                break;
        }
    }

    public static void getWindow(ControllerSpa app, JFrame frame) {
        BDConect = app;
        interfaz = frame;

        interfaz.setContentPane(new VentanaPrincipal().VentanaPanel);

    }


    public void privilegiosButton(String user){
        switch(user) {
            case "Administracion":
                PersonalButton.setEnabled(false);
                citasButton.setEnabled(false);
                paquetesVendidosButton.setEnabled(false);
                clientesButton.setEnabled(false);

                PersonalButton.setVisible(false);
                citasButton.setVisible(false);
                paquetesVendidosButton.setVisible(false);
                clientesButton.setVisible(false);
                break;
            case "Atencion al Cliente":
                PersonalButton.setEnabled(false);
                paquetesButton.setEnabled(false);
                AreasButton.setEnabled(false);
                CategoriaButton.setEnabled(false);
                materialesButton.setEnabled(false);
                TratamientosButton.setEnabled(false);

                usersButton.setEnabled(false);
                usersButton.setVisible(false);
                PersonalButton.setVisible(false);
                paquetesButton.setVisible(false);
                AreasButton.setVisible(false);
                CategoriaButton.setVisible(false);
                materialesButton.setVisible(false);
                TratamientosButton.setVisible(false);
                break;
            case "Recursos Humanos":
                citasButton.setEnabled(false);
                paquetesVendidosButton.setEnabled(false);
                paquetesButton.setEnabled(false);
                AreasButton.setEnabled(false);
                CategoriaButton.setEnabled(false);
                materialesButton.setEnabled(false);
                TratamientosButton.setEnabled(false);
                clientesButton.setEnabled(false);

                usersButton.setEnabled(false);
                usersButton.setVisible(false);

                citasButton.setVisible(false);
                paquetesVendidosButton.setVisible(false);
                paquetesButton.setVisible(false);
                AreasButton.setVisible(false);
                CategoriaButton.setVisible(false);
                materialesButton.setVisible(false);
                TratamientosButton.setVisible(false);
                clientesButton.setVisible(false);
                break;
        }
    }

    private void configurarMenuConHeader() {
        // Panel contenedor principal (sidebar)
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(0x1E1E1E));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));
        sidebarPanel.setPreferredSize(new Dimension(220, Integer.MAX_VALUE));



        // 1. HEADER CON ICONO DE ROL
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(0x252525));
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Icono según rol (SVG o texto)
        JLabel iconoRol = new JLabel();
        iconoRol.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconoRol.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));

        // Personalizar según el rol
        String rol = ConnectionBD.user();
        switch(rol) {
            case "Administracion":
                iconoRol.setIcon(crearIconoCircular("⚙️", 80, new Color(0x3A6EA5)));
                break;
            case "Atencion al Cliente":
                iconoRol.setIcon(crearIconoCircular("👩‍💼", 80, new Color(0x5E8C31)));
                break;
            case "Recursos Humanos":
                iconoRol.setIcon(crearIconoCircular("👥", 80, new Color(0x9C27B0)));
                break;
            default:
                iconoRol.setIcon(crearIconoCircular("👤", 80, new Color(0x808080)));
        }

        // Label del rol
        JLabel rolLabel = new JLabel(rol.toUpperCase());
        rolLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rolLabel.setForeground(Color.WHITE);
        rolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rolLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        headerPanel.add(iconoRol);
        headerPanel.add(rolLabel);
        sidebarPanel.add(headerPanel);

        // 2. LISTA DE BOTONES (igual que antes)
        String[][] categorias = {
                {"OPERACIONES", "AreasButton", "CategoriaButton", "PersonalButton", "reportesButton", "usersButton"},
                {"CLIENTES", "citasButton", "clientesButton", "paquetesVendidosButton"},
                {"INVENTARIO", "paquetesButton", "materialesButton", "TratamientosButton"},
        };

        for (String[] categoria : categorias) {
            // Título de categoría
            JLabel titulo = new JLabel(categoria[0]);
            titulo.setForeground(new Color(0x808080));
            titulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
            titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
            titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidebarPanel.add(titulo);

            // Botones de la categoría
            int cant = categoria.length;
            for (int i = 1; i < categoria.length; i++) {
                try {
                    JButton boton = (JButton) VentanaPrincipal.class
                            .getDeclaredField(categoria[i])
                            .get(this);
                    if (boton != null) {
                        // Estilo individual de botón
                        if(!boton.isVisible()){
                            cant--;
                        }
                        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
                        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                        boton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
                        boton.setHorizontalAlignment(SwingConstants.LEFT);
                        boton.setBackground(new Color(0x1E1E1E));

                        // Efecto hover moderno
                        boton.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseEntered(MouseEvent e) {
                                boton.setBackground(new Color(0x3A3A3A));
                            }
                            @Override
                            public void mouseExited(MouseEvent e) {
                                boton.setBackground(new Color(0x1E1E1E));
                            }
                        });

                        sidebarPanel.add(boton);
                    }
                } catch (Exception e) {
                    System.err.println("Botón no encontrado: " + categoria[i]);
                }
            }

            if(cant == 1){
                titulo.setText("");
            }

            // Separador entre categorías
            if (!categoria[0].equals("REPORTES") && cant != 1) {
                JSeparator separator = new JSeparator();
                separator.setForeground(new Color(0x333333));
                separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                separator.setAlignmentX(Component.LEFT_ALIGNMENT);
                sidebarPanel.add(separator);
            }
        }


        // 3. FOOTER CON LOGOUT
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(0x1E1E1E));
        footerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        footerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        Logout.setBackground(new Color(0xFF5555));
        Logout.setForeground(Color.WHITE);
        Logout.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x444444)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        Logout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        footerPanel.add(Logout);

        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(footerPanel);

        // Actualizar layout
        VentanaPanel.removeAll();
        VentanaPanel.setLayout(new BorderLayout());
        VentanaPanel.add(sidebarPanel, BorderLayout.WEST);
        VentanaPanel.add(new JScrollPane(PanelTable), BorderLayout.CENTER);
        interfaz.setMinimumSize(new Dimension(250 + 400, 600)); // Mínimo ancho: sidebar + contenido


    }

    // Método auxiliar para crear iconos circulares
    private Icon crearIconoCircular(String texto, int tamaño, Color colorFondo) {
        BufferedImage image = new BufferedImage(tamaño, tamaño, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        // Fondo circular
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(colorFondo);
        g2.fillOval(0, 0, tamaño, tamaño);

        // Texto centrado
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.PLAIN, tamaño/2));
        FontMetrics fm = g2.getFontMetrics();
        int x = (tamaño - fm.stringWidth(texto)) / 2;
        int y = (fm.getAscent() + (tamaño - (fm.getAscent() + fm.getDescent())) / 2);
        g2.drawString(texto, x, y);
        g2.dispose();

        return new ImageIcon(new ImageIcon("src/img/logo_login.png")
                .getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
    }

}

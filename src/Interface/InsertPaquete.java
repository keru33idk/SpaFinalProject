package Interface;

import Logic.services.ServicesLocator;
import utils.*;

import Logic.Models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import Logic.ConnectionBD;
import Logic.ControllerSpa;

import java.sql.SQLException;
import java.util.ArrayList;

public class InsertPaquete extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextFieldNuevo tFNombre;
    private DecimalTextField tFPrecio;
    private JTextFieldNuevo tFDuracion;

    private JTable tableTratamientos;
    private JTable tableTratamientoLista;
    private JButton FlechaIzquierdaButton;
    private JButton FlechaDerechaButton;
    private JLabel Warning;

    private static Paquete PaqActu;

    private static ControllerSpa BDConect;

    public InsertPaquete(JFrame inter, String titulo) {
        super(inter, titulo, true);
        if(getTitle().equalsIgnoreCase("Actualizar Paquete")){
            buttonOK.setText("Actualizar");
            Warning.setText("Advertencia: Los tratamientos que actualices aquí se actualizan en tiempo real en la BD sin necesidad de pulsar actualizar, proceder con precaución");
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //tFPrecio.setNumeros(true);
        tFPrecio.setText("0.00");
        tFNombre.setLetras(true);
        tFDuracion.setNumeros(true);
        tFDuracion.setSinEspacios(true);
        tFDuracion.setLimite(3);

        //Tabla Tratamientos Paquete Disponibles
        TableModelBD modeloT = new TableModelBD("Tratamiento");
        String[] columnNamesPersonal = {"Nombre", "Categoria"};

        modeloT.setColumnIdentifiers(columnNamesPersonal);
        for (Tratamiento t : BDConect.getTratamientos()) {
            if(getPaqActu() != null && getPaqActu().getTratamientos().contains(t)){
                Object[] newRow = new Object[]{t,
                        BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(t.getCodCategoria())).findAny().orElse(null)
                };
                modeloT.addRow(newRow);
            }
        }
        tableTratamientos.setModel(modeloT);
        tableTratamientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Tabla Tratamientos
        TableModelBD modeloT2 = new TableModelBD("Tratamiento");
        modeloT2.setColumnIdentifiers(columnNamesPersonal);
        for (Tratamiento t : BDConect.getTratamientos()) {
            if(getPaqActu() != null && !getPaqActu().getTratamientos().contains(t)){
                Object[] newRow = new Object[]{t,
                        BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(t.getCodCategoria())).findAny().orElse(null)
                };
                modeloT2.addRow(newRow);
            }
        }
        tableTratamientoLista.setModel(modeloT2);
        tableTratamientoLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        if(!getTitle().equalsIgnoreCase("Actualizar Paquete")){
            modeloT2.setColumnIdentifiers(columnNamesPersonal);
            for (Tratamiento t : BDConect.getTratamientos()) {
                System.out.println("AAAAAAAAAAAAAAAa");
                Object[] newRow = new Object[]{t,
                        BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(t.getCodCategoria())).findAny().orElse(null)
                };
                modeloT2.addRow(newRow);
            }
            tableTratamientoLista.setModel(modeloT2);
            tableTratamientoLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
        tableTratamientos.getTableHeader().setReorderingAllowed(false);
        tableTratamientoLista.getTableHeader().setReorderingAllowed(false);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    if(ValidarCampos.validarCamposNoVacios(contentPane)){
                        if(getTitle().equalsIgnoreCase("Actualizar Paquete")){
                            try {
                                onOKUp();
                            } catch (SQLException ex) {
                                ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                            }
                        }else{
                            try {
                                onOK();
                            } catch (SQLException ex) {
                                ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                            }
                        }
                    }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        FlechaIzquierdaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(!getTitle().equalsIgnoreCase("Actualizar Paquete")){
                    int indice = tableTratamientoLista.getSelectedRow();
                    if(indice != -1){
                        Tratamiento trat = (Tratamiento) tableTratamientoLista.getValueAt(indice, 0);
                        ((DefaultTableModel) tableTratamientoLista.getModel()).removeRow(indice);
                        ((DefaultTableModel) tableTratamientos.getModel()).addRow(
                                new Object[]{trat,
                                        BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(trat.getCodCategoria())).findAny().orElse(null)
                                }
                        );
                    }

                }else{
                    int indice = tableTratamientoLista.getSelectedRow();
                    if(indice != -1){
                        Tratamiento trat = (Tratamiento) tableTratamientoLista.getValueAt(indice, 0);

                        try {
                            ServicesLocator.paqueteTratamientoService().insertarPaqueteTratamiento(getPaqActu().getCodPaquete(), trat.getCodTratamiento(), trat.getCodCategoria());
                            ((DefaultTableModel) tableTratamientoLista.getModel()).removeRow(indice);
                            ((DefaultTableModel) tableTratamientos.getModel()).addRow(
                                    new Object[]{trat,
                                            BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(trat.getCodCategoria())).findAny().orElse(null)
                                    }
                            );
                        } catch (SQLException ex) {
                            ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                        }
                    }

                }

            }
        });
        FlechaDerechaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(!getTitle().equalsIgnoreCase("Actualizar Paquete")){
                    int indice = tableTratamientos.getSelectedRow();
                    if(indice != -1){
                        Tratamiento trat = (Tratamiento) tableTratamientos.getValueAt(indice, 0);
                        ((DefaultTableModel) tableTratamientos.getModel()).removeRow(indice);
                        ((DefaultTableModel) tableTratamientoLista.getModel()).addRow(
                                new Object[]{trat,
                                        BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(trat.getCodCategoria())).findAny().orElse(null)
                                }
                        );
                    }

                }else{
                    int indice = tableTratamientos.getSelectedRow();
                    if(indice != -1){
                        Tratamiento trat = (Tratamiento) tableTratamientos.getValueAt(indice, 0);

                        try {
                            ServicesLocator.paqueteTratamientoService().eliminarPaqueteTratamiento(getPaqActu().getCodPaquete(), trat.getCodTratamiento(), trat.getCodCategoria());
                            ((DefaultTableModel) tableTratamientos.getModel()).removeRow(indice);
                            ((DefaultTableModel) tableTratamientoLista.getModel()).addRow(
                                    new Object[]{trat,
                                            BDConect.getCategorias().stream().filter(c -> c.getCodCategoria().equals(trat.getCodCategoria())).findAny().orElse(null)
                                    }
                            );
                        } catch (SQLException ex) {
                            ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                        }
                    }

                }

            }
        });
    }

    private void onOK() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        String nombre = tFNombre.getText();
        double precio = Double.parseDouble(tFPrecio.getText());
        int duracion =Integer.parseInt(tFDuracion.getText());
        ArrayList<Tratamiento>listaT = new ArrayList<Tratamiento>();
        /*
        ArrayList<Tratamiento>listaT = new ArrayList<Tratamiento>();
        int[] seleccionadas = tableTratamientos.getSelectedRows();
        //añado el nombre de los materiales seleccionados al arraylist
        for(int s: seleccionadas){
            listaT.add((Tratamiento)tableTratamientos.getValueAt(s,0));
        }*/
        int iMax = tableTratamientos.getRowCount();
        for(int i = 0; i < iMax; i++){
            Tratamiento a = (Tratamiento) tableTratamientos.getValueAt(i,0);
            listaT.add(a);
        }

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea insertar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Paquete Paq = new Paquete(nombre,precio,duracion);
            for(Tratamiento Td :listaT) {
                 Paq.getTratamientos().add(Td);
            }
            BDConect.getPaquetes().add(Paq);
            ServicesLocator.paqueteService().insertarPaquete(Paq.getCodPaquete(),Paq.getNombrePaquete(),Paq.getPrecioPaquete(),Paq.getDuracionTotal());
            ServicesLocator.paqueteService().añadirPaquetesTratamiento(Paq);
            dispose();
        }
    }
    private void onOKUp() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        String nombre = tFNombre.getText();
        double precio = Double.parseDouble(tFPrecio.getText());
        int duracion = Integer.parseInt(tFDuracion.getText());
        /*
        ArrayList<String>listaT = new ArrayList<String>();
        int[] seleccionadas = tableTratamientos.getSelectedRows();
        //añado el nombre de los materiales seleccionados al arraylist
        for(int s: seleccionadas){
            listaT.add((String)tableTratamientos.getValueAt(s,0));
        }*/

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea modificar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Paquete Paq = new Paquete(getPaqActu().getCodPaquete(),nombre,precio,duracion);
            /*
            for(String Td :listaT) {
                Tratamiento Ta = BDConect.getTratamientos().stream().filter(m -> m.getNombreTratamiento().equalsIgnoreCase(Td)).findAny().orElse(null);
                Paq.getTratamientos().add(Ta);
            }*/
            //BDConect.getPaquetes().add(Paq);
            ServicesLocator.paqueteService().actualizarPaquete(Paq.getCodPaquete(),Paq.getNombrePaquete(),Paq.getPrecioPaquete(),Paq.getDuracionTotal());
            BDConect.getPaquetes().set(BDConect.getPaquetes().indexOf(getPaqActu()), Paq);
            //PaqueteService.eliminarPaquetesTratamiento(Paq);
            //PaqueteService.añadirPaquetesTratamiento(Paq);
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static Paquete getPaqActu() {
        return PaqActu;
    }

    public static void setPaqActu(Paquete paqActu) {
        PaqActu = paqActu;
    }

    public JTextFieldNuevo gettFNombre() {
        return tFNombre;
    }

    public JTextFieldNuevo gettFDuracion() {
        return tFDuracion;
    }


    public DecimalTextField gettFPrecio() {
        return tFPrecio;
    }



    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }
}

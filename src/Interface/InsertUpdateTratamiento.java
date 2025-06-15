package Interface;

import Logic.Models.*;
import Logic.services.*;
import utils.*;

import javax.swing.*;
import java.awt.event.*;
import Logic.ConnectionBD;
import Logic.ControllerSpa;
import utils.JTextFieldNuevo;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
public class InsertUpdateTratamiento extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextFieldNuevo tFNombre;
    private JTextFieldNuevo tFDuracion;
    private JTextFieldNuevo tFFrecuencia;
    private DecimalTextField tFPrecio;
    private JComboBox comboBoxCategoria;
    private JTextField textAreaDescripcion;
    private JTable tableMateriales;
    private JTable tableDisponibles;
    private JButton FlechaDerechaButton;
    private JLabel Warning;
    private JButton FlechaIzquierdaButton;
    private static Tratamiento TratActu;

    private static ControllerSpa BDConect;

    public InsertUpdateTratamiento(JFrame inter, String titulo) throws SQLException {
        super(inter, titulo, true);
        if (getTitle().equalsIgnoreCase("Actualizar Tratamiento")) {
            buttonOK.setText("Actualizar");
            Warning.setText("Advertencia: Los tratamientos que actualices aquí se actualizan en tiempo real en la BD sin necesidad de pulsar actualizar, proceder con precaución");

        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        tFNombre.setLetras(true);
        tFDuracion.setNumeros(true);
        tFDuracion.setSinEspacios(true);
        tFDuracion.setLimite(3);
        tFFrecuencia.setNumeros(true);
        //tFPrecio.setNumeros(true);
        //tFPrecio.setText("0.00");

        for (Categoria c : BDConect.getCategorias())
            comboBoxCategoria.addItem(c);
        /*
        TableModelBD modeloT = new TableModelBD("Material");
        for (Material m : BDConect.getMateriales()) {
            modeloT.adicionarMaterial(
                    m.getNombreMaterial()
            );
        }
        tableMateriales.setModel(modeloT);
        tableMateriales.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);*/

        //Tabla Tratamientos Paquete Disponibles
        TableModelBD modeloT = new TableModelBD("Material");
        String[] columnNamesPersonal = {"Nombre"};

        modeloT.setColumnIdentifiers(columnNamesPersonal);
        for (Material m : BDConect.getMateriales()) {
            if(getTratActu() != null && getTratActu().getMateriales().contains(m)){
                Object[] newRow = new Object[]{m};
                modeloT.addRow(newRow);
                System.out.println("PASOOOO POR AQUI");
            }
        }
        tableMateriales.setModel(modeloT);
        tableMateriales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Tabla Tratamientos
        TableModelBD modeloT2 = new TableModelBD("Material");
        modeloT2.setColumnIdentifiers(columnNamesPersonal);
        for (Material m : BDConect.getMateriales()) {
            if(getTratActu() != null && !getTratActu().getMateriales().contains(m)){
                Object[] newRow = new Object[]{m};
                modeloT2.addRow(newRow);
            }
        }
        tableDisponibles.setModel(modeloT2);


        if(!getTitle().equalsIgnoreCase("Actualizar Tratamiento")){
            modeloT2.setColumnIdentifiers(columnNamesPersonal);
            for (Material m : BDConect.getMateriales()) {
                System.out.println("AAAAAAAAAAAAAAAa");
                Object[] newRow = new Object[]{m};
                modeloT2.addRow(newRow);
            }
            tableDisponibles.setModel(modeloT2);
            tableDisponibles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        tableDisponibles.getTableHeader().setReorderingAllowed(false);
        tableMateriales.getTableHeader().setReorderingAllowed(false);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                if(getTitle().equalsIgnoreCase("Actualizar Tratamiento")){
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


        FlechaDerechaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(!getTitle().equalsIgnoreCase("Actualizar Tratamiento")){
                    int indice = tableMateriales.getSelectedRow();
                    if(indice != -1){
                        Material trat = (Material) tableMateriales.getValueAt(indice, 0);
                        ((DefaultTableModel) tableMateriales.getModel()).removeRow(indice);
                        ((DefaultTableModel) tableDisponibles.getModel()).addRow(
                                new Object[]{trat}
                        );
                    }

                }else{
                    int indice = tableMateriales.getSelectedRow();
                    if(indice != -1){
                        Material trat = (Material) tableMateriales.getValueAt(indice, 0);

                        try {
                            ServicesLocator.materialTratamientoService().eliminarMaterialTratamiento(trat.getCodMaterial(), getTratActu().getCodTratamiento(), getTratActu().getCodCategoria());
                            ((DefaultTableModel) tableMateriales.getModel()).removeRow(indice);
                            ((DefaultTableModel) tableDisponibles.getModel()).addRow(
                                    new Object[]{trat}
                            );
                        } catch (SQLException ex) {
                            ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                        }
                    }

                }

            }
        });
        FlechaIzquierdaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(!getTitle().equalsIgnoreCase("Actualizar Tratamiento")){
                    int indice = tableDisponibles.getSelectedRow();
                    if(indice != -1){
                        Material trat = (Material) tableDisponibles.getValueAt(indice, 0);
                        ((DefaultTableModel) tableDisponibles.getModel()).removeRow(indice);
                        ((DefaultTableModel) tableMateriales.getModel()).addRow(
                                new Object[]{trat}
                        );
                    }

                }else{
                    int indice = tableDisponibles.getSelectedRow();
                    if(indice != -1){
                        Material trat = (Material) tableDisponibles.getValueAt(indice, 0);

                        try {
                            ServicesLocator.materialTratamientoService().insertarMaterialTratamiento(trat.getCodMaterial(), getTratActu().getCodTratamiento(), getTratActu().getCodCategoria());
                            ((DefaultTableModel) tableDisponibles.getModel()).removeRow(indice);
                            ((DefaultTableModel) tableMateriales.getModel()).addRow(
                                    new Object[]{trat}
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
        String descripcion =textAreaDescripcion.getText();
        int frecuencia =Integer.parseInt(tFFrecuencia.getText());
        int duracion =Integer.parseInt(tFDuracion.getText());
        double precio = Double.parseDouble(tFPrecio.getText());
        UUID codCategoria = ((Categoria)comboBoxCategoria.getSelectedItem()).getCodCategoria();
        String Categoria = ((Categoria)comboBoxCategoria.getSelectedItem()).getNombreCategoria();


        ArrayList<Material>listaM = new ArrayList<Material>();
        int iMax = tableMateriales.getRowCount();
        for(int i = 0; i < iMax; i++){
            Material a = (Material) tableMateriales.getValueAt(i,0);
            listaM.add(a);
        }

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea insertar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Tratamiento Trat = new Tratamiento(nombre, descripcion,
            frecuencia,duracion,precio,codCategoria);
            for(Material md :listaM) {
                Trat.getMateriales().add(md);
            }
            BDConect.getTratamientos().add(Trat);
            ServicesLocator.tratamientoService().insertarTratamiento(Trat.getCodTratamiento(),Trat.getNombreTratamiento(),Trat.getDescripcion(),Trat.getFrecuenciaSolicitudMensual(),Trat.getDuracion(),Trat.getPrecio(),Trat.getCodCategoria());
            ServicesLocator.tratamientoService().añadirMaterialesTratamiento(Trat);
            dispose();
        }
    }

    private void onOKUp() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        String nombre = tFNombre.getText();
        String descripcion =textAreaDescripcion.getText();
        int frecuencia =Integer.parseInt(tFFrecuencia.getText());
        int duracion =Integer.parseInt(tFDuracion.getText());
        double precio = Double.parseDouble(tFPrecio.getText());
        UUID codCategoria = ((Categoria)comboBoxCategoria.getSelectedItem()).getCodCategoria();
        String Categoria = ((Categoria)comboBoxCategoria.getSelectedItem()).getNombreCategoria();


        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea modificar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Tratamiento Trat = new Tratamiento(getTratActu().getCodTratamiento(),nombre, descripcion,
                    frecuencia,duracion,precio,codCategoria);

            //BDConect.getTratamientos().add(Trat);
            ServicesLocator.tratamientoService().actualizarTratamiento(Trat.getCodTratamiento(),Trat.getNombreTratamiento(),Trat.getDescripcion(),Trat.getFrecuenciaSolicitudMensual(),Trat.getDuracion(),Trat.getPrecio(),Trat.getCodCategoria());
            BDConect.getTratamientos().set(BDConect.getTratamientos().indexOf(getTratActu()), Trat);
            //TratamientoService.eliminarMaterialesTratamiento(Trat);
            //TratamientoService.añadirMaterialesTratamiento(Trat);
            dispose();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Tratamiento getTratActu() {
        return TratActu;
    }

    public static void setTratActu(Tratamiento tratActu) {
        TratActu = tratActu;
    }

    public JTextFieldNuevo gettFNombre() {
        return tFNombre;
    }

    public JTextFieldNuevo gettFDuracion() {
        return tFDuracion;
    }

    public JTextFieldNuevo gettFFrecuencia() {
        return tFFrecuencia;
    }

    public DecimalTextField gettFPrecio() {
        return tFPrecio;
    }

    public JComboBox getComboBoxCategoria() {
        return comboBoxCategoria;
    }

    public JTextField getTextAreaDescripcion() {
        return textAreaDescripcion;
    }

    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }

}

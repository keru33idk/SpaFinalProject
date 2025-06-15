package Interface;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.Models.Area;
import Logic.Models.Material;
import Logic.Models.Personal;
import Logic.Models.Tratamiento;
import Logic.services.EmpleadoService;
import Logic.services.PaqueteTratamientoService;
import Logic.services.ServicesLocator;
import Logic.services.SuplenteService;
import utils.JTextFieldNuevo;
import utils.ManejadorExcepcionesSQL;
import utils.TableModelBD;
import utils.ValidarCampos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class InsertUpdateEmpleado extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel nombreLabel;
    private JTextFieldNuevo textNombre;
    private JLabel EspecialidadLabel;
    private JTextFieldNuevo textFieldEspecial;
    private JLabel HorasLabel;
    private JTextFieldNuevo textFieldHoras;
    private JLabel DNILabel;
    private JTextFieldNuevo textFieldDNI;
    private JTextField textFieldDirecc;
    private JLabel direccionLabel;
    private JTextFieldNuevo textFieldTelefono;
    private JTextFieldNuevo textFieldDistrito;
    private JComboBox<Area> AreacomboBox1;
    private JComboBox TratcomboBox;
    private JCheckBox checkBoxSuplente;
    private JTable tableSuplentesPersonal;
    private JButton btnIzquierda;
    private JButton btnDerecha;
    private JTable tableSuplentes;
    private JLabel Warning;
    private static ControllerSpa BDConect;
    private static Personal PActu;

    public InsertUpdateEmpleado(JFrame inter, String titulo) {
        super(inter, titulo, true);
        if(getTitle().equalsIgnoreCase("Actualizar Empleado")){
            buttonOK.setText("Actualizar");
            Warning.setText("Advertencia: Los suplentes que actualices aquí se actualizan en tiempo real en la BD sin necesidad de pulsar actualizar, proceder con precaución");

        }
        textFieldDNI.setLimite(11);
        textFieldDNI.setNumeros(true);
        textFieldDNI.setSinEspacios(true);
        textFieldTelefono.setLimite(8);
        textFieldTelefono.setNumeros(true);
        textFieldTelefono.setSinEspacios(true);
        textFieldHoras.setNumeros(true);
        textFieldHoras.setLimite(3);
        textNombre.setLetras(true);
        textFieldEspecial.setLetras(true);
        checkBoxSuplente.setSelected(true);
        tableSuplentesPersonal.setEnabled(false);
        tableSuplentes.setEnabled(false);
        AreacomboBox1.setEnabled(false);
        TratcomboBox.setEnabled(false);

        if(getPActu() != null && !getPActu().isEmpleado()){
            checkBoxSuplente.setSelected(false);
            tableSuplentesPersonal.setEnabled(true);
            tableSuplentes.setEnabled(true);
            AreacomboBox1.setEnabled(true);
            TratcomboBox.setEnabled(true);
        }



        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        TableModelBD modeloT = new TableModelBD("Personal");
        String[] columnNamesPersonal = {"Nombre", "DNI"};

        modeloT.setColumnIdentifiers(columnNamesPersonal);
        for (Personal p : BDConect.getEmpleados()) {
            if(getPActu() != null && getPActu().getSuplentes().contains(p)  && p.isEmpleado() && !p.equals(getPActu())){
                System.out.println("AAAAAAAAAAAAAAAa");
                Object[] newRow = new Object[]{p, p.getDni()};
                modeloT.addRow(newRow);
            }
        }
        tableSuplentesPersonal.setModel(modeloT);
        tableSuplentesPersonal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableModelBD modeloT2 = new TableModelBD("Personal");
        modeloT2.setColumnIdentifiers(columnNamesPersonal);
        for (Personal p : BDConect.getEmpleados()) {
            if(getPActu() != null && !getPActu().getSuplentes().contains(p) && p.isEmpleado() && !p.equals(getPActu())){
                System.out.println("BBBBBBBBB");
                Object[] newRow = new Object[]{p, p.getDni()};
                modeloT2.addRow(newRow);
            }
        }
        tableSuplentes.setModel(modeloT2);

        if(!getTitle().equalsIgnoreCase("Actualizar Empleado")){
            modeloT2.setColumnIdentifiers(columnNamesPersonal);
            for (Personal p : BDConect.getEmpleados()) {
                if(p.isEmpleado()  && !p.equals(getPActu())){
                System.out.println("AAAAAAAAAAAAAAAa");
                Object[] newRow = new Object[]{p};
                modeloT2.addRow(newRow);
                }
            }
            tableSuplentes.setModel(modeloT2);
            tableSuplentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        for(Area a : BDConect.getAreas())
            AreacomboBox1.addItem(a);
        for(Tratamiento t : BDConect.getTratamientos()){
            TratcomboBox.addItem(t);
        }
        tableSuplentes.getTableHeader().setReorderingAllowed(false);
        tableSuplentesPersonal.getTableHeader().setReorderingAllowed(false);

        btnIzquierda.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               try {
                                                   if (ConnectionBD.getConn().isClosed())
                                                       ConnectionBD.connect();
                                               } catch (SQLException ex) {
                                                   ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                                               }
                                               if (!getTitle().equalsIgnoreCase("Actualizar Empleado")) {
                                                   int indice = tableSuplentes.getSelectedRow();
                                                   if (indice != -1) {
                                                       Personal Pe = (Personal) tableSuplentes.getValueAt(indice, 0);
                                                       ((DefaultTableModel) tableSuplentes.getModel()).removeRow(indice);
                                                       ((DefaultTableModel) tableSuplentesPersonal.getModel()).addRow(
                                                               new Object[]{Pe,
                                                                       BDConect.getEmpleados().stream().filter(c -> c.getDni().equals(Pe.getDni())).findAny().orElse(null)
                                                               }
                                                       );
                                                   }

                                               } else {
                                                   int indice = tableSuplentes.getSelectedRow();
                                                   if (indice != -1) {
                                                       Personal Pe = (Personal) tableSuplentes.getValueAt(indice, 0);
                                                       try {
                                                           ServicesLocator.suplenteService().insertarSuplente(Pe.getIdEmpleado(), getPActu().getIdEmpleado());
                                                           ((DefaultTableModel) tableSuplentes.getModel()).removeRow(indice);
                                                           ((DefaultTableModel) tableSuplentesPersonal.getModel()).addRow(
                                                                   new Object[]{Pe,
                                                                           BDConect.getEmpleados().stream().filter(c -> c.getDni().equals(Pe.getDni())).findAny().orElse(null)
                                                                   }
                                                           );
                                                       } catch (SQLException ex) {
                                                           ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                                                       }
                                                   }

                                               }

                                           }
                                       });

        btnDerecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(!getTitle().equalsIgnoreCase("Actualizar Empleado")){
                    int indice = tableSuplentesPersonal.getSelectedRow();
                    if(indice != -1){
                        Personal Pe = (Personal) tableSuplentesPersonal.getValueAt(indice, 0);
                        ((DefaultTableModel) tableSuplentesPersonal.getModel()).removeRow(indice);
                        ((DefaultTableModel) tableSuplentes.getModel()).addRow(
                                new Object[]{Pe,Pe.getDni() }
                        );
                    }

                }else{
                    int indice = tableSuplentesPersonal.getSelectedRow();
                    if(indice != -1){
                        Personal Pe = (Personal) tableSuplentesPersonal.getValueAt(indice, 0);

                        try {
                            ServicesLocator.suplenteService().eliminarSuplente(Pe.getIdEmpleado(), getPActu().getIdEmpleado());
                            ((DefaultTableModel) tableSuplentesPersonal.getModel()).removeRow(indice);
                            ((DefaultTableModel) tableSuplentes.getModel()).addRow(
                                    new Object[]{Pe,Pe.getDni()}
                            );
                        } catch (SQLException ex) {
                            ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                        }
                    }

                }

            }
        });


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                if(getTitle().equalsIgnoreCase("Actualizar Empleado")){
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
            }}
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
        
        checkBoxSuplente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBoxSuplente.isSelected()){
                    if(tableSuplentesPersonal.getRowCount() > 0 && getTitle().equalsIgnoreCase("Actualizar Empleado")){
                        JOptionPane.showMessageDialog(
                                null,
                                "Un empleado suplente no puede tener suplentes." ,
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        checkBoxSuplente.setSelected(false);

                    }else{
                    tableSuplentesPersonal.setEnabled(false);
                    tableSuplentes.setEnabled(false);
                    AreacomboBox1.setEnabled(false);
                    TratcomboBox.setEnabled(false);
                    }


                }else{
                    tableSuplentesPersonal.setEnabled(true);
                    tableSuplentes.setEnabled(true);
                    AreacomboBox1.setEnabled(true);
                    TratcomboBox.setEnabled(true);

                }

            }
        });
    }

    private void onOK() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        String nombre = textNombre.getText();
        String especialidad = textFieldEspecial.getText();
        int horas = Integer.parseInt(textFieldHoras.getText());
        String dni = textFieldDNI.getText();
        String direcc = textFieldDirecc.getText();
        String telefono = textFieldTelefono.getText();
        String distrito = textFieldDistrito.getText();
        boolean isSuplente = checkBoxSuplente.isSelected();
        String area = null;
        UUID tratamiento = null;
        UUID codCategoria = null;
        ArrayList<Personal>listaPSuplente = new ArrayList<Personal>();
        if(!isSuplente){
            area = ((Area)AreacomboBox1.getSelectedItem()).getArea();
            tratamiento = ((Tratamiento)TratcomboBox.getSelectedItem()).getCodTratamiento();
            codCategoria = ((Tratamiento)TratcomboBox.getSelectedItem()).getCodCategoria();
            int iMax = tableSuplentesPersonal.getRowCount();
            for(int i = 0; i < iMax; i++){
                Personal p = (Personal) tableSuplentesPersonal.getValueAt(i,0);
                listaPSuplente.add(p);
            }

        }


        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea insertar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Personal p = new Personal(nombre,especialidad,horas,dni,direcc,telefono,distrito,isSuplente,area,tratamiento,codCategoria);
            BDConect.getEmpleados().add(p);
            ServicesLocator.empleadoService().insertarEmpleado(p.getIdEmpleado(),nombre,especialidad,horas,dni,direcc,telefono,distrito,area,tratamiento,codCategoria);
            if(!p.isEmpleado()) {
                for (Personal pe : listaPSuplente) {
                    if(ConnectionBD.getConn().isClosed())
                        ConnectionBD.connect();
                    ServicesLocator.suplenteService().insertarSuplente(pe.getIdEmpleado(),p.getIdEmpleado());
                }
            }
            dispose();
        }

    }

    private void onOKUp() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        String nombre = textNombre.getText();
        String especialidad = textFieldEspecial.getText();
        int horas = Integer.parseInt(textFieldHoras.getText());
        String dni = textFieldDNI.getText();
        String direcc = textFieldDirecc.getText();
        String telefono = textFieldTelefono.getText();
        String distrito = textFieldDistrito.getText();
        boolean isSuplente = checkBoxSuplente.isSelected();
        ArrayList<Personal> listaPSuplente = new ArrayList<>();
        String area = null;
        UUID tratamiento = null;
        UUID codCategoria = null;
        if(!isSuplente){
            area = ((Area)AreacomboBox1.getSelectedItem()).getArea();
            tratamiento = ((Tratamiento)TratcomboBox.getSelectedItem()).getCodTratamiento();
            codCategoria = ((Tratamiento)TratcomboBox.getSelectedItem()).getCodCategoria();

            int iMax = tableSuplentesPersonal.getRowCount();
            for(int i = 0; i < iMax; i++){
                Personal p = (Personal) tableSuplentesPersonal.getValueAt(i,0);
                listaPSuplente.add(p);
            }
        }


        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea actualizar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {

            Personal p = new Personal(getPActu().getIdEmpleado(),nombre,especialidad,horas,dni,direcc,telefono,distrito,isSuplente,area,tratamiento,codCategoria);

            ServicesLocator.empleadoService().actualizarEmpleado(p.getIdEmpleado(),nombre,especialidad,horas,dni,direcc,telefono,distrito,area,tratamiento,codCategoria);
            BDConect.getEmpleados().set(BDConect.getEmpleados().indexOf(getPActu()), p);

            dispose();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }

    public JTextFieldNuevo getTextNombre() {
        return textNombre;
    }

    public JTextFieldNuevo getTextFieldEspecial() {
        return textFieldEspecial;
    }

    public JTextFieldNuevo getTextFieldHoras() {
        return textFieldHoras;
    }

    public JTextFieldNuevo getTextFieldDNI() {
        return textFieldDNI;
    }

    public JTextField getTextFieldDirecc() {
        return textFieldDirecc;
    }

    public JTextFieldNuevo getTextFieldTelefono() {
        return textFieldTelefono;
    }

    public JTextFieldNuevo getTextFieldDistrito() {
        return textFieldDistrito;
    }

    public JComboBox<Area> getAreacomboBox1() {
        return AreacomboBox1;
    }

    public JComboBox getTratcomboBox() {
        return TratcomboBox;
    }

    public JCheckBox getCheckBox1() {
        return checkBoxSuplente;
    }

    public static void setPActu(Personal PAct) {
        PActu = PAct;
    }

    public static Personal getPActu() {
        return PActu;
    }
}

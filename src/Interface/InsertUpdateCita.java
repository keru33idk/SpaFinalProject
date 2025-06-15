package Interface;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.Models.Cita;
import Logic.Models.Cliente;
import Logic.Models.Tratamiento;
import Logic.services.ServicesLocator;
import utils.ManejadorExcepcionesSQL;
import utils.ValidarCampos;


import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class InsertUpdateCita extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTextArea textAreaObservaciones;
    private JTextField txtFiltroCliente;
    private JTextField txtFiltroTratamiento;

    private List<Cliente> todosClientes;
    private List<Tratamiento> todosTratamientos;
    private Cita CitaUp;

    private static ControllerSpa BDConect;

    public InsertUpdateCita(JFrame inter, String titulo) {
        super(inter, titulo, true);
        if(getTitle().equalsIgnoreCase("Actualizar Cita")){
            buttonOK.setText("Actualizar");
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        txtFiltroCliente.putClientProperty("JTextField.placeholderText", "Filtrar cliente...");
        txtFiltroTratamiento.putClientProperty("JTextField.placeholderText", "Filtrar tratamiento...");


        todosClientes = BDConect.getClientes();
        todosTratamientos = BDConect.getTratamientos();

        // Configura los listeners para los TextField
        txtFiltroCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarClientes();
            }
        });

        txtFiltroTratamiento.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTratamientos();
            }
        });


        for(Cliente c : BDConect.getClientes()){
            comboBox1.addItem(c);
        }
        for(Tratamiento c : BDConect.getTratamientos()){
            comboBox2.addItem(c);
        }


        Calendar minfecha = Calendar.getInstance();
        minfecha.set(LocalDate.now().getYear(),LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth());
        Calendar maxfecha = Calendar.getInstance();
        maxfecha.set(2100, Calendar.DECEMBER, 31);

        Calendar fecha = Calendar.getInstance();
        fecha.set(LocalDate.now().getYear(),LocalDate.now().getMonthValue()-1, LocalDate.now().getDayOfMonth());
        fecha.add(Calendar.DAY_OF_MONTH, 1);

        SpinnerDateModel model = new SpinnerDateModel();
        model.setStart(minfecha.getTime());
        model.setEnd(maxfecha.getTime());
        model.setValue(fecha.getTime());

        spinner1.setModel(model);
        spinner2.setModel(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner1, "dd/MM/yyyy");
        spinner1.setEditor(dateEditor);
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(spinner2, "HH:mm:ss");
        spinner2.setEditor(dateEditor2);



        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                    if(comboBox1.getSelectedItem() == null || comboBox2.getSelectedItem() == null){
                        JOptionPane.showMessageDialog(
                                null,
                                "Hay un campo que no fue seleccionado",
                                "Error de Base de Datos",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }else{
                        if(getTitle().equalsIgnoreCase("Actualizar Cita")){
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
    }

    private void onOK() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea insertar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Cliente cliente = (Cliente) comboBox1.getSelectedItem();
            Tratamiento tratamiento = (Tratamiento) comboBox2.getSelectedItem();
            Date fecha = (Date)spinner1.getValue();
            Date hora = (Date)spinner2.getValue();
            String Obser = textAreaObservaciones.getText();

            Cita c = new Cita(tratamiento.getCodTratamiento(),tratamiento.getCodCategoria(),cliente.getIdCliente(),new java.sql.Date(fecha.getTime()), new java.sql.Time(hora.getTime()), Obser);
            BDConect.getCitas().add(c);
            ServicesLocator.citaService().insertarCita(c.getCodSolicitud(),tratamiento.getCodTratamiento(),tratamiento.getCodCategoria(),cliente.getIdCliente(),new java.sql.Date(fecha.getTime()), new java.sql.Time(hora.getTime()), Obser);
            dispose();

        }
    }

    private void onOKUp() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea modificar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Cliente cliente = (Cliente) comboBox1.getSelectedItem();
            Tratamiento tratamiento = (Tratamiento) comboBox2.getSelectedItem();
            Date fecha = (Date)spinner1.getValue();
            Date hora = (Date)spinner2.getValue();
            String Obser = textAreaObservaciones.getText();

            Cita c = new Cita(CitaUp.getCodSolicitud(),tratamiento.getCodTratamiento(),tratamiento.getCodCategoria(),cliente.getIdCliente(),new java.sql.Date(fecha.getTime()), new java.sql.Time(hora.getTime()), Obser);
            ServicesLocator.citaService().actualizarCita(c.getCodSolicitud(),tratamiento.getCodTratamiento(),tratamiento.getCodCategoria(),cliente.getIdCliente(),new java.sql.Date(fecha.getTime()), new java.sql.Time(hora.getTime()), Obser);
            BDConect.getCitas().set(BDConect.getCitas().indexOf(getCitaUp()),c);
            dispose();

        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JComboBox getComboBox2() {
        return comboBox2;
    }

    public JSpinner getSpinner1() {
        return spinner1;
    }

    public JSpinner getSpinner2() {
        return spinner2;
    }

    public Cita getCitaUp() {
        return CitaUp;
    }

    public void setCitaUp(Cita citaUp) {
        CitaUp = citaUp;
    }

    public JTextArea getTextAreaObservaciones() {
        return textAreaObservaciones;
    }

    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }

    private void filtrarClientes() {
        String filtro = txtFiltroCliente.getText().toLowerCase();
        List<Cliente> clientesFiltrados = todosClientes.stream()
                .filter(cliente ->
                        cliente.getNombreCliente().toLowerCase().contains(filtro) ||
                                String.valueOf(cliente.getIdCliente()).contains(filtro))
                .collect(Collectors.toList());

        actualizarComboBoxClientes(clientesFiltrados);
    }

    private void filtrarTratamientos() {
        String filtro = txtFiltroTratamiento.getText().toLowerCase();
        List<Tratamiento> tratamientosFiltrados = todosTratamientos.stream()
                .filter(tratamiento ->
                        tratamiento.getNombreTratamiento().toLowerCase().contains(filtro) ||
                                String.valueOf(tratamiento.getCodTratamiento()).contains(filtro) ||
                                String.valueOf(tratamiento.getCodCategoria()).contains(filtro))
                .collect(Collectors.toList());

        actualizarComboBoxTratamientos(tratamientosFiltrados);
    }

    private void actualizarComboBoxClientes(List<Cliente> clientes) {
        comboBox1.removeAllItems();
        for(Cliente c : clientes) {
            comboBox1.addItem(c);
        }
    }

    private void actualizarComboBoxTratamientos(List<Tratamiento> tratamientos) {
        comboBox2.removeAllItems();
        for(Tratamiento t : tratamientos) {
            comboBox2.addItem(t);
        }
    }

    public JTextField getTxtFiltroCliente() {
        return txtFiltroCliente;
    }
}



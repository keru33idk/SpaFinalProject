package Interface;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.Models.Cliente;
import Logic.Models.Paquete;
import Logic.Models.PaqueteVendido;
import Logic.Models.Tratamiento;
import Logic.services.ServicesLocator;
import utils.ManejadorExcepcionesSQL;
import utils.ValidarCampos;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InsertUpdatePaqueteVendido extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBoxCliente;
    private JComboBox comboBoxPaquetes;
    private JSpinner spinner1FechaInicio;
    private JSpinner spinner2FechaFin;
    private JTextField textPaquete;
    private JTextField textcliente;
    private static ControllerSpa BDConect;
    private PaqueteVendido PaqVendidoActu;
    private List<Cliente> todosClientes;
    private List<Paquete> todosPaquetes;


    public InsertUpdatePaqueteVendido(JFrame inter, String titulo) {
        super(inter, titulo, true);
        if(getTitle().equalsIgnoreCase("Actualizar Paquete Vendido")){
            buttonOK.setText("Actualizar");
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        textPaquete.putClientProperty("JTextField.placeholderText", "Filtrar paquete...");
        textcliente.putClientProperty("JTextField.placeholderText", "Filtrar cliente...");


        todosClientes = BDConect.getClientes();
        todosPaquetes = BDConect.getPaquetes();

        textcliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarClientes();
            }
        });
        textPaquete.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarPaquete();
            }
        });

        for(Cliente c : BDConect.getClientes()){
            comboBoxCliente.addItem(c);
        }
        for(Paquete c : BDConect.getPaquetes()){
            comboBoxPaquetes.addItem(c);
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

        SpinnerDateModel model2 = new SpinnerDateModel();
        model2.setStart(minfecha.getTime());
        model2.setEnd(maxfecha.getTime());
        model2.setValue(fecha.getTime());

        spinner1FechaInicio.setModel(model);
        spinner2FechaFin.setModel(model2);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinner1FechaInicio, "dd/MM/yyyy");
        spinner1FechaInicio.setEditor(dateEditor);
        JSpinner.DateEditor dateEditor2 = new JSpinner.DateEditor(spinner2FechaFin, "dd/MM/yyyy");
        spinner2FechaFin.setEditor(dateEditor2);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                    if(comboBoxCliente.getSelectedItem() == null || comboBoxPaquetes.getSelectedItem() == null){
                        JOptionPane.showMessageDialog(
                                null,
                                "Hay un campo que no fue seleccionado",
                                "Error de Base de Datos",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }else{
                if(getTitle().equalsIgnoreCase("Actualizar Paquete Vendido")){
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
            }}}
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
            UUID codCliente = ((Cliente) comboBoxCliente.getSelectedItem()).getIdCliente();
            UUID codPaq = ((Paquete) comboBoxPaquetes.getSelectedItem()).getCodPaquete();
            java.sql.Timestamp fechaC = new Timestamp(System.currentTimeMillis());
            Date fechaI = (Date)spinner1FechaInicio.getValue();
            Date fechaF = (Date)spinner2FechaFin.getValue();

            PaqueteVendido p = new PaqueteVendido(codCliente, codPaq, fechaC, new java.sql.Date(fechaI.getTime()), new java.sql.Date(fechaF.getTime()));
            BDConect.getPaqueteVendidos().add(p);
            ServicesLocator.paqueteVendidoService().insertarPaqueteVendido(codCliente,codPaq, fechaC, p.getFechaInicio(), p.getFechaFin());
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
            UUID codCliente = ((Cliente) comboBoxCliente.getSelectedItem()).getIdCliente();
            UUID codPaq = ((Paquete) comboBoxPaquetes.getSelectedItem()).getCodPaquete();
            Date fechaI = (Date)spinner1FechaInicio.getValue();
            Date fechaF = (Date)spinner2FechaFin.getValue();

            PaqueteVendido p = new PaqueteVendido(codCliente, codPaq, getPaqVendidoActu().getFechaCompra(), new java.sql.Date(fechaI.getTime()), new java.sql.Date(fechaF.getTime()));
            ServicesLocator.paqueteVendidoService().actualizarPaqueteVendido(codCliente,codPaq, getPaqVendidoActu().getFechaCompra(), p.getFechaInicio(), p.getFechaFin());
            BDConect.getPaqueteVendidos().set(BDConect.getPaqueteVendidos().indexOf(getPaqVendidoActu()), p);
            dispose();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public PaqueteVendido getPaqVendidoActu() {
        return PaqVendidoActu;
    }

    public void setPaqVendidoActu(PaqueteVendido paqVendidoActu) {
        PaqVendidoActu = paqVendidoActu;
    }

    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }

    public JComboBox getComboBoxCliente() {
        return comboBoxCliente;
    }

    public JComboBox getComboBoxPaquetes() {
        return comboBoxPaquetes;
    }

    public JSpinner getSpinner1FechaInicio() {
        return spinner1FechaInicio;
    }

    public JSpinner getSpinner2FechaFin() {
        return spinner2FechaFin;
    }

    private void filtrarClientes() {
        String filtro = textcliente.getText().toLowerCase();
        List<Cliente> clientesFiltrados = todosClientes.stream()
                .filter(cliente ->
                        cliente.getNombreCliente().toLowerCase().contains(filtro) ||
                                String.valueOf(cliente.getIdCliente()).contains(filtro))
                .collect(Collectors.toList());

        actualizarComboBoxClientes(clientesFiltrados);
    }

    private void filtrarPaquete() {
        String filtro = textPaquete.getText().toLowerCase();
        List<Paquete> paquetesFiltrados = todosPaquetes.stream()
                .filter(paquete ->
                        paquete.getNombrePaquete().toLowerCase().contains(filtro) ||
                                String.valueOf(paquete.getCodPaquete()).contains(filtro))
                .collect(Collectors.toList());

        actualizarComboBoxPaquetes(paquetesFiltrados);
    }

    private void actualizarComboBoxClientes(List<Cliente> clientes) {
        comboBoxCliente.removeAllItems();
        for(Cliente c : clientes) {
            comboBoxCliente.addItem(c);
        }
    }

    private void actualizarComboBoxPaquetes(List<Paquete> paquetes) {
        comboBoxPaquetes.removeAllItems();
        for(Paquete t : paquetes) {
            comboBoxPaquetes.addItem(t);
        }
    }

    public JTextField getTextPaquete() {
        return textPaquete;
    }

    public JTextField getTextcliente() {
        return textcliente;
    }
}

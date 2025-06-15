package Interface;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.Models.Area;
import Logic.Models.Cliente;
import Logic.services.AreaService;
import Logic.services.ClienteService;
import Logic.services.ServicesLocator;
import utils.JTextFieldNuevo;
import utils.ManejadorExcepcionesSQL;
import utils.ValidarCampos;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class InsertUpdateCliente extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextFieldNuevo Campo_Texto_Nombre;
    private Cliente ClienteUP;
    private static ControllerSpa BDConect;

    public InsertUpdateCliente(JFrame inter, String titulo) {
        super(inter, titulo, true);
        Campo_Texto_Nombre.setLetras(true);
        if(getTitle().equalsIgnoreCase("Actualizar Cliente")){
            buttonOK.setText("Actualizar");
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                if(getTitle().equalsIgnoreCase("Actualizar Cliente")){
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
    }


    private void onOK() throws SQLException {

        if (ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();


        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea insertar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            String nombre = Campo_Texto_Nombre.getText();
            Cliente c = new Cliente(nombre);
            BDConect.getClientes().add(c);
            ServicesLocator.clienteService().insertarCliente(c.getIdCliente(), c.getNombreCliente());
            dispose();

        }
    }

    private void onOKUp() throws SQLException {

        if (ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();


        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea modificar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            String nombre = Campo_Texto_Nombre.getText().trim();
            Cliente c = new Cliente(getClienteUP().getIdCliente(), nombre);
            ServicesLocator.clienteService().actualizarCliente(c.getIdCliente(), c.getNombreCliente());
            BDConect.getClientes().set(BDConect.getClientes().indexOf(getClienteUP()), c);
            dispose();

        }
    }

        private void onCancel(){
            // add your code here if necessary
            dispose();
        }

    public Cliente getClienteUP() {
        return ClienteUP;
    }

    public JTextField getCampo_Texto_Nombre() {
        return Campo_Texto_Nombre;
    }

    public void setClienteUP(Cliente clienteUP) {
        ClienteUP = clienteUP;
    }

    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }
}


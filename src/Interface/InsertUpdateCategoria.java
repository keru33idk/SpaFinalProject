package Interface;

import Logic.Models.*;
import Logic.services.CategoriaService;
import Logic.services.ServicesLocator;
import utils.JTextFieldNuevo;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

import utils.JTextFieldNuevo;
import Logic.ConnectionBD;
import Logic.ControllerSpa;
import utils.ManejadorExcepcionesSQL;
import utils.ValidarCampos;


public class InsertUpdateCategoria extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextFieldNuevo tFCategoria;

    private Categoria catUp;

    private static ControllerSpa BDConect;

    public InsertUpdateCategoria(JFrame inter, String titulo) {
        super(inter, titulo, true);
        if(getTitle().equalsIgnoreCase("Actualizar Categoria")){
            buttonOK.setText("Actualizar");
        }
        tFCategoria.setLetras(true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                if(getTitle().equalsIgnoreCase("Actualizar Categoria")){
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

        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();


        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea insertar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Categoria c = new Categoria(tFCategoria.getText());
            BDConect.getCategorias().add(c);
            ServicesLocator.categoriaService().insertarCategoria(c.getCodCategoria(),c.getNombreCategoria());
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
            Categoria c = new Categoria(tFCategoria.getText());
            ServicesLocator.categoriaService().actualizarCategoria(getCatUp().getCodCategoria(),c.getNombreCategoria());
            BDConect.getCategorias().set(BDConect.getCategorias().indexOf(getCatUp()),c);
            dispose();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Categoria getCatUp() {
        return catUp;
    }

    public void setCatUp(Categoria catUp) {
        this.catUp = catUp;
    }

    public JTextFieldNuevo gettFCategoria() {
        return tFCategoria;
    }

    public void settFCategoria(JTextFieldNuevo tFCategoria) {
        this.tFCategoria = tFCategoria;
    }

    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }
}

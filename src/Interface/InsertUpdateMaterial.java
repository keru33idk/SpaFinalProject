package Interface;

import Logic.ControllerSpa;

import javax.swing.*;
import java.awt.event.*;
import Logic.Models.Material;
import Logic.services.MaterialService;
import Logic.services.ServicesLocator;
import utils.JTextFieldNuevo;


import java.sql.SQLException;


import Logic.ConnectionBD;
import utils.ManejadorExcepcionesSQL;
import utils.ValidarCampos;

public class InsertUpdateMaterial extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextFieldNuevo tFMaterial;
    private Material MActu;

    private static ControllerSpa BDConect;

    public  InsertUpdateMaterial (JFrame inter, String titulo) {
        super(inter, titulo, true);
        if(getTitle().equalsIgnoreCase("Actualizar Material")){
            buttonOK.setText("Actualizar");
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        tFMaterial.setLetras(true);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                if(getTitle().equalsIgnoreCase("Actualizar Material")){
                    try {
                        onOKUP();
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
            Material m = new Material(tFMaterial.getText());
            BDConect.getMateriales().add(m);
            ServicesLocator.materialService().insertarMaterial(m.getCodMaterial(),m.getNombreMaterial());
            dispose();
        }

    }

    private void onOKUP() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea modificar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            Material m = new Material(getMActu().getCodMaterial(),tFMaterial.getText());
            ServicesLocator.materialService().actualizarMaterial(m.getCodMaterial(),m.getNombreMaterial());
            BDConect.getMateriales().set(BDConect.getMateriales().indexOf(getMActu()), m);
            dispose();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Material getMActu() {
        return MActu;
    }

    public void setMActu(Material MActu) {
        this.MActu = MActu;
    }

    public JTextFieldNuevo gettFMaterial() {
        return tFMaterial;
    }

    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }
}
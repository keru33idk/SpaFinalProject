package Interface;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.Models.Area;
import Logic.services.AreaService;
import Logic.services.ServicesLocator;
import utils.JTextFieldNuevo;
import utils.ManejadorExcepcionesSQL;
import utils.ValidarCampos;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class InsertUpdateArea extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField Campo_texto_Nombre;
    private JTextFieldNuevo Campo_Texto_Cantidad;

    private Area AreaUp;

    private static ControllerSpa BDConect;

    public InsertUpdateArea(JFrame inter, String titulo) {
        super(inter, titulo, true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        if(getTitle().equalsIgnoreCase("Actualizar Area")){
            buttonOK.setText("Actualizar");
        }
        Campo_Texto_Cantidad.setNumeros(true);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                if(getTitle().equalsIgnoreCase("Actualizar Area")){
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
            int cant = Integer.parseInt(Campo_Texto_Cantidad.getText());
            String nombreArea = Campo_texto_Nombre.getText();
            Area A = new Area(nombreArea,cant);
            BDConect.getAreas().add(A);
            ServicesLocator.areaService().insertarArea(A.getArea(),A.getCantPersonalFijo());
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
            int cant = Integer.parseInt(Campo_Texto_Cantidad.getText());
            String nombreArea = Campo_texto_Nombre.getText();
            Area A = new Area(nombreArea,cant);
            BDConect.getAreas().add(A);
            ServicesLocator.areaService().actualizarArea(A.getArea(),A.getCantPersonalFijo(), getAreaUp().getArea());
            BDConect.getAreas().set(BDConect.getAreas().indexOf(getAreaUp()), A);
            dispose();

        }

    }

    public Area getAreaUp() {
        return AreaUp;
    }

    public void setAreaUp(Area areaUp) {
        AreaUp = areaUp;
    }

    public JTextField getCampo_texto_Nombre() {
        return Campo_texto_Nombre;
    }

    public JTextFieldNuevo getCampo_Texto_Cantidad() {
        return Campo_Texto_Cantidad;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void getWindow(ControllerSpa app) {
        BDConect = app;
    }
}

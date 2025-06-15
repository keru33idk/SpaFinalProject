package Interface;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.Models.User;
import Logic.services.ServicesLocator;
import Logic.services.UserService;
import com.formdev.flatlaf.FlatClientProperties;
import utils.ManejadorExcepcionesSQL;
import utils.ValidarCampos;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

public class InsertUpdateUsers extends JDialog {
    private static ControllerSpa BDConect;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombretextField1;
    private JPasswordField passwordField1;
    private JComboBox comboBox1;
    private static User usUpd;

    public InsertUpdateUsers(JFrame inter, String titulo) {
        super(inter, titulo, true);
        if(getTitle().equalsIgnoreCase("Actualizar Usuario")){
            buttonOK.setText("Actualizar");
        }
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        passwordField1.putClientProperty(FlatClientProperties.STYLE,
                "showRevealButton: true");

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(ValidarCampos.validarCamposNoVacios(contentPane)){
                    if(passwordField1.getPassword().length < 6 || passwordField1.getPassword().length > 16){
                        JOptionPane.showMessageDialog(
                                null,
                                "La contraseña debe tener entre 6 y 16 caracteres",
                                "Error de Base de Datos",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }else{
                    if(getTitle().equalsIgnoreCase("Actualizar Usuario")){
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

        String rol = (String) comboBox1.getSelectedItem();
        switch(rol){
            case "Administrador":
                rol = "admin";
                break;
            case "Atencion al Cliente":
                rol = "aten";
                break;
            case "Recursos Humanos":
                rol = "recur";
                break;
        }

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea insertar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            UserService.añadirUser(nombretextField1.getText(), String.valueOf(passwordField1.getPassword()), rol);
            dispose();
        }
        dispose();
    }

    private void onOKUP() throws SQLException {
        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();

        String rol = (String) comboBox1.getSelectedItem();
        switch(rol){
            case "Administrador":
                rol = "admin";
                break;
            case "Atencion al Cliente":
                rol = "aten";
                break;
            case "Recursos Humanos":
                rol = "recur";
                break;
        }

        int Option = JOptionPane.showConfirmDialog(null,
                "¿Está seguro que desea modificar este elemento",
                "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (Option == JOptionPane.YES_OPTION) {
            User u = new User(getUsUpd().getId(),getUsUpd().getNombre(), getUsUpd().getRol());
            UserService.UpdateUser(getUsUpd().getId(), nombretextField1.getText(), String.valueOf(passwordField1.getPassword()), rol);
            BDConect.getUsers().set(BDConect.getUsers().indexOf(getUsUpd()), u);
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

    public static User getUsUpd() {
        return usUpd;
    }

    public static void setUsUpd(User usUpds) {
        usUpd = usUpds;
    }

    public JTextField getNombretextField1() {
        return nombretextField1;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }
}

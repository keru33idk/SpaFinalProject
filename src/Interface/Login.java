package Interface;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.services.UserService;
import com.formdev.flatlaf.FlatClientProperties;
import utils.ManejadorExcepcionesSQL;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login {

    private JPanel Login;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton buttonLogin;
    private JLabel User;
    private JLabel logo;
    private static ControllerSpa BDConect;
    private static JFrame interfaz;

    public Login() {
        passwordField1.putClientProperty(FlatClientProperties.STYLE,
                "showRevealButton: true");
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String us = textField1.getText();
                String pass = new String(passwordField1.getPassword());
                ConnectionBD.setRole(us);
                ConnectionBD.connect();
                String rol = "";
                try {
                    rol = UserService.getUser(us, pass);
                    System.out.println(rol);
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                if(!rol.equalsIgnoreCase("")){
                    switch(rol){
                        case "admin":
                            ConnectionBD.setUser("Administracion");
                            break;
                        case "aten":
                            ConnectionBD.setUser("Atencion al Cliente");
                            break;
                        case "recur":
                            ConnectionBD.setUser("Recursos Humanos");
                            break;

                    }
                }else{
                    try {
                        ConnectionBD.getConn().close();
                        throw new SQLException("Contraseña o Usuario Incorrecto", "28P01");
                    } catch (SQLException ex) {
                        ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                    }

                }


                try {
                    if(!ConnectionBD.getConn().isClosed()){
                        VentanaPrincipal.getWindow(BDConect,interfaz);
                        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        interfaz.pack();
                        interfaz.setLocationRelativeTo(null);
                        interfaz.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        interfaz.setVisible(true);
                    }
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
            }
        });
    }

    public static void getWindow(ControllerSpa app, JFrame frame) {
        BDConect = app;
        interfaz = frame;

        interfaz.setContentPane(new Login().Login);

    }
}

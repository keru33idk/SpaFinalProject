package Interface.Reportes;

import Logic.ConnectionBD;
import Logic.ControllerSpa;
import Logic.Models.Cliente;
import Logic.services.ServicesLocator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmpleadosDeCliente extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;

    public EmpleadosDeCliente(JFrame inter, String tittle) throws SQLException {
        super(inter, tittle);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        if(ConnectionBD.getConn().isClosed())
            ConnectionBD.connect();
        ControllerSpa BDConect = new ControllerSpa();
        BDConect.cargarClientes();
        for(Cliente c : BDConect.getClientes()){
            comboBox1.addItem(c);
        }
        BDConect = null;

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

    private void onOK() {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("cliente", ((Cliente)comboBox1.getSelectedItem()).getNombreCliente());


        try {
            List<HashMap<String, Object>> dataList = new ArrayList<>();
            ResultSet rs = ServicesLocator.empleadoService().EmpleadosDeUnCliente(((Cliente) comboBox1.getSelectedItem()));
            while(rs.next()){
                HashMap<String, Object> fila = new HashMap<>();
                ResultSetMetaData metaData = rs.getMetaData();
                int col = metaData.getColumnCount();
                for(int i = 1; i<= col;i++){
                    String colName = metaData.getColumnName(i);
                    if(rs.getObject(i) instanceof BigDecimal){
                        fila.put(colName,((BigDecimal)rs.getObject(i)).doubleValue());
                    }else{
                        fila.put(colName,rs.getObject(i));
                    }
                }
                dataList.add(fila);
            }
            parametros.put("empleados", new JRBeanArrayDataSource(dataList.toArray()));

        } catch (SQLException ex) {
            System.out.println(ex);

        }
        try {
            // Verificar que el archivo existe
            File reportFile = new File("src/jasperReports/EmpleadosQueHanAtendidoCliente.jasper");
            if (!reportFile.exists()) {
                throw new FileNotFoundException("Archivo .jasper no encontrado: " + reportFile.getPath());
            }

            // Cargar usando JRLoader (más robusto)
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);

            // Llenar reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parametros,
                    new JREmptyDataSource()
            );

            // Exportar a PDF
            String outputPath = System.getProperty("user.home") + "/EmpleadosQueHanAtendidoCliente.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            System.out.println("PDF generado en: " + outputPath);
            JOptionPane.showMessageDialog(
                    null,
                    "PDF generado en: " + outputPath,
                    "PDF Generado Correctamenta",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (JRException | FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error al generar reporte",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}

package Interface.Reportes;

import Logic.services.ServicesLocator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class VentasPorMes extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;

    public VentasPorMes(JFrame inter, String tittle) {
        super(inter, tittle);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        // 1. Configuración CRUCIAL que debe ir AL INICIO (antes de cualquier operación con Jasper)

        System.setProperty("net.sf.jasperreports.export.pdf.glyph.renderer", "none"); // Usar "none" en lugar de "basic"
        System.setProperty("net.sf.jasperreports.export.pdf.fonts.ignore", "true");
        System.setProperty("net.sf.jasperreports.export.pdf.classic.legacy.mode", "true");

        HashMap<String, Object> parametros = new HashMap<>();
        try {
            ResultSet rs = ServicesLocator.citaService().tratamientoVendidosReporte(comboBox1.getSelectedIndex()+1);
            rs.next();
            int cant = rs.getInt(1);
            Double ventas = rs.getDouble(2);
            rs = ServicesLocator.paqueteVendidoService().PaquetesVendidosReporte(comboBox1.getSelectedIndex()+1);
            rs.next();
            int cantP = rs.getInt(1);
            Double ventasP = rs.getDouble(2);
            parametros.put("mes_parametro", (String) comboBox1.getSelectedItem());
            parametros.put("cantidad_paquetes", cantP);
            parametros.put("cantidad_tratamientos_vendidos", cant);
            parametros.put("paquetes_vendidos", ventasP);
            parametros.put("tratamientos_vendidos", ventas);
        } catch (SQLException ex) {
            System.out.println(ex);

        }
        try {
            // Verificar que el archivo existe
            File reportFile = new File("src/jasperReports/Reporte Venta.jasper");
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
            String outputPath = System.getProperty("user.home") + "/Reporte_Venta.pdf";
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



package Interface.Reportes;

import Logic.ConnectionBD;
import Logic.services.ServicesLocator;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import utils.ManejadorExcepcionesSQL;
import utils.MaterialesDiscrepanciaReporte;
import utils.TratamientoDiscrepanciaReporte;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ReportesMenu {
    private JButton ventasPorMesButton;
    private JButton informeDiscrepanciaButton;
    private JPanel Menu;
    private JButton a3TratamientosMásSolicitadosButton;
    private JButton tratamientosYPaquetesDeButton;
    private JButton empleadosQueHanAtendidoButton;
    private static JFrame interfaz;

    public ReportesMenu() {
        ventasPorMesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentasPorMes frame = new VentasPorMes(interfaz, "Ventas Por Mes");
                frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        informeDiscrepanciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> parametros = new HashMap<>();
                try {
                    ConnectionBD.connect();
                    String sql = "SELECT tratamiento.codtratamiento, \n" +
                            "categoria.codcategoria, \n" +
                            "tratamiento.nombretratamiento, \n" +
                            "COUNT(*) AS planificados, \n" +
                            "SUM(CASE WHEN cita.fecha < CURRENT_DATE THEN 1 ELSE 0 END) AS realizados\n" +
                            "\n" +
                            "FROM cita JOIN tratamiento ON cita.tratamiento__codtratamiento = tratamiento.codtratamiento \n" +
                            "JOIN categoria ON tratamiento.categoria_codcategoria = categoria.codcategoria\n" +
                            "WHERE EXTRACT(MONTH from fecha) = EXTRACT(MONTH from CURRENT_DATE) AND\n" +
                            "EXTRACT(YEAR from fecha) = EXTRACT(YEAR from CURRENT_DATE)\n" +
                            "GROUP BY tratamiento.codtratamiento, tratamiento.nombretratamiento, categoria.codcategoria;\n" +
                            "\n";
                    PreparedStatement pstmt = ConnectionBD.getConn().prepareStatement(sql);
                    ResultSet rs = pstmt.executeQuery();


                    ConnectionBD.connect();
                    List<TratamientoDiscrepanciaReporte> f = new ArrayList<>();
                    while(rs.next()){
                        TratamientoDiscrepanciaReporte t = new TratamientoDiscrepanciaReporte(
                                (UUID)rs.getObject(1),
                                (UUID)rs.getObject(2),
                                rs.getString(3),
                                rs.getInt(4),
                                rs.getInt(5),
                                rs.getInt(4) - rs.getInt(5));
                        f.add(t);
                        ResultSet rS = ServicesLocator.materialService().buscarTratamientos(t);
                        while (rS.next()){
                            t.getMateriales().add(new MaterialesDiscrepanciaReporte(rS.getString(4),rS.getInt(5),rS.getInt(6),
                                    rS.getInt(5)-rS.getInt(6)));
                        }
                    }
                    parametros.put("tratamientosDataSource", new JRBeanCollectionDataSource(f));
                    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

                    parametros.put("mes", meses[java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)]);
                    // Verificar que el archivo existe
                    File reportFile = new File("src/jasperReports/Discre.jasper");
                    if (!reportFile.exists()) {
                        throw new FileNotFoundException("Archivo .jasper no encontrado: " + reportFile.getPath());
                    }

                    // Cargar usando JRLoader (más robusto)
                    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);

                    // Llenar reporte
                    JasperPrint jasperPrint = JasperFillManager.fillReport(
                            jasperReport,
                            parametros,
                            new JRBeanCollectionDataSource(f)
                    );

                    // Exportar a PDF
                    String outputPath = System.getProperty("user.home") + "/Discrepancia.pdf";
                    try {
                        JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
                        JOptionPane.showMessageDialog(
                                null,
                                "PDF generado en: " + outputPath,
                                "PDF Generado Correctamenta",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                    } catch (JRException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(
                                null,
                                "Error al generar reporte",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }


                } catch (SQLException | FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al generar reporte",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                } catch (JRException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al generar reporte",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            }
        });
        a3TratamientosMásSolicitadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> parametros = new HashMap<>();
                try {
                    int cont = 1;
                    ResultSet rs = ServicesLocator.tratamientoService().tratamientosMasSolicitados();
                    while(rs.next()){
                        String trat = rs.getString(1) + " de la categoria "+ rs.getString(2);
                        parametros.put("trat"+ cont, trat);
                        cont++;
                    }
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);

                }
                try {
                    // Verificar que el archivo existe
                    File reportFile = new File("src/jasperReports/3TratMasSolici.jasper");
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
                    String outputPath = System.getProperty("user.home") + "/TratamientosMasSolicitados.pdf";
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
            }
        });
        tratamientosYPaquetesDeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaquetesTratCliente frame = null;
                try {
                    frame = new PaquetesTratCliente(interfaz, "Tratamientos y Paquetes de un Cliente");
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
        empleadosQueHanAtendidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpleadosDeCliente frame = null;
                try {
                    frame = new EmpleadosDeCliente(interfaz, "Empleados que han Atendido Cliente");
                } catch (SQLException ex) {
                    ManejadorExcepcionesSQL.mostrarErrorSQL(ex);
                }
                frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public JFrame interfaz() {
        return interfaz;
    }

    public static void setInterfaz(JFrame inter) {
        interfaz = inter;
        interfaz.setContentPane(new ReportesMenu().Menu);
    }
}

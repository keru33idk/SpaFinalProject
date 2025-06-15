package utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ValidarCampos {

    public static boolean validarCamposNoVacios(Container contenedor) {
        boolean validacionExitosa = true;
        List<JTextField> camposVacios = new ArrayList<>();
        List<JTextField> espacios = new ArrayList<>();

        // Buscar todos los JTextField recursivamente
        buscarCamposVacios(contenedor, camposVacios);
        buscarEspacios(contenedor, espacios);

        if (!camposVacios.isEmpty()) {
            mostrarErrorCamposVacios(contenedor, camposVacios);
            validacionExitosa = false;
        }
        if (!espacios.isEmpty()) {
            mostrarErrorEspacios(contenedor, espacios);
            validacionExitosa = false;
        }

        return validacionExitosa;
    }

    private static void buscarCamposVacios(Container contenedor, List<JTextField> camposVacios) {
        for (Component comp : contenedor.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field.getText().trim().isEmpty() && !field.getName().equalsIgnoreCase("filtrar")) {
                    camposVacios.add(field);
                }
            } else if (comp instanceof Container) {
                buscarCamposVacios((Container) comp, camposVacios);
            }
        }
    }

    private static void buscarEspacios(Container contenedor, List<JTextField> espacios) {
        for (Component comp : contenedor.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (!field.getText().matches("^[^\\s]+( [^\\s]+)*$") && !field.getName().equalsIgnoreCase("filtrar")) {
                    espacios.add(field);
                }
            } else if (comp instanceof Container) {
                buscarEspacios((Container) comp, espacios);
            }
        }
    }

    private static void mostrarErrorCamposVacios(Container contenedor, List<JTextField> camposVacios) {
        StringBuilder mensajeError = new StringBuilder("Los siguientes campos no pueden estar vacíos:\n");

        for (JTextField campo : camposVacios) {
            String nombreCampo = (campo.getName() != null) ? campo.getName() : "Campo sin nombre";
            mensajeError.append("- ").append(nombreCampo).append("\n");
        }

        JOptionPane.showMessageDialog(
                contenedor,
                mensajeError.toString(),
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
        );

        camposVacios.get(0).requestFocusInWindow();
    }
    private static void mostrarErrorEspacios(Container contenedor, List<JTextField> espacios) {
        StringBuilder mensajeError = new StringBuilder("Los siguientes campos tienen espacios no permitidos:\n");

        for (JTextField campo : espacios) {
            String nombreCampo = (campo.getName() != null) ? campo.getName() : "Campo sin nombre";
            mensajeError.append("- ").append(nombreCampo).append("\n");
        }

        JOptionPane.showMessageDialog(
                contenedor,
                mensajeError.toString(),
                "Error de validación",
                JOptionPane.ERROR_MESSAGE
        );

        espacios.get(0).requestFocusInWindow();
    }


    public static void configurarCampo(JTextField campo, String nombre) {
        campo.setName(nombre);
    }
}
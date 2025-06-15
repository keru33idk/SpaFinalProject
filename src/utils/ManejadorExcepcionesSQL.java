package utils;

import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ManejadorExcepcionesSQL {

    public static void mostrarErrorSQL(SQLException ex) {
        String mensajeUsuario;

        // Obtener el código SQLState (estándar SQL)
        String sqlState = ex.getSQLState();

        // Obtener el código de error específico del vendor (PostgreSQL en este caso)
        int errorCode = ex.getErrorCode();

        // Obtener el mensaje completo para analizar
        String mensajeCompleto = ex.getMessage();

        // Identificar el tipo de error
        if (sqlState.equals("23503")) { // Violación de llave foránea
            mensajeUsuario = "No se puede eliminar/modificar porque existen registros relacionados en otras tablas.";
        }
        else if (sqlState.equals("23505")) { // Violación de unicidad
            mensajeUsuario = "El registro ya existe (violación de unicidad).";
        }
        else if (sqlState.equals("22001")) { // Violación de longitud
            mensajeUsuario = "Datos demasiado largos para el campo.";
        }
        else if (sqlState.equals("28000")) { // Violación de acceso
            mensajeUsuario = "Usuario no existe.";
        }
        else if (sqlState.equals("28P01")) { // Violación de contraseña
            mensajeUsuario = "Contraseña incorrecta.";
        }
        else if(sqlState.equals("42501")){
            mensajeUsuario = "No tiene permisos para esto.";
        }
        else if (sqlState.equals("23514")) { // Violación de restricción CHECK
            // Analizar el mensaje para determinar qué restricción CHECK se violó
            if (mensajeCompleto.contains("cantpersonalfijo_check")) {
                mensajeUsuario = "La cantidad de personal fijo no puede ser negativa.";
            }
            else if (mensajeCompleto.contains("horastrabajosemanal_check")) {
                mensajeUsuario = "Las horas trabajadas semanales deben estar entre 1 y 60.";
            }
            else if (mensajeCompleto.contains("telefono_check")) {
                mensajeUsuario = "El número de teléfono debe tener exactamente 8 dígitos.";
            }
            else if (mensajeCompleto.contains("duraciontotal_check")) {
                mensajeUsuario = "La duración del paquete debe ser mayor que 0.";
            }
            else if (mensajeCompleto.contains("preciopaquete_check")) {
                mensajeUsuario = "El precio del paquete debe ser mayor que 0.";
            }
            else if (mensajeCompleto.contains("duracion_check")) {
                mensajeUsuario = "La duración del tratamiento debe ser mayor que 0.";
            }
            else if (mensajeCompleto.contains("frecuenciadesolicitudmensual_check")) {
                mensajeUsuario = "La frecuencia de solicitud mensual no puede ser negativa.";
            }
            else if (mensajeCompleto.contains("precio_check")) {
                mensajeUsuario = "El precio del tratamiento debe ser mayor que 0.";
            }
            else if (mensajeCompleto.contains("paquetevendido_check")) {
                mensajeUsuario = "La fecha de inicio no puede ser anterior a la fecha de compra.";
            }
            else if (mensajeCompleto.contains("paquetevendido_check1")) {
                mensajeUsuario = "La fecha de fin no puede ser anterior a la fecha de inicio.";
            }
            else {
                mensajeUsuario = "Error de validación: " + mensajeCompleto;
            }
        }
        // Errores de triggers (generalmente lanzan excepciones con código P0001)
        else if (mensajeCompleto.contains("tr_verificar_fecha")) {
            if (mensajeCompleto.contains("anterior a la fecha actual")) {
                mensajeUsuario = "La fecha de la cita no puede ser anterior a la fecha actual.";
            }
            else if (mensajeCompleto.contains("no puede ser hoy")) {
                mensajeUsuario = "No se pueden programar citas para el mismo día.";
            }
            else if (mensajeCompleto.contains("Rango de año no válido")) {
                mensajeUsuario = "El año de la cita no puede ser mayor a 2100.";
            }
            else {
                mensajeUsuario = "Error en la fecha de la cita: " + mensajeCompleto;
            }
        }
        else if (mensajeCompleto.contains("tr_area_empleado_cant")) {
            mensajeUsuario = "El área ha alcanzado el máximo de empleados permitidos.";
        }
        else if (mensajeCompleto.contains("validar_empleados_suplentes")) {
            if (mensajeCompleto.contains("no está marcado como suplente")) {
                mensajeUsuario = "El empleado suplente no está marcado como suplente.";
            }
            else if (mensajeCompleto.contains("no puede ser fijo")) {
                mensajeUsuario = "El empleado fijo no puede estar marcado como suplente, actualiza primero.";
            }
            else if (mensajeCompleto.contains("no existe en la tabla EMPLEADO")) {
                mensajeUsuario = mensajeCompleto.substring(mensajeCompleto.indexOf("EXCEPTION") + 10);
            }
            else {
                mensajeUsuario = "Error en la asignación de suplente: " + mensajeCompleto;
            }
        }
        else if (mensajeCompleto.contains("tr_verificar_fecha_paq")) {
            if (mensajeCompleto.contains("anterior a la fecha actual")) {
                mensajeUsuario = "La fecha de inicio no puede ser anterior a la fecha actual.";
            }
            else if (mensajeCompleto.contains("no puede ser hoy")) {
                mensajeUsuario = "No se pueden programar paquetes para comenzar hoy mismo.";
            }
            else if (mensajeCompleto.contains("Rango de año no válido")) {
                mensajeUsuario = "El año de inicio o fin no puede ser mayor a 2100.";
            }
            else {
                mensajeUsuario = "Error en las fechas del paquete: " + mensajeCompleto;
            }
        }
        else if (mensajeCompleto.contains("connection")) {
            mensajeUsuario = "Error de conexión con la base de datos.";
        }
        else if(mensajeCompleto.contains("verificar_paquete_sin_trat")){
            mensajeUsuario = "No se puede comprar un paquete sin tratamientos.";
        }
        else {
            // Mensaje genérico para otros errores
            mensajeUsuario = "Error de base de datos: " + ex.getMessage();
        }

        // Mostrar diálogo de error
        JOptionPane.showMessageDialog(
                null,
                mensajeUsuario,
                "Error de Base de Datos",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
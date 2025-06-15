package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TratamientoDiscrepanciaReporte {
    private UUID codTratamiento;
    private UUID codCategoria;
    private String nombreTratamiento;
    private int tratamientosPlanificados;
    private int tratamientosRealizados;
    private int diferenciaTratamiento;
    private List<MaterialesDiscrepanciaReporte> materiales;

    public TratamientoDiscrepanciaReporte(String nombreTratamiento, int tratamientosPlanificados, int tratamientosRealizados, int diferenciaTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
        this.tratamientosPlanificados = tratamientosPlanificados;
        this.tratamientosRealizados = tratamientosRealizados;
        this.diferenciaTratamiento = diferenciaTratamiento;
        this.materiales = new ArrayList<MaterialesDiscrepanciaReporte>();
    }

    public TratamientoDiscrepanciaReporte(UUID codTratamiento, UUID codCategoria, String nombreTratamiento, int tratamientosPlanificados, int tratamientosRealizados, int diferenciaTratamiento) {
        this.codTratamiento = codTratamiento;
        this.codCategoria = codCategoria;
        this.nombreTratamiento = nombreTratamiento;
        this.tratamientosPlanificados = tratamientosPlanificados;
        this.tratamientosRealizados = tratamientosRealizados;
        this.diferenciaTratamiento = diferenciaTratamiento;
        this.materiales = new ArrayList<MaterialesDiscrepanciaReporte>();
    }

    public UUID getCodTratamiento() {
        return codTratamiento;
    }

    public void setCodTratamiento(UUID codTratamiento) {
        this.codTratamiento = codTratamiento;
    }

    public UUID getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(UUID codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public int getTratamientosPlanificados() {
        return tratamientosPlanificados;
    }

    public void setTratamientosPlanificados(int tratamientosPlanificados) {
        this.tratamientosPlanificados = tratamientosPlanificados;
    }

    public int getTratamientosRealizados() {
        return tratamientosRealizados;
    }

    public void setTratamientosRealizados(int tratamientosRealizados) {
        this.tratamientosRealizados = tratamientosRealizados;
    }

    public int getDiferenciaTratamiento() {
        return diferenciaTratamiento;
    }

    public void setDiferenciaTratamiento(int diferenciaTratamiento) {
        this.diferenciaTratamiento = diferenciaTratamiento;
    }

    public List<MaterialesDiscrepanciaReporte> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialesDiscrepanciaReporte> materiales) {
        this.materiales = materiales;
    }
}

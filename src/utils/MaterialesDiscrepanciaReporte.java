package utils;

public class MaterialesDiscrepanciaReporte {
    private String nombreMaterial;
    private int materialesPlanificados;
    private int materialesUtilizados;
    private int diferenciaMateriales;

    public MaterialesDiscrepanciaReporte(String nombreMaterial, int materialesPlanificados, int materialesUtilizados, int diferenciaMateriales) {
        this.nombreMaterial = nombreMaterial;
        this.materialesPlanificados = materialesPlanificados;
        this.materialesUtilizados = materialesUtilizados;
        this.diferenciaMateriales = diferenciaMateriales;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public int getMaterialesPlanificados() {
        return materialesPlanificados;
    }

    public void setMaterialesPlanificados(int materialesPlanificados) {
        this.materialesPlanificados = materialesPlanificados;
    }

    public int getMaterialesUtilizados() {
        return materialesUtilizados;
    }

    public void setMaterialesUtilizados(int materialesUtilizados) {
        this.materialesUtilizados = materialesUtilizados;
    }

    public int getDiferenciaMateriales() {
        return diferenciaMateriales;
    }

    public void setDiferenciaMateriales(int diferenciaMateriales) {
        this.diferenciaMateriales = diferenciaMateriales;
    }
}

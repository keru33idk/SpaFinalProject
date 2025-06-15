package Logic.services;

public class ServicesLocator {
    private static AreaService areaService = null;
    private static CategoriaService categoriaService = null;
    private static CitaService citaService = null;
    private static ClienteService clienteService = null;
    private static EmpleadoService empleadoService = null;
    private static MaterialService materialService = null;
    private static MaterialTratamientoService materialTratamientoService = null;
    private static PaqueteService paqueteService = null;
    private static PaqueteTratamientoService paqueteTratamientoService = null;
    private static PaqueteVendidoService paqueteVendidoService = null;
    private static SuplenteService suplenteService = null;
    private static TratamientoService tratamientoService = null;

    public static AreaService areaService() {
        if(areaService == null)
            areaService = new AreaService();
        return areaService;
    }

    public static CategoriaService categoriaService() {
        if(categoriaService == null)
            categoriaService = new CategoriaService();
        return categoriaService;
    }

    public static CitaService citaService() {
        if(citaService == null)
            citaService = new CitaService();
        return citaService;
    }

    public static ClienteService clienteService() {
        if(clienteService == null)
            clienteService = new ClienteService();
        return clienteService;
    }

    public static EmpleadoService empleadoService() {
        if(empleadoService == null)
            empleadoService = new EmpleadoService();
        return empleadoService;
    }

    public static MaterialService materialService() {
        if(materialService == null)
            materialService = new MaterialService();
        return materialService;
    }

    public static MaterialTratamientoService materialTratamientoService() {
        if(materialTratamientoService == null)
            materialTratamientoService = new MaterialTratamientoService();
        return materialTratamientoService;
    }

    public static PaqueteService paqueteService() {
        if(paqueteService == null)
            paqueteService = new PaqueteService();
        return paqueteService;
    }

    public static PaqueteTratamientoService paqueteTratamientoService() {
        if(paqueteTratamientoService == null)
            paqueteTratamientoService = new PaqueteTratamientoService();
        return paqueteTratamientoService;
    }

    public static PaqueteVendidoService paqueteVendidoService() {
        if(paqueteVendidoService == null)
            paqueteVendidoService = new PaqueteVendidoService();
        return paqueteVendidoService;
    }

    public static SuplenteService suplenteService() {
        if(suplenteService == null)
            suplenteService = new SuplenteService();
        return suplenteService;
    }

    public static TratamientoService tratamientoService() {
        if(tratamientoService == null)
            tratamientoService = new TratamientoService();
        return tratamientoService;
    }
}

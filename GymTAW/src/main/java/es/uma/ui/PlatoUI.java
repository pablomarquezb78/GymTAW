package es.uma.ui;



public class PlatoUI {
    private Integer id;
    private String nombre;
    private String tiempoDePreparacion;
    private String receta;
    private String enlaceReceta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTiempoDePreparacion() {
        return tiempoDePreparacion;
    }

    public void setTiempoDePreparacion(String tiempoDePreparacion) {
        this.tiempoDePreparacion = tiempoDePreparacion;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    public String getEnlaceReceta() {
        return enlaceReceta;
    }

    public void setEnlaceReceta(String enlaceReceta) {
        this.enlaceReceta = enlaceReceta;
    }

    public boolean estaVacio(){
        return this.getNombre().isEmpty() && this.getTiempoDePreparacion().isEmpty() && this.getReceta().isEmpty();
    }
}

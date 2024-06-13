package es.uma.ui;

import es.uma.entity.Ingrediente;
import es.uma.entity.User;

import java.util.ArrayList;

public class PlatoDietistaUI {

    private Integer id;
    private String nombre;
    private String tiempoDePreparacion;
    private String receta;
    private String enlaceReceta;
    private ArrayList<Ingrediente> ingredientes;
    private Integer addedIngrediente;

    public Integer getAddedIngrediente() {
        return addedIngrediente;
    }

    public void setAddedIngrediente(Integer addedIngrediente) {
        this.addedIngrediente = addedIngrediente;
    }

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

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}

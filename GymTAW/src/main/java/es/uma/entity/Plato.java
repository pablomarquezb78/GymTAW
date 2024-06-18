package es.uma.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "plato")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplato", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "tiempo_de_preparacion", length = 30)
    private String tiempoDePreparacion;

    @Column(name = "receta", length = 1500)
    private String receta;

    @Column(name = "enlace_receta", length = 200)
    private String enlaceReceta;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AsignacionPlatoIngredienteDietistaCreador> asignaciones;

    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CantidadIngredientePlatoComida> cantidades;

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

}
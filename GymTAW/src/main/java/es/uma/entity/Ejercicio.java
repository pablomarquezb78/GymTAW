package es.uma.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idejercicio", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 45)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo")
    private TipoEjercicio tipo;

    @Column(name = "descripcion", length = 350)
    private String descripcion;

    @Column(name = "enlace_video", length = 250)
    private String enlaceVideo;

    @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImplementacionEjercicioRutina> implementaciones;

    // Getters y setters
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

    public TipoEjercicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoEjercicio tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnlaceVideo() {
        return enlaceVideo;
    }

    public void setEnlaceVideo(String enlaceVideo) {
        this.enlaceVideo = enlaceVideo;
    }

    public List<ImplementacionEjercicioRutina> getImplementaciones() {
        return implementaciones;
    }

    public void setImplementaciones(List<ImplementacionEjercicioRutina> implementaciones) {
        this.implementaciones = implementaciones;
    }
}

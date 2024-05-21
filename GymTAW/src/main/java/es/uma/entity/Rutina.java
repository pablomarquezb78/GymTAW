package es.uma.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "rutina")
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrutina", nullable = false)
    private Integer id;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenador")
    private User entrenador;

    @OneToMany(mappedBy = "rutina")
    private List<ImplementacionEjercicioRutina> implementacionesEjercicioRutina;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public User getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(User entrenador) {
        this.entrenador = entrenador;
    }

    public List<ImplementacionEjercicioRutina> getImplementacionesEjercicioRutina() {
        return implementacionesEjercicioRutina;
    }

    public void setImplementacionesEjercicioRutina(List<ImplementacionEjercicioRutina> implementacionesEjercicioRutina) {
        this.implementacionesEjercicioRutina = implementacionesEjercicioRutina;
    }

}
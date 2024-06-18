package es.uma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`implementacion_ejercicio-rutina`")
public class ImplementacionEjercicioRutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idimplementacion_ejercicio-rutina", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejercicio", nullable = false)
    private Ejercicio ejercicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rutina", nullable = false)
    private Rutina rutina;

    @OneToMany(mappedBy = "implementacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackEjercicio> feedbacks;

    @Column(name = "sets", length = 45)
    private String sets;

    @Column(name = "repeticiones", length = 45)
    private String repeticiones;

    @Column(name = "peso", length = 45)
    private String peso;

    @Column(name = "tiempo", length = 45)
    private String tiempo;

    @Column(name = "kilocalorias", length = 45)
    private String kilocalorias;

    @Column(name = "metros", length = 45)
    private String metros;

}
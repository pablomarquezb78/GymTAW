package es.uma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`implementacion_ejercicio-rutina`")
public class ImplementacionEjercicioRutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`idimplementacion_ejercicio-rutina`", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejercicio")
    private Ejercicio ejercicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rutina")
    private Rutina rutina;

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
package es.uma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feedback_ejercicio")
public class FeedbackEjercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfeedback_ejercicio", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`implementacion_ejercicio-rutina`")
    private ImplementacionEjercicioRutina implementacionEjercicioRutina;

    @Column(name = "Serie", length = 45)
    private String serie;

    @Column(name = "Repeticiones_realizadas", length = 45)
    private String repeticionesRealizadas;

    @Column(name = "Peso_realizado", length = 45)
    private String pesoRealizado;

}
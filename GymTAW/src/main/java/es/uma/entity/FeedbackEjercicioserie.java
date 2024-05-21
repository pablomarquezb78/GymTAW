package es.uma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feedback_ejercicioserie")
public class FeedbackEjercicioserie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfeedback_ejercicioserie", nullable = false)
    private Integer id;

    @Column(name = "Serie", length = 45)
    private String serie;

    @Column(name = "Repeticiones_realizadas", length = 45)
    private String repeticionesRealizadas;

    @Column(name = "Peso_realizado", length = 45)
    private String pesoRealizado;

    @Column(name = "Tiempo_realizado", length = 45)
    private String tiempoRealizado;

    @Column(name = "Kilocalorias_realizado", length = 45)
    private String kilocaloriasRealizado;

    @Column(name = "Metros_realizado", length = 45)
    private String metrosRealizado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedback_ejercicio")
    private FeedbackEjercicio feedbackEjercicio;

}
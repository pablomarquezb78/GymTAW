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

    @Column(name = "seguimiento_sets_done", length = 45)
    private String seguimientoSetsDone;

    @Column(name = "seguimiento_tiempo_done", length = 45)
    private String seguimientoTiempoDone;

    @Column(name = "seguimiento_kilocalorias_done", length = 45)
    private String seguimientoKilocaloriasDone;

    @Column(name = "seguimiento_metros__done", length = 45)
    private String seguimientoMetrosDone;

    @Column(name = "realizado")
    private Byte realizado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "implementacion")
    private ImplementacionEjercicioRutina implementacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_entrenamiento")
    private DiaEntrenamiento diaEntrenamiento;

}
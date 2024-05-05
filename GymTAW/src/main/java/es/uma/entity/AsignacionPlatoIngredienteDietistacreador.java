package es.uma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "asignacion_plato_ingrediente_dietistacreador")
public class AsignacionPlatoIngredienteDietistacreador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasignacion_plato_ingrediente_dietista_creador", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plato")
    private Plato plato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingrediente")
    private Ingrediente ingrediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dietista")
    private User dietista;

}
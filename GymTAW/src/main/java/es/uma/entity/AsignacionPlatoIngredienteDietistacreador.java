package es.uma.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "asignacion_plato_ingrediente_dietista_creador")
public class AsignacionPlatoIngredienteDietistacreador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasignacion_plato_ingrediente_dietista_creador", nullable = false)
    private Integer idasignacion_plato_ingrediente_dietista_creador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plato")
    private Plato plato;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingrediente")
    private Ingrediente ingrediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dietista")
    private User dietista;


    public Integer getIdasignacion_plato_ingrediente_dietista_creador() {
        return idasignacion_plato_ingrediente_dietista_creador;
    }

    public void setIdasignacion_plato_ingrediente_dietista_creador(Integer idasignacion_plato_ingrediente_dietista_creador) {
        this.idasignacion_plato_ingrediente_dietista_creador = idasignacion_plato_ingrediente_dietista_creador;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public User getDietista() {
        return dietista;
    }

    public void setDietista(User dietista) {
        this.dietista = dietista;
    }
}
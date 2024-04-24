package es.uma;

import jakarta.persistence.*;

@Entity
@Table(name = "asignacion_cliente_entrenador")
public class AsignacionClienteEntrenador {
    @Id
    @Column(name = "idasignacion_cliente_entrenador", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente")
    private User cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenador")
    private User entrenador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getCliente() {
        return cliente;
    }

    public void setCliente(User cliente) {
        this.cliente = cliente;
    }

    public User getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(User entrenador) {
        this.entrenador = entrenador;
    }

}
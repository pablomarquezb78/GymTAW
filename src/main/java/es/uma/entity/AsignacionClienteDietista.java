package es.uma.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "asignacion_cliente_dietista")
public class AsignacionClienteDietista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idasignacion_cliente_dietista", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente")
    private User cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dietista")
    private User dietista;

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

    public User getDietista() {
        return dietista;
    }

    public void setDietista(User dietista) {
        this.dietista = dietista;
    }

}
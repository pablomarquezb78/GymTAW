package es.uma.gymtaw;

import jakarta.persistence.*;

@Entity
@Table(name = "asignacion_cliente_dietista")
public class AsignacionClienteDietista {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente")
    private User cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dietista")
    private User dietista;

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
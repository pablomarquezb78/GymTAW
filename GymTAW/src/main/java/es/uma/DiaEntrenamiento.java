package es.uma;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "dia_entrenamiento")
public class DiaEntrenamiento {
    @Id
    @Column(name = "iddia_entrenamiento", nullable = false)
    private Integer id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "seguimiento", length = 1500)
    private String seguimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente")
    private User cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rutina")
    private Rutina rutina;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    public User getCliente() {
        return cliente;
    }

    public void setCliente(User cliente) {
        this.cliente = cliente;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

}
package es.uma.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_ejercicio")
public class TipoEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipo_ejercicio", nullable = false)
    private Integer id;

    @Column(name = "tipo_de_ejercicio", length = 45)
    private String tipoDeEjercicio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoDeEjercicio() {
        return tipoDeEjercicio;
    }

    public void setTipoDeEjercicio(String tipoDeEjercicio) {
        this.tipoDeEjercicio = tipoDeEjercicio;
    }

}
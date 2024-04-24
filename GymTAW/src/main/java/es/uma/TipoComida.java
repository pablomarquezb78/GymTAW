package es.uma;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_comida")
public class TipoComida {
    @Id
    @Column(name = "idtipo_comida", nullable = false)
    private Integer id;

    @Column(name = "comida_del_dia", length = 45)
    private String comidaDelDia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComidaDelDia() {
        return comidaDelDia;
    }

    public void setComidaDelDia(String comidaDelDia) {
        this.comidaDelDia = comidaDelDia;
    }

}
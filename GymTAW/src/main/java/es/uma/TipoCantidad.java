package es.uma;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_cantidad")
public class TipoCantidad {
    @Id
    @Column(name = "idtipo_cantidad", nullable = false)
    private Integer id;

    @Column(name = "tipo_cantidad_medida", length = 45)
    private String tipoCantidadMedida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoCantidadMedida() {
        return tipoCantidadMedida;
    }

    public void setTipoCantidadMedida(String tipoCantidadMedida) {
        this.tipoCantidadMedida = tipoCantidadMedida;
    }

}
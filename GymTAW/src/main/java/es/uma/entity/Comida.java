package es.uma.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comida")
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcomida", nullable = false)
    private Integer id;

    @Column(name = "realizado")
    private Byte realizado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_comida")
    private TipoComida tipoComida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dia_dieta")
    private DiaDieta diaDieta;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getRealizado() {
        return realizado;
    }

    public void setRealizado(Byte realizado) {
        this.realizado = realizado;
    }

    public TipoComida getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
    }

    public DiaDieta getDiaDieta() {
        return diaDieta;
    }

    public void setDiaDieta(DiaDieta diaDieta) {
        this.diaDieta = diaDieta;
    }

}
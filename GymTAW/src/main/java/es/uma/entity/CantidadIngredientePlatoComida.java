package es.uma.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "`cantidad_ingrediente-plato-comida`")
public class CantidadIngredientePlatoComida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`idcantidad_ingrediente-plato`", nullable = false)
    private Integer id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_cantidad")
    private TipoCantidad tipoCantidad;

    @Column(name = "cantidad_consumida")
    private Integer cantidadConsumida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingrediente")
    private Ingrediente ingrediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plato")
    private Plato plato;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "comida")
    private Comida comida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public TipoCantidad getTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(TipoCantidad tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    public Integer getCantidadConsumida() {
        return cantidadConsumida;
    }

    public void setCantidadConsumida(Integer cantidadConsumida) {
        this.cantidadConsumida = cantidadConsumida;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public Comida getComida() {
        return comida;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

}
package es.uma.ui;

import es.uma.entity.Ingrediente;
import es.uma.entity.TipoCantidad;

//@author: Jaime Ezequiel Rodriguez Rodriguez

public class IngredienteImplementandoUI {

    protected Ingrediente ingrediente;
    protected Integer cantidad;
    protected TipoCantidad tipoCantidad;

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
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
}

package es.uma.ui;

import es.uma.entity.TipoCantidad;
import es.uma.entity.TipoComida;


public class CantidadPlatoComida {
    private Integer id;
    private String nombreCliente;
    private String nombreDietista;
    private Integer cantidad;
    private TipoComida tipoComida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreDietista() {
        return nombreDietista;
    }

    public void setNombreDietista(String nombreDietista) {
        this.nombreDietista = nombreDietista;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public TipoComida getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public boolean estaVacio(){
        return this.getNombreCliente().isEmpty() && this.getNombreDietista().isEmpty() && this.getTipoComida() == null && this.getCantidad() == null;
    }


}

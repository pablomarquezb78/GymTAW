package es.uma.ui;

import es.uma.dto.TipoComidaDTO;
import es.uma.entity.TipoComida;

//@author: Pablo Miguel Aguilar Blanco

public class CantidadPlatoComida {
    private Integer id;
    private String nombreCliente;
    private String nombreDietista;
    private Integer cantidad;
    private Integer tipoComida;

    public Integer getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(Integer tipoComida) {
        this.tipoComida = tipoComida;
    }

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


    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public boolean estaVacio(){
        return this.getNombreCliente().isEmpty() && this.getNombreDietista().isEmpty() && this.getTipoComida() == null && this.getCantidad() == null;
    }


}

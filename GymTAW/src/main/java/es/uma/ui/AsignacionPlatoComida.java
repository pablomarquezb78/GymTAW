package es.uma.ui;

import es.uma.entity.TipoComida;

public class AsignacionPlatoComida {

    private Integer idPlato;
    private String fecha;
    private Integer idComida;
    private Integer idCliente;
    private Integer idDietista;
    private Integer cantidad;
    private TipoComida tipoComida;

    public Integer getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Integer idPlato) {
        this.idPlato = idPlato;
    }

    public Integer getIdComida() {
        return idComida;
    }

    public void setIdComida(Integer idComida) {
        this.idComida = idComida;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdDietista() {
        return idDietista;
    }

    public void setIdDietista(Integer idDietista) {
        this.idDietista = idDietista;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public TipoComida getTipoComida() {
        return tipoComida;
    }

    public void setTipoComida(TipoComida tipoComida) {
        this.tipoComida = tipoComida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean estaVacio(){
        return this.getIdCliente() == null && this.getIdDietista() == null && this.getTipoComida() == null && this.getCantidad() == null && this.getFecha().isEmpty();
    }
}

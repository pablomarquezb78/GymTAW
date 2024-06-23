package es.uma.ui;

import es.uma.dto.IngredienteDTO;
import es.uma.dto.TipoComidaDTO;
import es.uma.entity.Ingrediente;
import es.uma.entity.TipoComida;
import lombok.Getter;

import java.util.List;

//@author: Pablo Miguel Aguilar Blanco

@Getter
public class AsignacionPlatoComida {

    private Integer idPlato;
    private String fecha;
    private Integer idComida;
    private Integer idCliente;
    private Integer idDietista;
    private Integer cantidad;
    private Integer tipoComida;

    public void setTipoComida(Integer tipoComida) {
        this.tipoComida = tipoComida;
    }

    public void setIdPlato(Integer idPlato) {
        this.idPlato = idPlato;
    }

    public void setIdComida(Integer idComida) {
        this.idComida = idComida;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdDietista(Integer idDietista) {
        this.idDietista = idDietista;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public boolean estaVacio(){
        return this.getIdCliente() == null && this.getIdDietista() == null && this.getTipoComida() == null && this.getCantidad() == null && this.getFecha().isEmpty();
    }
}

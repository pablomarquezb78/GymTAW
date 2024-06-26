package es.uma.ui;

import es.uma.dto.EjercicioDTO;
import es.uma.dto.TipoEjercicioDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.Rutina;
import es.uma.entity.TipoEjercicio;

public class Implementacion {


    private Integer id;
    private Integer rutina;
    private String sets;
    private String repeticiones;
    private String peso;
    private String tiempo;
    private String kilocalorias;
    private String metros;
    private String seguimientoSetsDone;
    private String seguimientoTiempoDone;
    private String seguimientoKilocaloriasDone;
    private String seguimientoMetrosDone;
    private Byte realizado;
    private Integer iddia;
    private EjercicioDTO ejercicio;
    private TipoEjercicioDTO tipofiltrado;
    private String nombrefiltrado;
    private Integer idfiltrado;
    private Integer idejercicio;
    private Integer auxValue;

    public void setAuxValue(Integer auxValue) {
        this.auxValue = auxValue;
    }

    public Integer getAuxValue() {
        return auxValue;
    }

    public Integer getIdejercicio() {
        return idejercicio;
    }

    public void setIdejercicio(Integer idejercicio) {
        this.idejercicio = idejercicio;
    }

    public Integer getIdfiltrado() {
        return idfiltrado;
    }

    public void setIdfiltrado(Integer idfiltrado) {
        this.idfiltrado = idfiltrado;
    }

    public EjercicioDTO getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(EjercicioDTO ejercicio) {
        this.ejercicio = ejercicio;
    }

    public TipoEjercicioDTO getTipofiltrado() {
        return tipofiltrado;
    }

    public void setTipofiltrado(TipoEjercicioDTO tipofiltrado) {
        this.tipofiltrado = tipofiltrado;
    }

    public String getNombrefiltrado() {
        return nombrefiltrado;
    }

    public void setNombrefiltrado(String nombrefiltrado) {
        this.nombrefiltrado = nombrefiltrado;
    }

    public Integer getIddia() {
        return iddia;
    }

    public void setIddia(Integer iddia) {
        this.iddia = iddia;
    }




    public Integer getIdDia() {
        return iddia;
    }

    public void setIdDia(Integer iddia) {
        this.iddia = iddia;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public Integer getRutina() {
        return rutina;
    }

    public void setRutina(Integer rutina) {
        this.rutina = rutina;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getKilocalorias() {
        return kilocalorias;
    }

    public void setKilocalorias(String kilocalorias) {
        this.kilocalorias = kilocalorias;
    }

    public String getMetros() {
        return metros;
    }

    public void setMetros(String metros) {
        this.metros = metros;
    }

    public String getSeguimientoSetsDone() {
        return seguimientoSetsDone;
    }

    public void setSeguimientoSetsDone(String seguimientoSetsDone) {
        this.seguimientoSetsDone = seguimientoSetsDone;
    }

    public String getSeguimientoTiempoDone() {
        return seguimientoTiempoDone;
    }

    public void setSeguimientoTiempoDone(String seguimientoTiempoDone) {
        this.seguimientoTiempoDone = seguimientoTiempoDone;
    }

    public String getSeguimientoKilocaloriasDone() {
        return seguimientoKilocaloriasDone;
    }

    public void setSeguimientoKilocaloriasDone(String seguimientoKilocaloriasDone) {
        this.seguimientoKilocaloriasDone = seguimientoKilocaloriasDone;
    }

    public String getSeguimientoMetrosDone() {
        return seguimientoMetrosDone;
    }

    public void setSeguimientoMetrosDone(String seguimientoMetrosDone) {
        this.seguimientoMetrosDone = seguimientoMetrosDone;
    }

    public Byte getRealizado() {
        return realizado;
    }

    public void setRealizado(Byte realizado) {
        this.realizado = realizado;
    }

    public boolean estaVacio(){
        return rutina == null && this.getSets().isEmpty() && this.getMetros().isEmpty() &&
                this.getKilocalorias().isEmpty() && this.getRepeticiones().isEmpty() && this.getTiempo().isEmpty() && this.getPeso().isEmpty();
    }
}

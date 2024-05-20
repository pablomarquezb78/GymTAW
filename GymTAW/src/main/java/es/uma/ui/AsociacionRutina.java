package es.uma.ui;

public class AsociacionRutina {

    private String fecha;
    private Integer idTrainer;
    private Integer idCliente;
    private Integer idRutina;

    public String getFecha() {
        return fecha;
    }

    public Integer getIdTrainer() {
        return idTrainer;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public Integer getIdRutina() {
        return idRutina;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdTrainer(Integer idTrainer) {
        this.idTrainer = idTrainer;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public void setIdRutina(Integer idRutina) {
        this.idRutina = idRutina;
    }
}

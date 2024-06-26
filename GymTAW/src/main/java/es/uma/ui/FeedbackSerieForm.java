package es.uma.ui;


//@author: Pablo Márquez Benítez
public class FeedbackSerieForm {

    protected Integer implementacionId;
    protected String serieSeleccionada;
    protected String repeticionesRealizadas;
    protected String pesoRealizado;
    protected Integer segundosRealizados;
    protected Integer minutosRealizados;
    protected String kilocaloriasRealizado;
    protected String metrosRealizado;
    protected Integer feedbackEjercicioId;

    public Integer getFeedbackEjercicioId() {
        return feedbackEjercicioId;
    }

    public void setFeedbackEjercicioId(Integer feedbackEjercicioId) {
        this.feedbackEjercicioId = feedbackEjercicioId;
    }

    public Integer getSegundosRealizados() {
        return segundosRealizados;
    }

    public void setSegundosRealizados(Integer segundosRealizados) {
        this.segundosRealizados = segundosRealizados;
    }

    public Integer getMinutosRealizados() {
        return minutosRealizados;
    }

    public void setMinutosRealizados(Integer minutosRealizados) {
        this.minutosRealizados = minutosRealizados;
    }

    public Integer getImplementacionId() {
        return implementacionId;
    }

    public void setImplementacionId(Integer implementacionId) {
        this.implementacionId = implementacionId;
    }

    public String getSerieSeleccionada() {
        return serieSeleccionada;
    }

    public void setSerieSeleccionada(String serieSeleccionada) {
        this.serieSeleccionada = serieSeleccionada;
    }

    public String getRepeticionesRealizadas() {
        return repeticionesRealizadas;
    }

    public void setRepeticionesRealizadas(String repeticionesRealizadas) {
        this.repeticionesRealizadas = repeticionesRealizadas;
    }

    public String getPesoRealizado() {
        return pesoRealizado;
    }

    public void setPesoRealizado(String pesoRealizado) {
        this.pesoRealizado = pesoRealizado;
    }

    public String getKilocaloriasRealizado() {
        return kilocaloriasRealizado;
    }

    public void setKilocaloriasRealizado(String kilocaloriasRealizado) {
        this.kilocaloriasRealizado = kilocaloriasRealizado;
    }

    public String getMetrosRealizado() {
        return metrosRealizado;
    }

    public void setMetrosRealizado(String metrosRealizado) {
        this.metrosRealizado = metrosRealizado;
    }
}

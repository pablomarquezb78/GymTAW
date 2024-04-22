package es.uma.gymtaw;

import jakarta.persistence.*;

@Entity
@Table(name = "`implementacion_ejercicio-rutina`")
public class ImplementacionEjercicioRutina {
    @Id
    @Column(name = "`idimplementacion_ejercicio-rutina`", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ejercicio")
    private Ejercicio ejercicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rutina")
    private Rutina rutina;

    @Column(name = "sets", length = 45)
    private String sets;

    @Column(name = "repeticiones", length = 45)
    private String repeticiones;

    @Column(name = "peso", length = 45)
    private String peso;

    @Column(name = "tiempo", length = 45)
    private String tiempo;

    @Column(name = "kilocalorias", length = 45)
    private String kilocalorias;

    @Column(name = "metros", length = 45)
    private String metros;

    @Column(name = "seguimiento_sets_done", length = 45)
    private String seguimientoSetsDone;

    @Column(name = "seguimiento_repeticiones_done", length = 45)
    private String seguimientoRepeticionesDone;

    @Column(name = "seguimiento_peso_done", length = 45)
    private String seguimientoPesoDone;

    @Column(name = "seguimiento_tiempo_done", length = 45)
    private String seguimientoTiempoDone;

    @Column(name = "seguimiento_kilocalorias_done", length = 45)
    private String seguimientoKilocaloriasDone;

    @Column(name = "seguimiento_metros_done", length = 45)
    private String seguimientoMetrosDone;

    @Column(name = "realizado")
    private Byte realizado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
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

    public String getSeguimientoRepeticionesDone() {
        return seguimientoRepeticionesDone;
    }

    public void setSeguimientoRepeticionesDone(String seguimientoRepeticionesDone) {
        this.seguimientoRepeticionesDone = seguimientoRepeticionesDone;
    }

    public String getSeguimientoPesoDone() {
        return seguimientoPesoDone;
    }

    public void setSeguimientoPesoDone(String seguimientoPesoDone) {
        this.seguimientoPesoDone = seguimientoPesoDone;
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

}
package es.uma.dto;

import lombok.Data;

@Data
public class IngredienteDTO {
    private Integer id;
    private String nombre;
    private String kilocalorias;
    private String proteinas;
    private String grasas;
    private String azucares;
    private String hidratosDeCarbono;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getKilocalorias() {
        return kilocalorias;
    }

    public void setKilocalorias(String kilocalorias) {
        this.kilocalorias = kilocalorias;
    }

    public String getProteinas() {
        return proteinas;
    }

    public void setProteinas(String proteinas) {
        this.proteinas = proteinas;
    }

    public String getGrasas() {
        return grasas;
    }

    public void setGrasas(String grasas) {
        this.grasas = grasas;
    }

    public String getAzucares() {
        return azucares;
    }

    public void setAzucares(String azucares) {
        this.azucares = azucares;
    }

    public String getHidratosDeCarbono() {
        return hidratosDeCarbono;
    }

    public void setHidratosDeCarbono(String hidratosDeCarbono) {
        this.hidratosDeCarbono = hidratosDeCarbono;
    }
}

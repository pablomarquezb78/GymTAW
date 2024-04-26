package es.uma.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingrediente")
public class Ingrediente {
    @Id
    @Column(name = "idingrediente", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "kilocalorias", length = 30)
    private String kilocalorias;

    @Column(name = "proteinas", length = 30)
    private String proteinas;

    @Column(name = "grasas", length = 30)
    private String grasas;

    @Column(name = "azucares", length = 30)
    private String azucares;

    @Column(name = "hidratos_de_carbono", length = 30)
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
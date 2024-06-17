package es.uma.ui;

import es.uma.entity.CantidadIngredientePlatoComida;
import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;

import java.util.List;

public class FeedbackDietistaMostrarUI {

    protected Plato platoMostrando;
    protected List<CantidadIngredientePlatoComida> cantidadesIngredientePlatoComida;

    public Plato getPlatoMostrando() {
        return platoMostrando;
    }

    public void setPlatoMostrando(Plato platoMostrando) {
        this.platoMostrando = platoMostrando;
    }

    public List<CantidadIngredientePlatoComida> getCantidadesIngredientePlatoComida() {
        return cantidadesIngredientePlatoComida;
    }

    public void setCantidadesIngredientePlatoComida(List<CantidadIngredientePlatoComida> cantidadesIngredientePlatoComida) {
        this.cantidadesIngredientePlatoComida = cantidadesIngredientePlatoComida;
    }
}

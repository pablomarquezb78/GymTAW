package es.uma.ui;

import es.uma.entity.CantidadIngredientePlatoComida;
import es.uma.entity.Ingrediente;
import es.uma.entity.Plato;

import java.util.List;

public class ComidaUI {

    protected List<Plato> listaPlatosComida;
    protected List<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado;
    protected Plato selectedPlato;
    protected CantidadIngredientePlatoComida editingCantidadIngrediente;
    protected Plato addingPlato;

    public List<Plato> getListaPlatosComida() {
        return listaPlatosComida;
    }

    public void setListaPlatosComida(List<Plato> listaPlatosComida) {
        this.listaPlatosComida = listaPlatosComida;
    }

    public List<CantidadIngredientePlatoComida> getListaCantidadIngredientesPlatoSeleccionado() {
        return listaCantidadIngredientesPlatoSeleccionado;
    }

    public void setListaCantidadIngredientesPlatoSeleccionado(List<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado) {
        this.listaCantidadIngredientesPlatoSeleccionado = listaCantidadIngredientesPlatoSeleccionado;
    }

    public Plato getSelectedPlato() {
        return selectedPlato;
    }

    public void setSelectedPlato(Plato selectedPlato) {
        this.selectedPlato = selectedPlato;
    }

    public CantidadIngredientePlatoComida getEditingCantidadIngrediente() {
        return editingCantidadIngrediente;
    }

    public void setEditingCantidadIngrediente(CantidadIngredientePlatoComida editingCantidadIngrediente) {
        this.editingCantidadIngrediente = editingCantidadIngrediente;
    }

    public Plato getAddingPlato() {
        return addingPlato;
    }

    public void setAddingPlato(Plato addingPlato) {
        this.addingPlato = addingPlato;
    }
}

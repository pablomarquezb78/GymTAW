package es.uma.service;

import es.uma.dao.AsignacionPlatoIngredienteDietistacreadorRepositoy;
import es.uma.entity.AsignacionPlatoIngredienteDietistaCreador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@author: Pablo Miguel Aguilar Blanco

@Service
public class AsignacionPlatoIngredienteDietistaCreadorService {

    @Autowired
    private AsignacionPlatoIngredienteDietistacreadorRepositoy asignacionPlatoIngredienteDietistacreadorRepositoy;

    public void deleteByDietist(Integer id){
        for(AsignacionPlatoIngredienteDietistaCreador aspidc : asignacionPlatoIngredienteDietistacreadorRepositoy.getByDietist(id)){
            asignacionPlatoIngredienteDietistacreadorRepositoy.deleteById(aspidc.getId());
        }
    }

}

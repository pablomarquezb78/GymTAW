package es.uma.service;


import es.uma.dao.AsignacionClienteDietistaRepository;
import es.uma.dao.AsignacionClienteEntrenadorRepository;
import es.uma.dto.AsignacionClienteDietistaDTO;
import es.uma.dto.AsignacionClienteEntrenadorDTO;
import es.uma.dto.UserDTO;
import es.uma.entity.AsignacionClienteDietista;
import es.uma.entity.AsignacionClienteEntrenador;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@author: Pablo Miguel Aguilar Blanco

@Service
public class AsignacionClienteDietistaService {
    @Autowired
    AsignacionClienteDietistaRepository asignacionClienteDietistaRepository;

    @Autowired
    UserService userService;

    public List<AsignacionClienteDietistaDTO> findByCustomer(Integer id){
        return this.convertListEntityToDto(asignacionClienteDietistaRepository.buscarPorCliente(id));
    }

    public List<AsignacionClienteDietistaDTO> findByDietist(Integer id){
        return this.convertListEntityToDto(asignacionClienteDietistaRepository.buscarPorDietista(id));
    }

    public void addDietist(Integer id, Integer idCliente){
        AsignacionClienteDietista acd = new AsignacionClienteDietista();
        acd.setDietista(userService.convertDtoToEntity(userService.getById(id)));
        acd.setCliente(userService.convertDtoToEntity(userService.getById(idCliente)));

        asignacionClienteDietistaRepository.save(acd);
    }

    public Integer deleteAsociation(Integer id){
        AsignacionClienteDietista acd = asignacionClienteDietistaRepository.findById(id).orElse(null);
        Integer clienteID = acd.getCliente().getId();
        asignacionClienteDietistaRepository.delete(acd);
        return clienteID;
    }

    public void deleteByDietist(Integer id){
        List<AsignacionClienteDietista> asignaciones = asignacionClienteDietistaRepository.buscarPorDietista(id);
        if (asignaciones != null || !asignaciones.isEmpty()) {
            for (AsignacionClienteDietista asignacion : asignaciones) {
                asignacionClienteDietistaRepository.deleteById(asignacion.getId());
            }
        }
    }

    public void deleteByCustomer(Integer id){
        List<AsignacionClienteDietista> asignacionesDietista = asignacionClienteDietistaRepository.buscarPorCliente(id);
        if (asignacionesDietista != null || !asignacionesDietista.isEmpty()) {
            for (AsignacionClienteDietista asignacionDietista : asignacionesDietista) {
                asignacionClienteDietistaRepository.deleteById(asignacionDietista.getId());
            }
        }
    }


    public AsignacionClienteDietistaDTO convertEntityToDto(AsignacionClienteDietista asignacionClienteDietista) {
        AsignacionClienteDietistaDTO asignacionClienteDietistaDTO = new AsignacionClienteDietistaDTO();
        asignacionClienteDietistaDTO.setDietista(userService.convertEntityToDto(asignacionClienteDietista.getDietista()));
        asignacionClienteDietistaDTO.setCliente(userService.convertEntityToDto(asignacionClienteDietista.getCliente()));
        asignacionClienteDietistaDTO.setId(asignacionClienteDietista.getId());
        return asignacionClienteDietistaDTO;
    }

    public List<AsignacionClienteDietistaDTO> convertListEntityToDto(List<AsignacionClienteDietista> asignacionClienteDietistas){
        List<AsignacionClienteDietistaDTO> asignacionClienteDietistaDTOList = new ArrayList<>();
        for(AsignacionClienteDietista asignacionClienteDietista : asignacionClienteDietistas){
            asignacionClienteDietistaDTOList.add(this.convertEntityToDto(asignacionClienteDietista));
        }
        return asignacionClienteDietistaDTOList;
    }

}

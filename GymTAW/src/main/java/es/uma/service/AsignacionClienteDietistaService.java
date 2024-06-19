package es.uma.service;


import es.uma.dao.AsignacionClienteDietistaRepository;
import es.uma.dao.AsignacionClienteEntrenadorRepository;
import es.uma.dto.AsignacionClienteDietistaDTO;
import es.uma.dto.AsignacionClienteEntrenadorDTO;
import es.uma.entity.AsignacionClienteDietista;
import es.uma.entity.AsignacionClienteEntrenador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsignacionClienteDietistaService {
    @Autowired
    AsignacionClienteDietistaRepository asignacionClienteDietistaRepository;

    @Autowired
    UserService userService;

    public List<AsignacionClienteDietistaDTO> findByCustomer(Integer id){
        return this.convertListEntityToDto(asignacionClienteDietistaRepository.buscarPorCliente(id));
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

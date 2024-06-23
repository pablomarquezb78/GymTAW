package es.uma.service;


import es.uma.dao.AsignacionClienteEntrenadorRepository;
import es.uma.dto.AsignacionClienteEntrenadorDTO;
import es.uma.dto.UserDTO;
import es.uma.entity.AsignacionClienteDietista;
import es.uma.entity.AsignacionClienteEntrenador;
import es.uma.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@author: Pablo Miguel Aguilar Blanco

@Service
public class AsignacionClienteEntrenadorService {

    @Autowired
    AsignacionClienteEntrenadorRepository asignacionClienteEntrenadorRepository;

    @Autowired
    UserService userService;

    public List<AsignacionClienteEntrenadorDTO> findByCustomer(Integer id){
        return this.convertListEntityToDto(asignacionClienteEntrenadorRepository.buscarPorCliente(id));
    }

    public List<AsignacionClienteEntrenadorDTO> findByTrainer(Integer id){
        return this.convertListEntityToDto(asignacionClienteEntrenadorRepository.buscarPorEntrenador(id));
    }

    public void addTrainer(Integer id, Integer idCliente){
        AsignacionClienteEntrenador ace = new AsignacionClienteEntrenador();
        ace.setEntrenador(userService.convertDtoToEntity(userService.getById(id)));
        ace.setCliente(userService.convertDtoToEntity(userService.getById(idCliente)));

        asignacionClienteEntrenadorRepository.save(ace);
    }

    public Integer deleteAsociation(Integer id){
        AsignacionClienteEntrenador ace = asignacionClienteEntrenadorRepository.findById(id).orElse(null);
        Integer clienteID = ace.getCliente().getId();
        asignacionClienteEntrenadorRepository.delete(ace);
        return clienteID;
    }

    public void deleteByTrainer(Integer id){
        List<AsignacionClienteEntrenador> asignaciones = asignacionClienteEntrenadorRepository.buscarPorEntrenador(id);
        if (asignaciones != null || !asignaciones.isEmpty()) {
            for (AsignacionClienteEntrenador asignacion : asignaciones) {
                asignacionClienteEntrenadorRepository.deleteById(asignacion.getId());
            }
        }
    }

    public void deleteByCustomer(Integer id){
        List<AsignacionClienteEntrenador> asignacionesEntrenador = asignacionClienteEntrenadorRepository.buscarPorCliente(id);
        if (asignacionesEntrenador != null || !asignacionesEntrenador.isEmpty()) {
            for (AsignacionClienteEntrenador asignacionEntrenador : asignacionesEntrenador) {
                asignacionClienteEntrenadorRepository.deleteById(asignacionEntrenador.getId());
            }
        }
    }


    public AsignacionClienteEntrenadorDTO convertEntityToDto(AsignacionClienteEntrenador asignacionClienteEntrenador) {
        AsignacionClienteEntrenadorDTO asignacionClienteEntrenadorDTO = new AsignacionClienteEntrenadorDTO();
        asignacionClienteEntrenadorDTO.setEntrenador(userService.convertEntityToDto(asignacionClienteEntrenador.getEntrenador()));
        asignacionClienteEntrenadorDTO.setCliente(userService.convertEntityToDto(asignacionClienteEntrenador.getCliente()));
        asignacionClienteEntrenadorDTO.setId(asignacionClienteEntrenador.getId());
        return asignacionClienteEntrenadorDTO;
    }

    public List<AsignacionClienteEntrenadorDTO> convertListEntityToDto(List<AsignacionClienteEntrenador> asignacionClienteEntrenadorList){
        List<AsignacionClienteEntrenadorDTO> asignacionClienteEntrenadorDTOList = new ArrayList<>();
        for(AsignacionClienteEntrenador asignacionClienteEntrenador : asignacionClienteEntrenadorList){
            asignacionClienteEntrenadorDTOList.add(this.convertEntityToDto(asignacionClienteEntrenador));
        }
        return asignacionClienteEntrenadorDTOList;
    }
}

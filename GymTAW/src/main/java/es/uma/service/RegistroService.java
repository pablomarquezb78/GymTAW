package es.uma.service;

import es.uma.dao.RegistroRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.RegistroDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroService {

    @Autowired
    public RegistroRepository registroRepository;


    public List<RegistroDTO> getAllRegisters(){
        return registroRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public RegistroDTO getRegisterById(Integer id){
        return this.convertEntityToDto(registroRepository.findById(id).orElse(null));
    }

    public void deleteRegisterById(Integer id){
        registroRepository.deleteById(id);
    }

    public RegistroDTO convertEntityToDto(Registro registro){
        RegistroDTO registroDTO = new RegistroDTO();
        registroDTO.setId(registro.getId());
        registroDTO.setNombre(registro.getNombre());
        registroDTO.setApellidos(registro.getApellidos());
        registroDTO.setPassword(registro.getPassword());
        registroDTO.setRol(registro.getRol());
        registroDTO.setFechaNacimiento(registro.getFechaNacimiento());

        return registroDTO;
    }
}

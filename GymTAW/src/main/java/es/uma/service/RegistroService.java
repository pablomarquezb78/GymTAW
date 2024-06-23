package es.uma.service;

import es.uma.dao.RegistroRepository;
import es.uma.dto.EjercicioDTO;
import es.uma.dto.RegistroDTO;
import es.uma.entity.Ejercicio;
import es.uma.entity.Registro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroService {

    @Autowired
    public RegistroRepository registroRepository;

    //@author: Pablo Miguel Aguilar Blanco
    public List<RegistroDTO> getAllRegisters(){
        return registroRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    //@author: Pablo Miguel Aguilar Blanco
    public RegistroDTO getRegisterById(Integer id){
        return this.convertEntityToDto(registroRepository.findById(id).orElse(null));
    }

    //@author: Pablo Miguel Aguilar Blanco
    public void save(String username, String password, String nombre, String apellidos, String nacimiento, Integer telefono, Integer rol){
        Registro peticion = new Registro();
        peticion.setUsername(username);
        peticion.setPassword(password);
        peticion.setNombre(nombre);
        peticion.setApellidos(apellidos);

        LocalDate fecha = LocalDate.parse(nacimiento);

        peticion.setFechaNacimiento(fecha);
        peticion.setTelefono(telefono);
        peticion.setRol(rol);

        this.registroRepository.save(peticion);
    }

    public void deleteRegisterById(Integer id){
        registroRepository.deleteById(id);
    }

    //@author: Pablo Miguel Aguilar Blanco
    public RegistroDTO convertEntityToDto(Registro registro){
        RegistroDTO registroDTO = new RegistroDTO();
        registroDTO.setId(registro.getId());
        registroDTO.setNombre(registro.getNombre());
        registroDTO.setApellidos(registro.getApellidos());
        registroDTO.setPassword(registro.getPassword());
        registroDTO.setRol(registro.getRol());
        registroDTO.setFechaNacimiento(registro.getFechaNacimiento());
        registroDTO.setTelefono(registro.getTelefono());

        return registroDTO;
    }
}

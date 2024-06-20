package es.uma.service;

import es.uma.dao.DiaDietaRepository;
import es.uma.dto.DiaDietaDTO;
import es.uma.entity.DiaDieta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaDietaService {

    @Autowired
    DiaDietaRepository diaDietaRepository;
    @Autowired
    UserService userService;


    public void save(DiaDieta diaDieta){
        diaDietaRepository.save(diaDieta);
    }

    public DiaDietaDTO convertEntityToDto(DiaDieta diaDieta){
        DiaDietaDTO diaDietaDTO = new DiaDietaDTO();
        diaDietaDTO.setCliente(userService.convertEntityToDto(diaDieta.getCliente()));
        diaDietaDTO.setDietista(userService.convertEntityToDto(diaDieta.getDietista()));
        diaDietaDTO.setFecha(diaDieta.getFecha());
        return diaDietaDTO;
    }
}

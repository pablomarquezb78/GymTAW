package es.uma.service;

import es.uma.dao.DiaDietaRepository;
import es.uma.dao.DiaEntrenamientoRepository;
import es.uma.dto.DiaDietaDTO;
import es.uma.entity.DiaDieta;
import es.uma.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DiaDietaService {

    @Autowired
    DiaDietaRepository diaDietaRepository;
    @Autowired
    UserService userService;
    @Autowired
    private DiaEntrenamientoRepository diaEntrenamientoRepository;

    public DiaDietaDTO getDiaDietaDeClienteFecha(User user, LocalDate fecha){
        return convertEntityToDto(diaDietaRepository.diaDietaConcretoCliente(user,fecha));
    }

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

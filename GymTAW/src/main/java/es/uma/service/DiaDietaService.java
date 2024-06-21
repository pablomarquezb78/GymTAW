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

    public void guardarDiaDieta(DiaDietaDTO diaDietaDTO){
        DiaDieta diaDieta = convertDtoToEntity(diaDietaDTO);
        diaDietaRepository.save(diaDieta);
    }

    public DiaDietaDTO getDiaDietaDeClienteFecha(Integer userId, LocalDate fecha){
        return convertEntityToDto(diaDietaRepository.diaDietaConcretoCliente(userId,fecha));
    }

    public void save(DiaDieta diaDieta){
        diaDietaRepository.save(diaDieta);
    }

    public DiaDietaDTO convertEntityToDto(DiaDieta diaDieta) {
        DiaDietaDTO diaDietaDTO = new DiaDietaDTO();
        diaDietaDTO.setId(diaDieta.getId());
        diaDietaDTO.setCliente(userService.convertEntityToDto(diaDieta.getCliente()));
        diaDietaDTO.setDietista(userService.convertEntityToDto(diaDieta.getDietista()));
        diaDietaDTO.setFecha(diaDieta.getFecha());
        diaDietaDTO.setSeguimiento(diaDieta.getSeguimiento());
        return diaDietaDTO;
    }

    public DiaDieta convertDtoToEntity(DiaDietaDTO diaDietaDTO) {
        DiaDieta diaDieta = new DiaDieta();
        diaDieta.setId(diaDietaDTO.getId());
        diaDieta.setCliente(userService.convertDtoToEntity(diaDietaDTO.getCliente()));
        diaDieta.setDietista(userService.convertDtoToEntity(diaDietaDTO.getDietista()));
        diaDieta.setFecha(diaDietaDTO.getFecha());
        diaDieta.setSeguimiento(diaDietaDTO.getSeguimiento());
        return diaDieta;
    }

}

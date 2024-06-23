package es.uma.service;

import es.uma.dao.DiaDietaRepository;
import es.uma.dao.DiaEntrenamientoRepository;
import es.uma.dto.DiaDietaDTO;
import es.uma.dto.UserDTO;
import es.uma.entity.DiaDieta;
import es.uma.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiaDietaService {

    @Autowired
    DiaDietaRepository diaDietaRepository;
    @Autowired
    UserService userService;
    @Autowired
    private DiaEntrenamientoRepository diaEntrenamientoRepository;

    //@author: Pablo Márquez Benítez
    public void guardarDiaDieta(DiaDietaDTO diaDietaDTO){
        DiaDieta diaDieta = convertDtoToEntity(diaDietaDTO);
        diaDietaRepository.save(diaDieta);
    }

    //@author: Pablo Márquez Benítez
    public DiaDietaDTO getDiaDietaDeClienteFecha(Integer userId, LocalDate fecha){
        DiaDieta diaDieta = diaDietaRepository.diaDietaConcretoCliente(userId,fecha);
        if(diaDieta!=null){
            return convertEntityToDto(diaDieta);
        }else{
            return null;
        }
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<DiaDietaDTO> getByCustomer(Integer id){
        return diaDietaRepository.findByCustomer(id).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<DiaDietaDTO> getByDietist(Integer id){
        return diaDietaRepository.findByDietist(id).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    //@author: Pablo Miguel Aguilar Blanco
    public void deleteByCustomer(Integer id){
        List<DiaDieta> diaDietas = diaDietaRepository.findByCustomer(id);
        if(diaDietas != null || !diaDietas.isEmpty()){
            for(DiaDieta dieta : diaDietas){
                diaDietaRepository.deleteById(dieta.getId());
            }
        }
    }

    //@author: Pablo Miguel Aguilar Blanco
    public void deleteByDietist(Integer id){
        List<DiaDieta> diaDietas = diaDietaRepository.findByDietist(id);
        if(diaDietas != null || !diaDietas.isEmpty()){
            for(DiaDieta dieta : diaDietas){
                diaDietaRepository.deleteById(dieta.getId());
            }
        }
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public DiaDietaDTO getDiaDietaByDietistaClienteFecha(UserDTO dietistaDTO, UserDTO clienteDTO, LocalDate fecha)
    {
        User dietista = userService.convertDtoToEntity(dietistaDTO);
        User cliente = userService.convertDtoToEntity(clienteDTO);
        DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
        DiaDietaDTO diaDietaDTO = null;
        if(diaDieta != null) diaDietaDTO = convertEntityToDto(diaDieta);
        return diaDietaDTO;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public DiaDietaDTO getDiaDietaByDietistaClienteFechaOrCreateIfNull(UserDTO dietistaDTO, UserDTO clienteDTO, LocalDate fecha)
    {
        User dietista = userService.convertDtoToEntity(dietistaDTO);
        User cliente = userService.convertDtoToEntity(clienteDTO);
        DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha);
        if(diaDieta == null)
        {
            diaDieta = new DiaDieta();
            diaDieta.setCliente(cliente);
            diaDieta.setDietista(dietista);
            diaDieta.setFecha(fecha);
            diaDietaRepository.save(diaDieta);
            diaDieta = diaDietaRepository.getUltimoDiaDietaAdded();
        }
        DiaDietaDTO diaDietaDTO = convertEntityToDto(diaDieta);
        return diaDietaDTO;
    }

    //@author: Pablo Miguel Aguilar Blanco
    public void save(DiaDieta diaDieta){
        diaDietaRepository.save(diaDieta);
    }

    //@author: Pablo Miguel Aguilar Blanco
    public DiaDietaDTO convertEntityToDto(DiaDieta diaDieta) {
        DiaDietaDTO diaDietaDTO = new DiaDietaDTO();
        diaDietaDTO.setId(diaDieta.getId());
        diaDietaDTO.setCliente(userService.convertEntityToDto(diaDieta.getCliente()));
        diaDietaDTO.setDietista(userService.convertEntityToDto(diaDieta.getDietista()));
        diaDietaDTO.setFecha(diaDieta.getFecha());
        diaDietaDTO.setSeguimiento(diaDieta.getSeguimiento());
        return diaDietaDTO;
    }

    //@author: Pablo Márquez Benítez
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

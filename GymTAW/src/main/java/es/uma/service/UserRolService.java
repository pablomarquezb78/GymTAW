package es.uma.service;


import es.uma.dao.RegistroRepository;
import es.uma.dao.UserRolRepository;
import es.uma.dto.UserRolDTO;
import es.uma.entity.UserRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRolService {

    @Autowired
    UserRolRepository userRolRepository;

    public UserRolDTO getUserRolById(Integer rolId){
        return this.convertEntityToDto(userRolRepository.findById(rolId).orElse(null));
    }

    public List<UserRolDTO> getAllRoles(){
        return userRolRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public UserRol convertDtoToEntity(UserRolDTO userRolDTO) {
        UserRol userRol = new UserRol();
        userRol.setId(userRolDTO.getId());
        userRol.setRolUsuario(userRolDTO.getRolUsuario());
        return userRol;
    }

    public UserRolDTO convertEntityToDto(UserRol userRol) {
        UserRolDTO userRolDTO = new UserRolDTO();
        userRolDTO.setId(userRol.getId());
        userRolDTO.setRolUsuario(userRol.getRolUsuario());
        return userRolDTO;
    }

}

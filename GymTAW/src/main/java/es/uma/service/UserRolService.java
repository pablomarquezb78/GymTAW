package es.uma.service;


import es.uma.dto.UserRolDTO;
import es.uma.entity.UserRol;
import org.springframework.stereotype.Service;

@Service
public class UserRolService {




    public UserRolDTO convertEntityToDto(UserRol userRol) {
        UserRolDTO userRolDTO = new UserRolDTO();
        userRolDTO.setId(userRol.getId());
        userRolDTO.setRolUsuario(userRol.getRolUsuario());
        return userRolDTO;
    }

}

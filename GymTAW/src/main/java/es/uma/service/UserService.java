package es.uma.service;

import es.uma.dao.UserRepository;
import es.uma.dto.UserDTO;
import es.uma.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolService userRolService;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllCustomers() {
        return userRepository.listarClientes()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllTrainers() {
        return userRepository.listarEntrenadores()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllDietistas() {
        return this.userRepository.listarDietistas()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getClientesDeEntrenador(User user) {
        return userRepository.clientesAsociadosConEntrenador(user)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDTO convertEntityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setNombre(user.getNombre());
        userDTO.setApellidos(user.getApellidos());
        userDTO.setTelefono(user.getTelefono());
        userDTO.setPeso(user.getPeso());
        userDTO.setAltura(user.getAltura());
        userDTO.setFechaNacimiento(user.getFechaNacimiento());
        userDTO.setDescripcionPersonal(user.getDescripcionPersonal());
        userDTO.setRol(userRolService.convertEntityToDto(user.getRol()));
        return userDTO;
    }


}

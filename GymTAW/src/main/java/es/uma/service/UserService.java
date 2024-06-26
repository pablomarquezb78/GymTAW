package es.uma.service;

import es.uma.dao.UserRepository;
import es.uma.dao.UserRolRepository;
import es.uma.dto.*;
import es.uma.entity.AsignacionClienteDietista;
import es.uma.entity.AsignacionClienteEntrenador;
import es.uma.entity.User;
import es.uma.entity.UserRol;
import es.uma.ui.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolService userRolService;

    @Autowired
    private RegistroService registroService;
    @Autowired
    private UserRolRepository userRolRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    //@author: Pablo Miguel Aguilar Blanco
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

    public List<UserDTO> getClientesDeEntrenadorYNombre(User user,String nombre) {
        return userRepository.clientesAsociadosConEntrenadorYNombre(user,nombre)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    //@author: Pablo Miguel Aguilar Blanco
    public UserDTO createNewUser(Integer registerId){
        UserDTO userDTO = new UserDTO();
        RegistroDTO registroDTO = registroService.getRegisterById(registerId);
        UserRolDTO userRolDTO = userRolService.getUserRolById(registroDTO.getRol());

        userDTO.setUsername(registroDTO.getUsername());
        userDTO.setNombre(registroDTO.getNombre());
        userDTO.setPassword(registroDTO.getPassword());
        userDTO.setRol(userRolDTO);
        userDTO.setFechaNacimiento(registroDTO.getFechaNacimiento());
        userDTO.setApellidos(registroDTO.getApellidos());
        userDTO.setTelefono(registroDTO.getTelefono());

        this.saveNewUser(userDTO);

        return userDTO;
    }

    public void setUserVerPerfilCliente(Usuario usuario,User user){
        usuario.setRol(user.getRol().getId());
        usuario.setId(user.getId());
        usuario.setUsername(user.getUsername());
        usuario.setNombre(user.getNombre());
        usuario.setApellidos(user.getApellidos());
        usuario.setTelefono(user.getTelefono());
        usuario.setPeso(user.getPeso());
        usuario.setAltura(user.getAltura());
        usuario.setFechaNacimiento(String.valueOf(user.getFechaNacimiento()));
        usuario.setDescripcionPersonal(user.getDescripcionPersonal());

    }

    public void guardarCambiosPerfil(Usuario usuario){

        User user = userRepository.getById(usuario.getId());
        user.setNombre(usuario.getNombre());
        user.setApellidos(usuario.getApellidos());
        user.setTelefono(usuario.getTelefono());
        user.setPeso(usuario.getPeso());
        user.setAltura(usuario.getAltura());
        user.setFechaNacimiento(LocalDate.parse(usuario.getFechaNacimiento()));
        user.setDescripcionPersonal(usuario.getDescripcionPersonal());
        userRepository.save(user);
    }

    //@author: Pablo Miguel Aguilar Blanco
    public void saveNewUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setNombre(userDTO.getNombre());
        user.setApellidos(userDTO.getApellidos());
        user.setTelefono(userDTO.getTelefono());
        user.setFechaNacimiento(userDTO.getFechaNacimiento());
        user.setRol(userRolService.convertDtoToEntity(userDTO.getRol()));

        userRepository.save(user);
    }

    //@author: Pablo Miguel Aguilar Blanco
    public void saveUser(Usuario usuario){
        User user = new User();
        user.setUsername(usuario.getUsername());
        user.setPassword(usuario.getPassword());
        user.setNombre(usuario.getNombre());
        user.setApellidos(usuario.getApellidos());
        user.setTelefono(usuario.getTelefono());
        user.setPeso(usuario.getPeso());
        user.setAltura(usuario.getAltura());
        user.setFechaNacimiento(this.convertirStringALocalDate(usuario.getFechaNacimiento()));
        user.setDescripcionPersonal(usuario.getDescripcionPersonal());
        user.setRol(userRolRepository.getById(usuario.getRol()));

        userRepository.save(user);
    }

    //@author: Pablo Miguel Aguilar Blanco
    public void editUser(Usuario usuario){
        User user = userRepository.findById(usuario.getId()).orElse(null);
        user.setUsername(usuario.getUsername());
        user.setPassword(usuario.getPassword());
        user.setNombre(usuario.getNombre());
        user.setApellidos(usuario.getApellidos());
        user.setTelefono(usuario.getTelefono());
        user.setPeso(usuario.getPeso());
        user.setAltura(usuario.getAltura());
        user.setFechaNacimiento(this.convertirStringALocalDate(usuario.getFechaNacimiento()));
        user.setDescripcionPersonal(usuario.getDescripcionPersonal());
        user.setRol(userRolRepository.getById(usuario.getRol()));

        userRepository.save(user);
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> filterUsers(String nombre, String apellidos){
        return this.convertlistEntityToDto(userRepository.filtrarUsuarios(nombre, apellidos));
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> filterUsersWithDate(String nombre, String apellidos, LocalDate fechaNacimiento){
        return this.convertlistEntityToDto(userRepository.filtrarUsuariosConFecha(nombre, apellidos, fechaNacimiento));
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> filterUsersWithRol(String nombre, String apellidos, Integer rol){
        return this.convertlistEntityToDto(userRepository.filtrarUsuariosConRol(nombre, apellidos, rol));
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> filterUsersWithRolAndDate(String nombre, String apellidos, LocalDate fechaNacimiento, Integer rol){
        return this.convertlistEntityToDto(userRepository.filtrarUsuariosConRolYFecha(nombre, apellidos, fechaNacimiento, rol));
    }



    public UserDTO getById(Integer id){
        return this.convertEntityToDto(userRepository.findById(id).orElse(null));
    }

    //@author: Pablo Miguel Aguilar Blanco
    public Usuario setUsuario(Usuario usuario, UserDTO user){
        usuario.setUsername(user.getUsername());
        usuario.setPassword(user.getPassword());
        usuario.setNombre(user.getNombre());
        usuario.setApellidos(user.getApellidos());
        usuario.setPeso(user.getPeso());
        usuario.setAltura(user.getAltura());
        usuario.setRol(user.getRol().getId());
        usuario.setTelefono(user.getTelefono());
        usuario.setFechaNacimiento(user.getFechaNacimiento().toString());

        return usuario;
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> asociatedTrainers(List<AsignacionClienteEntrenadorDTO> asignacionClienteEntrenadorDTO){
        List<UserDTO> entrenadoresAsociados = new ArrayList<>();
        for(AsignacionClienteEntrenadorDTO asignacion : asignacionClienteEntrenadorDTO){
            entrenadoresAsociados.add(asignacion.getEntrenador());
        }
        return  entrenadoresAsociados;
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> noAsociatedTrainers(List<UserDTO> noTrainers){
        return this.convertlistEntityToDto(userRepository.entrenadoresNoAsociadosAlCliente(this.convertlistDtoToEntity(noTrainers)));
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> asociatedDietist(List<AsignacionClienteDietistaDTO> asignacionClienteDietistaDTO){
        List<UserDTO> dietistasAsociados = new ArrayList<>();
        for(AsignacionClienteDietistaDTO asignacion : asignacionClienteDietistaDTO){
            dietistasAsociados.add(asignacion.getDietista());
        }
        return  dietistasAsociados;
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> noAsociatedDietist(List<UserDTO> noDietist){
        return this.convertlistEntityToDto(userRepository.dietistasNoAsociadosAlCliente(this.convertlistDtoToEntity(noDietist)));
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public UserDTO findUserByUsernameAndPassword(String username, String password){
        User user = this.userRepository.findByUsernamePassword(username, password);
        UserDTO userDTO = null;
        if(user != null) userDTO = this.convertEntityToDto(user);
        return userDTO;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public UserDTO guardarDietistaPerfilEdit(UserDTO userDTO, Usuario dietista)
    {
        User dietistaUser = this.convertDtoToEntity(userDTO);
        dietistaUser.setNombre(dietista.getNombre());
        dietistaUser.setApellidos(dietista.getApellidos());
        dietistaUser.setAltura(dietista.getAltura());
        dietistaUser.setPeso(dietista.getPeso());
        dietistaUser.setDescripcionPersonal(dietista.getDescripcionPersonal());
        userRepository.save(dietistaUser);
        UserDTO userDTOout = this.convertEntityToDto(dietistaUser);
        return userDTOout;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<UserDTO> getClientesAsociadosAlDietista(UserDTO dietistaDTO)
    {
        User dietista = this.convertDtoToEntity(dietistaDTO);
        List<User> clientes = userRepository.clientesAsociadosConDietista(dietista);
        List<UserDTO> clientesDTO = this.convertlistEntityToDto(clientes);
        return clientesDTO;
    }

    //@author: Pablo Miguel Aguilar Blanco
    public User convertDtoToEntity(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setNombre(userDTO.getNombre());
        user.setApellidos(userDTO.getApellidos());
        user.setTelefono(userDTO.getTelefono());
        user.setPeso(userDTO.getPeso());
        user.setAltura(userDTO.getAltura());
        user.setFechaNacimiento(userDTO.getFechaNacimiento());
        user.setDescripcionPersonal(userDTO.getDescripcionPersonal());
        user.setRol(userRolService.convertDtoToEntity(userDTO.getRol()));

        return user;
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

    //@author: Pablo Miguel Aguilar Blanco
    public List<UserDTO> convertlistEntityToDto(List<User> userList){
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : userList){
            userDTOList.add(this.convertEntityToDto(user));
        }
        return userDTOList;
    }

    //@author: Pablo Miguel Aguilar Blanco
    public List<User> convertlistDtoToEntity(List<UserDTO> userDtoList){
        List<User> userList = new ArrayList<>();
        for(UserDTO user : userDtoList){
            userList.add(this.convertDtoToEntity(user));
        }
        return userList;
    }

    //@author: Pablo Miguel Aguilar Blanco
    private LocalDate convertirStringALocalDate(String fechaStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(fechaStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }


}

package es.uma.dto;

import es.uma.entity.UserRol;
import lombok.Data;


import java.time.LocalDate;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private Integer telefono;
    private Integer peso;
    private Integer altura;
    private LocalDate fechaNacimiento;
    private String descripcionPersonal;
    private UserRol rol;
}

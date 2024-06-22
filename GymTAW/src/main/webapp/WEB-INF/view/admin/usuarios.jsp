<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.ui.Usuario" %>
<%@ page import="es.uma.dto.UserDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserDTO> usuarios = (List<UserDTO>) request.getAttribute("usuarios");
    List<UserRolDTO> roles = (List<UserRolDTO>) request.getAttribute("roles");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin~Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
</head>

<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
</br>
<table class="table table-bordered table-hover">
    <thead class="text-white text-center" style="background-color: #343a40">
    <tr>
        <th>ID</th>
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th>PESO</th>
        <th>ALTURA</th>
        <th>TELEFONO</th>
        <th>ACCIONES</th>
    </tr>
    </thead>
    <tbody>
    <%
        for(UserDTO user : usuarios){
    %>
    <tr class="text-center">
        <td><%=user.getId()%></td>
        <td><%=user.getNombre()%></td>
        <td><%=user.getApellidos()%></td>
        <td><%=user.getRol().getRolUsuario()%></td>
        <td><%=user.getFechaNacimiento()%></td>
        <td><%=user.getPeso()%>Kg</td>
        <td><%=user.getAltura()%>cm</td>
        <td><%=user.getTelefono()%></td>
        <td>
            <a href="/admin/editarUsuario?id=<%=user.getId()%>" class="btn btn-warning btn-sm">
                <i class="fas fa-pencil-alt"></i> Editar
            </a>
            <a href="/admin/borrarUsuario?id=<%=user.getId()%>" class="btn btn-danger btn-sm">
                <i class="fas fa-trash-alt"></i> Borrar
            </a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<a href="/admin/crearNuevoUsuario" class="btn btn-warning mt-3">Crear nuevo usuario</a>
<br>

<form:form action="/admin/filtrarUsuarios" method="post" modelAttribute="usuario" class="p-4">
    <div class="form-row">
        <div class="form-group col-md-4">
            <label for="nombre">Nombre:</label>
            <form:input path="nombre" size="15" class="form-control" id="nombre" style="width: auto;"></form:input>
        </div>
        <div class="form-group col-md-8">
            <label for="apellidos">Apellidos:</label>
            <form:input path="apellidos" size="50" class="form-control" id="apellidos" style="width: auto;"></form:input>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-4">
            <label for="fechaNacimiento">Fecha de nacimiento:</label>
            <form:input path="fechaNacimiento" type="date" size="10" class="form-control" id="fechaNacimiento" style="width: auto;"></form:input>
        </div>
        <div class="form-group col-md-8">
            <label for="rol">Rol:</label>
            <div>
                <form:radiobuttons path="rol" items="${roles}" itemLabel="rolUsuario" delimiter="</br>" itemValue="id"></form:radiobuttons>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mt-3">Filtrar usuario</button>
</form:form>
</body>
</html>

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
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

<br/>

<table border="1" cellpadding="10" cellspacing="10">
    <tr>
        <th>ID</th>
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th>PESO</th>
        <th>ALTURA</th>
        <th>TELEFONO</th>
        <th></th>
        <th></th>
    </tr>

    <%
        for(UserDTO user : usuarios){

    %>
    <tr>
        <td><%=user.getId()%></td>
        <td><%=user.getNombre()%></td>
        <td><%=user.getApellidos()%></td>
        <td><%=user.getRol().getRolUsuario()%></td>
        <td><%=user.getFechaNacimiento()%></td>
        <td><%=user.getPeso()%></td>
        <td><%=user.getAltura()%></td>
        <td><%=user.getTelefono()%></td>
        <td><a href="/admin/editarUsuario?id=<%=user.getId()%>" class="btn btn-success"><i class="fas fa-pencil-alt"></i> Editar</a></td>
        <td><a href="/admin/borrarUsuario?id=<%=user.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/admin/crearNuevoUsuario" class="btn btn-success mt-3">Crear nuevo usuario</a>
<br>

<form:form action="/admin/filtrarUsuarios" method="post" modelAttribute="usuario">
    <br>
    <label>Nombre:</label>
    <form:input path="nombre" size="15"></form:input>
    <label>Apellidos: </label>
    <form:input path="apellidos" size="50"></form:input>
    <br>
    <label>Fecha de nacimiento:</label>
    <form:input path="fechaNacimiento" size="10"></form:input>
    <label>Rol:</label>
    <form:radiobuttons path="rol" items="${roles}" itemLabel="rolUsuario" itemValue="id"></form:radiobuttons>
    <br>
    <form:button class="btn btn-success mt-3">Filtrar usuario</form:button>
</form:form>


</body>
</html>

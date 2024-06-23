<!-- @Author: Pablo Miguel Aguilar Blanco -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.dto.UserDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserDTO> usuarios = (List<UserDTO>) request.getAttribute("usuarios");
    List<UserRolDTO> roles = (List<UserRolDTO>) request.getAttribute("roles");
    request.setAttribute("paginaActual", "usuarios");

%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin~Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
    <style>
        .container {
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #343a40 !important;
            color: #ffffff;
        }
    </style>
</head>

<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
</br>
<div class="container">
    <h3>Lista de usuarios</h3>
    <%
        if (usuarios != null && !usuarios.isEmpty()) {
    %>
    <table class="table table-bordered table-hover">
        <thead class="text-white text-center" style="background-color: #343a40">
        <tr>
            <th>ID</th>
            <th>USERNAME</th>
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
        </thead>
        <tbody>
        <%
            for (UserDTO user : usuarios) {
        %>
        <tr class="text-center">
            <td><%=user.getId()%></td>
            <td><%=user.getUsername()%></td>
            <td><%=user.getNombre()%></td>
            <td><%=user.getApellidos()%></td>
            <td><%=user.getRol().getRolUsuario()%></td>
            <td><%=user.getFechaNacimiento()%></td>
            <td><%=user.getPeso()%>Kg</td>
            <td><%=user.getAltura()%>cm</td>
            <td><%=user.getTelefono()%></td>
            <td>
                <a href="/admin/editarUsuario?id=<%=user.getId()%>" class="btn btn-primary btn-sm">
                    <i class="fas fa-pencil-alt"></i> Editar
                </a>
            </td>
            <td>
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
    <%
    } else {
    %>
    <p>No hay usuarios inscritos en la aplicación</p>
    <%
        }
    %>
    <a href="/admin/crearNuevoUsuario" class="btn btn-primary mt-3"> <i class="fas fa-plus"></i> Crear nuevo usuario</a>
</div>

<br>

<div class="container">
    <h5>Opciones de búsqueda</h5>
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
</div>
</body>
</html>

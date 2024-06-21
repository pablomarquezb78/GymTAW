<%@ page import="es.uma.entity.User" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserDTO> entrenadores = (List<UserDTO>) request.getAttribute("entrenadores");
    UserDTO cliente = (UserDTO) request.getAttribute("cliente");
%>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

<br/>
<table class="table table-bordered table-hover">
    <thead class="text-center" style="background-color: #343a40; color: white;">
    <tr>
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <% for (UserDTO entrenador : entrenadores) { %>
    <tr class="text-center">
        <td><%= entrenador.getNombre() %></td>
        <td><%= entrenador.getApellidos() %></td>
        <td><%= entrenador.getRol().getRolUsuario() %></td>
        <td><%= entrenador.getFechaNacimiento() %></td>
        <td><a href="/admin/anyadirAsignacionEntrenador?id=<%= entrenador.getId() %>&idCliente=<%= cliente.getId() %>" class="btn btn-success btn-sm">Confirmar</a></td>
    </tr>
    <% } %>
    </tbody>
</table>

</body>
</html>
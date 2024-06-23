<!-- @Author: Pablo Miguel Aguilar Blanco -->

<%@ page import="java.util.*" %>
<%@ page import="es.uma.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserDTO> entrenadores = (List<UserDTO>) request.getAttribute("entrenadores");
    UserDTO cliente = (UserDTO) request.getAttribute("cliente");
    request.setAttribute("paginaActual", "asignar");
%>

<html>
<head>
    <title>Admin~Asignar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

<br/>
<div class="container">
    <h3>Lista de entrenadores</h3>
    <% if (entrenadores.size() > 0) { %>
    <p>Entrenadores no asignados a <%=cliente.getNombre()%> <%=cliente.getApellidos()%></p>
    <table class="table table-bordered table-hover">
        <thead class="text-center" style="background-color: #343a40; color: white;">
        <tr>
            <th>ID</th>
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
            <td><%= entrenador.getId() %></td>
            <td><%= entrenador.getNombre() %></td>
            <td><%= entrenador.getApellidos() %></td>
            <td><%= entrenador.getRol().getRolUsuario() %></td>
            <td><%= entrenador.getFechaNacimiento() %></td>
            <td><a href="/admin/anyadirAsignacionEntrenador?id=<%= entrenador.getId() %>&idCliente=<%= cliente.getId() %>" class="btn btn-success btn-sm">Confirmar</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <p>No hay entrenadores para asignar al cliente <%=cliente.getNombre()%> <%=cliente.getApellidos()%></p>
    <% } %>
</div>
</body>
</html>
<!-- @Author: Pablo Miguel Aguilar Blanco -->
<%@ page import="java.util.*" %>
<%@ page import="es.uma.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserDTO> clientes = (List<UserDTO>) request.getAttribute("clientes");
    request.setAttribute("paginaActual", "asignar");

%>

<html>
<head>
    <title>Admin~Asignar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
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
    <h3>Lista de clientes</h3>
    <p>Asigna entrenadores o dietistas a un cliente</p>
    <%
        if (clientes != null && !clientes.isEmpty()) {
    %>
    <table class="table table-bordered table-hover">
        <thead class="table_header text-white text-center">
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
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for (UserDTO cliente : clientes) {
        %>
        <tr class="text-center">
            <td><%= cliente.getId() %></td>
            <td><%= cliente.getNombre() %></td>
            <td><%= cliente.getApellidos() %></td>
            <td><%= cliente.getRol().getRolUsuario() %></td>
            <td><%= cliente.getFechaNacimiento() %></td>
            <td><%= cliente.getPeso() %>Kg</td>
            <td><%= cliente.getAltura() %>cm</td>
            <td><%= cliente.getTelefono() %></td>
            <td>
                <a href="/admin/asignarEntrenador?id=<%= cliente.getId() %>" class="btn btn-primary btn-sm">
                    <i class="fas fa-user-plus"></i> Asignar entrenador
                </a>
            </td>
            <td>
                <a href="/admin/asignarDietista?id=<%= cliente.getId() %>" class="btn btn-primary btn-sm">
                    <i class="fas fa-user-plus"></i> Asignar dietista
                </a>
            </td>
            <td>
                <a href="/admin/eliminarAsignaciones?id=<%= cliente.getId() %>" class="btn btn-danger btn-sm">
                    <i class="fas fa-trash-alt"></i> Eliminar asignaciones
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
    <p>No hay clientes inscritos en la aplicación</p>
    <%
        }
    %>
</div>

</body>
</html>
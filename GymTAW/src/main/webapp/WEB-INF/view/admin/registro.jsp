<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Registro" %>
<%@ page import="es.uma.dto.RegistroDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<RegistroDTO> peticiones = (List<RegistroDTO>) request.getAttribute("peticiones");
    request.setAttribute("paginaActual", "autenticar");

%>

<html>
<head>
    <title>Admin~Registro</title>
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
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

<br/>

<%
    if (peticiones.isEmpty()) {
%>
<h1 class="text-center">No hay peticiones de registro</h1>
<%
} else {
%>
<div class="container">
    <h3>Listado de peticiones de registro</h3>
    <table class="table table-bordered table-hover">
        <thead class="text-center" style="background-color: #343a40; color: white;">
        <tr>
            <th>ID</th>
            <th>USERNAME</th>
            <th>NOMBRE</th>
            <th>APELLIDOS</th>
            <th>ROL</th>
            <th>FECHA NACIMIENTO</th>
            <th>TELEFONO</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <% for (RegistroDTO usuario : peticiones) { %>
        <tr class="text-center">
            <td><%= usuario.getId() %></td>
            <td><%= usuario.getUsername() %></td>
            <td><%= usuario.getNombre() %></td>
            <td><%= usuario.getApellidos() %></td>
            <td>
                <%
                    String rol = "";
                    switch (usuario.getRol()) {
                        case 2: rol = "cliente"; break;
                        case 3: rol = "bodybuilder"; break;
                        case 4: rol = "crosstrainer"; break;
                        case 5: rol = "dietista"; break;
                        default: rol = "Otro";
                    }
                %>
                <%= rol %>
            </td>
            <td><%= usuario.getFechaNacimiento() %></td>
            <td><%= usuario.getTelefono() %></td>
            <td>
                <a href="/admin/autenticar?id=<%= usuario.getId() %>" class="btn btn-success btn-sm"> Autenticar </a>
            </td>
            <td>
                <a href="/admin/borrarPeticion?id=<%= usuario.getId() %>" class="btn btn-danger btn-sm">
                    <i class="fas fa-trash-alt"></i> Cancelar
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<%
    }
%>


</body>
</html>
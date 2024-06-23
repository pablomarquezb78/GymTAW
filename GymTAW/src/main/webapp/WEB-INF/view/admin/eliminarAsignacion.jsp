<!-- @Author: Pablo Miguel Aguilar Blanco -->

<%@ page import="java.util.*" %>
<%@ page import="es.uma.dto.AsignacionClienteEntrenadorDTO" %>
<%@ page import="es.uma.dto.AsignacionClienteDietistaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<AsignacionClienteEntrenadorDTO> asignacionesEntrenador = (List<AsignacionClienteEntrenadorDTO>) request.getAttribute("asignacionesEntrenador");
    List<AsignacionClienteDietistaDTO> asignacionesDietista = (List<AsignacionClienteDietistaDTO>) request.getAttribute("asignacionesDietista");
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
<div class="row">
    <div class="col-md-6">
        <div class="container">
            <h3>Entrenadores asignados</h3>
            <% if (asignacionesEntrenador.size() > 0) { %>
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
                <% for (AsignacionClienteEntrenadorDTO entrenador : asignacionesEntrenador) { %>
                <tr class="text-center">
                    <td><%= entrenador.getEntrenador().getNombre() %></td>
                    <td><%= entrenador.getEntrenador().getApellidos() %></td>
                    <td><%= entrenador.getEntrenador().getRol().getRolUsuario() %></td>
                    <td><%= entrenador.getEntrenador().getFechaNacimiento() %></td>
                    <td><a href="/admin/eliminarAsignacionEntrenador?id=<%= entrenador.getId() %>" class="btn btn-danger btn-sm">Eliminar entrenador</a></td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p>No hay asignado ningún entrenador</p>
            <% } %>
        </div>
    </div>
    <div class="col-md-6">
        <div class="container">
            <h3>Dietistas asignados</h3>
            <% if (asignacionesDietista.size() > 0) { %>
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
                <% for (AsignacionClienteDietistaDTO dietista : asignacionesDietista) { %>
                <tr class="text-center">
                    <td><%= dietista.getDietista().getNombre() %></td>
                    <td><%= dietista.getDietista().getApellidos() %></td>
                    <td><%= dietista.getDietista().getRol().getRolUsuario() %></td>
                    <td><%= dietista.getDietista().getFechaNacimiento() %></td>
                    <td><a href="/admin/eliminarAsignacionDietista?id=<%= dietista.getId() %>" class="btn btn-danger btn-sm">Eliminar dietista</a></td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } else { %>
            <p>No hay asignado ningún dietista</p>
            <% } %>
        </div>
    </div>
</div>


</body>
</html>
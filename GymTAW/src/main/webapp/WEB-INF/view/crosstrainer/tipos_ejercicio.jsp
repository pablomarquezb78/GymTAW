<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.dto.TipoEjercicioDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>

<%
    List<TipoEjercicioDTO> tiposEjercicio = (List<TipoEjercicioDTO>) request.getAttribute("tiposEjercicio");
    String cabecera = "cabecera_entrenador.jsp";
%>
<html>
<head>
    <title>Tipos de ejercicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous">
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
            background-color: #007bff;
            color: #ffffff;
        }
        .btn-custom {
            margin-right: 10px;
        }
        .btn-editar {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-borrar {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn {
            margin-top: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="<%=cabecera%>"></jsp:include>
<br>
<div class="container">
    <h1>Tipos de ejercicio</h1>

    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre del Tipo de Ejercicio</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <% if (tiposEjercicio.size() > 0) { %>
        <% for (TipoEjercicioDTO tipo : tiposEjercicio) { %>
        <tr>
            <td><%= tipo.getId() %></td>
            <td><%= tipo.getTipoDeEjercicio() %></td>
            <td><a href="/entrenamientos/editarTipo?id=<%= tipo.getId() %>" class="btn btn-primary btn-primary"><i class="fas fa-pencil-alt"></i> Editar</a></td>
            <td><a href="/entrenamientos/borrarTipo?id=<%= tipo.getId() %>" class="btn btn-danger btn-borrar"><i class="fas fa-trash-alt"></i> Borrar</a></td>
            <td><a href="/entrenamientos/verImplementacionesAsociadasTipo?id=<%= tipo.getId() %>" class="btn btn-info"><i class="fas fa-eye"></i> Ver ejercicios asociados</a></td>
        </tr>
        <% } %>
        <% } else { %>
        <tr>
            <td colspan="5"><h1>No hay creado ningún tipo de ejercicio :(</h1></td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <a href="/entrenamientos/crear-tipo" class="btn btn-success mt-3">Crear nuevo tipo de ejercicio</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

<%@ page import="es.uma.entity.DiaEntrenamiento" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.dto.DiaEntrenamientoDTO" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer id = (Integer) request.getAttribute("idcliente");
    List<DiaEntrenamientoDTO> diaEntrenamientos = (List<DiaEntrenamientoDTO>) request.getAttribute("diasEntrenamientos");
%>
<html>
<head>
    <title>Calendario Ejercicios</title>
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
            text-align: center;
        }
        th {
            background-color: #343a40 !important;
            color: #ffffff;
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
<jsp:include page="cabecera_entrenador.jsp"/>

<div class="container mt-5">
    <h1 class="text-center mb-4">Entrenamientos</h1>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col">Fecha</th>
            <th scope="col">Editar</th>
            <th scope="col">Borrar</th>
            <th scope="col">Feedback</th>
        </tr>
        </thead>
        <tbody>
        <% if (!diaEntrenamientos.isEmpty()) {
            for (DiaEntrenamientoDTO diaEntrenamiento : diaEntrenamientos) { %>
        <tr>
            <td><%= diaEntrenamiento.getFecha() %></td>
            <td><a href="/entrenamientos/crearrutina?idrutina=<%= diaEntrenamiento.getRutina().getId() %>" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Editar</a></td>
            <td>
                <form method="post" action="/entrenamientos/borrardia">
                    <input type="hidden" name="id" value="<%= diaEntrenamiento.getId() %>">
                    <button type="submit" class="btn btn-danger btn-borrar"><i class="fas fa-trash-alt"></i> Borrar</button>
                </form>
            </td>
            <td><a href="/entrenamientos/verDesempenyoEntrenamientosEntrenador?idUsuario=<%= diaEntrenamiento.getCliente().getId() %>&fecha=<%= diaEntrenamiento.getFecha() %>" class="btn btn-info"><i class="fas fa-eye"></i> Ver feedback</a></td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="4"><h3>No hay ningún entrenamiento :(</h3></td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <form method="post" action="/entrenamientos/nuevo-entrenamiento" class="mt-3">
        <input type="hidden" name="id" value="<%= id %>">
        <button type="submit" class="btn btn-success">Nuevo Entrenamiento</button>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

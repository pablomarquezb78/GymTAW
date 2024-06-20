<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Rutina" %>
<%@ page import="es.uma.dto.RutinaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String cabecera = "cabecera_entrenador.jsp";
    List<RutinaDTO> rutinas = (List<RutinaDTO>) request.getAttribute("rutinas");
%>
<html>
<head>
    <title>Rutinas</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <!-- Font Awesome CSS for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
</head>
<body>
<jsp:include page="<%=cabecera%>"></jsp:include>

<div class="container mt-5">
    <h2>Buscar Rutinas</h2>

    <form method="post" action="/entrenamientos/filtrarRutinas" class="mb-4">
        <div class="input-group mb-3">
            <input id="nombrerutina" name="nombrerutina" type="text" class="form-control me-2" placeholder="Nombre de Rutina">
            <button type="submit" class="btn btn-success me-1" name="pormi" value="1">SOLO CREADAS POR MI</button>
            <button type="submit" class="btn btn-success" name="pormi" value="0">BUSCAR TODAS</button>
        </div>
    </form>

    <% if(rutinas.size() > 0) { %>
    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>Nombre</th>
            <th>Entrenador</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <% for (RutinaDTO rutina : rutinas) { %>
        <tr>
            <td><%= rutina.getNombre() %></td>
            <td><%= rutina.getEntrenador().getNombre() %></td>
            <td><a href="/entrenamientos/crearrutina?idrutina=<%= rutina.getId() %>" class="btn btn-success">Editar</a></td>
            <td><a href="/entrenamientos/borrarRutina?idrutina=<%= rutina.getId() %>" class="btn btn-danger">Borrar</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else { %>
    <h3>No hay rutinas creadas :(</h3>
    <% } %>
</div>

<!-- Bootstrap JS and dependencies (optional, only if needed) -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-..." crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-..." crossorigin="anonymous"></script>
</body>
</html>

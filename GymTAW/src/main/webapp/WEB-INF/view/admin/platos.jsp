<!-- @Author: Pablo Miguel Aguilar Blanco -->

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.dto.PlatoDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<PlatoDTO> platos = (List<PlatoDTO>) request.getAttribute("platos");
    request.setAttribute("paginaActual", "platos");

%>

<html>
<head>
    <title>Admin~Platos</title>
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
<div class="container">
    <h3>Lista de platos</h3>
    <% if (platos.size() > 0) { %>
    <table class="table table-bordered table-hover">
        <thead class="text-white text-center" style="background-color: #343a40">
        <tr>
            <th>ID</th>
            <th>NOMBRE</th>
            <th>TIEMPO</th>
            <th>RECETA</th>
            <th>ENLACE DEL VÍDEO</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for(PlatoDTO plato : platos){
        %>
        <tr class="text-center">
            <td><%=plato.getId()%></td>
            <td><%=plato.getNombre()%></td>
            <td><%=plato.getTiempoDePreparacion()%>min</td>
            <td><%=plato.getReceta()%></td>
            <td><%=plato.getEnlaceReceta()%></td>

            <td>
                <a href="/admin/editarPlato?id=<%=plato.getId()%>" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> Editar</a>
            </td>
            <td>
                <a href="/admin/borrarPlato?id=<%=plato.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a>
            </td>
            <td>
                <a href="/admin/verComidasAsociadas?id=<%=plato.getId()%>" class="btn btn-info"> <i class="fas fa-eye"></i> Ver comidas asociadas</a>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
 <% } else { %>
    <p>No hay platos creados en la aplicación</p>
<% } %>
<a href="/admin/crearNuevoPlato" class="btn btn-primary mt-3"><i class="fas fa-plus"></i> Crear nuevo plato</a>

</div>
</br>
<div class="container">
    <h5>Opciones de búsqueda</h5>
<form:form action="/admin/filtrarPlatos" method="post" modelAttribute="plato" class="p-4">
    <div class="form-row">
        <div class="form-group col-md-4">
            <label>Nombre:</label>
            <form:input path="nombre" size="20" class="form-control" style="width: auto;"></form:input>
        </div>
        <div class="form-group col-md-4">
            <label>Tiempo:</label>
            <form:input path="tiempoDePreparacion" size="5" class="form-control" style="width: auto;"></form:input>
        </div>
        <div class="form-group col-md-4">
            <label>Receta:</label>
            <form:input path="receta" size="50" class="form-control" style="width: auto;"></form:input>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mt-3">Filtrar plato</button>
</form:form>
</div>
</body>
</html>
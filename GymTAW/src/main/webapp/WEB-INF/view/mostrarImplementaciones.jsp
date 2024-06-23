<!-- @Authors: Pablo Miguel Aguilar Blanco & Adrián Fernandez Vera & Antonio Salvador Gámez Zafra -->

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.*" %>
<%@ page import="es.uma.dto.ImplementacionEjercicioRutinaDTO" %>
<%@ page import="es.uma.dto.EjercicioDTO" %>
<%@ page import="es.uma.dto.RutinaDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<ImplementacionEjercicioRutinaDTO> implementaciones = (List<ImplementacionEjercicioRutinaDTO>) request.getAttribute("implementaciones");
    EjercicioDTO ejercicio = (EjercicioDTO) request.getAttribute("ejercicio");
    List<RutinaDTO> rutinas = (List<RutinaDTO>) request.getAttribute("rutinas");

    String dir = "/comun/filtrarImplementaciones?id="+ejercicio.getId();

    UserRolDTO rol = (UserRolDTO) request.getAttribute("rol");
    String title = "Trainer";
    String cabecera = "./crosstrainer/cabecera_entrenador.jsp";
    request.setAttribute("paginaActual", "ejercicios");

    if(rol.getId() == 1){
        title = "Admin~Ejercicios";
        cabecera = "./admin/cabeceraAdmin.jsp";
    }
%>

<html>
<head>
    <title><%=title%></title>
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
<jsp:include page="<%=cabecera%>"></jsp:include>

<br/>

<div class="container">
    <h3>Rutinas asociadas al ejercicio: <%=ejercicio.getNombre()%></h3>
    <% if (implementaciones.size() > 0) { %>
    <table class="table table-bordered table-hover">
        <thead class="text-center" style="background-color: #343a40; color: white;">
        <tr>
            <th>ID</th>
            <th>NOMBRE DE LA RUTINA</th>
            <th>SERIES</th>
            <th>REPETICIONES</th>
            <th>PESO</th>
            <th>METROS</th>
            <th>TIEMPO</th>
            <th>KCAL</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <% for (ImplementacionEjercicioRutinaDTO implementacion : implementaciones) { %>
        <tr class="text-center">
            <td><%= implementacion.getId() %></td>
            <td><%= implementacion.getRutina().getNombre() %></td>
            <td><%= implementacion.getSets() %></td>
            <td><%= implementacion.getRepeticiones() %></td>
            <td><%= implementacion.getPeso()%>Kg</td>
            <td><%= implementacion.getMetros()%></td>
            <td><%= implementacion.getTiempo()%>s</td>
            <td><%= implementacion.getKilocalorias() %></td>
            <td><a href="/entrenamientos/editarimplementaciondefinitiva?id=<%= implementacion.getId() %>" class="btn btn-primary btn-sm"> <i class="fas fa-pencil-alt"></i> Editar</a></td>
            <td><a href="/comun/borrarImplementacion?idEjercicio=<%= ejercicio.getId() %>&idImplementacion=<%= implementacion.getId() %>" class="btn btn-danger btn-sm"> <i class="fas fa-trash-alt"></i> Borrar</a></td>
        </tr>
        <% } %>
        </tbody>
    </table>
<% } else { %>
    <p>No hay ninguna implementación creada</p>
<% }
    if(rol.getId() == 1){
%>
<a href="/comun/crearImplementacion?id=<%= ejercicio.getId() %>" class="btn btn-primary"><i class="fas fa-plus"></i> Crear nueva implementación</a>
</div>
<%
    }
%>
</br>
<div class="container">
    <h5>Opciones de búsqueda</h5>
    <form:form action="<%=dir%>" method="post" modelAttribute="implementacion" class="p-4">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="rutina">Rutina:</label>
                <form:select path="rutina" items="${rutinas}" itemLabel="nombre" itemValue="id" class="form-control" style="width: auto;"></form:select>
            </div>
            <div class="form-group col-md-6">
                <label for="sets">Series:</label>
                <form:input path="sets" id="sets" class="form-control" size="10" style="width: auto;"/>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="repeticiones">Repeticiones:</label>
                <form:input path="repeticiones" id="repeticiones" class="form-control" size="10" style="width: auto;"/>
            </div>
            <div class="form-group col-md-6">
                <label for="peso">Peso:</label>
                <form:input path="peso" id="peso" class="form-control" size="10" style="width: auto;"/>
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="tiempo">Tiempo:</label>
                <form:input path="tiempo" id="tiempo" class="form-control" size="10" style="width: auto;"/>
            </div>
            <div class="form-group col-md-4">
                <label for="metros">Metros:</label>
                <form:input path="metros" id="metros" class="form-control" size="10" style="width: auto;"/>
            </div>
            <div class="form-group col-md-4">
                <label for="kilocalorias">Kcal:</label>
                <form:input path="kilocalorias" id="kilocalorias" class="form-control" size="10" style="width: auto;"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary mt-3">Filtrar implementacion</button>
    </form:form>
</div>
</body>
</html>
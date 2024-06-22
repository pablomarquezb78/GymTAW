<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.CantidadIngredientePlatoComida" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="es.uma.entity.TipoComida" %>
<%@ page import="es.uma.dto.CantidadIngredientePlatoComidaDTO" %>
<%@ page import="es.uma.dto.IngredienteDTO" %>
<%@ page import="es.uma.dto.PlatoDTO" %>
<%@ page import="es.uma.dto.TipoComidaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<CantidadIngredientePlatoComidaDTO> comidas = (List<CantidadIngredientePlatoComidaDTO>) request.getAttribute("comidas");
    List<IngredienteDTO> ingredientes = (List<IngredienteDTO>) request.getAttribute("ingredientes");
    List<TipoComidaDTO> tiposComida = (List<TipoComidaDTO>) request.getAttribute("tiposComida");
    PlatoDTO plato = (PlatoDTO) request.getAttribute("plato");
    String dir = "/admin/filtrarComidas?id="+plato.getId();
    request.setAttribute("paginaActual", "platos");
%>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />

</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

<br/>
<% if (comidas.size() > 0) { %>
<table class="table table-bordered table-hover">
    <thead class="text-center" style="background-color: #343a40; color: white;">
    <tr>
        <th>ID</th>
        <th>FECHA/DÍA</th>
        <th>CLIENTE</th>
        <th>DIETISTA</th>
        <th>FRANJA HORARIA</th>
        <th>CANTIDAD</th>
        <th>INGREDIENTES</th>
        <th>ACCIONES</th>
    </tr>
    </thead>
    <tbody>
    <% for (CantidadIngredientePlatoComidaDTO comida : comidas) { %>
    <tr class="text-center">
        <td><%= comida.getId() %></td>
        <td><%= comida.getComida().getDiaDieta().getFecha() %></td>
        <td><%= comida.getComida().getDiaDieta().getCliente().getNombre() %></td>
        <td><%= comida.getComida().getDiaDieta().getDietista().getNombre() %></td>
        <td><%= comida.getComida().getTipoComida().getComidaDelDia() %></td>
        <td><%= comida.getCantidad() %>g</td>
        <td>
            <%
                String ingredientesStr = "";
                for (IngredienteDTO ingrediente : ingredientes) {
                    ingredientesStr += ingrediente.getNombre() + ", ";
                }
                if (ingredientesStr.length() > 0) {
                    ingredientesStr = ingredientesStr.substring(0, ingredientesStr.length() - 2);
                }
            %>
            <%= ingredientesStr %>
        </td>
        <td><a href="/admin/editarComida?idPlato=<%= plato.getId() %>&idComida=<%= comida.getId() %>" class="btn btn-warning btn-sm"><i class="fas fa-pencil-alt"></i> Editar</a>
        <a href="/admin/borrarComida?idPlato=<%= plato.getId() %>&idComida=<%= comida.getId() %>" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt"></i> Borrar</a></td>
    </tr>
    <% } %>
    </tbody>
</table>
<% } else { %>
<div class="text-center">
    <h1>No hay comidas creadas</h1>
</div>
<% } %>

<div>
    <a href="/admin/crearNuevaComida?idPlato=<%= plato.getId() %>" class="btn btn-warning mt-3"><i class="fas fa-plus"></i> Crear nueva comida</a>
</div>


<br>
<form:form action="<%=dir%>" method="post" modelAttribute="cantidadPlatoComida" class="p-4">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="nombreCliente">Cliente:</label>
            <form:input path="nombreCliente" id="nombreCliente" class="form-control" size="15" style="width: auto;"/>
        </div>
        <div class="form-group col-md-6">
            <label for="nombreDietista">Dietista:</label>
            <form:input path="nombreDietista" id="nombreDietista" class="form-control" size="15" style="width: auto;"/>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="tipoComida">Franja horaria:</label>
            <form:select path="tipoComida" items="${tiposComida}" itemLabel="comidaDelDia" itemValue="id" class="form-control" style="width: auto;"></form:select>
        </div>
        <div class="form-group col-md-6">
            <label for="cantidad">Cantidad:</label>
            <form:input path="cantidad" id="cantidad" class="form-control" size="10" style="width: auto;"/>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mt-3">Filtrar plato</button>
</form:form>

</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.CantidadIngredientePlatoComida" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="es.uma.entity.TipoComida" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<CantidadIngredientePlatoComida> comidas = (List<CantidadIngredientePlatoComida>) request.getAttribute("comidas");
    List<Ingrediente> ingredientes = (List<Ingrediente>) request.getAttribute("ingredientes");
    List<TipoComida> tiposComida = (List<TipoComida>) request.getAttribute("tiposComida");
    Plato plato = (Plato) request.getAttribute("plato");
    String dir = "/admin/filtrarComidas?id="+plato.getId();
%>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

<br/>
<table border="1" cellpadding="10" cellspacing="10">
    <tr>
        <th>ID</th>
        <th>FECHA/DÍA</th>
        <th>CLIENTE</th>
        <th>DIETISTA</th>
        <th>FRANJA HORARIA</th>
        <th>CANTIDAD</th>
        <th>INGREDIENTES</th>
        <th></th>
        <th></th>
    </tr>

    <%
        for(CantidadIngredientePlatoComida comida : comidas){

    %>
    <tr>
        <td><%=comida.getId()%></td>
        <td><%=comida.getComida().getDiaDieta().getFecha()%></td>
        <td><%=comida.getComida().getDiaDieta().getCliente().getNombre()%></td>
        <td><%=comida.getComida().getDiaDieta().getDietista().getNombre()%></td>
        <td><%=comida.getComida().getTipoComida().getComidaDelDia()%></td>
        <td><%=comida.getCantidad()%></td>
        <td>
            <% for(Ingrediente ingrediente : ingredientes){
            %>
                <%=ingrediente.getNombre() +", "%>
            <%
                }
            %>
        </td>

        <td><a href="/admin/editarComida?idPlato=<%=plato.getId()%>&idComida=<%=comida.getId()%>">Editar</a></td>
        <td><a href="/admin/borrarComida?idPlato=<%=plato.getId()%>&idComida=<%=comida.getId()%>">Borrar</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/admin/crearNuevaComida?idPlato=<%=plato.getId()%>" class="btn btn-success mt-3">Crear nueva comida</a>

<br>
<form:form action="<%=dir%>" method="post" modelAttribute="cantidadPlatoComida">
    <br>
    <label>Cliente:</label>
    <form:input path="nombreCliente" size="15"></form:input>
    <label>Dietista: </label>
    <form:input path="nombreDietista" size="50"></form:input>
    <br>
    <label>Franja horaria:</label>
    <form:select path="tipoComida" items="${tiposComida}" itemLabel="comidaDelDia" itemValue="id"></form:select>
    <label>Cantidad:</label>
    <form:input path="cantidad" size="50"></form:input>
    <br>
    <form:button class="btn btn-success mt-3">Filtrar plato</form:button>
</form:form>
</body>
</html>
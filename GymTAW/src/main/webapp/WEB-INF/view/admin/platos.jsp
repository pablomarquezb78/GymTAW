<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="es.uma.dto.PlatoDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<PlatoDTO> platos = (List<PlatoDTO>) request.getAttribute("platos");
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
<table border="1" cellpadding="10" cellspacing="10">
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

    <%
        for(PlatoDTO plato : platos){

    %>
    <tr>
        <td><%=plato.getId()%></td>
        <td><%=plato.getNombre()%></td>
        <td><%=plato.getTiempoDePreparacion()%></td>
        <td><%=plato.getReceta()%></td>
        <td><%=plato.getEnlaceReceta()%></td>

        <td><a href="/admin/editarPlato?id=<%=plato.getId()%>" class="btn btn-success"><i class="fas fa-pencil-alt"></i> Editar</a></td>
        <td><a href="/admin/borrarPlato?id=<%=plato.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a></td>
        <td><a href="/admin/verComidasAsociadas?id=<%=plato.getId()%>">Ver comidas asociadas</a></td>
    </tr>
    <%
        }
    %>
</table>
<td><a href="/admin/crearNuevoPlato"  class="btn btn-success mt-3">Crear nuevo plato</a></td>

<form:form action="/admin/filtrarPlatos" method="post" modelAttribute="plato">
    <br>
    <label>Nombre:</label>
    <form:input path="nombre" size="15"></form:input>
    <label>Tiempo:</label>
    <form:input path="tiempoDePreparacion" size="10"></form:input>
    <label>Receta:</label>
    <form:input path="receta" size="30"></form:input>
    <br>
    <form:button class="btn btn-success mt-3">Filtrar plato</form:button>
</form:form>
</body>
</html>
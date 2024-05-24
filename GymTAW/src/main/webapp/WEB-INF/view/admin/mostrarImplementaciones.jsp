
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<ImplementacionEjercicioRutina> implementaciones = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");
    Ejercicio ejercicio = (Ejercicio) request.getAttribute("ejercicio");
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
        <th>NOMBRE DE LA RUTINA</th>
        <th>SERIES</th>
        <th>REPETICIONES</th>
        <th>PESO</th>
        <th>METROS</th>
        <th>TIEMPO</th>
        <th>KCAL</th>
        <th></th>
    </tr>

    <%
        for(ImplementacionEjercicioRutina implementacion : implementaciones){

    %>
    <tr>
        <td><%=implementacion.getId()%></td>
        <td><%=implementacion.getRutina().getNombre()%></td>
        <td><%=implementacion.getSets()%></td>
        <td><%=implementacion.getRepeticiones()%></td>
        <td><%=implementacion.getPeso()%></td>
        <td><%=implementacion.getMetros()%></td>
        <td><%=implementacion.getTiempo()%></td>
        <td><%=implementacion.getKilocalorias()%></td>
        <td><a href="/admin/editarImplementacion?id=<%=implementacion.getId()%>">Editar</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/admin/crearImplementacion?id=<%=ejercicio.getId()%>">Crear nueva implementacion</a>

<form:form action="/admin/filtrarImplementaciones" method="post" modelAttribute="implementacion">
    <br>
    <label>*Nombre:</label>
    <form:input path="nombre" size="15"></form:input>
    <label>*Apellidos: </label>
    <form:input path="apellidos" size="50"></form:input>
    <br>
    <label>*Fecha de nacimiento:</label>
    <form:input path="fechaNacimiento" size="10"></form:input>
    <label>*Rol:</label>
    <form:radiobuttons path="rol" items="${roles}" itemLabel="rolUsuario" itemValue="id"></form:radiobuttons>
    <br>
    <form:button class="btn btn-success mt-3">Filtrar implementacion</form:button>
</form:form>
</body>
</html>
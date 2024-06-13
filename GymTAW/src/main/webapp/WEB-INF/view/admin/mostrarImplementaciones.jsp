<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="es.uma.entity.Rutina" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<ImplementacionEjercicioRutina> implementaciones = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");
    Ejercicio ejercicio = (Ejercicio) request.getAttribute("ejercicio");
    List<Rutina> rutinas = (List<Rutina>) request.getAttribute("rutinas");
    String dir = "/admin/filtrarImplementaciones?id="+ejercicio.getId();
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
        <td><a href="/admin/borrarImplementacion?idEjercicio=<%=ejercicio.getId()%>&idImplementacion=<%=implementacion.getId()%>">Borrar</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/admin/crearImplementacion?id=<%=ejercicio.getId()%>">Crear nueva implementacion</a>

<form:form action="<%=dir%>" method="post" modelAttribute="implementacion">
    <br>
    <label>Rutina:</label>
    <form:select path="rutina" items="${rutinas}" itemLabel="nombre" itemValue="id" ></form:select>
    <label>Series: </label>
    <form:input path="sets" size="50"></form:input>
    <label>Repeticiones:</label>
    <form:input path="repeticiones" size="10"></form:input>
    <br>
    <label>Peso:</label>
    <form:input path="peso" size="10"></form:input>
    <label>Tiempo:</label>
    <form:input path="tiempo" size="10"></form:input>
    <label>Metros:</label>
    <form:input path="metros" size="10"></form:input>
    <label>Kcal:</label>
    <form:input path="kilocalorias" size="10"></form:input>
    <br>
    <form:button class="btn btn-success mt-3">Filtrar implementacion</form:button>
</form:form>
</body>
</html>
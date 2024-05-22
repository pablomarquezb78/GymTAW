
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
        <td><a href="/admin/editarImplementacion?id=<%=implementacion.getId()%>">Editar</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/admin/crearImplementacion?id=<%=ejercicio.getId()%>">Crear nueva implementacion</a>
</body>
</html>
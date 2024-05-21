
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<ImplementacionEjercicioRutina> rutinas = (List<ImplementacionEjercicioRutina>) request.getAttribute("rutinas");
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
        for(ImplementacionEjercicioRutina rutina : rutinas){

    %>
    <tr>
        <td><%=rutina.getId()%></td>
        <td><%=rutina.getRutina().getNombre()%></td>
        <td><%=rutina.getSets()%></td>
        <td><%=rutina.getRepeticiones()%></td>
        <td><%=rutina.getPeso()%></td>
        <td><%=rutina.getMetros()%></td>
        <td><%=rutina.getTiempo()%></td>
        <td><a href="/admin/editarImplementacion?id=<%=rutina.getId()%>">Editar</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/admin/crearImplementacion">Crear nueva implementacion</a>
</body>
</html>
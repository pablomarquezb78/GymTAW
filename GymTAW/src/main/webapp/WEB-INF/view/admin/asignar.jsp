<%@ page import="es.uma.entity.User" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<User> clientes = (List<User>) request.getAttribute("clientes");
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
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th>PESO</th>
        <th>ALTURA</th>
        <th>TELEFONO</th>
        <th></th>
        <th></th>
        <th></th>
    </tr>

    <%
        for(User cliente : clientes){

    %>
    <tr>
        <td><%=cliente.getId()%></td>
        <td><%=cliente.getNombre()%></td>
        <td><%=cliente.getApellidos()%></td>
        <td><%=cliente.getRol().getRolUsuario()%></td>
        <td><%=cliente.getFechaNacimiento()%></td>
        <td><%=cliente.getPeso()%></td>
        <td><%=cliente.getAltura()%></td>
        <td><%=cliente.getTelefono()%></td>
        <td><a href="/admin/asignarEntrenador?id=<%=cliente.getId()%>">Asignar entrenador</a></td>
        <td><a href="/admin/asignarDietista?id=<%=cliente.getId()%>">Asignar dietista</a></td>
        <td><a href="/admin/eliminarAsignaciones?id=<%=cliente.getId()%>">Eliminar asignaciones</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
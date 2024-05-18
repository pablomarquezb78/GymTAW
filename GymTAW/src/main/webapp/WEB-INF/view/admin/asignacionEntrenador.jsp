<%@ page import="es.uma.entity.User" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<User> entrenadores = (List<User>) request.getAttribute("entrenadores");
    User cliente = (User) request.getAttribute("cliente");
%>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item active">
                <a class="nav-link" href="/admin/">Inicio</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/admin/autenticarUsuarios">Autenticar</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/mostrarUsuarios">Usuarios</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/asignarCliente">Asignar</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/mostrarEjercicios">Ejercicios</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/admin/mostrarPlatos">Platos</a>
            </li>
        </ul>
    </div>
</nav>

<br/>
<table border="1" cellpadding="10" cellspacing="10">
    <tr>
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th></th>
        <th></th>
    </tr>

    <%
        for(User entrenador : entrenadores){

    %>
    <tr>
        <td><%=entrenador.getNombre()%></td>
        <td><%=entrenador.getApellidos()%></td>
        <td><%=entrenador.getRol().getRolUsuario()%></td>
        <td><%=entrenador.getFechaNacimiento()%></td>
        <td><a href="/admin/anyadirAsignacionEntrenador?id=<%=entrenador.getId()%>&idCliente=<%=cliente.getId()%>">Confirmar</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
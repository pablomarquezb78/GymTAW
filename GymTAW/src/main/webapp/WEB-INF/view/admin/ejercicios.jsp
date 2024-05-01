
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
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
                    <a class="nav-link">Autenticar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Usuarios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Asignar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Ejercicios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link">Platos</a>
                </li>
            </ul>
        </div>
    </nav>

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
        </tr>

<%
    for(Ejercicio ejercicio : ejercicios){

%>
        <tr>
            <td><%=ejercicio.getId()%></td>

            <td><a href="/admin/editar?id=<%=ejercicio.getId()%>">Editar</a></td>
            <td><a href="/admin/borrar?id=<%=ejercicio.getId()%>">Borrar</a></td>
        </tr>
<%
    }
%>
    </table>
</body>
</html>
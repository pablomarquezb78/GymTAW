
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
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
            <th>ID</th>
            <th>NOMBRE DEL EJERCICIO</th>
            <th>TIPO DE EJERCICIO</th>
            <th>ENLACE DEL VÍDEO</th>
            <th>DESCRIPCIÓN</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>

<%
    for(Ejercicio ejercicio : ejercicios){

%>
        <tr>
            <td><%=ejercicio.getId()%></td>
            <td><%=ejercicio.getNombre()%></td>
            <td><%=ejercicio.getTipo().getTipoDeEjercicio()%></td>
            <td><%=ejercicio.getEnlaceVideo()%></td>
            <td><%=ejercicio.getDescripcion()%></td>
            <td><a href="/admin/editarEjercicio?id=<%=ejercicio.getId()%>">Editar</a></td>
            <td><a href="/admin/borrarEjercicio?id=<%=ejercicio.getId()%>">Borrar</a></td>
            <td><a href="/admin/verRutinasAsociadas?id=<%=ejercicio.getId()%>">Ver rutinas asociadas</a></td>
        </tr>
<%
    }
%>
    </table>
<a href="/admin/crearNuevoEjercicio">Crear nuevo ejercicio</a>
</body>
</html>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Registro" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Registro> peticiones = (List<Registro>) request.getAttribute("peticiones");
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
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th>TELEFONO</th>
        <th></th>
        <th></th>
    </tr>

    <%
        String rol = "";
        for(Registro usuario : peticiones){
            if(usuario.getRol() == 2){
                rol = "cliente";
            }else if(usuario.getRol() == 3){
                rol = "bodybuilder";
            }else if(usuario.getRol() == 4){
                rol = "crosstrainer";
            }else if(usuario.getRol() == 5){
                rol = "dietista";
            }
    %>
    <tr>
        <td><%=usuario.getId()%></td>
        <td><%=usuario.getNombre()%></td>
        <td><%=usuario.getApellidos()%></td>
        <td><%=rol%></td>
        <td><%=usuario.getFechaNacimiento()%></td>
        <td><%=usuario.getTelefono()%></td>
        <td><a href="/admin/autenticar?id=<%=usuario.getId()%>">Autenticar</a></td>
        <td><a href="/admin/borrarPeticion?id=<%=usuario.getId()%>">Borrar</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
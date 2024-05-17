<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserRol> roles = (List<UserRol>) request.getAttribute("roles");
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
<div>
    <h3>
        Añadir usuario
    </h3>
    <p>Introduzca los datos necesarios para añadir un nuevo usuario</p>
    <form:form action="/admin/modificarUsuario" method="post" modelAttribute="usuario">
        <form:hidden path="id"></form:hidden>
        Usuario*: <form:input path="username"></form:input>
        Contraseña*: <form:input path="password"></form:input>
        Nombre*: <form:input path="nombre"></form:input>
        Apellidos*: <form:input path="apellidos"></form:input>
        Fecha de nacimiento*:
        Rol*: <form:radiobuttons path="rol" items="${roles}" itemLabel="rolUsuario" itemValue="id"></form:radiobuttons>
        Peso: <form:input path="peso"></form:input>
        Altura: <form:input path="altura"></form:input>
        Telefono: <form:input path="telefono"></form:input>
        <form:button>Guardar</form:button>
    </form:form>
</div>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<TipoEjercicio> tipos = (List<TipoEjercicio>) request.getAttribute("tipos");
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
        Crear ejercicio
    </h3>
    <p>Introduzca los datos necesarios para añadir un nuevo ejercicio</p>
    <form:form action="/admin/anyadirEjercicio" method="post" modelAttribute="ejercicioUI">
        Nombre: <form:input path="nombre"></form:input>
        Tipo: <form:radiobuttons path="idTipo" items="${tipos}" itemLabel="tipoDeEjercicio" itemValue="id"></form:radiobuttons>
        Enlace: <form:input path="enlaceVideo"></form:input>
        Descripcion: <form:input path="descripcion"></form:input>
        <form:button>Crear ejercicio</form:button>
    </form:form>
</div>
</body>
</html>
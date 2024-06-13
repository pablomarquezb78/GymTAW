<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.uma.ui.PlatoDietistaUI" %>
<%@ page import="es.uma.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User dietista = (User) request.getAttribute("dietista");
%>

<html>
<head>
    <title>Dietista perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item">
                <a class="nav-link" href="/dietista/">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Clientes</a>
            </li>
            <li class="nav-item active text-weight-bold">
                <a class="nav-link" href="/dietista/mostrarPerfil">Perfil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesión</a>
            </li>
        </ul>
    </div>
</nav>

<div class="row justify-content-center">
    <div class="col-sm-8 justify-content-center ">
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <b>Nombre:</b> <%=dietista.getNombre()%>
            </div>
            <div class="col justify-content-center">
                <b>Apellidos:</b> <%=dietista.getApellidos()%>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <b>Altura:</b> <%=dietista.getAltura()%>
            </div>
            <div class="col justify-content-center">
                <b>Fecha de Nacimiento:</b> <%=dietista.getFechaNacimiento()%>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <b>Peso:</b> <%=dietista.getPeso()%>
            </div>
            <div class="col justify-content-center">
                <b>Descripcion:</b> <%=dietista.getDescripcionPersonal()%>
            </div>
        </div>
        <div class="row justify-content-center">
            <a href="/dietista/editarPerfil"> Editar perfil </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
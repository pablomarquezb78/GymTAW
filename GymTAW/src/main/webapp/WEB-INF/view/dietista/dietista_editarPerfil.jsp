<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.uma.ui.PlatoDietistaUI" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.ui.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Usuario dietista = (Usuario) request.getAttribute("dietista");
%>

<html>
<head>
    <title>Dietista editar perfil</title>
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
        <form:form action="/dietista/guardarPerfil" method="post" modelAttribute="dietista">
            <div class="row justify-content-center">
                <div class="col justify-content-center">
                    <b>Nombre:</b> <form:input path="nombre" value="<%=dietista.getNombre()%>" size="50" maxlength="70"/>
                </div>
                <div class="col justify-content-center">
                    <b>Apellidos:</b> <form:input path="apellidos" value="<%=dietista.getApellidos()%>" size="50" maxlength="150"/>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col justify-content-center">
                    <b>Altura:</b> <form:input path="altura" value="<%=dietista.getAltura()%>" size="20" maxlength="20"/>
                </div>
                <div class="col justify-content-center">
                    <b>Fecha de Nacimiento:</b> <%=dietista.getFechaNacimiento()%>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col justify-content-center">
                    <b>Peso:</b> <form:input path="peso" value="<%=dietista.getPeso()%>" size="20" maxlength="20"/>
                </div>
                <div class="col justify-content-center">
                    <b>Descripcion:</b> <form:input path="descripcionPersonal" value="<%=dietista.getDescripcionPersonal()%>" size="50" maxlength="1500"/>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col justify-content-center">
                    <button>Guardar</button>
                </div>
                <div class="col justify-content-center">
                    <a href="/dietista/mostrarPerfil"> Cancelar </a>
                </div>
            </div>
        </form:form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
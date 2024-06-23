<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.ui.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Usuario dietista = (Usuario) request.getAttribute("dietista");
%>

<%-- @author: Jaime Ezequiel Rodriguez Rodriguez --%>

<html>
<head>
    <title>Dietista editar perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            border: 1px solid grey;
            margin-top: 20px;
        }
        .navbar-nav .nav-item .nav-link.active {
            font-weight: bold;
            color: black;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item">
                <a class="nav-link" href="/dietista/">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/dietista/mostrarClientes">Clientes</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/dietista/mostrarPerfil">Perfil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesión</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-sm-8 form-container">
            <form:form action="/dietista/guardarPerfil" method="post" modelAttribute="dietista">
                <div class="row mb-3">
                    <div class="col">
                        <label for="nombre" class="form-label"><b>Nombre:</b></label>
                        <form:input path="nombre" id="nombre" class="form-control" value="${dietista.nombre}" size="50" maxlength="70"/>
                    </div>
                    <div class="col">
                        <label for="apellidos" class="form-label"><b>Apellidos:</b></label>
                        <form:input path="apellidos" id="apellidos" class="form-control" value="${dietista.apellidos}" size="50" maxlength="150"/>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col">
                        <label for="altura" class="form-label"><b>Altura(cm):</b></label>
                        <form:input path="altura" id="altura" class="form-control" value="${dietista.altura}" size="20" maxlength="20"/>
                    </div>
                    <div class="col">
                        <label class="form-label"><b>Fecha de Nacimiento:</b></label>
                        <p class="form-control-plaintext">${dietista.fechaNacimiento}</p>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col">
                        <label for="peso" class="form-label"><b>Peso(kg):</b></label>
                        <form:input path="peso" id="peso" class="form-control" value="${dietista.peso}" size="20" maxlength="20"/>
                    </div>
                    <div class="col">
                        <label for="descripcionPersonal" class="form-label"><b>Descripción:</b></label>
                        <form:input path="descripcionPersonal" id="descripcionPersonal" class="form-control" value="${dietista.descripcionPersonal}" size="50" maxlength="1500"/>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col text-center">
                        <button type="submit" class="btn btn-primary">Guardar</button>
                        <a href="/dietista/mostrarPerfil" class="btn btn-secondary">Cancelar</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnbD4La6h/ZGHrR9Fcy0EF+P85y1GQ9xSLJk6l9gWgg1vsJ8fnC" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-cjC3uQ8FeI+NQFf01OM0e2dOj/5v5QVXyPhtkSfzAjQpF3GZbO14eO8ha5joWgu0" crossorigin="anonymous"></script>
</body>
</html>

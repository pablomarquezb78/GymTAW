<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User dietista = (User) request.getAttribute("dietista");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dietista perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .profile-container {
            background: rgba(0, 0, 0, 0.7);
            padding: 30px;
            border-radius: 10px;
            color: white;
        }
        .profile-label {
            font-weight: bold;
            color: white;
        }
        .profile-link a {
            color: white;
            text-decoration: underline;
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
            <li class="nav-item active text-weight-bold">
                <a class="nav-link" href="/dietista/mostrarPerfil">Perfil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesión</a>
            </li>
        </ul>
    </div>
</nav>

<div class="d-flex flex-column justify-content-center align-items-center vh-100">
    <div class="profile-container">
        <div class="row mb-3">
            <div class="col">
                <span class="profile-label">Nombre:</span> <%=dietista.getNombre()%>
            </div>
            <div class="col">
                <span class="profile-label">Apellidos:</span> <%=dietista.getApellidos()%>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <span class="profile-label">Altura:</span> <%=dietista.getAltura()%>
            </div>
            <div class="col">
                <span class="profile-label">Fecha de Nacimiento:</span> <%=dietista.getFechaNacimiento()%>
            </div>
        </div>
        <div class="row mb-3">
            <div class="col">
                <span class="profile-label">Peso:</span> <%=dietista.getPeso()%>
            </div>
            <div class="col">
                <span class="profile-label">Descripción:</span> <%=dietista.getDescripcionPersonal()%>
            </div>
        </div>
        <div class="row justify-content-center profile-link">
            <a href="/dietista/editarPerfil">Editar perfil</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnrROZqPLFpuUWI4a2sA8pD5A4cJZHPUOks+EmW1E6Lxk3VFtDM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGktK0gYf94IYNd2tKpREIHMR5cQm75J5pbWuyj6cvF2DkSPEj3h4dHGsR9" crossorigin="anonymous"></script>
</body>
</html>

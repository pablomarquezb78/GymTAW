<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- @author: Jaime Ezequiel Rodriguez Rodriguez --%>

<html>
<head>
    <title>Dietista Ingrediente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            color: black;
            border: 1px solid grey;
        }
        .form-label {
            font-weight: bold;
            color: black;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item active text-weight-bold">
                <a class="nav-link" href="/dietista/">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/dietista/mostrarClientes">Clientes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/dietista/mostrarPerfil">Perfil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesión</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row justify-content-center" style="height: 75vh;">
        <div class="col-sm-6 form-container">
            <form:form method="post" action="guardarNuevoIngrediente" modelAttribute="nuevoIngrediente">
                <div class="mb-3">
                    <label for="nombre" class="form-label">Nombre:</label>
                    <form:input type="text" path="nombre" id="nombre" size="50" maxlength="50" class="form-control"/> <br/>
                </div>
                <div class="mb-3">
                    <label for="kilocalorias" class="form-label">Kilocalorias(cada 100 gramos):</label>
                    <form:input type="text" path="kilocalorias" id="kilocalorias" size="30" maxlength="30" class="form-control"/> <br/>
                </div>
                <div class="mb-3">
                    <label for="proteinas" class="form-label">Proteinas(cada 100 gramos):</label>
                    <form:input type="text" path="proteinas" id="proteinas" size="30" maxlength="30" class="form-control"/> <br/>
                </div>
                <div class="mb-3">
                    <label for="grasas" class="form-label">Grasas(cada 100 gramos):</label>
                    <form:input type="text" path="grasas" id="grasas" size="30" maxlength="30" class="form-control"/> <br/>
                </div>
                <div class="mb-3">
                    <label for="azucares" class="form-label">Azucares(cada 100 gramos):</label>
                    <form:input type="text" path="azucares" id="azucares" size="30" maxlength="30" class="form-control"/> <br/>
                </div>
                <div class="mb-3">
                    <label for="hidratosDeCarbono" class="form-label">Hidratos de carbono(cada 100 gramos):</label>
                    <form:input type="text" path="hidratosDeCarbono" id="hidratosDeCarbono" size="30" maxlength="30" class="form-control"/> <br/>
                </div>
                <button type="submit" class="btn btn-primary">Guardar</button>
                <a href="/dietista/crearPlato" class="btn btn-secondary">Cancelar</a>
            </form:form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnrROZqPLFpuUWI4a2sA8pD5A4cJZHPUOks+EmW1E6Lxk3VFtDM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGktK0gYf94IYNd2tKpREIHMR5cQm75J5pbWuyj6cvF2DkSPEj3h4dHGsR9" crossorigin="anonymous"></script>
</body>
</html>

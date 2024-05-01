<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    UserRol trainer = (UserRol) request.getAttribute("trainer");
    String typeOfTrainer = trainer.getRolUsuario();

    User userTrainer = (User) request.getAttribute("user");
    String nameOfTrainer = userTrainer.getUsername();

%>


<html>
<head>
    <title>Trainer Menú</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>

<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item active">
                <a class="nav-link">Inicio</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Entrenamiento</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Dietas</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Desempeño</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Perfil</a>
            </li>
        </ul>
    </div>
</nav>


<h1>Bienvenido <%=nameOfTrainer%>!</h1>
<h2><%=typeOfTrainer%> Trainer</h2>

<section>

    <a href="/trainer/crear" class="btn btn-success">Crear Ejercicio</a>
    <a href="/trainer/clientes" class="btn btn-success">Clientes</a>

</section>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
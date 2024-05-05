<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    UserRol trainerRol = (UserRol) request.getAttribute("trainer");
    String categoriaEjercicio = trainerRol.getRolUsuario();
    TipoEjercicio tipoEjercicio = (TipoEjercicio) request.getAttribute("tipoEjercicio");
    //Declaro el tipo (-1 es para crear el ejercicio completamente nuevo y sin asociar a un cliente) (otro numero es para
    //es para modificar un ejercicio) (-2 es para crear un ejercicio asociandolo a un usuario)
    Integer tipo;
    String titulo = "Nuevo tipo";
    if(tipoEjercicio.getId() == -1){
        tipo = 0;
    }

%>

<html>
<head>
    <title><%=titulo%> </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>

<body>

<%--<nav class="navbar navbar-expand-lg navbar-light bg-light">
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
</nav>--%>


<h1><%=titulo%></h1>

<section>

    <form method="post">

        <label>Nombre</label>
        <input type="text">

    </form>

</section>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

%>

<html>
<head>
    <title>Dietista Ingrediente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item active text-weight-bold">
                <a class="nav-link" href="/dietista/">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Clientes</a>
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

<div class="row justify-content-center">
    <div class="col-sm-4 justify-content-center ">
        <form:form method="post" action="guardarNuevoIngrediente" modelAttribute="nuevoIngrediente">
            <div className="border">
                Nombre: <form:input type="text" path="nombre" size="50" maxlength="50"/> <br/>
            </div>
            <div className="border">
                Kilocalorias: <form:input type="text" path="kilocalorias" size="30" maxlength="30"/> <br/>
            </div>
            <div className="border">
                Proteinas: <form:input type="text" path="proteinas" size="30" maxlength="30"/> <br/>
            </div>
            <div className="border">
                Grasas: <form:input type="text" path="grasas" size="30" maxlength="30"/> <br/>
            </div>
            <div className="border">
                Azucares: <form:input type="text" path="azucares" size="30" maxlength="30"/> <br/>
            </div>
            <div className="border">
                Hidratos de carbono: <form:input type="text" path="hidratosDeCarbono" size="30" maxlength="30"/> <br/>
            </div>
            <button>Guardar</button> <a href="/dietista/crearPlato">Cancelar</a>
        </form:form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
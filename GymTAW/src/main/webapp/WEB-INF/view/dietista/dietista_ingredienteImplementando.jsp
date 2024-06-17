<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.uma.ui.PlatoDietistaUI" %>
<%@ page import="es.uma.entity.*" %>
<%@ page import="es.uma.ui.IngredienteImplementandoUI" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ingrediente> listaIngredientes = (List<Ingrediente>) request.getAttribute("listaIngredientes");
    List<TipoCantidad> listaTipoCantidad = (List<TipoCantidad>) request.getAttribute("listaTipoCantidad");
    IngredienteImplementandoUI ingredienteImplementandoUI = (IngredienteImplementandoUI) request.getAttribute("ingredienteImplementandoUI");
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

<div class="row justify-content-center">
    <div class="col-sm-8 justify-content-center ">
        <form:form method="post" action="/dietista/guardarIngredienteImplementando" modelAttribute="ingredienteImplementandoUI">
            <div class="row justify-content-center">
                <div class="col justify-content-center">
                    <%
                        if(ingredienteImplementandoUI.getIngrediente() == null)
                        {
                    %>
                    Ingrediente a añadir: <form:select path="ingrediente" items="${listaIngredientes}" itemValue="id" itemLabel="nombre"/>
                    <%
                    } else {
                    %>
                    <form:hidden path="ingrediente"/>
                    Ingrediente: <%=ingredienteImplementandoUI.getIngrediente().getNombre()%>
                    <%
                        }
                    %>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col justify-content-center">
                    Cantidad: <form:input path="cantidad" size="10"/>
                </div>
                <div class="col justify-content-center">
                    Tipo de cantidad: <form:select path="tipoCantidad" items="${listaTipoCantidad}" itemValue="id" itemLabel="tipoCantidadMedida"/>
                </div>
            </div>
            <form:button>Guardar</form:button>
        </form:form>
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <h5><a href="/dietista/volverComida">Volver</a></h5>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
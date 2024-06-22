<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.ui.IngredienteImplementandoUI" %>
<%@ page import="es.uma.dto.TipoCantidadDTO" %>
<%@ page import="es.uma.dto.IngredienteDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<IngredienteDTO> listaIngredientes = (List<IngredienteDTO>) request.getAttribute("listaIngredientes");
    List<TipoCantidadDTO> listaTipoCantidad = (List<TipoCantidadDTO>) request.getAttribute("listaTipoCantidad");
    IngredienteImplementandoUI ingredienteImplementandoUI = (IngredienteImplementandoUI) request.getAttribute("ingredienteImplementandoUI");
%>

<html>
<head>
    <title>Dietista ingrediente implementando</title>
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
        .form-label {
            font-weight: bold;
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

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-sm-8 form-container">
            <form:form method="post" action="/dietista/guardarIngredienteImplementando" modelAttribute="ingredienteImplementandoUI">
                <div class="row mb-3">
                    <div class="col text-center">
                        <label class="form-label">
                            <%
                                if (ingredienteImplementandoUI.getIngrediente() == null) {
                            %>
                            Ingrediente a añadir:
                            <form:select path="ingrediente" items="${listaIngredientes}" itemValue="id" itemLabel="nombre" class="form-control"/>
                            <%
                            } else {
                            %>
                            Ingrediente: <%=ingredienteImplementandoUI.getIngrediente().getNombre()%>
                            <form:hidden path="ingrediente"/>
                            <%
                                }
                            %>
                        </label>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col text-center">
                        <label class="form-label">Cantidad:</label>
                        <form:input path="cantidad" size="10" class="form-control"/>
                    </div>
                    <div class="col text-center">
                        <label class="form-label">Tipo de cantidad:</label>
                        <form:select path="tipoCantidad" items="${listaTipoCantidad}" itemValue="id" itemLabel="tipoCantidadMedida" class="form-control"/>
                    </div>
                </div>
                <div class="row mb-3">
                    <div class="col text-center">
                        <form:button class="btn btn-primary">Guardar</form:button>
                    </div>
                </div>
            </form:form>
            <div class="row">
                <div class="col text-center">
                    <h5><a href="/dietista/volverComida">Volver</a></h5>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnbD4La6h/ZGHrR9Fcy0EF+P85y1GQ9xSLJk6l9gWgg1vsJ8fnC" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cjC3uQ8FeI+NQFf01OM0e2dOj/5v5QVXyPhtkSfzAjQpF3GZbO14eO8ha5joWgu0" crossorigin="anonymous"></script>
</body>
</html>

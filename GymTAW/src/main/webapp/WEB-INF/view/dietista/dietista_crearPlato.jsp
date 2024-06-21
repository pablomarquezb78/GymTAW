<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.ui.PlatoDietistaUI" %>
<%@ page import="es.uma.dto.IngredienteDTO" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PlatoDietistaUI platoDietista = (PlatoDietistaUI) request.getAttribute("platoDietista");
    List<IngredienteDTO> ingredientesExistentes = (List<IngredienteDTO>) request.getAttribute("ingredientesExistentes");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dietista crear plato</title>
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
        .profile-link a {
            color: black;
            text-decoration: underline;
        }
        .border-box {
            border: 1px solid grey;
            padding: 15px;
            border-radius: 5px;
            background: white;
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
    <div class="row justify-content-center">
        <div class="col-sm-8">
            <div class="border-box mb-3">
                <h4>Ingredientes (completa este campo antes de continuar con el formulario):</h4>
                <ul>
                    <%
                        if (platoDietista.getIngredientes() != null) {
                            for (Ingrediente ingrediente : platoDietista.getIngredientes()) {
                    %>
                    <li>
                        <%= ingrediente.getNombre() %> <a href="/dietista/eliminarIngrediente?ingredienteId=<%= ingrediente.getId() %>">X</a>
                    </li>
                    <%
                            }
                        }
                    %>
                </ul>
                <form:form method="post" action="addIngredienteExistente" modelAttribute="platoDietista">
                    <form:hidden path="id"/>
                    <form:hidden path="nombre"/>
                    <form:hidden path="tiempoDePreparacion"/>
                    <form:hidden path="receta"/>
                    <form:hidden path="enlaceReceta"/>
                    <form:hidden path="ingredientes"/>
                    <div class="mb-3">
                        <label for="addedIngrediente" class="form-label">Agregar Ingrediente:</label>
                        <form:select path="addedIngrediente" items="${ingredientesExistentes}" itemValue="id" itemLabel="nombre" class="form-select"/>
                        <%--
                        <from:select id="addedIngrediente" name="addedIngrediente" class="form-select">
                            <%
                                for (IngredienteDTO ingrediente : ingredientesExistentes) {
                            %>
                            <option value="<%= ingrediente.getId() %>"><%= ingrediente.getNombre() %></option>
                            <%
                                }
                            %>
                        </from:select>
                        --%>
                    </div>
                    <button class="btn btn-primary">Añadir este ingrediente</button>
                </form:form>
                <a href="/dietista/addNuevoIngrediente" class="btn btn-link">Introducir nuevo ingrediente</a>
            </div>

            <form:form action="guardarPlato" method="post" modelAttribute="platoDietista">
                <div class="border-box mb-3">
                    <label for="nombre" class="form-label">Nombre del plato:</label>
                    <form:input path="nombre" id="nombre" maxlength="100" size="100" class="form-control"/>
                </div>
                <div class="border-box mb-3">
                    <label for="tiempoDePreparacion" class="form-label">Tiempo de preparación:</label>
                    <form:input path="tiempoDePreparacion" id="tiempoDePreparacion" maxlength="30" size="30" class="form-control"/>
                </div>
                <div class="border-box mb-3">
                    <label for="receta" class="form-label">Receta:</label>
                    <form:input path="receta" id="receta" maxlength="1500" size="100" class="form-control"/>
                </div>
                <div class="border-box mb-3">
                    <label for="enlaceReceta" class="form-label">Video de la receta (enlace de youtube):</label>
                    <form:input path="enlaceReceta" id="enlaceReceta" maxlength="200" size="150" class="form-control"/>
                </div>
                <form:hidden path="ingredientes"/>
                <form:hidden path="id"/>
                <button class="btn btn-success">Guardar</button>
                <a href="/dietista/" class="btn btn-secondary">Cancelar</a>
            </form:form>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnrROZqPLFpuUWI4a2sA8pD5A4cJZHPUOks+EmW1E6Lxk3VFtDM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGktK0gYf94IYNd2tKpREIHMR5cQm75J5pbWuyj6cvF2DkSPEj3h4dHGsR9" crossorigin="anonymous"></script>
</body>
</html>

<%@ page import="es.uma.ui.TipoEjercicioUI" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    TipoEjercicioUI tipoEjercicioUI = (TipoEjercicioUI) request.getAttribute("tipoEjercicio");
    String actionRol = "/entrenamientos/guardar-tipo-ejercicio";
    String cabecera = "cabecera_entrenador.jsp";

%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fábrica Tipo Ejercicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        body {
        }
        .container {
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            max-width: 600px;
            margin: auto;
            margin-top: 20px;
        }
        .form-title {
            margin-bottom: 20px;
        }
    </style>
</head>

<body>
<jsp:include page="<%=cabecera%>"></jsp:include>

<div class="container">
    <h3 class="form-title">
        <%= tipoEjercicioUI.getIdTipoEjercicio() == -1 ? "Crear tipo de ejercicio" : "Modificar tipo de ejercicio" %>
    </h3>
    <p>
        <%= tipoEjercicioUI.getIdTipoEjercicio() == -1 ? "Introduzca los datos necesarios para añadir un nuevo tipo de ejercicio" : "Modifica los datos del tipo de ejercicio como desee" %>
    </p>

    <form:form method="post" action="<%= actionRol %>" modelAttribute="tipoEjercicio">
        <form:hidden path="idTipoEjercicio" />

        <div class="mb-3">
            <label for="nombreTipoEjercicio" class="form-label">Nombre del Tipo de Ejercicio</label>
            <form:input path="nombreTipoEjercicio" id="nombreTipoEjercicio" class="form-control" />
        </div>

        <button type="submit" class="btn btn-primary">Guardar tipo de ejercicio</button>
    </form:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-..." crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-..." crossorigin="anonymous"></script>
</body>

</html>

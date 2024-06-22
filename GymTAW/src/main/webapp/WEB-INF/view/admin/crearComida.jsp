<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.ui.EjercicioUI" %>
<%@ page import="es.uma.ui.CantidadPlatoComida" %>
<%@ page import="es.uma.ui.AsignacionPlatoComida" %>
<%@ page import="es.uma.entity.*" %>
<%@ page import="es.uma.dto.UserDTO" %>
<%@ page import="es.uma.dto.TipoComidaDTO" %>
<%@ page import="es.uma.dto.IngredienteDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    AsignacionPlatoComida asignacionPlatoComida = (AsignacionPlatoComida) request.getAttribute("asignacionPlatoComida");
    List<UserDTO> clientes = (List<UserDTO>) request.getAttribute("clientes");
    List<UserDTO> dietistas = (List<UserDTO>) request.getAttribute("dietistas");
    List<TipoComidaDTO> tiposComida = (List<TipoComidaDTO>) request.getAttribute("tiposComida");
    List<IngredienteDTO> ingredientes = (List<IngredienteDTO>) request.getAttribute("ingredientes");
    request.setAttribute("paginaActual", "platos");

%>
<html>
<head>
    <title>Crear comida</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
            background: #f8f9fa;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        .form-header {
            font-size: 1.5em;
            margin-bottom: 20px;
        }
        .form-label {
            font-weight: bold;
        }
        .form-select,
        .form-input {
            margin-bottom: 15px;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="form-container">
                <div class="form-header">
                    <h3><%=asignacionPlatoComida.getIdComida() == null ? "Crear comida" : "Modificar comida" %></h3>
                    <p><%=asignacionPlatoComida.getIdComida() == null ? "Introduzca los datos necesarios para añadir una nueva comida" : "Modifica los datos de la comida como desee" %></p>
                </div>

                <form:form action="/admin/guardarComida" method="post" modelAttribute="asignacionPlatoComida">
                    <form:hidden path="idPlato"/>
                    <form:hidden path="idComida"/>
                    <div class="form-group form-input">
                        <label class="form-label">Fecha:</label>
                        <form:input path="fecha" type="date" size="10" class="form-control"/>
                    </div>
                    <div class="form-group form-select">
                        <label class="form-label">Cliente:</label>
                        <form:select path="idCliente" items="${clientes}" itemValue="id" itemLabel="nombre" class="form-control"/>
                    </div>
                    <div class="form-group form-select">
                        <label class="form-label">Entrenador:</label>
                        <form:select path="idDietista" items="${dietistas}" itemValue="id" itemLabel="nombre" class="form-control"/>
                    </div>
                    <div class="form-group form-select">
                        <label class="form-label">Tipo:</label>
                        <form:select path="tipoComida" items="${tiposComida}" itemLabel="comidaDelDia" itemValue="id" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Cantidad (g):</label>
                        <form:input path="cantidad" size="5" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <form:button class="btn btn-success mt-3">Guardar comida</form:button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
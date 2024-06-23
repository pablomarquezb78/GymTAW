<!-- @Author: Pablo Miguel Aguilar Blanco -->

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.ui.PlatoUI" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PlatoUI platoUI = (PlatoUI) request.getAttribute("platoUI");
    request.setAttribute("paginaActual", "platos");
%>
<html>
<head>
    <title>Admin</title>
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
        .form-input {
            margin-bottom: 15px;
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
                    <h3><%=platoUI.getId() == null ? "Crear plato" : "Modificar plato" %></h3>
                    <p><%=platoUI.getId() == null ? "Introduzca los datos necesarios para añadir un nuevo plato" : "Modifica los datos del plato como desee" %></p>
                </div>

                <form:form action="/admin/anyadirPlato" method="post" modelAttribute="platoUI">
                    <form:hidden path="id"/>
                    <div class="form-group form-input">
                        <label class="form-label">Nombre:</label>
                        <form:input path="nombre" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Tiempo de preparación:</label>
                        <form:input path="tiempoDePreparacion" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Receta:</label>
                        <form:input path="receta" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Enlace:</label>
                        <form:input path="enlaceReceta" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <form:button class="btn btn-success mt-3">Guardar plato</form:button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

</div>
</body>
</html>
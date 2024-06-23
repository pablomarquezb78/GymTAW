<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Rutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.dto.RutinaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<RutinaDTO> rutinas = (List<RutinaDTO>) request.getAttribute("rutinas");
    Boolean fallo = (Boolean) request.getAttribute("fallo");
%>

<html>
<head>
    <title>Asociar Rutina</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .container-custom {
            margin-top: 50px;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .header-title {
            margin-bottom: 30px;
            text-align: center; /* Alineación centrada del título */
        }
        .form-group {
            margin-bottom: 15px;
        }
        .radio-label {
            margin-left: 10px; /* Espacio entre el radio button y el label */
        }
        .btn-custom {
            margin-top: 20px;
        }
        .error-rutina{
            text-align: center;
        }
    </style>
</head>
<body>
<jsp:include page="cabecera_entrenador.jsp"/>


<div class="container container-custom">
    <h1 class="header-title">Listado de Rutinas ya creadas</h1>

    <%
        if(fallo != null) {
            if(fallo){
    %>
        <h3 class="error-rutina">No puedes ponerle a un cliente más de una rutina al día! Vuelve a intentarlo con otra fecha.</h3>
    <%
            }
        }
    %>

    <% if(rutinas != null && !rutinas.isEmpty()) { %>

    <form:form action="/entrenamientos/asociar-rutina" method="post" modelAttribute="asociacionRutina">
        <div class="form-group">
            <form:hidden path="idCliente"/>
            <form:hidden path="idTrainer"/>
            <label for="fecha">Fecha:</label>
            <form:input id="fecha" type="date" path="fecha" value="2024-01-01" class="form-control"/>
        </div>
        <div class="form-group">
            <label>Seleccione una Rutina:</label><br>
            <form:radiobuttons path="idRutina" items="${rutinas}" itemValue="id" itemLabel="nombre" delimiter="<br>" cssClass="radio-label"/>
        </div>
        <button type="submit" class="btn btn-primary btn-custom">Asociar</button>
    </form:form>

    <% } else { %>
    <h2 class="text-center">¡Vaya! No hay ninguna rutina disponible. Te recomendamos que crees una :)</h2>
    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

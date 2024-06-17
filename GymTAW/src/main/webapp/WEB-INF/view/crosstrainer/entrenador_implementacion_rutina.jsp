<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.ui.Implementacion" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.Rutina" %>
<%
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
    Boolean editable = (Boolean) request.getAttribute("editable");

    Implementacion implementacion = (Implementacion) request.getAttribute("implementacion");

    String sets = "", repeticiones = "", peso = "", tiempo = "", cal = "", metros = "";

    UserRol userRol = (UserRol) session.getAttribute("rol");
    String actionRol = "/entrenamientos/guardarimplementacionrutina";
    Boolean disabled = false;

    if (editable != null && editable) {
        sets = implementacion.getSets();
        repeticiones = implementacion.getRepeticiones();
        peso = implementacion.getPeso();
        tiempo = implementacion.getTiempo();
        cal = implementacion.getKilocalorias();
        metros = implementacion.getMetros();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Formulario de Implementación</title>
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
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="form-container">
                <div class="form-header">
                    <h3><%= implementacion.getId() == null ? "Crear implementacion" : "Modificar implementacion" %></h3>
                    <p><%= implementacion.getId() == null ? "Introduzca los datos necesarios para anadir una nueva implementacion" : "Modifica los datos de la implementacion como desee" %></p>
                </div>

                <form:form action="<%= actionRol %>" method="post" modelAttribute="implementacion">
                    <div class="form-group form-input">
                        <label class="form-label">Set:</label>
                        <form:input path="sets" value="${sets}" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Repeticiones:</label>
                        <form:input path="repeticiones" value="${repeticiones}" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Peso:</label>
                        <form:input path="peso" value="${peso}" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Tiempo:</label>
                        <form:input path="tiempo" value="${tiempo}" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Kcal:</label>
                        <form:input path="kilocalorias" value="${cal}" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Metros:</label>
                        <form:input path="metros" value="${metros}" class="form-control"/>
                    </div>
                    <div class="form-group form-select">
                        <label class="form-label">Ejercicio:</label>
                        <form:select path="ejercicio" size="3" items="${ejercicios}" itemValue="id" itemLabel="nombre" class="form-control"></form:select>
                    </div>
                    <form:hidden path="id"/>
                    <form:hidden path="rutina"/>
                    <div class="form-group">
                        <form:button class="btn btn-primary mt-3">GUARDAR</form:button>
                    </div>
                </form:form>
                <!-- Formulario de filtrado por nombre -->
                <form:form method="post" modelAttribute="implementacion" action="/entrenamientos/filtrarejerciciopornombre" class="mt-4">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre:</span>
                        <form:input path="nombrefiltrado" class="form-control" aria-label="Nombre del ejercicio" aria-describedby="basic-addon1"/>
                        <button type="submit" class="btn btn-primary">Filtrar</button>
                    </div>
                    <form:hidden path="id"/>
                    <form:hidden path="rutina"/>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlYlRd5zybT9Sc2n3MGLzJQd4qfjk1eD5wU8jg9L8/sd8fiQ68xXXby6iZp" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVkiBFEiyI2ErQpkCjfnmjLnx1pA6IbsEcjFZhJvPuhhxZZj3swl7l9Tw1RgyVGN" crossorigin="anonymous"></script>
</body>
</html>

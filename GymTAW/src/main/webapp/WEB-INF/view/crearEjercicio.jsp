<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="es.uma.ui.EjercicioUI" %>
<%@ page import="es.uma.dto.TipoEjercicioDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<TipoEjercicioDTO> tipos = (List<TipoEjercicioDTO>) request.getAttribute("tipos");
    EjercicioUI ejercicioUI = (EjercicioUI) request.getAttribute("ejercicioUI");
    UserRolDTO userRol = (UserRolDTO) session.getAttribute("rol");
    Boolean disabled = true;
    String cabecera = "./crosstrainer/cabecera_entrenador.jsp";
    String clase = "Crossfit";
    if(userRol.getId() == 1) {
        disabled = false;
        cabecera = "./admin/cabeceraAdmin.jsp";
        clase = "";
    }else if(userRol.getId() == 3) {
        clase = "bodybuilding";
    }

%>
<html>
<head>
    <title>Crear ejercicio</title>
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
<jsp:include page="<%=cabecera%>"></jsp:include>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="form-container">
                <div class="form-header">
                    <h3><%=ejercicioUI.getId() == null ? "Crear ejercicio" : "Modificar ejercicio" %></h3>
                    <p><%=ejercicioUI.getId() == null ? "Introduzca los datos necesarios para añadir un nuevo ejercicio" : "Modifica los datos del ejercicio como desee" %></p>
                </div>

                <form:form action="/comun/guardarEjercicio" method="post" modelAttribute="ejercicioUI">
                    <form:hidden path="id"/>
                    <div class="form-group form-input">
                        <label class="form-label">Nombre:</label>
                        <form:input path="nombre" size="15" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Clase:</label>
                        <form:input path="trainerEjercicio" size="10" disabled="${disabled}" value="${clase}" class="form-control"/>
                    </div>
                    <div class="form-group form-select">
                        <label class="form-label">Tipo:</label> </br>
                        <form:radiobuttons path="idTipo" items="${tipos}" delimiter="<br>" itemLabel="tipoDeEjercicio" itemValue="id"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Enlace:</label>
                        <form:input path="enlaceVideo" size="50" class="form-control"/>
                    </div>
                    <div class="form-group form-input">
                        <label class="form-label">Descripcion:</label>
                        <form:input path="descripcion" size="50" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <form:button class="btn btn-success mt-3">Guardar ejercicio</form:button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
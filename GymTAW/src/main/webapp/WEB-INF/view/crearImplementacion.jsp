<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.ui.Implementacion" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.Rutina" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="es.uma.dto.EjercicioDTO" %>
<%@ page import="es.uma.dto.RutinaDTO" %>
<%@ page import="es.uma.dto.TipoEjercicioDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%
    List<EjercicioDTO> ejercicios = (List<EjercicioDTO>) request.getAttribute("ejercicios");
    List<RutinaDTO> rutinas = (List<RutinaDTO>) request.getAttribute("rutinas");
    Boolean editable = true;
    List<TipoEjercicioDTO> tipos = (List<TipoEjercicioDTO>) request.getAttribute("tipos");

    Implementacion implementacion = (Implementacion) request.getAttribute("implementacion");

    Integer entrenamiento = (Integer) request.getAttribute("entrenamiento");


    String sets = "", repeticiones = "", peso = "", tiempo = "", cal = "", metros = "";

    String cabecera = "./crosstrainer/cabecera_entrenador.jsp";

    UserRolDTO userRol = (UserRolDTO) session.getAttribute("rol");


    String actionRol = "/entrenamientos/guardarimplementacion"; /// Esto<<<<<
    cabecera = "./crosstrainer/cabecera_entrenador.jsp";
    String filtrar = "/comun/filtrartipo";

    Boolean isAdmin = userRol.getId() == 1;

    Boolean disabled = true;
    if(isAdmin) {
        actionRol = "/comun/guardarImplementacion";
        cabecera = "./admin/cabeceraAdmin.jsp";
        disabled = true;
    }else if(implementacion.getAuxValue() == 0){
        actionRol = "/comun/guardarImplementacion";
    }else{
        actionRol = "/comun/guardarImplementacionTrainer";
    }

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

<jsp:include page="<%=cabecera%>"></jsp:include>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="form-container">
                <div class="form-header">
                    <h3><%= implementacion.getId() == null ? "Crear implementacion" : "Modificar implementacion" %></h3>
                    <p><%= implementacion.getId() == null ? "Introduzca los datos necesarios para agregar una nueva implementacion" : "Modifica los datos de la implementacion como desee" %></p>
                </div>

                <form:form action="<%= actionRol %>" method="post" modelAttribute="implementacion">
                <div class="row">
                    <div class="col-md-6">
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
                        <%
                            if(isAdmin){
                        %>
                            <div class="form-group form-select">
                                <label class="form-label">Rutina:</label>
                                <form:select path="rutina" items="${rutinas}" itemValue="id" itemLabel="nombre" class="form-control"></form:select>
                            </div>
                        <%
                            }
                        %>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group form-select">
                            <label class="form-label">Ejercicio:</label>
                            <select name="idejercicioseleccionado"  size="10" class="form-control">
                                <%
                                    for(EjercicioDTO ej: ejercicios){
                                        String seleccionado = "";
                                        if(ej.getId()==implementacion.getEjercicio().getId()) seleccionado = "selected";
                                %>
                                    <option <%=seleccionado%> value="<%=ej.getId()%>"><%=ej.getNombre()%></option>
                                <%
                                    }
                                   // <form:select size="10" path="ejercicio" items="${ejercicios}" itemValue="id" itemLabel="nombre" class="form-control"></form:select>
                                %>

                            </select>
                                </div>
                                <form:hidden path="rutina"></form:hidden>
                                <form:hidden path="idDia"/>
                                <form:hidden path="id"/>
                                <div class="form-group">
                            <form:button class="btn btn-primary mt-3">GUARDAR</form:button>
                            </form:form>

                            <div class="mt-4">
                                <h4>Filtrar ejercicios:</h4>
                                <form:form action="<%=filtrar%>" method="post" modelAttribute="implementacion">
                                    <form:radiobuttons path="idfiltrado" items="${tipos}" delimiter="<br>" itemLabel="tipoDeEjercicio" itemValue="id"/>
                                    <form:hidden path="iddia"/>
                                    <form:hidden path="id"/>
                                    <form:hidden path="auxValue"></form:hidden>
                                    <br>
                                    <button class="btn btn-secondary mt-3">Filtrar</button>
                                </form:form>
                            </div>

                        </div>
                    </div>
                </div>


            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlYlRd5zybT9Sc2n3MGLzJQd4qfjk1eD5wU8jg9L8/sd8fiQ68xXXby6iZp" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVkiBFEiyI2ErQpkCjfnmjLnx1pA6IbsEcjFZhJvPuhhxZZj3swl7l9Tw1RgyVGN" crossorigin="anonymous"></script>
</body>
</html>

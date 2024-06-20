<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="button" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Rutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Rutina> rutinas = (List<Rutina>) request.getAttribute("rutinas");

%>

<html>

<head>

    <title>Asociar Rutina</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<jsp:include page="cabecera_entrenador.jsp"/>

    <h1>Listado de Rutinas ya creadas:</h1>

    <%
        if(rutinas.size()>0){
    %>

    <form:form action="/entrenamientos/asociar-rutina" method="post" modelAttribute="asociacionRutina">

        <form:hidden path="idCliente"></form:hidden>
        <form:hidden path="idTrainer"></form:hidden>
        <form:input type="date" path="fecha"></form:input>
        <br>
        <form:radiobuttons path="idRutina" items="${rutinas}" delimiter="<br>" itemValue="id" itemLabel="nombre"></form:radiobuttons>
        <br>
        <form:button class="btn btn-primary">Asociar</form:button>
    </form:form>

    <%
        }
        else{
    %>
    <h2>Vaya! No hay ninguna rutina dispoible, te recomendamos que crees una :)</h2>
    <%
        }
    %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>

</html>
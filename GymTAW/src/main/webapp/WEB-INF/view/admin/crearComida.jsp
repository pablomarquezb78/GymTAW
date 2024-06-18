<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="es.uma.ui.EjercicioUI" %>
<%@ page import="es.uma.ui.CantidadPlatoComida" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.entity.TipoComida" %>
<%@ page import="es.uma.ui.AsignacionPlatoComida" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    AsignacionPlatoComida asignacionPlatoComida = (AsignacionPlatoComida) request.getAttribute("asignacionPlatoComida");
    List<User> clientes = (List<User>) request.getAttribute("clientes");
    List<User> dietistas = (List<User>) request.getAttribute("dietistas");
    List<TipoComida> tiposComida = (List<TipoComida>) request.getAttribute("tipos");
%>
<html>
<head>
    <title>Crear comida</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
<div>
    <h3>
        <%=asignacionPlatoComida.getIdComida() == null ? "Crear comida" : "Editar comida"%>
    </h3>
    <p>
        <%=asignacionPlatoComida.getIdComida() == null ? "Introduzca los datos necesarios para añadir una nueva comida" : "Edita los datos de la comida como desee"%>
    </p>
    <form:form action="/admin/guardarComida" method="post" modelAttribute="asignacionPlatoComida">
        <form:hidden path="idPlato"></form:hidden>
        <form:hidden path="idComida"></form:hidden>
        <label>Fecha (yyyy-MM-dd): </label>
        <form:input path="fecha" size="10"></form:input>
        <br>
        <label>Cliente: </label>
        <form:select path="idCliente" items="${clientes}" itemValue="id" itemLabel="nombre"></form:select>
        <br>
        <label>Entrenador:</label>
        <form:select path="idDietista" items="${dietistas}" itemValue="id" itemLabel="nombre"></form:select>
        <br>
        <label>Tipo:</label>
        <form:select path="tipoComida" items="${tiposComida}" itemLabel="comidaDelDia" itemValue="id"></form:select>
        <br>
        <label>Cantidad:</label>
        <form:input path="cantidad" size="5"></form:input>
        <br>
        <label>Ingredientes:</label>
        <br>
        <form:button>Guardar comida</form:button>
    </form:form>
</div>
</body>
</html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.ui.PlatoUI" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    PlatoUI platoUI = (PlatoUI) request.getAttribute("platoUI");

%>
<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
<div>
    <h3>
        <%=platoUI.getId() == null ? "Crear plato" : "Modificar plato"%>
    </h3>
    <p>
        <%=platoUI.getId() == null ? "Introduzca los datos necesarios para añadir un nuevo plato" : "Modifica los datos del plato como desee"%>
    </p>
    <form:form action="/admin/anyadirPlato" method="post" modelAttribute="platoUI">
        <form:hidden path="id"></form:hidden>
        <label>Nombre: </label>
        <form:input path="nombre"></form:input>
        <br>
        <label>Tiempo de preparacion:</label>
        <form:input path="tiempoDePreparacion"></form:input>
        <br>
        <label>Receta:</label>
        <form:input path="receta"></form:input>
        <br>
        <label>Enlace:</label>
        <form:input path="enlaceReceta"></form:input>
        <br>
        <form:button>Guardar plato</form:button>
    </form:form>
</div>
</body>
</html>
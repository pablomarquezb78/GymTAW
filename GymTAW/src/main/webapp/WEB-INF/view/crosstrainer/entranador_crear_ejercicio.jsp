<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%

    UserRol userRol = (UserRol) request.getAttribute("rol");
    Integer tipoRol = userRol.getId();
    String trainerEjercicio = userRol.getRolUsuario();
    List<TipoEjercicio> tipoEjercicioList = (List<TipoEjercicio>) request.getAttribute("tiposEjercicios");
%>

<html>

<head>
        <title>Creador Ejercicio</title>
</head>
<body>

    <form:form method="post" action="" modelAtribute="ejercicio">
        
        <label>Nombre</label>
        <form:input path="nombreEjercicio" type="text"></form:input>

        <label>Clase</label>
        <form:input path="trainerEjercicio" type="text" disabled="true" value="<%=trainerEjercicio%>"></form:input>

        <label>Tipo</label>
        <form:select path="tipoEjercicio" items="${tipoEjercicioList}" itemLabel="tipoDeEjercicio" itemValue="id"></form:select>

        <label>Descripción</label>
        <form:textarea path="descripcionEjercicio"></form:textarea>

        <label>Enlace video</label>
        <form:input path="enlaceVideoEjercicio" type="text"></form:input>


        <form:button>CREAR</form:button>

    </form:form>


</body>
</html>
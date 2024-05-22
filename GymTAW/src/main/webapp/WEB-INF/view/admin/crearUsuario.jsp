<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.ui.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserRol> roles = (List<UserRol>) request.getAttribute("roles");
    Usuario usuario = (Usuario) request.getAttribute("usuario");
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
        <%=usuario.getId() == null ? "Crear usuario" : "Modificar usuario"%>
    </h3>
    <p><%=usuario.getId() == null ? "Introduzca los datos necesarios para añadir un nuevo usuario" : "Modifica los datos del usuario como desee"%></p>
    <form:form action="/admin/guardarUsuario" method="post" modelAttribute="usuario">
        <form:hidden path="id"></form:hidden>
        <label>*Usuario:</label>
        <form:input path="username" size="15"></form:input>
        <br>
        <label>*Contraseña:</label>
        <form:input path="password" size="25"></form:input>
        <br>
        <label>*Nombre:</label>
        <form:input path="nombre" size="15"></form:input>
        <br>
        <label>*Apellidos: </label>
        <form:input path="apellidos" size="50"></form:input>
        <br>
        <label>*Fecha de nacimiento:</label>
        <br>
        <label>*Rol:</label>
        <form:radiobuttons path="rol" items="${roles}" delimiter="<br>" itemLabel="rolUsuario" itemValue="id"></form:radiobuttons>
        <br>
        <label>Peso:</label>
        <form:input path="peso" size="3"></form:input>
        <br>
        <label>Altura:</label>
        <form:input path="altura" size="3"></form:input>
        <br>
        <label>Telefono:</label>
        <form:input path="telefono" size="9"></form:input>
        <br>
        <form:button class="btn btn-success mt-3">Guardar usuario</form:button>
    </form:form>
</div>
</body>
</html>

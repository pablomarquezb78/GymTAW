<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.ui.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserRol> roles = (List<UserRol>) request.getAttribute("roles");
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    String action = "/admin/anyadirEjercicio";
    if(usuario.getId() != null){
        action = "/admin/modificarUsuario";
    }
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
    <form:form action="<%=action%>" method="post" modelAttribute="usuario">
        <form:hidden path="id"></form:hidden>
        Usuario*: <form:input path="username"></form:input>
        Contraseña*: <form:input path="password"></form:input>
        Nombre*: <form:input path="nombre"></form:input>
        Apellidos*: <form:input path="apellidos"></form:input>
        Fecha de nacimiento*:
        Rol*: <form:radiobuttons path="rol" items="${roles}" delimiter="<br>" itemLabel="rolUsuario" itemValue="id"></form:radiobuttons>
        Peso: <form:input path="peso"></form:input>
        Altura: <form:input path="altura"></form:input>
        Telefono: <form:input path="telefono"></form:input>
        <form:button>Guardar usuario</form:button>
    </form:form>
</div>
</body>
</html>

<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.entity.UserRol" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User user = (User) session.getAttribute("user");
    UserRol rol = (UserRol) session.getAttribute("rol");
%>

<html>
<head>
    <title>Login</title>
</head>
<body>

<p>
Se ha logeado el usuario: <%= user.getUsername() %>, que tiene el rol de: <%= rol.getRolUsuario() %>.
</p>

</body>
</html>
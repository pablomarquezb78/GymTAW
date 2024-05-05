<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String error = (String) request.getAttribute("error");
%>

<html>
<head>
    <title>Login</title>
</head>
<body>
<form method="post" action="/autentica">
    Usuario: <input type="text" name="usuario_login"> <br>
    Contraseña: <input type="password" name="password_login"> <br>
    <button>Acceder</button>
</form>
<a href="/registrar">Registrarse</a>

<%
    if(error != null)
    {
%>
    <h5>ERROR: <%= error %></h5>
<%
    }
%>

</body>
</html>
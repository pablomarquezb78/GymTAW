<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String error = (String) request.getAttribute("error");
%>

<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<h1 class="text-center">Login</h1>
<div class="d-flex flex-column justify-content-center align-items-center vh-100">
<form method="post" action="/autentica">
    Usuario: <input type="text" name="usuario_login"> <br>
    Contraseña: <input type="password" name="password_login"> <br>
    <button>Acceder</button>
</form>
<a href="/registrar">Registrarse</a>
</div>

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
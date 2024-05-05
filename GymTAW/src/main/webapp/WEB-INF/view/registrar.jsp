<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Register</title>
</head>
<body>
<h2>Introduzca los datos para crear un nuevo usuario</h2>

<form method="post" action="/peticion">
    Usuario: <input type="text" name="usuario"> <br>
    Contraseña: <input type="password" name="password"> <br>
    Nombre: <input type="text" name="nombre"> <br>
    Apellidos: <input type="text" name="apellidos"> <br>
    Fecha de nacimiento: <input type="date" name="fecha_nacimiento"> <br>
    Teléfono: <input type="number" name="telefono"> <br>
    Rol: <input type="radio" id="1" name="rol" value="2">Cliente <br>
    <input type="radio" id="2" name="rol" value="3">Bodybuilder <br>
    <input type="radio" id="3" name="rol" value="4">Crosstrainer <br>
    <input type="radio" id="4" name="rol" value="5">Dietista <br>
    <button>Enviar</button>
</form>

</body>
</html>
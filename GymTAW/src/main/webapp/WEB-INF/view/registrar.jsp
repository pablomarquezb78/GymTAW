<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Registrar</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<h1>Registrar</h1>
<h2>Introduzca los datos para crear un nuevo usuario</h2>
 <form method="post" action="/peticion" class="m-2">
     *Usuario: <input type="text" name="usuario"> <br>
     *Contraseña: <input type="password" name="password"> <br>
     *Nombre: <input type="text" name="nombre"> <br>
     *Apellidos: <input type="text" name="apellidos"> <br>
     *Fecha de nacimiento: <input type="date" name="fecha_nacimiento"> <br>
     Teléfono: <input type="number" name="telefono"> <br>
     *Rol:
        <br>
        <input type="radio" id="1" name="rol" value="2">Cliente <br>
        <input type="radio" id="2" name="rol" value="3">Bodybuilder <br>
        <input type="radio" id="3" name="rol" value="4">Crosstrainer <br>
        <input type="radio" id="4" name="rol" value="5">Dietista <br>
        <button>Enviar</button>
    </form>
</body>
</html>
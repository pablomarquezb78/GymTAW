<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer id = (Integer) request.getAttribute("idcliente");
%>

<html>

<head>

    <title>Menú Crear Entrenamiento</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<jsp:include page="cabecera_entrenador.jsp"/>

<h1>CREADOR EJERCICIO</h1>

    <form method="post" action="/entrenamientos/entrenador-rutina">

        <input type="date" value="2000-01-01" name="fecha">
        <input hidden="hidden" name="id" value="<%=id%>">
        <button type="submit" name="accion" value="crear">Crear Rutina</button>
        <button type="submit" name="accion" value="asociar">Asociar Rutina</button>

    </form>

<%--
    <a href="/entrenamientos/creador-rutina" class="btn btn-success">Crear Rutina</a>
    <a href="/entrenamientos/asociar-rutina" class="btn btn-primary">Asociar rutina</a>
--%>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>

</html>


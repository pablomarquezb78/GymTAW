<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer id = (Integer) request.getAttribute("idcliente");
%>

<html>
<head>
    <title>Menú Crear Entrenamiento</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .btn-custom {
            margin: 10px;
        }
        .container-custom {
            margin-top: 50px;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<jsp:include page="cabecera_entrenador.jsp"/>

<div class="container container-custom text-center">
    <h1 class="mb-4">Creador de Ejercicio</h1>
    <div class="d-flex justify-content-center">
        <a class="btn btn-success btn-custom" href="/entrenamientos/entrenador-crearrutina">Crear Rutina</a>
        <a class="btn btn-primary btn-custom" href="/entrenamientos/entrenador-rutina?id=<%=id%>">Asociar Rutina</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

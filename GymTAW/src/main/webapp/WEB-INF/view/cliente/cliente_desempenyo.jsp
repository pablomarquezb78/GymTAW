<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- @author: Pablo Márquez Benítez --%>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Desempeño</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<div class="d-flex flex-column align-items-center my-3 position-absolute top-50 start-50 translate-middle">
    <h1>Elige que desempeño observar:</h1>
    <div class="d-flex gap-3 my-3">
        <a class="btn btn-primary" href="verDesempenyoDietas">Desempeño Dietas</a>
        <a class="btn btn-primary" href="verDesempenyoEntrenamientos">Desempeño Entrenamientos</a>
    </div>
</div>

</body>
</html>

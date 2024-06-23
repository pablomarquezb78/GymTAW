<%@ page import="es.uma.dto.DiaDietaDTO" %>
<%@ page import="es.uma.dto.DiaEntrenamientoDTO" %>
<%@ page import="java.time.LocalDate" %>
<%-- @author: Pablo Márquez Benítez --%>
<%
    //OBTENEMOS PARAMETROS
    DiaEntrenamientoDTO diaEntrenamiento = (DiaEntrenamientoDTO) request.getAttribute("diaEntrenamiento");
    DiaDietaDTO diaDieta = (DiaDietaDTO) request.getAttribute("diaDieta");
    String nombreUsuario = (String) request.getAttribute("nombreUsuario");
    LocalDate fechaInicio = (LocalDate) request.getAttribute("fechaInicio");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>


<h1 class="text-center position-absolute top-50 start-50 translate-middle" style="padding-bottom: 2%">¡Bienvenido <%=nombreUsuario%>!</h1>
<h2 class="position-absolute top-50 start-50 translate-middle mt-3">Hoy es: <%=fechaInicio%></h2>

<div class="d-flex justify-content-center gap-3 position-absolute top-50 start-50 translate-middle" style="margin-top: 3%">
    <div>
        <%if(diaEntrenamiento!=null){%>
        <button onclick="location.href='/cliente/mostrarEntrenamientos'" class="mt-5 btn btn-primary" style="margin-top: 17%">Ver entrenamiento de hoy</button>
        <%}else{%>
        <h4 style="margin-top: 13%">No hay entrenamiento hoy</h4>
        <%}%>
    </div>
    <div>
        <%if(diaDieta!=null){%>
        <button onclick="location.href='/cliente/mostrarDietas'" class="mt-5 btn btn-primary my-3" style="margin-top: 17%">Ver dieta de hoy</button>
        <%}else{%>
        <h4 style="margin-top: 20%">No hay Dieta hoy</h4>
        <%}%>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer clientes = (Integer) request.getAttribute("clientes");
    Integer entrenadores = (Integer) request.getAttribute("entrenadores");
    Integer dietistas = (Integer) request.getAttribute("dietistas");
    Integer ejercicios = (Integer) request.getAttribute("ejercicios");
    Integer platos = (Integer) request.getAttribute("platos");
    request.setAttribute("paginaActual", "inicio");

%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
<div class="container mt-5">
    <div class="row">
        <div class="col-12">
            <h1 class="text-center mb-4">Bienvenido admin</h1>
            <section class="bg-light p-4 rounded shadow-sm">
                <ul class="list-group">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Clientes registrados:
                        <span class="badge bg-info rounded-pill text-dark"><%=clientes%></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Número de entrenadores totales registrados:
                        <span class="badge bg-info rounded-pill text-dark"><%=entrenadores%></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Número de dietistas registrados:
                        <span class="badge bg-info rounded-pill text-dark"><%=dietistas%></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Ejercicios totales creados:
                        <span class="badge bg-info rounded-pill text-dark"><%=ejercicios%></span>
                    </li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        Platos creados:
                        <span class="badge bg-info rounded-pill text-dark"><%=platos%></span>
                    </li>
                </ul>
            </section>
        </div>
    </div>
</div>
</body>
</html>

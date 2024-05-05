<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer clientes = (Integer) request.getAttribute("clientes");
    Integer entrenadores = (Integer) request.getAttribute("entrenadores");
    Integer dietistas = (Integer) request.getAttribute("clientes");
    Integer ejercicios = (Integer) request.getAttribute("ejercicios");
    Integer platos = (Integer) request.getAttribute("platos");
%>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav nav-fill w-100">
                <li class="nav-item active">
                    <a class="nav-link" href="/admin/">Inicio</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="/admin/autenticarUsuarios">Autenticar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/mostrarUsuarios">Usuarios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/asignarCliente">Asignar</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/mostrarEjercicios">Ejercicios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/mostrarPlatos">Platos</a>
                </li>
            </ul>
        </div>
    </nav>
    <h1>
        Bienvenido admin
    </h1>
    <section>
        <ul>
            <li>
                Clientes registrados: <%=clientes%>
            </li>
            <li>
                Número de entrenadores totales registrados: <%=entrenadores%>
            </li>
            <li>
                Número de dietistas registrados: <%=entrenadores%>
            </li>
            <li>
                Ejercicios totales creados: <%=ejercicios%>
            </li>
            <li>
                Platos creados: <%=platos%>
            </li>
        </ul>
    </section>
</body>
</html>
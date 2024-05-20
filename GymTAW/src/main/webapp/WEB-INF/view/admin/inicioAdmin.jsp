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
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
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
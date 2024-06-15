<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.uma.ui.PlatoDietistaUI" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="java.time.Year" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<User> clientes = (List<User>) request.getAttribute("clientes");
%>

<html>
<head>
    <title>Dietista clientes asociados</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item">
                <a class="nav-link" href="/dietista/">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/dietista/mostrarClientes">Clientes</a>
            </li>
            <li class="nav-item active text-weight-bold">
                <a class="nav-link" href="/dietista/mostrarPerfil">Perfil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesión</a>
            </li>
        </ul>
    </div>
</nav>

<div class="row justify-content-center">
    <div class="col-sm-8 justify-content-center ">
        <h1>Clientes:</h1>
        <table>
            <tr>
                <th>NOMBRE</th>
                <th>EDAD</th>
                <th>PESO</th>
                <th>ALTURA</th>
                <th></th>
            </tr>
            <%
                for(User cliente : clientes)
                {
                    int anyoActual = Year.now().getValue();
            %>
                <tr>
                    <td><%=cliente.getNombre()%></td>
                    <td><%=anyoActual - cliente.getFechaNacimiento().getYear()%></td>
                    <td><%=cliente.getPeso()%></td>
                    <td><%=cliente.getAltura()%></td>
                    <td><a href="/dietista/cliente?id=<%=cliente.getId()%>">Acceder al cliente</a></td>
                </tr>
            <%
                }
            %>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
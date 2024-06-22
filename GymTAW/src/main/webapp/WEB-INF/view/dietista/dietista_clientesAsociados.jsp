<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="java.time.Year" %>
<%@ page import="es.uma.dto.UserDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserDTO> clientes = (List<UserDTO>) request.getAttribute("clientes");
%>

<%-- @author: Jaime Ezequiel Rodriguez Rodriguez --%>

<html>
<head>
    <title>Dietista clientes asociados</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .table-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            border: 1px solid grey;
            margin-top: 20px;
        }
        .navbar-nav .nav-item .nav-link.active {
            font-weight: bold;
            color: black;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table th, table td {
            padding: 10px;
            text-align: left;
        }
        table th {
            background-color: #f8f9fa;
        }
        table tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
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
            <li class="nav-item active">
                <a class="nav-link" href="/dietista/mostrarPerfil">Perfil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesión</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-sm-10 table-container">
            <h1>Clientes</h1>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>NOMBRE</th>
                    <th>EDAD</th>
                    <th>PESO</th>
                    <th>ALTURA</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    int anyoActual = Year.now().getValue();
                    for(UserDTO cliente : clientes) {
                %>
                <tr>
                    <td><%=cliente.getNombre()%> <%=cliente.getApellidos()%></td>
                    <td><%=anyoActual - cliente.getFechaNacimiento().getYear()%></td>
                    <td><%=cliente.getPeso()%></td>
                    <td><%=cliente.getAltura()%></td>
                    <td><a href="/dietista/cliente?id=<%=cliente.getId()%>" class="btn btn-primary btn-sm">Acceder al cliente</a></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnrROZqPLFpuUWI4a2sA8pD5A4cJZHPUOks+EmW1E6Lxk3VFtDM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGktK0gYf94IYNd2tKpREIHMR5cQm75J5pbWuyj6cvF2DkSPEj3h4dHGsR9" crossorigin="anonymous"></script>
</body>
</html>

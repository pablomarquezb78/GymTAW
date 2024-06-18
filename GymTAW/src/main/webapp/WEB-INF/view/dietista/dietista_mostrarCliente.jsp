<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.Year" %>
<%@ page import="es.uma.entity.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User cliente = (User) request.getAttribute("cliente");
    List<TipoComida> tiposDeComida = (List<TipoComida>) request.getAttribute("tiposDeComida");
    List<LocalDate> listaFechas = (List<LocalDate>) request.getAttribute("listaFechas");
    Map<String, List<Plato>> tablaComidas = (Map<String, List<Plato>>) request.getAttribute("tablaComidas");
%>

<html>
<head>
    <title>Dietista cliente</title>
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
    <div class="row justify-content-center table-container">
        <div class="col-12">
            <div class="row">
                <div class="col-md-4">
                    <h1>Cliente: <%=cliente.getNombre()%> <%=cliente.getApellidos()%></h1>
                    <p>Edad: <%=Year.now().getValue() - cliente.getFechaNacimiento().getYear()%></p>
                    <p>Peso: <%=cliente.getPeso()%></p>
                    <p>Altura: <%=cliente.getAltura()%></p>
                </div>
                <div class="col-md-4">
                    <h1>Fecha:</h1>
                    <form:form method="post" action="/dietista/setFechaCliente" modelAttribute="diaComida">
                        <div class="mb-3">
                            <label for="day" class="form-label">Dia</label>
                            <form:input path="day" id="day" class="form-control" size="10" maxlength="10"/>
                        </div>
                        <div class="mb-3">
                            <label for="month" class="form-label">Mes</label>
                            <form:input path="month" id="month" class="form-control" size="10" maxlength="10"/>
                        </div>
                        <div class="mb-3">
                            <label for="year" class="form-label">Año</label>
                            <form:input path="year" id="year" class="form-control" size="10" maxlength="10"/>
                        </div>
                        <form:button class="btn btn-primary">Seleccionar fecha</form:button>
                    </form:form>
                </div>
                <div class="col-md-4">
                    <h1>Comida:</h1>
                    <form:form method="post" action="/dietista/selectComidaCliente" modelAttribute="diaComida">
                        <form:hidden path="day"/>
                        <form:hidden path="month"/>
                        <form:hidden path="year"/>
                        <div class="mb-3">
                            <label for="tipoComida" class="form-label">Tipo de Comida</label>
                            <form:select path="tipoComida" id="tipoComida" items="${tiposDeComida}" itemValue="id" itemLabel="comidaDelDia" class="form-control"/>
                        </div>
                        <form:button class="btn btn-primary">Acceder comida</form:button>
                    </form:form>
                </div>
            </div>
            <div class="row mt-4">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th></th>
                        <%
                            for(LocalDate fecha : listaFechas) {
                        %>
                        <th><%= fecha.getDayOfMonth() %> - <%= fecha.getMonthValue() %> - <%= fecha.getYear() %></th>
                        <%
                            }
                        %>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        String[] comidas = {"Desayuno", "Medio dia", "Almuerzo", "Merienda", "Cena"};
                        for(int j = 0; j < comidas.length; ++j) {
                    %>
                    <tr>
                        <td><%= comidas[j] %></td>
                        <%
                            for(int i = 1; i <= 7; ++i) {
                                String diaComida = "Dia" + i + "Comida" + (j + 1);
                        %>
                        <td>
                            <%
                                List<Plato> platos = tablaComidas.get(diaComida);
                                if(platos == null || platos.isEmpty()) {
                            %>
                            (Platos no establecidos)
                            <%
                            } else {
                            %>
                            <ul>
                                <%
                                    for(Plato p : platos) {
                                %>
                                <li><%= p.getNombre() %></li>
                                <%
                                    }
                                %>
                            </ul>
                            <%
                                }
                            %>
                        </td>
                        <%
                            }
                        %>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnrROZqPLFpuUWI4a2sA8pD5A4cJZHPUOks+EmW1E6Lxk3VFtDM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGktK0gYf94IYNd2tKpREIHMR5cQm75J5pbWuyj6cvF2DkSPEj3h4dHGsR9" crossorigin="anonymous"></script>
</body>
</html>

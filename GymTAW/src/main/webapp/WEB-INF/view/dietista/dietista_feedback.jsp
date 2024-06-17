<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.Year" %>
<%@ page import="es.uma.entity.*" %>
<%@ page import="es.uma.ui.FeedbackDietistaMostrarUI" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    DiaDieta diaDieta = (DiaDieta) request.getAttribute("diaDieta");
    Comida comida = (Comida) request.getAttribute("comida");
    List<Plato> listaPlatos = (List<Plato>) request.getAttribute("listaPlatos");
    FeedbackDietistaMostrarUI feedback = (FeedbackDietistaMostrarUI) request.getAttribute("feedback");
%>

<html>
<head>
    <title>Dietista comida feedback</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
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
        .table-container {
            margin-top: 20px;
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

<div class="container mt-4">
    <div class="row justify-content-center">
        <div class="col-sm-10 form-container">
            <div class="row">
                <div class="col text-center">
                    <h2>Fecha: <%=diaDieta.getFecha().getDayOfMonth()%> - <%=diaDieta.getFecha().getMonthValue()%> - <%=diaDieta.getFecha().getYear()%></h2>
                </div>
                <div class="col text-center">
                    <h2>Comida: <%=comida.getTipoComida().getComidaDelDia()%></h2>
                </div>
                <div class="col text-center">
                    <%
                        String realizado = "NO";
                        if(comida.getRealizado() != null && comida.getRealizado() == 1) realizado = "SI";
                    %>
                    <h2>Comida realizada: <%=realizado%></h2>
                </div>
            </div>
            <br/>
            <div class="row">
                <div class="col text-center">
                    <h4>Cliente: <%=diaDieta.getCliente().getNombre()%> <%=diaDieta.getCliente().getApellidos()%></h4>
                    <p>Edad: <%=Year.now().getValue() - diaDieta.getCliente().getFechaNacimiento().getYear()%></p>
                    <p>Peso: <%=diaDieta.getCliente().getPeso()%> kg</p>
                    <p>Altura: <%=diaDieta.getCliente().getAltura()%> cm</p>
                </div>
                <div class="col text-center">
                    <h4>Platos Establecidos:</h4>
                    <form:form method="post" action="/dietista/feedbackComidaSelectedPlato" modelAttribute="feedback">
                        <form:select path="platoMostrando" items="${listaPlatos}" itemValue="id" itemLabel="nombre" size="5" class="form-control"/>
                        <br/>
                        <form:button class="btn btn-primary mt-2">Mostrar plato</form:button>
                    </form:form>
                </div>
                <div class="col text-center">
                    <%
                        if(feedback.getPlatoMostrando() == null) {
                    %>
                    <h4>Cantidades (selecciona un plato):</h4>
                    <%
                    } else {
                    %>
                    <h4>Cantidades (<%=feedback.getPlatoMostrando().getNombre()%>):</h4>
                    <table class="table table-bordered table-container">
                        <thead>
                        <tr>
                            <th>Ingrediente</th>
                            <th>Cantidad consumida</th>
                            <th>Cantidad asignada</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (CantidadIngredientePlatoComida c : feedback.getCantidadesIngredientePlatoComida()) {
                                String cantidadConsumida = (c.getCantidadConsumida() != null) ? c.getCantidadConsumida() + " " + c.getTipoCantidad().getTipoCantidadMedida() : "(sin especificar)";
                        %>
                        <tr>
                            <td><%=c.getIngrediente().getNombre()%></td>
                            <td><%=cantidadConsumida%></td>
                            <td><%=c.getCantidad()%> <%=c.getTipoCantidad().getTipoCantidadMedida()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                    <%
                        }
                    %>
                </div>
            </div>
            <div class="row">
                <div class="col text-center">
                    <h5>Comentarios del cliente (sobre el día completo):</h5>
                    <p><%= (diaDieta.getSeguimiento() != null) ? diaDieta.getSeguimiento() : "(sin especificar)" %></p>
                </div>
            </div>
            <div class="row">
                <div class="col text-center">
                    <h5><a href="/dietista/volverComida">Volver</a></h5>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnbD4La6h/ZGHrR9Fcy0EF+P85y1GQ9xSLJk6l9gWgg1vsJ8fnC" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cjC3uQ8FeI+NQFf01OM0e2dOj/5v5QVXyPhtkSfzAjQpF3GZbO14eO8ha5joWgu0" crossorigin="anonymous"></script>
</body>
</html>

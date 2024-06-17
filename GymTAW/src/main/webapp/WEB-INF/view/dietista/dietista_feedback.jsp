<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.uma.ui.PlatoDietistaUI" %>
<%@ page import="java.time.Year" %>
<%@ page import="es.uma.entity.*" %>
<%@ page import="ch.qos.logback.core.joran.sanity.Pair" %>
<%@ page import="java.util.Map" %>
<%@ page import="es.uma.ui.DiaComida" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="org.springframework.cglib.core.Local" %>
<%@ page import="es.uma.ui.ComidaUI" %>
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
    <title>Dietista comida seleccionada</title>
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
    <div class="col-sm-10 justify-content-center ">
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <h2>Fecha: <%=diaDieta.getFecha().getDayOfMonth()%> - <%=diaDieta.getFecha().getMonthValue()%> - <%=diaDieta.getFecha().getYear()%></h2>
            </div>
            <div class="col justify-content-center">
                <h2>Comida: <%=comida.getTipoComida().getComidaDelDia()%></h2>
            </div>
            <div class="col justify-content-center">
                <%
                    String realizado = "NO";
                    if(comida.getRealizado() != null && comida.getRealizado() == 1) realizado = "SI";
                %>
                <h2>Comida realizada: <%=realizado%></h2>
            </div>
        </div>
        <br/>
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <h4>Cliente: <%=diaDieta.getCliente().getNombre()%> <%=diaDieta.getCliente().getApellidos()%></h4>
                Edad: <%=Year.now().getValue() - diaDieta.getCliente().getFechaNacimiento().getYear()%> <br/>
                Peso: <%=diaDieta.getCliente().getPeso()%> <br/>
                Altura: <%=diaDieta.getCliente().getAltura()%> <br/>
            </div>
            <div class="col justify-content-center">
                <h4>Platos Establecidos:</h4>
                <form:form method="post" action="/dietista/feedbackComidaSelectedPlato" modelAttribute="feedback">
                    <form:select path="platoMostrando" items="${listaPlatos}" itemValue="id" itemLabel="nombre" size="5"/>
                    <br/>
                    <form:button>Mostrar plato</form:button>
                </form:form>
            </div>
            <div class="col justify-content-center">
                <%
                    if(feedback.getPlatoMostrando() == null)
                    {
                %>
                <h4>Cantidades (selecciona un plato):</h4> <br/>
                <%
                } else {
                %>
                <h4>Cantidades (<%=feedback.getPlatoMostrando().getNombre()%>):</h4> <br/>
                <table>
                    <tr>
                        <th>Ingrediente</th>
                        <th>Cantidad consumida</th>
                        <th>Cantidad asignada</th>
                    </tr>
                    <%
                        for (CantidadIngredientePlatoComida c : feedback.getCantidadesIngredientePlatoComida())
                        {
                    %>
                    <tr>
                        <td><%=c.getIngrediente().getNombre()%></td>
                        <%
                            String cantidadConsumida = "(sin especificar)";
                            if(c.getCantidadConsumida() != null) cantidadConsumida = c.getCantidadConsumida() + " " + c.getTipoCantidad().getTipoCantidadMedida();
                        %>
                        <td><%=cantidadConsumida%></td>
                        <td><%=c.getCantidad()%> <%=c.getTipoCantidad().getTipoCantidadMedida()%></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <h5>Comentarios del cliente (sobre el dia completo):</h5> <br/>
                <%
                    String seguimiento = "(sin especificar)";
                    if(diaDieta.getSeguimiento() != null) seguimiento = diaDieta.getSeguimiento();
                %>
                <p><%=seguimiento%></p>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <h5><a href="/dietista/volverComida">Volver</a></h5>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
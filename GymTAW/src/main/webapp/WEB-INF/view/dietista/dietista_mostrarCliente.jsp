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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User cliente = (User) request.getAttribute("cliente");
    List<TipoComida> tiposDeComida = (List<TipoComida>) request.getAttribute("tiposDeComida");
    List<LocalDate> listaFechas = (List<LocalDate>) request.getAttribute("listaFechas");
    Map<String, List<Plato>> tablaComidas = (Map<String, List<Plato>>) request.getAttribute("tablaComidas");
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
    <div class="col-sm-10 justify-content-center ">
        <div class="row justify-content-center">
            <div class="col justify-content-center">
                <h1>Cliente: <%=cliente.getNombre()%> <%=cliente.getApellidos()%></h1> <br/>
                Edad: <%=Year.now().getValue() - cliente.getFechaNacimiento().getYear()%> <br/>
                Peso: <%=cliente.getPeso()%> <br/>
                Altura: <%=cliente.getAltura()%> <br/>
            </div>
            <div class="col justify-content-center">
                <h1>Fecha:</h1> <br/>
                <form:form method="post" action="/dietista/setFechaCliente" modelAttribute="diaComida">
                    Dia: <form:input path="day" size="10" maxlength="10"/> <br/>
                    Mes: <form:input path="month" size="10" maxlength="10"/> <br/>
                    Año: <form:input path="year" size="10" maxlength="10"/> <br/>
                    <form:button>Seleccionar fecha</form:button>
                </form:form>
            </div>
            <div class="col justify-content-center">
                <h1>Comida:</h1> <br/>
                <form:form method="post" action="/dietista/selectComidaCliente" modelAttribute="diaComida">
                    <form:hidden path="day"/>
                    <form:hidden path="month"/>
                    <form:hidden path="year"/>
                    <form:select path="tipoComida" items="${tiposDeComida}" itemValue="id" itemLabel="comidaDelDia"/>
                    <form:button>Acceder comida</form:button>
                </form:form>
            </div>
        </div>
        <div class="row justify-content-center">
            <table>
                <tr>
                    <th></th>
                    <th><%=listaFechas.get(0).getDayOfMonth()%> - <%=listaFechas.get(0).getMonthValue()%> - <%=listaFechas.get(0).getYear()%></th>
                    <th><%=listaFechas.get(1).getDayOfMonth()%> - <%=listaFechas.get(1).getMonthValue()%> - <%=listaFechas.get(1).getYear()%></th>
                    <th><%=listaFechas.get(2).getDayOfMonth()%> - <%=listaFechas.get(2).getMonthValue()%> - <%=listaFechas.get(2).getYear()%></th>
                    <th><%=listaFechas.get(3).getDayOfMonth()%> - <%=listaFechas.get(3).getMonthValue()%> - <%=listaFechas.get(3).getYear()%></th>
                    <th><%=listaFechas.get(4).getDayOfMonth()%> - <%=listaFechas.get(4).getMonthValue()%> - <%=listaFechas.get(4).getYear()%></th>
                    <th><%=listaFechas.get(5).getDayOfMonth()%> - <%=listaFechas.get(5).getMonthValue()%> - <%=listaFechas.get(5).getYear()%></th>
                    <th><%=listaFechas.get(6).getDayOfMonth()%> - <%=listaFechas.get(6).getMonthValue()%> - <%=listaFechas.get(6).getYear()%></th>
                </tr>
                <%
                    for(int j = 1; j<=5; ++j)
                    {
                %>
                    <tr>
                        <%
                            String comida;
                            switch(j)
                            {
                                case 1:
                                    comida = "Desayuno";
                                    break;
                                case 2:
                                    comida = "Medio dia";
                                    break;
                                case 3:
                                    comida = "Almuerzo";
                                    break;
                                case 4:
                                    comida = "Merienda";
                                    break;
                                default:
                                    comida = "Cena";
                                    break;
                            }
                        %>
                        <td><%=comida%></td>
                        <%
                            for(int i = 1; i<=7; ++i)
                            {
                        %>
                            <td>
                                <%
                                    String diaComida = "Dia" + i + "Comida" + j;
                                    if(tablaComidas.get(diaComida).isEmpty())
                                    {

                                %>
                                (Platos no establecidos)
                                <%
                                    } else {
                                %>
                                <ul>
                                <%
                                        for(Plato p : tablaComidas.get(diaComida))
                                        {
                                %>
                                <li><%=p.getNombre()%></li>
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
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
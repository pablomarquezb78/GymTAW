<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.Year" %>
<%@ page import="es.uma.entity.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="es.uma.ui.ComidaUI" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    User cliente = (User) request.getAttribute("cliente");
    LocalDate fecha = (LocalDate) request.getAttribute("fecha");
    TipoComida selectedComida = (TipoComida) request.getAttribute("selectedComida");
    List<Plato> platosDisponibles = (List<Plato>) request.getAttribute("platosDisponibles");
    ComidaUI comidaUI = (ComidaUI) request.getAttribute("comidaUI");
    List<Plato> listaPlatosComidaOut = new ArrayList<>(comidaUI.getListaPlatosComida());
%>

<html>
<head>
    <title>Dietista comida seleccionada</title>
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

<div class="container mt-4">
    <div class="row mb-4 table-container">
        <div class="col text-center">
            <h2>Fecha: <%=fecha.getDayOfMonth()%> - <%=fecha.getMonthValue()%> - <%=fecha.getYear()%></h2>
        </div>
        <div class="col text-center">
            <h2>Comida: <%=selectedComida.getComidaDelDia()%></h2>
        </div>
        <div class="col text-center">
            <h2><a href="/dietista/accederFeedbackComida?comidaID=<%=selectedComida.getId()%>">Comentarios del cliente</a></h2>
        </div>
    </div>

    <div class="row mb-4 table-container">
        <div class="col text-center">
            <h4>Cliente: <%=cliente.getNombre()%> <%=cliente.getApellidos()%></h4>
            Edad: <%=Year.now().getValue() - cliente.getFechaNacimiento().getYear()%> <br/>
            Peso: <%=cliente.getPeso()%> <br/>
            Altura: <%=cliente.getAltura()%> <br/>
        </div>

        <div class="col text-center">
            <h4>Platos Establecidos:</h4>
            <form:form method="post" action="/dietista/mostrarPlatoComida" modelAttribute="comidaUI">
                <form:hidden path="listaPlatosComida"/>
                <form:hidden path="platoExistente"/>
                <form:select path="selectedPlato" class="form-control" size="5">
                    <%
                        for (Plato p : listaPlatosComidaOut) {
                    %>
                    <form:option value="<%=p.getId()%>" label="<%=p.getNombre()%>"></form:option>
                    <%
                        }
                    %>
                </form:select>
                <br/>
                <form:button class="btn btn-primary">Mostrar plato</form:button>
            </form:form>

            <%
                if (comidaUI.getSelectedPlato() != null) {
            %>
            <form:form method="post" action="/dietista/eliminarPlatoComida" modelAttribute="comidaUI">
                <form:hidden path="listaPlatosComida"/>
                <form:hidden path="selectedPlato"/>
                <form:hidden path="platoExistente"/>
                <form:button class="btn btn-danger">Eliminar de la comida: <%=comidaUI.getSelectedPlato().getNombre()%></form:button>
            </form:form>
            <%
                }
            %>
        </div>

        <div class="col text-center">
            <%
                if (comidaUI.getSelectedPlato() == null) {
            %>
            <h4>Cantidades (selecciona un plato):</h4>
            <%
            } else {
            %>
            <h4>Cantidades (<%=comidaUI.getSelectedPlato().getNombre()%>):</h4>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Ingrediente</th>
                    <th>Cantidad</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (CantidadIngredientePlatoComida c : comidaUI.getListaCantidadIngredientesPlatoSeleccionado()) {
                %>
                <tr>
                    <td><%=c.getIngrediente().getNombre()%></td>
                    <td><%=c.getCantidad()%> <%=c.getTipoCantidad().getTipoCantidadMedida()%></td>
                    <td><a href="/dietista/editarCantidadIngrediente?cantidadId=<%=c.getId()%>">Editar cantidad</a></td>
                    <td><a href="/dietista/deleteIngrediente?cantidadId=<%=c.getId()%>">Eliminar ingrediente</a></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <%
                }
            %>

            <form:form method="post" action="/dietista/addIngredientePlatoComida" modelAttribute="comidaUI">
                <form:hidden path="listaPlatosComida"/>
                <form:hidden path="selectedPlato"/>
                <form:hidden path="platoExistente"/>
                <%
                    if (!comidaUI.getListaCantidadIngredientesPlatoSeleccionado().isEmpty()) {
                %>
                <form:hidden path="listaCantidadIngredientesPlatoSeleccionado"/>
                <%
                    }
                %>
                <br/>
                <form:button class="btn btn-primary">Añadir ingrediente</form:button>
            </form:form>
        </div>

        <div class="col text-center">
            <h4>Platos disponibles a añadir:</h4>
            <form:form method="post" action="/dietista/addPlatoToPlatoComida" modelAttribute="comidaUI">
                <form:hidden path="listaPlatosComida"/>
                <form:hidden path="platoExistente"/>
                <form:select path="addingPlato" items="${platosDisponibles}" itemValue="id" itemLabel="nombre" size="5" class="form-control"></form:select>
                <br/>
                <form:button class="btn btn-primary">Añadir plato seleccionado</form:button>
            </form:form>
        </div>
    </div>

    <div class="row mb-4 table-container">
        <div class="col text-center">
            <h5><a href="/dietista/showFechaCliente">Volver</a></h5>
        </div>
        <%
            if (comidaUI.isPlatoExistente()) {
        %>
        <div class="col text-center">
            <h3 class="text-danger">El plato que has intentado añadir ya existe!!!</h3>
        </div>
        <%
            }
        %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnbD4La6h/ZGHrR9Fcy0EF+P85y1GQ9xSLJk6l9gWgg1vsJ8fnC" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cjC3uQ8FeI+NQFf01OM0e2dOj/5v5QVXyPhtkSfzAjQpF3GZbO14eO8ha5joWgu0" crossorigin="anonymous"></script>
</body>
</html>

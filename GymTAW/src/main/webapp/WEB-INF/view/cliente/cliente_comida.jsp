<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.CantidadIngredientePlatoComida" %>
<%@ page import="es.uma.entity.Comida" %>
<%@ page import="es.uma.entity.Ingrediente" %><%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 18/06/2024
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    Comida comida = (Comida) request.getAttribute("comida");
    List<Plato> platos = (List<Plato>) request.getAttribute("platos");
    List<CantidadIngredientePlatoComida> cantidades = (List<CantidadIngredientePlatoComida>) request.getAttribute("cantidades");
    Plato platoSeleccionado = (Plato) request.getAttribute("platoSeleccionado");
    CantidadIngredientePlatoComida cantidadSeleccionada = (CantidadIngredientePlatoComida) request.getAttribute("cantidadSeleccionada");

    Byte realizado = comida.getRealizado();
    Integer platoSeleccionadoId, cantidadSeleccionadaID;

    //COMPROBACION DE PARAMETROS
    if(realizado == null) realizado = 0;
    if(platoSeleccionado!=null){
        platoSeleccionadoId = platoSeleccionado.getId();
    }else{
        platoSeleccionadoId = -1;
    }
    if(cantidadSeleccionada!=null){
        cantidadSeleccionadaID = cantidadSeleccionada.getId();
    }else{
        cantidadSeleccionadaID = -1;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Comidas</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>
<h1>Platos del <%=comida.getTipoComida().getComidaDelDia()%>:</h1>

<form method="post" action="/cliente/seleccionarPlato">
    <input type="hidden" value="<%=comida.getId()%>" name="comidaId">
    <div class="d-flex flex-column w-25 m-3">
        <span>Plato:</span>
        <select name="platoSeleccionado" class="form-select w-50">
            <%for(Plato p: platos){%>
            <option value="<%=p.getId()%>" <%= (p.getId() == platoSeleccionadoId) ? "selected" : "" %>><%=p.getNombre()%></option>
            <%}%>
        </select>
        <button class="btn btn-primary mt-3 w-50">Seleccionar Plato</button>
    </div>
</form>

<div class="bg-secondary w-25 m-3 p-3 rounded">
    <form method="post" action="/cliente/guardarFeedbackComida">
        <h3>Feedback de <%=comida.getTipoComida().getComidaDelDia()%>:</h3>
        <div class="d-flex gap-2">
            <span class="text-white">Realizado:</span>
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" name="realizado" value="1" <%= realizado == 1 ? "checked" : "" %>>
            </div>
        </div>
        <input type="hidden" value="<%=comida.getId()%>" name="comidaId">
        <button class="btn btn-success mt-3">Guardar</button>
    </form>
</div>

<div class="bg-secondary w-25 m-3 rounded">
    <div class="m-3">
        <h3>Ingredientes de <%=platoSeleccionado.getNombre()%>:</h3>
        <div class="d-flex gap-3">
            <form method="post" action="/cliente/seleccionarIngrediente">
                <select name="cantidadSeleccionada" size="<%=cantidades.size()%>" class="form-select w-100">
                    <%for(CantidadIngredientePlatoComida c : cantidades){%>
                    <option value="<%=c.getId()%>" <%= (c.getId() == cantidadSeleccionadaID) ? "selected" : "" %>><%=c.getCantidad()%> <%=c.getTipoCantidad().getTipoCantidadMedida()%> de <%=c.getIngrediente().getNombre()%></option>
                    <%}%>
                </select>
                <input type="hidden" value="<%=comida.getId()%>" name="comidaId">
                <input type="hidden" value="<%=platoSeleccionado.getId()%>" name="platoId">
                <button class="btn btn-primary mt-3">Seleccionar Ingrediente</button>
            </form>

            <form method="post" action="/cliente/guardarCantidadConsumidaIngrediente" style="<%= realizado == 0 ? "display:none" : "" %>">
                <div class="d-flex flex-column">
                    <span class="text-white">Cantidad Consumida(<%=cantidadSeleccionada.getTipoCantidad().getTipoCantidadMedida()%>):</span>
                    <input type="number" name="cantidadConsumida" value="<%=cantidadSeleccionada.getCantidadConsumida()%>">
                    <input type="hidden" value="<%=comida.getId()%>" name="comidaId">
                    <input type="hidden" value="<%=platoSeleccionado.getId()%>" name="platoId">
                    <input type="hidden" value="<%=cantidadSeleccionada.getId()%>" name="cantidadID">
                    <button class="btn btn-primary mt-3">Guardar</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="bg-secondary w-25 m-3 p-3 rounded">
    <h3>Enlace receta:</h3>
    <a class="text-white"><%=platoSeleccionado.getEnlaceReceta()%></a>
    <h3>Tiempo de preparacion:</h3>
    <span class="text-white"><%=platoSeleccionado.getTiempoDePreparacion()%></span>
    <h3>Receta:</h3>
    <p class="text-white"><%=platoSeleccionado.getReceta()%></p>
</div>

</body>
</html>

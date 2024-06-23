<%@ page import="java.util.List" %>
<%@ page import="es.uma.dto.ComidaDTO" %>
<%@ page import="es.uma.dto.PlatoDTO" %>
<%@ page import="es.uma.dto.CantidadIngredientePlatoComidaDTO" %>
<%-- @author: Pablo Márquez Benítez --%>
<%
    //OBTENEMOS PARAMETROS
    ComidaDTO comida = (ComidaDTO) request.getAttribute("comida");
    List<PlatoDTO> platos = (List<PlatoDTO>) request.getAttribute("platos");
    List<CantidadIngredientePlatoComidaDTO> cantidades = (List<CantidadIngredientePlatoComidaDTO>) request.getAttribute("cantidades");
    PlatoDTO platoSeleccionado = (PlatoDTO) request.getAttribute("platoSeleccionado");
    CantidadIngredientePlatoComidaDTO cantidadSeleccionada = (CantidadIngredientePlatoComidaDTO) request.getAttribute("cantidadSeleccionada");

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
<h1 class="text-center">Platos del <%=comida.getTipoComida().getComidaDelDia()%>:</h1>

<form method="post" action="/cliente/seleccionarPlato">
    <input type="hidden" value="<%=comida.getId()%>" name="comidaId">
    <div class="d-flex flex-column w-25 align-items-center mx-auto">
        <select name="platoSeleccionado" class="form-select w-50 mt-3">
            <%for(PlatoDTO p: platos){%>
            <option value="<%=p.getId()%>" <%= (p.getId() == platoSeleccionadoId) ? "selected" : "" %>><%=p.getNombre()%></option>
            <%}%>
        </select>
        <button class="btn btn-primary mt-3 w-50">Seleccionar Plato</button>
    </div>
</form>

<div class="d-flex flex-column justify-content-center">

    <div class="d-flex justify-content-center">
        <div class="bg-secondary w-25 h-25 m-3 p-3 rounded">
            <form method="post" action="/cliente/guardarFeedbackComida">
                <h3>Feedback de <%=comida.getTipoComida().getComidaDelDia()%>:</h3>
                <div class="d-flex gap-2">
                    <span class="text-white">Realizado:</span>
                    <div class="form-check form-switch">
                        <input class="form-check-input" type="checkbox" name="realizado" value="1" <%= realizado == 1 ? "checked" : "" %>>
                    </div>
                </div>
                <input type="hidden" value="<%=platoSeleccionadoId%>" name="platoSeleccionadoID">
                <input type="hidden" value="<%=comida.getId()%>" name="comidaId">
                <button class="btn btn-success mt-3">Guardar</button>
            </form>
        </div>

        <div class="bg-secondary w-25 m-3 rounded">
            <div class="m-3">
                <h3>Ingredientes de <%=platoSeleccionado.getNombre()%>:</h3>
                <div class="d-flex gap-3">
                    <form method="post" action="/cliente/seleccionarIngrediente">
                        <span class="text-white">Ingrediente:</span>
                        <select name="cantidadSeleccionada" size="<%=cantidades.size()%>" class="form-select w-100">
                            <%for(CantidadIngredientePlatoComidaDTO c : cantidades){%>
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
    </div>

    <div class="d-flex justify-content-center">
        <div class="bg-secondary w-25 m-3 p-3 rounded">
            <h3>Receta:</h3>
            <p class="text-white"><%=platoSeleccionado.getReceta()%></p>
            <h3>Tiempo de preparacion:</h3>
            <span class="text-white"><%=platoSeleccionado.getTiempoDePreparacion()%>m</span>
            <h3>Enlace receta:</h3>
            <a class="text-white"><%=platoSeleccionado.getEnlaceReceta()%></a>
        </div>

        <div class="bg-secondary w-25 m-3 p-3 rounded">
            <h3>Información nutricional <%=cantidadSeleccionada.getIngrediente().getNombre()%>:</h3>
            <div>
                <h4 class="d-inline">Azúcares:</h4>
                <span class="d-inline text-white"><%=cantidadSeleccionada.getIngrediente().getAzucares()%>g</span>
            </div>
            <div>
                <h4 class="d-inline">Kilocalorías:</h4>
                <span class="d-inline text-white"><%=cantidadSeleccionada.getIngrediente().getKilocalorias()%>g</span>
            </div>
            <div>
                <h4 class="d-inline">Grasas:</h4>
                <span class="d-inline text-white"><%=cantidadSeleccionada.getIngrediente().getGrasas()%>g</span>
            </div>
            <div>
                <h4 class="d-inline">Proteínas:</h4>
                <span class="d-inline text-white"><%=cantidadSeleccionada.getIngrediente().getProteinas()%>g</span>
            </div>
            <div>
                <h4 class="d-inline">Hidratos de Carbono:</h4>
                <span class="d-inline text-white"><%=cantidadSeleccionada.getIngrediente().getHidratosDeCarbono()%>g</span>
            </div>
        </div>
    </div>

</div>

</body>
</html>

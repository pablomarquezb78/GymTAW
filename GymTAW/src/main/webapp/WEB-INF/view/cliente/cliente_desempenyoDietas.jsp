<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="es.uma.dto.ComidaDTO" %>
<%@ page import="es.uma.dto.PlatoDTO" %>
<%@ page import="es.uma.dto.CantidadIngredientePlatoComidaDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Desempeño dietas</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<div class="d-flex flex-column align-items-center">
    <h1>Desempeño de las dietas</h1>

    <form method="post" action="/cliente/filtrarDesempenyoDieta" class="d-flex gap-3">
        <input class="form-control w-100" type="date" name="fechaDesempenyoDieta">
        <button class="btn btn-primary">Filtrar</button>
    </form>

    <%
        Map<ComidaDTO, Map<PlatoDTO, List<CantidadIngredientePlatoComidaDTO>>> comidaPlatosCantidades = (Map<ComidaDTO, Map<PlatoDTO, List<CantidadIngredientePlatoComidaDTO>>>) request.getAttribute("comidaPlatosCantidades");

        if (comidaPlatosCantidades.isEmpty()) {
    %>
    ¡No hay comidas asignadas para este día!
    <%
    } else {
    %>
    <table class="table w-50">
        <thead>
        <tr>
            <th scope="col">Comida</th>
            <th scope="col">Plato</th>
            <th scope="col">Cantidad Estipulada</th>
            <th scope="col">Cantidad Consumida</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Iteramos sobre cada entrada
            for (Map.Entry<ComidaDTO, Map<PlatoDTO, List<CantidadIngredientePlatoComidaDTO>>> entryComida : comidaPlatosCantidades.entrySet()) {
                ComidaDTO comida = entryComida.getKey();
                Map<PlatoDTO, List<CantidadIngredientePlatoComidaDTO>> platosCantidades = entryComida.getValue();

                // Mostrar el nombre de la comida en la primera fila de cada grupo de platos
                boolean firstRow = true;
                for (Map.Entry<PlatoDTO, List<CantidadIngredientePlatoComidaDTO>> entryPlato : platosCantidades.entrySet()) {
                    PlatoDTO plato = entryPlato.getKey();
                    List<CantidadIngredientePlatoComidaDTO> cantidades = entryPlato.getValue();

                    // Mostrar el nombre de la comida solo en la primera fila de cada grupo de platos
                    if (firstRow) {
        %>
        <tr>
            <td rowspan="<%= platosCantidades.size() %>"><%= comida.getTipoComida().getComidaDelDia() %></td>
            <%
                    firstRow = false;
                }
            %>
            <td><%= plato.getNombre() %></td>
            <td>
                <%
                    // Iterar sobre las cantidades de ingredientes para este plato
                    boolean firstCantidad = true;
                    for (CantidadIngredientePlatoComidaDTO cantidad : cantidades) {
                        if (!firstCantidad) {
                %>
                <br> <!-- Para separar múltiples cantidades de ingredientes -->
                <%
                    }
                %>
                <%=cantidad.getCantidad() + " " + cantidad.getTipoCantidad().getTipoCantidadMedida()%>
                <%
                        firstCantidad = false;
                    }
                %>
            </td>
            <td>
                <%
                    // Iterar sobre las cantidades de ingredientes para este plato
                    firstCantidad = true;
                    for (CantidadIngredientePlatoComidaDTO cantidad : cantidades) {
                        if (!firstCantidad) {
                %>
                <br> <!-- Para separar múltiples cantidades de ingredientes -->
                <%
                    }
                %>
                <%=(cantidad.getCantidadConsumida() == null ? 0 : cantidad.getCantidadConsumida()) + " " + cantidad.getTipoCantidad().getTipoCantidadMedida()%>
                <%
                        firstCantidad = false;
                    }
                %>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>

</body>
</html>

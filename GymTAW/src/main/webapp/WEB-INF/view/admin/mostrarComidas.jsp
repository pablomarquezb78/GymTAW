
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.CantidadIngredientePlatoComida" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<CantidadIngredientePlatoComida> comidas = (List<CantidadIngredientePlatoComida>) request.getAttribute("comidas");
    List<Ingrediente> ingredientes = (List<Ingrediente>) request.getAttribute("ingredientes");
%>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

<br/>
<p>Cliente: <%=comidas.getFirst().getComida().getDiaDieta().getCliente().getNombre()%> Dietista: <%=comidas.getFirst().getComida().getDiaDieta().getDietista().getNombre()%> </p>
<table border="1" cellpadding="10" cellspacing="10">
    <tr>
        <th>ID</th>
        <th>FECHA/DÍA</th>
        <th>FRANJA HORARIA</th>
        <th>CANTIDAD</th>
        <th>INGREDIENTES</th>
        <th></th>
    </tr>

    <%
        for(CantidadIngredientePlatoComida comida : comidas){

    %>
    <tr>
        <td><%=comida.getId()%></td>
        <td><%=comida.getComida().getDiaDieta().getFecha()%></td>
        <td><%=comida.getComida().getTipoComida().getComidaDelDia()%></td>
        <td><%=comida.getCantidad()%></td>
        <td>
            <% for(Ingrediente ingrediente : ingredientes){
            %>
                <%=ingrediente.getNombre() +", "%>
            <%
                }
            %>
        </td>

        <td><a href="/admin/editar?id=<%=comida.getId()%>">Editar</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
<%@ page import="es.uma.entity.DiaEntrenamiento" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>

<%
    List<DiaEntrenamiento> list = (List<DiaEntrenamiento>) request.getAttribute("dias");
    User cliente = (User) request.getAttribute("cliente");

%>

<html>
<head>
    <title>Semana Entrenamientos</title>
</head>

<body>

<form method="post" action="/entrenamientos/creardia">
    <input type="hidden" name="clienteid" value="<%=cliente.getId()%>">
    <button type="submit">Crear Entrenamiento</button>
</form>


<table border="1">
    <tr>
        <th>ID</th>
        <th>Dia</th>
        <th></th>
        <th></th>
        <th></th>
    </tr>

    <%
        if(list !=null){
            for (DiaEntrenamiento dia : list) {
    %>
    <tr>
        <td><%=dia.getId()%></td>
        <td><%= dia.getFecha() %></td>
        <td>Editar</td>
        <td>
            <form method="post" action="/entrenamientos/borrar">
                <input type="hidden"  name="id" value="<%=dia.getId()%>">
                <button type="submit"> Borrar </button>
            </form>
        </td>

    </tr>

    <%
        }
    }else{
    %>
    <h2> NO EXISTEN ENTRENAMIENTOS</h2>
    <%
        }
    %>
</table>


</body>
</html>
<%@ page import="es.uma.entity.DiaEntrenamiento" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>

<%
    List<DiaEntrenamiento> list = (List<DiaEntrenamiento>) request.getAttribute("dias");
    User cliente = (User) request.getAttribute("cliente");

%>

<html>
<head>
    Semana Entrenamientos
</head>

<body>
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
        <td>Ver</td>
        <td><a href="/entrenamientos/borrar?id=<%=dia.getId()%>">Borrar</a></td>
        <td>Editar</td>
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
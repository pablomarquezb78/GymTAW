<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>

<%
    List<User> lista = (List<User>) request.getAttribute("lista");

%>

<html>
<head>
    <title>
        Listado de Clientes Asociados
    </title>
</head>

<body>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellidos</th>

        </tr>

        <%
            for(User usuario : lista){
        %>

        <td><%=usuario.getId()%></td>
        <td><%=usuario.getNombre()%></td>
        <td><%=usuario.getApellidos()%></td>

        <%
            }
        %>

    </table>


</body>




</html>
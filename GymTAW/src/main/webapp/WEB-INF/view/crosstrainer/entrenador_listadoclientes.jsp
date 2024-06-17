<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>

<%
    List<User> lista = (List<User>) request.getAttribute("lista");

%>

<html>
<head>
    <title>
        Clientes Asociados
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>


<body>
<jsp:include page="cabecera_entrenador.jsp"/>

    <h1> CLIENTES ASOCIADOS :</h1>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th></th>
            <th></th>
        </tr>

        <%
            for(User usuario : lista){
        %>

        <td><%=usuario.getId()%></td>
        <td><%=usuario.getNombre()%></td>
        <td><%=usuario.getApellidos()%></td>
        <td><a href="/entrenamientos/verperfilcliente?idcliente=<%=usuario.getId()%>">Ver Perfil</a></td>
        <td><a href="/entrenamientos/versemana?id=<%=usuario.getId()%>">Ver Entrenamientos</a></td>
        <%
            }
        %>
        <a href="/entrenamientos/crear-ejercicio" class="btn btn-success">Crear Ejercicio</a>
        <a href="/entrenamientos/crear-tipo" class="btn btn-success">Crear Tipo</a>
    </table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>
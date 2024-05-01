<%@ page import="es.uma.entity.User" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    List<User> lista = (List<User>) request.getAttribute("listadoUsuarios");


%>

<html>
<head>
    <title>Listado usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>

<body>


<h1>Listado de usuarios</h1>

<section>

        <%
            if(lista != null && lista.size() > 0){
            for(User user : lista){

        %>
        <tr>

            <td>
                <%=user.getId()%>
            </td>
            <td>
                <%=user.getNombre()%>
            </td>
            <td>
                <%=user.getApellidos()%>
            </td>
            <td>
                <a>Ver perfil</a>
            </td>
            <td>
                <a href="/trainer/entrenamientos=?<%=user.getId()%>">Ver entrenamientos</a>
            </td>
        </tr>
        <%
            }
            }
            else{
        %>
            <span>No hay nadie :(</span>
        <%
            }
        %>


</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
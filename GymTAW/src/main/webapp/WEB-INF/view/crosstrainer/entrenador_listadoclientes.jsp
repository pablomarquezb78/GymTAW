<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.dto.UserDTO" %>

<%
    List<UserDTO> lista = (List<UserDTO>) request.getAttribute("lista");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Clientes Asociados</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .container {
            background: #ffffff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 30px;
        }
        h1 {
            margin-bottom: 20px;
        }
        table {
            margin-top: 20px;
        }
        th, td {
            text-align: center;
        }
        .btn-custom {
            margin-right: 10px;
        }
    </style>
</head>
<body>
<jsp:include page="cabecera_entrenador.jsp"/>

<div class="container">


    <h1>CLIENTES ASOCIADOS</h1>

    <form class="d-flex mb-3" method="post" action="/entrenamientos/filtrarClientes">
        <%
            if(!lista.isEmpty()){
        %>
        <input name="nombre" type="text" class="form-control me-2" placeholder="Filtrar por Nombre">
        <button class="btn btn-primary" type="submit">Filtrar</button>
        <%
        } else {
        %>
        <input type="hidden" value="" name="nombre">
        <button class="btn btn-secondary" type="submit">Reiniciar</button>
        <%
            }
        %>
    </form>

    <%
        if(lista.isEmpty()){
    %>
    <p class="text-danger">No hay usuarios asignados con ese nombre</p>
    <%
    } else {
    %>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellidos</th>
            <th>Ver Perfil</th>
            <th>Ver Entrenamientos</th>
        </tr>
        </thead>
        <tbody>
        <%
            for(UserDTO usuario : lista){
        %>
        <tr>
            <td><%=usuario.getId()%></td>
            <td><%=usuario.getNombre()%></td>
            <td><%=usuario.getApellidos()%></td>
            <td><a href="/entrenamientos/verperfilcliente?idcliente=<%=usuario.getId()%>" class="btn btn-info btn-sm">Ver Perfil</a></td>
            <td><a href="/entrenamientos/versemana?id=<%=usuario.getId()%>" class="btn btn-info btn-sm">Ver Entrenamientos</a></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        }
    %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

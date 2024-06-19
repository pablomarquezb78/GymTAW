<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Rutina" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String cabecera = "cabecera_entrenador.jsp";
    List<Rutina> rutinas = (List<Rutina>) request.getAttribute("rutinas");
%>
<html>
<head>
    <title>Rutinas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />

</head>
<body>
<jsp:include page="<%=cabecera%>"></jsp:include>

<%
    if(rutinas.size()>0){

%>

<div class="container mt-5">

    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>Nombre</th>
            <th>Fecha de creación</th>
            <th>Entrenador</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Rutina rutina : rutinas) {
        %>
        <tr>
            <td><%=rutina.getNombre()%></td>
            <td><%=rutina.getFechaCreacion().toString()%></td>
            <td><%=rutina.getEntrenador().getNombre()%></td>
            <td><a href="/entrenamientos/crearrutina?idrutina=<%=rutina.getId()%>" class="btn btn-success">Editar</a></td>
            <td><a class="btn btn-danger">Borrar</a></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
<%
    }else{

%>
<h1>No hay rutinas creadas :(</h1>
<%
    }
%>
</body>
</html>
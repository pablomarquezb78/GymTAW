
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
%>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />

</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>

    <br/>
    <table border="1" cellpadding="10" cellspacing="10">
        <tr>
            <th>ID</th>
            <th>NOMBRE DEL EJERCICIO</th>
            <th>TIPO DE EJERCICIO</th>
            <th>ENLACE DEL VÍDEO</th>
            <th>DESCRIPCIÓN</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>

<%
    for(Ejercicio ejercicio : ejercicios){

%>
        <tr>
            <td><%=ejercicio.getId()%></td>
            <td><%=ejercicio.getNombre()%></td>
            <td><%=ejercicio.getTipo().getTipoDeEjercicio()%></td>
            <td><%=ejercicio.getEnlaceVideo()%></td>
            <td><%=ejercicio.getDescripcion()%></td>
            <td><a href="/admin/editarEjercicio?id=<%=ejercicio.getId()%>" class="btn btn-success"><i class="fas fa-pencil-alt"></i> Editar</a></td>
            <td><a href="/admin/borrarEjercicio?id=<%=ejercicio.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a></td>
            <td><a href="/admin/verRutinasAsociadas?id=<%=ejercicio.getId()%>">Ver rutinas asociadas</a></td>
        </tr>
<%
    }
%>
    </table>
<a href="/admin/crearNuevoEjercicio" class="btn btn-success mt-3">Crear nuevo ejercicio</a>
</body>
</html>
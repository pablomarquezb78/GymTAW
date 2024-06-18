<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ejercicio" %>


<%

    UserRol userRol = (UserRol) request.getAttribute("rol");
    List<TipoEjercicio> tiposEjercicio = (List<TipoEjercicio>) request.getAttribute("tiposEjercicio");
    String cabecera = "./crosstrainer/cabecera_entrenador.jsp";
    if(userRol.getId() == 1) {
        cabecera = "./admin/cabeceraAdmin.jsp";
    }
%>
<html>
<head>
    <title>Tipos de ejercicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
</head>
<body>
<jsp:include page="<%=cabecera%>"></jsp:include>
<br>
<table border="1" cellpadding="10" cellspacing="10">
<tr>
    <th>ID</th>
    <th>NOMBRE DEL TIPO DE EJERCICIO</th>
    <th></th>
    <th></th>
    <th></th>
</tr>
<%
    if(tiposEjercicio.size() > 0){
        for(TipoEjercicio tipo : tiposEjercicio){
%>
<tr>
    <td><%=tipo.getId()%></td>
    <td><%=tipo.getTipoDeEjercicio()%></td>
    <td><a href="/comun/editarEjercicio?id=<%=tipo.getId()%>" class="btn btn-success"><i class="fas fa-pencil-alt"></i> Editar</a></td>
    <td><a href="/comun/borrarEjercicio?id=<%=tipo.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a></td>
    <td><a href="/comun/verImplementacionesAsociadas?id=<%=tipo.getId()%>">Ver ejercicios asociados</a></td>
</tr>
<%
        }
    }else{
%>
    <h1>No hay creado ningun tipo de ejercicio :(</h1>
<%
    }
%>
</table>

</body>
</html>
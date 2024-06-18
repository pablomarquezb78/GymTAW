<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
    List<TipoEjercicio> tipos = (List<TipoEjercicio>) request.getAttribute("tipos");

    UserRol rol = (UserRol) request.getAttribute("rol");
    String title = "Trainer";
    String cabecera = "../crosstrainer/cabecera_entrenador.jsp";
    if(rol.getId() == 1){
        title = "Admin";
        cabecera = "cabeceraAdmin.jsp";
    }
%>

<html>
<head>
    <title><%=title%></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-..." crossorigin="anonymous" />
</head>
<body>
<jsp:include page="<%=cabecera%>"></jsp:include>
<br/>

    <%
        if(ejercicios.size() > 0){

    %>
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
        <td><a href="/comun/editarEjercicio?id=<%=ejercicio.getId()%>" class="btn btn-success"><i class="fas fa-pencil-alt"></i> Editar</a></td>
        <td><a href="/comun/borrarEjercicio?id=<%=ejercicio.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a></td>
        <td><a href="/comun/verImplementacionesAsociadas?id=<%=ejercicio.getId()%>">Ver rutinas asociadas</a></td>
    </tr>
    <%
        }

    %>
    </table>
    <a href="/comun/crearNuevoEjercicio" class="btn btn-success mt-3">Crear nuevo ejercicio</a>

    <form:form action="/comun/filtrarEjercicios" method="post" modelAttribute="ejercicio">
        <br>
        <label>Nombre:</label>
        <form:input path="nombre" size="15"></form:input>
        <label>Descripcion:</label>
        <form:input path="descripcion" size="10"></form:input>
        <label>Tipo:</label>
        <form:radiobuttons path="idTipo" items="${tipos}" itemLabel="tipoDeEjercicio" itemValue="id"></form:radiobuttons>
        <br>
        <form:button class="btn btn-success mt-3">Filtrar ejercicio</form:button>
    </form:form>

    <%
        }else{
    %>
            <h2>No hay ningún ejercicio :(</h2>
            <br>
            <a href="/comun/crearNuevoEjercicio" class="btn btn-success mt-3">Crear nuevo ejercicio</a>
    <%
        }
    %>

</body>
</html>

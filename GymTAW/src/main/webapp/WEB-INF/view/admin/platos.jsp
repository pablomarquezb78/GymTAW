<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="es.uma.dto.PlatoDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<PlatoDTO> platos = (List<PlatoDTO>) request.getAttribute("platos");
    request.setAttribute("paginaActual", "platos");

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
<table class="table table-bordered table-hover">
    <thead class="text-white text-center" style="background-color: #343a40">
    <tr>
        <th>ID</th>
        <th>NOMBRE</th>
        <th>TIEMPO</th>
        <th>RECETA</th>
        <th>ENLACE DEL VÍDEO</th>
        <th>ACCIONES</th>
    </tr>
    </thead>
    <tbody>
    <%
        for(PlatoDTO plato : platos){
    %>
    <tr class="text-center">
        <td><%=plato.getId()%></td>
        <td><%=plato.getNombre()%></td>
        <td><%=plato.getTiempoDePreparacion()%>s</td>
        <td><%=plato.getReceta()%></td>
        <td><%=plato.getEnlaceReceta()%></td>

        <td>
            <a href="/admin/editarPlato?id=<%=plato.getId()%>" class="btn btn-warning"><i class="fas fa-pencil-alt"></i> Editar</a>
            <a href="/admin/borrarPlato?id=<%=plato.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a>
            <a href="/admin/verComidasAsociadas?id=<%=plato.getId()%>" class="btn btn-primary"> <i class="fas fa-eye"></i> Ver comidas asociadas</a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>

<a href="/admin/crearNuevoPlato" class="btn btn-warning mt-3">Crear nuevo plato</a>

<form:form action="/admin/filtrarPlatos" method="post" modelAttribute="plato" class="p-4">
    <div class="form-row">
        <div class="form-group col-md-4">
            <label>Nombre:</label>
            <form:input path="nombre" size="15" class="form-control" style="width: auto;"></form:input>
        </div>
        <div class="form-group col-md-4">
            <label>Tiempo:</label>
            <form:input path="tiempoDePreparacion" size="10" class="form-control" style="width: auto;"></form:input>
        </div>
        <div class="form-group col-md-4">
            <label>Receta:</label>
            <form:input path="receta" size="30" class="form-control" style="width: auto;"></form:input>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mt-3">Filtrar plato</button>
</form:form>
</body>
</html>
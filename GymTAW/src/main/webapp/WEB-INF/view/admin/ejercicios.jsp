<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="es.uma.dto.EjercicioDTO" %>
<%@ page import="es.uma.dto.TipoEjercicioDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<EjercicioDTO> ejercicios = (List<EjercicioDTO>) request.getAttribute("ejercicios");
    List<TipoEjercicioDTO> tipos = (List<TipoEjercicioDTO>) request.getAttribute("tipos");

    UserRolDTO rol = (UserRolDTO) request.getAttribute("rol");
    String title = "Trainer";
    String cabecera = "../crosstrainer/cabecera_entrenador.jsp";
    request.setAttribute("paginaActual", "ejercicios");

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
<table class="table table-bordered table-hover">
    <thead class="text-white text-center" style="background-color: #343a40">
    <tr>
        <th>ID</th>
        <th>NOMBRE DEL EJERCICIO</th>
        <th>TIPO DE EJERCICIO</th>
        <th>ENLACE DEL VÍDEO</th>
        <th>DESCRIPCIÓN</th>
        <th>ACCIONES</th>
    </tr>
    </thead>
    <tbody>
    <%
        for(EjercicioDTO ejercicio : ejercicios){
    %>
    <tr class="text-center">
        <td><%=ejercicio.getId()%></td>
        <td><%=ejercicio.getNombre()%></td>
        <td><%=ejercicio.getTipo().getTipoDeEjercicio()%></td>
        <td><%=ejercicio.getEnlaceVideo()%></td>
        <td><%=ejercicio.getDescripcion()%></td>
        <td>
            <a href="/comun/editarEjercicio?id=<%=ejercicio.getId()%>" class="btn btn-warning btn-sm">
                <i class="fas fa-pencil-alt"></i> Editar
            </a>
            <a href="/comun/borrarEjercicio?id=<%=ejercicio.getId()%>" class="btn btn-danger btn-sm">
                <i class="fas fa-trash-alt"></i> Borrar
            </a>
            <a href="/comun/verImplementacionesAsociadas?id=<%=ejercicio.getId()%>" class="btn btn-primary btn-sm"> <i class="fas fa-eye"></i> Ver rutinas asociadas
            </a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<a href="/comun/crearNuevoEjercicio" class="btn btn-warning mt-3">Crear nuevo ejercicio</a>

<form:form action="/comun/filtrarEjercicios" method="post" modelAttribute="ejercicio" class="p-4">
    <div class="form-row">
        <div class="form-group col-md-4">
            <label for="nombre">Nombre:</label>
            <form:input path="nombre" id="nombre" class="form-control" style="width: auto;"/>
        </div>
        <div class="form-group col-md-8">
            <label for="descripcion">Descripción:</label>
            <form:input size="100" path="descripcion" id="descripcion" class="form-control" style="width: auto;"/>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-12">
            <label>Tipo:</label>
            <div>
                <form:radiobuttons path="idTipo" items="${tipos}" delimiter="</br>" itemLabel="tipoDeEjercicio" itemValue="id"/>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary mt-3">Filtrar ejercicio</button>
</form:form>

    <%
        }else{
    %>
            <h2>No hay ningún ejercicio </h2>
            <br>
            <a href="/comun/crearNuevoEjercicio" class="btn btn-warning mt-3">Crear nuevo ejercicio</a>
    <%
        }
    %>

</body>
</html>

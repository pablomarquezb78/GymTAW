<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="es.uma.ui.Usuario" %>


<%
    Integer rolid = (Integer) request.getAttribute("rolid");
    Usuario usuario = (Usuario) request.getAttribute("usuario");

    String cabecera = "";
    if(rolid==1) cabecera = "./admin/cabeceraAdmin.jsp"; //admin
    if(rolid==2) cabecera = "./cliente/cabecera_cliente.jsp"; //cliente
    if(rolid==3) cabecera = "./crosstrainer/cabecera_entrenador.jsp"; //trainer
    if(rolid==4) cabecera = "./crosstrainer/cabecera_entrenador.jsp"; //trainer
    if(rolid==5) cabecera = "./crosstrainer/cabecera_entrenador.jsp"; // dietista (cambiar: No fue encontrada la cabecera de dietista)
%>
<!DOCTYPE html>
<html>
<head>
    <title>Perfil de Usuario</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .profile-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            color: black;
            margin-top: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
        }
        .profile-header {
            font-size: 2em;
        }
        .profile-label {
            font-weight: bold;
        }
        .placeholder-image {
            width: 120px;
            height: 120px;
            background: #e0e0e0;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            color: #888;
            margin-bottom: 20px;
        }
    </style>
</head>
<body class="bg-light">

<jsp:include page="<%=cabecera%>"></jsp:include>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="profile-container text-center">
                <div class="d-flex justify-content-center">
                    <div class="placeholder-image">
                        <span>IMG</span>
                    </div>
                </div>
                <div class="profile-header">
                    Perfil de: ${usuario.username}
                </div>
                <form:form modelAttribute="usuario" method="post" action="/comun/guardarPerfil">
                    <div class="mb-3">
                        <label for="nombre" class="profile-label">Nombre:</label>
                        <form:input path="nombre" id="nombre" cssClass="form-control"/>
                    </div>
                    <div class="mb-3">
                        <label for="apellidos" class="profile-label">Apellidos:</label>
                        <form:input path="apellidos" id="apellidos" cssClass="form-control"/>
                    </div>
                    <div class="mb-3">
                        <label for="telefono" class="profile-label">Num. Tlf:</label>
                        <form:input path="telefono" id="telefono" cssClass="form-control"/>
                    </div>
                    <div class="mb-3">
                        <label for="peso" class="profile-label">Peso:</label>
                        <form:input path="peso" id="peso" cssClass="form-control" type="number"/>
                    </div>
                    <div class="mb-3">
                        <label for="altura" class="profile-label">Altura:</label>
                        <form:input path="altura" id="altura" cssClass="form-control" type="number"/>
                    </div>
                    <div class="mb-3">
                        <label for="fechaNacimiento" class="profile-label">Fecha de Nacimiento:</label>
                        <form:input path="fechaNacimiento" id="fechaNacimiento" cssClass="form-control" type="date"/>
                    </div>
                    <div class="mb-3">
                        <label for="descripcionPersonal" class="profile-label">Descripcion Personal:</label>
                        <form:textarea path="descripcionPersonal" id="descripcionPersonal" cssClass="form-control"/>
                    </div>
                    <form:hidden path="id"></form:hidden>
                    <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlYlRd5zybT9Sc2n3MGLzJQd4qfjk1eD5wU8jg9L8/sd8fiQ68xXXby6iZp" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVkiBFEiyI2ErQpkCjfnmjLnx1pA6IbsEcjFZhJvPuhhxZZj3swl7l9Tw1RgyVGN" crossorigin="anonymous"></script>
</body>
</html>

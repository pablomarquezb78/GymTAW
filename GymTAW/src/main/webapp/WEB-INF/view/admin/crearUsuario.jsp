<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page import="java.util.*" %>
<%@ page import="es.uma.ui.Usuario" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<UserRolDTO> roles = (List<UserRolDTO>) request.getAttribute("roles");
    Usuario usuario = (Usuario) request.getAttribute("usuario");
%>
<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
            background: #f8f9fa;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 50px;
        }
        .form-header {
            font-size: 1.5em;
            margin-bottom: 20px;
        }
        .form-label {
            font-weight: bold;
        }
        .form-select,
        .form-input {
            margin-bottom: 15px;
        }
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
<jsp:include page="cabeceraAdmin.jsp"></jsp:include>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="form-container">
                <div class="form-header">
                    <h3><%=usuario.getId() == null ? "Crear usuario" : "Modificar usuario" %></h3>
                    <p><%=usuario.getId() == null ? "Introduzca los datos necesarios para añadir un nuevo usuario" : "Modifica los datos del usuario como desee" %></p>
                </div>

                <form:form action="/admin/guardarUsuario" method="post" modelAttribute="usuario">
                    <form:hidden path="id"></form:hidden>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group form-input">
                                <label class="form-label">*Usuario:</label>
                                <form:input path="username" size="15" class="form-control"/>
                            </div>
                            <div class="form-group form-input">
                                <label class="form-label">*Contraseña:</label>
                                <form:input path="password" size="25" class="form-control"/>
                            </div>
                            <div class="form-group form-input">
                                <label class="form-label">*Nombre:</label>
                                <form:input path="nombre" size="15" class="form-control"/>
                            </div>
                            <div class="form-group form-input">
                                <label class="form-label">*Apellidos:</label>
                                <form:input path="apellidos" size="50" class="form-control"/>
                            </div>
                            <div class="form-group form-input">
                                <label class="form-label">*Fecha de nacimiento:</label>
                                <form:input path="fechaNacimiento" size="10" class="form-control"/>
                            </div>
                            <div class="form-group form-select">
                                <label class="form-label">*Rol:</label> </br>
                                <form:radiobuttons path="rol" items="${roles}" delimiter="<br>" itemLabel="rolUsuario" itemValue="id" />
                            </div>
                            <div class="form-group form-input">
                                <label class="form-label">Peso:</label>
                                <form:input path="peso" size="3" class="form-control"/>
                            </div>
                            <div class="form-group form-input">
                                <label class="form-label">Altura:</label>
                                <form:input path="altura" size="3" class="form-control"/>
                            </div>
                            <div class="form-group form-input">
                                <label class="form-label">Telefono:</label>
                                <form:input path="telefono" size="9" class="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:button class="btn btn-success mt-3">Guardar usuario</form:button>
                    </div>
                </form:form>

            </div>
        </div>
    </div>
</div>


</body>
</html>

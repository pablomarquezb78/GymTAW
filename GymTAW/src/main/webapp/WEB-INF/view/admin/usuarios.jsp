<%@ page import="es.uma.entity.User" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<User> usuarios = (List<User>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
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
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th>PESO</th>
        <th>ALTURA</th>
        <th>TELEFONO</th>
        <th></th>
        <th></th>
    </tr>

    <%
        for(User usuario : usuarios){

    %>
    <tr>
        <td><%=usuario.getId()%></td>
        <td><%=usuario.getNombre()%></td>
        <td><%=usuario.getApellidos()%></td>
        <td><%=usuario.getRol().getRolUsuario()%></td>
        <td><%=usuario.getFechaNacimiento()%></td>
        <td><%=usuario.getPeso()%></td>
        <td><%=usuario.getAltura()%></td>
        <td><%=usuario.getTelefono()%></td>
        <td><a href="/admin/editarUsuario?id=<%=usuario.getId()%>" class="btn btn-success"><i class="fas fa-pencil-alt"></i> Editar</a></td>
        <td><a href="/admin/borrarUsuario?id=<%=usuario.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Borrar</a></td>
    </tr>
    <%
        }
    %>
</table>
<a href="/admin/crearNuevoUsuario" class="btn btn-success mt-3">Crear nuevo usuario</a>
</body>
</html>

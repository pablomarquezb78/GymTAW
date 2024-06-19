<%@ page import="java.util.*" %>
<%@ page import="es.uma.entity.Registro" %>
<%@ page import="es.uma.dto.RegistroDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<RegistroDTO> peticiones = (List<RegistroDTO>) request.getAttribute("peticiones");
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
        <th>NOMBRE</th>
        <th>APELLIDOS</th>
        <th>ROL</th>
        <th>FECHA NACIMIENTO</th>
        <th>TELEFONO</th>
        <th></th>
        <th></th>
    </tr>

    <%
        String rol = "";
        for(RegistroDTO usuario : peticiones){
            if(usuario.getRol() == 2){
                rol = "cliente";
            }else if(usuario.getRol() == 3){
                rol = "bodybuilder";
            }else if(usuario.getRol() == 4){
                rol = "crosstrainer";
            }else if(usuario.getRol() == 5){
                rol = "dietista";
            }
    %>
    <tr>
        <td><%=usuario.getId()%></td>
        <td><%=usuario.getNombre()%></td>
        <td><%=usuario.getApellidos()%></td>
        <td><%=rol%></td>
        <td><%=usuario.getFechaNacimiento()%></td>
        <td><%=usuario.getTelefono()%></td>
        <td><a href="/admin/autenticar?id=<%=usuario.getId()%>" class="btn btn-success"> Autenticar </a></td>
        <td><a href="/admin/borrarPeticion?id=<%=usuario.getId()%>" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Cancelar</a></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
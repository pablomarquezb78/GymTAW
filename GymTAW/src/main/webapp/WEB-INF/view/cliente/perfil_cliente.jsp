<%@ page import="es.uma.entity.User" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    User user = (User) request.getAttribute("user");
    UserRol userRol = (UserRol) request.getAttribute("rol");
    boolean editar = userRol.getId() == 1 || userRol.getId() == 2;
    String disabled = "disabled";
    if(editar){
        disabled = "";
    }
%>
<html>

<head>
    <title>Perfil cliente</title>
</head>
<body>

    <%
        if(user != null){
    %>
        <section>

            <span>Nombre:</span>
            <input <%=disabled%> value="<%=user.getNombre()%>">

        </section>
    <%
        }
    %>

</body>
</html>

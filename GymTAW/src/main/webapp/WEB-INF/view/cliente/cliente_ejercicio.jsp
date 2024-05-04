<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 03/05/2024
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    String descripcion = (String) request.getAttribute("descripcion");
    String enlaceVideo = (String) request.getAttribute("enlaceVideo");
    String tipoEjercicio = (String) request.getAttribute("tipoEjercicio");

    //COMPROBACIONES PARAMOTRES
    if(descripcion == null) descripcion="";
    if (enlaceVideo == null) enlaceVideo="";
    if(tipoEjercicio == null) tipoEjercicio="";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<h5>Tipo: <%=tipoEjercicio%></h5><br/>
<h5>Descripción:</h5>
<p><%=descripcion%></p>
<h5>URL: <%=enlaceVideo%></h5>

<form>
    <select name="realizado">
        <option value="1" selected>Si</option>
        <option value="2" >No</option>
    </select>
    Series Realizadas:
    <input type="text" name="series">
    Series Realizadas:
    <input type="text" name="series">
    Series Realizadas:
    <input type="text" name="series">
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

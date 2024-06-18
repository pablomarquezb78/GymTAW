<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="es.uma.entity.TipoEjercicio" %>


<%
    String actionRol = "/entrenamientos/guardar-tipo-ejercicio";
    String cabecera = "cabecera_entrenador.jsp";
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
    TipoEjercicio tipoEjercicio = (TipoEjercicio) request.getAttribute("tipoEjercicio");
%>
<html>
<head>
    <title>Ejercicios asociados a <%=tipoEjercicio.getTipoDeEjercicio()%></title>
</head>
<body>
<jsp:include page="<%=cabecera%>"></jsp:include>

<h1>Ejercicios asociados a <%=tipoEjercicio.getTipoDeEjercicio()%></h1>

<table>

    <%
        if(ejercicios.size() > 0){

    %>

    <tr>
        <th>ID</th>
        <th>NOMBRE DE LA RUTINA</th>
        <th>SERIES</th>
        <th>REPETICIONES</th>
        <th>PESO</th>
        <th>METROS</th>
        <th>TIEMPO</th>
        <th>KCAL</th>
        <th></th>
        <th></th>
    </tr>

    <%
            for(Ejercicio ejercicio : ejercicios){
    %>



</table>




</body>
</html>
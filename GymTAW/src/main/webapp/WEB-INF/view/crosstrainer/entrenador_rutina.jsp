<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.dao.EjercicioRepository" %>
<%
    List<ImplementacionEjercicioRutina> lista = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");
%>


<html>

<head>
    <title>Rutina</title>
</head>
<body>

<table border="1">
    <tr>
        <th>Ejercicio</th>
        <th>Sets</th>
        <th>Repeticiones</th>
        <th>Peso</th>
        <th>Metros</th>
        <th>Tiempo</th>
        <th>RealizadoCheck</th>
        <th></th>
    </tr>

    <%
        for(ImplementacionEjercicioRutina imp : lista){
    %>
        <tr>
            <td><%=imp.getEjercicio().getNombre()%></td>
            <td><%=imp.getSets() != null ? imp.getSets() : ""%></td>
            <td><%=imp.getRepeticiones() != null ? imp.getRepeticiones() : ""%></td>
            <td><%=imp.getPeso() != null ? imp.getPeso() : ""%>Kg</td>
            <td><%=imp.getMetros() != null ? imp.getMetros() : ""%>Mts</td>
            <td><%=imp.getTiempo() != null ? imp.getTiempo() : ""%></td>
            <td><%=imp.getRealizado() != null ? imp.getRealizado() : "NO"%></td>
            <td><a href="/entrenamientos/editarimplementacion?id=<%=imp.getId()%>">Editar</a></td>
        </tr>
    <%
        }
    %>

</table>



</body>
</html>
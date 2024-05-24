<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.dao.EjercicioRepository" %>
<%
    List<ImplementacionEjercicioRutina> lista = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");
    Integer idrutina = (Integer) request.getAttribute("idrutina");
%>


<html>

<head>
    <title>Rutina</title>
</head>
<body>
<h1>NUEVA RUTINA</h1>
<table border="1">
    <tr>
        <th>Ejercicio</th>
        <th>Sets</th>
        <th>Repeticiones</th>
        <th>Peso</th>
        <th>Metros</th>
        <th>Tiempo</th>
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
        <td>
            <form action="/entrenamientos/borrarimplementacionderutina" method="post">
                <input type="hidden" name="id" value="<%=imp.getId()%>">
                <input type="hidden" name="idrutina" value="<%=idrutina%>">
                <button type="submit">Quitar</button>
            </form>
        </td>
    </tr>
    <%
        }
    %>

</table>

<form action="/entrenamientos/crearimplementacionrutina" method="get">
    <input type="hidden" name="id" value="<%=-1%>">
    <input type="hidden" name="idrutina" value="<%=idrutina%>">
    <button type="submit">Anadir Ejercicio</button>
</form>



</body>
</html>
<%@ page import="es.uma.entity.DiaEntrenamiento" %>
<%@ page import="java.util.List" %>

<%
    List<DiaEntrenamiento> list = (List<DiaEntrenamiento>) request.getAttribute("dias");


%>

<html>
    <head>
        Semana Entrenamientos
    </head>

    <body>
        <table>
            <tr>
                <th>Dia</th>
                <th></th>
                <th></th>
                <th></th>
            </tr>

            <%
                if(list !=null){
                for (DiaEntrenamiento dia : list) {
            %>

                <td><%= dia.getFecha() %></td>
                <td>Ver</td>
                <td>Modificar</td>
                <td>Eliminar</td>
            <%
                    }
                }else{
            %>
                <h2> NO EXISTEN ENTRENAMIENTOS AÚN</h2>
            <%
                }
            %>
        </table>


    </body>
</html>
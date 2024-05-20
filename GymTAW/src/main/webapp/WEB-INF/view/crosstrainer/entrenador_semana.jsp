<%@ page import="es.uma.entity.DiaEntrenamiento" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer id = (Integer) request.getAttribute("idcliente");
    List<DiaEntrenamiento> diaEntrenamientos = (List<DiaEntrenamiento>) request.getAttribute("diasEntrenamientos");
%>
<html>

    <head>
        <title>Calendario Ejercicios</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    </head>
    <body>

        <h1>ENTRENAMIENTOS</h1>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Fecha</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
        <%
            if(!diaEntrenamientos.isEmpty()){

                for(DiaEntrenamiento diaEntrenamiento : diaEntrenamientos){

        %>

        <tr>
            <td><%=diaEntrenamiento.getFecha()%></td>
            <td><a href="">Editar</a></td>
            <td href="">Borrar</td>
        </tr>

        <%
                }
            }else{
        %>
        <h3>No hay ningún entrenamiento :(</h3>
        <%
            }
        %>
            <tbody>
        </table>

    <form method="post" action="/entrenamientos/nuevo-entrenamiento">
        <input hidden="hidden" name="id" value="<%=id%>">
        <button type="submit">Nuevo Entrenamiento</button>
    </form>



    </form>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    </body>
</html>



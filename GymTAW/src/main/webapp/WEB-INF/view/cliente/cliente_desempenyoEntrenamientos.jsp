<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.FeedbackEjercicioserie" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Desempeño Entrenamientos</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<div class="d-flex flex-column align-items-center">
    <h1 class="mt-3">Desempeño de los Entrenamientos</h1>

    <form method="post" action="/cliente/filtrarDesempenyoEntrenamiento" class="d-flex gap-3 mt-3">
        <input class="form-control w-100" type="date" name="fechaDesempenyoEntrenamiento">
        <button class="btn btn-primary">Filtrar</button>
    </form>

    <%
        // Obtener el mapa de entrenamientoFeedbackSeries del request
        List<ImplementacionEjercicioRutina> l = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");
        Map<ImplementacionEjercicioRutina,List<FeedbackEjercicioserie>> map = (Map<ImplementacionEjercicioRutina, List<FeedbackEjercicioserie>>) request.getAttribute("implementacionEjercicioRutinaListMap");

        if (l.isEmpty()) {
    %>
    ¡No hay entrenamientos asignados para este día!
    <%
    } else {
    %>
    <table class="table w-75">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Nombre</th>
            <th scope="col">Tipo</th>
            <th scope="col">Series</th>
            <th scope="col">Series Realizadas</th>
        </tr>
        </thead>
        <tbody>
            <%
    for(int i = 0; i< l.size(); i++){
        String tiempo = "-";
        if(l.get(i).getTiempo()!=null){
            int tiempoINT = Integer.parseInt(l.get(i).getTiempo());
            int minutos = tiempoINT/60;
            int segundos = tiempoINT%60;
            tiempo = "";
            if(minutos!=0) tiempo +=minutos + "m";
            if(segundos!=0) tiempo += segundos + "s";
        }
%>
        <tr>
            <th scope="row"><%=i+1%></th>
            <td><%=l.get(i).getEjercicio().getNombre()%></td>
            <td><%=l.get(i).getEjercicio().getTipo().getTipoDeEjercicio()%></td>
            <td><%=l.get(i).getSets()%></td>
            <td><%=l.get(i).getFeedbacks().getFirst().getSeguimientoSetsDone()%></td>
        </tr>
            <%
    }
%>
        <tbody>
    </table>
    <%
        }
    %>
</div>

</body>
</html>

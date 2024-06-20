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
    <table class="table w-100">
        <thead>
        <tr>
            <th scope="col">Nombre</th>
            <th scope="col">Tipo</th>
            <th scope="col">Series</th>
            <th scope="col">Series Realizadas</th>
            <th scope="col">Repeticiones</th>
            <th scope="col">Repeticiones Realizadas</th>
            <th scope="col">Peso</th>
            <th scope="col">Peso Realizado</th>
            <th scope="col">Tiempo</th>
            <th scope="col">Tiempo Realizado</th>
            <th scope="col">Kcal</th>
            <th scope="col">Kcal Realizados</th>
            <th scope="col">Metros</th>
            <th scope="col">Metros Realizados</th>
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
            <td><%=l.get(i).getEjercicio().getNombre()%></td>
            <td><%=l.get(i).getEjercicio().getTipo().getTipoDeEjercicio()%></td>
            <td><%=l.get(i).getSets() == null ? "-" : l.get(i).getSets()%></td>
            <td><%=l.get(i).getFeedbacks().getFirst().getSeguimientoSetsDone() == null ? "-" : l.get(i).getFeedbacks().getFirst().getSeguimientoSetsDone()%></td>
            <td><%=l.get(i).getRepeticiones() == null ? "-" : l.get(i).getRepeticiones()%></td>
            <td>
            <%
                List<FeedbackEjercicioserie> feedbackEjercicioseries = map.get(l.get(i));
                if(!(feedbackEjercicioseries.isEmpty() || l.get(i).getRepeticiones()==null)){
                    for(FeedbackEjercicioserie f: feedbackEjercicioseries){
            %>
                Ser<%=f.getSerie()%> -> Repeticiones: <%=f.getRepeticionesRealizadas() == null ? "-" : f.getRepeticionesRealizadas()%><br/>
            <%
                    }
                }else{
            %>
                -
                <%
                }
            %>
            </td>
            <td><%=l.get(i).getPeso() == null ? "-" : l.get(i).getPeso() + "kg"%></td>
            <td>
                <%
                    if(!(feedbackEjercicioseries.isEmpty() || l.get(i).getPeso()==null)){
                        for(FeedbackEjercicioserie f: feedbackEjercicioseries){
                %>
                Ser<%=f.getSerie()%> -> Peso: <%=f.getPesoRealizado() == null ? "-" : f.getPesoRealizado() + "kg"%><br/>
                <%
                    }
                }else{
                %>
                <%=l.get(i).getFeedbacks().getFirst().getSeguimientoPesoDone() == null ? "-" : l.get(i).getFeedbacks().getFirst().getSeguimientoPesoDone()%>
                <%
                    }
                %>
            </td>
            <td><%=l.get(i).getTiempo() == null ? "-" : tiempo%></td>
            <td>
                <%
                    if(!(feedbackEjercicioseries.isEmpty() || l.get(i).getTiempo()==null)){
                        for(FeedbackEjercicioserie f: feedbackEjercicioseries){
                            tiempo = "-";
                            if(f.getTiempoRealizado()!=null){
                                int tiempoINT = Integer.parseInt(f.getTiempoRealizado());
                                int minutos = tiempoINT/60;
                                int segundos = tiempoINT%60;
                                tiempo = "";
                                if(minutos!=0) tiempo +=minutos + "m";
                                if(segundos!=0) tiempo += segundos + "s";
                            }
                %>
                Ser<%=f.getSerie()%> -> Tiempo: <%=f.getTiempoRealizado() == null ? "-" : tiempo%><br/>
                <%
                    }
                }else{
                        tiempo = "-";
                        if(l.get(i).getFeedbacks().getFirst().getSeguimientoTiempoDone()!=null){
                            int tiempoINT = Integer.parseInt(l.get(i).getFeedbacks().getFirst().getSeguimientoTiempoDone());
                            int minutos = tiempoINT/60;
                            int segundos = tiempoINT%60;
                            tiempo = "";
                            if(minutos!=0) tiempo +=minutos + "m";
                            if(segundos!=0) tiempo += segundos + "s";
                        }
                %>
                <%=l.get(i).getFeedbacks().getFirst().getSeguimientoTiempoDone() == null ? "-" : tiempo%>
                <%
                    }
                %>
            </td>
            <td><%=l.get(i).getKilocalorias() == null ? "-" : l.get(i).getKilocalorias()%></td>
            <td>
                <%
                    if(!(feedbackEjercicioseries.isEmpty() || l.get(i).getKilocalorias()==null)){
                        for(FeedbackEjercicioserie f: feedbackEjercicioseries){
                %>
                Ser<%=f.getSerie()%> ->  Kcal: <%=f.getKilocaloriasRealizado() == null ? "-" : f.getKilocaloriasRealizado()%><br/>
                <%
                    }
                }else{
                %>
                <%=l.get(i).getFeedbacks().getFirst().getSeguimientoKilocaloriasDone() == null ? "-" : l.get(i).getFeedbacks().getFirst().getSeguimientoKilocaloriasDone()%>
                <%
                    }
                %>
            </td>
            <td><%=l.get(i).getMetros() == null ? "-" : l.get(i).getMetros()%></td>
            <td>
                <%
                    if(!(feedbackEjercicioseries.isEmpty() || l.get(i).getMetros()==null)){
                        for(FeedbackEjercicioserie f: feedbackEjercicioseries){
                %>
                Ser<%=f.getSerie()%> -> Metros: <%=f.getMetrosRealizado() == null ? "-" : f.getMetrosRealizado()%><br/>
                <%
                    }
                }else{
                %>
                <%=l.get(i).getFeedbacks().getFirst().getSeguimientoMetrosDone() == null ? "-" : l.get(i).getFeedbacks().getFirst().getSeguimientoMetrosDone()%>
                <%
                    }
                %>
            </td>
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

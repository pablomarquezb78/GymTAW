<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.FeedbackEjercicioserie" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="javax.management.relation.Role" %>
<%@ page import="es.uma.entity.UserRol" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LocalDate localDate = (LocalDate) request.getAttribute("fecha");
    UserRol userRol = (UserRol) request.getAttribute("rol");

    String cabecera = "../crosstrainer/cabecera_entrenador.jsp";
    String disabled = "disabled";
    String hidden = "hidden";

    if(userRol.getId() == 2) {
        cabecera = "cabecera_cliente.jsp";
        disabled = "";
        hidden = "";
    }
%>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Desempeño Entrenamientos</title>
</head>
<body>

<jsp:include page="<%=cabecera%>"/>

<div class="d-flex flex-column align-items-center">
    <h1 class="mt-3">Desempeño de los Entrenamientos</h1>

    <form method="post" action="/cliente/filtrarDesempenyoEntrenamiento" class="d-flex gap-3 mt-3">
        <input class="form-control w-100" type="date" name="fechaDesempenyoEntrenamiento" value="<%=localDate%>" <%=disabled%>>
        <button class="btn btn-primary" <%=hidden%>>Filtrar</button>
    </form>

    <%
        // Obtener el mapa de entrenamientoFeedbackSeries del request
        List<ImplementacionEjercicioRutina> l = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");
        Map<ImplementacionEjercicioRutina, List<FeedbackEjercicioserie>> map = (Map<ImplementacionEjercicioRutina, List<FeedbackEjercicioserie>>) request.getAttribute("implementacionEjercicioRutinaListMap");

        if (l == null || l.isEmpty()) {
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
    for (ImplementacionEjercicioRutina implementacion : l) {
        String tiempo = "-";
        if (implementacion.getTiempo() != null && !implementacion.getTiempo().isEmpty()) {
            int tiempoINT = Integer.parseInt(implementacion.getTiempo());
            int minutos = tiempoINT / 60;
            int segundos = tiempoINT % 60;
            tiempo = "";
            if (minutos != 0) tiempo += minutos + "m";
            if (segundos != 0) tiempo += segundos + "s";
        }
%>
        <tr>
            <td><%= implementacion.getEjercicio().getNombre() %></td>
            <td><%= implementacion.getEjercicio().getTipo().getTipoDeEjercicio() %></td>
            <td><%= implementacion.getSets() == null ? "-" : implementacion.getSets() %></td>
            <td><%= !implementacion.getFeedbacks().isEmpty() && implementacion.getFeedbacks().get(0).getSeguimientoSetsDone() != null ? implementacion.getFeedbacks().get(0).getSeguimientoSetsDone() : "-" %></td>
            <td><%= implementacion.getRepeticiones() == null ? "-" : implementacion.getRepeticiones() %></td>
            <td>
                <%
                    List<FeedbackEjercicioserie> feedbackEjercicioseries = map.get(implementacion);
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && implementacion.getRepeticiones() != null) {
                        for (FeedbackEjercicioserie f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Repeticiones: <%= f.getRepeticionesRealizadas() == null ? "-" : f.getRepeticionesRealizadas() %><br/>
                <%
                    }
                } else {
                %>
                -
                <%
                    }
                %>
            </td>
            <td><%= implementacion.getPeso() == null ? "-" : implementacion.getPeso() + "kg" %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && implementacion.getPeso() != null) {
                        for (FeedbackEjercicioserie f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Peso: <%= f.getPesoRealizado() == null ? "-" : f.getPesoRealizado() + "kg" %><br/>
                <%
                    }
                } else {
                %>
                <%= !implementacion.getFeedbacks().isEmpty() && implementacion.getFeedbacks().get(0).getSeguimientoPesoDone() != null ? implementacion.getFeedbacks().get(0).getSeguimientoPesoDone() : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= implementacion.getTiempo() == null ? "-" : tiempo %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && implementacion.getTiempo() != null) {
                        for (FeedbackEjercicioserie f : feedbackEjercicioseries) {
                            tiempo = "-";
                            if (f.getTiempoRealizado() != null && !f.getTiempoRealizado().isEmpty()) {
                                int tiempoINT = Integer.parseInt(f.getTiempoRealizado());
                                int minutos = tiempoINT / 60;
                                int segundos = tiempoINT % 60;
                                tiempo = "";
                                if (minutos != 0) tiempo += minutos + "m";
                                if (segundos != 0) tiempo += segundos + "s";
                            }
                %>
                Ser<%= f.getSerie() %> -> Tiempo: <%= f.getTiempoRealizado() == null ? "-" : tiempo %><br/>
                <%
                    }
                } else {
                    tiempo = "-";
                    if (!implementacion.getFeedbacks().isEmpty() && implementacion.getFeedbacks().get(0).getSeguimientoTiempoDone() != null && !implementacion.getFeedbacks().get(0).getSeguimientoTiempoDone().isEmpty()) {
                        int tiempoINT = Integer.parseInt(implementacion.getFeedbacks().get(0).getSeguimientoTiempoDone());
                        int minutos = tiempoINT / 60;
                        int segundos = tiempoINT % 60;
                        tiempo = "";
                        if (minutos != 0) tiempo += minutos + "m";
                        if (segundos != 0) tiempo += segundos + "s";
                    }
                %>
                <%= !implementacion.getFeedbacks().isEmpty() && implementacion.getFeedbacks().get(0).getSeguimientoTiempoDone() != null ? tiempo : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= implementacion.getKilocalorias() == null ? "-" : implementacion.getKilocalorias() %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && implementacion.getKilocalorias() != null) {
                        for (FeedbackEjercicioserie f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> ->  Kcal: <%= f.getKilocaloriasRealizado() == null ? "-" : f.getKilocaloriasRealizado() %><br/>
                <%
                    }
                } else {
                %>
                <%= !implementacion.getFeedbacks().isEmpty() && implementacion.getFeedbacks().get(0).getSeguimientoKilocaloriasDone() != null ? implementacion.getFeedbacks().get(0).getSeguimientoKilocaloriasDone() : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= implementacion.getMetros() == null ? "-" : implementacion.getMetros() %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && implementacion.getMetros() != null) {
                        for (FeedbackEjercicioserie f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Metros: <%= f.getMetrosRealizado() == null ? "-" : f.getMetrosRealizado() %><br/>
                <%
                    }
                } else {
                %>
                <%= !implementacion.getFeedbacks().isEmpty() && implementacion.getFeedbacks().get(0).getSeguimientoMetrosDone() != null ? implementacion.getFeedbacks().get(0).getSeguimientoMetrosDone() : "-" %>
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

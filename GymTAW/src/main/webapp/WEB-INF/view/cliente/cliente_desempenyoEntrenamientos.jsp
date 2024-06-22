<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="es.uma.dto.ImplementacionEjercicioRutinaDTO" %>
<%@ page import="es.uma.dto.UserRolDTO" %>
<%@ page import="es.uma.dto.FeedbackEjercicioDTO" %>
<%@ page import="es.uma.dto.FeedbackEjercicioserieDTO" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.antlr.v4.runtime.misc.Pair" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LocalDate localDate = (LocalDate) request.getAttribute("fecha");
    UserRolDTO userRol = (UserRolDTO) request.getAttribute("rol");

    String cabecera = "../crosstrainer/cabecera_entrenador.jsp";
    String disabled = "disabled";
    String hidden = "hidden";

    if (userRol != null && userRol.getId() == 2) {
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

<jsp:include page="<%= cabecera %>"/>

<div class="d-flex flex-column align-items-center">
    <h1 class="mt-3">Desempeño de los Entrenamientos</h1>

    <form method="post" action="/cliente/filtrarDesempenyoEntrenamiento" class="d-flex gap-3 mt-3">
        <input class="form-control w-100" type="date" name="fechaDesempenyoEntrenamiento" value="<%= localDate %>" <%= disabled %>>
        <button class="btn btn-primary" <%= hidden %>>Filtrar</button>
    </form>

    <%
        List<Pair<ImplementacionEjercicioRutinaDTO, FeedbackEjercicioDTO>> listaPares = (List<Pair<ImplementacionEjercicioRutinaDTO, FeedbackEjercicioDTO>>) request.getAttribute("listaPares");
        Map<ImplementacionEjercicioRutinaDTO, List<FeedbackEjercicioserieDTO>> map = (Map<ImplementacionEjercicioRutinaDTO, List<FeedbackEjercicioserieDTO>>) request.getAttribute("implementacionEjercicioRutinaListMap");

        if (listaPares == null || listaPares.isEmpty()) {
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
            for (Pair<ImplementacionEjercicioRutinaDTO, FeedbackEjercicioDTO> par : listaPares) {
                ImplementacionEjercicioRutinaDTO implementacion = par.a;
                FeedbackEjercicioDTO feedback = par.b;
                String setsDone = feedback == null ? null : feedback.getSeguimientoSetsDone();
                String pesoDone = feedback == null ? null : feedback.getSeguimientoPesoDone();
                String kcalDone = feedback == null ? null : feedback.getSeguimientoKilocaloriasDone();
                String tiempoDone = feedback == null ? null : feedback.getSeguimientoTiempoDone();
                String metrosDone = feedback == null ? null : feedback.getSeguimientoMetrosDone();

                String tiempo = "-";
                if (!implementacion.getTiempo().isEmpty()) {
                    int tiempoINT = Integer.parseInt(implementacion.getTiempo());
                    int minutos = tiempoINT / 60;
                    int segundos = tiempoINT % 60;
                    tiempo = "";
                    if (minutos != 0) tiempo += minutos + "m";
                    if (segundos != 0) tiempo += segundos + "s";
                }

                List<FeedbackEjercicioserieDTO> feedbackEjercicioseries = map.get(implementacion);
        %>
        <tr>
            <td><%= implementacion.getEjercicio().getNombre() %></td>
            <td><%= implementacion.getEjercicio().getTipo().getTipoDeEjercicio() %></td>
            <td><%= implementacion.getSets().isEmpty() ? "-" : implementacion.getSets() %></td>
            <td><%= setsDone == null ? "-" : feedback.getSeguimientoSetsDone()%></td>
            <td><%= implementacion.getRepeticiones().isEmpty() ? "-" : implementacion.getRepeticiones() %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && implementacion.getRepeticiones() != null) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
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
            <td><%= implementacion.getPeso().isEmpty() ? "-" : implementacion.getPeso() + "kg" %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !implementacion.getPeso().isEmpty()) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Peso: <%= f.getPesoRealizado()==null ? "-" : f.getPesoRealizado() + "kg" %><br/>
                <%
                    }
                } else {
                %>
                <%= pesoDone != null ? feedback.getSeguimientoPesoDone() : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= implementacion.getTiempo().isEmpty() ? "-" : tiempo %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !implementacion.getTiempo().isEmpty()) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
                            tiempo = "-";
                            if (f.getTiempoRealizado() != null) {
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
                    if (tiempoDone != null) {
                        int tiempoINT = Integer.parseInt(feedback.getSeguimientoTiempoDone());
                        int minutos = tiempoINT / 60;
                        int segundos = tiempoINT % 60;
                        tiempo = "";
                        if (minutos != 0) tiempo += minutos + "m";
                        if (segundos != 0) tiempo += segundos + "s";
                    }
                %>
                <%= tiempoDone != null ? tiempo : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= implementacion.getKilocalorias().isEmpty() ? "-" : implementacion.getKilocalorias() %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !implementacion.getKilocalorias().isEmpty()) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Kcal: <%= f.getKilocaloriasRealizado() == null ? "-" : f.getKilocaloriasRealizado() %><br/>
                <%
                    }
                } else {
                %>
                <%= kcalDone != null ? feedback.getSeguimientoKilocaloriasDone() : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= implementacion.getMetros().isEmpty() ? "-" : implementacion.getMetros() %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !implementacion.getMetros().isEmpty()) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Metros: <%= f.getMetrosRealizado() == null ? "-" : f.getMetrosRealizado() %><br/>
                <%
                    }
                } else {
                %>
                <%= metrosDone != null ? feedback.getSeguimientoMetrosDone() : "-" %>
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

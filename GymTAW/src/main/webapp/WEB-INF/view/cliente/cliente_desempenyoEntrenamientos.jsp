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
        List<Pair<ImplementacionEjercicioRutinaDTO,FeedbackEjercicioDTO>> listaPares = (List<Pair<ImplementacionEjercicioRutinaDTO,FeedbackEjercicioDTO>>) request.getAttribute("listaPares");
        Map<ImplementacionEjercicioRutinaDTO,List<FeedbackEjercicioserieDTO>> map = (Map<ImplementacionEjercicioRutinaDTO, List<FeedbackEjercicioserieDTO>>) request.getAttribute("implementacionEjercicioRutinaListMap");

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
    for (Pair<ImplementacionEjercicioRutinaDTO,FeedbackEjercicioDTO> par : listaPares) {
        String tiempo = "-";
        if (par.a.getTiempo() != null && !par.a.getTiempo().isEmpty()) {
            int tiempoINT = Integer.parseInt(par.a.getTiempo());
            int minutos = tiempoINT / 60;
            int segundos = tiempoINT % 60;
            tiempo = "";
            if (minutos != 0) tiempo += minutos + "m";
            if (segundos != 0) tiempo += segundos + "s";
        }
%>
        <tr>
            <td><%= par.a.getEjercicio().getNombre() %></td>
            <td><%= par.a.getEjercicio().getTipo().getTipoDeEjercicio() %></td>
            <td><%= par.a.getSets() == null ? "-" : par.a.getSets() %></td>
            <td><%= par.b.getSeguimientoSetsDone() != null ? par.b.getSeguimientoSetsDone() : "-" %></td>
            <td><%= par.a.getRepeticiones() == null ? "-" : par.a.getRepeticiones() %></td>
            <td>
                <%
                    List<FeedbackEjercicioserieDTO> feedbackEjercicioseries = map.get(par.a);
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && par.a.getRepeticiones() != null) {
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
            <td><%= par.a.getPeso() == null ? "-" : par.a.getPeso() + "kg" %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && par.a.getPeso() != null) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Peso: <%= f.getPesoRealizado() == null ? "-" : f.getPesoRealizado() + "kg" %><br/>
                <%
                    }
                } else {
                %>
                <%= par.b.getSeguimientoPesoDone() != null ? par.b.getSeguimientoPesoDone() : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= par.a.getTiempo() == null ? "-" : tiempo %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && par.a.getTiempo() != null) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
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
                    if (par.b.getSeguimientoTiempoDone() != null && !par.b.getSeguimientoTiempoDone().isEmpty()) {
                        int tiempoINT = Integer.parseInt(par.b.getSeguimientoTiempoDone());
                        int minutos = tiempoINT / 60;
                        int segundos = tiempoINT % 60;
                        tiempo = "";
                        if (minutos != 0) tiempo += minutos + "m";
                        if (segundos != 0) tiempo += segundos + "s";
                    }
                %>
                <%= par.b.getSeguimientoTiempoDone() != null ? tiempo : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= par.a.getKilocalorias() == null ? "-" : par.a.getKilocalorias() %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && par.a.getKilocalorias() != null) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> ->  Kcal: <%= f.getKilocaloriasRealizado() == null ? "-" : f.getKilocaloriasRealizado() %><br/>
                <%
                    }
                } else {
                %>
                <%= par.b.getSeguimientoKilocaloriasDone() != null ? par.b.getSeguimientoKilocaloriasDone() : "-" %>
                <%
                    }
                %>
            </td>
            <td><%= par.a.getMetros() == null ? "-" : par.a.getMetros() %></td>
            <td>
                <%
                    if (feedbackEjercicioseries != null && !feedbackEjercicioseries.isEmpty() && par.a.getMetros() != null) {
                        for (FeedbackEjercicioserieDTO f : feedbackEjercicioseries) {
                %>
                Ser<%= f.getSerie() %> -> Metros: <%= f.getMetrosRealizado() == null ? "-" : f.getMetrosRealizado() %><br/>
                <%
                    }
                } else {
                %>
                <%= par.b.getSeguimientoMetrosDone() != null ? par.b.getSeguimientoMetrosDone() : "-" %>
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

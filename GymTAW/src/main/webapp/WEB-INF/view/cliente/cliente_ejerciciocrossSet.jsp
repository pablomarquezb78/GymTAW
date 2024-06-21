<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="es.uma.entity.FeedbackEjercicioserie"%>
<%@ page import="es.uma.ui.FeedbackSerieForm" %>
<%@ page import="es.uma.entity.FeedbackEjercicio" %>
<%@ page import="es.uma.dto.ImplementacionEjercicioRutinaDTO" %>
<%@ page import="es.uma.dto.FeedbackEjercicioDTO" %>
<%@ page import="es.uma.dto.FeedbackEjercicioserieDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Márquez Benítez
  Date: 21/05/2024
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    ImplementacionEjercicioRutinaDTO implementacion = (ImplementacionEjercicioRutinaDTO) request.getAttribute("implementacion");
    FeedbackEjercicioDTO feedbackEjercicio = (FeedbackEjercicioDTO) request.getAttribute("feedback");
    FeedbackEjercicioserieDTO feedbackEjercicioSerie = (FeedbackEjercicioserieDTO)request.getAttribute("feedbackSerie");
    FeedbackSerieForm feedbackSerieForm = (FeedbackSerieForm) request.getAttribute("feedbackSerieForm");

    Byte realizado = feedbackEjercicio.getRealizado();

    //PARAMETROS FEEDBACK
    String setsRealizadasSTR = feedbackEjercicio.getSeguimientoSetsDone();
    Integer setsRealizadas = 0;
    if(setsRealizadasSTR!=null) setsRealizadas = Integer.parseInt(setsRealizadasSTR);

    //PARAMETROS FEEDBACK SERIE
    String serieSeleccionadaSTR = "";
    //Si hay feedback obtenemos sus datos
    if(feedbackEjercicioSerie!=null) {

        serieSeleccionadaSTR = feedbackEjercicioSerie.getSerie();
    }

    Integer serieSeleccionada = 1;
    if(!serieSeleccionadaSTR.isEmpty()) serieSeleccionada = Integer.parseInt(serieSeleccionadaSTR);

    //COMPROBACIONES PARAMETROS
    if(realizado == null) realizado = 0;
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Ejercicio CrossFit/Sets</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>
<jsp:include page="cliente_ejercicio.jsp"/>

<div class="d-flex justify-content-center">
    <div class="bg-secondary w-25 m-3 p-3 rounded h-25">
        <h3>Feedback:</h3>
        <form method="post" action="/cliente/guardarFeedbackEjercicio">
            <div class="d-flex gap-2">
                <span class="text-white">Realizado:</span>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" name="realizado" value="1" <%= realizado == 1 ? "checked" : "" %>>
                </div>
                <span class="text-white">Series Realizadas:</span>
                <input type="number" name="seriesRealizadas" value="<%=setsRealizadas%>" <%=realizado==0 ?  "disabled" : ""%> class="w-25">
            </div>

            <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
            <input type="hidden" name="feedbackEjercicio" value="<%=feedbackEjercicio.getId()%>">
            <button class="btn btn-success mt-3">Guardar</button>
        </form>
    </div>

    <div style=" <%= (realizado == 0 || setsRealizadas == 0) ? "display:none" : "" %>" class="bg-secondary m-3 w-25 rounded my-3 p-3">

        <h3 class="text-center pb-2">Selección serie:</h3>
        <form method="post" action="/cliente/seleccionarSerie">
            <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
            <div class="row">
                <div class="col-md-6">
                    <span class="text-white">Selección serie:</span>
                    <select name="set" class="form-select w-50">
                        <% for (int i = 1; i<=setsRealizadas; i++){ %>
                        <option value="<%=i%>" <%= serieSeleccionada.equals(i)? "selected" : "" %>><%=i%></option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-6">
                    <button class="btn btn-success mt-4">Seleccionar</button>
                </div>
            </div>
        </form>
        <%
            boolean hayRepeticiones = implementacion.getRepeticiones()!=null;
            boolean hayPeso = implementacion.getPeso()!=null;
            boolean hayTiempo = implementacion.getTiempo()!=null;
            boolean hayKilocalorias = implementacion.getKilocalorias()!=null;
            boolean hayMetros = implementacion.getMetros()!=null;
        %>
        <h3 class="text-center py-2">Feedback serie:</h3>
        <form:form method="post" action="/cliente/guardarFeedbackSerieCross" modelAttribute="feedbackSerieForm">
            <span class="text-white">Repeticiones:</span><br/>
            <form:input type="number" disabled="<%=!hayRepeticiones%>" path="repeticionesRealizadas" size="5" required="<%=hayRepeticiones%>"></form:input><br/>
            <span class="text-white">Peso(Kg):</span><br/>
            <form:input type="number" step="0.01" disabled="<%=!hayPeso%>" path="pesoRealizado" size="5" required="<%=hayPeso%>"></form:input><br/>
            <div class="d-flex align-items-center justify-content-center">
                <div>
                    <span class="text-white">Minutos:</span><br/>
                    <form:input cssClass="w-50" type="number" disabled="<%=!hayTiempo%>" path="minutosRealizados" size="5" required="<%=hayTiempo%>"></form:input>
                </div>
                <div>
                    <span class="text-white">Segundos:</span><br/>
                    <form:input cssClass="w-50" type="number" disabled="<%=!hayTiempo%>" path="segundosRealizados" size="5" required="<%=hayTiempo%>"></form:input>
                </div>
            </div>
            <span class="text-white">Kilocalorias:</span><br/>
            <form:input type="number" disabled="<%=!hayKilocalorias%>" path="kilocaloriasRealizado" size="5" required="<%=hayKilocalorias%>"></form:input><br/>
            <span class="text-white">Metros:</span><br/>
            <form:input type="number" disabled="<%=!hayMetros%>" path="metrosRealizado" size="5" required="<%=hayMetros%>"></form:input>

            <form:hidden path="implementacionId"></form:hidden>
            <form:hidden path="serieSeleccionada"></form:hidden>
            <form:hidden path="feedbackEjercicio"></form:hidden><br/>
            <form:button class="btn btn-success mt-3">Guardar</form:button>
        </form:form>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
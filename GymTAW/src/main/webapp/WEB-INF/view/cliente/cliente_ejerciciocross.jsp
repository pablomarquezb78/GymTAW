<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="es.uma.entity.FeedbackEjercicioserie"%>
<%@ page import="es.uma.ui.FeedbackSerieForm" %>
<%@ page import="es.uma.entity.FeedbackEjercicio" %>
<%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 22/05/2024
  Time: 0:23
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    ImplementacionEjercicioRutina implementacion = (ImplementacionEjercicioRutina) request.getAttribute("implementacion");
    FeedbackEjercicio feedbackEjercicio = (FeedbackEjercicio) request.getAttribute("feedback");
    FeedbackSerieForm feedbackSerieForm = (FeedbackSerieForm) request.getAttribute("feedbackSerieForm");

    Byte realizado = feedbackEjercicio.getRealizado();

    //COMPROBACIONES PARAMETROS
    if(realizado == null) realizado = 0;
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>
<jsp:include page="cliente_ejercicio.jsp"/>

<div class="bg-secondary w-25 m-3 p-3 rounded">
    <h3>Feedback Ejercicio:</h3>
    <form method="post" action="/cliente/guardarFeedbackEjercicio">
        <div class="d-flex gap-2">
            <span class="text-white">Realizado:</span>
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" name="realizado" value="1" <%= realizado == 1 ? "checked" : "" %>>
            </div>
        </div>

        <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
        <input type="hidden" name="feedbackEjercicio" value="<%=feedbackEjercicio.getId()%>">
        <button class="btn btn-success mt-3">Guardar</button>
    </form>
</div>

<div style="<%=realizado == 0 ? "display:none" : ""%>" class="bg-secondary m-3 w-25 rounded p-3 position-absolute top-50 start-50 translate-middle">
    <%
        boolean hayPeso = implementacion.getPeso()!=null;
        boolean hayTiempo = implementacion.getTiempo()!=null;
        boolean hayKilocalorias = implementacion.getKilocalorias()!=null;
        boolean hayMetros = implementacion.getMetros()!=null;
    %>
    <h5>Feedback Ejercicio Realizado:</h5>
    <form:form method="post" action="/cliente/guardarFeedbackCross" modelAttribute="feedbackSerieForm">
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
        <form:hidden path="feedbackEjercicio"></form:hidden><br/>
        <form:button class="btn btn-success mt-3">Guardar</form:button>
    </form:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
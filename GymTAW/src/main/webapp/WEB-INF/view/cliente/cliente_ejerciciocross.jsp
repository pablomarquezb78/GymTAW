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

<h5>Feedback:</h5>

<form method="post" action="/cliente/guardarFeedbackEjercicio">
    Realizado:
    <select name="realizado">
        <option value="1" <%= realizado == 1 ? "selected" : "" %>>Si</option>
        <option value="0" <%= realizado == 0 ? "selected" : "" %>>No</option>
    </select>
    <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
    <input type="hidden" name="feedbackEjercicio" value="<%=feedbackEjercicio.getId()%>">
    <button>Guardar</button>
</form>

<div style="<%=realizado == 0 ? "display:none" : ""%>">
    <%
        boolean hayPeso = implementacion.getPeso()!=null;
        boolean hayTiempo = implementacion.getTiempo()!=null;
        boolean hayKilocalorias = implementacion.getKilocalorias()!=null;
        boolean hayMetros = implementacion.getMetros()!=null;
    %>
    <h5>Feedback Ejercicio:</h5>
    <form:form method="post" action="/cliente/guardarFeedbackCross" modelAttribute="feedbackSerieForm">
        Peso Realizado(Kg):
        <form:input type="number" step="0.01" disabled="<%=!hayPeso%>" path="pesoRealizado" size="5" required="<%=hayPeso%>"></form:input><br/>
        Minutos Realizados:
        <form:input type="number" disabled="<%=!hayTiempo%>" path="minutosRealizados" size="5" required="<%=hayTiempo%>"></form:input><br/>
        Segundos Realizados:
        <form:input type="number" disabled="<%=!hayTiempo%>" path="segundosRealizados" size="5" required="<%=hayTiempo%>"></form:input><br/>
        Kilocalorias Realizadas:
        <form:input type="number" disabled="<%=!hayKilocalorias%>" path="kilocaloriasRealizado" size="5" required="<%=hayKilocalorias%>"></form:input><br/>
        Metros Realizados:
        <form:input type="number" disabled="<%=!hayMetros%>" path="metrosRealizado" size="5" required="<%=hayMetros%>"></form:input>

        <form:hidden path="implementacionId"></form:hidden>
        <form:hidden path="feedbackEjercicio"></form:hidden><br/>
        <form:button>Guardar</form:button>
    </form:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
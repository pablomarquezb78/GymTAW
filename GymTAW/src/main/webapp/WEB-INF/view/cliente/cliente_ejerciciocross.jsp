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

    //PARAMETROS FEEDBACK
    String setsRealizadasSTR = feedbackEjercicio.getSeguimientoSetsDone();
    Integer setsRealizadas = 0;
    if(setsRealizadasSTR!=null) setsRealizadas = Integer.parseInt(setsRealizadasSTR);

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
    <select name="realizado">
        <option value="1" <%= realizado == 1 ? "selected" : "" %>>Si</option>
        <option value="0" <%= realizado == 0 ? "selected" : "" %>>No</option>
    </select>
    Series Realizadas:
    <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
    <input type="hidden" name="feedbackEjercicio" value="<%=feedbackEjercicio.getId()%>">
    <button>Guardar</button>
</form>

<div style="<%=realizado == 0 ? "display:none" : ""%>">
    <h5>Feedback Ejercicio:</h5>
    <form:form method="post" action="/cliente/guardarFeedbackCross" modelAttribute="feedbackSerieForm">
        Repeticiones Realizadas:
        <form:input disabled="<%=implementacion.getRepeticiones()!= null ? false : true%>" path="repeticionesRealizadas" size="5"></form:input><br/>
        Peso(Kg):
        <form:input disabled="<%=implementacion.getPeso()!= null ? false : true%>" path="pesoRealizado" size="5"></form:input><br/>
        Minutos Realizados:
        <form:input type="number" disabled="<%=implementacion.getTiempo()!= null ? false : true%>" path="minutosRealizados" size="5"></form:input><br/>
        Segundos Realizados:
        <form:input type="number" disabled="<%=implementacion.getTiempo()!= null ? false : true%>" path="segundosRealizados" size="5"></form:input><br/>
        Kilocalorias Realizadas:
        <form:input disabled="<%=implementacion.getKilocalorias()!= null ? false : true%>" path="kilocaloriasRealizado" size="5"></form:input><br/>
        Metros Realizados:
        <form:input disabled="<%=implementacion.getMetros()!= null ? false : true%>" path="metrosRealizado" size="5"></form:input>

        <form:hidden path="implementacionId"></form:hidden>
        <form:hidden path="feedbackEjercicio"></form:hidden><br/>
        <form:button>Guardar</form:button>
    </form:form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
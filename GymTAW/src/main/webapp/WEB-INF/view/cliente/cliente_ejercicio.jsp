<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="es.uma.entity.FeedbackEjercicio" %><%--
  Created by IntelliJ IDEA.
  User: Usuario
  Date: 03/05/2024
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    ImplementacionEjercicioRutina implementacion = (ImplementacionEjercicioRutina) request.getAttribute("implementacion");
    String descripcion = implementacion.getEjercicio().getDescripcion();
    String enlaceVideo = implementacion.getEjercicio().getEnlaceVideo();
    String tipoEjercicio = implementacion.getEjercicio().getTipo().getTipoDeEjercicio();
    int sets = Integer.parseInt(implementacion.getSets());

    //PARAMETROS FEEDBACK
    String setsRealizadas = implementacion.getSeguimientoSetsDone();
    Byte realizado = implementacion.getRealizado();

    //PARAMETROS FEEDBACK SERIE
    FeedbackEjercicio feedbackEjercicio = (FeedbackEjercicio)request.getAttribute("feedback");
    String repeticionesRealizadas = "";
    String pesoRealizado = "";
    Integer serieSeleccionada = 1;
    if (feedbackEjercicio != null) {
        repeticionesRealizadas = feedbackEjercicio.getRepeticionesRealizadas();
        pesoRealizado = feedbackEjercicio.getPesoRealizado();
        serieSeleccionada = Integer.parseInt(feedbackEjercicio.getSerie());
    }

    //COMPROBACIONES PARAMETROS
    if(descripcion == null) descripcion="";
    if(enlaceVideo == null) enlaceVideo="";
    if(tipoEjercicio == null) tipoEjercicio="";
    if(setsRealizadas == null) setsRealizadas = "";
    if(realizado == null) realizado = 0;
    if(pesoRealizado == null) pesoRealizado = "";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<h5>Tipo: <%=tipoEjercicio%></h5><br/>
<h5>Descripción:</h5>
<p><%=descripcion%></p>
<h5>URL: <%=enlaceVideo%></h5>

<form method="post" action="/cliente/guardarFeedbackEjercicio">
    <select name="realizado">
        <option value="1" <%= realizado == 1 ? "selected" : "" %>>Si</option>
        <option value="0" <%= realizado == 0 ? "selected" : "" %>>No</option>
    </select>
    Series Realizadas:
    <input type="text" name="seriesRealizadas" value="<%=setsRealizadas%>">
    <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
    <button>Guardar</button>
</form>

<form method="post" action="/cliente/seleccionarSerie">
Serie:
<select name="set">
<%
for (int i = 1; i<=sets; i++){

%>
    <option value="<%=i%>" <%= serieSeleccionada.equals(i)? "selected" : "" %>><%=i%></option>
<%
    }
%>
</select>
<input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
<button>Seleccionar</button>
</form>

<form method="post" action="/cliente/guardarFeedbackSerie">
    Repeticiones Realizadas:
    <input type="text" name="repeticionesRealizadas" value="<%=repeticionesRealizadas%>">
    Peso(Kg):
    <input type="text" name="pesoRealizado" value="<%=pesoRealizado%>">
    <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
    <input type="hidden" name="serieSeleccionada" value="<%=serieSeleccionada%>">
    <button>Guardar</button>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

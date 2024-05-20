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

    //PARAMETROS FEEDBACK
    String setsRealizadasSTR = implementacion.getSeguimientoSetsDone();
    Integer setsRealizadas = 0;
    if(setsRealizadasSTR!=null) setsRealizadas = Integer.parseInt(setsRealizadasSTR);

    Byte realizado = implementacion.getRealizado();

    //PARAMETROS FEEDBACK SERIE
    FeedbackEjercicio feedbackEjercicio = (FeedbackEjercicio)request.getAttribute("feedback");
    String repeticionesRealizadas = "";
    String pesoRealizado = "";
    String serieSeleccionadaSTR = "";
    //Si hay feedback obtenemos sus datos
    if(feedbackEjercicio!=null) {
        repeticionesRealizadas = feedbackEjercicio.getRepeticionesRealizadas();
        pesoRealizado = feedbackEjercicio.getPesoRealizado();
        serieSeleccionadaSTR = feedbackEjercicio.getSerie();
    }

    Integer serieSeleccionada = 1;
    if(!serieSeleccionadaSTR.equals("")) serieSeleccionada = Integer.parseInt(serieSeleccionadaSTR);

    //COMPROBACIONES PARAMETROS
    if(descripcion == null) descripcion="";
    if(enlaceVideo == null) enlaceVideo="";
    if(tipoEjercicio == null) tipoEjercicio="";
    if(realizado == null) realizado = 0;
    if(pesoRealizado == null) pesoRealizado = "";
    if(repeticionesRealizadas == null) repeticionesRealizadas = "";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Nombre</th>
            <th scope="col">Series</th>
            <th scope="col">Repeticiones</th>
            <th scope="col">Peso</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th scope="row">1</th>
            <td><%=implementacion.getEjercicio().getNombre()%></td>
            <td><%=implementacion.getSets()%></td>
            <td><%=implementacion.getRepeticiones()%></td>
            <td><%=implementacion.getPeso()%></td>
        </tr>
    <tbody>
</table>

<h5>Tipo: <%=tipoEjercicio%></h5><br/>
<h5>Descripción:</h5>
<p><%=descripcion%></p><br/>
<h5>URL: <%=enlaceVideo%></h5>

<br/>
<h5>Feedback:</h5>

<form method="post" action="/cliente/guardarFeedbackEjercicio">
    <select name="realizado">
        <option value="1" <%= realizado == 1 ? "selected" : "" %>>Si</option>
        <option value="0" <%= realizado == 0 ? "selected" : "" %>>No</option>
    </select>
    Series Realizadas:
    <input type="number" name="seriesRealizadas" value="<%=setsRealizadas%>">
    <input type="hidden" name="implementacion" value="<%=implementacion.getId()%>">
    <button>Guardar</button>
</form>

<div style=" <%= (realizado == 0 || setsRealizadas == 0) ? "display:none" : "" %>">

    <form method="post" action="/cliente/seleccionarSerie">
        Serie:
        <select name="set">
            <%
                for (int i = 1; i<=setsRealizadas; i++){
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

</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

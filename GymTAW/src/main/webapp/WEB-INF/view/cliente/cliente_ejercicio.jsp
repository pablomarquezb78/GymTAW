
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Márquez Benítez
  Date: 21/05/2024
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    ImplementacionEjercicioRutina implementacion = (ImplementacionEjercicioRutina) request.getAttribute("implementacion");
    String descripcion = implementacion.getEjercicio().getDescripcion();
    String enlaceVideo = implementacion.getEjercicio().getEnlaceVideo();
    String tipoEjercicio = implementacion.getEjercicio().getTipo().getTipoDeEjercicio();

    //COMPROBACIONES PARAMETROS
    if(descripcion == null) descripcion="";
    if(enlaceVideo == null) enlaceVideo="";
    if(tipoEjercicio == null) tipoEjercicio="";

    String tiempo = "-";
    if(implementacion.getTiempo()!=null){
        int tiempoINT = Integer.parseInt(implementacion.getTiempo());
        int minutos = tiempoINT/60;
        int segundos = tiempoINT%60;
        tiempo = "";
        if(minutos!=0) tiempo +=minutos + "m";
        if(segundos!=0) tiempo += segundos + "s";
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Nombre</th>
        <th scope="col">Tipo</th>
        <th scope="col">Series</th>
        <th scope="col">Repeticiones</th>
        <th scope="col">Peso</th>
        <th scope="col">Tiempo</th>
        <th scope="col">Kilocalorias</th>
        <th scope="col">Metros</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">1</th>
        <td><%=implementacion.getEjercicio().getNombre()%></td>
        <td><%=implementacion.getEjercicio().getTipo().getTipoDeEjercicio()%></td>
        <td><%=implementacion.getSets()%></td>
        <td><%=implementacion.getRepeticiones() != null ? implementacion.getRepeticiones() : "-"%></td>
        <td><%=implementacion.getPeso() != null ? implementacion.getPeso() : "-"%></td>
        <td><%=tiempo%></td>
        <td><%=implementacion.getKilocalorias() != null ? implementacion.getKilocalorias() : "-"%></td>
        <td><%=implementacion.getMetros() != null ? implementacion.getMetros() : "-"%></td>
    </tr>
    <tbody>
</table>

<h5>Tipo: <%=tipoEjercicio%></h5><br/>
<h5>Descripción:</h5>
<p><%=descripcion%></p><br/>
<h5>URL: <%=enlaceVideo%></h5>

<br/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

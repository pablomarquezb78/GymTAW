
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="es.uma.dto.ImplementacionEjercicioRutinaDTO" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Márquez Benítez
  Date: 21/05/2024
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    ImplementacionEjercicioRutinaDTO implementacion = (ImplementacionEjercicioRutinaDTO) request.getAttribute("implementacion");
    String descripcion = implementacion.getEjercicio().getDescripcion();
    String enlaceVideo = implementacion.getEjercicio().getEnlaceVideo();

    //COMPROBACIONES PARAMETROS
    if(descripcion == null) descripcion="";
    if(enlaceVideo == null) enlaceVideo="";

    String tiempo = "-";
    if(implementacion.getTiempo()!=null && !implementacion.getTiempo().isEmpty()){
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
    <title>Ejercicio</title>
</head>
<body>
<h1 class="text-center">Detalles del entrenamiento:</h1>

<table class="table w-75 mx-auto">
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
        <td><%=!implementacion.getSets().isEmpty() ? implementacion.getSets() : "-"%></td>
        <td><%=!implementacion.getRepeticiones().isEmpty() ? implementacion.getRepeticiones() : "-"%></td>
        <td><%=!implementacion.getPeso().isEmpty() ? implementacion.getPeso() : "-"%></td>
        <td><%=tiempo%></td>
        <td><%=!implementacion.getKilocalorias().isEmpty() ? implementacion.getKilocalorias() : "-"%></td>
        <td><%=!implementacion.getMetros().isEmpty() ? implementacion.getMetros() : "-"%></td>
    </tr>
    <tbody>
</table>

<div class="bg-secondary w-25 m-3 p-3 rounded mx-auto">
    <h3>Descripción:</h3>
    <p class="text-white"><%=descripcion%></p><br/>
    <h3>URL:</h3>
    <a class="text-white"><%=enlaceVideo%></a>
</div>

<br/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>

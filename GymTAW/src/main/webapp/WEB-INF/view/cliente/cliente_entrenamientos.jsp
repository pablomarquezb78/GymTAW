<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Márquez Benítez
  Date: 29/04/2024
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    List<ImplementacionEjercicioRutina> l = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");

    String filtroDia = request.getParameter("filtroDia");

    //COMPROBACIONES PARAMETROS
    if (filtroDia == null) filtroDia="1"; //Por defecto se selecciona el dia actual en el sistema (lunes en nuestro caso)
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<form method="post" action="/cliente/filtrar">
    <div class="d-flex w-25 m-3 gap-2">
        <select name="filtroDia" class="form-select w-25">
            <option value="1" <%= "1".equals(filtroDia) ? "selected" : "" %>>Lunes</option>
            <option value="2" <%= "2".equals(filtroDia) ? "selected" : "" %>>Martes</option>
            <option value="3" <%= "3".equals(filtroDia) ? "selected" : "" %>>Miércoles</option>
            <option value="4" <%= "4".equals(filtroDia) ? "selected" : "" %>>Jueves</option>
            <option value="5" <%= "5".equals(filtroDia) ? "selected" : "" %>>Viernes</option>
            <option value="6" <%= "6".equals(filtroDia) ? "selected" : "" %>>Sábado</option>
            <option value="7" <%= "7".equals(filtroDia) ? "selected" : "" %>>Domingo</option>
        </select>
        <button class="btn btn-primary">Filtrar</button>
    </div>
</form>

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
        <th scope="col">Kcal</th>
        <th scope="col">Metros</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
<%
    for(int i = 0; i< l.size(); i++){
        String tiempo = "-";
        if(l.get(i).getTiempo()!=null){
            int tiempoINT = Integer.parseInt(l.get(i).getTiempo());
            int minutos = tiempoINT/60;
            int segundos = tiempoINT%60;
            tiempo = "";
            if(minutos!=0) tiempo +=minutos + "m";
            if(segundos!=0) tiempo += segundos + "s";
        }
%>
    <tr>
        <th scope="row"><%=i+1%></th>
        <td><%=l.get(i).getEjercicio().getNombre()%></td>
        <td><%=l.get(i).getEjercicio().getTipo().getTipoDeEjercicio()%></td>
        <td><%=l.get(i).getSets() != null ? l.get(i).getSets() : "-"%></td>
        <td><%=l.get(i).getRepeticiones() != null ? l.get(i).getRepeticiones() : "-"%></td>
        <td><%=l.get(i).getPeso() != null ? l.get(i).getPeso() : "-"%></td>
        <td><%=tiempo%></td>
        <td><%=l.get(i).getKilocalorias() != null ? l.get(i).getKilocalorias() : "-"%></td>
        <td><%=l.get(i).getMetros() != null ? l.get(i).getMetros() : "-"%></td>
        <td><a href="ejercicio?id=<%=l.get(i).getId()%>" class="btn btn-primary">Seleccionar</a></td>
    </tr>
<%
    }
%>
    <tbody>
</table>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
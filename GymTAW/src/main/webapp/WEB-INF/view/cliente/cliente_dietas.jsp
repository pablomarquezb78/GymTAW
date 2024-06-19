<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Comida" %>
<%@ page import="es.uma.entity.DiaDieta" %>
<%--
  Created by IntelliJ IDEA.
  User: Pablo Márquez Benítez
  Date: 29/04/2024
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%
    //OBTENEMOS PARAMETROS
    List<Comida> comidas = (List<Comida>) request.getAttribute("comidas");
    DiaDieta diaDieta = (DiaDieta) request.getAttribute("diaDieta");

    String filtroDia = request.getParameter("filtroDia");
    String filtroSemana = request.getParameter("filtroSemana");

    //COMPROBACIONES PARAMETROS
    if (filtroDia == null) filtroDia="1"; //Por defecto se selecciona el dia actual en el sistema (lunes en nuestro caso)
    if (filtroSemana == null) filtroSemana = "0";
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<jsp:include page="cabecera_cliente.jsp"/>

<div class="d-flex flex-column align-items-center">
        <h1 >Dietas:</h1>
        <form method="post" action="/cliente/filtrarFechaDieta">
            <div class="d-flex w-100 m-3 gap-2">
                <div class="d-flex gap-3 w-100">
                    <div>
                        <span>Semana:</span><br/>
                        <select name="filtroSemana" class="form-select">
                            <option value="0" <%= "0".equals(filtroSemana) ? "selected" : "" %>>Actual</option>
                            <option value="1" <%= "1".equals(filtroSemana) ? "selected" : "" %>>Siguiente</option>
                        </select>
                    </div>
                    <div>
                        <span>Dia:</span><br/>
                        <select name="filtroDia" class="form-select">
                            <option value="1" <%= "1".equals(filtroDia) ? "selected" : "" %>>Lunes</option>
                            <option value="2" <%= "2".equals(filtroDia) ? "selected" : "" %>>Martes</option>
                            <option value="3" <%= "3".equals(filtroDia) ? "selected" : "" %>>Miércoles</option>
                            <option value="4" <%= "4".equals(filtroDia) ? "selected" : "" %>>Jueves</option>
                            <option value="5" <%= "5".equals(filtroDia) ? "selected" : "" %>>Viernes</option>
                            <option value="6" <%= "6".equals(filtroDia) ? "selected" : "" %>>Sábado</option>
                            <option value="7" <%= "7".equals(filtroDia) ? "selected" : "" %>>Domingo</option>
                        </select>
                    </div>
                    <div>
                        <br/>
                        <button class="btn btn-primary">Filtrar</button>
                    </div>
                </div>
            </div>
        </form>

    <%
        if(comidas.isEmpty()){
    %>
        ¡No hay comidas asignadas para hoy!
    <%
        }else{
    %>

    <table class="table w-50">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Comida</th>
            <th scope="col">Seleccionar</th>
        </tr>
        </thead>
        <tbody>
        <%
            for(int i = 0; i< comidas.size(); i++){
        %>
        <tr>
            <th scope="row"><%=i+1%></th>
            <td><%=comidas.get(i).getTipoComida().getComidaDelDia()%></td>
            <td><a href="seleccionarComida?id=<%=comidas.get(i).getId()%>" class="btn btn-primary">Seleccionar</a></td>
        </tr>
            <%
        }%>
        <tbody>
    </table>
        <%
        }
    %>
</div>

<%if(diaDieta!=null){%>
    <form method="post" action="/cliente/guardarSeguimientoDieta" class="d-flex flex-column align-items-center gap-3 mt-3">
        <h2>Seguimiento general del día:</h2>
        <textarea class="form-control w-25" style="height: 200px" name="seguimientoDieta" placeholder="<%=diaDieta.getSeguimiento() == null ? "Introduce seguimiento" : diaDieta.getSeguimiento()%>"></textarea>
        <button class="btn btn-primary">Guardar Seguimiento</button>
    </form>
<%}%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
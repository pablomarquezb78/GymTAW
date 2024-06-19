<%@ page import="es.uma.entity.ImplementacionEjercicioRutina" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.dao.EjercicioRepository" %>
<%@ page import="es.uma.entity.Rutina" %>
<%@ page import="es.uma.dto.RutinaDTO" %>
<%@ page import="es.uma.dto.ImplementacionEjercicioRutinaDTO" %>
<%
    List<ImplementacionEjercicioRutina> lista = (List<ImplementacionEjercicioRutina>) request.getAttribute("implementaciones");
    Rutina rutina = (Rutina) request.getAttribute("rutina");

    Boolean editar = true;
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rutina</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="cabecera_entrenador.jsp"></jsp:include>
<div class="container mt-5">
    <h1 class="text-center mb-4">Nueva Rutina</h1>

    <form action="/entrenamientos/cambiarnombrerutina" method="post" class="mb-4">
        <input type="hidden" name="idrutina" value="<%= rutina.getId() %>">
        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" class="form-control" value="<%= rutina.getNombre() %>" size="40">
        </div>
        <button type="submit" class="btn btn-primary">Cambiar Nombre</button>
    </form>

    <table class="table table-bordered">
        <thead class="thead-dark">
        <tr>
            <th>Ejercicio</th>
            <th>Sets</th>
            <th>Repeticiones</th>
            <th>Peso</th>
            <th>Metros</th>
            <th>Tiempo</th>
            <%
                if(editar) {
            %>
            <th>Editar</th>
            <th>Borrar</th>
            <%
                }
            %>
        </tr>
        </thead>
        <tbody>
        <%

            if(lista.size()>0 && editar){
                for (ImplementacionEjercicioRutina imp : lista) {
        %>
        <tr>
            <td><%= imp.getEjercicio().getNombre() %></td>
            <td><%= imp.getSets()%></td>
            <td><%= imp.getRepeticiones()%></td>
            <td><%= imp.getPeso()%> Kg</td>
            <td><%= imp.getMetros()%> Mts</td>
            <td><%= imp.getTiempo()%></td>
            <td>
                <form action="/entrenamientos/editarimplementaciondefinitiva" method="get" class="d-inline">
                    <input type="hidden" name="id" value="<%= imp.getId() %>">
                    <button type="submit" class="btn btn-primary btn-sm">Editar</button>
                </form>
            </td>
            <td>
                <form action="/entrenamientos/borrarimplementacionderutina" method="post" class="d-inline">
                    <input type="hidden" name="id" value="<%= imp.getId() %>">
                    <input type="hidden" name="idrutina" value="<%= rutina.getId() %>">
                    <button type="submit" class="btn btn-danger btn-sm">Quitar</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
            else if(lista.size()>0 && !editar) {
                for (ImplementacionEjercicioRutina imp : lista) {
        %>
        <tr>
            <td><%= imp.getEjercicio().getNombre() %></td>
            <td><%= imp.getSets()%></td>
            <td><%= imp.getRepeticiones()%></td>
            <td><%= imp.getPeso()%> Kg</td>
            <td><%= imp.getMetros()%> Mts</td>
            <td><%= imp.getTiempo()%></td>
        </tr>
        <%
            }}
        %>
        </tbody>
    </table>

    <form action="/entrenamientos/crearimplementacionrutina" method="get">
        <input type="hidden" name="id" value="<%=-1 %>">
        <input type="hidden" name="idrutina" value="<%= rutina.getId() %>">
        <button type="submit" class="btn btn-success">Agregar Ejercicio</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

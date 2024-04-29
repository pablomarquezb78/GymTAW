<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String panel = (String) request.getAttribute("panel");
    Plato plato = (Plato) request.getAttribute("selectedPlato");
    String nombrePlato;
    String recetaPlato;
    String tiempoPlato;
    if (plato != null) {
        nombrePlato = plato.getNombre();
        recetaPlato = plato.getReceta();
        tiempoPlato = plato.getTiempoDePreparacion();
    }
    else {
        nombrePlato = "(No se ha seleccionado ningun plato)";
        recetaPlato = "";
        tiempoPlato = "";
    }
    List<Plato> listaPlatos = (List<Plato>) request.getAttribute("listaPlatos");
    List<Ingrediente> listaIngredientes = (List<Ingrediente>) request.getAttribute("listaIngredientes");
    if(listaIngredientes == null)
    {
        listaIngredientes = new ArrayList<Ingrediente>();
    }
%>

<html>
<head>
    <title>Dietista Platos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item active">
                <a class="nav-link">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Clientes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Perfil</a>
            </li>
        </ul>
    </div>
</nav>

<%
    switch (panel)
    {
        case "main":

%>

<div class="row">
    <div class="col-sm-4">
        <h4>Platos:</h4>
    </div>
    <div class="col-sm-8">
        <h4>Nombre: <%= nombrePlato %></h4>
    </div>
</div>
<div class="row">
    <div class="col-sm-4">
        <%-- Hueco en blanco --%>
    </div>
    <div class="col-sm-4">
        <h6>Ingredientes:</h6>
    </div>
    <div class="col-sm-4">
        <h6>Preparación:</h6>
    </div>
</div>
<div class="row">
    <div class="col-sm-4">
        <form action="mostrarPlato" method="get">
        <select name="platosDisplay" size="<%= listaPlatos.size() %>">
            <%
                for(Plato p : listaPlatos) {
            %>
                <option value="<%= p.getId() %>"><%= p.getNombre() %></option>
            <%
                }
            %>
        </select> <br>
        <%
            if (!listaPlatos.isEmpty())
            {
        %>
        <button>Mostrar</button>
        <%
            }
        %>
        </form>
    </div>
    <div class="col-sm-4">
        <ul>
            <%
                for (Ingrediente i : listaIngredientes)
                {
            %>
                <li>
                    <%= i.getNombre() %>
                </li>
            <%
                }
            %>
        </ul>
        <h6>Tiempo de preparacion: <%= tiempoPlato %></h6>
    </div>
    <div class="col-sm-4">
        <article>
            <span class="border border-2 rounded-1">
                <%= recetaPlato %>
            </span>
        </article>
    </div>
</div>

<%
            break;
    }
%>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
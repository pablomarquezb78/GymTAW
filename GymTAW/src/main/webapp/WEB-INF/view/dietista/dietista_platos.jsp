<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
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
            <li class="nav-item active text-weight-bold">
                <a class="nav-link" href="/dietista/">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link">Clientes</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/dietista/mostrarPerfil">Perfil</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/cerrarSesion">Cerrar sesión</a>
            </li>
        </ul>
    </div>
</nav>

<div class="row justify-content-center">
    <div class="col-sm-4 justify-content-center ">
        <h4>Platos:</h4>
        <form action="/dietista/mostrarPlato" method="post">
            <select name="platosDisplay" size="<%= listaPlatos.size() %>">
                <%
                    for(Plato p : listaPlatos) {
                        String selected = "";
                        if(p.equals(plato))
                        {
                            selected = "selected";
                        }
                %>
                <option value="<%= p.getId() %>" <%=selected%>><%= p.getNombre() %></option>
                <%
                    }
                %>
            </select> <br/> <br/>
            <%
                if (!listaPlatos.isEmpty())
                {
            %>
            <button>Mostrar plato</button>
            <%
                }
            %>
        </form>
        <form action="/dietista/crearPlato" method="get">
            <button>Crear plato</button>
        </form>
        <%
            if(plato != null) {
        %>
            <form action="/dietista/editarPlato" method="get">
                <input type="hidden" name="platoId" value="<%=plato.getId()%>">
                <button>Editar plato: <%=plato.getNombre()%></button>
            </form>
            <form action="/dietista/borrarPlato" method="post">
                <input type="hidden" name="platoId" value="<%=plato.getId()%>">
                <button>Borrar plato: <%=plato.getNombre()%></button>
            </form>
        <%
            }
        %>
    </div>
    <div class="col-sm-8 justify-content-center ">
        <%
            if(plato != null) {
        %>
        <h4>Nombre: <%= nombrePlato %></h4>
        <div class="row justify-content-left">
            <div class="col-sm-4 justify-content-center ">
                <h5>Ingredientes:</h5>
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
                <h5>Tiempo de preparacion: <%= tiempoPlato %></h5>
            </div>
            <div class="col-sm-4 justify-content-center ">
                <h5>Preparación:</h5>
                <article>
                    <span class="border border-2 rounded-1">
                        <%= recetaPlato %>
                    </span>
                </article>
                <h5>Video Receta:</h5> <br/>
                <iframe width="560" height="315" src="<%=plato.getEnlaceReceta()%>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
            </div>
        <%
            }
        %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
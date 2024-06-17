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
    } else {
        nombrePlato = "(No se ha seleccionado ningún plato)";
        recetaPlato = "";
        tiempoPlato = "";
    }
    List<Plato> listaPlatos = (List<Plato>) request.getAttribute("listaPlatos");
    List<Ingrediente> listaIngredientes = (List<Ingrediente>) request.getAttribute("listaIngredientes");
    if (listaIngredientes == null) {
        listaIngredientes = new ArrayList<Ingrediente>();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dietista Platos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            color: black;
            border: 1px solid grey;
        }
        .form-label {
            font-weight: bold;
            color: black;
        }
        .profile-link a {
            color: black;
            text-decoration: underline;
        }
        .border-box {
            border: 1px solid grey;
            padding: 15px;
            border-radius: 5px;
            background: white;
        }
        .vh-75 {
            height: 75vh;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav nav-fill w-100">
            <li class="nav-item active text-weight-bold">
                <a class="nav-link" href="/dietista/">Platos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/dietista/mostrarClientes">Clientes</a>
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

<div class="container-fluid">
    <div class="row justify-content-center vh-75">
        <div class="col-sm-4 form-container">
            <h4 class="text-center">Platos</h4>
            <form action="/dietista/mostrarPlato" method="post" class="mb-3">
                <div class="mb-3">
                    <label for="platosDisplay" class="form-label">Seleccionar Plato:</label>
                    <select id="platosDisplay" name="platosDisplay" size="5" class="form-select">
                        <%
                            for (Plato p : listaPlatos) {
                                String selected = "";
                                if (p.equals(plato)) {
                                    selected = "selected";
                                }
                        %>
                        <option value="<%= p.getId() %>" <%= selected %>><%= p.getNombre() %></option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <%
                    if (!listaPlatos.isEmpty()) {
                %>
                <button class="btn btn-success w-100">Mostrar plato</button>
                <%
                    }
                %>
            </form>
            <form action="/dietista/crearPlato" method="get" class="mb-3">
                <button class="btn btn-primary w-100">Crear plato</button>
            </form>
            <%
                if (plato != null) {
            %>
            <form action="/dietista/editarPlato" method="get" class="mb-3">
                <input type="hidden" name="platoId" value="<%= plato.getId() %>">
                <button class="btn btn-warning w-100">Editar plato: <%= plato.getNombre() %></button>
            </form>
            <form action="/dietista/borrarPlato" method="post" class="mb-3">
                <input type="hidden" name="platoId" value="<%= plato.getId() %>">
                <button class="btn btn-danger w-100">Borrar plato: <%= plato.getNombre() %></button>
            </form>
            <%
                }
            %>
        </div>
        <div class="col-sm-8 form-container">
            <%
                if (plato != null) {
            %>
            <h4 class="text-center">Nombre: <%= nombrePlato %></h4>
            <div class="row">
                <div class="col-sm-6">
                    <div class="border-box mb-3">
                        <h5>Ingredientes:</h5>
                        <ul>
                            <%
                                for (Ingrediente i : listaIngredientes) {
                            %>
                            <li><%= i.getNombre() %></li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                    <div class="border-box mb-3">
                        <h5>Tiempo de preparación:</h5>
                        <p><%= tiempoPlato %></p>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="border-box mb-3">
                        <h5>Preparación:</h5>
                        <p><%= recetaPlato %></p>
                    </div>
                    <div class="border-box mb-3">
                        <h5>Video Receta:</h5>
                        <div class="embed-responsive embed-responsive-16by9">
                            <iframe class="embed-responsive-item" width="560" height="315" src="<%= plato.getEnlaceReceta() %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXlXtW8VDtnrROZqPLFpuUWI4a2sA8pD5A4cJZHPUOks+EmW1E6Lxk3VFtDM" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGktK0gYf94IYNd2tKpREIHMR5cQm75J5pbWuyj6cvF2DkSPEj3h4dHGsR9" crossorigin="anonymous"></script>
</body>
</html>

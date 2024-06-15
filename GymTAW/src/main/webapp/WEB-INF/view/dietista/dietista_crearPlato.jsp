<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Plato" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.entity.Ingrediente" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.uma.ui.PlatoDietistaUI" %>
<%@ page import="es.uma.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //List<Ingrediente> listaIngredientes = (List<Ingrediente>) request.getAttribute("listaIngredientes");
    PlatoDietistaUI platoDietista = (PlatoDietistaUI) request.getAttribute("platoDietista");
    List<Ingrediente> ingredientesExistentes = (List<Ingrediente>) request.getAttribute("ingredientesExistentes");
%>

<html>
<head>
    <title>Dietista crear plato</title>
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

<div class="row justify-content-center">
    <div class="col-sm-8 justify-content-center ">
        <div className="border">
            Ingredientes (completa este campo antes de continuar con el formulario): <br/>
            <ul>
                <%
                    if (platoDietista.getIngredientes() != null)
                    {
                        for(Ingrediente ingrediente : platoDietista.getIngredientes())
                        {
                %>
                <li><%=ingrediente.getNombre()%> <a href="/dietista/eliminarIngrediente?ingredienteId=<%=ingrediente.getId()%>">X</a></li>
                <%
                        }
                    }
                %>
            </ul>
            <form:form method="post" action="addIngredienteExistente" modelAttribute="platoDietista">
                <form:hidden path="ingredientes"/>
                <form:hidden path="id"/>
                <form:hidden path="nombre"/>
                <form:hidden path="tiempoDePreparacion"/>
                <form:hidden path="receta"/>
                <form:hidden path="enlaceReceta"/>
                <select name="addedIngrediente">
                    <%
                        for(Ingrediente ingrediente : ingredientesExistentes)
                        {
                    %>
                        <option value="<%=ingrediente.getId()%>" label="<%=ingrediente.getNombre()%>"/>
                    <%
                        }
                    %>
                </select>
                <button>Añadir este ingrediente</button>
            </form:form>
            <a href="/dietista/addNuevoIngrediente">Introducir nuevo ingrediente</a>
        </div>
        <br/>
        <br/>
        <form:form action="guardarPlato" method="post" modelAttribute="platoDietista">
            <div className="border">
                Nombre del plato: <form:input path="nombre" maxlength="100" size="100"/>
            </div> <br/>
            <div className="border">
                Tiempo de preparación: <form:input path="tiempoDePreparacion" maxlength="30" size="30"/>
            </div> <br/>
            <div className="border">
                Receta: <form:input path="receta" maxlength="1500" size="100"/>
            </div> <br/>
            <div className="border">
                Video de la receta (enlace de youtube): <form:input path="enlaceReceta" maxlength="200" size="150"/>
            </div> <br/>
            <form:hidden path="ingredientes"/>
            <form:hidden path="id"/>
            <button>Guardar</button> <a href="/dietista/">Cancelar</a>
        </form:form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
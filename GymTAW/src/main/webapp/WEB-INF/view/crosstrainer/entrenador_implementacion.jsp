<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="es.uma.entity.Ejercicio" %>
<%@ page import="java.util.List" %>
<%@ page import="es.uma.ui.Implementacion" %>
<%
    List<Ejercicio> ejercicios = (List<Ejercicio>) request.getAttribute("ejercicios");
    Boolean editable = (Boolean) request.getAttribute("editable");

    Implementacion implementacion = (Implementacion) request.getAttribute("implementacion");

    String sets = "", repeticiones = "", peso = "", tiempo = "", cal = "", metros = "";

    if (editable != null && editable) {
        sets = implementacion.getSets();
        repeticiones = implementacion.getRepeticiones();
        peso = implementacion.getPeso();
        tiempo = implementacion.getTiempo();
        cal = implementacion.getKilocalorias();
        metros = implementacion.getMetros();
    }
%>
<html>
<head>
    <title>Formulario de Implementación</title>
</head>
<body>
<form:form action="/entrenamientos/guardarimplementacion" method="post" modelAttribute="implementacion">
    <h3>Sets: <form:input path="sets" value="${sets}"/></h3>
    <h3>Repeticiones: <form:input path="repeticiones" value="${repeticiones}"/></h3>
    <h3>Peso: <form:input path="peso" value="${peso}"/></h3>
    <h3>Tiempo: <form:input path="tiempo" value="${tiempo}"/></h3>
    <h3>Kcal: <form:input path="kilocalorias" value="${cal}"/></h3>
    <h3>Metros: <form:input path="metros" value="${metros}"/></h3>
    <h3>Ejercicio:</h3>
    <form:select path="ejercicio" items="${ejercicios}" itemValue="id" itemLabel="nombre" >
    </form:select>
    <form:hidden path="idDia"></form:hidden>
    <form:hidden path="id"></form:hidden>
    <form:button>GUARDAR</form:button>
</form:form>
</body>
</html>

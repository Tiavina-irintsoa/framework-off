<%@ page import="java.util.Date" %>
<%@ page import="packages.*" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Affichage des détails de l'employé</title>
</head>
<body>
    <h1>Détails de l'employé</h1>
    
    <% 
     Emp employe = (Emp) request.getAttribute("emp");
     String remarque = (String) request.getAttribute("remarque");
    %>
    
    <h6>Nom : <%= employe.getNom() %></h6>
    <h6>Département : <%= employe.getDepartement() %></h6>
    <h6>Date d'embauche : <%= employe.getDateEmbauche() %></h6>
    
    <%-- Affichage de la photo de l'employé --%>
    
    
    <%-- Affichage de la remarque sur l'employé --%>
    <h3>Remarque : <%= remarque %></h3>
    
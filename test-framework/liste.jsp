<%@ page import="java.util.*" %>
<%@ page import="packages.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% List<Emp> empList = (List<Emp>) request.getAttribute("liste"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>Nom</th>
                <th>Département</th>
            </tr>
        </thead>
        <tbody>
            <% for (Emp emp : empList) { %>
                <tr>
                    <td><%= emp.getNom() %></td>
                    <td><%= emp.getDepartement() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>

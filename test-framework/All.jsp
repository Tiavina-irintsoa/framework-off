<%@ page import="packages.*"%>
<%@ page import="java.util.Vector"%>

<%
    Vector<Emp> liste=(Vector<Emp>) request.getAttribute("liste");
    String title=(String) request.getAttribute("titre");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><% out.print(title); %></title>
</head>
<body>
    Welcome to all.jsp
    <%
        for(Emp emp:liste){
            out.println(emp.getNom()+" "+emp.getDepartement());
            out.println("<br>");
        }
    %>
</body>
</html>
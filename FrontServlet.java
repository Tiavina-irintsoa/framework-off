package etu1840.framework.servlet;
import java.io.*; 
import jakarta.servlet.*; 
import jakarta.servlet.http.*;  
import java.text.*;
import java.util.*;
import util.Util;
import etu1840.framework.*;
public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> MappingUrls;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out=response.getWriter();
        String url=request.getRequestURL().toString();
        out.print("url:");
        out.print(Util.getUrlMapping(url));
        
    }
}

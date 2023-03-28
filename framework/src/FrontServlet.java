package etu1840.framework.servlet;
import java.io.*; 
import jakarta.servlet.*; 
import jakarta.servlet.http.*;  
import java.text.*;
import java.util.*;
import etu1840.framework.*;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;
import java.lang.reflect.*;
public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> MappingUrls;
    public void init() throws ServletException{
        String packagename="";
        this.MappingUrls=new HashMap<String,Mapping>();
        try{
            MyPackage p=new MyPackage();
            Vector<Class<?>> classes=p.getClasses(packagename);
            for(Class c : classes){
                Method[] methods=c.getDeclaredMethods();
                for (Method method : methods){
                    if(method.isAnnotationPresent(Url.class)==true){
                        Mapping mapping=new Mapping(c.getName(),method.getName());
                        String url=method.getAnnotation(Url.class).mapping();
                        this.MappingUrls.put(url,mapping);
                    }
                }
            }   
        }
       catch(ClassNotFoundException e){
            e.printStackTrace();
       }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request,response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out=response.getWriter();
        String applicationPath = request.getServletContext().getRealPath("");
        String url=request.getRequestURL().toString();
        out.print("url:");
        out.println(Util.getUrlMapping(url));   

        for (Map.Entry<String, Mapping> entry : MappingUrls.entrySet()) {
            String key = entry.getKey();
            Mapping value = entry.getValue();
            out.println(key+":"+value.getClassName()+"."+value.getMethod());
        }

    }
}

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
    String baseUrl;
    public void init() throws ServletException{
        this.baseUrl=this.getInitParameter("baseUrl");
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
    protected void setObject(Object object, HttpServletRequest request,Class<?> classe) throws Exception{
    
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            Util.set(object,paramName,request.getParameter(paramName),classe);

        }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out=response.getWriter();
        String url=request.getRequestURL().toString();
        String urlmapping=Util.getUrlMapping(url,this.baseUrl);
        try {
            Mapping mapping=this.MappingUrls.get(urlmapping);
            System.out.println(urlmapping);
            if(mapping==null){
                throw new Exception("Aucun mapping trouve pour "+urlmapping);
            }
            
            

            Class<?> classe=Class.forName(mapping.getClassName());
            Constructor<?> constructor = classe.getConstructor();
            Object classinstance=constructor.newInstance();
            setObject(classinstance,request,classe);

            ModelView modelview=(ModelView) classe.getDeclaredMethod(mapping.getMethod()).invoke(classinstance);

            out.println("invoked");
            
            if(modelview.getData()!=null){
                for(Map.Entry<String,Object> entry : modelview.getData().entrySet()){
                    request.setAttribute(entry.getKey(),entry.getValue());
                }
            }
            RequestDispatcher dispat = request.getRequestDispatcher(modelview.getView());
            dispat.forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}

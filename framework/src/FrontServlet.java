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
            System.out.println("setted"+paramName);
        }
    }
    protected Object[] getArguments(HttpServletRequest request, Method method) throws Exception{
        Enumeration<String> parameterNames = request.getParameterNames();
        Parameter[] parameters = method.getParameters();
        Object[] argvalue = new Object[parameters.length];
        String[] parameterArray = Collections.list(parameterNames).toArray(new String[0]);
        int argindex=0;
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
            for (String paramName : parameterArray) {
                if(paramName.compareToIgnoreCase(parameter.getName())==0){  
                    argvalue[argindex]=StringCaster.cast(request.getParameter(paramName),parameter.getType() );
                    argindex++;
                }
            }
        }
        if(argindex!=parameters.length){
            throw new Exception("La methode requiert" + parameters.length +" arguments, "+(argindex-1)+"donnees");
        }
        return argvalue;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out=response.getWriter();

        //get url
        String url=request.getRequestURL().toString();
        String urlmapping=Util.getUrlMapping(url,this.baseUrl);
        try {
            Mapping mapping=this.MappingUrls.get(urlmapping);
            System.out.println(urlmapping);
            if(mapping==null){
                throw new Exception("Aucun mapping trouve pour "+urlmapping);
            }
            
            //instancier l'objet
            Class<?> classe=Class.forName(mapping.getClassName());
            Constructor<?> constructor = classe.getConstructor();
            Object classinstance=constructor.newInstance();

            //alimenter les attributs de l'objet
            setObject(classinstance,request,classe);

            Method methode = Util.getMethod(classe,mapping.getMethod());
            //arguments de la fonction
            Object[] args=getArguments(request, methode);

            //envoyer les data dans la page
            ModelView modelview=(ModelView) methode.invoke(classinstance,args);  

            if(modelview.getData()!=null){
                for(Map.Entry<String,Object> entry : modelview.getData().entrySet()){
                    request.setAttribute(entry.getKey(),entry.getValue());
                }
            }

            //dispatcher la requete
            RequestDispatcher dispat = request.getRequestDispatcher(modelview.getView());
            dispat.forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}

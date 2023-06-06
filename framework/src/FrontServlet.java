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
        
        System.out.println("setting fields...");
        String contentType = request.getContentType();

        if (contentType != null && contentType.startsWith("multipart/form-data")) {
            
            Collection<Part> parts = request.getParts();
            for(Part part: parts){
                String paramName = part.getName();
                set(object,paramName,request.getParameter(paramName),classe,request);
            }
        } 
        else {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            set(object,paramName,request.getParameter(paramName),classe,request);
        }
        }
        
    }

    //setter les arguments de l'objet
    protected static void set(Object objet,String field,String value,Class<?> classe,HttpServletRequest request) throws Exception{
        Method setter=Util.getSetter(classe,field);
        if(setter!=null){
            for(Field f: classe.getDeclaredFields()){
                if(f.getName().compareToIgnoreCase(field)==0){
                    //si image
                    if(f.getType().getSimpleName().compareTo("FileUpload")==0){
                        Part filePart = request.getPart(field);
                        byte[] fileBytes = Util.getBytesFromPart(filePart);
                        String fileName = extractFileName(filePart);
                        FileUpload file = new FileUpload();
                        //alimenter l'attribut BYTES de fileupload par les bytes du fchier uploade
                        file.setBytes(fileBytes);
                        file.setName(fileName);
                        System.out.println("Filename:"+fileName);
                        setter.invoke(objet,file);
                    }
                    else{
                        setter.invoke(objet,StringCaster.cast(value,setter.getParameterTypes()[0]));
                    }
                }
            }
        }
    }
    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");

        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return "";
    }
    protected Object[] getArguments(HttpServletRequest request, Method method) throws Exception{
        Enumeration<String> parameterNames = request.getParameterNames();
        Parameter[] parameters = method.getParameters();
        Object[] argvalue = new Object[parameters.length];
        String[] parameterArray = Collections.list(parameterNames).toArray(new String[0]);
        int argindex=0;
        for (Parameter arg : parameters) {
            boolean found_a_parameter=false;
            String name = arg.getName();
            for (String paramName : parameterArray) {
                System.out.println(paramName+" et "+name);
                if(paramName.compareToIgnoreCase(arg.getName())==0){  
                    argvalue[argindex]=StringCaster.cast(request.getParameter(paramName),arg.getType() );
                    argindex++;
                    found_a_parameter=true;
                }
            }
            if(!found_a_parameter){
                argvalue[argindex]=null;
            }
        }
        return argvalue;
    }
    protected void setRequestAttribute(HttpServletRequest request,HashMap<String,Object> data){
        for(Map.Entry<String,Object> entry : data.entrySet()){
            request.setAttribute(entry.getKey(),entry.getValue());
            System.out.println(entry.getKey()+"  "+ entry.getValue());
        }   
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        PrintWriter out=response.getWriter();

        //get url
        String url=request.getRequestURL().toString();
        String urlmapping=Util.getUrlMapping(url,this.baseUrl);
        try {
            Mapping mapping=this.MappingUrls.get(urlmapping);
            if(mapping==null){
                throw new Exception("Aucun mapping trouve pour "+urlmapping);
            }
            //instancier l'objet
            Class<?> classe=Class.forName(mapping.getClassName());
            Constructor<?> constructor = classe.getConstructor();
            Object classinstance=constructor.newInstance();

            //alimenter les attributs de l'objet
            setObject(classinstance,request,classe);
            System.out.println("finished setting fields");

            Method methode = Util.getMethod(classe,mapping.getMethod());
            //arguments de la fonction
            Object[] args=getArguments(request, methode);

            //envoyer les data dans la page
            ModelView modelview=(ModelView) methode.invoke(classinstance,args);  

            if(modelview.getData()!=null){
                setRequestAttribute(request,modelview.getData());
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

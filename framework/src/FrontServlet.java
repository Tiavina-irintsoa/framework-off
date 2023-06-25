package etu1840.framework.servlet;
import java.io.*; 
import jakarta.servlet.*; 
import jakarta.servlet.http.*;  
import java.text.*;
import java.util.*;
import etu1840.framework.*;
import etu1840.framework.util.*;
import etu1840.framework.annotation.*;
import com.google.gson.Gson;

import java.lang.reflect.*;

public class FrontServlet extends HttpServlet{
    HashMap<String,Mapping> MappingUrls;
    HashMap<String,Object> singletons;
    HashMap<String,Field[]> classFields;
    String baseUrl;
    String sessionName;

    public void init() throws ServletException{
        this.baseUrl=this.getInitParameter("baseUrl");
        this.sessionName=this.getInitParameter("sessionName");
        this.MappingUrls=new HashMap<String,Mapping>();
        this.singletons=new HashMap<String,Object>();
        this.classFields=new HashMap<String,Field[]>();
        try{
            Util.setFrontServletAttribute(this.MappingUrls,this.singletons,this.classFields);
        }
        catch(Exception e){
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
        
        if(method.isAnnotationPresent(Parameters.class)){
            boolean found_a_parameter = false;
            for (Parameter arg : parameters) {
                found_a_parameter=false;
                for (String paramName : parameterArray) {
                    if(method.getAnnotation(Parameters.class).args()[argindex].compareTo(paramName)==0){  
                        argvalue[argindex]=StringCaster.cast(request.getParameter(paramName),arg.getType() );
                        found_a_parameter=true;
                    }
                }
                if(found_a_parameter){
                    argindex++;
                }
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
    protected void setSession(HashMap<String,Object> data, HttpSession session){
        for(Map.Entry<String,Object> entry : data.entrySet()){
            session.setAttribute(entry.getKey(),entry.getValue());
        } 
    }
    protected void removeSession( ArrayList<String> removed,HttpSession session) {
        for (String attributeName : removed) {
            session.removeAttribute(attributeName);
        }
    }
    
    protected boolean authentified(HttpSession session, Method methode){
            if (methode.isAnnotationPresent(Auth.class)==true){
                String profile = methode.getAnnotation(Auth.class).profile();
                if(profile .compareTo("")==0){
                    if(session.getAttribute(this.sessionName)!=null){
                        return true;
                    }
                }
                else{
                    if(session.getAttribute(profile)!=null){
                        return true;
                    }
                }
                return false;  
            }
            return true;
    }
    protected HashMap<String, Object> getSessionData(HttpSession session) {
        Session sessionObject = new Session();
        HashMap<String, Object> content = new HashMap<>();
    
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);
            content.put(attributeName, attributeValue);
        }
        return content;
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
            String classname= mapping.getClassName();
            //instancier l'objet
            Class<?> classe=Class.forName(classname);

            Object classinstance =null;
            //test si la classe est singleton:
            if(this.singletons.containsKey(classname)){
                classinstance = this.singletons.get(classname);
                if(classinstance==null){
                    System.out.println("created singleton"+ classname);
                    Constructor<?> constructor = classe.getConstructor();
                     classinstance=constructor.newInstance();
                    this.singletons.put(classname,classinstance);
                }
                else{
                     Util.resetAttributes(classinstance,classname,this.classFields);
                }
            }
            //si la classe n'a pas d'annotations singletons
            else{
                Constructor<?> constructor = classe.getConstructor();
                classinstance=constructor.newInstance();
                System.out.println("created non-singleton");
            }            
            //alimenter les attributs de l'objet
            setObject(classinstance,request,classe);
            System.out.println("finished setting fields");

            Method methode = Util.getMethod(classe,mapping.getMethod());
            //arguments de la fonction
            Object[] args=getArguments(request, methode);
            HttpSession session = request.getSession();

            // si la methode a une annotation @auth
            if(!authentified(session,methode)){
                throw new Exception("Vous n'avez pas la permission necessaire");
            }

            if(methode.isAnnotationPresent(Sess.class)){
                Field[] fields = this.classFields.get(classname);
                Field sessionField = Util.findField(fields, "session"); 

                if(sessionField == null){
                    throw new Exception("Vous devez avoir un attribut HashMap Session dans votre controller");
                }

                Method setterSession = Util.getSetter(classe, "session");
                if(setterSession==null){
                    throw new Exception("Veuillez créer un getter pour l'attribut session");
                }
                Object[] params = new Object[]{
                    getSessionData(session)
                };
                setterSession.invoke(classinstance, params);
            }
            //envoyer les data dans la page
            ModelView modelview=(ModelView) methode.invoke(classinstance,args);  


            if(modelview.getData()!=null && modelview.isJSON()==false){
                setRequestAttribute(request,modelview.getData());
            }
            if(modelview.isJSON()){
                response.setContentType("application/json");
                Gson gson = new Gson();
                String json = gson.toJson(modelview.getData());
                out.println(json);
            }
            if(modelview.getSession()!=null){
                if(modelview.getSession().getRemoved()!=null){
                    removeSession(modelview.getSession().getRemoved(),session);
                }
                if(modelview.getSession().getContent()!=null){
                    setSession(modelview.getSession().getContent(),session);
                }
            }
            
            //dispatcher la requete
            if(modelview.getView()!=null){
                RequestDispatcher dispat = request.getRequestDispatcher(modelview.getView());
                dispat.forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }
    }
}

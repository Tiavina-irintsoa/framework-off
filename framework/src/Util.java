package etu1840.framework.util;
import java.lang.reflect.*;
import jakarta.servlet.http.Part;
import java.io.*;
import etu1840.framework.annotation.*;
import java.util.*;
import etu1840.framework.*;
import com.google.gson.Gson;

public class Util{

    public static Field findField(Field[] fields, String name){
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().compareTo(name)==0) {
                return fields[i];
            }
        }
        return null;
    }
    public static String getUrlMapping(String url,String base_url){
        int len = base_url.length();
        return url.substring(len);
    }
    public static void setFrontServletAttribute(HashMap<String,Mapping> MappingUrls,HashMap<String,Object> singletons,HashMap<String,Field[]> classFields) throws Exception{
        MyPackage p=new MyPackage();
        String packagename="";
        Vector<Class<?>> classes=p.getClasses(packagename);
            for(Class c : classes){
                String classname= c.getName();
                classFields.put(classname,c.getDeclaredFields());
                if(c.isAnnotationPresent(Scope.class)){
                    singletons.put(classname,null);
                }
                Method[] methods=c.getDeclaredMethods();
                for (Method method : methods){
                    if(method.isAnnotationPresent(Url.class)==true){
                        System.out.println(method.getName()+"miditra");
                        Mapping mapping=new Mapping(c.getName(),method.getName());
                        String url=method.getAnnotation(Url.class).mapping();
                       MappingUrls.put(url,mapping);
                    }
                }
            }   
    }
    public static String jsonEncode(Object o){
        Gson gson = new Gson();
        String json = gson.toJson(o);
        return json;
            
    }
    public static Method getSetter(Class<?> classe,String field) throws Exception{
        for (Method method: classe.getDeclaredMethods()){
            if(method.getName().compareToIgnoreCase("set"+field)==0){
                return method;
            }
        }
        return null;
    }
    public static Method getGetter(Class<?> classe,String field) throws Exception{
        for (Method method: classe.getDeclaredMethods()){
            if(method.getName().compareToIgnoreCase("get"+field)==0){
                return method;
            }
        }
        return null;
    }
    public static Object getDefaultValue( Class<?> type ){
		System.out.println(" type field default : "+type);
		if( type == boolean.class )
			return false;
		if( type == byte.class )
			return (byte) 0;
		if (type == short.class)
			return (short) 0;
		if ( type == int.class )
			return 0;
		if ( type == float.class )
			return 0;
		if ( type == float.class )
			return 0;
		return null;
	}
    public static void resetAttributes(Object object,String className, HashMap<String,Field[]> classFields ) throws Exception{
        Field[] fields = classFields.get(className);
        Class<?> classe = object.getClass();
        for(Field f: fields){
            Method setter = getSetter(classe,f.getName());
            setter.invoke(object,getDefaultValue(f.getType()));
        }
    }
    public static byte[] getBytesFromPart(Part part) throws IOException {
        InputStream inputStream = part.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096]; // or any other suitable buffer size

        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        return outputStream.toByteArray();
    }
    public static Method getMethod (Class<?> classe, String methodname) throws Exception{
        for (Method method: classe.getDeclaredMethods()){
            if(method.getName().compareToIgnoreCase(methodname)==0){
                return method;
            }
        }
        throw new ClassNotFoundException();
    }
        
}
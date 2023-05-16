package etu1840.framework.util;
import java.util.Enumeration;
import java.lang.reflect.*;

public class Util{
    public static String getUrlMapping(String url,String base_url){
        int len = base_url.length();
        return url.substring(len);
    }
    public static Method getSetter(Class<?> classe,String field) throws Exception{
        for (Method method: classe.getDeclaredMethods()){
            if(method.getName().compareToIgnoreCase("set"+field)==0){
                System.out.println(method.getName());
                System.out.println("cond1");
                System.out.println("done");
                return method;
            }
        }
        throw new Exception("Setter pour "+field+" non trouve");
    }
    public static Method getMethod (Class<?> classe, String methodname) throws Exception{
        for (Method method: classe.getDeclaredMethods()){
            if(method.getName().compareToIgnoreCase(methodname)==0){
                return method;
            }
        }
        throw new ClassNotFoundException();
    }
    public static void set(Object objet,String field,String value,Class<?> classe) throws Exception{
        for(Field f: classe.getDeclaredFields()){
            if(f.getName().compareToIgnoreCase(field)==0){
                Method setter=getSetter(classe,field);
                setter.invoke(objet,StringCaster.cast(value,setter.getParameterTypes()[0]));
            }
        }
    }    
}
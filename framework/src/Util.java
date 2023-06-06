package etu1840.framework.util;
import java.util.Enumeration;
import java.lang.reflect.*;
import jakarta.servlet.http.Part;
import java.io.*;
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
        return null;
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
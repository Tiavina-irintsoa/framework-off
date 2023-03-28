package etu1840.framework.util;
public class Util{
    public static String getUrlMapping(String url,String base_url){
        int len = base_url.length();
        return url.substring(len);
    }
}

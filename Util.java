package util;
public class Util{
    public static String getUrlMapping(String url){
        String base_url="http://localhost:8080/framework/";
        System.out.println(url);
        int len = base_url.length();
        return url.substring(len);
    }
}

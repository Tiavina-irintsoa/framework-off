package packages;
import etu1840.framework.annotation.*;
public class Emp{
    @Url(mapping="get")
    public String get(){
        return "get";
    }
    @Url(mapping="update")
    public String update(){
        return "get";
    }
}
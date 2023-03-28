package packages.model;
import etu1840.framework.util.*;
import etu1840.framework.annotation.*;
public class Class3{
    @Url(mapping="all")
    public ModelView all(){
        return new ModelView("All.jsp");
    }
}
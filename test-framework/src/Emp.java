package packages;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;

public class Emp{
    @Url(mapping="get")
    public ModelView get(){
        return new ModelView("Details.jsp");
    }
    @Url(mapping="update")
    public ModelView update(){
        return new ModelView("Update.jsp");
    }
}
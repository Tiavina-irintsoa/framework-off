package packages;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;

public class Class1{
    @Url(mapping="update/update")
    public ModelView update(){
        return new ModelView("Update.jsp");
    }
    public void delete(){

    }
}
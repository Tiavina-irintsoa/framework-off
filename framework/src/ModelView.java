package etu1840.framework.util;
import java.util.*;

public class ModelView {
    
    private String view;
    private HashMap<String,Object> data;

    public ModelView(){
        
    }
    public ModelView(String view) {
        this.view = view;
    }
    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
    public HashMap<String,Object> getData(){
        return this.data;
    }
    public void setData(HashMap<String,Object> newdata){
        this.data=newdata;
    }
    public void addItem(String key,Object value){
        if(this.data==null){
            this.data=new HashMap<String,Object>();
        }
        this.data.put(key,value);
    }
}

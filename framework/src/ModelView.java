package etu1840.framework.util;
import java.util.*;
import etu1840.framework.Session;

public class ModelView {
    
    private String view;
    private HashMap<String,Object> data;
    private  Session session =  new Session();
    private boolean isJson = false;
    public ModelView() {
    }
    
    public boolean isJson(){
        return isJson;
    }
    public void setJson(boolean json){
        this.isJson = json;
    }
    public ModelView(String view) {
        this.view = view;
    }
    
    public void addItem(String key,Object value){
        if(this.data==null){
            this.data=new HashMap<String,Object>();
        }
        this.data.put(key,value);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}

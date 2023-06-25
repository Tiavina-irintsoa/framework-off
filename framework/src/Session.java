package etu1840.framework;

import java.util.HashMap;
import java.util.ArrayList;

public class Session {
    HashMap<String, Object> content;
    ArrayList<String> removed;

    public void removeAttribute(String key){
        if (this.removed==null) {
            this.removed=new ArrayList<String>();
        }
        this.removed.add(key);
    }
    public void setAttribute(String key, Object value){
        if (this.content== null) {
            this.content = new HashMap<>();
        }
        this.content.put(key, value);
    }
    public Object getAttribute(String key){
        return content.get(key);
    }
    public HashMap<String, Object> getContent() {
        return content;
    }
    public void setContent(HashMap<String, Object> content) {
        this.content = content;
    }
    public ArrayList<String> getRemoved() {
        return removed;
    }
    public void setRemoved(ArrayList<String> removed) {
        this.removed = removed;
    }
   
}

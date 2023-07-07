package etu1840.framework;

import java.util.HashMap;
import java.util.ArrayList;

public class Session {
    ArrayList<String> removed;
    boolean invalidate = false;
    public void removeAttribute(String key){
        if (this.removed==null) {
            this.removed=new ArrayList<String>();
        }
        this.removed.add(key);
    }
    
    public boolean isInvalidate(){
        return invalidate;
    }
    public void setInvalidate(boolean inv){
        this.invalidate = inv;
    }
    
    public void remove(String remove){
        if(removed==null){
            removed = new ArrayList<String>();
        }
        removed.add(remove);
    }


    public ArrayList<String> getRemoved() {
        return removed;
    }
    public void setRemoved(ArrayList<String> removed) {
        this.removed = removed;
    }
   
}

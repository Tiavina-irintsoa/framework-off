package etu1840.framework;
public class Mapping {
    private String className;
    private String method;
    
    
    public Mapping(String className, String method) {
        setClassName(className);
        setMethod(method);
    }
    
    public String getClassName() {
        return className;
    }
    
    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
}

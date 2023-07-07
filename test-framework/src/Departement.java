package packages;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;
import java.util.Vector;
import java.util.Date;
import java.lang.reflect.*;
import java.io.FileOutputStream;


public class Departement{
    private int id;
    private String nom;
    
    
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    @Url(mapping="form_dept.do")
    public ModelView addform(){
        ModelView resp=new ModelView("Formulaire-dept.jsp");
        return resp;
    }
    @Url(mapping="save_dept.do")
    public  ModelView save(String prenom){
        ModelView resp=new ModelView("Fiche_dept.jsp");
        resp.addItem("titre","Nouveau departement");
        resp.addItem("nom", nom);
        return resp;
    }
    public Departement(){
        
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
}
package packages;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;
import java.util.Vector;
import java.util.Date;
import java.lang.reflect.*;
import java.io.FileOutputStream;

@Scope(scope="Singleton")
public class Emp{
    private int id;
    private String nom;
    private String departement;
    private Date dateEmbauche;
    private FileUpload cin;
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public FileUpload getCin() {
        return cin;
    }
    public void setCin(FileUpload cin) {
        this.cin = cin;
    }
    public void setDateEmbauche(Date date){
        this.dateEmbauche=date;
    }
    

    @Url(mapping="form.do")
    public ModelView addform(){
        ModelView resp=new ModelView("Formulaire.jsp");
        return resp;
    }
    @Auth(profile="admin")
    @Url(mapping="admin_test.do")
    public ModelView test_admin(){
        ModelView resp=new ModelView("Formulaire.jsp");
        return resp;
    }
    @Url(mapping="admin.do")
    public ModelView addAdmin(){
       ModelView resp = new ModelView("Added.jsp");
        resp.addSession("isConnected",true);
        resp.addSession("admin",new Emp());
        resp.addItem("profile","admin");
        return resp;
    }

    @Url(mapping="user.do")
    public ModelView addUser(){
        ModelView resp = new ModelView("Added.jsp");
        resp.addSession("isConnected",true);
        resp.addSession("user",new Emp());
        resp.addItem("profile","user");
        return resp;
    }

    @Parameters(args={"prenom", "num"})
    @Auth()
    @Url(mapping="save.do")
    public  ModelView save(String prenom, int num){
        ModelView resp=new ModelView("Fiche.jsp");
        resp.addItem("titre","Nouvel employe");
        resp.addItem("nom", nom);
        System.out.println(prenom);
        resp.addItem("prenom", prenom);
        resp.addItem("num", num + 4);
        resp.addItem("filename",cin.getName());
        try{
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(cin.getName());
                fos.write(cin.getBytes());
            } 
            finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            return resp;
        }
    }
    public Emp(){
        
    }
    public Date getDateEmbauche(){
        return this.dateEmbauche;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }
}
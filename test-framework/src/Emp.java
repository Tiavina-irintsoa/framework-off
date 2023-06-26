package packages;
import etu1840.framework.Session;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;
import java.util.Vector;
import java.util.Date;
import java.lang.reflect.*;
import java.io.FileOutputStream;
import java.util.HashMap;

@Scope(scope="Singleton")
public class Emp{
    private int id;
    private String nom;
    private String departement;
    public Emp(String nom, String departement) {
        this.nom = nom;
        this.departement = departement;
    }
    private Date dateEmbauche;
    private FileUpload cin;
    private HashMap<String,Object> session;

    
    public Emp(int id, String nom, String departement) {
        this.id = id;
        this.nom = nom;
        this.departement = departement;
    }
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
    
    @Sess
    @Url(mapping="sess.do")
    public ModelView sessionData(){
        ModelView mv = new ModelView();
        mv.setView("sess.jsp");
        mv.addItem("color", this.session.get("color"));
        return mv;
    }
    @Url(mapping="addsess.do")
    public ModelView addColor(){
        ModelView mv = new ModelView();
        mv.getSession().setAttribute("color","red");        
        return mv;
    }
    @Url(mapping="form.do")
    public ModelView addform(){
        ModelView resp=new ModelView("Formulaire.jsp");
        return resp;
    }

    @Url(mapping="remove-sess.do")
    public ModelView removeSession(){
        ModelView mv = new ModelView(); 
        mv.getSession().removeAttribute("color");
        return mv;
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
        resp.getSession().setAttribute("isConnected",true);
        resp.getSession().setAttribute("admin",new Emp());
        resp.addItem("profile","admin");
        return resp;
    }

    @Url(mapping="user.do")
    public ModelView addUser(){
        ModelView resp = new ModelView("Added.jsp");
        resp.getSession().setAttribute("isConnected",true);
        resp.getSession().setAttribute("user",new Emp());
        resp.addItem("profile","user");
        return resp;
    }

    @Url(mapping="json.do")
    public ModelView json(){
        Vector<Emp> employees = new Vector<>();
        // Création d'objets Emp et ajout au vecteur
        Emp emp1 = new Emp(1, "John Doe", "Ressources humaines");
        Emp emp2 = new Emp(2, "Jane Smith", "Ventes");
        Emp emp3 = new Emp(3, "Bob Johnson", "Développement");
        
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        ModelView resp = new ModelView();
        resp.setJSON(true);
        resp.addItem("profile","user");
        resp.addItem("cle","valeur");
        resp.addItem("objet", new Object());
        resp.addItem("liste", employees);
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
    public HashMap<String, Object> getSession() {
        return session;
    }
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    
}
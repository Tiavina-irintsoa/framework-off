package packages;
import etu1840.framework.Session;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;
import java.util.Vector;
import java.util.Date;
import java.lang.reflect.*;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

@Scope(scope="Singleton")
public class Emp {
    private int id;
    private String nom;
    private String departement;
    private Date dateEmbauche;
    private FileUpload cin;
    private HashMap<String,Object> session;
    private static Integer entree = 1;


    public static void setEntree(Integer nouveau){
        if(nouveau == null){
            nouveau = 1;
        }
        entree = nouveau;
    }
    public static Integer getEntree(){
        return entree;
    }

    public Emp(){

    }
    @Url(mapping="add-user.do")
    public ModelView addUser(){
        ModelView mv = new ModelView();
        this.session = new HashMap<String,Object>();
        this.session.put("isConnected",true);
        mv.setView("connecte.html");
        return mv;
    }

    @Url(mapping="invalidate.do")
    public ModelView invalidate(){
        ModelView mv = new ModelView();
        mv.getSession().setInvalidate(true);
        return mv;
    }

    @Url(mapping="add-session.do")
    public ModelView addSession(){
        ModelView mv = new ModelView();
        this.session = new HashMap<String,Object>();
        this.session.put("user1",new Emp(1, "John Doe", "Ressources humaines"));
        this.session.put("user2",new Emp(2, "Jane Smith", "Finance"));
        return mv;
    }

    @Url(mapping="")
    public ModelView index(){
        ModelView mv = new ModelView();
        mv.setView("index.html");
        return mv;

    }

    @Parameters(args="remove")
    @Url(mapping="remove-session.do")
    public ModelView removeSession(String remove){
        ModelView mv = new ModelView();
        System.out.println("remove="+remove);
        mv.getSession().remove(remove);
        return mv;
    }


    @Url(mapping="showSession.do")
    @Sess
    @JSON
    public HashMap<String,Object> showSession(){
        return session;
    }

    @Url(mapping="add-admin.do")
    public ModelView addAdmin(){
        ModelView mv = new ModelView();
        this.session = new HashMap<String,Object>();
        this.session.put("isConnected",true);
        this.session.put("admin",true);

        mv.setView("admin.html");
        return mv;
    }
    
    @Url(mapping="increment.do")
    public ModelView increment(){
        setEntree(entree+1);
        return new ModelView();
    }
    @Url(mapping="test-singleton.do")
    public ModelView singleton(){
        ModelView mv = new ModelView();
        mv.setJson(true);
        mv.addItem("entree",entree);
        return mv;
    }


    @Auth()
    @Url(mapping="emp-list.do")
    public ModelView liste(){
        List<Emp> empList = new ArrayList<>();
        Emp emp1 = new Emp(1, "John Doe", "Ressources humaines");
        Emp emp2 = new Emp(2, "Jane Smith", "Finance");
        Emp emp3 = new Emp(3, "Alice Johnson", "Informatique");
        empList.add(emp1);
        empList.add(emp2);
        empList.add(emp3); 

        ModelView mv = new ModelView();
        mv.addItem("liste",empList);
        mv.setView("liste.jsp");
        return mv;
    }

    @Auth(profile="admin")
    @Url(mapping="new-emp.do")
    public ModelView form(){
        ModelView mv = new ModelView();
        mv.setView("formulaire.jsp");
        return mv;
    }

    @Url(mapping="add-emp.do")
    @Parameters(args="remarque")
    public ModelView addEmp(String remarque){
        ModelView  mv = new ModelView();
        mv.setView("fiche.jsp");
        mv.addItem("emp",this);
        mv.addItem("remarque",remarque);
        System.out.println(cin.getName());
        try{
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(cin.getName());
                fos.write(cin.getBytes());
            }
            catch(Exception e){
                System.out.println("erreur");
            }
            finally {
                if (fos != null) {
                    fos.close();
                }
            }
        }
        catch(Exception e){
            System.out.println("erreur");
        }
        return mv;
    }
    public Emp(int id, String nom, String departement) {
        this.id = id;
        this.nom = nom;
        this.departement = departement;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public Date getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(Date dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public FileUpload getCin() {
        return cin;
    }

    public void setCin(FileUpload cin) {
        this.cin = cin;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

}
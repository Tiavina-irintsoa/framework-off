package packages;
import etu1840.framework.annotation.*;
import etu1840.framework.util.*;
import java.util.Vector;
import java.util.Date;
import java.lang.reflect.*;
public class Emp{
    private int id;
    private String nom;
    private String departement;
    private Date dateEmbauche;
    Vector<Emp> empList;


    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setDateEmbauche(Date date){
        this.dateEmbauche=date;
    }

    @Url(mapping="all")
    public ModelView all(){
        this.empList = new Vector<>();

        Emp emp1 = new Emp();
        emp1.setNom("John Doe");
        emp1.setDepartement("RH");
        empList.add(emp1);

        Emp emp2 = new Emp();
        emp2.setNom("Jane Smith");
        emp2.setDepartement("Finance");
        empList.add(emp2);

        Emp emp3 = new Emp();
        emp3.setNom("Bob Johnson");
        emp3.setDepartement("IT");
        empList.add(emp3);

        Emp emp4 = new Emp();
        emp4.setNom("Emily Watson");
        emp4.setDepartement("Marketing");
        empList.add(emp4);

        Emp emp5 = new Emp();
        emp5.setNom("Alex Turner");
        emp5.setDepartement("Design");
        empList.add(emp5);

        Emp emp6 = new Emp();
        emp6.setNom("Jessica Lee");
        emp6.setDepartement("Sales");
        empList.add(emp6);
        ModelView resp=new ModelView("All.jsp");
        resp.addItem("titre","Liste des employes");
        resp.addItem("liste",empList);
        return resp;
    }

    @Url(mapping="form")
    public ModelView addform(){
        ModelView resp=new ModelView("Formulaire.jsp");
        return resp;
    }
    @Url(mapping="save")
    public  ModelView save(){
        this.empList = new Vector<>();

        Emp emp1 = new Emp();
        emp1.setNom("John Doe");
        emp1.setDepartement("RH");
        empList.add(emp1);

        Emp emp2 = new Emp();
        emp2.setNom("Jane Smith");
        emp2.setDepartement("Finance");
        empList.add(emp2);

        Emp emp3 = new Emp();
        emp3.setNom("Bob Johnson");
        emp3.setDepartement("IT");
        empList.add(emp3);

        Emp emp4 = new Emp();
        emp4.setNom("Emily Watson");
        emp4.setDepartement("Marketing");
        empList.add(emp4);

        Emp emp5 = new Emp();
        emp5.setNom("Alex Turner");
        emp5.setDepartement("Design");
        empList.add(emp5);

        Emp emp6 = new Emp();
        emp6.setNom("Jessica Lee");
        emp6.setDepartement("Sales");
        empList.add(emp6);
        
        ModelView resp=new ModelView("All.jsp");
        this.empList.add(this);
        resp.addItem("titre","Liste des employes");
        resp.addItem("liste",empList);
        return resp;
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
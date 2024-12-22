package mg.itu.test.personne;

import mg.itu.relation.HasMany;
import mg.itu.request.Entity;
import mg.itu.test.animaux.Chien;

import java.util.List;

public class Personne extends Entity {
    private String nom;
    private String prenom;
    private int age;

    private List<Chien> chiens;

    public Personne(){

    }

    public HasMany chiens(){
        return this.hasMany(Chien.class,"idProprietaire","idPersonne");
    }
}

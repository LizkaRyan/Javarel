package mg.itu.test.animaux;

import mg.itu.relation.HasOne;
import mg.itu.request.Entity;
import mg.itu.test.personne.Personne;

public class Chien extends Entity {
    private String nom;
    private String prenom;

    private Personne proprietaire;

    public HasOne proprietaire(){
        return this.hasOne(Personne.class,"idProprietaire","idPersonne");
    }
}

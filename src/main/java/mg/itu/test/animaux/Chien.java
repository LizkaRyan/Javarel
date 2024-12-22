package mg.itu.test.animaux;

import mg.itu.relation.BelongsTo;
import mg.itu.relation.HasOne;
import mg.itu.request.Entity;
import mg.itu.test.personne.Personne;

public class Chien extends Entity {
    private String nom;
    private String prenom;

    private Personne proprietaire;

    public BelongsTo proprietaire(){
        return this.belongsTo(Personne.class,"idPersonne","idProprietaire");
    }
}

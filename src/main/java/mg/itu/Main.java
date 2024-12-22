package mg.itu;

import mg.itu.request.Entity;
import mg.itu.test.animaux.Chien;
import mg.itu.test.personne.Personne;

public class Main {
    public static void main(String[] args) {
        Personne personne=new Personne();
        try{
            System.out.println(new Chien().proprietaire().getRequest());
        }
        catch (Exception ex){
            System.out.println(ex);
        }
        System.out.println("Vita");
    }
}
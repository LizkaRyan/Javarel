package mg.itu;

import mg.itu.request.Entity;
import mg.itu.test.personne.Personne;

public class Main {
    public static void main(String[] args) {
        Personne personne=new Personne();
        System.out.println(personne.createQuery("p").getRequest());
        System.out.println("Vita");
    }
}
package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Humain extends Unites {
    protected Biome biome_favori;
    public Humain(Plateau _plateau, int mv, int nb_u, int idJoueur) {
        super(_plateau, nb_u, idJoueur);
        this.movement_possible = mv; //movement possible en x, y ; //3
        // djessy: adapte en fonction des pos si on veut ??
        // par exemple comme avec le cheval etc aux echec
        // alors faudra y complexifier si besoin....
        biome_favori = Biome.PLAIN;
    }
    @Override
    public String getTypeUnite() {
        return "Humain";
    }


}

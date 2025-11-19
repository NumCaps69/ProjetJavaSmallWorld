package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Humain extends Unites {
    protected int movement_possible;
    protected Biome biome_favori;
    public Humain(Plateau _plateau, int mv, int nb_u) {
        super(_plateau, nb_u);
        movement_possible = 3; //movement possible en x, y ;
        // djessy: adapte en fonction des pos si on veut ??
        // par exemple comme avec le cheval etc aux echec
        // alors faudra y complexifier si besoin....
        biome_favori = Biome.PLAIN;
    }


}

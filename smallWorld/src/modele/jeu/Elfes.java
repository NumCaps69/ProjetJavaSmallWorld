package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Elfes extends Unites {
    protected int movement_possible;
    protected Biome biome_favori;
    public Elfes(Plateau _plateau, int mv, int nb_u){
        super(_plateau, nb_u);
        movement_possible = 1;
        biome_favori = Biome.FOREST;
    }
    @Override
    public String getTypeUnite() {
        return "Elfes";
    }


}

package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Elfes extends Unites {
    protected Biome biome_favori;
    public Elfes(Plateau _plateau, int mv, int nb_u, int idJoueur) {
        super(_plateau, nb_u, idJoueur);
        this.movement_possible = mv; //1
        biome_favori = Biome.FOREST;
    }
    @Override
    public String getTypeUnite() {
        return "Elfes";
    }


}

package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Nain extends Unites{
    protected int movement_possible;
    protected Biome biome_favori;
    public Nain(Plateau _plateau, int mv, int nb_u) {
        super(_plateau, nb_u);
        movement_possible = 4;
        biome_favori = Biome.MOUNTAIN;
    }
}

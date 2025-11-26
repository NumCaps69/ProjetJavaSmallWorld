package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Nain extends Unites{
    protected Biome biome_favori;
    public Nain(Plateau _plateau, int mv, int nb_u) {
        super(_plateau, nb_u);
        this.movement_possible = 4;
        biome_favori = Biome.MOUNTAIN;
    }
    @Override
    public String getTypeUnite() {
        return "Nain";
    }
}

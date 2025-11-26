package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Gobelin extends Unites{
    protected Biome biome_favori;
    public Gobelin(Plateau _plateau, int mv, int nb_u) {
        super(_plateau, nb_u);
        this.movement_possible = 5;
        biome_favori = Biome.DESERT;
    }
    @Override
    public String getTypeUnite() {
        return "Gobelin";
    }
}

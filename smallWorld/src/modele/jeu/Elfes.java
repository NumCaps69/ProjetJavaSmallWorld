package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Elfes extends Unites {
    protected Biome biome_favori;

    /**
     * Constructeur
     * @param _plateau
     * @param mv le nombre de cases que peut parcourir l'unité
     * @param nb_u le nombre d'unités
     * @param idJoueur l'id du joueur qui le possède
     */
    public Elfes(Plateau _plateau, int mv, int nb_u, int idJoueur) {
        super(_plateau, nb_u, idJoueur);
        this.movement_possible = mv; //1
        biome_favori = Biome.FOREST;
    }
    /**GETTERS**/
    @Override
    public String getTypeUnite() {
        return "Elfes";
    }


}

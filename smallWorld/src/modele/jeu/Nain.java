package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Nain extends Unites{
    protected Biome biome_favori;
    /**
     * Constructeur
     * @param _plateau le plateau
     * @param mv le nombre de cases que peut parcourir l'unité
     * @param nb_u le nombre d'unités
     * @param idJoueur l'id du joueur qui le possède
     */
    public Nain(Plateau _plateau, int mv, int nb_u, int idJoueur) {
        super(_plateau, nb_u, idJoueur);
        this.movement_possible = mv; //4
        biome_favori = Biome.MOUNTAIN;
    }
    /**GETTERS**/
    @Override
    public String getTypeUnite() {
        return "Nain";
    }
}

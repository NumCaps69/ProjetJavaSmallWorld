package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;


public abstract class Obstacle {
    protected Case c;
    protected Plateau plateau;

    /**
     * Constructeur
     * @param _plateau le plateau
     */
    public Obstacle(Plateau _plateau) {
        plateau = _plateau;
    }
    /**GETTERS**/
    public Case getCase() {
        return c;
    }
    /**FONCTIONS ABSTRAITES**/
    public abstract boolean Traversee();
    public abstract String getTypeObstacle();
}

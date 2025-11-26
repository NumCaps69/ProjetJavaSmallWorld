package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;

public abstract class Obstacle {
    protected Case c;
    protected Plateau plateau;

    public Obstacle(Plateau _plateau) {
        plateau = _plateau;
    }
    public Case getCase() {
        return c;
    }
    public abstract String getTypeObstacle();
}

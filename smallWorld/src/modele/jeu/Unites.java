package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;

/**
 * Entités amenées à bouger
 */
public abstract class Unites {

    protected Case c;
    protected Plateau plateau;
    protected int nombre_unite;
    protected int movement_possible;

    public Unites(Plateau _plateau, int nb_unite) {
        plateau = _plateau;
        nombre_unite = nb_unite;
        movement_possible = 0;
    }

    public void quitterCase() {
        c.quitterLaCase();
    }
    public void allerSurCase(Case _c) {
        if (c != null) {
            quitterCase();
            
        }
        c = _c;
        plateau.arriverCase(c, this);
    }

    public Case getCase() {
        return c;
    }
    public void setNombreUnite(int n) {
        this.nombre_unite = n;
    }
    public int getNombreUnite(){
        return this.nombre_unite;
    }
    public int getMovement_possible() {
        return movement_possible;
    }
    public abstract String getTypeUnite();






}

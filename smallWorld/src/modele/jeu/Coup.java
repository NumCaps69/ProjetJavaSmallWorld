package modele.jeu;

import modele.plateau.Case;

public class Coup {
    protected Case dep;
    protected Case arr;
    public boolean finDeTour;
    private int nbUnites;

    /**
     * Constructeur de coup
     * @param _dep case de départ
     * @param _arr case d'arrivée
     * @param nb nb d'unités
     */
    public Coup(Case _dep, Case _arr, int nb) {
        dep = _dep;
        arr = _arr;
        finDeTour = false;
        this.nbUnites = nb;
    }

    /**
     * Deuxième constructeur
     * @param fin booléen pour indiquer la fin d'un tour
     */
    public Coup(boolean fin){
        this.finDeTour = fin;
    }

    /**
     * Indique la fin du tour
     * @return la valeur de finDeTour
     */
    public boolean isFinDeTour() {
        return finDeTour;
    }

/**GETTERS**/
    public Case getDep() {return dep;}
    public Case getArr() {return arr;}
    public int getNombreUnites() { return nbUnites; }
}

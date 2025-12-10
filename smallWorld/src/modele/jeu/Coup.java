package modele.jeu;

import modele.plateau.Case;

public class Coup {
    protected Case dep;
    protected Case arr;
    public boolean finDeTour;
    private int nbUnites;
    public Coup(Case _dep, Case _arr, int nb) {
        dep = _dep;
        arr = _arr;
        finDeTour = false;
        this.nbUnites = nb;
    }
    public Coup(boolean fin){
        this.finDeTour = fin;
    }

    public boolean isFinDeTour() {
        return finDeTour;
    }

    public Case getDep() {
        return dep;
    }
    public Case getArr() {
        return arr;
    }
    public int getNombreUnites() { return nbUnites; }
}

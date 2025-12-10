/*
 * Copyright (c) 2025 BELALEM KAISSE & KITA DJESSY-ALBERTO
 *
 * Ce code a été créé dans le cadre du projet LIFAPOO (SmallWorld en JAVA).
 * Toute copie, modification ou redistribution sans autorisation est interdite.
 * Tous droits réservés.
 */
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

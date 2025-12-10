/*
 * Copyright (c) 2025 BELALEM KAISSE & KITA DJESSY-ALBERTO
 *
 * Ce code a été créé dans le cadre du projet LIFAPOO (SmallWorld en JAVA).
 * Toute copie, modification ou redistribution sans autorisation est interdite.
 * Tous droits réservés.
 */
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

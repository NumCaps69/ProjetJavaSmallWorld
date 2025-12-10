/*
 * Copyright (c) 2025 BELALEM KAISSE & KITA DJESSY-ALBERTO
 *
 * Ce code a été créé dans le cadre du projet LIFAPOO (SmallWorld en JAVA).
 * Toute copie, modification ou redistribution sans autorisation est interdite.
 * Tous droits réservés.
 */
package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Pierre extends Obstacle{
    protected Biome biome_spawn;
    protected boolean possible_passage = false; //si on peut passer par dessus ou pas, enfin plus loin ou dessus

    /**
     * Constructeur
     * @param _plateau le plateau
     */
    public Pierre(Plateau _plateau) {
        super(_plateau);
        possible_passage = false;
        biome_spawn = Biome.PLAIN;
    }
    @Override
    /**
     * Vérifie si on peut passer l'obstacle
     * @return Booléen
     */
    public boolean Traversee() {
        return possible_passage;
    }

    /**GETTERS**/
    @Override
    public String getTypeObstacle() {
        return "Pierre";
    }


}

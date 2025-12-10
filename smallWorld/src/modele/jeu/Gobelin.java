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

public class Gobelin extends Unites{
    protected Biome biome_favori;
    /**
     * Constructeur
     * @param _plateau
     * @param mv le nombre de cases que peut parcourir l'unité
     * @param nb_unb_u le nombre d'unités
     * @param idJoueur l'id du joueur qui le possède
     */
    public Gobelin(Plateau _plateau, int mv, int nb_unb_u, int idJoueur) {
        super(_plateau, nb_unb_u, idJoueur);
        this.movement_possible = mv; //5
        biome_favori = Biome.DESERT;
    }
    /**GETTERS**/
    @Override
    public String getTypeUnite() {
        return "Gobelin";
    }
}

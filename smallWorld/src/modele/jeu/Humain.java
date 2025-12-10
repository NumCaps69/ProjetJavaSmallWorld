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

public class Humain extends Unites {
    protected Biome biome_favori;
    /**
     * Constructeur
     * @param _plateau
     * @param mv le nombre de cases que peut parcourir l'unité
     * @param nb_u le nombre d'unités
     * @param idJoueur l'id du joueur qui le possède
     */
    public Humain(Plateau _plateau, int mv, int nb_u, int idJoueur) {
        super(_plateau, nb_u, idJoueur);
        this.movement_possible = mv; //movement possible en x, y ; //3
        // djessy: adapte en fonction des pos si on veut ??
        // par exemple comme avec le cheval etc aux echec
        // alors faudra y complexifier si besoin....
        biome_favori = Biome.PLAIN;
    }
    /**GETTERS**/
    @Override
    public String getTypeUnite() {
        return "Humain";
    }


}

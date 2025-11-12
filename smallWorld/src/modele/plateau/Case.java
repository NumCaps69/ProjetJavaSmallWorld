/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.jeu.Unites;

public class Case {

    // TODO : ajouter le biome de la case
    protected Unites u;
    protected Plateau plateau;
    protected Biome biome;
    protected int nb_unites;



    public void quitterLaCase() {
        if(this.nb_unites > 0) {
            this.nb_unites--;
            if(this.nb_unites <= 0) {
                u = null;
            }
        }
    }



    public Case(Plateau _plateau) {

        plateau = _plateau;
        nb_unites = 0;
        u = null;
    }

    public Unites getUnites() {
        return u;
    }

    public Biome getBiome() {
        return biome;
    }



   }

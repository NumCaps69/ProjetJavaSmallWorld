/*
 * Copyright (c) 2025 BELALEM KAISSE & KITA DJESSY-ALBERTO
 *
 * Ce code a été créé dans le cadre du projet LIFAPOO (SmallWorld en JAVA).
 * Toute copie, modification ou redistribution sans autorisation est interdite.
 * Tous droits réservés.
 */
package modele.plateau;

import modele.jeu.Obstacle;
import modele.jeu.Unites;
import modele.jeu.*;

import java.util.ArrayList;
import java.util.Random;


public class Case {

    // TODO : ajouter le biome de la case
    protected Unites u;
    protected Plateau plateau;
    protected Biome biome;
    protected int nb_unites;
    protected Random rand;
    protected Obstacle o;
    protected Evenement e;

    public Case(Plateau _plateau) {

        plateau = _plateau;
        nb_unites = 0;
        u = null;
        biome = randomBiome();
        o = null;
    }

    public void quitterLaCase() {
        System.out.println("Appel à la fonction");
        if(this.nb_unites > 0) {
            this.nb_unites = 0;
            this.u = null;
        }

    }
    public Biome randomBiome() {
        rand = new Random();
        Biome [] allBiomes = Biome.values();
        return allBiomes[rand.nextInt(allBiomes.length)];
    }




    /**GETTERS + SETTERS**/

    public int getNbUnites() {return nb_unites;}

    public Unites getUnites() {return u;}
    public void setUnites(Unites u, int nb) { this.u = u;this.nb_unites = nb;}
    public Obstacle getObstacle(){return o;}
    public void setObstacle(Obstacle o) {this.o = o;}

    public Biome getBiome() {return biome;}

    public Evenement getEvent() {return e;}
    public void setEvent(Evenement e) {
        this.e = e;
    }


}



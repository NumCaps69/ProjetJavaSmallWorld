/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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



    public void quitterLaCase() {
        System.out.println("Appel à la fonction");
        if(this.nb_unites > 0) {
            this.nb_unites = 0;
            u = null;
        }

    }



    public Case(Plateau _plateau) {

        plateau = _plateau;
        nb_unites = 0;
        u = null;
        biome = randomBiome();
        o = null;
    }
    public void setUnites(Unites u, int nb) {
        this.u = u;
        this.nb_unites = nb;
    }

    public int getNbUnites() {
        return nb_unites;
    }

    public Unites getUnites() {
        return u;
    }
    public Obstacle getObstacle(){
        return o;
    }
    public void setObstacle(Obstacle o) {
        this.o = o;
    }

    public Biome getBiome() {
        return biome;
    }
    public void setEvent(Evenement e) {
        this.e = e;
    }

    public Evenement getEvent() {
        return e;
    }

    public Biome randomBiome() {
        rand = new Random();
        Biome [] allBiomes = Biome.values();
        return allBiomes[rand.nextInt(allBiomes.length)];
    }
    /*public void caseAccessible(Case c, int end, ArrayList<Case> l){
        if(end>0){
            end -=1;
            caseAccessible(c.)
        }*/
    }



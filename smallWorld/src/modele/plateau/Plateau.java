/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;


import modele.jeu.Elfes;
import modele.jeu.Gobelin;
import modele.jeu.Humain;
import modele.jeu.Nain;
import modele.jeu.Unites;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.lang.Math;

public class Plateau extends Observable {

    public static final int SIZE_X = 8;
    public static final int SIZE_Y = 8;


    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    protected Case[][] grilleCases = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées

    public Plateau() {
        initPlateauVide();
    }

    public Case[][] getCases() {
        return grilleCases;
    }
    public int getSizeX() {
        return SIZE_X;
    }

    public int getSizeY() {
        return SIZE_Y;
    }


    private void initPlateauVide() {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                grilleCases[x][y] = new Case(this);
                map.put(grilleCases[x][y], new Point(x, y));
            }

        }
    }

    public void initialiser() {


        Elfes c = new Elfes(this, 3, 4);
        c.allerSurCase(grilleCases[4][7]);
        Gobelin cG =  new Gobelin(this, 3, 9);
        cG.allerSurCase(grilleCases[4][6]);
        setChanged();
        notifyObservers();

    }

    public void arriverCase(Case c, Unites u) {
        c.setUnites(u, u.getNombreUnite());
    }

    public boolean peutDeplacer(Case c1, Case c2) {
        if (c1 == null || c2 == null) {
            System.out.println("Deux cases vides...");
            return false;
        }
        Unites unit = c1.getUnites();
        if (unit == null) {
            System.out.println("unit vide...");
            return false;
        }
        Point p1 = map.get(c1);
        Point p2 = map.get(c2);
        if (p1 == null || p2 == null) {
            System.out.println("Hors plateau...");
            return false;
        }
        if (p1.x != p2.x && p1.y != p2.y) { // gère le cas pour les déplacements en diagonale
            System.out.println("Déplacement impossible");
            return false;
        }
        int d = dist(c1, c2);
        if (d == 0 || d > unit.getMovement_possible()) {
            System.out.println("Deplacement impossible, reste sur place");
            return false;
        } else if (d <= unit.getMovement_possible()) {
            System.out.println("Déplacement autorisé : " + unit.getTypeUnite()
                    + " se déplace de " + d + " cases (Max: " + unit.getMovement_possible() + ")");
            return true;
        } else {
            System.out.println("Déplacement impossible");
            return false;
        }
    }

    public void deplacerUnite(Case c1, Case c2) {
        Unites unit = c1.getUnites();
        if(peutDeplacer(c1, c2)) {
            unit.allerSurCase(c2);
            setChanged();
            notifyObservers();
        }
        else{
            System.out.println("Déplacement impossible");
        }

    }


    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Case caseALaPosition(Point p) {
        Case retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleCases[p.x][p.y];
        }
        return retour;
    }

    private int dist(Case c1, Case c2) {
        Point p1 = map.get(c1);
        Point p2 = map.get(c2);
        if(c1== null || c2 == null) return 0;
        return Math.abs(p1.x - p2.x)+Math.abs(p1.y - p2.y);
    }

}
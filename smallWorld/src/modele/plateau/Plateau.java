/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;


import modele.jeu.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.lang.Math;

public class Plateau extends Observable {

    public static final int SIZE_X = 8;
    public static final int SIZE_Y = 8;


    private HashMap<Case, Point> map = new HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
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


        Elfes c = new Elfes(this, 3, 4, 0);
        c.allerSurCase(grilleCases[4][7]);
        Gobelin cG = new Gobelin(this, 3, 9, 1);
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
        }
        //Condition de l'obstacle
        int xDir = Integer.compare(p2.x, p1.x); // Vaut -1, 0 ou 1
        int yDir = Integer.compare(p2.y, p1.y); // Vaut -1, 0 ou 1

        // On part de la case juste après le départ
        int x = p1.x + xDir;
        int y = p1.y + yDir;

        // On avance case par case jusqu'à l'arrivée (incluse)
        while (x != p2.x || y != p2.y) {
            Case caseIntermediaire = grilleCases[x][y];
            Obstacle obs = caseIntermediaire.getObstacle();

            // Si on croise un obstacle infranchissable sur le chemin
            if (obs != null && !obs.Traversee()) {
                System.out.println("Chemin bloqué par " + obs.getTypeObstacle() + " en " + x + "," + y);
                return false;
            }

            // On vérifie aussi s'il y a une autre UNITÉ sur le chemin (sauf à l'arrivée car on peut attaquer)
            if (caseIntermediaire.getUnites() != null) {
                System.out.println("Chemin bloqué par une unité");
                return false;
            }

            x += xDir;
            y += yDir;
        }

        // 4. Vérification finale de la case d'arrivée (c2)
        // On refait le check pour l'arrivée spécifiquement (au cas où la boucle while s'arrête juste avant)
        Obstacle obsArrivee = c2.getObstacle();
        if (obsArrivee != null && !obsArrivee.Traversee()) {
            System.out.println("Case d'arrivée bloquée");
            return false;
        } else {
            System.out.println("Déplacement autorisé : " + unit.getTypeUnite()
                    + " se déplace de " + d + " cases (Max: " + unit.getMovement_possible() + ")");
            return true;
        }
    }

    public void deplacerUnite(Case c1, Case c2) {
        if (c1 == null || c2 == null) {
            System.out.println("Deux cases vides...");
            return;
        }
        Unites unit = c1.getUnites();
        if (unit == null) {
            System.out.println("unit vide...");
            return;
        }
        Point p1 = map.get(c1);
        Point p2 = map.get(c2);
        if (p1 == null || p2 == null) {
            System.out.println("Hors plateau...");
            return;
        }
        if (p1.x != p2.x && p1.y != p2.y) { // gère le cas pour les déplacements en diagonale
            System.out.println("Déplacement impossible");
            return;
        }
        c1.nb_unites = unit.getNombreUnite();
        //int d = dist(c1, c2);
        int d = calcDist(c1, c2, c1.getUnites().getMovement_possible());
        if (d == -1) {
            System.out.println("Déplacment impossible : Obstacle sur le chemin");
            return;
        } else if (d == 0) {

            System.out.println("Deplacement impossible, reste sur place");
        } else if (d <= unit.getMovement_possible()) {

            System.out.println("Déplacement autorisé : " + unit.getTypeUnite()
                    + " se déplace de " + d + " cases (Max: " + unit.getMovement_possible() + ")");
            unit.allerSurCase(c2);
        } else {
            System.out.println("Déplacement impossible");

        }


        setChanged();
        notifyObservers();
    }

    private int calcDist(Case dep, Case arr, int move_possible) {
        //case null
        if (dep == arr) {
            return 0;
        }
        int[][] Grille = new int[SIZE_X][SIZE_Y];
        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                Grille[i][j] = -1;
            }
        }
        Point debut = map.get(dep);
        Grille[debut.x][debut.y] = 0;
        for (int l = 0; l < move_possible; l++) {
            boolean test = false;
            for (int m = 0; m < SIZE_X; m++) {
                for (int n = 0; n < SIZE_Y; n++) {
                    if (Grille[m][n] == l) {
                        traiterVoisin(m, n + 1, l + 1, Grille, arr);
                        traiterVoisin(m, n - 1, l + 1, Grille, arr);
                        traiterVoisin(m + 1, n, l + 1, Grille, arr);
                        traiterVoisin(m - 1, n, l + 1, Grille, arr);
                        test = true;
                    }
                }
            }
            if (!test) {
                break;
            }
        }
        Point fin = map.get(arr);
        return Grille[fin.x][fin.y];


    }

    private void traiterVoisin(int x, int y, int d, int[][] Grille, Case arrivee) {
        if (x < 0 || x >= SIZE_X || y < 0 || y >= SIZE_Y) return;
        if (Grille[x][y] != -1) {
            return;
        }
        Case c = grilleCases[x][y];
        boolean estBloque = (c.getObstacle() != null && !c.getObstacle().Traversee());
        if (estBloque) {
            return;
        } else {
            Grille[x][y] = d;
        }
    }


    /**
     * Indique si p est contenu dans la grille
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
        if (c1 == null || c2 == null) return 0;
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    public int calculerScoreCase(int idJoueurActuel) {
        int pts = 0;
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                Case c = grilleCases[x][y];
                Unites u = c.getUnites();
                if (u != null && u.getIdJoueur() == idJoueurActuel) {
                    int ptsParCase = 1;
                    switch (u.getTypeUnite()) {
                        case "Nain" -> {
                            if (c.getBiome() == Biome.MOUNTAIN){
                                ptsParCase++;
                            }
                        }
                        case "Elfes" -> {
                            if (c.getBiome() == Biome.FOREST){
                                ptsParCase++;
                            }
                        }
                        case "Humain" -> {
                            if (c.getBiome() == Biome.PLAIN){
                                ptsParCase++;
                            }
                        }
                        case "Gobelin" -> {
                            if (c.getBiome() == Biome.DESERT){
                                ptsParCase++;
                            }
                        }
                    }
                    pts = pts + ptsParCase;
                }
            }
        }
        return pts;
    }
    private HashMap<Integer, Integer> pointsCombatPending = new HashMap<>();

    public void combatGagne(int idJ){
        pointsCombatPending.put(idJ, pointsCombatPending.getOrDefault(idJ, 0) + 1);
        setChanged();
        notifyObservers("SCORE : " + idJ);
    }
    public int getAndResetPointsCombat(int idJoueur) {
        if (pointsCombatPending.containsKey(idJoueur)) {
            int pts = pointsCombatPending.get(idJoueur);
            pointsCombatPending.put(idJoueur, 0); // Reset à 0 après lecture
            return pts;
        }
        return 0;
    }

}
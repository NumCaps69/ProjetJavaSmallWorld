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
import java.util.Random;

public class Plateau extends Observable {

    protected int SIZE_X = 8;
    protected int SIZE_Y = 8;
    protected boolean activer_obs;
    protected int max_object;
    protected boolean activer_evenements;
    private int cooldownMeteo = 0;


    private HashMap<Case, Point> map = new HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    protected Case[][] grilleCases = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées

    public Plateau() {
        this(8, 8, false, 0);
        initPlateauVide();
    }
    public Plateau(int x, int y, boolean activer_obs, int max_object) {
        this.SIZE_X = x;
        this.SIZE_Y = y;
        this.activer_obs = activer_obs;
        this.max_object = max_object;
        this.grilleCases = new Case[SIZE_X][SIZE_Y];
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
    protected void genererPierres() {
        if (!activer_obs) return;
        int max_obj = 0;
        while(max_obj < max_object) {
            for (int x = 0; x < getSizeX(); x++) {
                for (int y = 0; y < getSizeY(); y++) {
                    if (grilleCases[x][y].getBiome() == Biome.PLAIN) {
                        int rand = new Random().nextInt(2);
                        if (rand == 1) { //true = 1
                            grilleCases[x][y].setObstacle(new Pierre(this));
                            System.out.println("obj posé");
                            System.out.println(grilleCases[x][y].getObstacle() + " " +x + " " + y);
                            max_obj++;
                        }
                    }
                }
            }
        }
        setChanged();
        notifyObservers();
    }
    public void genererEvenementBase(){
        if(cooldownMeteo > 0){
            System.out.println("evenement en cours : pour encore : " + cooldownMeteo);
            cooldownMeteo--;
            setChanged();
            notifyObservers();
            return;
        }
        else if (cooldownMeteo == 0){
            System.out.println("reset evenement");
            resetEvenement();
            setChanged();
            notifyObservers();
        }
        Random rand = new Random();
        if (rand.nextInt(100)< 45) {
            System.out.println("il ne se passe rien");
            resetEvenement();
            setChanged();
            notifyObservers();
            return;
        }
        else {
            System.out.println("il se passe qqch");
            resetEvenement();
            cooldownMeteo = 4; //pdt 4 tours voir plus
            //alors 55% du temps il se passe du brouuillard
            for (int x = 0; x < getSizeX(); x++) {
                for (int y = 0; y < getSizeY(); y++) {
                    Case c = grilleCases[x][y];
                    c.setObstacle(grilleCases[x][y].getObstacle());
                    if (rand.nextInt(100) < 20 && c.getBiome().equals(Biome.FOREST)) {
                        c.setEvent(Evenement.BROUILLARD);
                    } else if (rand.nextInt(100) < 50 && c.getBiome().equals(Biome.FOREST) &&
                            ((grilleCases[x - 1][y].getBiome() == Biome.FOREST) ||
                                    (grilleCases[x + 1][y].getBiome() == Biome.FOREST) ||
                                    (grilleCases[x][y - 1].getBiome() == Biome.FOREST) ||
                                    (grilleCases[x][y + 1].getBiome() == Biome.FOREST))) {
                        c.setEvent(Evenement.BROUILLARD);
                    } else {
                        c.setEvent(Evenement.CALME);
                    }
                }
                setChanged();
                notifyObservers();
            }
        }
    }
    private void resetEvenement() {
        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                grilleCases[x][y].setEvent(Evenement.CALME);
            }
        }
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
        System.out.println("Tentative de mouvement PD :");
        System.out.println("Départ : " + p1.x + "," + p1.y);
        System.out.println("Arrivée : " + p2.x + "," + p2.y);
        if (p1 == null || p2 == null) {
            System.out.println("Hors plateau...");
            return false;
        }
        if (p1.x != p2.x && p1.y != p2.y) { // gère le cas pour les déplacements en diagonale
            System.out.println("Déplacement impossible diag");
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

        System.out.println("Tentative de mouvement :");
        System.out.println("Départ : " + p1.x + "," + p1.y);
        System.out.println("Arrivée : " + p2.x + "," + p2.y);

        if (p1 == null || p2 == null) {
            System.out.println("Hors plateau...");
            return;
        }
        if (p1.x != p2.x && p1.y != p2.y) { // gère le cas pour les déplacements en diagonale
            System.out.println("Déplacement impossible diag2");
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
        int score = 0;
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                Case c = grilleCases[x][y];
                Unites u = c.getUnites();
                if (u != null && u.getIdJoueur() == idJoueurActuel) {
                    int ptsParCase = 1;
                    switch (u.getTypeUnite()) {
                        case "Nain"    -> { if (c.getBiome() == Biome.MOUNTAIN) ptsParCase++; }
                        case "Elfes"   -> { if (c.getBiome() == Biome.FOREST)   ptsParCase++; }
                        case "Humain"  -> { if (c.getBiome() == Biome.PLAIN)    ptsParCase++; }
                        case "Gobelin" -> { if (c.getBiome() == Biome.DESERT)   ptsParCase++; }
                    }
                    score += ptsParCase;
                    }
            }
        }
        return score;
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
    public void debugQuiPossedeQuoi() {
        System.out.println("\n--- SCAN DU PLATEAU ---");
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (grilleCases[x][y].getUnites() != null) {
                    Unites u = grilleCases[x][y].getUnites();
                    System.out.println("Case [" + x + "," + y + "] : "
                            + u.getTypeUnite()
                            + " appartient au Joueur ID = " + u.getIdJoueur());
                }
            }
        }
        System.out.println("-----------------------\n");
    }
    public void rafraichirAffichage() {
        setChanged(); // On dit "Il y a du nouveau !"
        notifyObservers(); // On prévient la Vue
    }
}
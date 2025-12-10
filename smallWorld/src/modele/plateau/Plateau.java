/*
 * Copyright (c) 2025 BELALEM KAISSE & KITA DJESSY-ALBERTO
 *
 * Ce code a été créé dans le cadre du projet LIFAPOO (SmallWorld en JAVA).
 * Toute copie, modification ou redistribution sans autorisation est interdite.
 * Tous droits réservés.
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
    private HashMap<Integer, Integer> pointsCombatPending = new HashMap<>();
    protected Case[][] grilleCases = new Case[SIZE_X][SIZE_Y]; // permet de récupérer une case à partir de ses coordonnées

//CONSTRUCTEURS

    /**
     * Constructeur de la classe
     */
    public Plateau() {
        this(8, 8, false, 0);
        initPlateauVide();
    }

    /**
     * 2e constructeur de la classe
     * @param x dimension
     * @param y dimension
     * @param activer_obs booléen permettant la mise en place ou non d'obstacles
     * @param max_object le maximum d'obstacles posés
     */
    public Plateau(int x, int y, boolean activer_obs, int max_object) {
        this.SIZE_X = x;
        this.SIZE_Y = y;
        this.activer_obs = activer_obs;
        this.max_object = max_object;
        this.grilleCases = new Case[SIZE_X][SIZE_Y];
        initPlateauVide();
    }

//FIN CONSTRUCTEURS

//INITIALISATION
    /**
     * Initialise le plateau
     */
    private void initPlateauVide() {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                grilleCases[x][y] = new Case(this);
                map.put(grilleCases[x][y], new Point(x, y));
            }

        }
    }

    /**
     * Initialise les unités sur le plateau
     */
    public void initialiser() {


        Elfes c = new Elfes(this, 3, 4, 0);
        c.allerSurCase(grilleCases[4][7]);
        Gobelin cG = new Gobelin(this, 3, 9, 1);
        cG.allerSurCase(grilleCases[4][6]);
        setChanged();
        notifyObservers();

    }

    /**
     * Génère les obstacles
     */
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

    /**
     * Génère les événements
     */
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
            System.out.println("Calme");
            resetEvenement();
            setChanged();
            notifyObservers();
            return;
        }
        else {
            System.out.println("EVENT");
            resetEvenement();
            cooldownMeteo = 2; //pdt 4 tours voir plus
            //alors 55% du temps il se passe du brouuillard
            for (int x = 0; x < getSizeX(); x++) {
                for (int y = 0; y < getSizeY(); y++) {
                    Case c = grilleCases[x][y];
                    c.setObstacle(grilleCases[x][y].getObstacle());
                    Evenement event = Evenement.CALME;
                    if (c.getBiome() == Biome.FOREST) {
                        boolean voisinForet = aUnVoisinDeType(x, y, Biome.FOREST);

                        // 20% de base OU 50% si le biome voisin est la forêt
                        if (rand.nextInt(100) < 20 || (voisinForet && rand.nextInt(100) < 50)) {
                            event = Evenement.BROUILLARD;
                        }
                    }

                    // --- CANICULE (DESERT) ---
                    else if (c.getBiome() == Biome.DESERT) {
                        boolean voisinDesert = aUnVoisinDeType(x, y, Biome.DESERT);
                        // 20% de base OU 50% si le biome voisin est le désert
                        if (rand.nextInt(100) < 20 || (voisinDesert && rand.nextInt(100) < 50)) {
                            event = Evenement.CANICULE;
                        }
                    }

                    c.setEvent(event);
                }
            }
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Enlève les événements en cours sur toutes les cases
     */
    private void resetEvenement() {
        for (int x = 0; x < getSizeX(); x++) {
            for (int y = 0; y < getSizeY(); y++) {
                grilleCases[x][y].setEvent(Evenement.CALME);
            }
        }
    }
//FIN INITIALISATION

// GESTION DES CASES

    /**
     * Gère l'unité sur la case d'arrivée
     * @param c la case
     * @param u l'unité
     */
    public void arriverCase(Case c, Unites u) {
        c.setUnites(u, u.getNombreUnite());
    }

    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

        /**
         * Indique la position sur la grille d'un point P
         * @param p
         * @return la case sur laquelle se trouve le point
         */
    private Case caseALaPosition(Point p) {
        Case retour = null;

        if (contenuDansGrille(p)) {
            retour = grilleCases[p.x][p.y];
        }
        return retour;
    }


    /**
     * Permet la création d'un nouveau groupe d'unités
     * @param type la race
     * @param nb le nouveau nombre
     * @param idJoueur l'id du joueur possédant l'unité
     * @return le nouveau groupe d'unités
     */
    private Unites creerNouvelleUnite(String type, int nb, int idJoueur) {
        switch (type) {
            case "Elfes":   return new Elfes(this, 1, nb, idJoueur);
            case "Gobelin": return new Gobelin(this, 5, nb, idJoueur);
            case "Nain":    return new Nain(this, 4, nb, idJoueur);
            case "Humain":  return new Humain(this, 3, nb, idJoueur);
            default: return null;
        }
    }

//FIN GESTION DES CASES

//GESTION DU DEPLACEMENT


    /**
     * Calcule le mouvement de l'unité à partir de sa case
     * @param c la case où se trouve l'unité
     * @return le mouvement
     */
    private int calculMouvement(Case c){
        Unites u = c.getUnites();
        if (u == null) return 0;
        int mv = u.getMovement_possible();
        if (c.getEvent() == Evenement.BROUILLARD) {
            System.out.println("BROUILLARD !!! Cases dispo réduit à 1");
            return 1;
        }
        if (c.getEvent() == Evenement.CANICULE) {
            System.out.println("MMMMMMH CANICUUUUUUUULE !!!! Portée réduite de 1");
            return Math.max(0, mv - 1);
        }
        return mv;
    }

    /**
     * Gère le déplacement mais au niveau de l'image d'où le type de la fonction
     * @param c1 la case de départ
     * @param c2 la case d'arrivée
     * @return true si on peut se déplacer, sinon false
     */
    public boolean peutDeplacer(Case c1, Case c2) {
        if (c1 == null || c2 == null || c1.getUnites() == null) return false;
        Point p1 = map.get(c1);
        Point p2 = map.get(c2);
        if (p1.x != p2.x && p1.y != p2.y) { // gère le cas pour les déplacements en diagonale
            System.out.println("Déplacement impossible diag");
            return false;
        }
        int mv = calculMouvement(c1);
        int d = calcDist(c1,c2,mv);
        return (d > 0 && d <= mv);
    }


    /**
     * Gère le déplacement mais côté console
     * @param c1 la case de départx
     * @param c2 la case d'arrivée
     * @param qteDeplacement la quantité d'unité qu'on veut déplacer
     */
    public void deplacerUnite(Case c1, Case c2,int qteDeplacement) {
        if (c1 == null || c2 == null || c1.getUnites() == null)  {
            System.out.println("Cases invalides...");
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
        Unites unit = c1.getUnites();
        int mv = calculMouvement(c1);
        int d = calcDist(c1, c2, unit.getMovement_possible());
        if (d == -1) {
            System.out.println("Déplacment impossible : Obstacle sur le chemin");
            return;
        } else if (d == 0) {

            System.out.println("Deplacement impossible, reste sur place");
        } else if (d <= unit.getMovement_possible()) {

            System.out.println("Déplacement autorisé : " + unit.getTypeUnite()
                    + " se déplace de " + d + " cases (Max: " + unit.getMovement_possible() + ")");
            if (qteDeplacement >= unit.getNombreUnite()) {
                c1.setUnites(null, 0);
                unit.allerSurCase(c2);
            } else {// On gère le split des unités
                //On réduit le nombre sur la case de départ
                int reste = unit.getNombreUnite() - qteDeplacement;
                unit.setNombreUnite(reste);

                // Création du groupe qui part
                Unites detachement = creerNouvelleUnite(unit.getTypeUnite(), qteDeplacement, unit.getIdJoueur());

                if (detachement != null) {
                    detachement.allerSurCase(c2);
                }
            }
            setChanged();
            notifyObservers();
        }
        else {
            System.out.println("Déplacement impossible");

        }

    }

    /**
     * Calcule la distance pour le déplacement avec les Point
     * @param dep case de départ
     * @param arr case d'arrivée
     * @param move_possible le mouvement de l'unité
     * @return la distance en int
     */
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

//FIN GESTION DU DEPLACEMENT

//GESTION CONTRAINTES
    /**
     * Vérifie si les cases voisines n'ont pas d'obstacles
     * @param x les lignes de la grille
     * @param y les colonnes de la grille
     * @param d la distance
     * @param Grille la grille
     * @param arrivee la case d'arrivée
     */

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
     * Véifie qu'il y ait au moins un voisin d'une case unité vide afin que l'unité en quetion puisse se déplacer et ne pas être bloqué (être entouré d'obstacles)
     * @param x int
     * @param y int
     * @return Booléen
     */

    public boolean IssueHorsObs(int x, int y) {
        // Droite, Gauche, Bas, Haut
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < 4; i++) {
            int voisinX = x + dx[i];
            int voisinY = y + dy[i];

            if (voisinX >= 0 && voisinX < getSizeX() && voisinY >= 0 && voisinY < getSizeY()) {
                Case voisin = grilleCases[voisinX][voisinY];
                Obstacle obs = voisin.getObstacle();
                if (obs == null || obs.Traversee()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si le biome des cases voisines correspond au biome pour les événements dans notre cas
     * @param x int
     * @param y int
     * @param biomeCherche Biome
     * @return true s'il correspond au biome, false sinon
     */
    private boolean aUnVoisinDeType(int x, int y, Biome biomeCherche) { // pour gérer les événements
        if (x > 0 && grilleCases[x - 1][y].getBiome() == biomeCherche) return true;

        if (x < SIZE_X - 1 && grilleCases[x + 1][y].getBiome() == biomeCherche) return true;

        if (y > 0 && grilleCases[x][y - 1].getBiome() == biomeCherche) return true;

        if (y < SIZE_Y - 1 && grilleCases[x][y + 1].getBiome() == biomeCherche) return true;

        return false;
    }


    /**
     * Indique si p est contenu dans la grille
     * @param p
     * @return true s'il est bien dans la grille, false sinon
     */

//FIN GESTION CONTRAINTE


//GESTION SCORE
    /**
     * Calcule le score du joueur
     * @param idJoueurActuel l'id du joueur
     * @return le score
     */
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

    /**
     * Fonction faisant gagner un combat)
     * @param idJ entier id du joueur
     */

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

//FIN GESTION SCORE
    /**
     * Affiche le plateau mais dans la console (sert pour voir quelles unitées appartiennent à qui)
     *
     */
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

//GESTIONS OBSERVERS
    /**
     * Gère l'envoi d'information de la partie en cours et de la fin à la vue
     */
    public void rafraichirAffichage() {
        setChanged();
        notifyObservers();
    }
    public void signalerFinDePartie(String messageResultat) {
        setChanged();
        notifyObservers("FIN_PARTIE:" + messageResultat);
    }

//FIN GESTIONS OBSERVERS


    /**GETTERS**/
    public Case[][] getCases() {
        return grilleCases;
    }

    public int getSizeX() {
        return SIZE_X;
    }

    public int getSizeY() {
        return SIZE_Y;
    }
}
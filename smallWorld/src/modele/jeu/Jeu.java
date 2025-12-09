package modele.jeu;

import modele.plateau.Plateau;
import modele.plateau.Plateau2J;
import modele.plateau.Plateau3ou4J;
import modele.plateau.Case;

public class Jeu extends Thread{
    private Plateau plateau;
    private final int nb_joueur;
    private Joueur[] joueurs;
    protected Coup coupRecu;
    private int indJoueur = -1; //avoir 0 au déb
    private int tour = 1;
    private static int MAXT;


    public Jeu(int nb_j, boolean activer_obs, int max_u, int max_obj, int MAXT) {
        if(nb_j==2){
            plateau = new Plateau2J(nb_j, activer_obs, max_u, max_obj);
            System.out.print("2 Joueurs !! \n");
            plateau.initialiser();
        }else {
            plateau = new Plateau3ou4J(nb_j, activer_obs, max_u, max_obj);
            plateau.initialiser();
        }
        this.nb_joueur = nb_j;
        this.joueurs = new Joueur[nb_joueur];
        for (int i = 0; i < nb_joueur; i++) {
            joueurs[i] = new Joueur(this,i);
        }
        this.MAXT = MAXT;
        start();

    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void envoyerCoup(Coup c) {
        coupRecu = c;

        synchronized (this) {
            notify();
        }

    }


    public void appliquerCoup(Coup coup) {
        if (coup.dep == null && coup.arr == null) {
            // fin de tour = rien à faire au niveau du plateau
            System.out.println("Fin du tour du joueur " + indJoueur);
            return;
        }
        plateau.deplacerUnite(coup.dep, coup.arr);

    }


    public void run() {
        jouerPartie();
    }

    private Joueur Tour(){
        indJoueur = (indJoueur+1) % nb_joueur;
        System.out.println(indJoueur);
        return joueurs[indJoueur];
    }
    public int getIndJoueur(){
        return indJoueur;
    }

    public void jouerPartie() {
        while (tour<=MAXT) {
            Joueur joueurActuel = Tour();
            boolean finDeTour = false;

            while (!finDeTour) {
                Coup c = joueurActuel.getCoup();
                if (c.isFinDeTour()) {
                    System.out.println("Fin du tour du joueur " + (indJoueur + 1) + " button");
                    finDeTour = true;
                } else {
                        appliquerCoup(c);
                }
                plateau.notifyObservers();
                //Calcul des points
                int ptsSurCase = plateau.calculerScoreCase(joueurActuel.getId());
                System.out.println("J" + indJoueur + " contrôle " + ptsSurCase + " cases");
                joueurActuel.ajoutScore(ptsSurCase);

                System.out.println("SCORE TOTAL J" + indJoueur + " : " + joueurActuel.getScore());
                System.out.println("-------------------------------------------------");

            }

        }
    }
}

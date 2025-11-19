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
    private int indJoueur;



    public Jeu(int nb_j) {
        if(nb_j==2){
            plateau = new Plateau2J();
            System.out.print("2 Joueurs !! \n");
            plateau.initialiser();
        }else {
            plateau = new Plateau3ou4J();
            plateau.initialiser();
        }
        this.nb_joueur = nb_j;
        this.joueurs = new Joueur[nb_joueur];
        for (int i = 0; i < nb_joueur; i++) {
            joueurs[i] = new Joueur(this);
        }
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
        plateau.deplacerUnite(coup.dep, coup.arr);

    }

    public void attaquerCoup(Case c) {
    }


    public void run() {
        jouerPartie();
    }

    private Joueur Tour(){
        indJoueur = (indJoueur+1) % nb_joueur;
        return joueurs[indJoueur];
    }

    public void jouerPartie() {
        Joueur retour = null;

        while(true) {
            retour = Tour();
            for(int i = 0; i<nb_joueur; i++) {
                Coup c = retour.getCoup();
                appliquerCoup(c);
            }

        }

    }


}

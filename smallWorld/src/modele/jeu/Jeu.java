package modele.jeu;

import modele.plateau.Plateau;
import modele.plateau.Plateau2J;

public class Jeu extends Thread{
    private Plateau plateau;
    private final int nb_joueur;
    private Joueur[] joueurs;
    protected Coup coupRecu;



    public Jeu(int nb_j) {
        if(nb_j==2){
            plateau = new Plateau2J();
        }else{
            plateau = new Plateau2J();
        }
        plateau.initialiser();
        this.nb_joueur = nb_j;
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

    public void run() {
        jouerPartie();
    }

    public void jouerPartie() {

        while(true) {
            Coup c = j1.getCoup();
            appliquerCoup(c);

        }

    }


}

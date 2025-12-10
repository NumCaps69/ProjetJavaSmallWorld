package modele.jeu;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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



    public Jeu(int nb_j, boolean activer_obs, int max_u, int MAXT) {
        if(nb_j==2){
            plateau = new Plateau2J(nb_j,activer_obs,max_u,MAXT );
            System.out.print("2 Joueurs !! \n");
            plateau.initialiser();
        }else {
            plateau = new Plateau3ou4J(nb_j, activer_obs, max_u, MAXT);
            plateau.initialiser();
            plateau.debugQuiPossedeQuoi();
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
        plateau.deplacerUnite(coup.dep, coup.arr,coup.getNombreUnites());

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
    public Joueur getJoueur(int index){
        if(index>=0 && index<nb_joueur){
            return joueurs[index];
        }
        return null;
     }
     public int getTourActuel(){
        return tour;
     }

    public int getTourMAX(){
        return MAXT;
    }

    public void sauvegarderScore(int[] score) throws IOException {
        String fichier = "Score.txt";
        BufferedWriter EcritFichier = new BufferedWriter(new FileWriter(fichier, true));
        EcritFichier.write(String.format(String.format("Partie du : " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(LocalDateTime.now()))));
        for(int i = 0; i<score.length; i++){
            EcritFichier.write("Joueur "+ i + " : " + score[i]);
        }
        EcritFichier.close();
    }


    public void jouerPartie() {
        while (tour<=MAXT) {
            Joueur joueurActuel = Tour();
            int idActuel = joueurActuel.getId();
            plateau.rafraichirAffichage();
            System.out.println("--- Début du tour de J" + (indJoueur+1) + " ---");
            System.out.println(("Points du joueur " + indJoueur + " : " + joueurActuel.getScore()));
            boolean finDeTour = false;

            while (!finDeTour) {
                Coup c = joueurActuel.getCoup();
                if (c.isFinDeTour()) {
                    System.out.println("Fin du tour demandé par le joueur " + (indJoueur + 1) + " button");
                    finDeTour = true;
                } else {
                        appliquerCoup(c);
                        plateau.notifyObservers();
                }

            }
            //Calcul des points
            System.out.println(">> BILAN FIN DE TOUR JOUEUR " + idActuel);
            System.out.println("   Points avant : " + joueurActuel.getScore());

            int ptsSurCase = plateau.calculerScoreCase(idActuel);
            int ptsCombat = plateau.getAndResetPointsCombat(idActuel);
            int ptsTotal = ptsSurCase + ptsCombat;
            System.out.println("Gains de ce tour : " + ptsSurCase + " (cases) +" + ptsCombat + " (combat)");
            Joueur j = this.joueurs[idActuel];
            j.ajoutScore(ptsTotal);
            plateau.rafraichirAffichage();

            try{
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\n--- ETAT DES SCORES (Tableau complet) ---");
            for(int i=0; i < nb_joueur; i++) {
                System.out.println("Tab[" + i + "] (ID " + joueurs[i].getId() + ") : " + joueurs[i].getScore() + " points");
            }
            System.out.println("-----------------------------------------\n");
            if(indJoueur == nb_joueur-1) {
                tour++;
                System.out.println(">>> FIN DU TOUR DE TABLE " + (tour - 1) + " <<<");
                plateau.genererEvenementBase();
            }
        }
        System.out.println("FIN DE LA PARTIE");

        String resultat = terminerPartie();

        plateau.signalerFinDePartie(resultat);
    }
    private String terminerPartie() {
        StringBuilder res = new StringBuilder();
        int maxScore = -1;
        Joueur gagnant = null;
        boolean egalite = false;

        // Logique de ton ami (inchangée)
        for (Joueur j : joueurs) {
            String ligne = "Joueur " + (j.getId() + 1) + " : " + j.getScore() + " points\n";
            res.append(ligne);
            if (j.getScore() > maxScore) {
                maxScore = j.getScore();
                gagnant = j;
                egalite = false;
            } else if (j.getScore() == maxScore) {
                egalite = true;
            }
        }

        String messageFin;
        if (egalite) {
            messageFin = "\nMatch Nul ! Égalité parfaite.";
        } else {
            messageFin = "\nVICTOIRE DU JOUEUR " + (gagnant.getId() + 1) + " !";
        }

        return res.toString() + messageFin;
    }
}

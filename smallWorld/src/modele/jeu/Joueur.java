package modele.jeu;

public class Joueur {
    private Jeu jeu;
    private int score;
    //private Unites u;

    public Joueur(Jeu _jeu) {
        jeu = _jeu;
        score = 0;
    }



    public Coup getCoup() {

        synchronized (jeu) {
            try {
                jeu.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return jeu.coupRecu;
    }

    public int getScore(){
        return score;
    }
}

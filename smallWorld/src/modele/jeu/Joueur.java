package modele.jeu;

public class Joueur {
    private Jeu jeu;
    private int score;
    private int id;

    public Joueur(Jeu _jeu, int id) {
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

    public void ajoutScore(int pt){
        this.score = pt;
    }
    public int getId(){
        return id;
    }
}

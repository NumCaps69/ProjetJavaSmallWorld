/*
 * Copyright (c) 2025 BELALEM KAISSE & KITA DJESSY-ALBERTO
 *
 * Ce code a été créé dans le cadre du projet LIFAPOO (SmallWorld en JAVA).
 * Toute copie, modification ou redistribution sans autorisation est interdite.
 * Tous droits réservés.
 */
package modele.jeu;

public class Joueur {
    private Jeu jeu;
    private int score;
    private int id;

    /**
     * Constructeur
     * @param _jeu le jeu
     * @param id l'id du joueur
     */
    public Joueur(Jeu _jeu, int id) {
        jeu = _jeu;
        this.id = id;
        score = 0;
    }

    /**
     * Ajoute des points au score du joueur
     * @param pt le nombre de points
     */
    public void ajoutScore(int pt){
        this.score += pt;
    }

    /**GETTERS**/
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

    public int getId(){ return id; }
}

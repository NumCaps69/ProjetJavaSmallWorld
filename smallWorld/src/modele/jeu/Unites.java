package modele.jeu;

import modele.plateau.Case;
import modele.plateau.Plateau;
import java.util.Random;

import java.util.Objects;

/**
 * Entités amenées à bouger
 */
public abstract class Unites {

    protected Case c;
    protected Plateau plateau;
    protected int nombre_unite;
    protected int movement_possible;
    protected int idJoueur;

    public Unites(Case c) {}

    public Unites(Plateau _plateau, int nb_unite, int _id_joueur) {
        plateau = _plateau;
        nombre_unite = nb_unite;
        movement_possible = 0;
        idJoueur = _id_joueur;
    }

    public void quitterCase() {
        c.quitterLaCase();
    }

    public void allerSurCase(Case _c) {
        if (c != null) {
            c.quitterLaCase();
        }
        Unites uniteSurCase = _c.getUnites();

        if (uniteSurCase != null) {
            if(this.idJoueur == uniteSurCase.getIdJoueur()) {
                this.nombre_unite += uniteSurCase.getNombreUnite();
                _c.quitterLaCase();
            } else {
                // combat
                Random r = new Random();
                int rand = r.nextInt(10); // 0 à 9

                if (this.nombre_unite >= uniteSurCase.getNombreUnite()) {
                    //90% de chance de gagner
                    if(rand < 9){
                        _c.quitterLaCase();
                        System.out.println(this.getTypeUnite() + " " + this.nombre_unite + " a gagné (1)");
                        plateau.combatGagne(this.idJoueur);
                    }
                    else{
                        this.nombre_unite = 0;
                        this.c = null;
                        System.out.println(this.getTypeUnite() + " " + this.nombre_unite + " a perdu (1)");
                        return;
                    }
                } else {
                    if (rand < 5) {
                        _c.quitterLaCase();
                        System.out.println(this.getTypeUnite() + " " + this.nombre_unite + " a gagné (2)");
                        plateau.combatGagne(this.idJoueur);
                    } else {
                        this.nombre_unite = 0;
                        this.c = null;
                        System.out.println(this.getTypeUnite() + " " + this.nombre_unite + " a perdu (2)");
                        return;
                    }
                }
            }
        }
        c = _c;
        plateau.arriverCase(c, this);
    }

    public Case getCase() {
        return c;
    }
    public void setNombreUnite(int n) {
        this.nombre_unite = n;
    }
    public int getNombreUnite(){
        return this.nombre_unite;
    }
    public int getMovement_possible() {
        return movement_possible;
    }
    public int getIdJoueur() {
        return idJoueur;
    }
    public abstract String getTypeUnite();






}

package modele.plateau;

import modele.jeu.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Plateau2J extends Plateau{

    protected int nb_joueurs;
    protected int max_unite_per_all;
    private HashMap<Case, Point> map = new HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence

    public Plateau2J(int nb, boolean activer_obs, int max_u, int max_obj) {
        super(6, 6, activer_obs, max_obj);
        nb_joueurs = nb;
        this.activer_obs = activer_obs;
        max_unite_per_all = max_u;
        max_object = max_obj;
    }





    @Override
    public void initialiser() {
        System.out.println("initialisation du plateau lancée en mode 2J");
        if(activer_obs){
            genererPierres();
        }
        int x_max = getSizeX();
        int y_maxj1 = getSizeY()/2;
        int y_maxj2 = getSizeY();
        int max_unite_per_all = 8;
        int unite_pose = 0;
        while(unite_pose < max_unite_per_all){//dans le cas où on a pas le nb a poser requis...
            for (int x = 0; x < x_max; x++) {
                for (int y = 0; y < y_maxj1; y++) {
                    int rand = new Random().nextInt(2);
                    if(rand == 0 && unite_pose < max_unite_per_all && grilleCases[x][y].getObstacle() == null && IssueHorsObs(x,y) == true){
                        int rand_u = 0;
                        do{
                            rand_u = new Random().nextInt(8);
                        }while(rand_u==0 || rand_u > (max_unite_per_all-unite_pose));
                        //j1 = 0
                        Elfes e = new Elfes(this, 3, rand_u, 0);
                        e.allerSurCase(grilleCases[x][y]);
                        unite_pose+=rand_u;
                        System.out.println("unite posée J1");
                    }
                }
            }
        }
        unite_pose = 0;
        while(unite_pose < max_unite_per_all){
            for (int x = x_max/2; x < x_max; x++) {
                for (int y = y_maxj1; y < y_maxj2; y++) {
                    int rand = new Random().nextInt(2);
                    if(rand == 0 && unite_pose < max_unite_per_all && grilleCases[x][y].getObstacle() == null && IssueHorsObs(x,y) == true){
                        int rand_u = 0;
                        do{
                            rand_u = new Random().nextInt(8);
                        }while(rand_u==0 || rand_u > (max_unite_per_all-unite_pose));
                        Gobelin g = new Gobelin(this, 3, rand_u, 1);
                        g.allerSurCase(grilleCases[x][y]);
                        unite_pose+=rand_u;
                    }
                }
            }
        }
    }
}


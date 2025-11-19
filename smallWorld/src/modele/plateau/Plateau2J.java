package modele.plateau;

import modele.jeu.Elfes;
import modele.jeu.Gobelin;
import modele.jeu.Unites;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Plateau2J extends Plateau{

    protected int _longueur ;
    protected int _largeur ;

    public static final int SIZE_X = 6;
    public static final int SIZE_Y = 6;
    //rajouter unite choisi par chacun quand on aura le time
    public Plateau2J(){
        super();
        _longueur = 6;
        _largeur = 6;
        grilleCases = new Case[SIZE_X][SIZE_Y];
        initPlateauVide();
    }
    @Override
    public int getSizeX() {
        return _longueur;
    }

    @Override
    public int getSizeY() {
        return _largeur;
    }


    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    private void initPlateauVide() {
        for (int x = 0; x < _longueur; x++) {
            for (int y = 0; y < _largeur; y++) {
                grilleCases[x][y] = new Case(this);
                map.put(grilleCases[x][y], new Point(x, y));
            }
        }
    }

    @Override
    public void initialiser() {
        System.out.println("initialisation du plateau lancée en mode 2J");
        int x_max = _longueur;
        int y_maxj1 = _largeur/2;
        int y_maxj2 = _longueur;
        int max_unite_per_case = 3;
        int unite_pose = 0;
        while(unite_pose < max_unite_per_case){//dans le cas où on a pas le nb a poser requis...
            for (int x = 0; x < x_max; x++) {
                for (int y = 0; y < y_maxj1; y++) {
                    int rand = new Random().nextInt(2);
                    if(rand == 0 && unite_pose < max_unite_per_case){
                        int rand_u = new Random().nextInt(10);
                        Elfes e = new Elfes(this, 3, rand_u);
                        e.allerSurCase(grilleCases[x][y]);
                        unite_pose++;
                        System.out.println("unite posée J1");
                    }
                }
            }
        }
        unite_pose = 0;
        while(unite_pose < max_unite_per_case){
            for (int x = 0; x < x_max; x++) {
                for (int y = y_maxj1; y < y_maxj2; y++) {
                    int rand = new Random().nextInt(2);
                    if(rand == 0 && unite_pose < max_unite_per_case){
                        int rand_u = new Random().nextInt(10);
                        Gobelin g = new Gobelin(this, 3, rand_u);
                        g.allerSurCase(grilleCases[x][y]);
                        unite_pose++;
                    }
                }
            }
        }


    }
}


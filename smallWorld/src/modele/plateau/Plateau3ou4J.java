package modele.plateau;

import modele.jeu.Elfes;
import modele.jeu.Gobelin;
import modele.jeu.Humain;
import modele.jeu.Nain;
import modele.jeu.Unites;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Plateau3ou4J extends Plateau {
    protected int _longueur;
    protected int _largeur;
    protected Case[][] Tab_case;

    public Plateau3ou4J() {
        super();
        _longueur = 7;
        _largeur = 7;
        Tab_case = new Case[7][7];
        initPlateauVide();
    }

    private HashMap<Case, Point> map = new HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence

    private void initPlateauVide() {
        for (int x = 0; x < _longueur; x++) {
            for (int y = 0; y < _largeur; y++) {
                Tab_case[x][y] = new Case(this);
                map.put(Tab_case[x][y], new Point(x, y));
            }
        }
    }
    @Override
    public int getSizeX() {
        return _longueur;
    }

    @Override
    public int getSizeY() {
        return _largeur;
    }

    @Override
    public void initialiser() {
        System.out.println("initialisation du plateau lancée en mode 4J");
        int x_max = _longueur;
        int y_max = _largeur;
        // xmin j1 et j2 c'est 0 et leur max c'est le min de j3 et j4
        int x_minj3 = x_max / 2;
        int x_minj4 = x_minj3;
        // ymin de j1 et j4 c'est 0 et leur max c'est le min de j2 et j3
        int y_minj3 = y_max / 2;
        int y_minj2 = y_minj3;
        int max_unite_per_case = 3;
        int unite_pose = 0;
        //J1
        while (unite_pose < max_unite_per_case) {//dans le cas où on a pas le nb a poser requis...
            for (int x = 0; x < x_minj3; x++) {
                for (int y = 0; y < y_minj2; y++) {
                    int rand = new Random().nextInt(2);
                    if (rand == 0 && unite_pose < max_unite_per_case) {
                        int rand_u = new Random().nextInt(10);
                        Elfes e = new Elfes(this, 3, rand_u);
                        e.allerSurCase(grilleCases[x][y]);
                        unite_pose++;
                        System.out.println("unite posée J1");
                    }
                }
            }
        }
        //j2
        unite_pose = 0;
        while (unite_pose < max_unite_per_case) {
            for (int x = 0; x < x_minj3; x++) {
                for (int y = y_minj2+1; y < y_max; y++) {
                    int rand = new Random().nextInt(2);
                    if (rand == 0 && unite_pose < max_unite_per_case) {
                        int rand_u = new Random().nextInt(10);
                        Gobelin g = new Gobelin(this, 3, rand_u);
                        g.allerSurCase(grilleCases[x][y]);
                        unite_pose++;
                    }
                }
            }
        }
        //j3
        unite_pose = 0;
        while (unite_pose < max_unite_per_case) {
            for (int x = x_minj3+1; x < x_max; x++) {
                for (int y = y_minj3+1; y < y_max; y++) {
                    int rand = new Random().nextInt(2);
                    if (rand == 0 && unite_pose < max_unite_per_case) {
                        int rand_u = new Random().nextInt(10);
                        Humain h = new Humain(this, 3, rand_u);
                        h.allerSurCase(grilleCases[x][y]);
                        unite_pose++;
                    }
                }
            }
        }
        //j4
        unite_pose = 0;
        while (unite_pose < max_unite_per_case) {
            for (int x = x_minj4+1; x < x_max; x++) {
                for (int y = 0; y < y_minj3; y++) {
                    int rand = new Random().nextInt(2);
                    if (rand == 0 && unite_pose < max_unite_per_case) {
                        int rand_u = new Random().nextInt(10);
                        Nain n = new Nain(this, 3, rand_u);
                        n.allerSurCase(grilleCases[x][y]);
                        unite_pose++;
                    }
                }
            }
        }
    }
}

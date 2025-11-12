package modele.plateau;

import java.awt.*;
import java.util.HashMap;

public class Plateau2J extends Plateau{
    protected int _longueur ;
    protected int _largeur ;
    protected Case[][] Tab_case;
    public Plateau2J(){
        super();
        _longueur = 6;
        _largeur = 6;
        Tab_case = new Case[6][6];
        initPlateauVide();
    }

    private HashMap<Case, Point> map = new  HashMap<Case, Point>(); // permet de récupérer la position d'une case à partir de sa référence
    private void initPlateauVide() {
        for (int x = 0; x < _longueur; x++) {
            for (int y = 0; y < _largeur; y++) {
                Tab_case[x][y] = new Case(this);
                map.put(Tab_case[x][y], new Point(x, y));
            }
        }
    }
}

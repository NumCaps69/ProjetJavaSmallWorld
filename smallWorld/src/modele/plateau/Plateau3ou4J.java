package modele.plateau;

import java.awt.*;
import java.util.HashMap;

public class Plateau3ou4J extends Plateau{
    protected int _longueur ;
    protected int _largeur ;
    protected Case[][] Tab_case;
    public Plateau3ou4J(){
        super();
        _longueur = 7;
        _largeur = 7;
        Tab_case = new Case[7][7];
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

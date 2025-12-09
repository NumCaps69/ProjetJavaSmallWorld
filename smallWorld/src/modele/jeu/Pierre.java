package modele.jeu;

import modele.plateau.Biome;
import modele.plateau.Plateau;

public class Pierre extends Obstacle{
    protected Biome biome_spawn;
    protected boolean possible_passage = false; //si on peut passer par dessus ou pas, enfin plus loin ou dessus
    public Pierre(Plateau _plateau) {
        super(_plateau);
        possible_passage = false;
        biome_spawn = Biome.PLAIN;
    }
    @Override
    public String getTypeObstacle() {
        return "Pierre";
    }
    @Override
    public boolean Traversee() {
        return possible_passage;
    }

    @Override
    public boolean canPass(Unites u) {
        if ("Humain".equals(u.getTypeUnite())) {
            return true;
        } else {
            return possible_passage;
        }
    }
}

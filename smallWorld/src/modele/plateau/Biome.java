package modele.plateau;
import java.util.Random;

public abstract class Biome {
    protected Plateau plateau;
    protected Plaine p;
    protected Foret f;
    protected Desert d;
    protected Mountain m;

    public Biome(Plateau _plateau){
        plateau = _plateau;
    }

    public void randomBiome(){

        Random r =  new Random();
        int val = r.nextInt(4);
        switch(val){
            case 0:
                f = new Foret(this.plateau);
                break;
            case 1:
                d = new Desert(this.plateau);
                break;
            case 2:
                m = new Mountain(this.plateau);
                break;
            case 3:
                p = new Plaine(this.plateau);
                break;
            default:
                break;
        }
    }
}

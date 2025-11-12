package modele.plateau;
import java.util.Random;

public abstract class Biome {
    protected Plateau plateau;
    public Biome(Plateau _plateau){
        plateau = _plateau;
    }

    public void randomBiome(){
        Random r =  new Random();
        int val = r.nextInt(4);
        switch(val){
            case 0:
                Foret f = new Foret(this.plateau);
                break;
            case 1:
                Desert d = new Desert(this.plateau);
                break;
            case 2:
                Mountain m = new Mountain(this.plateau);
                break;
            case 3:
                Plaine p = new Plaine(this.plateau);
                break;
            default:
                break;
        }
    }
}

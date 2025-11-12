package modele.plateau;
import java.util.Random;

public abstract class Biome {
    protected Plateau plateau;
    public Biome(Plateau _plateau){
        plateau = _plateau;
    }

    /*public void randomBiome(){
        Random r =  new Random();
        int val = r.nextInt(4);
        switch(val){
            case 0:
                type = typeBiome.FORET;
                break;
            case 1:
                type = typeBiome.DESERT;
                break;
            case 2:
                type = typeBiome.MONTAGNE;
                break;
            case 3:
                type = typeBiome.PLAINE;
                break;
            default:
                break;
        }
    }*/
}

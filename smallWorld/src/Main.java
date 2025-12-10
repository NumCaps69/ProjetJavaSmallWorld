import modele.plateau.*;
import vuecontroleur.VueControleur;
import modele.jeu.Jeu;
import java.util.Scanner;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("CONFIG DU JEU : \n");
                Scanner s = new Scanner(System.in);
                System.out.println("Veuillez choisir le nombre de joueurs : (par défaut 2) - min: 2, max : 4");
                int nbj = s.nextInt();
                if(nbj < 2 || nbj > 4){
                    nbj = 2;
                }
                System.out.println("Voulez-vous activer les obstacles ? O/N");
                String ii = s.next();
                boolean activer;
                if(ii.equalsIgnoreCase("O")){
                    activer = true;
                }
                else{
                    activer = false;
                }
                System.out.println("Nombres d'unités maximum : (par défaut : 8) - min: 2, max: 12");
                int nbunits = s.nextInt();
                if(nbunits < 2 || nbunits > 12){
                    nbunits = 8;
                }
                System.out.println("Combien de tours maximum autorisez vous ? (min : 10, max : 50)");
                int nbtours = s.nextInt();
                if(nbtours < 10 || nbtours > 50){
                    nbtours = 10;
                }
                s.close();
                System.out.println("LE JEU SE LANCE ! \n");
                VueControleur vc = new VueControleur(new Jeu(nbj, activer, nbunits, nbtours));
                vc.setVisible(true);
            }
        };

        SwingUtilities.invokeLater(r);


    }
}
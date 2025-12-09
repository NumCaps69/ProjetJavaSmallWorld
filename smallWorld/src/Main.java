import modele.plateau.*;
import vuecontroleur.VueControleur;
import modele.jeu.Jeu;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                VueControleur vc = new VueControleur(new Jeu(4, true, 8, 40));
                vc.setVisible(true);
            }
        };

        SwingUtilities.invokeLater(r);


    }
}
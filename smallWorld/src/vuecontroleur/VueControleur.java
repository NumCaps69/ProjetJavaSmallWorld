package vuecontroleur;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;


import modele.jeu.Coup;
import modele.jeu.Jeu;
import modele.jeu.Unites;
import modele.plateau.Biome;
import modele.plateau.Case;
import modele.plateau.Plateau;
import modele.jeu.Obstacle;
import modele.jeu.Pierre;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (clic position départ -> position arrivée pièce))
 *
 */
public class VueControleur extends JFrame implements Observer {
    private Plateau plateau; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)
    private Jeu jeu;
    private final int sizeX; // taille de la grille affichée
    private final int sizeY;
    private static final int pxCase = 100; // nombre de pixel par case
    // icones affichées dans la grille
    private Image icoElfes;
    private Image icoHumain;
    private Image icoNain;
    private Image icoGobelin;
    private Image icoDesert;
    private Image icoPlain;
    private Image icoMoutain;
    private Image icoForet;
    private Image icoWater;
    private Image icoStone;


    private JComponent grilleIP;
    private Case caseClic1; // mémorisation des cases cliquées
    private Case caseClic2;


    private ImagePanel[][] tabIP; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône background et front, suivant ce qui est présent dans le modèle)


    public VueControleur(Jeu _jeu) {
        jeu = _jeu;
        plateau = jeu.getPlateau();
        sizeX = plateau.getSizeX();
        sizeY = plateau.getSizeY();

        System.out.println(sizeX);
        System.out.println(sizeY);


        chargerLesIcones();
        placerLesComposantsGraphiques();

        plateau.addObserver(this);

        mettreAJourAffichage();

    }


    private void chargerLesIcones() {
        //icoElfes = new ImageIcon("./data/res/cat.png").getImage();
        //icoDesert = new ImageIcon("./data/res/desert.png").getImage();

        icoElfes = new ImageIcon("smallWorld/data/units/unit_red.png").getImage();
        icoHumain = new ImageIcon("smallWorld/data/units/unit_blue.png").getImage();
        icoNain = new ImageIcon("smallWorld/data/units/unit_yellow.png").getImage();
        icoGobelin = new ImageIcon("smallWorld/data/units/unit_green.png").getImage();
        icoDesert = new ImageIcon("smallWorld/data/terrain/desert_tile.png").getImage();
        icoPlain = new ImageIcon("smallWorld/data/terrain/plain_tile.jpeg").getImage();
        icoForet = new ImageIcon("smallWorld/data/terrain/forest_tile.png").getImage();
        icoMoutain = new ImageIcon("smallWorld/data/terrain/moutain_tile.jpg").getImage();
        icoWater = new ImageIcon("smallWorld/data/terrain/water.png").getImage();
        icoStone = new ImageIcon("smallWorld/data/obstacles/stone_base.png").getImage();

        System.out.println("plain = " + icoPlain.getWidth(null) + "x" + icoPlain.getHeight(null));
        System.out.println("desert = " + icoDesert.getWidth(null) + "x" + icoDesert.getHeight(null));

    }



    private void placerLesComposantsGraphiques() {
        setTitle("Smallworld");
        setResizable(true);
        setSize(sizeX * pxCase, sizeX * pxCase);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        grilleIP = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille


        tabIP = new ImagePanel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                ImagePanel iP = new ImagePanel();

                tabIP[x][y] = iP; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )

                final int xx = x; // permet de compiler la classe anonyme ci-dessous
                final int yy = y;
                // écouteur de clics
                iP.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (caseClic1 == null) {
                            caseClic1 = plateau.getCases()[xx][yy];
                        } else {
                            caseClic2 = plateau.getCases()[xx][yy];
                            jeu.envoyerCoup(new Coup(caseClic1, caseClic2));
                            caseClic1 = null;
                            caseClic2 = null;
                        }

                    }
                });



                grilleIP.add(iP);
            }
        }
        add(grilleIP);
    }

    private Image getImageBiome(Biome biome) {
        return switch (biome) {
            case PLAIN -> icoPlain;
            case DESERT -> icoDesert;
            case FOREST -> icoForet;
            case MOUNTAIN -> icoMoutain;
            case WATER -> icoWater;
            default -> null;
        };
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabIP)
     */
    private void mettreAJourAffichage() {


        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                // biome par défault, à adapter suivant le modèle

                Case c = plateau.getCases()[x][y];
                Image biomeIMG = getImageBiome(c.getBiome());
                tabIP[x][y].setBack(biomeIMG);
                System.out.print("Biome : " + c.getBiome() + " ");
                tabIP[x][y].setObstacle(null);
                tabIP[x][y].setUnit(null);
                System.out.print("\n");
                Unites u = c.getUnites();
                Obstacle o = c.getObstacle();
                if (o != null) {
                    switch (o.getTypeObstacle()) {
                        case "Pierre" -> tabIP[x][y].setObstacle(icoStone);
                        // d'obj a set
                    }
                }
                if (u != null) {
                    tabIP[x][y].setTexte(u.getNombreUnite() + "");

                    switch (u.getTypeUnite()) {
                        case "Elfes"  -> tabIP[x][y].setUnit(icoElfes);
                        case "Humain" -> tabIP[x][y].setUnit(icoHumain);
                        case "Nain"   -> tabIP[x][y].setUnit(icoNain);
                        case "Gobelin"-> tabIP[x][y].setUnit(icoGobelin);
                    }
                }
            }
        grilleIP.repaint();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 

    }
}

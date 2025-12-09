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
    private static final int pxCase = 120; // nombre de pixel par case
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

    private JButton btnFINTOUR;
    private JLabel lblInfoJoueur;


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

        icoElfes = new ImageIcon("smallWorld/data/units/unit_elfe.png").getImage();
        icoHumain = new ImageIcon("smallWorld/data/units/unit_human.png").getImage();
        icoNain = new ImageIcon("smallWorld/data/units/unit_nain.png").getImage();
        icoGobelin = new ImageIcon("smallWorld/data/units/unit_goblin.png").getImage();
        icoDesert = new ImageIcon("smallWorld/data/terrain/desert_tile.png").getImage();
        icoPlain = new ImageIcon("smallWorld/data/terrain/plain_tile.jpeg").getImage();
        icoForet = new ImageIcon("smallWorld/data/terrain/forest_tile.png").getImage();
        icoMoutain = new ImageIcon("smallWorld/data/terrain/moutain_tile.jpg").getImage();
        icoWater = new ImageIcon("smallWorld/data/terrain/water.png").getImage();
        icoStone = new ImageIcon("smallWorld/data/obstacles/stone_base.png").getImage();

        //System.out.println("plain = " + icoPlain.getWidth(null) + "x" + icoPlain.getHeight(null));
        //System.out.println("desert = " + icoDesert.getWidth(null) + "x" + icoDesert.getHeight(null));

    }
    private void clearBordure(){
        for(int x = 0; x<sizeX; x++){
            for(int y = 0; y<sizeY; y++){
                tabIP[x][y].setBorder(null);
            }
        }
    }

    private void afficherDepPossible(int Depx, int Depy) {
        Case caseDep = plateau.getCases()[Depx][Depy];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Case caseArr = plateau.getCases()[x][y];
                if (plateau.peutDeplacer(caseDep,caseArr)){
                    tabIP[x][y].setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                }
            }
        }
        tabIP[Depx][Depy].setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }



    private void placerLesComposantsGraphiques() {
        setTitle("Smallworld");
        setResizable(true);
        int largeur = sizeX * pxCase;
        int hauteur = (sizeY * pxCase) + 100;
        setSize(largeur, hauteur);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        grilleIP = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille

        tabIP = new ImagePanel[sizeX][sizeY];

        lblInfoJoueur = new JLabel("OK FIRST"); // empeche le null d'apparaitre
        lblInfoJoueur.setFont(new Font("Arial", Font.BOLD, 24));
        lblInfoJoueur.setHorizontalAlignment(SwingConstants.CENTER);
        lblInfoJoueur.setOpaque(true);
        lblInfoJoueur.setBackground(Color.LIGHT_GRAY);
        lblInfoJoueur.setPreferredSize(new Dimension(largeur, 40));

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
                            Case c = plateau.getCases()[xx][yy];

                            // VERIFICATION ICI :
                            if (c.getUnites() != null) {
                                // On vérifie si l'ID du joueur de l'unité correspond au joueur courant du jeu
                                if (c.getUnites().getIdJoueur() == jeu.getIndJoueur()) {
                                    caseClic1 = c;
                                    afficherDepPossible(xx, yy);
                                } else {
                                    System.out.println("Ce n'est pas votre unité ! C'est le tour du joueur " + jeu.getIndJoueur());
                                }
                            }
                        }
                        else {
                            clearBordure();
                            if(plateau.getCases()[xx][yy] == caseClic1){
                                caseClic1 = null;
                                return;
                            }
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
        btnFINTOUR = new JButton("FIN TOUR");
        btnFINTOUR.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // reset case clic
                mettreAJourAffichage();
                caseClic1 = null;
                caseClic2 = null;
                jeu.envoyerCoup(new Coup(true));
            }
        });
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(grilleIP, BorderLayout.CENTER);
        panelPrincipal.add(lblInfoJoueur, BorderLayout.NORTH);

        JPanel panelBouton = new JPanel();
        panelBouton.add(btnFINTOUR);
        panelPrincipal.add(panelBouton, BorderLayout.SOUTH);

        add(panelPrincipal);
        //add(grilleIP);
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
    private String getNomRaceParJoueur(int idJoueur) {
        return switch (idJoueur) {
            case 0 -> "Elfes";
            case 1 -> "Gobelins";
            case 2 -> "Humains";
            case 3 -> "Nains";
            default -> "Inconnu";
        };
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabIP)
     */
    private void mettreAJourAffichage() {
        int id = jeu.getIndJoueur();
        String race = getNomRaceParJoueur(id);
        id+=1;
        lblInfoJoueur.setText("Tour du Joueur " + id + " : " + race);

        switch(id) {
            case 1 -> lblInfoJoueur.setForeground(Color.GREEN);
            case 2 -> lblInfoJoueur.setForeground(Color.RED);
            case 3 -> lblInfoJoueur.setForeground(Color.BLUE);
            case 4 -> lblInfoJoueur.setForeground(Color.BLACK);
        }

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                // biome par défault, à adapter suivant le modèle

                Case c = plateau.getCases()[x][y];
                Image biomeIMG = getImageBiome(c.getBiome());
                tabIP[x][y].setBack(biomeIMG);
                //System.out.print("Biome : " + c.getBiome() + " ");
                tabIP[x][y].setObs(null);
                tabIP[x][y].setUnit(null);
                tabIP[x][y].setTexte("");
                //System.out.print("\n");
                Unites u = c.getUnites();
                Obstacle o = c.getObstacle();
                if (o != null) {
                    switch (o.getTypeObstacle()) {
                        case "Pierre" -> tabIP[x][y].setObs(icoStone);
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

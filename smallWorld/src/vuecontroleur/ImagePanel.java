/*
 * Copyright (c) 2025 BELALEM KAISSE & KITA DJESSY-ALBERTO
 *
 * Ce code a été créé dans le cadre du projet LIFAPOO (SmallWorld en JAVA).
 * Toute copie, modification ou redistribution sans autorisation est interdite.
 * Tous droits réservés.
 */
package vuecontroleur;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image imgBackground;
    private Image imgObstacle;
    private Image imgUnit;
    private Image imgEvent;
    private String texte = "";
    /** GETTER & SETTER **/
    public void setBack(Image _imgBackground) {
        imgBackground = _imgBackground;
        repaint();
    }

    public void setObs(Image _imgObstacle) {
        imgObstacle = _imgObstacle;
        repaint();
    }

    public void setUnit(Image _imgUnit) {
        imgUnit = _imgUnit;
        repaint();
    }
    public void setEvent(Image _imgEvent) {
        imgEvent = _imgEvent;
        repaint();
    }
    /*public void setFront(Image _imgFront) {
        imgFront = _imgFront;
        repaint();
    }*/
    public void setTexte(String t) {
        texte = t;
        repaint();
    }

    /**
     * Paint les composants. GRAPHIQUE
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // cadre
        g.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 1, 1);

        if (imgBackground != null) {

            g.drawImage(imgBackground, 2, 2, getWidth()-4, getHeight()-4, this);
        }

        if (imgObstacle != null) {
            int w = (int) (getWidth() * 0.7);
            int h = (int) (getHeight() * 0.7);
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            g.drawImage(imgObstacle, x, y, w, h, this);
        }

        if (imgUnit != null) {
            int w = (int) (getWidth() * 0.5);
            int h = (int) (getHeight() * 0.5);
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            g.drawImage(imgUnit, x, y, w, h, this);
        }
        if (imgEvent != null) {
            int w = (int) (getWidth());
            int h = (int) (getHeight());
            int x = (getWidth() - h) / 2;
            int y = (getHeight() - h) / 2;
            g.drawImage(imgEvent, x, y, w, h, this);
        }
        Color c = new Color(getBackground().getRGB());
        int r = 255 - c.getRed();
        int gr = 255 - c.getGreen();
        int b = 255 - c.getBlue();
        Color inverse = new Color(r, gr, b);
        g.setColor(inverse);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(texte);
        int x = (getWidth() - textWidth) / 2;
        int y = getHeight() - 5;
        g.drawString(texte, x, y);
    }
}

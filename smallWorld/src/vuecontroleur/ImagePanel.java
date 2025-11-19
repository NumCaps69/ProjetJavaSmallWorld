package vuecontroleur;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image imgBackground;
    private Image imgFront;
    private String texte = "";

    public void setBack(Image _imgBackground) {
        imgBackground = _imgBackground;
        repaint();
    }

    public void setFront(Image _imgFront) {
        imgFront = _imgFront;
        repaint();
    }
    public void setTexte(String t) {
        texte = t;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // cadre
        g.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 1, 1);

        if (imgBackground != null) {

            g.drawImage(imgBackground, 2, 2, getWidth()-4, getHeight()-4, this);
        }

        if (imgFront != null) {
            g.drawImage(imgFront, 10, 10, (int) (getWidth()*0.5), (int) (getHeight()*0.5), this);
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

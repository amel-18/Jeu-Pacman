package view;
import java.awt.*;


/**
 * Un carré qui peut être manipulé et qui se dessine lui-même sur un canvas.
 *
 *
 * @inv getWidth() == getHeight()
 */

public class Rect extends Figure
{

    /**
     * Crée un nouveau carré.
     *
     * @param taille la taille initiale du carré
     * @param x la position initiale en x du carré
     * @param y la position initiale en y du carré
     * @param color la couleur initiale du carré.
     *
     * @pre taille >= 0
     */
    public Rect(int width,int height, int x, int y, String color)
    {
        super(width,height, x, y, color);
    }

    /**
     * Dessine le carré avec les spécifications actuelles à l'écran.
     */
    protected void draw()
    {
        Canvas canvas = Canvas.getCanvas();
        canvas.draw(this, getColor(), new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }


}

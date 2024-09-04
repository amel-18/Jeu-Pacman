package view;
import java.awt.*;
import java.awt.geom.*;

/**
 * Un cercle en forme d'arc qui peut être manipulé et qui se dessine sur un canvas.
 *
 * @inv getWidth() == getHeight()
 */

public class Circle extends Figure
{

    /**
     * Crée un nouveau cercle en forme d'arc.
     *
     * @param size la taille initiale du cercle
     * @param x la position x initiale du cercle
     * @param y la position y initiale du cercle
     * @param color la couleur initiale du cercle.
     *
     * @pre size >= 0
     */
    public Circle(int size, int x, int y, String color)
    {
        super(size, size, x, y, color);
    }

    /**
     * Donne la taille du cercle en pixels.
     *
     * @return la taille du cercle en pixels
     */
    public int getSize()
    {
        return getWidth();
    }

    /**
     * Change la taille par la nouvelle taille (en pixels).
     *
     * @param size la nouvelle taille en pixels
     *
     * @pre size >= 0
     */

    public void setSize(int size)
    {
        super.setSize(size, size);
    }

    /**
     * Change la taille par la nouvelle taille (en pixels).
     *
     * @param width la nouvelle largeur en pixels
     * @param height la nouvelle hauteur en pixels
     *
     * @pre width >= 0 && height == width
     */
    public void setSize(int width, int height)
    {
        assert width >= 0 && height == width : "Wrong dimensions";
        super.setSize(width, height);
    }


     /**
     * Dessine le cercle avec les spécifications actuelles à l'écran.
     */
    protected void draw()
    {
        Canvas canvas = Canvas.getCanvas();
        canvas.draw(this, getColor(), new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()));
    }

    /**
     * Vérifie l'invariant de la classe
     */
    protected void invariant() {
        super.invariant();
        assert getWidth() == getHeight() : "Invariant violated: wrong dimensions";
    }
}

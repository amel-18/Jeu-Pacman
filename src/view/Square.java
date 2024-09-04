package view;
import java.awt.*;

/**
 * Un carré pouvant être manipulé et se dessinant sur un canevas.
 *
 * @inv getWidth() == getHeight()
 */

public class Square extends Figure
{

    /**
     * Crée un nouveau carré.
     *
     * @param size la taille initiale du carré
     * @param x la position initiale en x du carré
     * @param y la position initiale en y du carré
     * @param color la couleur initiale du carré.
     *
     * @pre size >= 0
     * @pre color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue") || color.equals("yellow") || color.equals("green")
     */
    public Square(int size, int x, int y, String color)
    {
        super(size, size, x, y, color);
    }

    /**
     * Renvoie la taille du carré en pixels.
     *
     * @return la taille du carré en pixels
     */
    public int getSize()
    {
        return getWidth();
    }

    /**
     * Change la taille à la nouvelle taille (en pixels).
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
     * Change la taille à la nouvelle taille (en pixels).
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
     * Dessine le carré avec les spécifications actuelles à l'écran.
     */
    protected void draw()
    {
        Canvas canvas = Canvas.getCanvas();
        canvas.draw(this, getColor(), new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }

    /**
     * Vérifie l'invariant de la classe
     */
    protected void invariant() {
        super.invariant();
        assert getWidth() == getHeight() : "Invariant violated: wrong dimensions";
    }
}

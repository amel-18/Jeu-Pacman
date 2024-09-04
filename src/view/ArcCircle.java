package view;
import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Un cercle en forme d'arc qui peut être manipulé et qui se dessine sur un canvas.
 *
 * @inv getWidth() == getHeight()
 */

public class ArcCircle extends Figure {
	private Double start;
	private Double extent;

    /**
     * Crée un nouveau cercle en forme d'arc.
     *
     * @param size la taille initiale du cercle
     * @param x la position x initiale du cercle
     * @param y la position y initiale du cercle
     * @param color la couleur initiale du cercle.
     *
     * @pre size >= 0
     * @pre color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue") || color.equals("yellow") || color.equals("green") || color.equals("orange") || color.equals("pink") 
     */
	public ArcCircle(int size, int x, int y, String color,double start,double extent) {
		super(size, size, x, y, color);
		this.start=start;
		this.extent=extent;
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
     * Dessine le cercle en forme d'arc avec les spécifications actuelles à l'écran.
     */
    public void draw()
    {
        Canvas canvas = Canvas.getCanvas();
        canvas.draw(this, getColor(), new Arc2D.Double(getX(),getY(),getWidth(),getHeight(),this.start,this.extent,2));
    }

    /**
     * Vérifie l'invariant de la classe
     */
    public void invariant() {
        super.invariant();
        assert getWidth() == getHeight() : "Invariant violated: wrong dimensions";
    }

    /**
     * Change l'angle de début par un nouvel angle (en degrés).
     *
     * @param angSt le nouvel angle en degrés
     */
    public void setAngleStart(double angSt){
    	this.start=angSt;
    }

    /**
     * Change l'angle de fin par un nouvel angle (en degrés).
     *
     * @param angExt le nouvel angle en degrés
     */
    public void setAngleExtent(double angExt){
			this.extent=angExt;
    }
}

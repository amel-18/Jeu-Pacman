package view;

/**
 * Une figure abstraite qui peut être manipulée et qui se dessine sur un canvas.
 *
 * @inv getWidth() >= 0 && getHeight() >= 0
 * @inv getColor().equals("white") || getColor().equals("black") || getColor().equals("red") || getColor().equals("blue") || getColor().equals("yellow") || getColor().equals("green") || getColor().equals("orange") || getColor().equals("pink")
 */
public abstract class Figure
{
     // width la largeur initial de la figure en pixel
    // height la longueur initial de la figure en pixel
    // x c la la coordonée de position x de la figure en pixel
    // y c la la coordonée de position y de la figure en pixel
    // color c la couleur initial de la figure

    private int width; 
    private int height; 
    private int x; 
    private int y; 
    private String color = "white";

    
    public Figure(int width, int height, int x, int y, String color)
    {
        // assert c la verfication de la condition qui est supposé comme vrai
        assert width >= 0 && height >= 0 : "Wrong dimensions";
        assert color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue") || color.equals("yellow") || color.equals("green")|| color.equals("orange") || color.equals("pink") : "Wrong color";
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.color = color;
        invariant();
    }

 
    
    public int getX() {
        return x;
    }

    
    public int getY() {
        return y;
    }


    public int getWidth() {
        return width;
    }

    
    public int getHeight() {
        return height;
    }

    
    public String getColor() {
        return color;
    }

   //bouger d'une maniere aleatoire
    public void move()
    {
        // rien faire
        invariant();
    }
    /**
     * Déplace la figure de 'dx' et 'dy' pixels.
     *
     * @param dx nombre de pixels à déplacer vers la droite (distance>0) ou la gauche (distance<0).
     * @param dy nombre de pixels à déplacer vers le bas (distance>0) ou vers le haut (distance<0).
     */
    public void move(int dx, int dy)
    {
        erase();
        x += dx;
        y += dy;
        draw();
        invariant();
    }
    /**
     * Change la taille de la figure par la nouvelle taille (en pixels).
     *
     * @param width la nouvelle largeur en pixels
     * @param height la nouvelle hauteur en pixels
     *
     * @pre width >= 0 && height >= 0
     */
    public void setSize(int width, int height)
    {
        assert width >= 0 && height >= 0 : "Wrong dimensions";
        erase();
        this.width = width;
        this.height = height;
        draw();
        invariant();
    }

    /**
     * Change la couleur.
     *
     * @param color la nouvelle couleur
     *
     * @pre color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue") || color.equals("yellow") || color.equals("green") || color.equals("orange") || color.equals("pink")
     */
    public void setColor(String color)
    {
        assert color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue") || color.equals("yellow") || color.equals("green") || color.equals("orange") || color.equals("pink") : "Wrong color";
        this.color = color;
        draw();
        invariant();
    }

    /**
     * Dessine la figure avec les spécifications actuelles à l'écran.
     */
    protected abstract void draw();

    /**
     * Efface la figure à l'écran.
     */
    protected void erase()
    {
        Canvas canvas = Canvas.getCanvas();
        canvas.erase(this);
    }

    /**
     * Vérifie l'invariant de la classe
     */
    protected void invariant() {
        assert width >= 0 && height >= 0 : "Invariant violated: wrong dimensions";
        assert color.equals("white") || color.equals("black") || color.equals("red") || color.equals("blue") || color.equals("yellow") || color.equals("green") || color.equals("orange") || color.equals("pink") : "Invariant violated: wrong color";
    }
}

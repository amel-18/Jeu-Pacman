package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

public class Canvas {
    public static final int WIDTH = 500, HEIGHT = 500;

    private static Canvas canvasSingleton;

    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("Pac-Man Par Wassim et Amal", WIDTH, HEIGHT,
                    Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    // ----- partie instance -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    /**
     * Crée un Canvas.
     * 
     * @param title           titre à afficher dans la fenêtre du Canvas
     * @param width           largeur souhaitée du canvas
     * @param height          hauteur souhaitée du canvas
     * @param backgroundColor la couleur de fond souhaitée du canvas
     */
    private Canvas(String title, int width, int height, Color bgColor) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(30, 30);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColor = bgColor;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();
        canvas.addKeyListener(new KeyboardListener());
        canvas.setFocusable(true);
    }

    /**
     * Vérifie si la touche HAUT est actuellement enfoncée.
     * 
     * @return true si la touche HAUT est actuellement enfoncée
     */
    public boolean isUpPressed() {
        return upPressed;
    }

    /**
     * Vérifie si la touche BAS est actuellement enfoncée
     * 
     * @return true si la touche BAS est actuellement enfoncée
     */
    public boolean isDownPressed() {
        return downPressed;
    }

    /**
     * Vérifie si la touche GAUCHE est actuellement enfoncée
     * 
     * @return true si la touche GAUCHE est actuellement enfoncée
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Vérifie si la touche DROITE est actuellement enfoncée
     * 
     * @return true si la touche DROITE est actuellement enfoncée
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    /**
     * Reinitialise les mouvements
     */
    public void resetMove() {
        rightPressed = false;
        leftPressed = false;
        upPressed = false;
        downPressed = false;
    }

    /**
     * Dessine une chaîne de caractères sur le Canvas.
     * 
     * @param text la chaîne de caractères à afficher
     * @param x    coordonnée x pour le placement du texte
     * @param y    coordonnée y pour le placement du texte
     */
    public void printString(String text, int x, int y) {
        graphic.setFont(new Font("Arial", Font.BOLD, 20));
        graphic.setColor(Color.WHITE);
        graphic.drawString(text, x, y);
        canvas.repaint();
    }

    /**
     * Définit la visibilité du canvas et amène le canvas au premier plan de l'écran
     * lorsqu'il est rendu visible. Cette méthode peut également être utilisée pour
     * amener un canvas déjà visible au premier plan parmi les autres fenêtres.
     * 
     * @param visible valeur booléenne représentant la visibilité souhaitée
     *                du canvas (true ou false)
     */
    public void setVisible(boolean visible) {
        if (graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background color
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Dessine une forme donnée sur le Canvas.
     * 
     * @param referenceObject un objet pour définir l'identité de cette forme
     * @param color           la couleur de la forme
     * @param shape           l'objet de forme à dessiner sur le canvas
     */
    public void draw(Object referenceObject, String color, Shape shape) {
        objects.remove(referenceObject); // just in case it was already there
        objects.add(referenceObject); // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        // redraw();
    }

    /**
     * Efface une forme donnée de l'écran.
     * 
     * @param referenceObject l'objet de forme à effacer
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject); // just in case it was already there
        shapes.remove(referenceObject);
        // redraw();
    }

    /**
     * Définit la couleur de premier plan du Canvas.
     * 
     * @param colorString la nouvelle couleur de premier plan du Canvas
     */
    public void setForegroundColor(String colorString) {
        if (colorString.equals("red")) {
            graphic.setColor(new Color(235, 25, 25));
        } else if (colorString.equals("marron")) {
            graphic.setColor(new Color(193, 136, 69));
        } else if (colorString.equals("black")) {
            graphic.setColor(Color.black);
        } else if (colorString.equals("blue")) {
            graphic.setColor(new Color(89, 204, 253));
        } else if (colorString.equals("violet")) {
            graphic.setColor(new Color(130, 0, 140));
        } else if (colorString.equals("yellow")) {
            graphic.setColor(new Color(255, 230, 0));
        } else if (colorString.equals("pale")) {
            graphic.setColor(new Color(255, 255, 224));
        } else if (colorString.equals("orange")) {
            graphic.setColor(new Color(255, 128, 0));
        } else if (colorString.equals("purple")) {
            graphic.setColor(new Color(238, 130, 238));
        } else if (colorString.equals("green")) {
            graphic.setColor(new Color(80, 160, 60));
        } else if (colorString.equals("pink")) {
            graphic.setColor(new Color(255, 0, 127));
        } else if (colorString.equals("white")) {
            graphic.setColor(Color.white);
        } else if (colorString.equals("redG")) {
            graphic.setColor(new Color(239, 7, 7));
        } else if (colorString.equals("blueG")) {
            graphic.setColor(new Color(102, 254, 255));
        } else if (colorString.equals("orangeG")) {
            graphic.setColor(new Color(250, 156, 0));
        } else if (colorString.equals("pinkG")) {
            graphic.setColor(new Color(255, 152, 153));
        } else {
            graphic.setColor(Color.black);
        }
    }

    /**
     * Attend un nombre spécifié de millisecondes avant de terminer.
     * Cela permet de spécifier facilement un petit délai qui peut être
     * utilisé lors de la création d'animations.
     * 
     * @param milliseconds le nombre de millisecondes
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // Ignorer l'exception pour le moment
        }
    }

    /**
     * Redessine toutes les formes actuellement présentes sur le Canvas.
     */
    public void redraw(int score, int life, String meilleurScore) {
        erase();
        for (Object shape : objects) {
            shapes.get(shape).draw(graphic);
        }
        printString("Score : " + score, 10, 20);
        printString("Vie : " + life, 10, 40);
        canvas.repaint();
        wait(125);
    }

    /**
     * Efface l'ensemble du canvas. (Ne le redessine pas.)
     */
    private void erase() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    /************************************************************************
     * /**
     * Classe interne CanvasPane - le composant canvas réel contenu dans la
     * fenêtre du Canvas. Il s'agit essentiellement d'un JPanel avec la capacité
     * supplémentaire de rafraîchir l'image dessinée sur lui.
     */
    private class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    /************************************************************************
     * /**
     * Classe interne DescriptionForme - contient les informations sur une forme
     * dessinée sur le Canvas.
     */
    private class ShapeDescription {
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color) {
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.fill(shape);
        }
    }

    /************************************************************************
     * /**
     * Classe interne KeyboardListener - écoute les touches HAUT, BAS, DROITE,
     * GAUCHE.
     */
    private class KeyboardListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP:
                    upPressed = true;
                    downPressed = false;
                    leftPressed = false;
                    rightPressed = false;
                    break;
                case KeyEvent.VK_DOWN:
                    upPressed = false;
                    downPressed = true;
                    leftPressed = false;
                    rightPressed = false;
                    break;
                case KeyEvent.VK_LEFT:
                    upPressed = false;
                    downPressed = false;
                    leftPressed = true;
                    rightPressed = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    upPressed = false;
                    downPressed = false;
                    leftPressed = false;
                    rightPressed = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {

            switch (event.getKeyCode()) {
            }

        }
    }

    public void showMessage(String message) {
    }
}

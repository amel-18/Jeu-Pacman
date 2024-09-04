package view;

public class Gomme extends Figure {

  private Figure[] figures;
  private boolean supra;
  private boolean invisible;
  public  boolean changeMap;
  private String couleur;

  private static final String COLOR_WALK = "black";
  private static final String COLOR_GOM_white = "white";
  private static final String COLOR_GOM_blue = "blue";
  private static final String COLOR_GOM_purple = "purple";
  private static final String COLOR_GOM_orange = "orange";
  private static final String COLOR_GOM_green = "green";
  public static final int SCORE_GOMME_BLUE = 100;

  public static final int SCORE_GOMME = 100;

  /**
   * constructeur d'une case de jeu Gomme
   * sans gomme
   * @param  int size          la taille de la case
   * @param  int x             la position absolue x
   * @param  int y             la position absolue y
   */
  public Gomme (int size, int x, int y) {
    super(size, size, x, y, "black");
    this.figures = new Figure[2];
    this.figures[0] = new Square(size, x, y, this.COLOR_WALK);
  }

  /**
   * constructeur d'une case de jeu Gomme
   * avec :
   *   une gomme pouvant etre une supraGomme
   *   une gomme pouvant etre une gomme d'invisiblité
   *   une gomme pouvant etre une gomme de changement de map
   * parametre supra true si est une supragomme
   * parametre invisible true si est une gomme d'invisiblité
   * parametre changeMap true si est une gomme de changement de map
   * @param   size          la taille de la case
   * @param   x             la position absolue x
   * @param   y             la position absolue y
   * @param   supra     determine si l'objet est une superGomme
   * @param  invisible     determine si l'objet est une gomme d'invisiblité
   * @param   changeMap     determine si l'objet est une gomme de changement de map
   */
  public Gomme (int size, int x, int y, boolean supra,boolean invisible, boolean changeMap) {
    this(size, x, y);

    this.supra = supra;
    this.invisible = invisible;
    this.changeMap = changeMap;
    int sg = size;
    if ((this.supra==true)&&(this.invisible==false) && (this.changeMap==false)) {
      sg = size/2;
      int xg = x+(size/2)-(sg/2);
      int yg = y+(size/2)-(sg/2);
      this.figures[1] = new Circle(sg, xg, yg, this.COLOR_GOM_orange);
      // devenir superPacMan et manger les autres fantomes
      
    } else if ((this.supra==false)&&(this.invisible==true) && (this.changeMap==false)) {
      sg = size/2;
      int xg = x+(size/2)-(sg/2);
      int yg = y+(size/2)-(sg/2);
      this.figures[1] = new Circle(sg, xg, yg, this.COLOR_GOM_purple);
      //devenir invisible
    }else if((this.supra==false)&&(this.invisible==false) && (this.changeMap==true)){
            sg = size/2;
      int xg = x+(size/2)-(sg/2);
      int yg = y+(size/2)-(sg/2);
      this.figures[1] = new Circle(sg, xg, yg, this.COLOR_GOM_green);

//changer de map
    }
     else {
      sg = size/5;
    
    int xg = x+(size/2)-(sg/2);
    int yg = y+(size/2)-(sg/2);
    this.figures[1] = new Circle(sg, xg, yg, this.COLOR_GOM_blue);
     }
  }
  


  /**
   * place une gomme sur la case
   * @param Circle c objet formant une gomme
   */
  public void setGomme (Circle c) {
    this.figures[1] = c;
  }

  /**
   * retourne la gomme presente ou non sur la case
   * @return l'objet Circle si exist sinon NULL
   */
  public Figure getGomme () {
    return this.figures[1];
  }

  public boolean getSupra() {
    return this.supra;
  }
  public boolean getChangeMap(){
    return this.changeMap;
  } 
  public boolean getInvisible(){
    return this.invisible;
  }

  /**
   * dessine l'objet case Gomme
   * Square (fond)
   * Circle (gomme si exist)
   */
  public void draw () {
    for (Figure f : this.figures) {
      if (f!=null) {
        f.draw();
      }
    }
  }

}

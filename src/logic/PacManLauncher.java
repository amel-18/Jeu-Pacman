package logic;
import java.util.*;
import data.*;
import view.*;

public class PacManLauncher {

  private data.Map maps;
  private Pacman pacman;
  private Gomme gomme;
  private Ghost[] ghost;
  public static final String UP = "UP";
  public static final String DOWN = "DOWN";
  public static final String LEFT = "LEFT";
  public static final String RIGHT = "RIGHT";
  private static final int NBR_LVL = 2;

  /**
   * Initialise le jeu Pac-Man lors du lancement en créant la carte n°1,
   * le Pac-Man pour toute la partie et les fantômes du niveau.
   */
  public PacManLauncher() {
    this.maps = new data.Map(1);
    this.fillGhost();
    this.pacman = new Pacman(this.maps.getTailleCase(), this.maps.getPMX(), this.maps.getPMY());
    this.pacman.setMap(this.maps);
  }

  public static void main(String[] args) {
    Canvas c = Canvas.getCanvas();
    PacManLauncher pml = new PacManLauncher();
    pml.draw();
    pml.animate(); // Le niveau 1
    int i = 2;
      if (i >= PacManLauncher.NBR_LVL) {
        i = 1;
      }
    
    if (Integer.valueOf(Score.getScore()) < pml.getPacman().getScore()) {
      Score.setScore(pml.getPacman().getScore() + "");
    }
    System.out.println("~~~END~~~");

  }

  /**
   * Change la carte en utilisant le niveau passé en paramètre.
   *
   * @param lvl le niveau souhaité
   */
  public void upLvl(int lvl) {
    this.maps = new data.Map(2);
    this.fillGhost();
    this.pacman.setLocation(this.maps.getPMX(), this.maps.getPMY());
    this.pacman.setMap(this.maps);
  }

  /**
   * Crée tous les fantômes nécessaires en fonction de la carte actuelle.
   */
  public void fillGhost() {
    ArrayList<Integer[]> gs = this.maps.getPGhost();//tab des positions fantome
    this.ghost = new Ghost[gs.size()];

    String[] color = {"redG", "blueG", "orangeG", "pinkG"};
    int cpt = 0;
    int alea = 0;
    int cptGhost = 0;
    for (Integer[] t : gs) {
      alea = (int) (Math.random() * color.length);
      this.ghost[cpt] = new Ghost(this.maps.getTailleCase(), t[0], t[1], color[cptGhost]);
      this.ghost[cpt].setMap(this.maps);

      cpt++;
      cptGhost++;
      if (cptGhost >= color.length) {
        cptGhost = 0;
      }
    }
  }

  /**
   * Dessine la carte, Pac-Man et tous les fantômes d'un niveau.
   */
  public void draw() {
    this.maps.draw();
    this.pacman.draw();
    for (Ghost g : this.ghost) {
      if (g != null) {
        g.draw();
      }
    }
  }

  /**
   * Retourne le Pac-Man de la partie.
   *
   * @return le Pac-Man de la partie
   */
  public Pacman getPacman() {
    return this.pacman;
  }

  /**
   * Démarre le déroulement du jeu en surveillant la touche utilisée par l'utilisateur pour déplacer Pac-Man,
   * puis déplace les fantômes et vérifie les collisions éventuelles entre Pac-Man et les fantômes
   * (sans redessiner toute la carte).
   */
  public void animate() {
    Canvas c = Canvas.getCanvas();
    c.resetMove();
    while ( (this.pacman.getLife() > 0)) {
      //swich the key press, move the pacman
      if (c.isUpPressed()) {
        this.pacman.move(this.UP);
      } else if (c.isDownPressed()) {
        this.pacman.move(this.DOWN);
      } else if (c.isLeftPressed()) {
        this.pacman.move(this.LEFT);
      } else if (c.isRightPressed()) {
        this.pacman.move(this.RIGHT);
      }

      if (this.pacman.getPMSupra()) {
        
        for (Ghost g : this.ghost) {
          g.setEtatPeur();
        }
        this.pacman.resetSupra();
      }

      this.collisionGhost();

      for (Ghost g : this.ghost) {
        g.move();
      }
      this.collisionGhost();
      Canvas.getCanvas().redraw(this.pacman.getScore(), this.pacman.getLife(), Score.getScore());
          if (isGameWon()) {
            System.out.println("jeu Gagné" );
      // Toutes les gommes ont été collectées, vous pouvez sortir de la boucle et arreteé le jeu
            System.exit(0);
    }
          if(isGameLose()){
            System.out.println("Jeu perdu");
            System.exit(0);
          }

  }
}

  /**
   * Vérifie si le jeu est gagné, c'est-à-dire si le nombre de gommes restantes est égal à zéro.
   *
   * @return true si le jeu est gagné
   */
  public boolean isGameWon() {
    return this.maps.getNbGom() == 0;
}


/**
 * Verifi si le jeu est perdu. Pacman n'a plus de vie
 * @return true si le jeu est perdu
 */
public boolean isGameLose(){
  return this.pacman.getLife()==0;
}

  /**
   * Vérifie s'il existe une collision entre Pac-Man et l'un des fantômes.
   * Si oui, Pac-Man perd une vie et toutes les entités sont replacées à leur point de départ pour le niveau en cours.
   *
   * @return true si une collision existe
   */
  private boolean collisionGhost() {
    boolean ret = false;
    int i = 0;

    while (!ret && i < this.ghost.length) {
      if (this.pacman.isPacmanInvisible()) {
        // Si Pac-Man est invisible, il continue de manger les gommes normalement
        this.pacman.makePacmanInvisible();
        //System.out.println("Pacman est invisible"+pacman.isPacmanInvisible());
        if (this.pacman.colisionGhost(this.ghost[i]) && this.ghost[i].getPeur() == 0) {
          // Ne fait rien en cas de collision avec un fantôme
        }
      } else {
        // Si Pac-Man n'est pas invisible, il perd une vie en cas de collision avec un fantôme
        if (this.pacman.colisionGhost(this.ghost[i]) && this.ghost[i].getPeur() == 0) {
          this.pacman.carryOff();
          this.pacman.setLocation(this.maps.getPMX(), this.maps.getPMY());
          this.pacman.makePacmanVisible();
          //System.out.println("Pacman est visible"+pacman.isPacmanInvisible());
          ret = true;

        } else if (this.pacman.colisionGhost(this.ghost[i]) && this.ghost[i].getPeur() > 0) {
          ArrayList<Integer[]> gs = this.maps.getPGhost();
          int cpt = 0;
          for (Integer[] t : gs) {
            if (cpt == i) {
              this.ghost[cpt].setLocation(t[0], t[1]);
              this.ghost[cpt].setEtatNormal();
              //this.pacman.upScoreFantomme();
            }
            cpt++;
          }
        }
      }
      i++;
    }

    if (ret) {
      ArrayList<Integer[]> gs = this.maps.getPGhost();
      int cpt = 0;
      for (Integer[] t : gs) {
        this.ghost[cpt].setLocation(t[0], t[1]);
        cpt++;
      }
    }
    return ret;
  }

}
package logic;
import data.*;
import view.*;

/**
 * Class representant pacman
 * UN arc de cercle jaune avec une ouverture pour la bouche qui représente le pacman
 * avec un nombre de vie
 * et une vitesse fixe
 *
 * @inv getColor().equals("yellow")
 */
public class Pacman extends Entite {

	private static final String PACMAN_COLOR = "yellow"; // the Pacman default color
	private  final String PACMAN_INVISIBLE = "pale";
	private final String PACMAN_ORANGE="orange";
	private String currentColor;//couleur actuelle de pacman
	public static final int OUVERTURE_MIN = 10;//ouverture minimal de la bouche de pacman
	public static final int OUVERTURE_MAX = 40;//ouverture maximal de la bouche de pacman
	private static final int LIFE_START = 3;//nombre de vie de pacman
	public static final int SPEED_PACMAN = 10;//doit etre un multiple de taille de case
	private static final int PALIER = 5000;//palier pour gagner une vie
	private int ModeTimer;// definie combien de temps reste en mode pale
	private boolean isPale; //verifie si pacman est en mode pale
	private boolean isOrange;
	private ArcCircle pac;//representation graphique de pacman
	private int ouverture;// ouverture de la bouche de pacman
	private boolean mouthIsOpen;// ouverture de la bouche de pacman
	private boolean supra;// est ce que pacman a mangé une super gomme (orange)
	private boolean changeMap;//est ce que le pacman a mangé une gomme qui change la map (green)
	private boolean invisible;//est ce que le pacman a mangé une gomme qui le rend invisble (purple)
	private String dernierePosition;
	private String previousMove;//Le dernier mouvement de pacman
	private int life;// la vie du pacman
	private int score;// score de pacman
	private int palier;//prochain palier pour gagner une vie

	/**
	 * Crée un nouveau Pacman.
	 *
	 * @param size la taille de Pacman
	 * @param x la position x absolue de Pacman
	 * @param y la position y absolue de Pacman
	 * @pre size >= 0
	 * @post life >= 0
	 */
	public Pacman(int size, int x, int y) {
		this.pac = new ArcCircle(size, x, y, PACMAN_COLOR, 0, 360);
		this.ModeTimer = 0;
		this.isPale = false;
		this.isOrange=false;
		//initialize the direction of pacman
		this.dernierePosition = PacManLauncher.LEFT;
		this.ouverture = this.OUVERTURE_MIN;
		this.deplaceOuverture(PacManLauncher.LEFT);
		this.life = this.LIFE_START;
		this.supra = false;
		this.changeMap=false;
		this.invisible = false;
		this.previousMove = PacManLauncher.LEFT;
		this.palier = Pacman.PALIER;
	}

	/**
	 * Enlève une vie à Pacman.
	 *
	 * @return true si une vie est enlevée
	 */
	public boolean carryOff () {
		boolean ret = false;
		if (this.life > 0) {
			this.life -= 1;
			ret = true;
		}
		return ret;
	}
	/**
	 * Donne la vie de Pacman.
	 *
	 * @return la vie de Pacman
	 */
	public int getLife () {
		return this.life;
	}


	/**
	 * Augmente le score de Pacman.
	 */
	public void upScoreGomme () {

			// Ajoutez ici la logique pour la gomme violette
			if (this.map.getMap()[this.getColLign()[1]][this.getColLign()[0]] instanceof Gomme) {
				Gomme gomme = (Gomme) this.map.getMap()[this.getColLign()[1]][this.getColLign()[0]];
				if (gomme.getInvisible()) {
					score += 300; // Ajoutez le score approprié pour la gomme violette
					isPale = true;  // Pacman devient invisible
					// Mettez à jour la couleur de Pacman
					updatePacmanColor();
					makePacmanInvisible(); // Rend Pac-Man invisible
					ModeTimer =65;
				}

				if (gomme.getChangeMap()) {
					score += 1000;//vert
					ChangeMap();
				} else if (gomme.getSupra()) {
					score += 500;//orange
					isOrange=true;
					updatePacmanColor();
					ModeTimer=65;
					System.out.println("score supra:" + score);


				} else {
					score += Gomme.SCORE_GOMME_BLUE; // Score par défaut pour les autres gommes
				}
				if (ModeTimer <= 0) {
					makePacmanVisible(); // Rend Pac-Man visible
				}
			}
		if (this.score >= this.palier) {
			this.life++;
			this.palier += Pacman.PALIER;
		}
	}

	/**
	 * Met à jour la couleur de Pacman en fonction de son état (normal ou invisible).
	 */
	private void updatePacmanColor() {
		if (isPale) {
			currentColor = PACMAN_INVISIBLE;
		} else if(isOrange){
			currentColor = PACMAN_ORANGE;
		}else{
			currentColor= PACMAN_COLOR;
		}
		// Mettez à jour la couleur du cercle ArcCircle
		this.pac.setColor(currentColor);
	}

	private void ChangeMap(){
		PacManLauncher pml=new PacManLauncher();
		if(changeMap ){
			
			pml.upLvl(2);
      pml.draw();
      pml.animate();
		}
		
	}


	 /**
	 * Augmente le score de Pacman lorsqu'il mange un fantôme.
	 */
	public void upScoreFantomme () {
		this.score += Ghost.SCORE_FANTOME;
	}
	/**
	 * Donne le score de Pacman.
	 *
	 * @return le score de Pacman
	 */
	public int getScore () {
		return this.score;
	}
	/**
	 * Donne la vitesse de Pacman.
	 *
	 * @return la vitesse de Pacman
	 turn the pacman speed
   */
	public int getSpeed () {
		return Pacman.SPEED_PACMAN;
	}
	/**
	 * Donne la position x de Pacman en pixels.
	 *
	 * @return la position x de Pacman en pixels
	 */
	public int getX () {
		return this.pac.getX();
	}
	/**
	 * Donne la position y de Pacman en pixels.
	 *
	 * @return la position y de Pacman en pixels
	 */
	public int getY () {
		return this.pac.getY();
	}
	/**
	 * Donne la largeur de Pacman en pixels.
	 *
	 * @return la largeur de Pacman en pixels
	 */
	public int getWidth () {
		return this.pac.getWidth();
	}

	/** Pacman devins invisible*/
	public void makePacmanInvisible() {
		this.invisible = true;
	}

	/** Pacman devient visible*/
	public void makePacmanVisible() {
		this.invisible = false;
	}

	/** Cette méthode permet de vérifier si Pac-Man est invisible*/
	public boolean isPacmanInvisible() {
		return this.invisible;
	}

	/**
	 * dessine la representation de pacman
	 */
	public void draw () {
		this.pac.draw();
	}

	/**
	 * Déplace Pacman et toutes les figures qui le composent.
	 *
	 * @param toward La direction vers laquelle Pacman doit se déplacer.
	 */
	public void move (String toward) {
		int dx = 0;
		int dy = 0;

		if (isOrange) {
			ModeTimer--; // Décrémentez la durée du mode orange
			if (ModeTimer <= 0) {
				isOrange = false;  // Désactivez le mode orange
				updatePacmanColor();
			}
		}

		if (isPale) {
			ModeTimer--; // Décrémentez la durée du mode pale
			if (ModeTimer <= 0) {
				isPale = false;  // Désactivez le mode pale
				updatePacmanColor();
			}
		}

		if (this.testMove(toward)) {
			int[] crossMap = this.crossMap(toward);
			dx = crossMap[0];
			dy = crossMap[1];

			crossMap = this.checkColision(toward, dx, dy);
			dx = crossMap[0];
			dy = crossMap[1];

			this.deplaceOuverture(toward);
			this.animateMouth();
			this.move(dx, dy);//deplace le pacman
			this.previousMove = toward;
		} else {
			int[] crossMap = this.crossMap(this.previousMove);
			dx = crossMap[0];
			dy = crossMap[1];

			crossMap = this.checkColision(this.previousMove, dx, dy);
			dx = crossMap[0];
			dy = crossMap[1];

			this.deplaceOuverture(this.previousMove);
			this.animateMouth();
			this.move(dx, dy);//deplace le pacman
		}
		this.invariant();
	}

	/**
	 * Vérifie si un déplacement est possible.
	 *
	 * @param toward La direction vers laquelle Pacman devrait se déplacer.
	 * @return Vrai si le déplacement est possible.
	 */
	private boolean testMove (String toward) {
		boolean haveMoved = false;
		Figure[][] map = this.map.getMap();

		if(this.getX() % this.map.getTailleCase() == 0 && this.getY() % this.map.getTailleCase() == 0) {
			int[] colLign = this.getColLign();
	    int colonne = colLign[0];
	    int ligne = colLign[1];

			Figure fup = map[ligne-1][colonne];
			Figure fdown = map[ligne+1][colonne];
			Figure fleft = map[ligne][colonne-1];
			Figure fright = map[ligne][colonne+1];

			switch (toward) {
				case PacManLauncher.UP :
					if (fup.getClass().getName().compareTo("view.Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
				case PacManLauncher.DOWN :
					if (fdown.getClass().getName().compareTo("view.Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
				case PacManLauncher.LEFT :
					if (fleft.getClass().getName().compareTo("view.Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
				case PacManLauncher.RIGHT :
					if (fright.getClass().getName().compareTo("view.Wall") != 0) {
						haveMoved = true;
					} else {
					}
					break;
			}
		}

		return haveMoved;
	}

	/**
	 * Déplace l'entité d'une variation dx et dy
	 * relative à la position actuelle de l'entité.
	 *
	 * @param dx Le déplacement relatif à x.
	 * @param dy Le déplacement relatif à y.
	 */
	public void move (int dx, int dy) {
		this.pac.move(dx, dy);
	}

	/**
	 * Change la direction de la bouche de Pacman vers la nouvelle direction.
	 *
	 * @param direction La nouvelle direction de la bouche de Pacman.
	 * @pre direction.equals("UP") || direction.equals("LEFT") || direction.equals("DOWN") || direction.equals("RIGHT")
	 */
	private void deplaceOuverture(String direction) {
		int as = 0;
		int ae = 0;

		if (direction.equals(PacManLauncher.UP)) {
			as = (90-ouverture);
			ae = (-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.LEFT)) {
			as = (180-ouverture);
			ae = (-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.DOWN)) {
			as = (270-ouverture);
			ae = (-360+2*ouverture);
		} else if (direction.equals(PacManLauncher.RIGHT)) {
			as = (-ouverture);
			ae = (-360+2*ouverture);
		}

		this.pac.setAngleStart(as);
		this.pac.setAngleExtent(ae);
		this.dernierePosition = direction;
	}


	/**
   * definie les actions que l'entite va devoir realiser avec un objet de type gomme
   * qui est en position (i,j) sur la Map
   * @param map la carte ayant les objets de type gomme
   * @param  i   position colonne pour la Map
   * @param  j   position ligne dans la Map
   * @pre (map[i][j] instanceof Gomme) && (i>=0 && j>=0)
   */
	protected void actionWithGom (Figure[][] map, int i, int j) {
		Figure f = map[i][j];
		if (f instanceof Gomme) {
			Gomme tmp = (Gomme)f;
			if (tmp.getGomme() != null) {
				tmp.setGomme(null);//plus de gomme
				tmp.draw();
				map[i][j] = tmp;
				this.map.pickGom();
				this.upScoreGomme();
				if (tmp.getSupra()) {
					// Mettre tous les fantome en peur
					this.supra = true;
				}
				else if(tmp.getInvisible()){
					this.invisible=true;
				}
				else if(tmp.getChangeMap()){
					this.changeMap=true;
				}

			} else {
				//deja pas de gomme donc rien a faire
			}
		}
	}

	public boolean getPMSupra() {
		return this.supra;
	}

	public void resetSupra() {
		this.supra = false;
	}


	/**
	*	pacman peut agir sur les mur et les gommes
	*/
	public boolean typeCaseToCheck (Figure f) {
		return (f instanceof Wall) || (f instanceof Gomme);
	}

	/**
	*	animation de la bouche du pacman
	*/
	public void animateMouth()
	{
		if(mouthIsOpen) {
			//fermeture
			this.ouverture = this.OUVERTURE_MIN;
		} else {
			//ouverture
			this.ouverture = this.OUVERTURE_MAX;
		}
		this.deplaceOuverture(this.dernierePosition);
		this.mouthIsOpen = !this.mouthIsOpen;
	}

	/**
	 * verifie si une colission avec un fantome est effective
	 * @param  f le potentiel fantome sur le chemin de pacman
	 * @return vrai si une collision est effective
	 */
	public boolean colisionGhost (Ghost f) {
		boolean ret = false;

		int xf = f.getX();//x de fpac
		int yf = f.getY();//y de f
		int sf = f.getWidth();//size f

		int xt = this.getX();//x
		int yt = this.getY();//y
		int st = this.getWidth();//size

		boolean posMinX = (xt < (xf+sf)) || ((xt+st) < (xf+sf));//inferieur bord droit
		boolean posMaxX = (xt > xf) || (xt+st > xf);//superieur bord gauche
		boolean posMinY = (yt < (yf+sf)) || (yt+st < (yf+sf));//inferieur bord bas
		boolean posMaxY = (yt > yf) || (yt+st > yf);//superieur bord haut

		if (posMinX && posMaxX && posMinY && posMaxY) {
			ret = true;
		}

		return ret;
	}

	/**
	* Vérifie l'invariant de la classe
	 */

	protected void invariant() {
		this.pac.invariant();
		assert this.pac.getColor().equals("yellow") : "Invariant violated: wrong dimensions";
	}

}

package view;

public class MapGenerate {

  /** Tableau à deux dimensions de figures contenant toutes les figures de la carte case par case */
  private Figure[][] theMap;

  public MapGenerate (int length) {
    this.theMap = new Figure[length][length];
  }

  public void setFigure (int i, int j, Figure f) {
    this.theMap[i][j] = f;
  }

  public Figure[][] getTheMap () {
    return this.theMap;
  }

  /**
   * Dessine la carte
   */
  public void draw () {
    for (Figure[] fl : this.theMap) {
      for (Figure f : fl) {
        if (f!=null) {
          f.draw();
        }
      }
    }
  }
}

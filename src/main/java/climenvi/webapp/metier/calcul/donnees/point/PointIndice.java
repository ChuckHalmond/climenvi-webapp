package climenvi.webapp.metier.calcul.donnees.point;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Point dans un indice de prototype barres, boites ou courbe
 * 
 * @author Charles MECHERIKI
 *
 */
@Data
@AllArgsConstructor
public class PointIndice implements Comparable<PointIndice> {

	/**
	 * Annee du point (axe abscisse)
	 */
	private int annee;
	
	/**
	 * Valeur du point (axe ordonnées)
	 */
	private double valeur;

	/**
	 * Compare deux points par annee croisante
	 */
	@Override
	public int compareTo(PointIndice point) {
		return Integer.compare(annee, point.annee);
	}
}

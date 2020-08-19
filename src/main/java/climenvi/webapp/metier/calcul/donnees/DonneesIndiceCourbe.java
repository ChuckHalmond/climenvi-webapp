package climenvi.webapp.metier.calcul.donnees;

import java.util.ArrayList;

import climenvi.webapp.metier.calcul.donnees.point.PointIndice;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Donnees relatives a un indice de prototype courbe, soit un ensemble de points 
 * avec le premier et dernier point de la courbe de regression associee
 * 
 * @author Charles MECHERIKI
 *
 */
@Getter
@Setter
@ToString(callSuper=false)
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class DonneesIndiceCourbe extends DonneesIndice {

	/**
	 * Ensemble des points de l'indice
	 */
	private ArrayList<PointIndice> points;

	/**
	 * Premier point de la courbe de regression
	 */
	private PointIndice premierPointRegression;

	/**
	 * Dernier point de la courbe de regression
	 */
	private PointIndice dernierPointRegression;
}

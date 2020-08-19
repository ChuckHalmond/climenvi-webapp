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
 * Donnees relatives a un indice de prototype barres, soit un ensemble de points 
 * avec une reference vers le point minimum et le point maximum
 * 
 * @author Charles MECHERIKI
 *
 */
@Getter
@Setter
@ToString(callSuper=false)
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class DonneesIndiceBarres extends DonneesIndice {

	/**
	 * Ensemble des points de l'indice
	 */
	private ArrayList<PointIndice> points;

	/**
	 * Point minimal, i.e dont la valeur est minimale
	 */
	private PointIndice pointMin;

	/**
	 * Point maximal, i.e dont la valeur est minimale
	 */
	private PointIndice pointMax;
}

package climenvi.webapp.metier.calcul.donnees;

import climenvi.webapp.metier.calcul.donnees.point.PointIndice;
import climenvi.webapp.metier.model.periode.Periode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * Donnees relatives a un indice de prototype boites, soit une periode avec 
 * le point minimum, le point maximum, la moyenne, la mediane, le premier decile
 * et le neuvieme decile associe
 * 
 * @author Charles MECHERIKI
 *
 */
@Getter
@Setter
@ToString(callSuper=false)
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class DonneesIndiceBoites extends DonneesIndice {

	/**
	 * Periode sur laquelle les points initiaux ont ete calculees
	 */
	private Periode periode;
	
	/**
	 * Point minimal, i.e dont la valeur est minimale
	 */
	private PointIndice pointMin;
	
	/**
	 * Point maximal, i.e dont la valeur est maximale
	 */
	private PointIndice pointMax;
	
	/**
	 * Moyenne des points initiaux
	 */
	private double moyenne;
	
	/**
	 * Mediane des points initiaux
	 */
	private double mediane;
	
	/**
	 * Premier decile des points initiaux
	 */
	private double premierDecile;
	
	/**
	 * Neuvieme decile des points initiaux
	 */
	private double neuviemeDecile;
}

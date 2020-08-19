package climenvi.webapp.api.requete;

import climenvi.webapp.metier.model.indice.Indice;
import climenvi.webapp.metier.model.periode.Periode;
import climenvi.webapp.metier.model.representation.Representation;
import climenvi.webapp.metier.model.scenario.Scenario;
import climenvi.webapp.metier.model.site.Site;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Structure de la requete emise par le client pour obtenir le calcul d'un indice
 * 
 * @author Charles MECHERIKI
 *
 */
@Data
@NoArgsConstructor
public class RequeteCalculIndice {

	/**
	 * Scenario demande
	 */
	private Scenario scenario;

	/**
	 * Site demande
	 */
	private Site site;

	/**
	 * Periode demandee
	 */
	private Periode periode;

	/**
	 * Indice demande
	 */
	private Indice indice;
	
	/**
	 * Representation demandee
	 */
	private Representation representation;
}

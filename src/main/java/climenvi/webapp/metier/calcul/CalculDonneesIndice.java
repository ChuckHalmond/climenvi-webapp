package climenvi.webapp.metier.calcul;

import java.util.ArrayList;
import java.util.stream.Collectors;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.calcul.donnees.DonneesIndice;
import climenvi.webapp.metier.calcul.donnees.point.PointIndice;
import climenvi.webapp.metier.calcul.formule.FormuleIndice;
import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;

/**
 * Classe abstraite de calcul de donnees d'indice
 * 
 * @author Charles MECHERIKI
 *
 */
public abstract class CalculDonneesIndice<T extends DonneesIndice> {

	/**
	 * Calcul l'ensemble des donnees de l'indice a partir de la requete, la formule et les archives donnees
	 * 
	 * @param requete	la requete a l'origine du calcul
	 * @param formule	la formule de l'indice 
	 * @param archives	les archives de donnees
	 * @return			les donnees d'indice calculees
	 */
	public abstract T calculDonnees(RequeteCalculIndice requete, FormuleIndice formule, ArrayList<Archive> archives);
	
	/**
	 * Calcul les points de l'indice a partir de la requete, la formule et les archives et donnees
	 * 
	 * @param requete	la requete a l'origine du calcul
	 * @param formule	la formule de l'indice
	 * @param archives	les archives de donnees
	 * @return			les points de l'indice calcules
	 */
	public ArrayList<PointIndice> calculPointsIndice(RequeteCalculIndice requete, FormuleIndice formule, ArrayList<Archive> archives) {
		
		// On recupere les filtres de la formule
		ArrayList<FiltreArchive> filtres = formule.filtres(requete);
		
		return new ArrayList<PointIndice>(

				// On traverse toutes les archives
				archives
					.stream()
					.filter(
							// On les filtre
							archive -> filtres.stream().allMatch(filtre -> filtre.accepte(archive))
					)
					.collect(
							Collectors.collectingAndThen(

								// On applique la formule de collecte aux archives
								formule.collecte(),
								
								map -> map
										.entrySet()
				                        .stream()
				                        .map(
				                        		// On map le resultat en un PointIndice
				                        		entry -> new PointIndice(
				                        				entry.getKey(),
				                        				entry.getValue()
				                        		)
		                        		)
				                        .collect(
				                        		Collectors.toList()
				                        )
							)
		          )
		);
	}
}
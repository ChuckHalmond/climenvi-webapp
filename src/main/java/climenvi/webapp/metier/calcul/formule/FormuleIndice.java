package climenvi.webapp.metier.calcul.formule;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collector;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;
import lombok.NoArgsConstructor;

/**
 * Classe abstraite de formule spécifique à un indice
 *  
 * @author Charles MECHERIKI
 *
 */
@NoArgsConstructor
public abstract class FormuleIndice {

	/**
	 * Renvoie la liste des filtres specifiques a appliquer sur les archives avant le calcul de l'indice
	 * 
	 * Par exemple, si l'on souhaite calculer l'indice de Winkler (somme des températures base 10 du
	 * 1er avril au 31 octobre), on va renvoyer les filtres suivants:
	 * <ul>
	 * 	<li>FiltreArchiveTemperatureMin(10d)</li>
	 * 	<li>FiltreArchiveEntreDeuxDatesAnneeQuelconque(LocalDate.of(0, 4, 1), LocalDate.of(0, 10, 31))</li>
	 * </ul>
	 * pour filtrer les archives de température inférieure à 10 ou en dehors de l'interval de temps de l'indice
	 * 
	 * @param requete	la requete associee au calcul de donnees
	 * @return la liste des filtres specifiques a appliquer lors du calcul des donnees
	 */
	public abstract ArrayList<FiltreArchive> filtres(RequeteCalculIndice requete);

	/**
	 * Renvoie la formule de collecte a appliquer pour le calcul des points de l'indice
	 * Cette formule se situe apres avoir filtre les donnees
	 * 
	 * Par exemple, si l'on souhaite calculer l'indice de Winkler (somme des températures base 10 du
	 * 1er avril au 31 octobre), on va renvoyer :
	 * <pre>
	 * Collectors.groupingBy(		
	 * 	// On groupe les archives par annee	
	 *	archive -&gt; archive.getDate().getYear(),
	 *	Collectors.summingDouble(
	 *		// On calcul la somme des temperatures base 10 de chaque groupe
	 *		archive -&gt; ((archive.getTemperatureMin() + archive.getTemperatureMax()) / 2) - 10
	 *	)
	 * );
	 * </pre>
	 * 
	 * @return la formule de collecte a appliquer lors du calcul des donnees de l'indice
	 */
	public abstract Collector<Archive, ?, Map<Integer, Double>> collecte();
}

package climenvi.webapp.metier.calcul.formule.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.calcul.formule.FormuleIndice;
import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.filtre.impl.FiltreArchiveEntreDeuxDates;
import climenvi.webapp.metier.model.archive.Archive;

/**
 * Implementation de FormuleIndice pour l'indice TMA
 * 
 * @author Charles MECHERIKI
 *
 */
public class FormuleIndiceTMA extends FormuleIndice {

	@Override
	public ArrayList<FiltreArchive> filtres(RequeteCalculIndice requete) {
		return new ArrayList<FiltreArchive>(
				Arrays.asList(
					// On extrait les valeurs dans la periode de la requete
					new FiltreArchiveEntreDeuxDates(
							requete.getPeriode().getDateDebut(), requete.getPeriode().getDateFin()
					)
				)
		);
	}

	@Override
	public Collector<Archive, ?, Map<Integer, Double>> collecte() {
		return Collectors.groupingBy(
		
				// On les groupe par annee
				archive -> archive.getDate().getYear(),
				
				// On calcul la moyenne de temperature de chaque groupe
				Collectors.averagingDouble(
						archive -> (archive.getTemperatureMin() + archive.getTemperatureMax()) / 2
				)
		);
	}
}
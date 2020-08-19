package climenvi.webapp.metier.calcul.formule.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.calcul.formule.FormuleIndice;
import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.filtre.impl.FiltreArchiveEntreDeuxDates;
import climenvi.webapp.metier.filtre.impl.FiltreArchiveEntreDeuxDatesAnneeQuelconque;
import climenvi.webapp.metier.filtre.impl.FiltreArchiveTemperatureMin;
import climenvi.webapp.metier.model.archive.Archive;

/**
 * Implementation de FormuleIndice pour l'indice WINKLER
 * 
 * @author Charles MECHERIKI
 *
 */
public class FormuleIndiceWINKLER extends FormuleIndice {

	@Override
	public ArrayList<FiltreArchive> filtres(RequeteCalculIndice requete) {
		return new ArrayList<FiltreArchive>(Arrays.asList(
			// On extrait les valeurs dans la periode de la requete
			new FiltreArchiveEntreDeuxDates(
					requete.getPeriode().getDateDebut(), requete.getPeriode().getDateFin()
			),
			// On veut les archives entre le 1er avril et le 31 octobre de chaque annee
			new FiltreArchiveEntreDeuxDatesAnneeQuelconque(
					LocalDate.of(0, 4, 1),	// 1er avril
					LocalDate.of(0, 10, 31)	// 31 octobre
			),
			// On veut les archives dont la temperature est d'au moins 10°C
			new FiltreArchiveTemperatureMin(10d)
		));
	}
	
	@Override
	public Collector<Archive, ?, Map<Integer, Double>> collecte() {
		return Collectors.groupingBy(
										
				// On les groupe par annee
				archive -> archive.getDate().getYear(),
				
				// On calcul la somme des temperatures base 10 de chaque groupe
				Collectors.summingDouble(
						archive -> ((archive.getTemperatureMin() + archive.getTemperatureMax()) / 2) - 10
				)
		);
	}
}

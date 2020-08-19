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
import climenvi.webapp.metier.model.archive.Archive;

/**
 * Implementation de FormuleIndice pour l'indice CEP
 * 
 * @author Charles MECHERIKI
 *
 */
public class FormuleIndiceCEP extends FormuleIndice {

	@Override
	public ArrayList<FiltreArchive> filtres(RequeteCalculIndice requete) {
		return new ArrayList<FiltreArchive>(
				Arrays.asList(
						// On extrait les valeurs dans la periode de la requete
						new FiltreArchiveEntreDeuxDates(
								requete.getPeriode().getDateDebut(), requete.getPeriode().getDateFin()
						),
						// On considere que la periode estivale
						new FiltreArchiveEntreDeuxDatesAnneeQuelconque(
								LocalDate.of(0, 6, 1),	// 1er juin
								LocalDate.of(0, 8, 31)	// 31 aout
						)
				)
		);
	}

	@Override
	public Collector<Archive, ?, Map<Integer, Double>> collecte() {
		return Collectors.groupingBy(
				
				// On groupe les archives par annee
				archive -> archive.getDate().getYear(),
				
				// On calcul la somme de precipitations de chaque groupe
				Collectors.summingDouble(
						archive -> archive.getPrecipitation()
				)
		);
	}
}

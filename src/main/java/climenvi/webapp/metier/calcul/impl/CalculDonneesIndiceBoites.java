package climenvi.webapp.metier.calcul.impl;

import java.util.ArrayList;
import java.util.Comparator;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.calcul.CalculDonneesIndice;
import climenvi.webapp.metier.calcul.donnees.DonneesIndiceBoites;
import climenvi.webapp.metier.calcul.donnees.point.PointIndice;
import climenvi.webapp.metier.calcul.formule.FormuleIndice;
import climenvi.webapp.metier.model.archive.Archive;
import climenvi.webapp.metier.model.periode.Periode;

/**
 * Classe abstraite de calcul de donnees d'indice de prototype boites
 * 
 * @author Charles MECHERIKI
 *
 */
public class CalculDonneesIndiceBoites extends CalculDonneesIndice<DonneesIndiceBoites> {

	@Override
	public DonneesIndiceBoites calculDonnees(
			RequeteCalculIndice requete,
			FormuleIndice formule,
			ArrayList<Archive> archives
		) throws IllegalArgumentException {

		// Calcul des point
		ArrayList<PointIndice> points = calculPointsIndice(requete, formule, archives);

		// Recupere la periode
		Periode periode = requete.getPeriode();

		// Calcul du point min
		PointIndice pointMin = calculPointMin(points);
		
		// Calcul du point max
		PointIndice pointMax = calculPointMax(points);
		
		// Calcul de la moyenne
		double moyenne = calculMoyenne(points);

		// Calcul de la mediane
		double mediane = calculerIemeDecile(points, 5d);
		
		// Calcul du premier decile
		double premierDecile = calculerIemeDecile(points, 1d);
		
		// Calcul du neuvieme decile
		double neuviemeDecile = calculerIemeDecile(points, 9d);
		
		if (points.size() > 0) {
			return new DonneesIndiceBoites(
					periode,
					pointMin,
					pointMax,
					moyenne,
					mediane,
					premierDecile,
					neuviemeDecile
			);
		}

		throw new IllegalArgumentException("Aucun point adéquat n'a pu être calculé depuis les archives");
	}
	
	/**
	 * Calcul le point de valeur minimale parmi les points donnes
	 * 
	 * @param points	les points de l'indice
	 * @return			le point de valeur minimale parmi les points donnes
	 */
	public PointIndice calculPointMin(ArrayList<PointIndice> points) {
		return points.stream()
				.min(
						Comparator.comparing(PointIndice::getValeur)
				).get();
	}
	
	/**
	 * Calcul le point de valeur maximale parmi les points donnes
	 * 
	 * @param points	les points de l'indice
	 * @return			le point de valeur maximale parmi les points donnes
	 */
	public PointIndice calculPointMax(ArrayList<PointIndice> points) {
		return points.stream()
				.max(
						Comparator.comparing(PointIndice::getValeur)
				).get();
	}
	
	/**
	 * Calcul la valeur moyenne de l'ensemble des points donnees
	 * 
	 * @param points	les points de l'indice
	 * @return			la valeur moyenne de l'ensemble des points donnees
	 */
	public double calculMoyenne(ArrayList<PointIndice> points) {
		return points.stream()
				.mapToDouble(PointIndice::getValeur)
				.average()
				.getAsDouble();
	}

	/**
	 * Calcul la valeur du Ieme decile de l'ensemble de points donnes
	 * 
	 * @param points	les points de l'indice
	 * @param decile	numero du decile (entre 0 et 10)
	 * @return			la valeur mediane de l'ensemble de points donnes
	 */
	public double calculerIemeDecile(ArrayList<PointIndice> points, double decile) {
		double centile = 10 * decile;
		return new Percentile((int)centile).evaluate(
				points.stream().map(PointIndice::getValeur).mapToDouble(Number::doubleValue).toArray(),
				centile
		);
	}
}

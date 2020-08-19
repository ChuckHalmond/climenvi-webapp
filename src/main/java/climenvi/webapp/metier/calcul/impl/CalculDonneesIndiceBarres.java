package climenvi.webapp.metier.calcul.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.calcul.CalculDonneesIndice;
import climenvi.webapp.metier.calcul.donnees.DonneesIndiceBarres;
import climenvi.webapp.metier.calcul.donnees.point.PointIndice;
import climenvi.webapp.metier.calcul.formule.FormuleIndice;
import climenvi.webapp.metier.model.archive.Archive;

/**
 * Classe abstraite de calcul de donnees d'indice de prototype barres
 * 
 * @author Charles MECHERIKI
 *
 */
public class CalculDonneesIndiceBarres extends CalculDonneesIndice<DonneesIndiceBarres> {

	@Override
	public DonneesIndiceBarres calculDonnees(
			RequeteCalculIndice requete,
			FormuleIndice formule,
			ArrayList<Archive> archives
		) throws IllegalArgumentException {

		// Calcul des point
		ArrayList<PointIndice> points = calculPointsIndice(requete, formule, archives);
		
		// Calcul du point min
		PointIndice pointMin = calculerPointMin(points);
		
		// Clacul du point max
		PointIndice pointMax = calculerPointMax(points);
		
		// Trie les points par date croissante
		Collections.sort(points);
		
		if (points.size() > 0) {
			return new DonneesIndiceBarres(
					points,
					pointMin,
					pointMax
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
	public PointIndice calculerPointMax(ArrayList<PointIndice> points) {
		return points.stream()
				.max(
						Comparator.comparing(PointIndice::getValeur)
				).get();
	}

	/**
	 * Calcul le point de valeur maximale parmi les points donnes
	 * 
	 * @param points	les points de l'indice
	 * @return			le point de valeur maximale parmi les points donnes
	 */
	public PointIndice calculerPointMin(ArrayList<PointIndice> points) {
		return points.stream()
				.min(
						Comparator.comparing(PointIndice::getValeur)
				).get();
	}
}

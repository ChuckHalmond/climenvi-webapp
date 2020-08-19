package climenvi.webapp.metier.calcul.impl;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.calcul.CalculDonneesIndice;
import climenvi.webapp.metier.calcul.donnees.DonneesIndiceCourbe;
import climenvi.webapp.metier.calcul.donnees.point.PointIndice;
import climenvi.webapp.metier.calcul.formule.FormuleIndice;
import climenvi.webapp.metier.model.archive.Archive;

/**
 * Classe abstraite de calcul de donnees d'indice de prototype courbe
 * 
 * @author Charles MECHERIKI
 *
 */
public class CalculDonneesIndiceCourbe extends CalculDonneesIndice<DonneesIndiceCourbe> {

	@Override
	public DonneesIndiceCourbe calculDonnees(
			RequeteCalculIndice requete,
			FormuleIndice formule,
			ArrayList<Archive> archives
		) throws IllegalArgumentException {

		// Calcul des points
		ArrayList<PointIndice> points = calculPointsIndice(requete, formule, archives);
		
		// Trie les points par date croissante
		Collections.sort(points);
		
		if (points.size() > 0) {
			
			// Creation d'une courbe de regression
			SimpleRegression regression = calculRegression(points);
		
			PointIndice premierPointRegression = null, dernierPointRegression = null;

			// Calcul des coordonnees du premier point de la courbe
			premierPointRegression = new PointIndice(
					points.get(0).getAnnee(), regression.getIntercept() + points.get(0).getAnnee() * regression.getSlope()
			);
			
			// Calcul des coordonnees du dernier point de la courbe
			dernierPointRegression = new PointIndice(
					points.get(points.size() - 1).getAnnee(), regression.getIntercept() + points.get(points.size() - 1).getAnnee() * regression.getSlope()
			);
	
			// Renvoie l'ensemble des donnees precedentes
			return new DonneesIndiceCourbe(
					points,
					premierPointRegression,
					dernierPointRegression
			);
		}
		
		throw new IllegalArgumentException("Aucun point adéquat n'a pu être calculé depuis les archives");
	}
	
	/**
	 * Calcul la courbe de regression des points donnees
	 * 
	 * @param points	les points de l'indice
	 * @return			la courbe de regression des points donnees
	 */
	public SimpleRegression calculRegression(ArrayList<PointIndice> points) {
		
		SimpleRegression regression = new SimpleRegression(true);
		
		double[][] data = 
				points
				.stream()
				.map(
						point -> new double[] {
								(double)point.getAnnee(), point.getValeur()
						}
				)
				.toArray(double[][]::new);
		
		regression.addData(
				data
		);
		
		return regression;
	}

}
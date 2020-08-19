/**
 * ---------------
 * Outils Amcharts
 * ---------------
 * 
 * Quelques variables et methodes utilitaires utilisant la bibliotheque amcharts
 */

/** Couleurs utilisees frequemment dans les representations */
var outils_am_obtenirCouleurPrincipaleBis,
	outils_am_obtenirCouleurRegression,
	outils_am_obtenirCouleursPeriodes,
	outils_am_obtenirCouleursClairesPeriodes,
	outils_am_obtenirCouleursSombresPeriodes;

$(document).ready(function() {
	am4core.ready(function() {
		
		// Variables de couleurs
		var outils_am_couleurPrincipaleBis,
			outils_am_couleurRegression,
			outils_am_couleursPeriodes,
			outils_am_couleursClairesPeriodes,
			outils_am_couleursSombresPeriodes;
		
		// Utilisation du theme avec animations
		am4core.useTheme(am4themes_animated);
		
		// Chargement des representations uniquement lorsqu'elles sont visibles
		am4core.options.onlyShowOnViewport = true;
		
		// Couleur proche de la primary-color du theme bootstrap (vert)
		outils_am_couleurPrincipaleBis = am4core.color("#99C78F");
		
		// Gris utilise pour les droites de regression
		outils_am_couleurRegression = am4core.color("#858796");
		
		// Couleurs pour chacune des periodes (Reference, Futur proche, Futur lointain, Futur, Global)
		// respectivement bleu pale, vert pale et orange pale
		outils_am_couleursPeriodes = [
			am4core.color("#a0c5e8"),
			am4core.color("#c3deb7"),
			am4core.color("#f9cc9d")
		];
		
		// Couleurs plus claires que outils_am_couleursPeriodes
		outils_am_couleursClairesPeriodes = outils_am_couleursPeriodes.map(
				function(couleur) {
					return couleur.lighten(0.8);
				}
		);
		
		// Couleurs plus sombres que outils_am_couleursPeriodes
		outils_am_couleursSombresPeriodes = outils_am_couleursPeriodes.map(
				function(couleur) {
					return couleur.brighten(-0.2);
				}
		);
		
		outils_am_obtenirCouleurPrincipaleBis = function() {
			return outils_am_couleurPrincipaleBis;
		};
		
		outils_am_obtenirCouleurRegression = function() {
			return outils_am_couleurRegression;
		};
		
		outils_am_obtenirCouleursPeriodes = function(indice) {
			return outils_am_couleursPeriodes[
				indice % outils_am_couleursPeriodes.length
			];
		};
		
		outils_am_obtenirCouleursClairesPeriodes = function(indice) {
			return outils_am_couleursClairesPeriodes[
				indice % outils_am_couleursClairesPeriodes.length
			];
		};
		
		outils_am_obtenirCouleursSombresPeriodes = function(indice) {
			return outils_am_couleursSombresPeriodes[
				indice % outils_am_couleursSombresPeriodes.length
			];
		};
	});
});
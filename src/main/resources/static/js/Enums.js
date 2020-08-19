/**
 * -----
 * Enums
 * -----
 * 
 * Recupere les differentes enumerations (sites, scenarios, periodes, indices)
 * depuis l'EnumsAPI (backend)
 * 
 */

/** Conteneur pour les enumerations */
var Enums = {
	Sites: {},
	Scenarios: {},
	Periodes: {},
	Indices: {}
};

/**
 * Recupere la liste des enumerations via l'EnumsAPI (backend) et declenche un evenement
 * lorsqu'elles sont chargees / accessibles
 */
$(document).ready(function() {

	// On cree un evenement pour savoir quand les enumerations seront chargees
	// pour ne pas y faire reference avant qu'elles n'existent
	
	var EnumsPret = jQuery.Event("EnumsPret");

	EnumsPret.restants = Object.keys(Enums);
	EnumsPret.fait = function(nomEnum) {
		EnumsPret.restants.splice(
				EnumsPret.restants.indexOf(nomEnum), 1
		);
		Logger.debug("Enum '" + nomEnum + "' chargé");
		
		if (EnumsPret.restants.length == 0) {
			$(document).trigger(EnumsPret);
		}
	}
	
	// On va chercher les differentes enumerations depuis l'API
	
	Object.keys(Enums).forEach(function(nomEnum) {
		outils_squeletteAjaxPOST(nomEnum, "/api/enums/" + nomEnum.toLowerCase(), function(reponse) {
			Enums[nomEnum] = reponse;
			EnumsPret.fait(nomEnum);
		});
	});
});

/**
 * Une fois les enumerations recuperees, on cree des methodes pour faciliter leur acces
 */
$(document).on("EnumsPret", function() {
	
	Enums.obtenirIndicePeriodeDepuisTitrePeriode = function(titrePeriode) {
		var indicePeriode = 0;
	
		return Enums.Periodes.indexOf(
			Enums.Periodes.filter(
				periode => {
					return periode.titre == titrePeriode
				}
			)[0]
		);
	};
	
	Enums.obtenirIndicePeriodeDepuisAnnee = function(annee) {
		var indicePeriode = 0;
	
		while (
				annee > Enums.Periodes[indicePeriode].dateFin.year
				&& indicePeriode < Enums.Periodes.length - 1
		){
				indicePeriode++;
		}
	
		return indicePeriode;
	};
});
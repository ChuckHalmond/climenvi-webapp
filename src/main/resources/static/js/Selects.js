/**
 * -------
 * Selects
 * -------
 * 
 * Remplissage des select avec les valeurs d'enumerations, gestion des 
 * evenements utilisateurs (selections), mise a jour automatique des representations, etc.
 */

/**
 * Valeur des enums actuellement selectionnees par l'utilisateur
 */
var Site = {},
	Scenario = {},
	Periode = {};

/**
 * Methodes de mise a jour des variables globale ci-dessus, appellees
 * au changement des select
 */
var MajSite,
	MajScenario,
	MajPeriode;

/**
 * Elements HTML relatifs aux select
 */
var SiteSelect,
	ScenarioSelect,
	PeriodeSelect,
	IndicesSelect;

$(document).ready(function() {
	
	SiteSelect = document.getElementById("site-select");
	ScenarioSelect = document.getElementById("scenario-select");
	PeriodeSelect = document.getElementById("periode-select");
	IndicesSelect = document.getElementById("indices-select");
	
	$(document).on("EnumsPret", function() {

		/*--------------------------------------------------*/
		/*		Remplissage des selects	et mises a jour		*/
		/*--------------------------------------------------*/
		
		/*------------------*/
		/*		Sites		*/
		/*------------------*/
		
		Enums.Sites.forEach(function(site) {
			var option = document.createElement("option");
			
			option.value = site.nom;
			option.text = site.nom;
			
			option.setAttribute("data-icon", "fa-map-marker-alt");
			option.setAttribute("data-subtext", "(" + site.codePostal + ")");
			
			SiteSelect.appendChild(option);
		});
		
		SiteSelect.firstChild.setAttribute("selected", "selected");
		
		MajSite = function() {
			Site = Enums.Sites.filter(
				site => {
					return site.nom == SiteSelect.options[SiteSelect.selectedIndex].value
				}
			)[0];
		}
		
		SiteSelect.addEventListener("change", function() {
			MajSite();
			MajRepresentations();
		});

		/*----------------------*/
		/*		Scenarios		*/
		/*----------------------*/
		
		// On retire le scenario de reference pour simplifier
		Enums.Scenarios.shift(0, 1);
		
		Enums.Scenarios.forEach(function(scenario) {
			var option = document.createElement("option");
			
			option.value = scenario.titre;
			option.text = "Scénario " + scenario.titre;
			option.setAttribute("data-icon", "fa-chart-line");
			
			ScenarioSelect.appendChild(option);
		});

		ScenarioSelect.firstChild.setAttribute("selected", "selected");
		
		MajScenario = function() {
			Scenario = Enums.Scenarios.filter(
				scenario => {
					return scenario.titre == ScenarioSelect.options[ScenarioSelect.selectedIndex].value
				}
			)[0];
		}
		
		ScenarioSelect.addEventListener("change", function() {
			MajScenario();
			MajRepresentations();
		});

		/*----------------------*/
		/*		Periodes		*/
		/*----------------------*/
			
		Enums.Periodes.forEach(function(periode) {
			var option = document.createElement("option");
			
			option.value = periode.titre;
			option.text = "Période : " + periode.titre;
			
			option.setAttribute("data-icon", "fa-history");
			option.setAttribute("data-subtext", "i.e. de " + periode.dateDebut.year + " à " + periode.dateFin.year);
			
			PeriodeSelect.appendChild(option);
		});
		
		PeriodeSelect.firstChild.setAttribute("selected", "selected");
		
		MajPeriode = function() {
			Periode = Enums.Periodes.filter(
				periode => {
					return periode.titre == PeriodeSelect.options[PeriodeSelect.selectedIndex].value
				}
			)[0];
		}
		
		PeriodeSelect.addEventListener("change", function() {
			MajPeriode();
			MajRepresentations();
		});

		/*------------------*/
		/*		Indices		*/
		/*------------------*/
		
		Enums.Indices.forEach(function(indice) {
			var option = document.createElement("option");
			
			option.value = indice.acronyme;
			option.text = indice.titre;
			
			option.setAttribute("selected", "selected");
			
			IndicesSelect.appendChild(option);
		});
		
		MajIndicesSelectionnes = function() {
			var indicesDivs = document.querySelectorAll(".indice");
			var nomsindicesSelectionnes = $(IndicesSelect).val();

			indicesDivs.forEach(function(indiceDiv) {
				var nomIndice = indiceDiv.getAttribute("data-indice");
				
				if (nomsindicesSelectionnes.indexOf(nomIndice) >= 0) {
					if (indiceDiv.className.indexOf(" d-none") >= 0) {
						indiceDiv.className = indiceDiv.className.replace(" d-none", "");
					}
				}
				else {
					if (indiceDiv.className.indexOf(" d-none") < 0) {
						indiceDiv.className += " d-none";
					}
				}
			});
		}
		
		IndicesSelect.addEventListener("change", MajIndicesSelectionnes);
		
		/*--------------------------------------*/
		/*		Activation de selectpicker		*/
		/*--------------------------------------*/
		
		$(SiteSelect).selectpicker({
			width: "auto",
			style: "btn-white",
			iconBase: "fa",
			showSubtext: true
		});
		
		$(ScenarioSelect).selectpicker({
			width: "auto",
			style: "btn-primary",
			iconBase: "fa",
			showSubtext: true
		});
		
		$(PeriodeSelect).selectpicker({
			width: "auto",
			iconBase: "fa",
			showSubtext: true
		});
		
		$(IndicesSelect).selectpicker({
			width: "auto",
			selectedTextFormat: "count",
			countSelectedText: "{0} indices affichés sur {1}",
			actionsBox: true,
			selectAllText: "Sélectionner les indices",
			deselectAllText: "Déselectionner les indices",
			liveSearch: true,
			liveSearchPlaceholder: "Rechercher un indice"
		});
	});
});
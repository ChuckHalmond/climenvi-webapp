/**
 * --------------------------
 * RepresentationIndiceCourbe
 * --------------------------
 * 
 * Cree une representation en courbe avec une droite de regression a partir des donnees recues
 * Les donnees attendues doivent etre de type DonneesIndiceCourbe (voir backend)
 * 
 * @param indice l'indice a representer
 * 
 */
function RepresentationIndiceCourbe(indice, cumulative) {

	/**
	 * Heritage (prototypage) Javascript
	 */
	RepresentationIndiceCourbe.prototype = Object.create(RepresentationIndice.prototype);
	RepresentationIndiceCourbe.prototype.constructor = RepresentationIndice;
	
	RepresentationIndice.call(this, indice, cumulative);
	
	/**
	 * Attributs prives
	 */
	
	/** Autoreference */
	var _this = this;
	
	/** Principales variables (Amcharts) */
	var _am_chart,
		_am_dateAxis,
		_am_valueAxis,
		_am_lineSeries_courbe,
		_am_lineSeries_regression;

	/**
	 * Attributs publics
	 */

	/**
	 * Initialisation de la representation (axes, mapping des donnes, etc.)
	 */
	_this.initialiser = function() {

		_am_chart = am4core.create(_this.indiceChartDiv, am4charts.XYChart);
		_this.indiceTextP.innerHTML = "";

		/*----------------------*/
		/*	Axe X - Date Axis	*/
		/*----------------------*/
		
		_am_dateAxis = _am_chart.xAxes.push(new am4charts.DateAxis());
		_am_dateAxis.startLocation = 0.5;
		_am_dateAxis.endLocation = 0.5;
		_am_dateAxis.baseInterval = {
			timeUnit: "year", count: 1
		};

		/*----------------------*/
		/*	Axe Y - Value Axis	*/
		/*----------------------*/
		
		_am_valueAxis = _am_chart.yAxes.push(new am4charts.ValueAxis());

		/*----------------------*/
		/*	Serie de donnees	*/
		/*----------------------*/
		
		_am_lineSeries_courbe = _am_chart.series.push(new am4charts.LineSeries());
		_am_lineSeries_courbe.strokeWidth = 3;
		_am_lineSeries_courbe.propertyFields.stroke = "lineColor";
		_am_lineSeries_courbe.propertyFields.fill = "lineColor";
		_am_lineSeries_courbe.connect = false;
		_am_lineSeries_courbe.tooltipText = "La valeur de "
			+ _this.indice.acronyme
			+ " est de [bold]{valueY}" + _this.indice.unite + "[/]"
			+ " pour l'année [bold]{dateX}[/]";
		
		_am_lineSeries_regression = _am_chart.series.push(new am4charts.LineSeries());
		_am_lineSeries_regression.strokeWidth = 2
		_am_lineSeries_regression.stroke = _am_lineSeries_regression.fill = outils_am_obtenirCouleurRegression();
		
		/*------------------*/
		/*	Mapping series	*/
		/*------------------*/
		
		_am_lineSeries_courbe.dataFields.valueY = "valeur";
		_am_lineSeries_courbe.dataFields.dateX = "annee";
		
		_am_lineSeries_regression.dataFields.valueY = "valeur";
		_am_lineSeries_regression.dataFields.dateX = "annee";
		
		/*----------------------*/
		/*	Bullets et symboles	*/
		/*----------------------*/
		
		var am_bullet = _am_lineSeries_regression.bullets.push(new am4charts.CircleBullet());
		
		am_bullet.strokeWidth = 2;
		am_bullet.stroke = am4core.color("white");
		am_bullet.circle.fill = _am_lineSeries_regression.stroke;

		am_bullet.tooltipText = "La valeur de "
			+ _this.indice.acronyme
			+ " est aux alentours de"
			+ " [bold]{valueY}" + _this.indice.unite + "[/]"
			+ " pour l'année [bold]{dateX}[/]";

		/*----------*/
		/*	Divers	*/
		/*----------*/
		
		// Effets au survol

		var am_hoverState = am_bullet.states.create("hover");
		am_hoverState.properties.scale = 1.7;
		
		// Curseur
		
		_am_chart.cursor = new am4charts.XYCursor();
		
		// Scrollbar
		
		_am_chart.scrollbarX = new am4core.Scrollbar();
		
		// Formats
		
		_am_chart.dateFormatter.dateFormat = "yyyy";
		_am_chart.numberFormatter.numberFormat = "#.#";
	};
	
	/**
	 * Pretraitement des donnees (ajout d'attributs, conversions, etc.)
	 * 
	 * @param donnees	les donnees calculees pour l'indice
	 * @param scenario 	le scenario associe aux donnees
	 * @param periode	la periode associee aux donnees
	 * @param site		le site associe aux donnees
	 */
	_this.pretraitementDonnees = function(donnees, scenario, periode, site) {

		// Passage des annees en string pour qu'elles soient correctement interpretees
		
		for (var i = 0; i < donnees.points.length; i++) {
			donnees.points[i].annee = donnees.points[i].annee.toString();
		}

		donnees.premierPointRegression.annee = donnees.premierPointRegression.annee.toString();
		donnees.dernierPointRegression.annee = donnees.dernierPointRegression.annee.toString();
		
		// Ajout de la propriete lineColor pour determiner la couleur de la courbe
		
		for (var i = 0; i < donnees.points.length; i++) {
			donnees.points[i].lineColor = outils_am_obtenirCouleursPeriodes(
					Enums.obtenirIndicePeriodeDepuisAnnee(donnees.points[i].annee)
			);
		}
	};
	
	/**
	 * Integration des donnees a la representation (ajout des donnees, d'adapters, d'interpretation, etc.)
	 * 
	 * @param donnees	les donnees calculees pour l'indice
	 * @param scenario 	le scenario associe aux donnees
	 * @param periode	la periode associee aux donnees
	 * @param site		le site associe aux donnees
	 */
	_this.integrerDonnees = function(donnees, scenario, periode, site) {

		// Ajout des donnees a la representation
		
		_am_chart.invalidateData();
		_am_chart.addData(donnees.points, _this.cumulative ? 0 : _am_chart.data.length);

		// Ajout d'un segment a la ligne de regression

		_am_lineSeries_regression.invalidateData();
		_am_lineSeries_regression.addData([
				donnees.premierPointRegression,
				donnees.dernierPointRegression
		]);

		// Mise a jour de l'interpretation
		
		var valeurDiff = donnees.dernierPointRegression.valeur - donnees.premierPointRegression.valeur;
		var valeurMoyenne = (donnees.premierPointRegression.valeur + donnees.dernierPointRegression.valeur) / 2;
		
		_this.indiceTextP.innerHTML += 
					"À <span class='text-primary'>" + site.nom + "</span>"
					+ " de <span class='text-primary'>" + periode.dateDebut.year + "</span>"
					+ " à <span class='text-primary'>" + periode.dateFin.year + "</span>,"
					+ " la valeur de " + _this.indice.acronyme + ((valeurDiff > 0) ? " augmente" : " diminue")
					+ " de <span class='text-primary'>" + valeurDiff.toFixed(1) + _this.indice.unite + "</span>"
					+ " et atteint en moyenne"
					+ " <span class='text-primary'>" + valeurMoyenne.toFixed(1) + _this.indice.unite + "</span><br/>";
	};
};
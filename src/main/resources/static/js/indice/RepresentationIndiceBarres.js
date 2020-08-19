/**
 * --------------------------
 * RepresentationIndiceBarres
 * --------------------------
 * 
 * Cree une representation en barres a partir des donnes recues
 * Les donnees attendues doivent etre de type DonneesIndiceBoites (voir backend)
 * 
 * @param indice l'indice a representer
 * 
 */
function RepresentationIndiceBarres(indice, cumulative) {
	
	/**
	 * Heritage (prototypage) Javascript
	 */
	RepresentationIndiceBarres.prototype = Object.create(RepresentationIndice.prototype);
	RepresentationIndiceBarres.prototype.constructor = RepresentationIndice;
	
	RepresentationIndice.call(this, indice, cumulative);
	
	/**
	 * Attributs prives
	 */
	
	/** Autoreference */
	var _this = this;
	
	/** Principales variables (Amcharts) */
	var _am_chart,
		_am_categoryAxis,
		_am_valueAxis,
		_am_series;
	
	/**
	 * Attributs publics / methodes
	 */

	/**
	 * Initialisation de la representation (axes, mapping des donnes, etc.)
	 */
	_this.initialiser = function() {

		_am_chart = am4core.create(_this.indiceChartDiv, am4charts.XYChart);
		_this.indiceTextP.innerHTML = "";
	
		/*--------------------------*/
		/*	Axe X - Category Axis	*/
		/*--------------------------*/
		
		_am_categoryAxis = _am_chart.xAxes.push(new am4charts.CategoryAxis());
		_am_categoryAxis.dataFields.category = "annee";
		_am_categoryAxis.renderer.grid.template.location = 0;
		_am_categoryAxis.renderer.minGridDistance = 30;
		_am_categoryAxis.renderer.labels.template.horizontalCenter = "right";
		_am_categoryAxis.renderer.labels.template.verticalCenter = "middle";
		_am_categoryAxis.renderer.labels.template.rotation = 270;
		_am_categoryAxis.renderer.labels.template.min
		
		/*----------------------*/
		/*	Axe Y - Value Axis	*/
		/*----------------------*/

		_am_valueAxis = _am_chart.yAxes.push(new am4charts.ValueAxis());
		_am_valueAxis.renderer.minWidth = 50;

		/*------------------------------*/
		/*	Serie de donnees - Barres	*/
		/*------------------------------*/
		
		_am_series = _am_chart.series.push(new am4charts.ColumnSeries());	
		_am_series.sequencedInterpolation = true;	
		_am_series.tooltip.pointerOrientation = "vertical";	
		_am_series.columns.template.strokeWidth = 0;
		_am_series.columns.template.column.cornerRadiusTopLeft = 10;
		_am_series.columns.template.column.cornerRadiusTopRight = 10;
		_am_series.columns.template.column.fillOpacity = 0.8;

		/*------------------*/
		/*	Mapping series	*/
		/*------------------*/
		
		_am_series.dataFields.valueY = "valeur";
		_am_series.dataFields.categoryX = "annee";

		/*----------*/
		/*	Divers	*/
		/*----------*/
		
		// Effet au survol
		
		var am_hoverState = _am_series.columns.template.column.states.create("hover");
		am_hoverState.properties.fillOpacity = 1;

		// Curseurs
		
		_am_chart.cursor = new am4charts.XYCursor();
		_am_chart.cursor.xAxis = _am_categoryAxis;

		// Scrollbar
		
		_am_chart.scrollbarX = new am4core.Scrollbar();

		// Formats
		
		_am_chart.dateFormatter.inputDateFormat = "yyyy";
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
		
		// Mise en evidence des points min et max
		
		for (var i = 0; i < donnees.points.length; i++) {
			var point = donnees.points[i];
			if (point.annee == donnees.pointMin.annee) {
				point.type = "min";
			}
			else if (point.annee == donnees.pointMax.annee) {
				point.type = "max";
			}
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
		
		_am_chart.invalidateData();
		_am_chart.addData(donnees.points, _this.cumulative ? 0 : _am_chart.data.length);
		
		// Adapter de la couleur des barres selon le type de point
		
		_am_series.columns.template.adapter.add("fill", function(fill, target) {
			var annee = target.tooltipDataItem.dataContext.annee;
			var type = target.tooltipDataItem.dataContext.type;

			if (type == "min" || type == "max") {
				return outils_am_obtenirCouleursSombresPeriodes(
						Enums.obtenirIndicePeriodeDepuisAnnee(annee)
				);
			}
			else {
				return outils_am_obtenirCouleursPeriodes(
						Enums.obtenirIndicePeriodeDepuisAnnee(annee)
				);
			}
		});
		
		// Adapter du texte au survol selon la valeur
		
		_am_series.adapter.add("tooltipText", (text, target) => {
			var type = target.tooltipDataItem.dataContext.type;

			return "La valeur de "
					+ _this.indice.acronyme
					+ " est de [bold]{valueY}" + _this.indice.unite + "[/]"
					+ " pour l'année [bold]{categoryX}[/]"
					+ ((type == "min") ? " (valeur minimale de période)" : "")
					+ ((type == "max") ? " (valeur maximale de période)" : "");

		});

		// Mise a jour de l'interpretation
		
		_this.indiceTextP.innerHTML += 
			"À <span class='text-primary'>" + site.nom + "</span>"
			+ " de <span class='text-primary'>" + periode.dateDebut.year + "</span>"
			+ " à <span class='text-primary'>" + periode.dateFin.year + "</span>,"
			+ " la valeur minimale de "	+ _this.indice.acronyme	+ " est de"
			+ " <span class='text-primary'>" + donnees.pointMin.valeur.toFixed(1) + _this.indice.unite + "</span>" 
			+ " en <span class='text-primary'>" + donnees.pointMin.annee + "</span>"
			+ " et la valeur maximale est de"
			+ " <span class='text-primary'>" + donnees.pointMax.valeur.toFixed(1) + _this.indice.unite + "</span>" 
			+ " en <span class='text-primary'>" + donnees.pointMax.annee + "</span><br/>";
	};
};
/**
 * --------------------------
 * RepresentationIndiceBoites
 * --------------------------
 * 
 * Cree des boites a moustaches (min, max, mediane) a partir des donnes recues
 * Les donnees attendues doivent etre de type DonneesIndiceBoites (voir backend)
 * 
 * @param indice l'indice a representer
 * 
 */
function RepresentationIndiceBoites(indice, cumulative) {

	/**
	 * Heritage (prototypage) Javascript
	 */
	RepresentationIndiceBoites.prototype = Object.create(RepresentationIndice.prototype);
	RepresentationIndiceBoites.prototype.constructor = RepresentationIndice;
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
		_am_columnSeries_min,
		_am_columnSeries_mediane,
		_am_columnSeries_max,
		_am_columnSeries_deciles,
		_am_columnSeries_repartition,
		_am_lineSeries_courbeMin,
		_am_lineSeries_courbeMax;

	/**
	 * Attributs publics
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
		_am_categoryAxis.dataFields.category = "periode";
		_am_categoryAxis.renderer.grid.template.location = 0;
		_am_categoryAxis.renderer.minGridDistance = 30;
		_am_categoryAxis.renderer.labels.template.min;
		
		/*----------------------*/
		/*	Axe Y - Value Axis	*/
		/*----------------------*/

		_am_valueAxis = _am_chart.yAxes.push(new am4charts.ValueAxis());
		_am_valueAxis.renderer.minWidth = 50;
		_am_valueAxis.renderer.minGridDistance = 50;
		_am_valueAxis.extraMax  = 0.15; // Pour ne pas mordre les triangles

		/*----------------------*/
		/*	Series de donnees	*/
		/*----------------------*/
		
		_am_columnSeries_min = _am_chart.series.push(new am4charts.ColumnSeries());
		_am_columnSeries_min.columns.template.strokeOpacity = 0;
		_am_columnSeries_min.columns.template.fillOpacity = 0;
		_am_columnSeries_min.stacked = true;

		_am_columnSeries_mediane = _am_chart.series.push(new am4charts.ColumnSeries());
		_am_columnSeries_mediane.columns.template.strokeOpacity = 0;
		_am_columnSeries_mediane.columns.template.fillOpacity = 0;
		_am_columnSeries_mediane.stacked = true;
		
		_am_columnSeries_max = _am_chart.series.push(new am4charts.ColumnSeries());
		_am_columnSeries_max.columns.template.strokeOpacity = 0;
		_am_columnSeries_max.columns.template.fillOpacity = 0;
		_am_columnSeries_max.stacked = true;
		
		_am_columnSeries_deciles = _am_chart.series.push(new am4charts.ColumnSeries());
		_am_columnSeries_deciles.columns.template.strokeOpacity = 0;
		_am_columnSeries_deciles.columns.template.width = 30;
		_am_columnSeries_deciles.stacked = true;
		_am_columnSeries_deciles.zIndex = 1;
		
		_am_columnSeries_repartition = _am_chart.series.push(new am4charts.ColumnSeries());
		_am_columnSeries_repartition.columns.template.strokeOpacity = 0;
		_am_columnSeries_repartition.columns.template.width = 2;
		_am_columnSeries_repartition.fill = outils_am_obtenirCouleurRegression();
		_am_columnSeries_repartition.stacked = true;
		_am_columnSeries_repartition.zIndex = 0;
		
		_am_lineSeries_courbeMin = _am_chart.series.push(new am4charts.LineSeries());
		_am_lineSeries_courbeMin.yAxis = _am_valueAxis;
		_am_lineSeries_courbeMin.strokeWidth = 2;
		_am_lineSeries_courbeMin.stroke = _am_lineSeries_courbeMin.fill = outils_am_obtenirCouleurRegression();
		
		_am_lineSeries_courbeMax = _am_chart.series.push(new am4charts.LineSeries());
		_am_lineSeries_courbeMax.yAxis = _am_valueAxis;
		_am_lineSeries_courbeMax.strokeWidth = 2;
		_am_lineSeries_courbeMax.stroke = _am_lineSeries_courbeMax.fill = outils_am_obtenirCouleurRegression();

		// Adapters (couleur des tooltipText)
		
		_am_columnSeries_min.columns.template.adapter.add("fill", function(fill, target) {
			return (!target.dataItem) ? fill : outils_am_obtenirCouleursPeriodes(
					Enums.obtenirIndicePeriodeDepuisTitrePeriode(
							target.dataItem.categories.categoryX
					)
			);
		});
		
		_am_columnSeries_mediane.columns.template.adapter.add("fill", function(fill, target) {
			return (!target.dataItem) ? fill : outils_am_obtenirCouleursSombresPeriodes(
					Enums.obtenirIndicePeriodeDepuisTitrePeriode(
							target.dataItem.categories.categoryX
					)
			);
		});

		_am_columnSeries_max.columns.template.adapter.add("fill", function(fill, target) {
			return (!target.dataItem) ? fill : outils_am_obtenirCouleursPeriodes(
					Enums.obtenirIndicePeriodeDepuisTitrePeriode(
							target.dataItem.categories.categoryX
					)
			);
		});

		_am_columnSeries_deciles.columns.template.adapter.add("fill", function(fill, target) {
			return (!target.dataItem) ? fill : outils_am_obtenirCouleursClairesPeriodes(
					Enums.obtenirIndicePeriodeDepuisTitrePeriode(
							target.dataItem.categories.categoryX
					)
			);
		});
		
		// Tooltips
		
		_am_columnSeries_min.tooltipText = "Valeur minimale : [bold]{valeurMin}" + _this.indice.unite + "[/]";
		_am_columnSeries_mediane.tooltipText = "Valeur médiane : [bold]{valeurMediane}" + _this.indice.unite + "[/]";
		_am_columnSeries_max.tooltipText = "Valeur maximale : [bold]{valeurMax}" + _this.indice.unite + "[/]";
		
		/*------------------*/
		/*	Mapping series	*/
		/*------------------*/
		
		_am_columnSeries_min.dataFields.valueY = "positionMin";
		_am_columnSeries_min.dataFields.categoryX = "periode";

		_am_columnSeries_mediane.dataFields.valueY = "positionMediane";
		_am_columnSeries_mediane.dataFields.categoryX = "periode";
		
		_am_columnSeries_max.dataFields.valueY = "positionMax";
		_am_columnSeries_max.dataFields.categoryX = "periode";
		
		_am_columnSeries_deciles.dataFields.categoryX = "periode";
		_am_columnSeries_deciles.dataFields.openValueY = "positionPremierDecile";
		_am_columnSeries_deciles.dataFields.valueY = "positionNeuviemeDecile";
		
		_am_columnSeries_repartition.dataFields.categoryX = "periode";
		_am_columnSeries_repartition.dataFields.openValueY = "positionDebutRepartition";
		_am_columnSeries_repartition.dataFields.valueY = "positionFinRepartition";

		_am_lineSeries_courbeMin.dataFields.valueY = "valeurMin";
		_am_lineSeries_courbeMin.dataFields.categoryX = "periode";
		
		_am_lineSeries_courbeMax.dataFields.valueY = "valeurMax";
		_am_lineSeries_courbeMax.dataFields.categoryX = "periode";
		
		/*----------------------*/
		/*	Bullets et symboles	*/
		/*----------------------*/
		
		var am_bulletTriangle_min = _am_columnSeries_min.bullets.push(new am4charts.Bullet()).createChild(am4core.Triangle);
		am_bulletTriangle_min.strokeOpacity = 0;
		am_bulletTriangle_min.horizontalCenter = "middle";
		am_bulletTriangle_min.verticalCenter = "top";
		am_bulletTriangle_min.direction = "top";
		am_bulletTriangle_min.width = 18;
		am_bulletTriangle_min.height = 18;

		var am_bulletRectangle_mediane = _am_columnSeries_mediane.bullets.push(new am4charts.Bullet()).createChild(am4core.Rectangle);
		am_bulletRectangle_mediane.strokeOpacity = 0;
		am_bulletRectangle_mediane.horizontalCenter = "middle";
		am_bulletRectangle_mediane.verticalCenter = "middle";
		am_bulletRectangle_mediane.width = 30;
		am_bulletRectangle_mediane.height = 10;
		
		var am_bulletTriangle_max = _am_columnSeries_max.bullets.push(new am4charts.Bullet()).createChild(am4core.Triangle);
		am_bulletTriangle_max.strokeOpacity = 0;
		am_bulletTriangle_max.horizontalCenter = "middle";
		am_bulletTriangle_max.verticalCenter = "bottom";
		am_bulletTriangle_max.direction = "bottom";
		am_bulletTriangle_max.width = 18;
		am_bulletTriangle_max.height = 18;

		// Adapters (couleur des symboles)
		
		am_bulletTriangle_min.adapter.add("fill", function(fill, target) {
			return (!target.dataItem) ? fill : outils_am_obtenirCouleursPeriodes(
					Enums.obtenirIndicePeriodeDepuisTitrePeriode(
							target.dataItem.categories.categoryX
					)
			);
		});
		
		am_bulletRectangle_mediane.adapter.add("fill", function(fill, target) {
			return (!target.dataItem) ? fill : outils_am_obtenirCouleursSombresPeriodes(
					Enums.obtenirIndicePeriodeDepuisTitrePeriode(
							target.dataItem.categories.categoryX
					)
			);
		});

		am_bulletTriangle_max.adapter.add("fill", function(fill, target) {
			return (!target.dataItem) ? fill : outils_am_obtenirCouleursPeriodes(
					Enums.obtenirIndicePeriodeDepuisTitrePeriode(
							target.dataItem.categories.categoryX
					)
			);
		});

		/*----------*/
		/*	Divers	*/
		/*----------*/

		// Curseurs
		
		_am_chart.cursor = new am4charts.XYCursor();
		_am_chart.cursor.xAxis = _am_categoryAxis;

		// Scrollbars
		
		_am_chart.scrollbarX = new am4core.Scrollbar();

		// Formats

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

		donnees.points = [{
				valeurMin:						donnees.pointMin.valeur,
				positionMin:					donnees.pointMin.valeur,
				valeurMediane:					donnees.mediane,
				positionMediane:				donnees.mediane - donnees.pointMin.valeur,
				valeurMax:						donnees.pointMax.valeur,
				positionMax:					donnees.pointMax.valeur - donnees.mediane,
				positionPremierDecile:			donnees.premierDecile,
				positionNeuviemeDecile:			donnees.neuviemeDecile,
				positionDebutRepartition:		donnees.pointMin.valeur - donnees.neuviemeDecile,
				positionFinRepartition:			donnees.pointMax.valeur - donnees.neuviemeDecile,
				periode: 						donnees.periode.titre
		}];
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
		_am_chart.addData(
				donnees.points,
				_this.cumulative ? 0 : _am_chart.data.length
		);

		// Mise a jour de l'interpretation
		
		_this.indiceTextP.innerHTML += 
			"À <span class='text-primary'>" + site.nom + "</span>"
			+ " de <span class='text-primary'>" + periode.dateDebut.year + "</span>"
			+ " à <span class='text-primary'>" + periode.dateFin.year + "</span>,"
			+ " la valeur de "	+ _this.indice.acronyme	+ " est en moyenne de"
			+ " <span class='text-primary'>" + donnees.moyenne.toFixed(1) + _this.indice.unite + "</span><br/>";
	};
};
/**
 * ----------
 * CarteSites
 * ----------
 * 
 * Carte qui se synchronize avec le #site-select, permettant de visualiser la position
 * des sites sur la carte de France et d'en faciliter la selection
 * 
 */
var CarteSites = function() {

	/**
	 * Attributs prives
	 */

	/** Autoreference */
	var _this = this;

	/** Reference vers les elements HTML associes */
	var _carteDiv = document.querySelector(".carte-sites"),
		_carteChartDiv = _carteDiv.querySelector(".chart"), 
		_carteLoaderDiv = _carteDiv.querySelector(".loader");

	/** Principales variables (Amcharts) */
	var _am_mapChart_carteFrance,
		_am_polygonSeries_departements,
		_am_imageSeries_sites,
		_am_image_marqueur;
	
	/**
	 * Attributs publics / methodes
	 */
	
	/**
	 * Initialise la structure de la carte
	 */
	_this.initialiser = function() {

		_am_mapChart_carte = am4core.create(_carteChartDiv, am4maps.MapChart);
		
		_am_mapChart_carte.geodata = am4geodata_franceDepartmentsHigh;
		_am_mapChart_carte.projection = new am4maps.projections.Mercator();
		_am_mapChart_carte.homeZoomLevel = 1;

		/*----------------------*/
		/*	Series de donneees	*/
		/*----------------------*/
		
		// Departements
		
		_am_polygonSeries_departements = _am_mapChart_carte.series.push(new am4maps.MapPolygonSeries());
		_am_polygonSeries_departements.useGeodata = true;
		_am_polygonSeries_departements.nonScalingStroke = true;
		_am_polygonSeries_departements.strokeOpacity = 0.5;
		_am_polygonSeries_departements.mapPolygons.template.tooltipText = "{name}";
		_am_polygonSeries_departements.mapPolygons.template.fill = outils_am_obtenirCouleurPrincipaleBis();

		// Sites
		
		_am_imageSeries_sites = _am_mapChart_carte.series.push(new am4maps.MapImageSeries());
		_am_imageSeries_sites.mapImages.template.propertyFields.longitude = "longitude";
		_am_imageSeries_sites.mapImages.template.propertyFields.latitude = "latitude";
		_am_imageSeries_sites.mapImages.template.nonScaling = true;

		/*----------*/
		/*	Images	*/
		/*----------*/
		
		// Marqueurs
		
		_am_image_marqueur = _am_imageSeries_sites.mapImages.template.createChild(am4core.Image);
		_am_image_marqueur.width = 24;
		_am_image_marqueur.height = 24;
		_am_image_marqueur.horizontalCenter = "middle";
		_am_image_marqueur.verticalCenter = "middle";
		_am_image_marqueur.tooltipText = "{label}";
		_am_image_marqueur.href = "img/marqueur.svg";
		_am_image_marqueur.fill = outils_am_obtenirCouleurRegression();
		_am_image_marqueur.cursorOverStyle = am4core.MouseCursorStyle.pointer;

		/*----------*/
		/*	Divers	*/
		/*----------*/
		
		// Effet au survol
		
		var am_hoverState = _am_image_marqueur.states.create("hover");
		am_hoverState.properties.scale = 1.5;
		
		// Effet au clique
		
		var am_activeState_selection = _am_image_marqueur.states.create("active");
		am_activeState_selection.properties.scale = 2;

		// Synchronization avec le selectpicker #site-select
		
		function onMarqueurHit(event) {
			var nomSite = event.target.dataItem.dataContext.site.nom;
			
			_am_imageSeries_sites.dataItems.values.forEach(function(site) {
				var marqueur = site.mapImage.children.values[0];
				marqueur.isActive = false;
			});
			event.target.isActive = true;
			
			$(SiteSelect).val(nomSite).triggerNative("change");
		}
		
		_am_image_marqueur.events.on("hit", onMarqueurHit, this);

		function onSiteSelectChange() {
			_am_imageSeries_sites.dataItems.values.forEach(function(site) {
				var marqueur = site.mapImage.children.values[0];
				marqueur.isActive = (marqueur.dataItem.dataContext.site.nom == $(SiteSelect).val());
			});
		}

		$(SiteSelect).on("change", onSiteSelectChange);
		
		// Attend le chargement de la carte pour declencher la selection du site
		
		_am_mapChart_carte.events.on("ready", function() {
			onSiteSelectChange();
		});
	};
	
	/**
	 * Integre les sites passes en parametre a la carte
	 * 
	 * @param sites	les sites a afficher
	 */
	_this.integrerSites = function(sites) {
		
		_this.initialiser();
		
		sites.forEach(function(site) {
			_am_imageSeries_sites.addData([{
			  "site" : site,
			  "latitude": site.latitude,
			  "longitude": site.longitude,
			  "label": "[bold]" + site.nom + "[/] (" + site.codePostal + ")"
			}]);
		});

		_this.afficherLoader(false);
	};
	
	/**
	 * Affiche ou cache le loader selon la valeur du booleen passe en parametre
	 * 
	 * @param afficher	si vrai le loader sera affiche, sinon le loader sera cache
	 */
	_this.afficherLoader = function(afficher) {
		var dnone = " d-none";
		if (afficher) {
			if (_carteChartDiv.className.indexOf(dnone) < 0) _carteChartDiv.className += dnone;	
			_carteLoaderDiv.className = _carteLoaderDiv.className.replace(dnone, "");
		}
		else {
			_carteChartDiv.className = _carteChartDiv.className.replace(dnone, "");
			if (_carteLoaderDiv.className.indexOf(dnone) < 0) _carteLoaderDiv.className += dnone;
		}
	};
};
/**
 * --------------------
 * RepresentationIndice
 * --------------------
 * 
 * Prototype pour les differentes representations d'indices (courbe, barres, boites, etc.)
 * Se charge notamment des requetes de recuperation des donnees, qui sont independantes
 * de la representation choisie
 * 
 * @param indice l'indice a representer
 */
function RepresentationIndice(indice, cumulative) {

	/**
	 * Attributs prives
	 */
	
	/** Autoreference */
	var _this = this;
	
	/**
	 * Attributs publics / methodes
	 */
	
	/** Indice (enum) asocie a la representation */
	_this.indice = indice;
	
	/** Si les donnees doivent etre cumulees ou non */
	_this.cumulative = cumulative;

	/** Reference vers les elements HTML associes */
	_this.indiceDiv = document.querySelector(".indice[data-indice='" + indice.acronyme + "'");
	_this.indiceChartDiv = _this.indiceDiv.querySelector(".chart");
	_this.indiceTextP = _this.indiceDiv.querySelector(".text");
	_this.loaderDiv = _this.indiceDiv.querySelector(".loader");
	
	/**
	 * Envoie une requete Ajax a la CalculIndiceAPI (backend) avec les arguments passes en parametre 
	 * (scenario, periode, site) et l'acronyme de l'indice, puis appel la fonction callback 
	 * avec les donnees recues
	 * 
	 * @param scenario 	le scenario souhaite
	 * @param periode	la periode souhaitee
	 * @param site		le site souhaite
	 * @param callback	la fonction a appeler avec les donnees recues
	 */	
	RepresentationIndice.prototype.requeteRecuperationDonnees = function(
			scenario, periode, site, callback) {

		outils_squeletteAjaxPOST(
				"Indice " + this.indice.acronyme,
				"/api/calculIndice/donnees",
				
				callback,
				
				JSON.stringify({ 
					"scenario"		: scenario.titre,
					"periode"		: periode.titre,
					"site"			: site,
					"indice"		: this.indice.acronyme,
					"representation": this.indice.representation	// Pourra etre remplace par une representation differente
				})
		);
	};
	
	/**
	 * Met a jour la representation de l'indice en declenchant les bonnes requetes selon
	 * la periode et le type de representation (cumulatif ou non) et l'integration des donnees
	 * 
	 * @param scenario 	le scenario souhaite
	 * @param periode	la periode souhaitee
	 * @param site		le site souhaite
	 */	
	RepresentationIndice.prototype.mettreAJourRepresentation = function(scenario, periode, site) {

		this.initialiser();
		
		RepresentationIndice.prototype.afficherLoader.call(this, true);

		// Dans le cas d'un graphe cumulatif, on recupere et integre les donnees
		// pour toutes les sous periodes individuellement
		if (this.cumulative && periode.sousPeriodes != null && periode.sousPeriodes.length > 0) {
			
			(function requeteSousPeriode(indiceSousPeriode) {
				var sousPeriode = periode.sousPeriodes[indiceSousPeriode];
				RepresentationIndice.prototype.requeteRecuperationDonnees.call(
					this,
					scenario, sousPeriode, site,
					// callback
					(function(donnees) {
						this.pretraitementDonnees(donnees, scenario, sousPeriode, site);
						this.integrerDonnees(donnees, scenario, sousPeriode, site);
						
						// On fait un appel recursif avec la sous periode suivante
						if (indiceSousPeriode < periode.sousPeriodes.length - 1) {
							requeteSousPeriode.call(this, indiceSousPeriode + 1);
						}
						else  {
							RepresentationIndice.prototype.afficherLoader.call(this, false);
						}
						
					}).bind(this)
				);
				
			}).apply(this, [0]);
		}
		// Autrement, dans le cas d'un graphe non cumulatif, on fait un seul appel sur la periode entiere
		else {
			RepresentationIndice.prototype.requeteRecuperationDonnees.call(
				this,
				scenario, periode, site,
				// callback
				(function(donnees) {
					this.pretraitementDonnees(donnees, scenario, periode, site);
					this.integrerDonnees(donnees, scenario, periode, site);
					
					RepresentationIndice.prototype.afficherLoader.call(this, false);
				}).bind(this)
			);
		}
	};
	
	/**
	 * Affiche ou cache le loader selon la valeur du booleen passe en parametre
	 * 
	 * @param afficher	si vrai le loader sera affiche, sinon le loader sera cache
	 */
	RepresentationIndice.prototype.afficherLoader = function(afficher) {
		var dnone = " d-none";
		if (afficher) {
			if (this.indiceChartDiv.className.indexOf(dnone) < 0) this.indiceChartDiv.className += dnone;	
			if (this.indiceTextP.className.indexOf(dnone) < 0) this.indiceTextP.className += dnone;
			this.loaderDiv.className = this.loaderDiv.className.replace(dnone, "");
		}
		else {
			this.indiceChartDiv.className = this.indiceChartDiv.className.replace(dnone, "");
			this.indiceTextP.className = this.indiceTextP.className.replace(dnone, "");
			if (this.loaderDiv.className.indexOf(dnone) < 0) this.loaderDiv.className += dnone;
		}
	};
	
	_this.initialiser = function() {
		Logger.erreur("Representation '" + this.indice.acronyme + "' doit implémenter la méthode initialiser()");
	}
	
	_this.pretraitementDonnees = function(donnees, scenario, periode, site) {
		Logger.erreur("Representation '" + this.indice.acronyme + "' doit implémenter la méthode pretraitementDonnees(donnees, scenario, periode, site)");
	}
	
	_this.integrerDonnees = function(donnees, scenario, periode, site) {
		Logger.erreur("Representation '" + this.indice.acronyme + "' doit implémenter la méthode integrerDonnees(donnees, scenario, periode, site)");
	}
};

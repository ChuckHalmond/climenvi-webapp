/**
 * ------
 * Outils
 * ------
 * 
 * Quelques methodes utilitaires d'ordre general
 */

/** Mode souhaite pour le logger : INFO ou DEBUG */
var LoggerMode = "INFO";

/**
 * Logger pour centraliser les problematiques de logs (debug, etc.)
 * 
 * @param mode	le mode de log souhaite : INFO ou DEBUG
 */
var Logger = (function(mode) {

	var _this = this;
	
	if (mode != "DEBUG" && mode != "INFO") {
		creerErreurAlert("Le mode de SimpleLogger '" + mode + "' est inconnu");
	}

	_this.info = function(message) {
		console.log("[INFO] " + message);
	}
	
	_this.erreur = function(message) {
		console.log(
				"[ERREUR] "
				+ message 
				+ "\nPasser éventuellement en mode 'DEBUG' pour obtenir plus d'informations"
		);
	}
	
	if (mode == "DEBUG") {
		_this.debug = function(message) {
			if (typeof message == "string") {
				console.log("[DEBUG] " + message);
			}
			else {
				console.log(message);
			}
		}
	}
	else {
		_this.debug = function(message) {}
	}
	
	return _this;

}).call(this, LoggerMode);

/**
 * Appel Ajax condense avec automatisation des logs de succes / echec
 * 
 * @param nomRequete	le nom a entionne dans les logs
 * @param url			l'url de la requete
 * @param callback		la methode a appeler une fois la reponse obtenue
 * @param data			les eventuelles donnees a joindre a la requete
 */
function outils_squeletteAjaxPOST(nomRequete, url, callback, data) {
	$.ajax({
		url: url,
		method: "POST",
		data: data,
		contentType: "application/json; charset=utf-8",
		beforeSend :
			function() {
				Logger.info("Début de la requête '" + nomRequete + "'");
				
				if (data != null) {
					Logger.debug("Données de la requête :");
					Logger.debug(data);
				}
			}
	}).done(
		function(reponse) {
			Logger.info("Succès de la requête '" + nomRequete + "'");
			
			Logger.debug("Réponse :");
			Logger.debug(reponse);
			
			callback(reponse);
		}
	).fail(
		function(reponse) {
			Logger.info("Échec de la requête '" + nomRequete + "'");
			Logger.erreur(reponse.responseText);
			
			Logger.debug("Réponse :");
			Logger.debug(reponse);
		}
	);
}

/**
 * Genere un message d'erreur et l'ajoute au DOM dans le premier .container-fluid accessible
 * 
 * @param message	le message a afficher
 */
function outils_creerErreurAlert(message) {
	
	var fluidContainer = document.querySelector(".container-fluid"),
		div = document.createElement("div");
	
	div.className += "error-message";
	
	div.innerHTML =
			"<div class='alert alert-danger alert-dismissable'>"
		      + "<a href='#' class='close' data-dismiss='alert' aria-label='close'>×</a>"
		      + "<string>Erreur : </strong><span>" + message + "</span>"
		    + "</div>";
	
	fluidContainer.insertBefore(
			div,
			fluidContainer.firstChild
	);
}

/**
 * Methode JQuery pour declencher les evenements natifs javascript
 */
(function($) {

    $.fn.triggerNative = function(eventName) {
        return this.each(function() {
            var el = $(this).get(0);
            triggerNativeEvent(el, eventName);
        });
    };

    function triggerNativeEvent(el, eventName){
      if (el.fireEvent) { // < IE9
        (el.fireEvent("on" + eventName));
      }
      else {
        var evt = document.createEvent("Events");
        evt.initEvent(eventName, true, false);
        el.dispatchEvent(evt);
      }
    };
}(jQuery));
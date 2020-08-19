package climenvi.webapp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.service.CalculIndiceService;

/**
 * API pour calculer les indices
 * 
 * @author Charles MECHERIKI
 *
 */
@RestController
@RequestMapping("/api/calculIndice")
public class CalculIndiceAPI {
	
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CalculIndiceAPI.class);
	
	/**
	 * Service de calcul des indices
	 */
	@Autowired
	private CalculIndiceService calculIndiceService;

	/**
	 * Renvoie les donnees de calcul de l'indice (json) pour la requete donnee
	 * 
	 * @param requete	requete de calcul de donnees (scenario, site, periode, indice, representation)
	 * @return			les donnees de calcul de l'indice (json) pour la requete donnee
	 */
    @PostMapping(value = "/donnees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> calculDonneesIndice(
            @RequestBody RequeteCalculIndice requete) {
    	
    		try {
    			logger.info("Réception de la requête : " + requete);
    			
	    		String reponse = new ObjectMapper().writeValueAsString(
	    				calculIndiceService.calculerDonneesIndice(
	    						requete
	    				).iterator().next()
	    		);

	    		logger.info("Envoie de la réponse : " + reponse);

	            return new ResponseEntity<>(reponse, HttpStatus.OK);
    		}
    		catch (Exception e) {
    			
    			logger.error("Erreur lors du traitement de la requête : " + e.getMessage(), e);
 
    			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    		}
    }
}

/**
 * 
 */
package climenvi.webapp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import climenvi.webapp.service.EnumsService;

/**
 * API de recuperations des enumerations
 * 
 * @author Charles MECHERIKI
 *
 */
@RestController
@RequestMapping("/api/enums")
public class EnumsAPI {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(EnumsAPI.class);
	
	/**
	 * Service de recuperation des enumerations
	 */
	@Autowired
	private EnumsService enumsService;
	
	/**
	 * Renvoie la liste des sites
	 * 
	 * @return	la liste des sites
	 */
    @PostMapping(value = "/sites", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listeSites() {

		try {
			String reponse = new ObjectMapper().writeValueAsString(
					enumsService.sites()
			);
	
	        return new ResponseEntity<>(reponse, HttpStatus.OK);
		}
		catch (Exception e) {
			
			logger.info(e.getMessage());
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
    
	/**
	 * Renvoie la liste des indices
	 * 
	 * @return	la liste des indices
	 */
    @PostMapping(value = "/indices", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listeIndices() {

		try {
			String reponse = new ObjectMapper().writeValueAsString(
					enumsService.indices()
			);
	
	        return new ResponseEntity<>(reponse, HttpStatus.OK);
		}
		catch (Exception e) {
			
			logger.info(e.getMessage());
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
    
	/**
	 * Renvoie la liste des scenarios
	 * 
	 * @return	la liste des scenarios
	 */
    @PostMapping(value = "/scenarios", produces = MediaType.APPLICATION_JSON_VALUE)
    	public ResponseEntity<String> listeScenarios() {

    	try {
    		String reponse = new ObjectMapper().writeValueAsString(
    				enumsService.scenarios()
    		);

            return new ResponseEntity<>(reponse, HttpStatus.OK);
		}
		catch (Exception e) {
			
			logger.info(e.getMessage());
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
    
	/**
	 * Renvoie la liste des periodes
	 * 
	 * @return	la liste des periodes
	 */
    @PostMapping(value = "/periodes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listePeriodes() {

		try {
			String reponse = new ObjectMapper().writeValueAsString(
					enumsService.periodes()
			);
	
	        return new ResponseEntity<>(reponse, HttpStatus.OK);
		}
		catch (Exception e) {
			
			logger.info(e.getMessage());
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
}

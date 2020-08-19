package climenvi.webapp.metier.model.representation;

import climenvi.webapp.metier.model.representation.prototype.PrototypeRepresentation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representation d'un indice
 * 
 * @author Charles MECHERIKI
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Representation {
	
	/**
	 * Prototype de la representation
	 */
	private PrototypeRepresentation prototype;
	
	/**
	 * Si la representation doit etre divisee en sous periode independantes
	 */
	private boolean cumulative;
}
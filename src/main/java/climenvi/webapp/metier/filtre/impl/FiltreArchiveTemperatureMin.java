package climenvi.webapp.metier.filtre.impl;

import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;
import lombok.AllArgsConstructor;

/**
 * Implementation de FiltreArchive acceptant les archives avec une temperature
 * minimale donnee
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
public class FiltreArchiveTemperatureMin extends FiltreArchive {

	/**
	 * Temperature minimale du filtre
	 */
    private double temperatureMin;
    
    @Override
    public boolean accepte(Archive archive) {
        return ((archive.getTemperatureMin() + archive.getTemperatureMax()) /2) >= temperatureMin;
    }
}
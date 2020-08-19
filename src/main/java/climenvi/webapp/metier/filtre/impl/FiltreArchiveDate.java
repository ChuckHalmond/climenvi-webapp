package climenvi.webapp.metier.filtre.impl;

import java.time.LocalDate;

import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;
import lombok.AllArgsConstructor;

/**
 * Implementation de FiltreArchive acceptant les archives de date donnee
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
public class FiltreArchiveDate extends FiltreArchive {

	/**
	 * Date du filtre
	 */
    private LocalDate date;

    @Override
    public boolean accepte(Archive archive) {
        return archive.getDate().equals(date);
    }
}
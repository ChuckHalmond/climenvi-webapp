package climenvi.webapp.metier.filtre.impl;

import java.time.LocalDate;

import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;
import lombok.AllArgsConstructor;

/**
 * Implementation de FiltreArchive acceptant les archives entre deux dates donnees
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
public class FiltreArchiveEntreDeuxDates extends FiltreArchive {

	/**
	 * Date de debut du filtre
	 */
    private LocalDate dateDebut;
    
    /**
     * Date de fin du filtre
     */
    private LocalDate dateFin;
    
    @Override
    public boolean accepte(Archive archive) {
        return (
        		archive.getDate().isAfter(dateDebut) || 
                archive.getDate().equals(dateDebut)
            ) && (
        		archive.getDate().isBefore(dateFin) ||
        		archive.getDate().equals(dateFin)
            );
    }
}
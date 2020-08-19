
package climenvi.webapp.metier.filtre.impl;

import java.time.LocalDate;

import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;
import lombok.AllArgsConstructor;

/**
 * Implementation de FiltreArchive acceptant les archives entre deux dates donnees,
 * quelque soit l'annee
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
public class FiltreArchiveEntreDeuxDatesAnneeQuelconque extends FiltreArchive {

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
        		archive.getDate().minusYears(archive.getDate().getYear()).isAfter(dateDebut) || 
                archive.getDate().minusYears(archive.getDate().getYear()).equals(dateDebut)
            ) && (
        		archive.getDate().minusYears(archive.getDate().getYear()).isBefore(dateFin) ||
        		archive.getDate().minusYears(archive.getDate().getYear()).equals(dateFin)
            );
    }
}

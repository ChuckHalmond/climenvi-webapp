package climenvi.webapp.metier.filtre;

import climenvi.webapp.metier.model.archive.Archive;

/**
 * Classe abstraite pour filtrer les archives
 * 
 * @author Charles MECHERIKI
 *
 */
public abstract class FiltreArchive {

	/**
	 * Renvoie true si l'archive est acceptee, false sinon
	 * 
	 * @param archive	l'archive a filtrer
	 * @return			true si l'archive est acceptee, false sinon
	 */
    public abstract boolean accepte(Archive archive);
}
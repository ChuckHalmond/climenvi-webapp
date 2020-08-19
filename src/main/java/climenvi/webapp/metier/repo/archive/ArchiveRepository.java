package climenvi.webapp.metier.repo.archive;

import java.util.ArrayList;

import climenvi.webapp.metier.model.archive.Archive;
import climenvi.webapp.metier.model.scenario.Scenario;
import climenvi.webapp.metier.model.site.Site;

/**
 * Interface pour acceder aux archives
 * 
 * @author Charles MECHERIKI
 *
 */
public interface ArchiveRepository {
	
    /**
     * Recupere toutes les archives pour le scenario et le site donne
     * 
     * @param scenario	le scenario souhaite
     * @param site		le site souhaite
     * @return          les archives climat
     * @throws Exception	si la recuperation des archives a echouee
     */
	public ArrayList<Archive> obtenirListeArchives(Scenario scenario, Site site) throws Exception;
}
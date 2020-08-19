package climenvi.webapp.metier.repo.archive.impl.proxy;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import climenvi.webapp.metier.model.archive.Archive;
import climenvi.webapp.metier.model.scenario.Scenario;
import climenvi.webapp.metier.model.site.Site;
import climenvi.webapp.metier.repo.archive.ArchiveRepository;

/**
 * Implementation proxy du ArchiveRepository
 * 
 * Intermediaire avant le repository csv, permettant de stocker les resultats 
 * de la derniere requete et ainsi d'eviter des calculs
 * 
 * @author Charles MECHERIKI
 *
 */
@Repository
@Qualifier("proxy")
public class ArchiveRepositoryProxy implements ArchiveRepository {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(ArchiveRepositoryProxy.class);
	
	/**
	 * ArchiveRepository csv
	 */
    @Autowired
    @Qualifier("csv")
    private ArchiveRepository archiveRepo;

    /**
     * Scenario de la derniere requete
     */
	private Scenario scenario;
	
	/**
	 * Site de la derniere requete
	 */
	private Site site;
	
	/**
	 * Cadenas pour assurer la synchronisation des requetes et eviter de 
	 * faire des appels au repository simultanement
	 */
	private final Object cadenas = new Object();
	
	/**
	 * Archives issues de la derniere requete
	 */
	private ArrayList<Archive> archives;
	
	@Override
	public ArrayList<Archive> obtenirListeArchives(
			Scenario scenario,
			Site site
		) throws Exception {
		
		synchronized (cadenas) {
			if (this.scenario == null || !this.scenario.equals(scenario) || 
				this.site == null || !this.site.equals(site)){

				logger.info(
						String.format(
								"Archives pour '%s' - '%s' non présentes : appel au repository",
								scenario, site
						)
				);
				
				archives = archiveRepo.obtenirListeArchives(scenario, site);

				this.scenario = scenario;
				this.site = site;
			}
			else {
				logger.info(
						String.format(
								"Archives pour '%s' - '%s' présentes : récupération directe",
								scenario, site
						)
				);
			}
	
			return archives;
		}
	}
}

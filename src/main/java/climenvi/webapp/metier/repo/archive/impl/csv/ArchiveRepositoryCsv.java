package climenvi.webapp.metier.repo.archive.impl.csv;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import climenvi.webapp.metier.csv.ExtracteurCSV;
import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;
import climenvi.webapp.metier.model.scenario.Scenario;
import climenvi.webapp.metier.model.site.Site;
import climenvi.webapp.metier.repo.archive.ArchiveRepository;

/**
 * Implementation csv du ArchiveRepository
 * 
 * Recupere la liste des archives directement depuis les fichiers .csv
 * 
 * @author Charles MECHERIKI
 *
 */
@Repository
@Qualifier("csv")
public class ArchiveRepositoryCsv implements ArchiveRepository {

	/**
	 * Logger
	 */
    private static final Logger logger = LogManager.getLogger(ArchiveRepositoryCsv.class);
    
    /**
     * Extracteur csv
     */
    @Autowired
    private ExtracteurCSV extracteur;

    public ArrayList<Archive> obtenirListeArchives(
    		Scenario scenario,
            Site site) throws Exception {

    		ArrayList<Archive> archives = new ArrayList<Archive>();
    		ArrayList<FiltreArchive> filtres = new ArrayList<FiltreArchive>();
    		
    		// Ajoute les archives de reference quelque soit le scenario
			if (!scenario.equals(Scenario.REFERENCE)) {
				archives.addAll(obtenirListeArchives(
					Scenario.REFERENCE,
					site
				));
			}

			// Determine le chemin du fichier .csv en fonction du site et du scenario
			String cheminRepertoireFichierCsv = MessageFormat.format(
					"static/csv/{0}/{0}_{1}.csv",
					site.toString(),
					scenario.toString()
			);

			InputStream flux = null;

			try {
				// On recupere un InputStream du fichier (compatible avec l'exportation jar du projet)
				flux = Thread.currentThread().getContextClassLoader().getResourceAsStream(
						cheminRepertoireFichierCsv
				);
    		}
    		catch (Exception e) {
    			e.printStackTrace();
        		throw new IllegalArgumentException(
        				String.format(
        						"Un fichier est attendu dans le répertoire '%s'",
        						cheminRepertoireFichierCsv
        				)
        		);
    		}
			
			logger.info(
					String.format("Fichier à extraire : '%s'", cheminRepertoireFichierCsv)
			);
			
			// Extraction des archives depuis le fichier .csv
			archives.addAll(
					extracteur.extraireArchivesClimatDepuisCsv(
							flux, filtres
					)
			);
			
			logger.info(
					String.format(
							"Fichier '%s' extrait, %d entrées lues",
							cheminRepertoireFichierCsv, archives.size()
					)
			);
			
			return archives;
    }
}
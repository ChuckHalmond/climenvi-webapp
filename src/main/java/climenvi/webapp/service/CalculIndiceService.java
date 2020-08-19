package climenvi.webapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import climenvi.webapp.api.requete.RequeteCalculIndice;
import climenvi.webapp.metier.calcul.CalculDonneesIndice;
import climenvi.webapp.metier.calcul.donnees.DonneesIndice;
import climenvi.webapp.metier.calcul.formule.FormuleIndice;
import climenvi.webapp.metier.model.archive.Archive;
import climenvi.webapp.metier.model.indice.Indice;
import climenvi.webapp.metier.model.periode.Periode;
import climenvi.webapp.metier.model.representation.Representation;
import climenvi.webapp.metier.model.scenario.Scenario;
import climenvi.webapp.metier.model.site.Site;
import climenvi.webapp.metier.repo.archive.ArchiveRepository;
import climenvi.webapp.metier.repo.site.SiteRepository;

/**
 * Service de calcul des indices
 * 
 * @author Charles MECHERIKI
 *
 */
@Service
public class CalculIndiceService {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CalculIndiceService.class);
	
	/**
	 * ArchiveRepository utilisant un systeme de proxy
	 */
	@Autowired
	@Qualifier("proxy")
	private ArchiveRepository archiveRepo;
	
	/**
	 * SiteRepository statique (lecture seule)
	 */
	@Autowired
	@Qualifier("statique")
	private SiteRepository siteRepo;

	/**
	 * Renvoie les donnees de l'indice repondant a la requete donnee
	 * 
	 * @param requete				requete de calcul de donnees (scenario, site, periode, indice, representation)
	 * @return						les donnees de l'indice repondant a la requete
	 * @throws Exception			si une erreur est survenu pendant une des etapes du calcul des donnees
	 */
	public Set<? extends DonneesIndice> calculerDonneesIndice(
			RequeteCalculIndice requete) throws Exception {

		// Verification de la validite de la requete
		
		Scenario scenario = requete.getScenario();
		
		if (scenario == null) {
			throw new IllegalArgumentException("Scénario invalide");
		}
		
		Periode periode = requete.getPeriode();
		
		if (periode == null) {
			throw new IllegalArgumentException("Période invalide");
		}

		Site site = requete.getSite();

		if (site == null) {
			throw new IllegalArgumentException("Site invalide");
		}

		Indice indice = requete.getIndice();
		
		if (indice == null) {
			throw new IllegalArgumentException("Indice invalide");
		}

		Representation representation = requete.getRepresentation();
		
		if (representation == null) {
			throw new IllegalArgumentException("Représentation invalide");
		}

		// Recuperation de la liste des archives pour le scenario et le site donnes
		
		ArrayList<Archive> archives = archiveRepo.obtenirListeArchives(
				scenario, site
		);
		
		logger.info(
				String.format(
						"Récuperations des archives pour l'indice '%s' terminée",
						indice
				)
		);

		// Calcul des donnees pour l'indice et les archives donnees
		
		FormuleIndice formule = null;
		
		try {
			Class<?> classe = Class.forName(
				FormuleIndice.class.getName().replace(
					FormuleIndice.class.getSimpleName(),
					"impl." + FormuleIndice.class.getSimpleName() + indice.getAcronyme()
				)
			);
			
			formule = (FormuleIndice)classe.newInstance();
		}
		catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(
					String.format("Aucune formule n'existe pour l'indice '%s'", indice.getAcronyme())
			);
		}

		CalculDonneesIndice<?> calcul = null;
		try {
			Class<?> classe = Class.forName(
				CalculDonneesIndice.class.getName().replace(
					CalculDonneesIndice.class.getSimpleName(),
					"impl." + CalculDonneesIndice.class.getSimpleName() + representation.getPrototype().getNom()
				)
			);

			calcul = (CalculDonneesIndice<?>)classe.newInstance();
		}
		catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(
					String.format(
							"Aucune calcul de données n'existe pour la représentation '%s'",
							representation.getPrototype().getNom()
					)
			);
		}

		Set<? extends DonneesIndice> donnees = Collections.singleton(
				calcul.calculDonnees(requete, formule, archives)
		);

		logger.info(
				String.format(
						"Calcul des données de l'indice '%s' terminé",
						indice
				)
		);
		
		return donnees;
	}
}
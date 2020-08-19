package climenvi.webapp.metier.repo.site.impl.statique;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import climenvi.webapp.metier.model.site.Site;
import climenvi.webapp.metier.repo.site.SiteRepository;

/**
 * Implementation statique du SiteRepository (lecture seule)
 * 
 * @author Charles MECHERIKI
 *
 */
@Repository
@Qualifier("statique")
public class SiteRepositoryStatique implements SiteRepository {
	
	/** 
	 * Repository statique des sites
	 */
	private static final ArrayList<Site> sites = new ArrayList<Site>() {
		
		private static final long serialVersionUID = 1L;

		{
			add(new Site("Meusnes", "41130", 11357, 47.23810f, 1.54473f));
			add(new Site("Cravant-les-Coteaux", "37500", 11203, 47.15230f, 0.38540f));
			add(new Site("Verdigny", "18300", 11357, 47.38380f, 2.81397f));
		}
	};

	@Override
	public Site obtenirSiteParNom(String nomSite) throws IllegalArgumentException {
		
		for (Site site : sites) {
			if (site.getNom().contentEquals(nomSite)) {
				return site;
			}
		}

		throw new IllegalArgumentException(
				String.format("Le site de nom '%s' est inconnu", nomSite)
		);
	}

	public ArrayList<Site> obtenirListe() {
		return sites;
	}
}

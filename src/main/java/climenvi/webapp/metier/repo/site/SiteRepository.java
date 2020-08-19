package climenvi.webapp.metier.repo.site;

import java.util.ArrayList;

import climenvi.webapp.metier.model.site.Site;

/**
 * Interface pour acceder aux sites
 * 
 * Devra a terme supporter l'ajout et la suppression de sites
 * 
 * @author Charles MECHERIKI
 *
 */
public interface SiteRepository {
	
	/**
	 * Renvoie le site de nom donne s'il existe
	 * 
	 * @param nomSite		le nom du site recherche
	 * @return				le site associe au nom
	 * @throws Exception	si le site est inconnu ou la recuperation a echouee
	 */
	public Site obtenirSiteParNom(String nomSite) throws Exception;
	
	/**
	 * Renvoie la liste des sites
	 * 
	 *  @return la liste des sites
	 */
	public ArrayList<Site> obtenirListe();
}

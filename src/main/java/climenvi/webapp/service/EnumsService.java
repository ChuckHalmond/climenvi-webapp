package climenvi.webapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import climenvi.webapp.metier.model.indice.Indice;
import climenvi.webapp.metier.model.periode.Periode;
import climenvi.webapp.metier.model.representation.prototype.PrototypeRepresentation;
import climenvi.webapp.metier.model.scenario.Scenario;
import climenvi.webapp.metier.model.site.Site;
import climenvi.webapp.metier.model.utilisateur.CategorieUtilisateur;
import climenvi.webapp.metier.repo.site.SiteRepository;

/**
 * Service de recuperation des enumerations
 * 
 * @author Charles MECHERIKI
 *
 */
@Service
public class EnumsService {
	
	/**
	 * SiteRepository statique (lecture seule)
	 */
	@Autowired
	@Qualifier("statique")
	private SiteRepository siteRepo;

	/**
	 * Renvoie la liste des sites depuis le repository
	 *  
	 * @return la liste des sites depuis le repository
	 */
	public ArrayList<Site> sites() {
		return siteRepo.obtenirListe();
    }
	
	/**
	 * Renvoie un tableau contenant la liste des indices
	 * 
	 * @return un tableau contenant la liste des indices
	 */
	public Indice[] indices() {
		return Indice.values();
    }
	
	/**
	 * Renvoie un tableau contenant la liste des scenarios
	 * 
	 * @return un tableau contenant la liste des scenarios
	 */
	public Scenario[] scenarios() {
		return Scenario.values();
    }

	/**
	 * Renvoie un tableau contenant la liste des periodes
	 * 
	 * @return un tableau contenant la liste des periodes
	 */
	public Periode[] periodes() {
		return Periode.values();
    }
	
	/**
	 * Renvoie un tableau contenant la liste des prototypes de representations
	 * 
	 * @return un tableau contenant la liste des prototypes de representations
	 */
	public PrototypeRepresentation[] prototypesRepresentation() {
		return PrototypeRepresentation.values();
	}
	
	/**
	 * Renvoie un tableau contenant la liste des categories d'utilisateur
	 * 
	 * @return un tableau contenant la liste des categories d'utilisateur
	 */
	public CategorieUtilisateur[] categoriesUtilisateur() {
		return CategorieUtilisateur.values();
    }

}

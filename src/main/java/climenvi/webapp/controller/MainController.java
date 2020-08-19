package climenvi.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import climenvi.webapp.service.EnumsService;

/**
 * Controleur principal de l'application
 * 
 * @author Charles MECHERIKI
 *
 */
@Controller
@RequestMapping("/")
public class MainController {

	/**
	 * Service de recuperation des enumerations
	 */
	@Autowired
	private EnumsService enumsService;
	
	/**
	 * Renvoie la page d'accueil
	 * 
	 * @param model	le model a associer a la page
	 * @return 		la page d'accueil
	 */
	@GetMapping
	public String accueil(Model model) {
		return "page/accueil";
	}

	/**
	 * Renvoie la page des indices
	 * 
	 * @param model	le model a associer a la page
	 * @return		la page des indices
	 */
	@GetMapping("indices")
	public String test(Model model) {
		
		model.addAttribute("prototypesRepresentation", enumsService.prototypesRepresentation());
		model.addAttribute("indices", enumsService.indices());
		
		return "page/indices";
	}
	
	/**
	 * Ajoute systematiquement au model des pages du controleur les categories d'utilisateur
	 * 
	 * @param model	le model associer aux pages du controleur
	 */
	@ModelAttribute
    public void categoriesUtilisateur(Model model) {
        model.addAttribute("categories", enumsService.categoriesUtilisateur());
    }
}

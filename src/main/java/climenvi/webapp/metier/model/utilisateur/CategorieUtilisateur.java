package climenvi.webapp.metier.model.utilisateur;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Categories d'utilisateur de la plateforme
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
public enum CategorieUtilisateur {
	/**
	 * Viticulteur ou vigneron
	 */
	VITICULTEUR("Viticulteur ou vigneron"),
	
	/**
	 * Conseiller ou expert
	 */
	CONSEILLER("Conseiller ou expert"),
	
	/**
	 * Formateur ou etudiant
	 */
	FORMATEUR("Formateur ou étudiant");
	
	/**
	 * Titre de la categorie utilisateur
	 */
	@Getter
	private String titre;
	
	/**
	 * Collection statique contenant les valeurs de l'enumeration
	 * utilisee pour pouvoir retrouver la valeur de la categorie utilisateur depuis son titre
	 */
	private static Map<String, CategorieUtilisateur> categorieUtilisateurs =
		Stream.of(
				CategorieUtilisateur.values()
		)
		.collect(
				Collectors.toMap(
						categorieUtilisateur -> categorieUtilisateur.titre, Function.identity()
				)
		);
	
	/**
	 * Renvoie la categorie utilisateur associee au titre passe en parametre
	 * 
	 * @param titre	le titre du scenario
	 * @return		la categorie utilisateur associe
	 */
    @JsonCreator
    public static CategorieUtilisateur fromString(String titre) {
        return Optional
            .ofNullable(categorieUtilisateurs.get(titre))
            .orElseThrow(() -> new IllegalArgumentException(titre));
    }

	/**
	 * Renvoie le titre de la categorie d'utilisateur
	 */
	@Override
	public String toString() {
		return titre;
	}
}

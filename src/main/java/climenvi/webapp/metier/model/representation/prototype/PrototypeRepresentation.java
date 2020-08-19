package climenvi.webapp.metier.model.representation.prototype;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Prototypes de representation (javascript) disponibles pour les indices
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PrototypeRepresentation {

	/**
	 * Prototype en barres
	 */
    BARRES("Barres"),
    
    /**
     * Prototype en boites (a moustache)
     */
    BOITES("Boites"),
    
    /**
     * Prototype en courbe
     */
    COURBE("Courbe");
	
	/**
	 * Nom du prototype
	 */
	@Getter
	private String nom;

	/**
	 * Collection statique contenant les valeurs de l'enumeration
	 * utilisee pour pouvoir retrouver la valeur de prototype depuis son nom
	 */
	private static Map<String, PrototypeRepresentation> prototypes =
		Stream.of(
				PrototypeRepresentation.values()
		)
		.collect(
				Collectors.toMap(
						prototype -> prototype.nom, Function.identity()
				)
		);
	
	/**
	 * Renvoie le prototype associee au nom passe en parametre
	 * 
	 * @param nom	le nom du prototype
	 * @return		prototype associe au nom passe en parametre
	 */
    @JsonCreator
    public static PrototypeRepresentation fromString(@JsonProperty("nom") String nom) {
        return Optional
            .ofNullable(prototypes.get(nom))
            .orElseThrow(() -> new IllegalArgumentException(nom));
    }
    
	/**
	 * Renvoie le nom du prototype
	 */
	@Override
	public String toString() {
		return nom;
	}
}

package climenvi.webapp.metier.model.scenario;

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
 * Scenario climatique selon Meteo France
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Scenario {
	
	/**
	 * Scenario de reference
	 */
    REFERENCE("Reference"),
    
    /**
     * Scenario RCP 4.5
     */
    RCP45("RCP4.5"),
    
    /**
     * Scenario RCP 8.5
     */
    RCP85("RCP8.5");
	
	/**
	 * Titre du scenario
	 */
	@Getter
	private String titre;

	/**
	 * Collection statique contenant les valeurs de l'enumeration
	 * utilisee pour pouvoir retrouver la valeur du scenario depuis son titre
	 */
	private static Map<String, Scenario> scenarios =
		Stream.of(
				Scenario.values()
		)
		.collect(
				Collectors.toMap(
						scenario -> scenario.titre, Function.identity()
				)
		);
	
	/**
	 * Renvoie le scenario associee au titre passe en parametre
	 * 
	 * @param titre	le titre du scenario
	 * @return		le scenario associe
	 */
    @JsonCreator
    public static Scenario fromString(@JsonProperty("titre") String titre) {
        return Optional
            .ofNullable(scenarios.get(titre))
            .orElseThrow(() -> new IllegalArgumentException(titre));
    }
    
	/**
	 * Renvoie le titre du scenario
	 */
	@Override
	public String toString() {
		return titre;
	}
}
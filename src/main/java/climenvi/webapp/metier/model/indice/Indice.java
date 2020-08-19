package climenvi.webapp.metier.model.indice;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import climenvi.webapp.metier.model.representation.Representation;
import climenvi.webapp.metier.model.representation.prototype.PrototypeRepresentation;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Indices climatiques et agro-climatiques, caracterises par un titre, un acronyme,
 * une unite, un type de representation (javascript) et une classe de calcul de donnees
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Indice {
	
	/**
	 * Temperature Moyenne Annuelle
	 */
	TMA("Température Moyenne Annuelle", "TMA", "°C", new Representation(PrototypeRepresentation.COURBE, false)),
	
	/**
	 * Cumul Annuel des Precipitations
	 */
	CAP("Cumul Annuel des Précipitations", "CAP", "mm", new Representation(PrototypeRepresentation.BARRES, false)),
	
	/**
	 * Indice de Winkler
	 */
	WINKLER("Indice de Winkler", "WINKLER", "°C x jours", new Representation(PrototypeRepresentation.BOITES, true)),
	
	/**
	 * Cumul Estival des Precipitations
	 */
	CEP("Cumul Estival des Précipitations", "CEP", "mm", new Representation(PrototypeRepresentation.BOITES, true));
	
	/**
	 * Titre de l'indice
	 */
	@Getter
	private String titre;
	
	/**
	 * Acronyme de l'indice
	 */
	@Getter
	private String acronyme;
	
	/**
	 * Unite de l'indice
	 */
	@Getter
	private String unite;

	/**
	 * Representation choisie pour l'indice
	 */
	@Getter
	private Representation representation;
	
	@Override
	public String toString() {
		return titre;
	}
	
	/**
	 * Collection statique contenant les valeurs des enumerations
	 * utilisee pour pouvoir retrouver la valeur de l'indice depuis son acronyme
	 */
	private static Map<String, Indice> indices =
		Stream.of(
				Indice.values()
		)
		.collect(
				Collectors.toMap(
						indice -> indice.acronyme, Function.identity()
				)
		);
	
	/**
	 * Renvoie l'indice associe a l'acronyme passe en parametre
	 * 
	 * @param acronyme	l'acronyme de l'indice
	 * @return			l'indice associe
	 */
    @JsonCreator
    public static Indice fromString(@JsonProperty("acronyme") String acronyme) {
        return Optional
            .ofNullable(indices.get(acronyme))
            .orElseThrow(() -> new IllegalArgumentException(acronyme));
    }
}

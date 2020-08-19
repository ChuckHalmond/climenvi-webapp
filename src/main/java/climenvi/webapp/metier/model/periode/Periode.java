package climenvi.webapp.metier.model.periode;

import java.time.LocalDate;
import java.time.Period;
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
 * Periode de temps, caracterisee par une date de debut, une date de fin et un nom
 * Ces periodes sont elles-memes composees de sous-periodes (reference, futur proche et futur lointain)
 * 
 * @author Charles MECHERIKI
 *
 */
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Periode {

	/**
	 * Periode de reference, du 1er janvier 1976 au 31 decembre 2005
	 */
	REFERENCE("Référence", LocalDate.of(1976, 1, 1), LocalDate.of(2005, 12, 31), null),
	
	/**
	 * Periode de futur proche, du 1er janvier 2021 au 31 decembre 2050
	 */
	FUTUR_PROCHE("Futur proche", LocalDate.of(2021, 1, 1), LocalDate.of(2050, 12, 31), null),
	
	/**
	 * Periode de futur lointain, du 1er janvier 2071 au 31 decembre 2100
	 */
	FUTUR_LOINTAIN("Futur lointain", LocalDate.of(2071, 1, 1), LocalDate.of(2100, 12, 31), null),
	
	/**
	 * Periode de futur, du 1er janvier 2006 au 31 decembre 2100
	 */
	FUTUR("Futur", LocalDate.of(2006, 1, 1), LocalDate.of(2100, 12, 31), new Periode[] {Periode.FUTUR_PROCHE, Periode.FUTUR_LOINTAIN}),
	
	/**
	 * Periode globale, du 1er janvier 1950 au 31 decembre 2100
	 */
	GLOBALE("Globale", LocalDate.of(1950, 1, 1), LocalDate.of(2100, 12, 31), new Periode[] {Periode.REFERENCE, Periode.FUTUR_PROCHE, Periode.FUTUR_LOINTAIN});
    
    /**
     * Titre de la periode
     */
	@Getter
    private String titre;

    /**
     * Date de debut de la periode
     */
	@Getter
    private LocalDate dateDebut;

    /**
     * Date de fin de la periode
     */
	@Getter
    private LocalDate dateFin;

    /**
     * Sous periode contenues dans la periode
     */
	@Getter
    private Periode[] sousPeriodes;	
	
	/**
	 * Collection statique contenant les valeurs de l'enumeration
	 * utilisee pour pouvoir retrouver la valeur de la periode depuis son titre
	 */
	private static Map<String, Periode> periodes =
		Stream.of(
				Periode.values()
		)
		.collect(
				Collectors.toMap(
						periode ->periode.titre, Function.identity()
				)
		);
	
	/**
	 * Renvoie la periode associee au titre passe en parametre
	 * 
	 * @param titre		le titre de la periode
	 * @return			la periode associee
	 */
    @JsonCreator
    public static Periode fromString(@JsonProperty("titre") String titre) {
        return Optional
            .ofNullable(periodes.get(titre))
            .orElseThrow(() -> new IllegalArgumentException(titre));
    }
    
    /**
     * Renvoie la date de milieu de periode
     * 
     * @return la date de milieu de periode
     */
    public LocalDate getMiddleDate() {
    	return dateDebut.plusDays(Period.between(dateDebut, dateFin).getDays() / 2);
    }

	/**
	 * Renvoie le titre de la periode
	 */
    @Override
    public String toString() {
    	return titre;
    }
}
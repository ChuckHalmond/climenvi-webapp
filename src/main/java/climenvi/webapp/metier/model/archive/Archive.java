package climenvi.webapp.metier.model.archive;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Archive climatique, correspondant a differentes donnees climatiques et agro-climatiques
 * relevees a une date precise
 * Equivalent a une unique ligne d'un fichier de donnees issues de la plateforme Drias
 * 
 * @author Charles MECHERIKI
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Archive {

    /**
     * Date du jour
     */
    private LocalDate date;

    /**
     * Temperature minimale journaliere à 2m (°C)
     */
    private float temperatureMin;

    /**
     * Temperature maximale journalière à 2m (°C)
     */
    private float temperatureMax;

    /**
     * Precipitations liquides a grande echelle	(mm / jour)
     */
    private float precipitation;

    /**
     * Chute de neige a grande echelle (mm / jour)
     */
    private float chuteNeige;

    /**
     * Humidite specifique à 2m (g / kg)
     */
    private float humidite;

    /**
     * Rayonnement visible incident a la surface (W / m2)
     */
    private float rayonnementVisible;

    /**
     * Rayonnement infrarouge incident a la surface (W / m2)
     */
    private float rayonnementInfra;

    /**
     * Vitesse du vent a 10m (m / s)
     */
    private float vitesseVent;

    /**
     * Maximum journalier des rafales de vent a 10m (m / s)
     */
    private float maxRafaleVent;
}
package climenvi.webapp.metier.model.site;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Site geographique, caracterise par son nom, son code postal, son point
 * Safran (utilise par la plateforme Drias) et ses coordonnees geographiques
 * 
 * @author Charles MECHERIKI
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Site {

    /**
     * Nom du site
     */
    private String nom;
    
    /**
     * Code postal du site
     */
    private String codePostal;

    /**
     * Point safran associe au site
     */
    private int pointSafran;
    
    /**
     * Latitude du point de grille SAFRAN en degrss decimaux (WGS84) du site
     */
    private float latitude;
    
    /**
     * Longitude du point de grille SAFRAN en degres decimaux (WGS84) du site
     */
    private float longitude;

    @Override
    public String toString() {
    	return nom;
    }
}
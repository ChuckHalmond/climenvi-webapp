package climenvi.webapp.metier.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import climenvi.webapp.metier.filtre.FiltreArchive;
import climenvi.webapp.metier.model.archive.Archive;

/**
 * Composant permettant d'extraire des archives climat depuis un fichier .csv correctement formate
 * Des filtres peuvent etre ajoute a l'extraction pour filtrer les archives
 * Utilise un InputStream pour lire les fichiers .csv afin d'etre compatible avec l'exportation jar du projet
 * 
 * @author Charles MECHERIKI
 *
 */
@Component
public class ExtracteurCSV {

    private static final Logger logger = LogManager.getLogger(ExtracteurCSV.class);
    
    /**
     * Symbole de commentaire
     */
    private static final char symboleCommentaire = '#';
    
    /**
     * Symbole de delimitation
     */
    private static final char symboleDelimitation = ';';
    
    /**
     * Entete du fichier csv (l'ordre doit correspondre)
     */
    private static final String[] entete = {
        "date", "latitude", "longitude", "temperatureMin", "temperatureMax", "precipitation", "chuteNeige", "humidite",
        "rayonnementVisible", "rayonnementInfra", "vitesseVent", "maxRafaleVent"
    };
    
    /**
     * Extrait les archives climat presentent dans le fichier csv qui respectent les critere donnees
     * 
     * @param fluxCsv	flux vers le fichier csv contenant les archives climat
     * @param filtres	les filtres devant etre respectes par les archives
     * @return			les archives climats du fichier respectant les criteres
     * @throws IOException					si le fichier ne peux pas etre ouvert
     * @throws IllegalArgumentException		si le formatage des donnees du fichier n'est pas bon
     */
    public ArrayList<Archive> extraireArchivesClimatDepuisCsv(InputStream fluxCsv, List<FiltreArchive> filtres) throws IOException, IllegalArgumentException {
        ArrayList<Archive> archives = new ArrayList<>();

        Reader lecteurFichier = new InputStreamReader(fluxCsv);

        Iterable<CSVRecord> entrees = CSVFormat.DEFAULT
            .withCommentMarker(symboleCommentaire)
            .withDelimiter(symboleDelimitation)
            .withHeader(entete)
            .parse(lecteurFichier);

        int numLigne = 0;

        boucleEntrees:
        for (CSVRecord entree : entrees) {

            // Si la ligne respecte la structure de l'entete spécifiee plus haut (nombre de valeurs)
            if (entree.isConsistent()) {

                try {
                    Archive archive = new Archive(
                        LocalDate.parse(entree.get("date"), DateTimeFormatter.ofPattern("yyyyMMdd")),
                        Float.parseFloat(entree.get("temperatureMin")),
                        Float.parseFloat(entree.get("temperatureMax")),
                        Float.parseFloat(entree.get("precipitation")),
                        Float.parseFloat(entree.get("chuteNeige")),
                        Float.parseFloat(entree.get("humidite")),
                        Float.parseFloat(entree.get("rayonnementVisible")),
                        Float.parseFloat(entree.get("rayonnementInfra")),
                        Float.parseFloat(entree.get("vitesseVent")),
                        Float.parseFloat(entree.get("maxRafaleVent"))
                    );

                    for (FiltreArchive filtre : filtres) {
                        if (!filtre.accepte(archive)) {
                            continue boucleEntrees;
                        }
                    }

                    archives.add(archive);
                }
                catch (IllegalArgumentException e) {
                    String msg = String.format(
                        "Le formatage a la ligne %d n'est pas reconnu", numLigne
                    );

                    logger.error(msg);

                    throw new IllegalArgumentException(msg);
                }
            }
            else {
                String msg = String.format(
                    "Le formatage à la ligne %d n'est pas reconnu", numLigne
                );

                logger.error(msg);

                throw new IllegalArgumentException(msg);
            }

            numLigne++;
        }

        return archives;
    }
}
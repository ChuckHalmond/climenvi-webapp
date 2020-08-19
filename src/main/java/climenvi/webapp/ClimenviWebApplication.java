package climenvi.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de lancement de l'application SpringBoot
 * 
 * @author Charles MECHERIKI
 *
 */
@SpringBootApplication
public class ClimenviWebApplication {

	/**
	 * Lance l'application avec les arguments donnes
	 * 
	 * @param args	les arguments pour l'application
	 */
	public static void main(String[] args) {
		SpringApplication.run(ClimenviWebApplication.class, args);
	}
}

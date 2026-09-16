package simulationfeuforet;

/**
 * Point d'entrée du programme de simulation de feu de forêt.
 * <p>
 * Cette classe restera volontairement "fine" : elle ne fait qu'orchestrer
 * les appels aux autres classes (lecture de la config, construction de la
 * simulation, exécution, affichage), sans logique métier propre.
 */
public class Main {



    public static void main(String[] args) {

        String cheminConfig = "src/main/resources/application.properties";

        try {
            //Permet de LIRE le fichier config
            LecteurConfiguration lecteur = new LecteurConfigurationProperties();

            //Charge les props config dans configurationSimulation (record simple qui check la validité des valeurs d'entrées)
            ConfigurationSimulation config = lecteur.charger(cheminConfig);

            //Affichage de la configuration
            System.out.println("Configuration chargée : grille %dx%d, probabilité=%.2f, %d case(s) en feu au départ."
                    .formatted(config.hauteur(), config.largeur(), config.probabilite(), config.positionsInitiales().size()));
            System.out.println();


            //Generation de la simulation à l'etape 0
            GenerateurAleatoire generateurAleatoire = new GenerateurAleatoireImpl();
            SimulationFeu simulation = SimulationFeu.depuisConfig(config, generateurAleatoire);

            // Execution et affichage de la simulation
            simulation.executer();

        } catch (ConfigurationInvalideException | IllegalArgumentException e) {
            System.err.println("Erreur de configuration : " + e.getMessage());
            System.exit(1);
        }
    }
}
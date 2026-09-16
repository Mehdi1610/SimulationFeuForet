package simulationfeuforet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LecteurConfigurationProperties implements LecteurConfiguration{

    //Regex pour recuperer et convertir le champ feuInitial en liste de position
    private static final Pattern PATTERN_POSITION = Pattern.compile("\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");

    @Override
    public ConfigurationSimulation charger(String cheminProperties) {
            Properties properties= chargerProperties(cheminProperties);

            int hauteur = lireEntier(properties, "hauteur");
            int largeur = lireEntier(properties, "largeur");
            double probabilite = lireDouble(properties, "probabilite");
            List<Position> positions = lirePosition(properties, "feuInitial");

            return new ConfigurationSimulation(hauteur,largeur,probabilite,positions,null);
     }

    private Properties chargerProperties(String cheminProperties) {

        Properties properties = new Properties();
        Path chemin = Path.of(cheminProperties);

        if (!Files.exists(chemin)) {
            throw new ConfigurationInvalideException("Fichier de configuration introuvable : " + cheminProperties);
        }

        try (InputStream flux = Files.newInputStream(chemin)) {
            properties.load(flux);
        } catch (IOException e) {
            throw new ConfigurationInvalideException("Impossible de lire le fichier : " + cheminProperties, e);
        }

        return properties;
    }

    private String lectureChamps(Properties properties, String champ) {
        String valeur = properties.getProperty(champ);
        if (valeur == null || valeur.isBlank()) {
            throw new ConfigurationInvalideException("Champ manquant dans le fichier de configuration : " + champ);
        }
        return valeur;
    }

    private int lireEntier(Properties properties, String champ){
        String valeur = lectureChamps(properties,champ);
        try{
            return Integer.parseInt(valeur.trim());
        } catch (NumberFormatException e){
            throw new ConfigurationInvalideException(
            "Le champ n'est pas un entier, valeur reçu : '%s' ".formatted(champ, valeur), e);
        }
    }

    private double lireDouble(Properties properties, String champ){
        String valeur = lectureChamps(properties, champ);
        try{
            return Double.parseDouble(valeur.trim());
        }catch (NumberFormatException e){
            throw new ConfigurationInvalideException(
                    "Le champ n'est pas un double, valeur reçu : '%s' ".formatted(champ, valeur), e);
        }
    }

    private List<Position> lirePosition(Properties properties, String champ){
        String valeur = lectureChamps(properties,champ);

        Matcher matcher = PATTERN_POSITION.matcher(valeur);
        List<Position> positions = new ArrayList<>();

        while (matcher.find()){
            int colonne = Integer.parseInt(matcher.group(1));
            int ligne = Integer.parseInt(matcher.group(2));
            positions.add(new Position(ligne,colonne));
        }
        if (positions.isEmpty()) {
            throw new ConfigurationInvalideException(
                    "Le champ '%s' ne contient aucune position valide, format attendu : [(x,y),...], reçu : '%s'"
                            .formatted(champ, valeur));
        }

        return positions;
    }
}

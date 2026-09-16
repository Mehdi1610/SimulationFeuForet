package simulationfeuforet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class LecteurConfigurationPropertiesTest {

    private final LecteurConfiguration lecteur = new LecteurConfigurationProperties();

    //ICI, x= colonne, y= ligne

    @Test
    void litUneConfigurationValideAvecUneSeulePosition(@TempDir Path dossierTemp) throws IOException {
        Path fichier = ecrireFichier(dossierTemp, """
                hauteur=10
                largeur=10
                probabilite=0.37
                feuInitial=[(5,5)]
                """);

        ConfigurationSimulation config = lecteur.charger(fichier.toString());

        assertEquals(10, config.hauteur());
        assertEquals(10, config.largeur());
        assertEquals(0.37, config.probabilite());
        assertEquals(1, config.positionsInitiales().size());
        assertEquals(new Position(5, 5), config.positionsInitiales().get(0));
    }

    @Test
    void litPlusieursPositionsInitiales(@TempDir Path dossierTemp) throws IOException {
        Path fichier = ecrireFichier(dossierTemp, """
                hauteur=10
                largeur=10
                probabilite=0.5
                feuInitial=[(5,5),(2,3)]
                """);

        ConfigurationSimulation config = lecteur.charger(fichier.toString());

        assertEquals(2, config.positionsInitiales().size());
        assertTrue(config.positionsInitiales().contains(new Position(5, 5)));
        assertTrue(config.positionsInitiales().contains(new Position(3, 2)));
    }

    @Test
    void inverseBienXYEnLigneColonne(@TempDir Path dossierTemp) throws IOException {
        Path fichier = ecrireFichier(dossierTemp, """
                hauteur=10
                largeur=10
                probabilite=0.5
                feuInitial=[(2,7)]
                """);

        ConfigurationSimulation config = lecteur.charger(fichier.toString());

        Position position = config.positionsInitiales().get(0);
        assertEquals(7, position.ligne(), "y du fichier doit devenir la ligne");
        assertEquals(2, position.colonne(), "x du fichier doit devenir la colonne");
    }

    @Test
    void unFichierInexistantLeveUneException() {
        assertThrows(ConfigurationInvalideException.class,
                () -> lecteur.charger("chemin/qui/nexiste/pas.properties"));
    }

    @Test
    void unChampManquanteLeveUneException(@TempDir Path dossierTemp) throws IOException {
        Path fichier = ecrireFichier(dossierTemp, """
                hauteur=10
                largeur=10
                probabilite=0.5
                """); // feuInitial manquant

        assertThrows(ConfigurationInvalideException.class, () -> lecteur.charger(fichier.toString()));
    }

    @Test
    void unePositionInvalideDansFeuInitialEstDetectee(@TempDir Path dossierTemp) throws IOException {
        Path fichier = ecrireFichier(dossierTemp, """
                hauteur=10
                largeur=10
                probabilite=0.5
                feuInitial=[(15,15)]
                """); // hors grille -> rejeté par ConfigurationSimulation

        assertThrows(IllegalArgumentException.class, () -> lecteur.charger(fichier.toString()));
    }

    @Test
    void unNombreMalFormeLeveUneException(@TempDir Path dossierTemp) throws IOException {
        Path fichier = ecrireFichier(dossierTemp, """
                hauteur=abc
                largeur=10
                probabilite=0.5
                feuInitial=[(5,5)]
                """);

        assertThrows(ConfigurationInvalideException.class, () -> lecteur.charger(fichier.toString()));
    }

    private Path ecrireFichier(Path dossier, String contenu) throws IOException {
        Path fichier = dossier.resolve("config.properties");
        Files.writeString(fichier, contenu);
        return fichier;
    }
}

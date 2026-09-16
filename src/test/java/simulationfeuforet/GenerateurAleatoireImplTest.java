package simulationfeuforet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateurAleatoireImplTest {

    @Test
    void uneProbabiliteDeZeroNeDeclencheJamaisLEvenement() {
        GenerateurAleatoire generateur = new GenerateurAleatoireImpl(42L);
        for (int i = 0; i < 1000; i++) {
            assertFalse(generateur.tirer(0.0));
        }
    }

    @Test
    void uneProbabiliteDeUnDeclencheToujoursLEvenement() {
        GenerateurAleatoire generateur = new GenerateurAleatoireImpl(42L);
        for (int i = 0; i < 1000; i++) {
            assertTrue(generateur.tirer(1.0));
        }
    }

    @Test
    void deuxGenerateursAvecLaMemeSeedProduisentLaMemeSequence() {
        GenerateurAleatoire generateur1 = new GenerateurAleatoireImpl(123L);
        GenerateurAleatoire generateur2 = new GenerateurAleatoireImpl(123L);

        for (int i = 0; i < 20; i++) {
            assertEquals(generateur1.tirer(0.5), generateur2.tirer(0.5));
        }
    }

    @Test
    void uneProbabiliteHorsDeZeroUnLeveUneException() {
        GenerateurAleatoire generateur = new GenerateurAleatoireImpl(1L);
        assertThrows(IllegalArgumentException.class, () -> generateur.tirer(-0.1));
        assertThrows(IllegalArgumentException.class, () -> generateur.tirer(1.1));
    }
}

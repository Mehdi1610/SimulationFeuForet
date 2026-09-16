package simulationfeuforet;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationSimulationTest {

    @Test
    void configurationValideEstAcceptee() {
        ConfigurationSimulation config = new ConfigurationSimulation(5, 5, 0.3, List.of(new Position(2, 2)), null);

        assertEquals(5, config.hauteur());
        assertEquals(5, config.largeur());
        assertEquals(0.3, config.probabilite());
        assertEquals(1, config.positionsInitiales().size());
        assertFalse(config.possedeSeedFixe());
    }

    @Test
    void seedFixeEstDetectee() {
        ConfigurationSimulation config = new ConfigurationSimulation(
                5, 5, 0.5, List.of(new Position(0, 0)), 13L);
        assertTrue(config.possedeSeedFixe());
    }



    @Test
    void dimensionsInvalidesEstRejetees() {
        assertThrows(IllegalArgumentException.class,
                () -> new ConfigurationSimulation(0, 5, 0.5, List.of(), null));
        assertThrows(IllegalArgumentException.class,
                () -> new ConfigurationSimulation(5, -1, 0.5, List.of(), null));
    }

    @Test
    void probabiliteHorsDeZeroUnEstRejetee() {
        assertThrows(IllegalArgumentException.class,
                () -> new ConfigurationSimulation(5, 5, -0.1, List.of(), null));
        assertThrows(IllegalArgumentException.class,
                () -> new ConfigurationSimulation(5, 5, 1.1, List.of(), null));
    }

    @Test
    void positionInitialeHorsGrilleEstRejetee() {
        assertThrows(IllegalArgumentException.class,
                () -> new ConfigurationSimulation(5, 5, 0.5, List.of(new Position(10, 10)), null));
    }

    @Test
    void uneListeNulleEstRejetee() {
        assertThrows(IllegalArgumentException.class,
                () -> new ConfigurationSimulation(5, 5, 0.5, null, null));
    }
}

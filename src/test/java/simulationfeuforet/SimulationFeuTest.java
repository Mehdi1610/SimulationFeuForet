package simulationfeuforet;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationFeuTest {



    @Test
    void avecProbabiliteUnLeFeuSePropageATousLesVoisinsValides() {
        Grille grille = new Grille(5, 5);
        grille.allumerFeu(2, 2); // 4 voisins valides (case centrale)

        SimulationFeu simulation = new SimulationFeu(grille, 1.0, new GenerateurAleatoireImpl(1L));
        simulation.etapeSuivante();

        Grille apresUneEtape = simulation.getetapeEnCours();
        assertEquals(EtatCase.CENDRE, apresUneEtape.getEtat(2, 2));
        for (Position voisin : grille.getVoisins(2, 2)) {
            assertEquals(EtatCase.EN_FEU, apresUneEtape.getEtat(voisin));
        }
    }

    @Test
    void laSimulationSeTermineQuandPlusAucuneCaseNEstEnFeu() {
        Grille grille = new Grille(3, 3);
        grille.allumerFeu(1, 1);

        SimulationFeu simulation = new SimulationFeu(grille, 1.0, new GenerateurAleatoireImpl(1L));
        assertFalse(simulation.feuEteint());

        simulation.executer();

        assertTrue(simulation.feuEteint());
        assertFalse(simulation.getetapeEnCours().contientDuFeu());
    }

    @Test
    void appelerEtapeSuivanteSurUneSimulationTermineeLeveUneException() {
        Grille grille = new Grille(3, 3);
        SimulationFeu simulation = new SimulationFeu(grille, 0.5, new GenerateurAleatoireImpl(1L));

        assertThrows(IllegalStateException.class, simulation::etapeSuivante);
    }

    @Test
    void lHistoriqueContientBienChaqueEtapeDansLOrdre() {
        Grille grille = new Grille(3, 3);
        grille.allumerFeu(1, 1);

        SimulationFeu simulation = new SimulationFeu(grille, 0.0, new GenerateurAleatoireImpl(1L));
        simulation.executer();

        List<Grille> historique = simulation.getEtapes();
        assertEquals(2, historique.size()); // état initial + 1 étape avant extinction
        assertEquals(EtatCase.EN_FEU, historique.get(0).getEtat(1, 1));
        assertEquals(EtatCase.CENDRE, historique.get(1).getEtat(1, 1));
    }

    @Test
    void depuisConfigConstruitBienLaSimulation() {
        ConfigurationSimulation config = new ConfigurationSimulation(
                5, 5, 0.0, List.of(new Position(2, 2)), null);

        SimulationFeu simulation = SimulationFeu.depuisConfig(config, new GenerateurAleatoireImpl(1L));

        assertEquals(EtatCase.EN_FEU, simulation.getetapeEnCours().getEtat(2, 2));
    }
}

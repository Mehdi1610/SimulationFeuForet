package simulationfeuforet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EtatCaseTest {

    @Test
    void onlyEmptyCanBurn(){
        assertTrue(EtatCase.VIDE.canBurn());
        assertFalse(EtatCase.EN_FEU.canBurn());
        assertFalse(EtatCase.CENDRE.canBurn());
    }

    @Test
    void everyStateHaveADifferentSign(){
        assertEquals('.',EtatCase.VIDE.getSymbole());
        assertEquals('F',EtatCase.EN_FEU.getSymbole());
        assertEquals('#',EtatCase.CENDRE.getSymbole());
    }
}

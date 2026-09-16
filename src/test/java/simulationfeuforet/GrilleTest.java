package simulationfeuforet;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GrilleTest {


    @Test
    void grilleSansFeu() {
        Grille grille = new Grille(5, 5);
        assertFalse(grille.contientDuFeu());
        assertEquals(EtatCase.VIDE, grille.getEtat(2, 2));
    }

    @Test
    void uneCaseAuCentreA4Voisins() {
        Grille grille = new Grille(5, 5);
        List<Position> voisins = grille.getVoisins(2, 2);
        assertEquals(4, voisins.size());
        //Test de chaque emplacement adjacent
        assertTrue(voisins.contains(new Position(1, 2)));
        assertTrue(voisins.contains(new Position(3, 2)));
        assertTrue(voisins.contains(new Position(2, 1)));
        assertTrue(voisins.contains(new Position(2, 3)));
    }

    @Test
    void uneCaseSurUnBordA3Voisins() {
        Grille grille = new Grille(5, 5);
        //Cast millieu haut (pas de voisin superieur)
        List<Position> voisins = grille.getVoisins(0, 2);
        assertEquals(3, voisins.size());
        assertTrue(voisins.contains(new Position(1, 2)));
        assertTrue(voisins.contains(new Position(0, 3)));
        assertFalse(voisins.contains(new Position(-1, 2)));
    }

    @Test
    void uneCaseDansUnCoinA2Voisins() {
        Grille grille = new Grille(5, 5);
        List<Position> voisins = grille.getVoisins(0, 0); // coin haut-gauche
        assertEquals(2, voisins.size());
        assertTrue(voisins.contains(new Position(1, 0)));
        assertTrue(voisins.contains(new Position(0, 1)));
        assertFalse(voisins.contains(new Position(0, -1)));
        assertThrows(IndexOutOfBoundsException.class, () -> grille.allumerFeu(-1, 0));
    }

    @Test
    void allumerFeuChangeLEtatEtContientFeuTrue() {
        Grille grille = new Grille(3, 3);
        grille.allumerFeu(1, 1);
        assertTrue(grille.contientDuFeu());
        assertEquals(EtatCase.EN_FEU, grille.getEtat(1, 1));
    }

    @Test
    void copierProduitUneNouvelleGrille() {
        Grille originale = new Grille(3, 3);
        originale.allumerFeu(1, 1);

        Grille copie = originale.copier();
        copie.setEtat(1, 1, EtatCase.CENDRE);

        assertEquals(EtatCase.CENDRE, copie.getEtat(1, 1));
        assertEquals(EtatCase.EN_FEU, originale.getEtat(1, 1));
        //Originale != Copie
        assertNotEquals(originale.getEtat(1,1),copie.getEtat(1,1));
    }

    @Test
    void positionHorsGrilleLeveIndexException() {
        Grille grille = new Grille(3, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> grille.getEtat(5, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> grille.allumerFeu(-1, 0));
    }

    @Test
    void dimensionsInvalidesLeventIlegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Grille(0, 5));
        assertThrows(IllegalArgumentException.class, () -> new Grille(5, -1));
    }
}

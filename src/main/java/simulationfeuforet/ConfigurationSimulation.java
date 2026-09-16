package simulationfeuforet;

import java.util.List;

public record ConfigurationSimulation(
        int hauteur,
        int largeur,
        double probabilite,
        List<Position> positionsInitiales,
        Long seed //nullable, sert pour les tests
) {
    //Verification des valeurs de la config lors de la création de ConfigurationSimulation
    public ConfigurationSimulation{
        if(hauteur<=0 || largeur<=0){
            throw new IllegalArgumentException("Une ou plusieurs dimensions entré est/sont négatives");
        }
        if(probabilite<0.0 || probabilite>1.0){
            throw new IllegalArgumentException("La probabilité de propagation doit etre comprise entre 0 et 1 (reçu %s"
                    .formatted(probabilite));
        }
        if (positionsInitiales == null){
            throw new IllegalArgumentException("Les positions initiales ne peuvent pas être nulles");
        }
        for (Position position : positionsInitiales) {
            if (position.ligne() < 0 || position.ligne() >= hauteur
                    || position.colonne() < 0 || position.colonne() >= largeur) {
                throw new IllegalArgumentException(
                        "La position initiale %s est hors de la grille (%d x %d)"
                                .formatted(position, hauteur, largeur));
            }
        }
    }
    public boolean possedeSeedFixe() {
        return seed != null;
    }
}

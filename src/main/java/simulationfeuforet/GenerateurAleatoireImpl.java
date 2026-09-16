package simulationfeuforet;

import java.util.Random;

public class GenerateurAleatoireImpl implements GenerateurAleatoire{

    private final Random random;

    public GenerateurAleatoireImpl(){
        this.random=new Random();
    }

    public GenerateurAleatoireImpl(long seed) {
        this.random = new Random(seed);
    }


    //Si true : le feu se propage
    @Override
    public boolean tirer(double probabilite) {
        if (probabilite < 0.0 || probabilite > 1.0) {
            throw new IllegalArgumentException(
                    "La probabilité doit être comprise entre 0 et 1 (reçu %s)".formatted(probabilite));
        }
        return random.nextDouble() < probabilite;
    }
}

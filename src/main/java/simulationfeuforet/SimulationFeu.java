package simulationfeuforet;

import java.util.ArrayList;
import java.util.List;

public class SimulationFeu {

    private static final int MAX_ETAPES = 1000000;

    private final double probabilite;
    private final GenerateurAleatoire generateurAleatoire;
    private final List<Grille> etapes = new ArrayList<>();


    public SimulationFeu(Grille etape0, double probabilite, GenerateurAleatoire generateurAleatoire) {

        if(etape0 == null){
            throw new IllegalArgumentException("La grille initiale ne peut pas être nulle");
        }
        if(probabilite<0.0 || probabilite>1.0){
            throw new IllegalArgumentException("La probabilité doit être comprise entre 0 et 1");
        }
        if(generateurAleatoire == null){
            throw new IllegalArgumentException("Le générateur aléatoire ne peut pas être nul");
        }

        this.probabilite = probabilite;
        this.generateurAleatoire = generateurAleatoire;
        this.etapes.add(etape0.copier());
    }

    public static SimulationFeu depuisConfig(ConfigurationSimulation config, GenerateurAleatoire generateurAleatoire){
        Grille grille = new Grille(config.hauteur(),config.largeur());
        for (Position position: config.positionsInitiales()){
            grille.allumerFeu(position.ligne(), position.colonne());
        }
        return new SimulationFeu(grille, config.probabilite(), generateurAleatoire);
    }

    public Grille getetapeEnCours(){
        return etapes.get(etapes.size()-1);
    }

    public boolean feuEteint(){
        return !getetapeEnCours().contientDuFeu();
    }

    public Grille etapeSuivante(){
        if(feuEteint()){
            throw new IllegalStateException("Il n'y a plus de feu à propager");
        }

        Grille ancienne = getetapeEnCours();
        Grille nouvelle = ancienne.copier();

        List<Position> casesEnFeu = trouverCaseEnFeu(ancienne);

        for(Position position : casesEnFeu){
            nouvelle.setEtat(position, EtatCase.CENDRE);

            for(Position voisin: ancienne.getVoisins(position)){
                if(ancienne.getEtat(voisin).canBurn() && generateurAleatoire.tirer(probabilite)){
                    nouvelle.setEtat(voisin,EtatCase.EN_FEU);
                }
            }
        }
        etapes.add(nouvelle);
        return nouvelle;
    }

    public void afficherEtape(int numero){
        System.out.println("Etape "+ numero +" ");
        System.out.println(getetapeEnCours());
        System.out.println();
    }

    public void executer() {
        int count=0;
        afficherEtape(count);
        while(!feuEteint()){
            etapeSuivante();
            count++;
            afficherEtape(count);
            if (count>MAX_ETAPES){
                throw new IllegalStateException("Nombre Max d'etapes dépassé, fin de simulation");
            }
        }
        System.out.println("Fin de simulation.");
        System.out.println("Terminée en : " + getTotalEtapes() + " étapes.");
    }

    public List<Position> trouverCaseEnFeu(Grille grille){
        List<Position> caseEnFeu = new ArrayList<>();

        for(int ligne=0; ligne< grille.getHauteur(); ligne++){
            for(int colonne=0; colonne< grille.getLargeur(); colonne++){
                if(grille.getEtat(ligne,colonne) == EtatCase.EN_FEU){
                    caseEnFeu.add(new Position(ligne,colonne));
                }
            }
        }
        return caseEnFeu;
    }

    public int getTotalEtapes(){
        return etapes.size()-1;
    }

    public List<Grille> getEtapes(){
        return List.copyOf(etapes);
    }

}

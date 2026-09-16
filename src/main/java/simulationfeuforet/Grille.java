package simulationfeuforet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Grille {


    private final int hauteur;
    private final int largeur;
    private final EtatCase[][] cases;


    public Grille(int hauteur, int largeur) {

        if(hauteur<=0|| largeur<=0){
            throw new IllegalArgumentException("Les dimensions doivent être positives");
        }
        this.hauteur = hauteur;
        this.largeur= largeur;
        this.cases = new EtatCase[hauteur][largeur];
        for (EtatCase[] ligne: cases){
            Arrays.fill(ligne, EtatCase.VIDE);
        }
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getLargeur() {
        return largeur;
    }

    private boolean estDansLaGrille(int ligne, int colonne) {
        return ligne >= 0 && ligne < hauteur && colonne >= 0 && colonne < largeur;
    }

    private void verifierPosition(int ligne, int colonne) {
        if (!estDansLaGrille(ligne, colonne)) {
            throw new IndexOutOfBoundsException(
                    "Position (%d, %d) hors de la grille (%d x %d)"
                            .formatted(ligne, colonne, hauteur, largeur));
        }
    }

    public EtatCase getEtat(int ligne, int colonne){
        verifierPosition(ligne,colonne);
        return cases[ligne][colonne];
    }

    public EtatCase getEtat(Position position){
        return getEtat(position.ligne(),position.colonne());
    }

    public void setEtat(int ligne, int colonne, EtatCase etat){
        verifierPosition(ligne, colonne);
        cases[ligne][colonne] = etat;
    }

    public void setEtat(Position position, EtatCase etat){
        setEtat(position.ligne(), position.colonne(), etat);
    }

    public  void allumerFeu(int ligne, int colonne){
        setEtat(ligne,colonne,EtatCase.EN_FEU);
    }

    public List<Position> getVoisins(int ligne,int colonne){
        verifierPosition(ligne,colonne);
        List<Position> voisins = new ArrayList<>(4);
        int[][] deplacements = {
                {-1,0},
                {1,0},
                {0,-1},
                {0,1}
        };
        for(int[] deplacement: deplacements){
            int ligneVoisine = ligne + deplacement[0];
            int colonneVoisine = colonne + deplacement[1];
            if( estDansLaGrille(ligneVoisine,colonneVoisine)){
                voisins.add(new Position(ligneVoisine,colonneVoisine));
            }
        }
        return voisins;
    }

    public List<Position> getVoisins(Position position){
        return getVoisins(position.ligne(),position.colonne());
    }

    public boolean contientDuFeu(){
        for( EtatCase[] ligne : cases){
            for(EtatCase etat: ligne){
                if(etat == EtatCase.EN_FEU){
                    return true;
                }
            }
        }
        return false;
    }

    public Grille copier(){
        Grille copie = new Grille(hauteur,largeur);
        for(int ligne=0; ligne<hauteur; ligne++){
            System.arraycopy(this.cases[ligne],0,copie.cases[ligne],0,largeur);
        }
        return copie;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (EtatCase[] ligne : cases) {
            for (EtatCase etat : ligne) {
                sb.append(etat.getSymbole()+" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}

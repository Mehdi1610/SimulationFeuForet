package simulationfeuforet;



public enum EtatCase {

    VIDE('■'),
    EN_FEU('⚠'),
    CENDRE('□');

    private final char symbole ;

    EtatCase(char symbole) {
        this.symbole = symbole;
    }

    public char getSymbole() {
        return symbole;
    }

    public boolean canBurn(){
        return this == VIDE;
    }
}

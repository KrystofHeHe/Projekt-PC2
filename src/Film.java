public class Film {
    private String nazev;
    private String reziser;
    private int rokVydani;
    private double hodnoceniDivaku;
    private int vekoveOmezeni;

    // Konstruktor pro hraný film
    public Film(String nazev, int rokVydani, String reziser, double hodnoceniDivaku) {
        this.nazev = nazev;
        this.reziser = reziser;
        this.rokVydani = rokVydani;
        this.hodnoceniDivaku = hodnoceniDivaku;
    }

    // Konstruktor pro animovaný film
    public Film(String nazev, int rokVydani, String reziser, double hodnoceniDivaku, int vekoveOmezeni) {
        this.nazev = nazev;
        this.reziser = reziser;
        this.rokVydani = rokVydani;
        this.hodnoceniDivaku = hodnoceniDivaku;
        this.vekoveOmezeni = vekoveOmezeni;
    }

    public Film(String nazev, String reziser, int rok, int hodnoceniDivaku, String[] herci, boolean animovany, String[] animatori, int vekoveOmezeni) {
    }

    // Gettery pro atributy
    public String getNazev() {
        return nazev;
    }

    public String getReziser() {
        return reziser;
    }

    public int getRokVydani() {
        return rokVydani;
    }

    public double getHodnoceniDivaku() {
        return hodnoceniDivaku;
    }

    public int getVekoveOmezeni() {
        return vekoveOmezeni;
    }
}

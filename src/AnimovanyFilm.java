import java.util.List;

public class AnimovanyFilm extends Film {
    private static List<String> animatori;
    private int vekoveOmezeni;

    public AnimovanyFilm(String nazev, int rokVydani, String reziser, double hodnoceniDivaku, int vekoveOmezeni, List<String> animatori) {
        super(nazev, rokVydani, reziser, hodnoceniDivaku);
        this.vekoveOmezeni = vekoveOmezeni;
        this.animatori = animatori;
    }

    public static List<String> getAnimatori() {
        return animatori;
    }

    public void setAnimatori(List<String> animatori) {
        this.animatori = animatori;
    }

    public int getVekoveOmezeni() {
        return vekoveOmezeni;
    }

    public void setVekoveOmezeni(int vekoveOmezeni) {
        this.vekoveOmezeni = vekoveOmezeni;
    }
}
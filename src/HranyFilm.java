import java.util.List;

public class HranyFilm extends Film {
    private static List<String> herci;

    public HranyFilm(String nazev, int rokVydani, String reziser, double hodnoceniDivaku, List<String> herci) {
        super(nazev, rokVydani, reziser, hodnoceniDivaku);
        this.herci = herci;

    }

    public static List<String> getHerci() {
        return herci;
    }

    public void setHerci(List<String> herci) {
        this.herci = herci;
    }
}
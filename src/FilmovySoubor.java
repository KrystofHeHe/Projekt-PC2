import java.io.*;
import java.util.*;

public class FilmovySoubor {
    private static File soubor;
    private static final String SOUBOR = "filmy.txt";
    public FilmovySoubor(String nazevSouboru) {
        soubor = new File(nazevSouboru);
    }

    public static ArrayList<Film> nactiFilmy() {
        ArrayList<Film> filmy = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("filmy.txt"))) {
            String radek;
            while ((radek = reader.readLine()) != null) {
                String[] hodnoty = radek.split(";");
                if (hodnoty.length != 7) {
                    System.err.println("Chybný záznam: " + Arrays.toString(hodnoty));
                    continue;
                }
                String nazev = hodnoty[0];
                String reziser = hodnoty[1];
                int rok = Integer.parseInt(hodnoty[2]);
                int hodnoceniDivaku = Integer.parseInt(hodnoty[3]);
                String[] herci = hodnoty[4].isEmpty() ? new String[0] : hodnoty[4].split(",");
                String[] animatori = hodnoty[5].isEmpty() ? new String[0] : hodnoty[5].split(",");
                int vekoveOmezeni = Integer.parseInt(hodnoty[6]);
                boolean animovany = Arrays.asList(animatori).contains("Animace");
                Film film = new Film(nazev, reziser, rok, hodnoceniDivaku, herci, animovany, animatori, vekoveOmezeni);
                filmy.add(film);
                System.out.println(radek);
            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání souboru: " + e.getMessage());
        }

        return filmy;
    }


    public static void Pridatfilm() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadejte název filmu: ");
        String title = scanner.nextLine();

        System.out.println("Zadejte jméno režiséra: ");
        String director = scanner.nextLine();

        System.out.println("Zadejte rok vydání: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Je film animovaný? (ano/ne)");
        String isAnimated = scanner.nextLine();

        int rating;
        if (isAnimated.equalsIgnoreCase("ano")) {
            System.out.println("Zadejte hodnocení (1-10): ");
            rating = scanner.nextInt();
            scanner.nextLine();
        } else {
            System.out.println("Zadejte hodnocení (1-5): ");
            rating = scanner.nextInt();
            scanner.nextLine();
        }

        String[] actors;
        actors = new String[0];
        ArrayList<String> animators = new ArrayList<>();
        int ageLimit;

        if (isAnimated.equalsIgnoreCase("ano")) {
            System.out.println("Zadejte jména animátorů, kteří se podíleli na vývoji (oddělte čárkou): ");
            String[] animatorsArray = scanner.nextLine().split(",");
            for (String animator : animatorsArray) {
                animators.add(animator);
            }

            System.out.println("Zadejte věkové omezení: ");
            ageLimit = scanner.nextInt();
            scanner.nextLine();
        } else {
            ageLimit = 0;
            System.out.println("Zadejte seznam herců (oddělte čárkou): ");
            actors = scanner.nextLine().split(",");
        }

        String newMovie = title + ";" + director + ";" + year + ";" + rating + ";" + String.join(",", actors) + ";" + String.join(",", animators) + ";" + ageLimit;

        FileWriter fileWriter = new FileWriter("filmy.txt", true);
        fileWriter.write(newMovie + "\n");
        fileWriter.close();

        System.out.println("Film byl úspěšně přidán do souboru.");
    }

    public static void upravFilm() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Zadej název filmu, který chceš upravit:");
        String nazevFilmu = sc.nextLine();

        File soubor = new File("filmy.txt");
        FileReader fr = new FileReader(soubor);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String radek;

        while ((radek = br.readLine()) != null) {
            String[] film = radek.split(";");
            if (film[0].equalsIgnoreCase(nazevFilmu)) {
                System.out.println("Vybrali jste film: " + film[0]);
                System.out.println("Vyberte, co chcete upravit: ");
                System.out.println("1 - název filmu");
                System.out.println("2 - režisér");
                System.out.println("3 - rok vydání");
                System.out.println("4 - seznam herců");
                System.out.println("5 - seznam animátorů a doporučený věk diváků (pouze u animovaných filmů)");

                int volba = Integer.parseInt(sc.nextLine());

                switch (volba) {
                    case 1:
                        System.out.println("Zadej nový název filmu:");
                        film[0] = sc.nextLine();
                        break;
                    case 2:
                        System.out.println("Zadej nového režiséra:");
                        film[1] = sc.nextLine();
                        break;
                    case 3:
                        System.out.println("Zadej nový rok vydání:");
                        film[2] = sc.nextLine();
                        break;
                    case 4:
                        System.out.println("Zadej nový seznam herců (oddělte čárkou):");
                        film[4] = sc.nextLine();
                        break;
                    case 5:
                        if (Arrays.asList(film[5].split(",")).contains("Animace")) {
                            System.out.println("Zadej nový seznam animátorů (oddělte čárkou):");
                            film[5] = sc.nextLine();
                            System.out.println("Zadej nové věkové omezení:");
                            film[6] = sc.nextLine();
                        } else {
                            System.out.println("Tento film není animovaný.");
                        }
                        break;
                    default:
                        System.out.println("Zadaná volba není platná.");
                        break;
                }
            }
            sb.append(String.join(";", film) + "\n");
        }

        FileWriter fileWriter = new FileWriter(soubor);
        fileWriter.write(sb.toString());
        fileWriter.close();
        System.out.println("Film byl úspěšně upraven.");
    }


    public static void smazFilm() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Zadej název filmu ke smazání: ");
        String nazev = sc.nextLine();

        try {
            File docasnySoubor = new File("filmy_temp.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(docasnySoubor));

            BufferedReader br = new BufferedReader(new FileReader("filmy.txt"));
            String radek;

            boolean nalezeno = false;
            while ((radek = br.readLine()) != null) {
                String[] pole = radek.split(";");
                if (pole[0].equals(nazev)) {
                    nalezeno = true;
                } else {
                    bw.write(radek);
                    bw.newLine();
                }
            }

            bw.close();
            br.close();

            if (!nalezeno) {
                System.out.println("Film se zadaným názvem nebyl nalezen.");
            } else {
                File puvodniSoubor = new File("filmy.txt");
                File starySoubor = new File("filmy_old.txt");
                puvodniSoubor.renameTo(starySoubor);

                docasnySoubor.renameTo(puvodniSoubor);

                starySoubor.delete();

                System.out.println("Film byl úspěšně smazán.");
            }
        } catch (IOException e) {
            System.out.println("Nastala chyba při čtení nebo zápisu do souboru.");
        }
    }

    public static void pridejHodnoceni() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Zadej název filmu, ke kterému chceš přidat hodnocení: ");
        String nazev = sc.nextLine();

        try {
            File soubor = new File("filmy.txt");
            BufferedReader br = new BufferedReader(new FileReader(soubor));
            String radek;
            ArrayList<String> radky = new ArrayList<>();

            while ((radek = br.readLine()) != null) {
                String[] polozky = radek.split(";");

                if (polozky[0].equalsIgnoreCase(nazev)) {
                    System.out.println("Hodnocení filmu " + nazev + ":");
                    if (null == polozky[4]) {
                        System.out.print("Zadej bodové hodnocení (1-10): ");
                        int hodnoceni = sc.nextInt();
                        while (hodnoceni < 1 || hodnoceni > 10) {
                            System.out.print("Hodnocení musí být v rozmezí 1-10. Zadej znovu: ");
                            hodnoceni = sc.nextInt();
                        }
                        polozky[6] = String.valueOf(hodnoceni);
                    } else {
                        System.out.print("Zadej bodové hodnocení (1-5): ");
                        int hodnoceni = sc.nextInt();
                        while (hodnoceni < 1 || hodnoceni > 5) {
                            System.out.print("Hodnocení musí být v rozmezí 1-5. Zadej znovu: ");
                            hodnoceni = sc.nextInt();
                        }
                        polozky[6] = String.valueOf(hodnoceni);
                    }
                    System.out.print("Chcete přidat slovní hodnocení? (ano/ne) ");
                    String volba = sc.next().toLowerCase();
                    if (volba.equals("ano")) {
                        System.out.print("Zadej slovní hodnocení: ");
                        sc.nextLine();
                        String slovniHodnoceni = sc.nextLine();
                        polozky[7] = slovniHodnoceni;
                    }
                }

                radky.add(String.join(";", polozky));
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(soubor));
            for (String r : radky) {
                bw.write(r);
                bw.newLine();
            }
            bw.close();

            System.out.println("Hodnocení bylo přidáno.");
        } catch (IOException e) {System.out.println("Chyba při čtení ze souboru.");
        }
    }

    public static void najdiFilm() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Zadej jméno herce/animátora: ");
        String jmeno = scanner.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("filmy.txt"));
            String line = reader.readLine();
            boolean nalezeno = false;

            System.out.println("Seznam filmů s hercem/animátorem \"" + jmeno + "\":\n");
            while (line != null) {
                String[] info = line.split(";");
                for (int i = 3; i < info.length - 2; i++) {
                    if (info[i].equalsIgnoreCase(jmeno)) {
                        System.out.println("Název: " + info[0]);
                        System.out.println("Režisér: " + info[1]);
                        System.out.println("Rok vydání: " + info[2]);
                        System.out.println("Seznam herců/animátorů: " + info[3] + ", " + info[4] + ", " + info[5]);
                        System.out.println("Hodnocení diváků: " + info[6]);
                        System.out.println();
                        nalezeno = true;
                        break;
                    }
                }
                line = reader.readLine();
            }
            reader.close();

            if (!nalezeno) {
                System.out.println("Herci/animátor \"" + jmeno + "\" nebyl nalezen v žádném filmu.");
            }

        } catch (IOException e) {
            System.out.println("Chyba při čtení ze souboru: " + e.getMessage());
        }
    }

    public static void vypisHercu() {
        HashMap<String, ArrayList<String>> herci = new HashMap<>();
        try {
            File soubor = new File("filmy.txt");
            Scanner sc = new Scanner(soubor);

            while (sc.hasNextLine()) {
                String radek = sc.nextLine();
                String[] pole = radek.split(";");

                String nazev = pole[0];
                String[] herciSeznam = pole[4].split(",");

                for (String h : herciSeznam) {
                    if (!herci.containsKey(h.trim())) {
                        herci.put(h.trim(), new ArrayList<String>());
                    }
                    herci.get(h.trim()).add(nazev);
                }
            }
            sc.close();

            System.out.println("Seznam herců, kteří se podíleli na více než jednom filmu:");
            for (Map.Entry<String, ArrayList<String>> entry : herci.entrySet()) {
                if (entry.getValue().size() > 1) {
                    System.out.println("Herci: " + entry.getKey());
                    System.out.println("Filmy: " + String.join(", ", entry.getValue()));
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Soubor nebyl nalezen.");
        }
    }

    public static void vypisAnimatoru() {
        HashMap<String, ArrayList<String>> animatori = new HashMap<>();
        try {
            File soubor = new File("filmy.txt");
            Scanner sc = new Scanner(soubor);

            while (sc.hasNextLine()) {
                String radek = sc.nextLine();
                String[] pole = radek.split(";");

                String nazev = pole[0];
                String typ = pole[1];
                String[] osobySeznam = pole[5].split(",");

                if (typ.equals("animovaný")) {
                    for (String o : osobySeznam) {
                        if (!animatori.containsKey(o.trim())) {
                            animatori.put(o.trim(), new ArrayList<String>());
                        }
                        animatori.get(o.trim()).add(nazev);
                    }
                }
            }
            sc.close();

            System.out.println("Seznam animátorů, kteří se podíleli na více než jednom animovaném filmu:");
            for (Map.Entry<String, ArrayList<String>> entry : animatori.entrySet()) {
                if (entry.getValue().size() > 1) {
                    System.out.println("Animátoři: " + entry.getKey());
                    System.out.println("Filmy: " + String.join(", ", entry.getValue()));
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Soubor nebyl nalezen.");
        }
    }

    public static void vypisFilmyPodleJmena() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Zadej jméno herce nebo animátora: ");
        String jmeno = scanner.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("filmy.txt"))) {
            String radek;
            while ((radek = reader.readLine()) != null) {
                String[] hodnoty = radek.split(";");
                if (hodnoty.length != 7) {
                    System.err.println("Chybný záznam: " + Arrays.toString(hodnoty));
                    continue;
                }
                String[] herci = hodnoty[4].isEmpty() ? new String[0] : hodnoty[4].split(",");
                String[] animatori = hodnoty[5].isEmpty() ? new String[0] : hodnoty[5].split(",");
                if (Arrays.asList(herci).contains(jmeno) || Arrays.asList(animatori).contains(jmeno)) {
                    System.out.println(hodnoty[0]);
                }
            }
        } catch (IOException e) {
            System.err.println("Chyba při načítání souboru: " + e.getMessage());
        }
    }



}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        boolean exit = false;
     //   Databaze.zapisDoSouboru();
        while (!exit) {
            System.out.println("\nFilmová databáze - Výběr možnosti:\n");
            System.out.println("1 - Přidání nového filmu");
            System.out.println("2 - Úprava filmu");
            System.out.println("3 - Smazání filmu");
            System.out.println("4 - Přidání hodnocení filmu");
            System.out.println("5 - Výpis filmů");
            System.out.println("6 - Vyhledání filmu");
            System.out.println("7 - Výpis herců nebo animátorů, kteří se podíleli na více než jednom filmu");
            System.out.println("8 - Výpis všech filmů, které obsahují konkrétního herce nebo animátora");
            System.out.println("0 - Konec programu");

            int volba = scanner.nextInt();
            scanner.nextLine(); // Pro zamezení problémům s čtením dalšího vstupu

            switch (volba) {
                case 0:
                    System.out.println("Ukončení programu.");
               //     Databaze.aktualizujZaznamy();
                    exit = true;
                    break;
                case 1:
                    FilmovySoubor.Pridatfilm();
                    break;
                case 2:
                    FilmovySoubor.upravFilm();
                    break;
                case 3:
                    FilmovySoubor.smazFilm();
                    break;
                case 4:
                    FilmovySoubor.pridejHodnoceni();
                    break;
                case 5:
                     FilmovySoubor.nactiFilmy();
                    break;
                case 6:
                    FilmovySoubor.najdiFilm();
                    break;
                case 7:
                    System.out.println("Chcete vypsat herce nebo animátory?");
                    String volba1 = scanner.nextLine();
                    if (volba1.equals("herce")) {
                        FilmovySoubor.vypisHercu();
                    } else if (volba1.equals("animátory")) {
                        FilmovySoubor.vypisAnimatoru();
                    } else {
                        System.out.println("Neplatná volba.");
                    }
                    break;
                case 8:
                FilmovySoubor.vypisFilmyPodleJmena();
                    break;

                default:
                    System.out.println("Neplatná volba.");
                    break;
            }
        }
    }

    }
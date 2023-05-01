import javax.swing.*;
import java.io.*;
import java.sql.*;

public class Databaze {
    private static final String url = "jdbc:sqlite:C:/Users/kikot/IdeaProjects/ProgProjekt/Databaze/db.db";
    private static Connection conn = null;

    public static void pripojit() {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Připojení se povedlo.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void odpojit() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Odpojení proběhlo úspěšně.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void zapisDoSouboru() {
        pripojit();
        try {
            File soubor = new File("filmy.txt");
            BufferedReader reader = new BufferedReader(new FileReader("filmy.txt"));
            String line = reader.readLine();

            while (line != null) {
                String[] data = line.split(";");
                String nazev = data[0];
                String reziser = data[1];
                int rokVydani = Integer.parseInt(data[2]);
                String herci = data[3];
                String animatori = data[4];
                int hodnoceniDivaku = Integer.parseInt(data[5]);

                PreparedStatement statement = conn.prepareStatement("INSERT INTO filmy (nazev, reziser, rok_vydani, herci, animatori, hodnoceni_divaku) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, nazev);
                statement.setString(2, reziser);
                statement.setInt(3, rokVydani);
                statement.setString(4, herci);
                statement.setString(5, animatori);
                statement.setInt(6, hodnoceniDivaku);
                statement.executeUpdate();

                line = reader.readLine();
            }

            reader.close();
            System.out.println("Data byla vložena do databáze.");

        } catch (Exception e) {
            System.out.println("Chyba při vkládání dat do databáze: " + e.getMessage());
        }
        odpojit();
    }

    public static void aktualizujZaznamy() {
        pripojit();
        File soubor = new File("filmy.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(soubor))) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM filmy");

            while (resultSet.next()) {
                String nazev = resultSet.getString("nazev");
                String reziser = resultSet.getString("reziser");
                int rokVydani = resultSet.getInt("rok_vydani");
                String herci = resultSet.getString("herci");
                String animatori = resultSet.getString("animatori");
                int hodnoceniDivaku = resultSet.getInt("hodnoceni_divaku");

                String radek = nazev + ";" + reziser + ";" + rokVydani + ";" + herci + ";" + animatori + ";" + hodnoceniDivaku;
                writer.write(radek);
                writer.newLine();
            }

            System.out.println("Data byla zapsána do souboru " + soubor);

        } catch (IOException | SQLException e) {
            System.out.println("Chyba při zápisu do souboru: " + e.getMessage());
        }
       soubor.delete();
        odpojit();
    }
}







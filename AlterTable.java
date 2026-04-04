import database.Database;
import java.sql.Connection;
import java.sql.Statement;

public class AlterTable {
    public static void main(String[] args) {
        try (Connection conn = Database.getConnection(); 
             Statement stmt = conn.createStatement()) {
             try {
                stmt.executeUpdate("ALTER TABLE OPERATIONS CHANGE compte_id compte_source INT");
             } catch (Exception ignored) {}
             try {
                stmt.executeUpdate("ALTER TABLE OPERATIONS ADD COLUMN compte_destination INT AFTER compte_source");
             } catch (Exception ignored) {}
             System.out.println("Table OPERATIONS ALTERED.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

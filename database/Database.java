package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/mobilemoney_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            throw new RuntimeException("\n=== ERREUR CRITIQUE ===\n" +
                "Driver JDBC introuvable. Vous devez exécuter le programme en incluant " +
                "le dossier lib dans le classpath.\n" +
                "Commande recommandée : java -cp \".;lib/*\" ui.App\n" +
                "=======================\n", e);
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            throw new RuntimeException("Échec de connexion à la base de données. Assurez-vous que MySQL/XAMPP est démarré.", e);
        }
    }

    public static void initDatabase() {
        String sqlMarchand = "CREATE TABLE IF NOT EXISTS MARCHAND (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nom VARCHAR(100) NOT NULL," +
                "compte_recepteur VARCHAR(50) NOT NULL," +
                "type_commerce VARCHAR(100)" +
                ")";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlMarchand);
        } catch (SQLException e) {
            System.err.println("Erreur d'initialisation de la base : " + e.getMessage());
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}




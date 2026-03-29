package dao;

import database.Database;
import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    public void addClient(Client client) {
        String sql = "INSERT INTO CLIENT (nom, prenom, telephone, adresse) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.setString(3, client.getTelephone());
            pstmt.setString(4, client.getAdresse());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding client: " + e.getMessage());
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM CLIENT";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone"),
                    rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching clients: " + e.getMessage());
        }
        return clients;
    }

    public Client getClientByTelephone(String telephone) {
        String sql = "SELECT * FROM CLIENT WHERE telephone = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, telephone);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching client by telephone: " + e.getMessage());
        }
        return null;
    }
}

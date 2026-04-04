package dao;

import database.Database;
import model.Compte;
import java.sql.*;

public class CompteDAO {
    public boolean addCompte(Compte compte) {
        String sql = "INSERT INTO COMPTE (numero_compte, solde, client_id) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, compte.getNumeroCompte());
            pstmt.setDouble(2, compte.getSolde());
            pstmt.setInt(3, compte.getClientId());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    compte.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding account: " + e.getMessage());
            return false;
        }
    }

    public Compte getCompteByNumero(String numero) {
        String sql = "SELECT * FROM COMPTE WHERE numero_compte = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, numero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Compte(
                        rs.getInt("id"),
                        rs.getString("numero_compte"),
                        rs.getDouble("solde"),
                        rs.getInt("client_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching account: " + e.getMessage());
        }
        return null;
    }

    public void updateSolde(int id, double nouveauSolde) {
        String sql = "UPDATE COMPTE SET solde = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, nouveauSolde);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
        }
    }
}

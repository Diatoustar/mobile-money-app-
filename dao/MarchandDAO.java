package dao;

import database.Database;
import model.Marchand;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Data Access Object (DAO) pour la gestion des marchands.Permet l'enregistrement et la récupération des informations des marchands.

public class MarchandDAO {
    
    public boolean addMarchand(Marchand marchand) {
        String sql = "INSERT INTO MARCHAND (nom, compte_recepteur, type_commerce) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, marchand.getNom());
            pstmt.setString(2, marchand.getCompteRecepteur());
            pstmt.setString(3, marchand.getTypeCommerce());
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    marchand.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur à l'ajout du marchand: " + e.getMessage());
            return false;
        }
    }

    public List<Marchand> getAllMarchands() {
        List<Marchand> marchands = new ArrayList<>();
        String sql = "SELECT * FROM MARCHAND";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                marchands.add(new Marchand(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("compte_recepteur"),
                    rs.getString("type_commerce")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de récupération des marchands: " + e.getMessage());
        }
        return marchands;
    }

    public Marchand getMarchandByNom(String nom) {
        String sql = "SELECT * FROM MARCHAND WHERE nom = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Marchand(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("compte_recepteur"),
                        rs.getString("type_commerce")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur de recherche du marchand: " + e.getMessage());
        }
        return null;
    }
}

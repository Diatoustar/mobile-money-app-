package dao;

import database.Database;
import model.Operation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO {
    public boolean addOperation(Operation operation) {
        String sql = "INSERT INTO OPERATIONS (type_operation, montant, compte_source, compte_destination, marchand) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, operation.getTypeOperation());
            pstmt.setDouble(2, operation.getMontant());
            
            if (operation.getCompteSource() != null) pstmt.setInt(3, operation.getCompteSource());
            else pstmt.setNull(3, Types.INTEGER);
            
            if (operation.getCompteDestination() != null) pstmt.setInt(4, operation.getCompteDestination());
            else pstmt.setNull(4, Types.INTEGER);
            
            pstmt.setString(5, operation.getMarchand());
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    operation.setId(generatedKeys.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding operation: " + e.getMessage());
            return false;
        }
    }

    public List<Operation> getHistoryByCompte(int compteId) {
        List<Operation> operations = new ArrayList<>();
        String sql = "SELECT * FROM OPERATIONS WHERE compte_source = ? OR compte_destination = ? ORDER BY date_operation DESC";
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, compteId);
            pstmt.setInt(2, compteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    operations.add(new Operation(
                        rs.getInt("id"),
                        rs.getString("type_operation"),
                        rs.getDouble("montant"),
                        rs.getTimestamp("date_operation"),
                        (Integer) rs.getObject("compte_source"),
                        (Integer) rs.getObject("compte_destination"),
                        rs.getString("marchand")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching history: " + e.getMessage());
        }
        return operations;
    }

    public List<Operation> getAllOperations() {
        List<Operation> operations = new ArrayList<>();
        String sql = "SELECT * FROM OPERATIONS ORDER BY date_operation DESC";
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                operations.add(new Operation(
                    rs.getInt("id"),
                    rs.getString("type_operation"),
                    rs.getDouble("montant"),
                    rs.getTimestamp("date_operation"),
                    (Integer) rs.getObject("compte_source"),
                    (Integer) rs.getObject("compte_destination"),
                    rs.getString("marchand")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all operations: " + e.getMessage());
        }
        return operations;
    }
}

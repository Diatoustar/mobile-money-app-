package model;

import java.sql.Timestamp;

// Modèle représentant une opération financière (transaction).
Détaille le type, le montant, la date, les comptes impliqués et optionnellement le marchand.
public class Operation {
    private int id;
    private String typeOperation;
    private double montant;
    private Timestamp dateOperation;
    private Integer compteSource; 
    private Integer compteDestination;
    private String marchand;

    public Operation() {}

    public Operation(int id, String typeOperation, double montant, Timestamp dateOperation, Integer compteSource, Integer compteDestination, String marchand) {
        this.id = id;
        this.typeOperation = typeOperation;
        this.montant = montant;
        this.dateOperation = dateOperation;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
        this.marchand = marchand;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTypeOperation() { return typeOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public Timestamp getDateOperation() { return dateOperation; }
    public void setDateOperation(Timestamp dateOperation) { this.dateOperation = dateOperation; }

    public Integer getCompteSource() { return compteSource; }
    public void setCompteSource(Integer compteSource) { this.compteSource = compteSource; }

    public Integer getCompteDestination() { return compteDestination; }
    public void setCompteDestination(Integer compteDestination) { this.compteDestination = compteDestination; }

    public String getMarchand() { return marchand; }
    public void setMarchand(String marchand) { this.marchand = marchand; }

    @Override
    public String toString() {
        String details = "";
        if ("TRANSFERT".equals(typeOperation)) {
            details = "(Cpte " + compteSource + " -> Cpte " + compteDestination + ")";
        } else if ("PAIEMENT".equals(typeOperation)) {
            details = "(Chez " + marchand + ")";
        } else if ("DEPOT".equals(typeOperation)) {
            details = "(Cpte " + compteDestination + ")";
        } else if ("RETRAIT".equals(typeOperation)) {
            details = "(Cpte " + compteSource + ")";
        }
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dateStr = (dateOperation != null) ? sdf.format(dateOperation) : "Inconnue";
        
        return String.format("[%s] %-10s | %8.2f FCFA %s", dateStr, typeOperation, montant, details);
    }
}

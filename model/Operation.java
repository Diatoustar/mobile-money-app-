package model;

import java.sql.Timestamp;

public class Operation {
    private int id;
    private String typeOperation;
    private double montant;
    private Timestamp dateOperation;
    private Integer compteSource; // Using Integer to allow null
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
        return "Operation [id=" + id + ", type=" + typeOperation + ", montant=" + montant + ", date=" + dateOperation + "]";
    }
}

package model;

public class Marchand {
    private int id;
    private String nom;
    private String compteRecepteur;
    private String typeCommerce;

    public Marchand() {}

    public Marchand(int id, String nom, String compteRecepteur, String typeCommerce) {
        this.id = id;
        this.nom = nom;
        this.compteRecepteur = compteRecepteur;
        this.typeCommerce = typeCommerce;
    }

    public Marchand(String nom, String compteRecepteur, String typeCommerce) {
        this.nom = nom;
        this.compteRecepteur = compteRecepteur;
        this.typeCommerce = typeCommerce;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getCompteRecepteur() { return compteRecepteur; }
    public void setCompteRecepteur(String compteRecepteur) { this.compteRecepteur = compteRecepteur; }

    public String getTypeCommerce() { return typeCommerce; }
    public void setTypeCommerce(String typeCommerce) { this.typeCommerce = typeCommerce; }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - Compte : %s", id, nom, typeCommerce, compteRecepteur);
    }
}

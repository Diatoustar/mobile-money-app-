package service;

import dao.ClientDAO;
import dao.CompteDAO;
import dao.OperationDAO;
import dao.MarchandDAO;
import model.Client;
import model.Compte;
import model.Marchand;
import model.Operation;
import java.sql.Timestamp;
import java.util.List;

// Gère la logique des opérations bancaires (dépôt, retrait, transfert, paiement marchand).
public class MobileMoneyService {
    private ClientDAO clientDAO = new ClientDAO();
    private CompteDAO compteDAO = new CompteDAO();
    private OperationDAO operationDAO = new OperationDAO();
    private MarchandDAO marchandDAO = new MarchandDAO();

    public boolean createClient(String nom, String prenom, String telephone, String adresse) {
        Client client = new Client(nom, prenom, telephone, adresse);
        return clientDAO.addClient(client);
    }

    public List<Client> getAllClients() {
        return clientDAO.getAllClients();
    }

    public boolean createAccount(String numero, int clientId) {
        Compte compte = new Compte(numero, 0.0, clientId);
        return compteDAO.addCompte(compte);
    }

    public boolean deposit(String numeroCompte, double montant) {
        Compte compte = compteDAO.getCompteByNumero(numeroCompte);
        if (compte != null) {
            compte.setSolde(compte.getSolde() + montant);
            compteDAO.updateSolde(compte.getId(), compte.getSolde());
            
            Operation op = new Operation();
            op.setTypeOperation("DEPOT");
            op.setMontant(montant);
            op.setCompteDestination(compte.getId());
            return operationDAO.addOperation(op);
        }
        return false;
    }

    public boolean withdraw(String numeroCompte, double montant) {
        Compte compte = compteDAO.getCompteByNumero(numeroCompte);
        if (compte != null && compte.getSolde() >= montant) {
            compte.setSolde(compte.getSolde() - montant);
            compteDAO.updateSolde(compte.getId(), compte.getSolde());
            
            Operation op = new Operation();
            op.setTypeOperation("RETRAIT");
            op.setMontant(montant);
            op.setCompteSource(compte.getId());
            return operationDAO.addOperation(op);
        }
        return false;
    }

    public boolean transfer(String sourceNumero, String destNumero, double montant) {
        Compte source = compteDAO.getCompteByNumero(sourceNumero);
        Compte dest = compteDAO.getCompteByNumero(destNumero);
        
        if (source == null) {
            System.err.println("Compte source inexistant.");
            return false;
        }
        if (dest == null) {
            System.err.println("Compte destinataire inexistant.");
            return false;
        }
        if (source.getSolde() < montant) {
            System.err.println("Solde insuffisant.");
            return false;
        }
        
        try (java.sql.Connection conn = database.Database.getConnection()) {
            conn.setAutoCommit(false);
            try {
                source.setSolde(source.getSolde() - montant);
                dest.setSolde(dest.getSolde() + montant);
                
                compteDAO.updateSolde(conn, source.getId(), source.getSolde());
                compteDAO.updateSolde(conn, dest.getId(), dest.getSolde());
                
                Operation op = new Operation();
                op.setTypeOperation("TRANSFERT");
                op.setMontant(montant);
                op.setCompteSource(source.getId());
                op.setCompteDestination(dest.getId());
                operationDAO.addOperation(conn, op);
                
                conn.commit();
                return true;
            } catch (java.sql.SQLException e) {
                conn.rollback();
                System.err.println("Erreur de transaction : " + e.getMessage());
                return false;
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            return false;
        }
    }

    public boolean payMerchant(String sourceNumero, String merchantName, double montant) {
        Compte source = compteDAO.getCompteByNumero(sourceNumero);
        Marchand marchand = marchandDAO.getMarchandByNom(merchantName);
        
        if (source == null) {
            System.err.println("Compte source inexistant.");
            return false;
        }
        if (marchand == null) {
            System.err.println("Marchand introuvable.");
            return false;
        }
        if (source.getSolde() < montant) {
            System.err.println("Solde insuffisant.");
            return false;
        }
        
        Compte dest = compteDAO.getCompteByNumero(marchand.getCompteRecepteur());
        if (dest == null) {
            System.err.println("Le compte récepteur du marchand est invalide.");
            return false;
        }

        try (java.sql.Connection conn = database.Database.getConnection()) {
            conn.setAutoCommit(false);
            try {
                source.setSolde(source.getSolde() - montant);
                dest.setSolde(dest.getSolde() + montant);
                
                compteDAO.updateSolde(conn, source.getId(), source.getSolde());
                compteDAO.updateSolde(conn, dest.getId(), dest.getSolde());
                
                Operation op = new Operation();
                op.setTypeOperation("PAIEMENT");
                op.setMontant(montant);
                op.setCompteSource(source.getId());
                op.setCompteDestination(dest.getId());
                op.setMarchand(merchantName);
                operationDAO.addOperation(conn, op);
                
                conn.commit();
                return true;
            } catch (java.sql.SQLException e) {
                conn.rollback();
                System.err.println("Erreur de transaction paiement : " + e.getMessage());
                return false;
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            return false;
        }
    }

    public boolean createMarchand(String nom, String compteRecepteur, String typeCommerce) {
        Marchand m = new Marchand(nom, compteRecepteur, typeCommerce);
        return marchandDAO.addMarchand(m);
    }
    
    public List<Marchand> getAllMarchands() {
        return marchandDAO.getAllMarchands();
    }
    
    public List<Operation> getOperationsByDate(Timestamp debut, Timestamp fin) {
        return operationDAO.getOperationsByDateRange(debut, fin);
    }
    
    public void showStatistics() {
        operationDAO.printStatistics();
    }

    public List<Client> searchClients(String keyword) {
        return clientDAO.searchClients(keyword);
    }

    public Double getBalance(String numeroCompte) {
        Compte compte = compteDAO.getCompteByNumero(numeroCompte);
        return (compte != null) ? compte.getSolde() : null;
    }

    public List<Operation> getGlobalHistory() {
        return operationDAO.getAllOperations();
    }

    public List<Operation> getAccountHistory(String numeroCompte) {
        Compte compte = compteDAO.getCompteByNumero(numeroCompte);
        if (compte != null) {
            return operationDAO.getHistoryByCompte(compte.getId());
        }
        return null;
    }
}

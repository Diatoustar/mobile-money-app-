package service;

import dao.ClientDAO;
import dao.CompteDAO;
import dao.OperationDAO;
import model.Client;
import model.Compte;
import model.Operation;
import java.util.List;

public class MobileMoneyService {
    private ClientDAO clientDAO = new ClientDAO();
    private CompteDAO compteDAO = new CompteDAO();
    private OperationDAO operationDAO = new OperationDAO();

    public void createClient(String nom, String prenom, String telephone, String adresse) {
        Client client = new Client(nom, prenom, telephone, adresse);
        clientDAO.addClient(client);
    }

    public List<Client> getAllClients() {
        return clientDAO.getAllClients();
    }

    public void createAccount(String numero, int clientId) {
        Compte compte = new Compte(numero, 0.0, clientId);
        compteDAO.addCompte(compte);
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
            operationDAO.addOperation(op);
            return true;
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
            operationDAO.addOperation(op);
            return true;
        }
        return false;
    }

    public boolean transfer(String sourceNumero, String destNumero, double montant) {
        Compte source = compteDAO.getCompteByNumero(sourceNumero);
        Compte dest = compteDAO.getCompteByNumero(destNumero);
        
        if (source != null && dest != null && source.getSolde() >= montant) {
            source.setSolde(source.getSolde() - montant);
            dest.setSolde(dest.getSolde() + montant);
            
            compteDAO.updateSolde(source.getId(), source.getSolde());
            compteDAO.updateSolde(dest.getId(), dest.getSolde());
            
            Operation op = new Operation();
            op.setTypeOperation("TRANSFERT");
            op.setMontant(montant);
            op.setCompteSource(source.getId());
            op.setCompteDestination(dest.getId());
            operationDAO.addOperation(op);
            return true;
        }
        return false;
    }

    public boolean payMerchant(String sourceNumero, String merchantName, double montant) {
        Compte source = compteDAO.getCompteByNumero(sourceNumero);
        if (source != null && source.getSolde() >= montant) {
            source.setSolde(source.getSolde() - montant);
            compteDAO.updateSolde(source.getId(), source.getSolde());
            
            Operation op = new Operation();
            op.setTypeOperation("PAIEMENT");
            op.setMontant(montant);
            op.setCompteSource(source.getId());
            op.setMarchand(merchantName);
            operationDAO.addOperation(op);
            return true;
        }
        return false;
    }
}

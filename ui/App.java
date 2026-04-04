package ui;

import service.MobileMoneyService;
import model.Client;
import model.Operation;
import java.util.Scanner;
import java.util.List;

public class App {
    private static MobileMoneyService service = new MobileMoneyService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MOBILE MONEY SYSTEM ===");
            System.out.println("1. Gérer les Clients");
            System.out.println("2. Gérer les Comptes");
            System.out.println("3. Opérations Financières");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            
            int choice = lireEntier();

            switch (choice) {
                case 1: clientMenu(); break;
                case 2: accountMenu(); break;
                case 3: operationMenu(); break;
                case 0: System.exit(0);
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void clientMenu() {
        System.out.println("\n--- GESTION DES CLIENTS ---");
        System.out.println("1. Ajouter un Client");
        System.out.println("2. Lister les Clients");
        System.out.println("3. Rechercher des Clients");
        System.out.println("0. Retour");
        System.out.print("Choix: ");
        int choice = lireEntier();

        if (choice == 0) return;

        if (choice == 1) {
            System.out.print("Nom: "); String nom = scanner.nextLine();
            System.out.print("Prénom: "); String prenom = scanner.nextLine();
            System.out.print("Téléphone: "); String tel = scanner.nextLine();
            System.out.print("Adresse: "); String adr = scanner.nextLine();
            if (service.createClient(nom, prenom, tel, adr)) {
                System.out.println("Client ajouté avec succès !");
            } else {
                System.out.println("Erreur: Impossible d'ajouter le client. Veuillez vérifier les informations.");
            }
        } else if (choice == 2) {
            List<Client> clients = service.getAllClients();
            for (Client c : clients) System.out.println(c);
        } else if (choice == 3) {
            System.out.print("Mot-clé (nom, prénom ou tel): ");
            String keyword = scanner.nextLine();
            List<Client> results = service.searchClients(keyword);
            for (Client c : results) System.out.println(c);
        }
    }

    private static void accountMenu() {
        System.out.println("\n--- GESTION DES COMPTES ---");
        System.out.println("1. Créer un Compte");
        System.out.println("2. Consulter le Solde");
        System.out.println("0. Retour");
        System.out.print("Choix: ");
        int choice = lireEntier();

        if (choice == 0) return;

        if (choice == 1) {
            System.out.print("Numéro de compte: "); String num = scanner.nextLine();
            System.out.print("ID Client: "); int clientId = lireEntier();
            if (service.createAccount(num, clientId)) {
                System.out.println("Compte créé avec succès !");
            } else {
                System.out.println("Erreur: Impossible de créer le compte. L'ID Client est introuvable ou le numéro de compte existe déjà.");
            }
        } else if (choice == 2) {
            System.out.print("Numéro de compte: "); String num = scanner.nextLine();
            Double solde = service.getBalance(num);
            if (solde != null) System.out.println("Solde actuel: " + solde + " FCFA");
            else System.out.println("Compte inexistant.");
        }
    }

    private static void operationMenu() {
        System.out.println("\n--- OPERATIONS FINANCIERES ---");
        System.out.println("1. Dépôt");
        System.out.println("2. Retrait");
        System.out.println("3. Transfert");
        System.out.println("4. Paiement Marchand");
        System.out.println("5. Historique (Global)");
        System.out.println("6. Historique (Par Compte)");
        System.out.println("0. Retour");
        System.out.print("Choix: ");
        int choice = lireEntier();

        if (choice == 0) return;

        if (choice >= 1 && choice <= 4) {
            System.out.print("Numéro de compte: "); String num = scanner.nextLine();
            System.out.print("Montant: "); double montant = lireDouble();

            boolean success = false;
            switch (choice) {
                case 1: success = service.deposit(num, montant); break;
                case 2: success = service.withdraw(num, montant); break;
                case 3:
                    System.out.print("Numéro destinataire: "); String dest = scanner.nextLine();
                    success = service.transfer(num, dest, montant);
                    break;
                case 4:
                    System.out.print("Nom du marchand: "); String merchant = scanner.nextLine();
                    success = service.payMerchant(num, merchant, montant);
                    break;
            }

            if (success) System.out.println("Opération réussie !");
            else System.out.println("Opération échouée.");
        } else if (choice == 5) {
            List<Operation> history = service.getGlobalHistory();
            for (Operation o : history) System.out.println(o);
        } else if (choice == 6) {
            System.out.print("Numéro de compte: "); String num = scanner.nextLine();
            List<Operation> history = service.getAccountHistory(num);
            if (history != null) {
                for (Operation o : history) System.out.println(o);
            } else {
                System.out.println("Compte inexistant.");
            }
        }
    }

    private static int lireEntier() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Veuillez saisir un nombre entier : ");
            }
        }
    }

    private static double lireDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide. Veuillez saisir un montant valide (ex: 1000.50) : ");
            }
        }
    }
}

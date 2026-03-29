package ui;

import service.MobileMoneyService;
import model.Client;
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
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Nom: "); String nom = scanner.nextLine();
            System.out.print("Prénom: "); String prenom = scanner.nextLine();
            System.out.print("Téléphone: "); String tel = scanner.nextLine();
            System.out.print("Adresse: "); String adr = scanner.nextLine();
            service.createClient(nom, prenom, tel, adr);
            System.out.println("Client ajouté !");
        } else if (choice == 2) {
            List<Client> clients = service.getAllClients();
            for (Client c : clients) System.out.println(c);
        }
    }

    private static void accountMenu() {
        System.out.println("\n--- GESTION DES COMPTES ---");
        System.out.println("1. Créer un Compte");
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Numéro de compte: "); String num = scanner.nextLine();
            System.out.print("ID Client: "); int clientId = scanner.nextInt();
            service.createAccount(num, clientId);
            System.out.println("Compte créé !");
        }
    }

    private static void operationMenu() {
        System.out.println("\n--- OPERATIONS FINANCIERES ---");
        System.out.println("1. Dépôt");
        System.out.println("2. Retrait");
        System.out.println("3. Transfert");
        System.out.println("4. Paiement Marchand");
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Numéro de compte: "); String num = scanner.nextLine();
        System.out.print("Montant: "); double montant = scanner.nextDouble();
        scanner.nextLine();

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
        else System.out.println("Opération échouée (solde insuffisant ou compte inexistant).");
    }
}

package ui;

import service.MobileMoneyService;
import model.Client;
import model.Marchand;
import model.Operation;
import database.Database;

import java.util.Scanner;
import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App {
    private static MobileMoneyService service = new MobileMoneyService();
    private static Scanner scanner = new Scanner(System.in);

    // ANSI Colors for better UI
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        // Initialize Database tables (e.g. MARCHAND)
        Database.initDatabase();

        while (true) {
            clearScreen();
            System.out.println(CYAN + BOLD + "╔════════════════════════════════════════╗" + RESET);
            System.out.println(CYAN + BOLD + "║        " + YELLOW + "MOBILE MONEY SYSTEM PRO" + CYAN + "         ║" + RESET);
            System.out.println(CYAN + BOLD + "╠════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN + BOLD + "║ " + YELLOW + "1. " + RESET + "Gérer les Clients                    " + CYAN + BOLD + "║" + RESET);
            System.out.println(CYAN + BOLD + "║ " + YELLOW + "2. " + RESET + "Gérer les Comptes                    " + CYAN + BOLD + "║" + RESET);
            System.out.println(CYAN + BOLD + "║ " + YELLOW + "3. " + RESET + "Gérer les Marchands                  " + CYAN + BOLD + "║" + RESET);
            System.out.println(CYAN + BOLD + "║ " + YELLOW + "4. " + RESET + "Opérations Financières               " + CYAN + BOLD + "║" + RESET);
            System.out.println(CYAN + BOLD + "║ " + YELLOW + "5. " + RESET + "Statistiques & Recherche Avancée     " + CYAN + BOLD + "║" + RESET);
            System.out.println(CYAN + BOLD + "╠════════════════════════════════════════╣" + RESET);
            System.out.println(CYAN + BOLD + "║ " + RED + "0. " + RESET + "Quitter                              " + CYAN + BOLD + "║" + RESET);
            System.out.println(CYAN + BOLD + "╚════════════════════════════════════════╝" + RESET);
            System.out.print(BOLD + "\n▶ Votre choix : " + RESET);
            
            int choice = lireEntier();

            switch (choice) {
                case 1: clientMenu(); break;
                case 2: accountMenu(); break;
                case 3: marchandMenu(); break;
                case 4: operationMenu(); break;
                case 5: statsMenu(); break;
                case 0: 
                    System.out.println(GREEN + "Merci d'avoir utilisé Mobile Money System. Au revoir!" + RESET);
                    System.exit(0);
                default: 
                    System.out.println(RED + "Choix invalide." + RESET);
                    pause();
            }
        }
    }

    private static void clientMenu() {
        clearScreen();
        System.out.println(CYAN + BOLD + "\n┌────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + BOLD + "│          " + YELLOW + "GESTION DES CLIENTS" + CYAN + "           │" + RESET);
        System.out.println(CYAN + BOLD + "└────────────────────────────────────────┘" + RESET);
        System.out.println("  1. Ajouter un Client");
        System.out.println("  2. Lister les Clients");
        System.out.println("  3. Rechercher des Clients");
        System.out.println("  0. Retour");
        System.out.print("\n▶ Choix : ");
        int choice = lireEntier();

        if (choice == 0) return;

        if (choice == 1) {
            String nom = lireChaineNonVide("Nom: ");
            String prenom = lireChaineNonVide("Prénom: ");
            String tel = lireTelephone("Téléphone (min 9 chiffres): ");
            String adr = lireChaineNonVide("Adresse: ");
            
            if (service.createClient(nom, prenom, tel, adr)) {
                System.out.println(GREEN + "Client ajouté avec succès !" + RESET);
            } else {
                System.out.println(RED + "Erreur: Impossible d'ajouter le client." + RESET);
            }
        } else if (choice == 2) {
            List<Client> clients = service.getAllClients();
            if (clients.isEmpty()) System.out.println("Aucun client trouvé.");
            for (Client c : clients) System.out.println(c);
        } else if (choice == 3) {
            String keyword = lireChaineNonVide("Mot-clé (nom, prénom ou tel): ");
            List<Client> results = service.searchClients(keyword);
            if (results.isEmpty()) System.out.println("Aucun client trouvé.");
            for (Client c : results) System.out.println(c);
        }
        pause();
    }

    private static void accountMenu() {
        clearScreen();
        System.out.println(CYAN + BOLD + "\n┌────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + BOLD + "│          " + YELLOW + "GESTION DES COMPTES" + CYAN + "           │" + RESET);
        System.out.println(CYAN + BOLD + "└────────────────────────────────────────┘" + RESET);
        System.out.println("  1. Créer un Compte");
        System.out.println("  2. Consulter le Solde");
        System.out.println("  0. Retour");
        System.out.print("\n▶ Choix : ");
        int choice = lireEntier();

        if (choice == 0) return;

        if (choice == 1) {
            String num = lireChaineNonVide("Numéro de compte: ");
            System.out.print("ID Client: "); int clientId = lireEntier();
            if (service.createAccount(num, clientId)) {
                System.out.println(GREEN + "Compte créé avec succès !" + RESET);
            } else {
                System.out.println(RED + "Erreur: Compte existant ou Client introuvable." + RESET);
            }
        } else if (choice == 2) {
            String num = lireChaineNonVide("Numéro de compte: ");
            Double solde = service.getBalance(num);
            if (solde != null) System.out.println(GREEN + "Solde actuel: " + solde + " FCFA" + RESET);
            else System.out.println(RED + "Compte inexistant." + RESET);
        }
        pause();
    }

    private static void marchandMenu() {
        clearScreen();
        System.out.println(CYAN + BOLD + "\n┌────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + BOLD + "│         " + YELLOW + "GESTION DES MARCHANDS" + CYAN + "          │" + RESET);
        System.out.println(CYAN + BOLD + "└────────────────────────────────────────┘" + RESET);
        System.out.println("  1. Ajouter un Marchand");
        System.out.println("  2. Lister les Marchands");
        System.out.println("  0. Retour");
        System.out.print("\n▶ Choix : ");
        int choice = lireEntier();

        if (choice == 0) return;

        if (choice == 1) {
            String nom = lireChaineNonVide("Nom du Marchand: ");
            String compte = lireChaineNonVide("Numéro compte récepteur: ");
            String type = lireChaineNonVide("Type de commerce: ");
            
            if (service.createMarchand(nom, compte, type)) {
                System.out.println(GREEN + "Marchand enregistré avec succès !" + RESET);
            } else {
                System.out.println(RED + "Erreur lors de l'ajout du marchand." + RESET);
            }
        } else if (choice == 2) {
            List<Marchand> marchands = service.getAllMarchands();
            if (marchands.isEmpty()) System.out.println("Aucun marchand enregistré.");
            for (Marchand m : marchands) System.out.println(m);
        }
        pause();
    }

    private static void operationMenu() {
        clearScreen();
        System.out.println(CYAN + BOLD + "\n┌────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + BOLD + "│         " + YELLOW + "OPERATIONS FINANCIERES" + CYAN + "         │" + RESET);
        System.out.println(CYAN + BOLD + "└────────────────────────────────────────┘" + RESET);
        System.out.println("  1. Dépôt");
        System.out.println("  2. Retrait");
        System.out.println("  3. Transfert");
        System.out.println("  4. Paiement Marchand");
        System.out.println("  0. Retour");
        System.out.print("\n▶ Choix : ");
        int choice = lireEntier();

        if (choice == 0) return;

        if (choice >= 1 && choice <= 4) {
            String num = lireChaineNonVide("Numéro de compte principal: ");
            double montant = lireMontantPositif("Montant: ");

            boolean success = false;
            switch (choice) {
                case 1: success = service.deposit(num, montant); break;
                case 2: success = service.withdraw(num, montant); break;
                case 3:
                    String dest = lireChaineNonVide("Numéro compte destinataire: ");
                    success = service.transfer(num, dest, montant);
                    break;
                case 4:
                    String merchant = lireChaineNonVide("Nom du marchand: ");
                    success = service.payMerchant(num, merchant, montant);
                    break;
            }

            if (success) System.out.println(GREEN + "Opération effectuée avec succès !" + RESET);
            else System.out.println(RED + "Opération refusée ou échouée." + RESET);
        }
        pause();
    }

    private static void statsMenu() {
        clearScreen();
        System.out.println(CYAN + BOLD + "\n┌────────────────────────────────────────┐" + RESET);
        System.out.println(CYAN + BOLD + "│       " + YELLOW + "STATISTIQUES ET RECHERCHE" + CYAN + "        │" + RESET);
        System.out.println(CYAN + BOLD + "└────────────────────────────────────────┘" + RESET);
        System.out.println("  1. Statistiques Globales");
        System.out.println("  2. Historique Complet");
        System.out.println("  3. Historique d'un Compte");
        System.out.println("  4. Recherche par Plage de Dates");
        System.out.println("  0. Retour");
        System.out.print("\n▶ Choix : ");
        int choice = lireEntier();

        if (choice == 0) return;

        switch (choice) {
            case 1:
                service.showStatistics();
                break;
            case 2:
                for (Operation o : service.getGlobalHistory()) System.out.println(o);
                break;
            case 3:
                String num = lireChaineNonVide("Numéro de compte: ");
                List<Operation> history = service.getAccountHistory(num);
                if (history != null && !history.isEmpty()) {
                    for (Operation o : history) System.out.println(o);
                } else {
                    System.out.println(RED + "Compte inexistant ou aucun historique." + RESET);
                }
                break;
            case 4:
                Timestamp debut = lireDate("Date de début (YYYY-MM-DD): ", " 00:00:00");
                Timestamp fin = lireDate("Date de fin (YYYY-MM-DD): ", " 23:59:59");
                if (debut != null && fin != null) {
                    List<Operation> rangeHistory = service.getOperationsByDate(debut, fin);
                    if (rangeHistory.isEmpty()) System.out.println("Aucune opération trouvée.");
                    for (Operation o : rangeHistory) System.out.println(o);
                }
                break;
        }
        pause();
    }

    // --- Utilitaires et Validations ---

    private static int lireEntier() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(RED + "Entrée invalide. Saisissez un entier: " + RESET);
            }
        }
    }

    private static double lireMontantPositif(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val <= 0) {
                    System.out.println(RED + "Le montant doit être strictement positif." + RESET);
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Montant invalide." + RESET);
            }
        }
    }

    private static String lireChaineNonVide(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    private static String lireTelephone(String prompt) {
        while (true) {
            String tel = lireChaineNonVide(prompt);
            if (tel.matches("\\d{9,}")) {
                return tel;
            } else {
                System.out.println(RED + "Le numéro doit contenir au moins 9 chiffres et aucun espace/lettre." + RESET);
            }
        }
    }

    private static Timestamp lireDate(String prompt, String suffix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("\\d{4}-\\d{2}-\\d{2}")) {
                try {
                    Date date = sdf.parse(input + suffix);
                    return new Timestamp(date.getTime());
                } catch (Exception e) {
                    System.out.println(RED + "Erreur de format. Réessayez." + RESET);
                }
            } else {
                System.out.println(RED + "Veuillez respecter le format AAAA-MM-JJ." + RESET);
            }
        }
    }

    private static void pause() {
        System.out.println(YELLOW + "\nAppuyez sur Entrée pour continuer..." + RESET);
        scanner.nextLine();
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            // Repli si impossible de clear
            for (int i=0; i<50; i++) System.out.println();
        }
    }
}

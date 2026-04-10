# Mobile Money System - Java Console Application

## Présentation du Projet
Cette application console développée en **Java** simule un système de **Mobile Money** complet (inspiré de services comme Wave ou Orange Money). Ce projet est réalisé dans le cadre de notre formation en **DUT2 Informatique** à l'École Supérieure Polytechnique (**ESP/UCAD**).

L'objectif est de garantir la sécurité des transactions et la persistance des données via une architecture robuste.

---

## Fonctionnalités Principales

### Gestion des Clients & Marchands
* Création et gestion des profils clients (Nom, Téléphone, Adresse).
* Gestion des **Marchands** pour les paiements de services.

### Gestion des Comptes
* Création de comptes associés de manière unique à un client.
* Consultation du solde en temps réel avec formatage monétaire (FCFA).

### Opérations Financières & Analyse
* **Transactions** : Dépôt, Retrait, Transfert sécurisé et Paiement Marchand.
* **Sécurité** : Gestion de l'atomicité des transferts (Transactions SQL).
* **Historique** : Consultation détaillée des mouvements par compte.
* **Statistiques** : Analyse globale du volume des transactions et recherche par plage de dates.

---

## Structure du Projet (Architecture N-Tiers)
Le projet respecte une séparation stricte des responsabilités :
- `model` : Objets métier (Client, Compte, Operation).
- `dao` : Data Access Objects pour les requêtes SQL.
- `service` : Logique métier et sécurisation des échanges.
- `database` : Singleton de connexion JDBC.
- `ui` : Interface utilisateur console interactive (codes couleurs ANSI).

---

## Spécifications Techniques
* **Langage** : Java (POO).
* **Base de données** : MySQL (`mobilemoney_db`).
* **Connecteur** : JDBC (mysql-connector-j).
* **Outil de modélisation** : StarUML (Diagramme de classe).

---

## Compilation du Projet
Ouvrez un terminal à la racine du dossier PROJETJAVA et exécutez la commande suivante :

```bash
javac -d . -cp ".;lib/*" dao/*.java database/*.java model/*.java service/*.java ui/*.java
```
---

## Exécution de l'Application
Une fois compilé, lancez le programme avec cette commande :
```bash
java -cp ".;lib/*" ui.App
```

---

## Équipe de Développement
* **Khadidiatou GAYE**
* **Josias ADINSI**
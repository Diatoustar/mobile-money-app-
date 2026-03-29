# Mobile Money System - Java Console Application

## Présentation du Projet
Cette application console développée en **Java** permet de simuler un système de **Mobile Money** complet. Ce projet est réalisé dans le cadre de notre formation en **DUT2 Informatique** à l'École Supérieure Polytechnique (**ESP**).

L'objectif principal est de gérer des clients, des comptes et de sécuriser des opérations financières via une base de données **MySQL**.

---

## Fonctionnalités Principales

### Gestion des Clients
* Ajout de nouveaux clients avec informations personnelles (Nom, Prénom, Téléphone, Adresse).
* Affichage de la liste complète des clients enregistrés.
* Recherche multicritère de clients.

### Gestion des Comptes
* Création de comptes Mobile Money associés à un client.
* Consultation du solde en temps réel.

### Opérations Financières
* **Dépôt** : Alimentation d'un compte spécifique.
* **Retrait** : Retrait d'argent sous condition de solde suffisant.
* **Transfert** : Envoi d'argent de compte à compte.
* **Paiement Marchand** : Règlement de services auprès de commerçants.
* **Historique** : Consultation globale ou détaillée par compte de toutes les transactions.

---
## Structure du projet
PROJET_JAV/
├── dao/
│   ├── ClientDAO.java 
│   ├── CompteDAO.java 
│   └── OperationDAO.java 
├── database/ 
│   └── Database.java
├── model/
│   ├── Client.java 
│   ├── Compte.java 
│   └── Operation.java 
├── service/ 
│   └── MobileMoneyService.java
└── ui/ 
    └── App.java 

## Spécifications Techniques
* **Langage** : Java.
* **Paradigme** : Programmation Orientée Objet (POO) avec encapsulation et gestion des exceptions.
* **Base de données** : MySQL (nommée `mobile_money_db`).
* **Persistance** : Connexion via **JDBC**.
* **Architecture** : Séparation en couches:
    * `model` : Entités (Client, Compte, Operation).
    * `dao` : Accès aux données (Data Access Object).
    * `service` : Logique métier.
    * `database` : Gestion de la connexion JDBC.
    * `ui` : Interface utilisateur console.

---

## Équipe de Développement
* **Khadidiatou GAYE**
* **Josias ADINSI**

>
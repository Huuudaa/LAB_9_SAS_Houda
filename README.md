# TP Lab 9 — Consommer un Web Service PHP depuis Android (Volley + Gson)

## Description

Application Android connectée à un Web Service PHP 8 via la bibliothèque **Volley**.
L'application permet d'ajouter des membres dans une base de données MySQL et
d'afficher la liste complète des membres enregistrés. La communication entre
Android et le serveur PHP se fait en **JSON**, parsé avec **Gson**.

---

## Demonstration

https://youtu.be/16PRFVg3DKw
---
## Architecture globale

```
Application Android
        │
        │  HTTP (Volley)
        ▼
Web Service PHP (XAMPP)
        │
        │  PDO
        ▼
Base de données MySQL (campus_db)
```

---

## Fonctionnalités

- Formulaire d'ajout d'un membre (nom, prénom, ville, genre)
- Envoi des données au serveur PHP via **requête POST Volley**
- Affichage de la réponse JSON parsée avec **Gson**
- Bouton "Voir membres" → navigation vers la liste complète
- Liste de tous les membres chargée via **requête GET Volley**
- Configuration réseau pour autoriser HTTP local (Android 9+)

---

## Partie 1 — Base de données MySQL

### Base et table

| Élément | Valeur |
|---|---|
| Base de données | `campus_db` |
| Table | `Membre` |
| Colonnes | `id`, `nom`, `prenom`, `ville`, `genre` |

### Données de test insérées

```
• Sas Houda — Marrakech — femme
• Rouhi Youssef — Casablanca — homme
```
<img width="1280" height="559" alt="image" src="https://github.com/user-attachments/assets/97756693-31ba-40c5-8b77-68c507871c2c" />

### Test avec Advanced REST Client (ARC)

Avant de tester depuis Android, vérifier les WS avec ARC :

| Test | URL | Méthode | Paramètres |
|---|---|---|---|
| Ajouter un membre | `http://localhost/projet/ws/ajouterMembre.php` | POST | `nom`, `prenom`, `ville`, `genre` |


| Lister les membres | `http://localhost/projet/ws/listerMembres.php` | GET | — |
<img width="1280" height="636" alt="image" src="https://github.com/user-attachments/assets/d9514ee7-0a37-4f9e-bc65-f634ae58cc4e" />


---
---

## Partie 2 — Web Service PHP

### Structure du projet PHP

```
C:\xampp\htdocs\projet\
├── classes/
│   └── Membre.php
├── connexion/
│   └── Connexion.php
├── dao/
│   └── IDao.php
├── service/
│   └── MembreService.php
└── ws/
    ├── ajouterMembre.php
    └── listerMembres.php
```

### Fichiers PHP

| Fichier | Rôle |
|---|---|
| `Connexion.php` | Connexion PDO à MySQL |
| `Membre.php` | Classe métier avec getters |
| `IDao.php` | Interface CRUD générique |
| `MembreService.php` | Implémentation CRUD + `recupererTous()` |
| `ajouterMembre.php` | WS POST — ajoute un membre et retourne la liste |
| `listerMembres.php` | WS GET — retourne tous les membres en JSON |

### URLs des Web Services

| URL | Méthode | Rôle |
|---|---|---|
| `http://10.0.2.2/projet/ws/ajouterMembre.php` | POST | Ajouter un membre |
| `http://10.0.2.2/projet/ws/listerMembres.php` | GET | Lister tous les membres |

> `10.0.2.2` est l'adresse de `localhost` vue depuis l'émulateur Android

### Exemple de réponse JSON

```json
[
  {"id":"1","nom":"Sas","prenom":"Houda","ville":"Marrakech","genre":"femme"},
  {"id":"2","nom":"Alami","prenom":"Youssef","ville":"Casablanca","genre":"homme"}
]
```
<img width="1214" height="400" alt="image" src="https://github.com/user-attachments/assets/6f234812-dc6c-4b14-881d-dc9d2b84a97e" />

---

## Partie 3 — Application Android

### Structure du projet Android

```
app/
├── src/
│   └── main/
│       ├── java/com/example/lab_9_sas_houda/
│       │   ├── beans/
│       │   │   └── Membre.java
│       │   └── ui/
│       │       ├── AjouterMembreActivity.java
│       │       └── ListeMembresActivity.java
│       └── res/
│           ├── layout/
│           │   ├── activity_ajouter_membre.xml
│           │   ├── activity_liste_membres.xml
│           │   └── item_membre.xml
│           ├── values/
│           │   ├── styles.xml
│           │   └── arrays.xml
│           └── xml/
│               └── network_security_config.xml
```

### Écrans

#### Écran 1 — Formulaire d'ajout (`AjouterMembreActivity`)

| Composant | ID | Rôle |
|---|---|---|
| `EditText` | `editNom` | Saisie du nom |
| `EditText` | `editPrenom` | Saisie du prénom |
| `Spinner` | `spinnerVille` | Sélection de la ville |
| `RadioButton` | `radioHomme` | Genre masculin |
| `RadioButton` | `radioFemme` | Genre féminin |
| `Button` | `btnAjouter` | Envoie la requête POST |
| `Button` | `btnVoirMembres` | Navigue vers la liste |
| `TextView` | `tvReponse` | Affiche la réponse du serveur |

#### Écran 2 — Liste des membres (`ListeMembresActivity`)

| Composant | ID | Rôle |
|---|---|---|
| `RecyclerView` / `ListView` | `listeMembres` | Affiche tous les membres |

---

## Flux de navigation

```
AjouterMembreActivity
        │
        ├── [Bouton Enregistrer] ──▶ POST vers ajouterMembre.php
        │                                    │
        │                            Réponse JSON affichée dans tvReponse
        │
        └── [Bouton Voir membres] ──▶ ListeMembresActivity
                                             │
                                     GET vers listerMembres.php
                                             │
                                     Liste JSON parsée et affichée
```

---

## Dépendances (`build.gradle.kts`)

```kotlin
implementation("androidx.appcompat:appcompat:1.6.1")
implementation("androidx.constraintlayout:constraintlayout:2.1.4")
implementation("com.android.volley:volley:1.2.1")
implementation("com.google.code.gson:gson:2.10.1")
```

---

## Configuration réseau (Android 9+)

Android 9 bloque par défaut les connexions HTTP non sécurisées.
Deux configurations nécessaires :

1. `res/xml/network_security_config.xml` — autorise `10.0.2.2`
2. `AndroidManifest.xml` — déclare `networkSecurityConfig` et `usesCleartextTraffic`

---

## AndroidManifest.xml — points importants

- Permission `INTERNET` obligatoire
- `networkSecurityConfig` pour autoriser HTTP local
- `AjouterMembreActivity` est le point d'entrée (`LAUNCHER`)
- `ListeMembresActivity` déclarée sans `exported`

---

## Concepts utilisés

| Concept | Description |
|---|---|
| `Volley` | Bibliothèque Android pour les requêtes HTTP |
| `StringRequest` | Requête HTTP retournant une String |
| `RequestQueue` | File d'attente des requêtes Volley |
| `getParams()` | Fournit les paramètres POST à Volley |
| `Gson` | Sérialisation/désérialisation JSON ↔ Java |
| `TypeToken` | Indique à Gson le type générique à parser |
| `PDO` | Accès sécurisé à MySQL depuis PHP |
| `json_encode()` | Convertit un tableau PHP en JSON |
| `FETCH_ASSOC` | Récupère les résultats SQL en tableau associatif |
| `10.0.2.2` | Adresse localhost vue depuis l'émulateur Android |

---

## Environnement

- **IDE Android** : Android Studio
- **IDE PHP** : VS Code / NetBeans
- **Serveur** : XAMPP (Apache + MySQL)
- **Langage Android** : Java
- **Langage serveur** : PHP 8
- **Base de données** : MySQL
- **Minimum SDK** : API 26 (Android 8.0)
- **Émulateur** : Medium Phone API 36.1

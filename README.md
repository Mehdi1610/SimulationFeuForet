# Simulation de feu de forêt

Simulation, étape par étape, de la propagation d'un feu sur une grille 2D : une case en feu s'éteint (cendre) et propage le feu à ses 4 voisines directes avec une probabilité `p`.

## Prérequis

- Java 21
- Maven

## Configuration

Les paramètres de la simulation sont définis dans `src/main/resources/application.properties` :

```properties
hauteur=5
largeur=5
probabilite=0.5
feuInitial=[(2,2)]
```

- `hauteur` / `largeur` : dimensions de la grille
- `probabilite` : probabilité (entre 0 et 1) qu'une case voisine prenne feu à chaque étape
- `feuInitial` : position(s) initiale(s) en feu, au format `[(x1,y1),(x2,y2),...]` — attention, ici **x = colonne, y = ligne** (converti automatiquement en interne)

## Lancer le programme

Depuis IntelliJ : exécuter la classe `Main`.

Depuis un terminal, à la racine du projet :

```bash
mvn compile exec:java
```

Le programme lit `application.properties`, exécute la simulation jusqu'à extinction complète du feu, et affiche chaque étape dans la console (`■` case vide, `F` case en feu, `#` cendre).

## Lancer les tests

```bash
mvn test
```

## Architecture

| Classe | Rôle                                                                                                 |
|---|------------------------------------------------------------------------------------------------------|
| `EtatCase` | Enum des 3 états d'une case (`VIDE`, `EN_FEU`, `CENDRE`)                                             |
| `Position` | Coordonnées `(ligne, colonne)` d'une case                                                            |
| `Grille` | Représente la grille, gère les accès, les voisins (avec gestion des bords) et la copie               |
| `ConfigurationSimulation` | Porte les paramètres de simulation, valide leur cohérence (dimensions, probabilité, positions)       |
| `LecteurConfiguration` / `LecteurConfigurationProperties` | Lit et parse le fichier `.properties`                                                                |
| `ConfigurationInvalideException` | Erreur dédiée en cas de fichier de config illisible ou mal formé                                     |
| `GenerateurAleatoire` / `GenerateurAleatoireImpl` | Encapsule l'aléa (tirage de probabilité), avec support d'une seed fixe pour des tests reproductibles |
| `SimulationFeu` | Classe principale : fait évoluer la grille étape par étape et affiche le résultat                    |
| `Main` | Point d'entrée, orchestre la lecture de la config et le lancement de la simulation                   |


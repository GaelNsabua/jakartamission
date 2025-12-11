# Jakarta Mission - Découvrir l'Indonésie

Application web Jakarta EE pour explorer et partager des lieux touristiques en Indonésie.

## Description

Jakarta Mission est une application web moderne développée avec Jakarta EE 10 qui permet aux utilisateurs de découvrir l'Indonésie, d'ajouter leurs lieux préférés et d'explorer la richesse culturelle de l'archipel indonésien.

## Fonctionnalités

- **Page d'accueil** : Hero section avec présentation de l'Indonésie
- **Gestion des lieux** : Ajouter des lieux touristiques avec nom, description, latitude et longitude
- **Exploration** : Visualiser les lieux ajoutés avec images et coordonnées GPS
- **À propos** : Informations détaillées sur l'Indonésie (géographie, culture, économie)
- **Design moderne** : Interface responsive avec Tailwind CSS et la police Lobster

## Technologies

- **Jakarta EE 10** (Jakarta Faces, CDI, Persistence)
- **Java**
- **Maven** pour la gestion des dépendances
- **Tailwind CSS** pour le style
- **Google Fonts** (Lobster)
- **Unsplash** pour les images

## Structure du Projet

```
jakartamission/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/jakarta/udbl/jakartamission/
│       │       ├── beans/
│       │       │   ├── LieuBean.java
│       │       │   ├── WelcomeBean.java
│       │       │   └── NavigationBean.java
│       │       └── resources/
│       ├── resources/
│       │   └── META-INF/
│       │       └── persistence.xml
│       └── webapp/
│           ├── index.xhtml
│           ├── home.xhtml
│           ├── pages/
│           │   ├── lieu.xhtml
│           │   └── a_propos.xhtml
│           └── WEB-INF/
│               ├── beans.xml
│               ├── faces-config.xml
│               ├── glassfish-web.xml
│               └── web.xml
└── pom.xml
```

## Installation et Déploiement

### Prérequis

- Java 11 ou supérieur
- Maven 3.6+
- GlassFish Server 7.x ou compatible Jakarta EE 10

### Étapes

1. **Cloner le projet**
   ```bash
   git clone https://github.com/GaelNsabua/jakartamission.git
   cd jakartamission
   ```

2. **Compiler le projet**
   ```bash
   mvn clean install
   ```

3. **Déployer sur GlassFish**
   - Copier le fichier WAR généré (`target/jakartamission-1.0.war`) dans le répertoire de déploiement de GlassFish
   - Ou utiliser l'interface d'administration de GlassFish pour déployer l'application

4. **Accéder à l'application**
   ```
   http://localhost:8080/jakartamission/
   ```

## Thème Visuel

L'application utilise les couleurs du drapeau indonésien (rouge et blanc) pour créer une identité visuelle cohérente :
- Gradients rouge/blanc dans les headers
- Boutons rouges avec effets hover
- Police Lobster pour un style unique et chaleureux
- Images provenant d'Unsplash pour illustrer l'Indonésie

## Beans Principaux

### LieuBean
Bean de session qui gère l'ajout et l'affichage des lieux touristiques.
- Propriétés : nom, description, latitude, longitude
- Liste des lieux ajoutés en session

### NavigationBean
Gère la navigation entre les différentes pages de l'application.

### WelcomeBean
Bean de démonstration pour la page d'accueil.

## Pages Disponibles

- `/index.xhtml` - Page de bienvenue avec le drapeau indonésien
- `/home.xhtml` - Page d'accueil principale avec navigation
- `/pages/lieu.xhtml` - Gestion des lieux (ajout et visualisation)
- `/pages/a_propos.xhtml` - Informations sur l'Indonésie

## Contribution

Les contributions sont les bienvenues ! N'hésitez pas à :
1. Fork le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Ouvrir une Pull Request

## Licence

Ce projet est développé dans un cadre éducatif.

## Auteur

**Gael Nsabua**
- GitHub: [@GaelNsabua](https://github.com/GaelNsabua)

## Remerciements

- Images fournies par [Unsplash](https://unsplash.com)
- Drapeau indonésien via Wikimedia Commons
- Framework CSS : [Tailwind CSS](https://tailwindcss.com)
- Police : [Google Fonts - Lobster](https://fonts.google.com/specimen/Lobster)

# Rapport de Projet - Application Touristique

## Introduction

Ce rapport décrit une application Android développée pour promouvoir le tourisme à Alger. L'application aide les utilisateurs à découvrir des lieux d'intérêt grâce à une interface moderne en thème sombre, un carrousel d'images, des listes de lieux, et des pages de détails avec galeries. Elle prend en charge l'anglais et l'arabe, inclut des animations fluides, et offre une navigation intuitive. Ce document présente la solution proposée, les composants de l'application (classes, fonctions, interfaces), les problèmes résolus, et la répartition des tâches entre Abdeldjalil, Ryane, Islam, et Yanice.

## Solution Proposée

L'application est une application Android native, développée en Java avec Android Studio, utilisant Material Design pour une interface attrayante et moderne. Elle permet aux utilisateurs d'explorer les lieux touristiques d'Alger de manière simple et interactive. Les principales fonctionnalités incluent :

1. **Page d'Accueil** :

    - Affiche un carrousel d'images qui défile automatiquement toutes les 3 secondes pour présenter des lieux emblématiques.
    - Inclut une image d'en-tête, une description, et une section avec les noms des étudiants : Abdeldjalil, Ryane, Islam, Yanice.
    - Offre une barre de recherche pour trouver des lieux, un changement de langue (anglais/arabe), et une navigation vers d'autres écrans.

2. **Liste des Lieux** :

    - Présente une liste de lieux avec des images, des titres, et une option pour ajouter aux favoris.
    - Inclut une barre de recherche pour filtrer les lieux rapidement.

3. **Détails des Lieux** :

    - Affiche une galerie d'images horizontale avec des points indicateurs pour montrer quelle image est visible.
    - Permet d'ouvrir les images en plein écran via un dialogue.
    - Montre le nom du lieu, une description, une note (étoiles), le nombre d'avis, et des icônes pour appeler, envoyer un SMS, envoyer un email, visiter un site web, ou ouvrir une carte.

4. **Thème et Accessibilité** :

    - Utilise un thème sombre avec des couleurs modernes et des coins arrondis sur les images pour un look professionnel.
    - Supporte l'anglais et l'arabe grâce à des fichiers de traduction.
    - Inclut des descriptions textuelles pour les images, améliorant l'accessibilité pour les utilisateurs de lecteurs d'écran.

## Problèmes Résolus

1. **Problème de Plein Écran** :

    - L'ouverture des images en plein écran causait un crash.
    - **Solution** : Correction du composant d'image dans le layout plein écran et vérification du code.

2. **Images Déformées dans le Carrousel** :

    - Les images du carrousel semblaient zoomées ou mal dimensionnées.
    - **Solution** : Ajustement de la taille et du mode d'affichage des images pour un rendu clair.

3. **Coins Arrondis Incohérents** :

    - Certaines images n'avaient pas de coins arrondis uniformes.
    - **Solution** : Application de coins arrondis à toutes les images pour une apparence cohérente.

## Composants de l'Application

### Pourquoi Utiliser RecyclerView au lieu de ListView

Nous avons choisi `RecyclerView` plutôt que `ListView` car il est plus moderne et efficace. `RecyclerView` réutilise les vues pour économiser de la mémoire, ce qui rend le défilement plus fluide, surtout pour les listes ou galeries avec beaucoup d'images. Il offre aussi plus de flexibilité pour personnaliser l'affichage, comme les galeries horizontales ou les animations.

### Classes Principales

1. **MainActivity** :

    - **Rôle** : Gère la page d'accueil avec le carrousel, l'image d'en-tête, et la navigation.
    - **Fonctions clés** :
        - Initialise le carrousel avec des images défilantes automatiquement.
        - Configure la barre de recherche et le changement de langue.
        - Gère la navigation vers d'autres écrans via un menu inférieur.
    - **Attributs** :
        - Carrousel (`RecyclerView`) pour les images.
        - Image d'en-tête (`ShapeableImageView`).
        - Menu de navigation (`BottomNavigationView`).

2. **PlacesListActivity** :

    - **Rôle** : Affiche une liste de lieux avec des images et des titres.
    - **Fonctions clés** :
        - Configure la liste des lieux avec un adaptateur.
        - Gère la recherche pour filtrer les lieux.
        - Ouvre la page de détails lors d'un clic sur un lieu.
    - **Attributs** :
        - Liste (`RecyclerView`) pour les lieux.
        - Barre de recherche (`SearchView`).

3. **PlaceDetailsActivity** :

    - **Rôle** : Présente les détails d'un lieu, y compris une galerie d'images.
    - **Fonctions clés** :
        - Affiche la galerie, le nom, la description, la note, et les actions (appel, SMS, etc.).
        - Ouvre un dialogue plein écran pour les images.
        - Met à jour les points indicateurs lors du défilement de la galerie.
    - **Attributs** :
        - Galerie (`RecyclerView`) pour les images.
        - Dialogue (`ViewPager2`) pour le plein écran.
        - Champs de texte (`TextView`), note (`RatingBar`), et icônes d'action (`LinearLayout`).

4. **PhotoAdapter** :

    - **Rôle** : Gère l'affichage des images dans le carrousel, la galerie, et le plein écran.
    - **Fonctions clés** :
        - Charge les images selon le contexte (carrousel, galerie, plein écran).
        - Applique des animations pour le carrousel.
        - Gère les clics sur les images pour ouvrir le plein écran.
    - **Attributs** :
        - Liste d'images (`List<Integer>`).
        - Indicateurs pour le type d'affichage (carrousel, plein écran).

### Interfaces

1. **OnPhotoClickListener** :
    - **Rôle** : Permet de gérer les clics sur les images de la galerie.
    - **Méthode** : `onPhotoClick(int position)` pour ouvrir l'image en plein écran.
    - **Utilisation** : Dans `PlaceDetailsActivity` pour lancer le dialogue plein écran.

### Layouts

- **Page d'Accueil** : Contient le carrousel, l'image d'en-tête, la section avec les noms des étudiants (Abdeldjalil, Ryane, Islam, Yanice), et un menu de navigation.
- **Liste des Lieux** : Affiche une liste verticale de lieux avec images et titres.
- **Détails des Lieux** : Inclut une galerie horizontale, des points de pagination, le nom, la description, la note, et des icônes d'action.
- **Dialogue Plein Écran** : Montre une image en grand avec un bouton pour fermer.
- **Layouts d'Images** : Gèrent les images pour le carrousel (petites), la galerie (moyennes), et le plein écran (grandes).

## Répartition des Tâches

L'équipe est composée d'Abdeldjalil, Ryane, Islam, et Yanice. Voici la répartition des tâches :

1. **Abdeldjalil - Interface Utilisateur** :

    - Développement du carrousel et des animations.
    - Création des interfaces (layouts, images, thème sombre).
    - **Contribution** : Design moderne et accessible.

2. **Ryane - Page d'Accueil** :

    - Configuration de la recherche et du changement de langue.
    - Gestion des traductions (anglais/arabe).
    - **Contribution** : Accueil interactif et fluide.

3. **Islam - Liste des Lieux** :

    - Création de la liste des lieux et de la recherche.
    - Gestion de la navigation entre écrans.
    - **Contribution** : Navigation simple et efficace.

4. **Yanice - Détails et Correctifs** :

    - Développement de la page de détails et de la galerie.
    - Résolution des problèmes (crash, images, coins arrondis).
    - **Contribution** : Détails fiables et corrections.

### Coordination

- Réunions hebdomadaires pour planifier et tester.
- Utilisation de Git pour gérer le code et Trello pour organiser les tâches.
- Tests sur différents appareils pour assurer la compatibilité.

## Conclusion

L'application touristique offre une expérience moderne et intuitive pour découvrir Alger. Avec un carrousel animé, des listes de lieux, des galeries d'images, et un support multilingue, elle est facile à utiliser et accessible. Les problèmes de crashes et d'affichage ont été résolus, et le thème sombre avec coins arrondis donne un look professionnel. La répartition des tâches entre Abdeldjalil, Ryane, Islam, et Yanice a permis une collaboration efficace, avec des tests réguliers pour garantir la qualité.
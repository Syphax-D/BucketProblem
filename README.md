Problème des Seaux
Projet L3 Informatique - UE Intelligence Artificielle Symbolique 2025/2026

Membres du groupe :
- AMROUN Said
- DIB Syphax

Présentation
Ce projet résout le problème des seaux par exploration d'un espace d'états. On part de seaux tous vides et on cherche à atteindre une répartition précise de l'eau en enchaînant des actions simples : remplir un seau, le vider, ou transvaser son contenu dans un autre.
Pour trouver la séquence d'actions, trois stratégies de parcours sont implémentées et comparées sur la même instance :

BFS (parcours en largeur) - garantit le chemin le plus court
DFS (parcours en profondeur) - s'enfonce dans une branche avant de revenir en arrière
Best-First - guidé par une heuristique, il privilégie les états les plus proches du but

Le programme affiche pour chaque stratégie le chemin trouvé, le nombre d'étapes et le nombre de nœuds explorés.

Structure du projet

- State : représente une configuration des seaux à un instant donné
- BucketInstance : lit et charge le fichier .buck
- BucketSearchEngine : moteur de recherche générique, indépendant de la stratégie
- Open / OpenFile / OpenPile / OpenListe : implémentations des trois stratégies de parcours
- SolveurSeaux : point d'entrée du programme



package org.example;

import java.util.Collection;

/**
 * Interface représentant la structure "Ouvert" du pseudo-code de recherche arborescente
 * @param <T> Type des états (State)
 */
public interface Open<T> {
    /** Insère un état dans la structure Ouvert */
    void inserer(T etat);

    /** Retourne et retire la tête de Ouvert (e <- tête(Ouvert) puis Ouvert <- Ouvert - {e}) */
    T extraireTete();

    /** Retourne la tête sans la retirer (tête(Ouvert)) */
    T tete();

    /** Vérifie si la structure est vide (Ouvert != 0) */
    boolean estVide();

    /** Vérifie si un état est déjà présent dans Ouvert */
    boolean contient(T etat);

    /** Retourne le nombre d'éléments dans la structure */
    int taille();
}
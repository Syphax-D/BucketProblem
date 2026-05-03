package org.example;

import java.util.*;

public class OpenPile<T> implements Open<T> {
    private final Deque<T> pile = new ArrayDeque<>();
    private final Set<T> presence = new HashSet<>();

    @Override public void inserer(T etat) {
        if (!presence.contains(etat)) {
            pile.push(etat);
            presence.add(etat);
        }
    }

    @Override public T extraireTete() {
        T etat = pile.isEmpty() ? null : pile.pop(); // ← pop() pour vrai LIFO
        if (etat != null) presence.remove(etat);
        return etat;
    }

    @Override public T tete() { return pile.peek(); }
    @Override public boolean estVide() { return pile.isEmpty(); }
    @Override public boolean contient(T etat) { return presence.contains(etat); }
    @Override public int taille() { return pile.size(); }
}
package org.example;

import java.util.*;

public class OpenListe<T> implements Open<T> {
    private final PriorityQueue<T> file;
    private final Set<T> presence = new HashSet<>();

    // Constructeur avec comparateur (pour les heuristiques)
    public OpenListe(Comparator<T> comparateur) {
        this.file = new PriorityQueue<>(comparateur);
    }

    @Override public void inserer(T etat) {
        if (!presence.contains(etat)) {
            file.offer(etat);
            presence.add(etat);
        }
    }

    @Override public T extraireTete() {
        T etat = file.poll();
        if (etat != null) presence.remove(etat);
        return etat;
    }

    @Override public T tete() { return file.peek(); }
    @Override public boolean estVide() { return file.isEmpty(); }
    @Override public boolean contient(T etat) { return presence.contains(etat); }
    @Override public int taille() { return file.size(); }
}
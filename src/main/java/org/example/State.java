package org.example;

import java.util.*;

public class State implements Comparable<State> {
    private final int[] contenants;
    private final int[] but;
    private final State parent;
    private final String action;
    private final int profondeur;

    // État initial
    public State(int n, int[] but) {
        this.contenants = new int[n];
        this.but = Arrays.copyOf(but, but.length);
        this.parent = null;
        this.action = "INIT";
        this.profondeur = 0;
    }

    // État successeur
    public State(int[] contenants, int[] but, State parent, String action) {
        this.contenants = Arrays.copyOf(contenants, contenants.length);
        this.but = Arrays.copyOf(but, but.length);
        this.parent = parent;
        this.action = action;
        this.profondeur = parent != null ? parent.profondeur + 1 : 0;
    }

    public boolean estBut(int[] but) {
        return Arrays.equals(this.contenants, but);
    }

    public List<State> genererVoisins(int[] capacites) {
        List<State> voisins = new ArrayList<>();
        int n = contenants.length;

        // Remplir
        for (int i = 0; i < n; i++) {
            if (contenants[i] < capacites[i]) {
                int[] nouveau = Arrays.copyOf(contenants, n);
                nouveau[i] = capacites[i];
                voisins.add(new State(nouveau, but, this,
                        String.format("Remplir S%d: %d→%d", i+1, contenants[i], capacites[i])));
            }
        }

        // Vider
        for (int i = 0; i < n; i++) {
            if (contenants[i] > 0) {
                int[] nouveau = Arrays.copyOf(contenants, n);
                nouveau[i] = 0;
                voisins.add(new State(nouveau, but, this,
                        String.format("Vider S%d: %d→0", i+1, contenants[i])));
            }
        }

        // Transvaser
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && contenants[i] > 0 && contenants[j] < capacites[j]) {
                    int[] nouveau = Arrays.copyOf(contenants, n);
                    int transfert = Math.min(contenants[i], capacites[j] - contenants[j]);
                    nouveau[i] -= transfert;
                    nouveau[j] += transfert;
                    voisins.add(new State(nouveau, but, this,
                            String.format("Transvaser %dL: S%d→S%d", transfert, i+1, j+1)));
                }
            }
        }

        return voisins;
    }

    // ── Heuristiques ──────────────────────────────────────────────────────────

    // H1 : somme des écarts absolus entre contenu actuel et but
    private int heuristiqueH1() {
        int somme = 0;
        for (int i = 0; i < contenants.length; i++)
            somme += Math.abs(contenants[i] - but[i]);
        return somme;
    }

    // H2 : nombre de seaux pas encore au bon niveau
    private int heuristiqueH2() {
        int count = 0;
        for (int i = 0; i < contenants.length; i++)
            if (contenants[i] != but[i]) count++;
        return count;
    }

    // Comparators statiques pour OpenListe
    public static final Comparator<State> H1 = Comparator.comparingInt(State::heuristiqueH1);
    public static final Comparator<State> H2 = Comparator.comparingInt(State::heuristiqueH2);

    // compareTo utilise H1 par défaut (pour compatibilité Comparable)
    @Override
    public int compareTo(State autre) {
        return Integer.compare(this.heuristiqueH1(), autre.heuristiqueH1());
    }

    // ── Equals / HashCode ─────────────────────────────────────────────────────

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State that = (State) o;
        return Arrays.equals(this.contenants, that.contenants);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(contenants);
    }

    // ── Affichage ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return Arrays.toString(contenants);
    }

    // Itératif pour éviter StackOverflow sur longs chemins
    public void afficherChemin() {
        List<State> chemin = new ArrayList<>();
        State courant = this;
        while (courant != null) {
            chemin.add(courant);
            courant = courant.parent;
        }
        Collections.reverse(chemin);
        System.out.println("        Départ : " + chemin.get(0));
        for (int i = 1; i < chemin.size(); i++) {
            System.out.println("        └─ " + chemin.get(i).action + " → " + chemin.get(i));
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public State getParent() { return parent; }
    public int getProfondeur() { return profondeur; }
}
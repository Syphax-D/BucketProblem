package org.example;

import java.util.*;

public class State implements Comparable<State> {
    private final int[] contenants;
    private final int[] but;
    private final State parent;
    private final String action;

    public State(int n, int[] but) {
        this.contenants = new int[n];
        this.but = Arrays.copyOf(but, but.length);
        this.parent = null;
        this.action = "INIT";
    }

    // ⚠️ Constructeur rendu PUBLIC pour être accessible depuis genererVoisins
    public State(int[] contenants, int[] but, State parent, String action) {
        this.contenants = Arrays.copyOf(contenants, contenants.length);
        this.but = Arrays.copyOf(but, but.length);
        this.parent = parent;
        this.action = action;
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

    private int heuristiqueH1() {
        int somme = 0;
        for (int i = 0; i < contenants.length; i++) {
            somme += Math.abs(contenants[i] - but[i]);
        }
        return somme;
    }

    @Override
    public int compareTo(State autre) {
        return Integer.compare(this.heuristiqueH1(), autre.heuristiqueH1());
    }

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

    @Override
    public String toString() {
        return Arrays.toString(contenants);
    }

    public void afficherChemin() {
        if (parent != null) {
            parent.afficherChemin();
            System.out.println("        └─ " + action + " → " + this);
        } else {
            System.out.println("        Départ : " + this);
        }
    }

    public State getParent() { return parent; }
}
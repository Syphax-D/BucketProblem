package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BucketInstance {
    private final int n;
    private final int[] capacites;
    private final int[] but;
    private final int[] etatInitial;

    public BucketInstance(String cheminFichier) throws IOException {
        List<String> lignes = Files.readAllLines(Paths.get(cheminFichier));

        // Nombre de seaux
        this.n = Integer.parseInt(lignes.get(0).trim());

        // Capacités
        this.capacites = new int[n];
        String[] capTokens = lignes.get(1).trim().split("\\s+");
        for (int i = 0; i < n; i++) {
            capacites[i] = Integer.parseInt(capTokens[i]);
        }

        // But
        this.but = new int[n];
        String[] butTokens = lignes.get(2).trim().split("\\s+");
        for (int i = 0; i < n; i++) {
            but[i] = Integer.parseInt(butTokens[i]);
        }

        // État initial
        this.etatInitial = new int[n]; // initialisé à 0 par défaut
    }

    public int getN() { return n; }
    public int[] getCapacites() { return Arrays.copyOf(capacites, n); }
    public int[] getBut() { return Arrays.copyOf(but, n); }

    @Override
    public String toString() {
        return String.format(
                " seaux: %d\n" +
                        "Capacites: %s\n" +
                        "Etat initial: %s\n" +
                        "But: %s",
                n,
                Arrays.toString(capacites),
                Arrays.toString(etatInitial),
                Arrays.toString(but)
        );
    }
}
package org.example;

import java.util.*;

public class BucketSearchEngine {
    private final BucketInstance instance;
    private int iterations;
    private int noeudsExplores;

    public BucketSearchEngine(BucketInstance instance) {
        this.instance = instance;
    }

    public State rechercher(Open<State> ouvert, boolean utiliserFerme) {
        this.iterations = 0;
        this.noeudsExplores = 0;

        // Ouvert <- {einit}
        State einit = new State(instance.getN(), instance.getBut());
        ouvert.inserer(einit);

        // [Fermé <- 0]
        Set<State> ferme = utiliserFerme ? new HashSet<>() : null;

        final int MAX_ITER = 10_000_000;

        // TantQue Ouvert != 0 Et tête(Ouvert) n'est pas un but
        while (!ouvert.estVide() && !ouvert.tete().estBut(instance.getBut()) && iterations < MAX_ITER) {
            iterations++;

            // e <- tête(Ouvert)
            State e = ouvert.tete();

            // Ouvert <- Ouvert - {e}
            ouvert.extraireTete();
            noeudsExplores++;

            // [Fermé <- Fermé ou {e}]
            if (utiliserFerme) {
                ferme.add(e);
            }

            // générer tous les voisins possibles de e
            List<State> voisins = e.genererVoisins(instance.getCapacites());

            // ... et les insérer dans Ouvert s'ils ne sont pas déjà dans Ouvert [ni dans Fermé]
            for (State voisin : voisins) {
                boolean dansOuvert = ouvert.contient(voisin);
                boolean dansFerme = (utiliserFerme && ferme != null && ferme.contains(voisin));

                if (!dansOuvert && !dansFerme) {
                    ouvert.inserer(voisin);
                }
            }
        }

        // Si Ouvert = 0 Alors il n'existe pas de but accessible
        if (ouvert.estVide() || iterations >= MAX_ITER) {
            System.out.println("ÉCHEC : but inaccessible après " + iterations + " itérations");
            System.out.println("Noeuds explorés : " + noeudsExplores);
            System.out.println("Taille Ouvert   : " + ouvert.taille());
            return null;
        }

        // Sinon l'élément tête(Ouvert) est un but
        State but = ouvert.tete();
        System.out.println("SUCCÈS : but atteint !");
        System.out.println("Itérations      : " + iterations);
        System.out.println("Noeuds explorés : " + noeudsExplores);
        System.out.println("Taille Ouvert   : " + ouvert.taille());
        System.out.println("État final      : " + but);

        // Afficher le chemin solution
        System.out.println("\nChemin solution :");
        but.afficherChemin();

        return but;
    }

    // Getters pour le rapport comparatif
    public int getIterations() { return iterations; }
    public int getNoeudsExplores() { return noeudsExplores; }
}
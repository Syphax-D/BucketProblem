package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SolveurSeaux {

    private static final String CHEMIN_INSTANCE = "src/main/java/org/example/instances/instances/3_7_13_19_23_29_a.buck";
    private static final int PROFONDEUR_MAX_DFS = 100;

    public static void main(String[] args) {
        System.out.println("SOLVEUR AUTONOME - Probleme des seaux");
        System.out.println("Instance : " + CHEMIN_INSTANCE);
        System.out.println("~".repeat(80) + "\n");

        if (!Files.exists(Paths.get(CHEMIN_INSTANCE))) {
            System.err.println("ERREUR : Fichier introuvable");
            return;
        }

        try {
            // Charger l'instance une seule fois
            BucketInstance instance = new BucketInstance(CHEMIN_INSTANCE);
            System.out.println("INSTANCE CHARGEE");
            System.out.println(instance);
            System.out.println("~".repeat(80) + "\n");

            // Best-First avec H1
            testerStrategie("Best-First (Heuristique H1)", new OpenListe<>(State.H1), instance, false);
            System.out.println("\n" + "~".repeat(80) + "\n");

            // Best-First avec H2
            testerStrategie("Best-First (Heuristique H2)", new OpenListe<>(State.H2), instance, false);
            System.out.println("\n" + "~".repeat(80) + "\n");

            // BFS
            testerStrategie("BFS (Parcours en Largeur)", new OpenFile<>(), instance, false);
            System.out.println("\n" + "~".repeat(80) + "\n");

            // DFS avec limite de profondeur
            testerStrategie("DFS (Parcours en Profondeur)", new OpenPile<>(), instance, true);

            // Rapport final
            System.out.println("\n" + "~".repeat(80));
            System.out.println("TEST TERMINE");

        } catch (IOException e) {
            System.err.println("ERREUR de lecture du fichier : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("ERREUR d'execution : " + e.getMessage());
        }
    }

    private static void testerStrategie(String nomStrategie, Open<State> structureOuvert,
                                        BucketInstance instance, boolean isDFS) {
        System.out.println("STRATEGIE : " + nomStrategie);
        System.out.println("-".repeat(80));

        BucketSearchEngine moteur = new BucketSearchEngine(instance);

        State solution;
        if (isDFS) {
            solution = moteur.rechercher(structureOuvert, true, PROFONDEUR_MAX_DFS);
        } else {
            solution = moteur.rechercher(structureOuvert, true);
        }

        if (solution != null) {
            // Compter les étapes
            int etapes = 0;
            State courant = solution;
            while (courant.getParent() != null) {
                etapes++;
                courant = courant.getParent();
            }

            System.out.println("\nRESULTAT : " + nomStrategie);
            System.out.println("Etapes dans le chemin   : " + etapes);
            System.out.println("Iterations de recherche : " + moteur.getIterations());
            System.out.println("Noeuds explores         : " + moteur.getNoeudsExplores());
        } else {
            System.out.println("\nRESULTAT : " + nomStrategie);
            System.out.println("Aucune solution trouvee");
            System.out.println("(Si DFS : essaie d'augmenter PROFONDEUR_MAX_DFS, actuellement " + PROFONDEUR_MAX_DFS + ")");
        }
    }
}
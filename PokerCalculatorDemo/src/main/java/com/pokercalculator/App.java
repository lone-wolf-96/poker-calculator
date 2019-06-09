package com.pokercalculator;

import java.util.Scanner;

public final class App {
    public static void main(String[] args) {
        try (final Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the pokerdata.txt source directory (Press Enter for default):");

            String nextLine = sc.nextLine().trim();
            String sourcePath = (nextLine.length() > 0) ? nextLine + "\\pokerdata.txt"
                    : System.getProperty("user.home") + "/Desktop/poker-calculator/pokerdata.txt";

            final Calculator c = new Calculator(sourcePath);

            System.out.println("Enter the poker_results.txt target directory (Press Enter for default):");

            nextLine = sc.nextLine().trim();

            String targetPath = (nextLine.length() > 0) ? nextLine + "\\poker_results.txt"
                    : System.getProperty("user.home") + "/Desktop/poker-calculator/poker_results.txt";

            if (c.printResults(targetPath)) {
                System.out.println("Resultados exitosos en su Escritorio.");
            } else {
                System.err.println("Ocurrió un error al procesar la información.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

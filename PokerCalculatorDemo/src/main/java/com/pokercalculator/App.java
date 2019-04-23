package com.pokercalculator;

import java.util.Scanner;

public final class App {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        System.out.println("Enter the pokerdata.txt source directory:");

        String sourcePath = sc.nextLine() + "\\pokerdata.txt";
        final Calculator c = new Calculator(sourcePath);

        System.out.println("Enter the poker_results.txt target directory:");

        String targetPath = sc.nextLine() + "\\poker_results.txt";

        if (c.printResults(targetPath)) {
            System.out.println("Resultados exitosos en su Escritorio.");
        } else {
            System.err.println("Ocurrió un error al procesar la información.");
        }

        sc.close();
    }
}

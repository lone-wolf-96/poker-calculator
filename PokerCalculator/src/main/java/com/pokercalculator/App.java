package com.pokercalculator;

import java.util.Scanner;

public final class App {
    public static void main(String[] args) {
        try (final Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the pokerdata.txt source directory (Press Enter for default):");
            String nextLine = sc.nextLine().trim();
            String sourcePath = getFolder(nextLine, "pokerdata.txt");

            System.out.println("Enter the poker_results.txt target directory (Press Enter for default):");
            nextLine = sc.nextLine().trim();
            String targetPath = getFolder(nextLine, "poker_results.txt");

            if (new Calculator(sourcePath).printResults(targetPath)) {
                System.out.println("Successful results in your folder.");
            } else {
                System.out.println("There's been an error processing the information.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private static String getFolder(String line, String fileName) {
        return ((line.length() > 0) ? line : System.getProperty("user.dir")) + "\\" + fileName;
    }
}

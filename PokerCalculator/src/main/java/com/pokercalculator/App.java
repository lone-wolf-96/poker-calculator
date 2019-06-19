package com.pokercalculator;

import java.util.Scanner;

public final class App {
    public static void main(String[] args) {
        try (final Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the pokerdata.txt source directory (Press Enter for default):");
            String nextLine = sc.nextLine().trim();
            final String sourcePath = getFolder(nextLine) + "pokerdata.txt";

            System.out.println("Enter the target directory (Press Enter for default):");
            nextLine = sc.nextLine().trim();
            final String targetPath = getFolder(nextLine);

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

    private static String getFolder(String line) {
        if (line.length() > 0) {
            return line + (line.charAt(line.length() - 1) == '\\' ? "" : "\\");
        }
        return System.getProperty("user.dir") + "\\";
    }
}

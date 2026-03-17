package Skillswap.app;

import java.util.*;
import Skillswap.domain.*;

public class Main {
    
    private static boolean uscita = true;
    private static Map<String, Runnable> comandi = new HashMap<>();

    public static void main(String[] args) {

        comandi.put("cs", Main::createStudent);
        comandi.put("addo", Main::addOffer);
        comandi.put("addr", Main::addRequest);
        comandi.put("print", Main::printList);
        comandi.put("out", Main::exit);

        try (Scanner scanner = new Scanner(System.in)) {
            while (uscita != false) {

                System.out.print("> ");
                String input = scanner.nextLine().trim();
                String[] parts = input.split(" ");
                String cmd = parts[0];

                if (comandi.containsKey(cmd)) {
                    comandi.get(cmd).run();
                } else {
                    System.out.println("Comando sconosciuto");
                }

            }
        }

    }

    private static void createStudent() {
        System.out.println("Creo studente...");
    }

    private static void addOffer() {
        System.out.println("Aggiungo offerta...");
    }

    private static void addRequest() {
        System.out.println("Aggiungo richiesta...");
    }

    private static void printList() {
        System.out.println("Stampo liste...");
    }

    private static void exit() {
        System.out.println("Uscita dal programma...");
        uscita = false;
    }
}

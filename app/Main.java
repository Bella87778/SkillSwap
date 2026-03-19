package Skillswap.app;

import java.util.*;
import Skillswap.domain.*;

public class Main {

    private static boolean uscita = true;
    private static Map<String, Runnable> comandi = new HashMap<>();
    private static Map<String, Studente> studente = new HashMap<>();
    private static Map<String, Studente> skills = new HashMap<>();
    private static Map<String, Studente> reviews = new HashMap<>();
    private static Map<String, Studente> requests = new HashMap<>();
    private static Map<String, Studente> offer = new HashMap<>();
    private static Map<String, Studente> exchanges = new HashMap<>();

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
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Creo studente...");
            String nome = scanner.nextLine();
            String student_id = scanner.nextLine();
            String classe = scanner.nextLine();
            String email = scanner.nextLine();

            Studente s = new Studente(nome, student_id, classe, email, 0, 0);
            studente.put(s.getID(), s);
        }
        System.out.println("Studente creato!");
    }

    private static void addOffer() {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Aggiungo offerta...");
            String skill_id = scanner.nextLine();
            String offer_id = scanner.nextLine();
            String student_id = scanner.nextLine();
            String level = scanner.nextLine();
            String note = scanner.nextLine();
            String active = scanner.nextLine();

            Offer o = new Offer(offer_id, student_id, skill_id, level, note, active);
            Offer.put(o.getID(), o);
        }
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

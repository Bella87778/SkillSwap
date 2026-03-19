package SkillSwap.app;

import SkillSwap.domain.*;
import java.util.*;

public class Main {

    private static boolean uscita = true;
    private static Map<String, Runnable> comandi = new HashMap<>();
    private static Map<String, Studente> studente = new HashMap<>();
    private static Map<String, Skills> skills = new HashMap<>();
    private static Map<String, Reviews> reviews = new HashMap<>();
    private static Map<String, Requests> requests = new HashMap<>();
    private static Map<String, Offer> offer = new HashMap<>();
    private static Map<String, Exchanges> exchanges = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        comandi.put("cs", Main::createStudent);
        comandi.put("addo", Main::addOffer);
        comandi.put("addr", Main::addRequest);
        comandi.put("print", Main::printList);
        comandi.put("out", Main::exit);

        while (uscita) {

            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ");
            String cmd = parts[0];

            if (cmd.equals("exit")) {
                break;
            }
            
            if (comandi.containsKey(cmd)) {
                comandi.get(cmd).run();
            } else {
                System.out.println("Comando sconosciuto");
            }

        }
    }

    private static void createStudent() {
        System.out.println("Creo studente...");
        String nome = scanner.nextLine();
        String student_id = scanner.nextLine();
        String classe = scanner.nextLine();
        String email = scanner.nextLine();

        Studente s = new Studente(nome, student_id, classe, email, 0, 0);
        studente.put(s.getID(), s);
        System.out.println("Studente creato!");
    }

    private static void addOffer() {
        System.out.println("Aggiungo offerta...");
        String skill_id = scanner.nextLine();
        String offer_id = scanner.nextLine();
        String student_id = scanner.nextLine();
        String level = scanner.nextLine();
        String note = scanner.nextLine();
        String active = scanner.nextLine();

        Offer o = new Offer(offer_id, student_id, skill_id, level, note, active);
        offer.put(o.getID(), o);
    }

    private static void addRequest() {
        System.out.println("Aggiungo richiesta...");
        String request_id = scanner.nextLine();
        String student_id = scanner.nextLine();
        String skill_id = scanner.nextLine();
        String min_level = scanner.nextLine();
        String note = scanner.nextLine();

        Requests r = new  Requests(request_id, student_id, skill_id, min_level, note);
        requests.put(r.getID(), r);
    }

    private static void printList() {
        System.out.println("Stampo liste...");
        System.out.println("Lista Richieste:");
        for(Map.Entry<String, Requests> entry : requests.entrySet()){
            System.out.println(entry.getValue());
        }

        System.out.println("Lista Offerte:");
        for(Map.Entry<String, Offer> entry : offer.entrySet()){
            System.out.println(entry.getValue());
        }
    }

    private static void exit() {
        System.out.println("Uscita dal programma...");
        uscita = false;
    }

    public static Map<String, Runnable> getComandi() {
        return comandi;
    }

    public static void setComandi(Map<String, Runnable> comandi) {
        Main.comandi = comandi;
    }

    public static Map<String, Studente> getStudente() {
        return studente;
    }

    public static void setStudente(Map<String, Studente> studente) {
        Main.studente = studente;
    }

    public static Map<String, Skills> getSkills() {
        return skills;
    }

    public static void setSkills(Map<String, Skills> skills) {
        Main.skills = skills;
    }

    public static Map<String, Reviews> getReviews() {
        return reviews;
    }

    public static void setReviews(Map<String, Reviews> reviews) {
        Main.reviews = reviews;
    }

    public static Map<String, Requests> getRequests() {
        return requests;
    }

    public static void setRequests(Map<String, Requests> requests) {
        Main.requests = requests;
    }

    public static Map<String, Offer> getOffer() {
        return offer;
    }

    public static void setOffer(Map<String, Offer> offer) {
        Main.offer = offer;
    }

    public static Map<String, Exchanges> getExchanges() {
        return exchanges;
    }

    public static void setExchanges(Map<String, Exchanges> exchanges) {
        Main.exchanges = exchanges;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        Main.scanner = scanner;
    }
}
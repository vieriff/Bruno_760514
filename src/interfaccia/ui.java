package src.interfaccia;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import src.dao.GestioneTheKnife;

public class ui {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String fileUtentiPath = "dati/utenti.txt";
    private static final String fileRistorantiPath = "dati/ristoranti.txt";
    private static final String fileRecensioniPath = "dati/recensioni.txt";

    public static void main(String[] args) {
        boolean running = true;
        String ruolo = "";
        String usernameLoggato = "";

        while (running) {
            System.out.println("\n--- Benvenuto in TheKnife ---");
            System.out.println("1. Login Cliente");
            System.out.println("2. Login Ristoratore");
            System.out.println("3. Registrati");
            System.out.println("4. Prosegui come guest");
            System.out.println("0. Esci");
            System.out.print("Scelta: ");

            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    if (GestioneTheKnife.loginUtenteU(username, password, "cliente")) {
                        usernameLoggato = username;
                        ruolo = "cliente";
                        menuCliente(usernameLoggato);
                    } else {
                        System.out.println("Login fallito.");
                    }
                }
                case "2" -> {
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    if (GestioneTheKnife.loginUtenteR(username, password, "ristoratore")) {
                        usernameLoggato = username;
                        ruolo = "ristoratore";
                        menuRistoratore(usernameLoggato);
                    } else {
                        System.out.println("Login fallito.");
                    }
                }
                case "3" -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Cognome: ");
                    String cognome = scanner.nextLine();
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Data di nascita (facoltativa): ");
                    String dataNascita = scanner.nextLine();
                    System.out.print("Domicilio: ");
                    String domicilio = scanner.nextLine();
                    System.out.print("Ruolo (cliente/ristoratore): ");
                    String ruoloReg = scanner.nextLine();
                    if (GestioneTheKnife.registraUtente(nome, cognome, username, password, dataNascita, domicilio, ruoloReg, "")) {
                        String linea = String.join(",", nome, cognome, username, password, dataNascita, domicilio, ruoloReg, "");
                        salvaSuFile(linea, fileUtentiPath);
                    }
                }
                case "4" -> {
                    System.out.print("Inserisci zona geografica: ");
                    String zona = scanner.nextLine();
                    List<String> risultati = GestioneTheKnife.CercaRistorantiL(zona);
                    risultati.forEach(System.out::println);
                }
                case "0" -> running = false;
                default -> System.out.println("Scelta non valida");
            }
        }
    }

    private static void menuCliente(String username) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1. Aggiungi ristorante ai preferiti");
            System.out.println("2. Rimuovi ristorante dai preferiti");
            System.out.println("3. Visualizza preferiti");
            System.out.println("4. Aggiungi recensione");
            System.out.println("5. Modifica recensione");
            System.out.println("6. Elimina recensione");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");

            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Luogo: ");
                    String luogo = scanner.nextLine();
                    GestioneTheKnife.aggiungiPreferito(username, nome, luogo);
                }
                case "2" -> {
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Luogo: ");
                    String luogo = scanner.nextLine();
                    GestioneTheKnife.rimuoviPreferito(username, nome, luogo);
                }
                case "3" -> GestioneTheKnife.visualizzaPreferiti(username);
                case "4" -> {
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Luogo: ");
                    String luogo = scanner.nextLine();
                    System.out.print("Voto (1-5): ");
                    int voto = Integer.parseInt(scanner.nextLine());
                    System.out.print("Testo recensione: ");
                    String testo = scanner.nextLine();
                    if (GestioneTheKnife.aggiungiRecensione(username, nome, voto, testo)) {
                        String linea = username + "," + nome + "," + voto + "," + testo.replace(",", " ").replace(";", " ");
                        salvaSuFile(linea, fileRecensioniPath);
                    }
                }
                case "5" -> System.out.println("Modifica recensione non implementata esplicitamente.");
                case "6" -> System.out.println("Eliminazione recensione non implementata esplicitamente.");
                case "0" -> back = true;
                default -> System.out.println("Scelta non valida");
            }
        }
    }

    private static void menuRistoratore(String username) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Menu Ristoratore ---");
            System.out.println("1. Aggiungi ristorante");
            System.out.println("2. Visualizza riepilogo recensioni");
            System.out.println("3. Visualizza recensioni");
            System.out.println("4. Rispondi a recensione");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");

            switch (scanner.nextLine()) {
      case "1" -> {
    System.out.print("Nome ristorante: ");
    String nome = scanner.nextLine();
    System.out.print("Nazione: ");
    String nazione = scanner.nextLine();
    System.out.print("Città: ");
    String citta = scanner.nextLine();
    System.out.print("Indirizzo: ");
    String indirizzo = scanner.nextLine();
    System.out.print("Latitudine: ");
    int lat = Integer.parseInt(scanner.nextLine());
    System.out.print("Longitudine: ");
    int lon = Integer.parseInt(scanner.nextLine());
    System.out.print("Prezzo medio: ");
    int prezzo = Integer.parseInt(scanner.nextLine());
    System.out.print("Delivery (si/no): ");
    String delivery = scanner.nextLine();
    System.out.print("Prenotazione online (si/no): ");
    String prenotazione = scanner.nextLine();
    System.out.print("Tipo cucina: ");
    String cucina = scanner.nextLine();

    boolean boolDelivery = delivery.equalsIgnoreCase("si");
    boolean boolPrenotazione = prenotazione.equalsIgnoreCase("si");

    if (GestioneTheKnife.aggiungiRistorante(nome, username, nazione, citta, indirizzo, lat, lon, prezzo,
            boolDelivery, boolPrenotazione, cucina)) {
        String riga = "nome;" + nome + ";nazione;" + nazione + ";citta;" + citta + ";indirizzo;" + indirizzo +
                ";" + lat + ";" + lon + ";" + prezzo + ";" + boolDelivery + ";" + boolPrenotazione + ";" + cucina;
        salvaSuFile(riga, fileRistorantiPath);
    }
}

                case "2" -> GestioneTheKnife.visualizzaRiepilogo();
                case "3" -> GestioneTheKnife.visualizzaRecensioni(username);
                case "4" -> {
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Username cliente: ");
                    String utente = scanner.nextLine();
                    System.out.print("Risposta: ");
                    String risposta = scanner.nextLine();
                    GestioneTheKnife.rispondiARecensione(username, nome, utente, risposta);
                }
                case "0" -> back = true;
                default -> System.out.println("Scelta non valida");
            }
        }
    }

    public static void salvaSuFile(String contenuto, String nomeFile) {
        try (FileWriter fw = new FileWriter(nomeFile, true)) {
            fw.write(contenuto + "\n");
            System.out.println("Salvataggio completato.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio su file: " + e.getMessage());
        }
    }
}

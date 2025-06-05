package src.interfaccia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import src.dao.*;
import src.sicurezzaPassword.*;


public class MainInterface {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
         boolean running = true;

    while (running) {
        System.out.println("\n--- Benvenuto in TheKnife ---");
        System.out.println("1. Login Cliente");
        System.out.println("2. Login Ristoratore");
        System.out.println("3. Registrati");
        System.out.println("4. Cerca ristorante");
        System.out.println("0. Esci");
        System.out.print("Scelta: ");

        switch (scanner.nextLine()) {
            case "1" -> login("cliente");
            case "2" -> login("ristoratore");
            case "3" -> registrazione();
            case "4" -> cercaRistoranti(); 
            case "0" -> running = false;
            default -> System.out.println("Scelta non valida");
        }
    }
}

    private static boolean login(String ruolo) {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        String passwordCriptata = Criptazione.critta(password);

        boolean success = false;
        if ("cliente".equals(ruolo)) {
            success = GestioneTheKnife.loginUtenteU(username, passwordCriptata, ruolo);
        } else if ("ristoratore".equals(ruolo)) {
            success = GestioneTheKnife.loginUtenteR(username, passwordCriptata, ruolo);
        }

        if (success) {
            System.out.println("Login riuscito con successo!");
            if ("cliente".equals(ruolo)) {
                menuCliente(username);
            } else {
                menuRistoratore(username);
            }
        } else {
            System.out.println("Login fallito. Username o password errati.");
        }
        return success;
    }

    private static void registrazione() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine().trim();
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("Tutti i campi obbligatori devono essere compilati.");
            return;
        }

        String passwordCriptata = Criptazione.critta(password);

        System.out.print("Data di nascita (dd/MM/yyyy) - facoltativa, premi Invio per saltare: ");
        String dataNascitaInput = scanner.nextLine().trim();

        Calendar dataNascitaCal = parseDataNascita(dataNascitaInput);
        if (dataNascitaCal == null) {
            System.out.println("Registrazione annullata a causa di data non valida.");
            return;
        }

        System.out.print("Domicilio: ");
        String domicilio = scanner.nextLine().trim();
        System.out.print("Ruolo (cliente/ristoratore): ");
        String ruolo = scanner.nextLine().trim().toLowerCase();

        if (domicilio.isEmpty() || (!ruolo.equals("cliente") && !ruolo.equals("ristoratore"))) {
            System.out.println("Domicilio obbligatorio e ruolo deve essere 'cliente' o 'ristoratore'.");
            return;
        }

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataNascitaStr = outputFormat.format(dataNascitaCal.getTime());

        boolean registrato = GestioneTheKnife.registraUtente(
                nome, cognome, username, passwordCriptata,
                dataNascitaStr, domicilio, ruolo, ""
        );

        if (registrato) {
            System.out.println("Registrazione completata con successo!");
        } else {
            System.out.println("Errore durante la registrazione. Riprova.");
        }
    }

    private static Calendar parseDataNascita(String inputData) {
        if (inputData == null || inputData.trim().isEmpty()) {
            // Data "vuota": impostiamo 1 gennaio anno 0
            return new GregorianCalendar(0, 0, 1);
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                Calendar cal = Calendar.getInstance();
                cal.setTime(sdf.parse(inputData));
                return cal;
            } catch (ParseException e) {
                System.out.println("Formato data non valido. Usa dd/MM/yyyy.");
                return null;
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
            System.out.println("5. Cerca ristorante");
            System.out.println("6. Modifica recensione (non disponibile)");
            System.out.println("7. Elimina recensione (non disponibile)");
            System.out.println("0. Logout");
            System.out.print("Scelta: ");

            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Luogo: ");
                    String luogo = scanner.nextLine();
                    boolean aggiunto = GestioneTheKnife.aggiungiPreferito(username, nome, luogo);
                    if (aggiunto) System.out.println("Ristorante aggiunto ai preferiti.");
                    else System.out.println("Errore nell'aggiunta ai preferiti.");
                }
                case "2" -> {
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Luogo: ");
                    String luogo = scanner.nextLine();
                    boolean rimosso = GestioneTheKnife.rimuoviPreferito(username, nome, luogo);
                    if (rimosso) System.out.println("Ristorante rimosso dai preferiti.");
                    else System.out.println("Errore nella rimozione dai preferiti.");
                }
                case "3" -> GestioneTheKnife.visualizzaPreferiti(username);
                case "4" -> {
                    System.out.print("Nome ristorante: ");
                    String nome = scanner.nextLine();
                    System.out.print("Luogo ristorante: ");
                    String luogo = scanner.nextLine();
                    int voto = leggiNumero("Voto (1-5): ");
                    System.out.print("Testo recensione: ");
                    String testo = scanner.nextLine();

                    boolean recensioneAggiunta = GestioneTheKnife.aggiungiRecensione(
                            username, nome, luogo, String.valueOf(voto), testo
                    );
                    if (recensioneAggiunta) {
                        System.out.println("Recensione aggiunta con successo.");
                    } else {
                        System.out.println("Errore nell'aggiunta della recensione.");
                    }
                }
                case "5" -> cercaRistoranti();
                case "6", "7" -> System.out.println("Funzionalità non ancora disponibile. Torna più tardi.");
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
            System.out.println("5. Cerca ristorante");
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
                    int lat = leggiNumero("Latitudine: ");
                    int lon = leggiNumero("Longitudine: ");
                    int prezzo = leggiNumero("Prezzo medio: ");
                    System.out.print("Delivery (true/false): ");
                    boolean delivery = leggiBoolean();
                    System.out.print("Prenotazione online (true/false): ");
                    boolean prenotazione = leggiBoolean();
                    System.out.print("Tipo cucina: ");
                    String cucina = scanner.nextLine();

                    boolean aggiunto = GestioneTheKnife.aggiungiRistorante(
                            nome, username, nazione, citta, indirizzo, lat, lon, prezzo,
                            delivery, prenotazione, cucina
                    );

                    if (aggiunto) {
                        System.out.println("Ristorante aggiunto con successo.");
                    } else {
                        System.out.println("Errore nell'aggiunta del ristorante.");
                    }
                }
                case "2" -> GestioneTheKnife.visualizzaRiepilogo();
                case "3" -> GestioneTheKnife.visualizzaRecensioni(username);
                case "4" -> {
                    System.out.print("Nome ristorante: ");
                    String nomeRistorante = scanner.nextLine();
                    System.out.print("Username cliente a cui rispondere: ");
                    String usernameCliente = scanner.nextLine();
                    System.out.print("Testo risposta: ");
                    String testoRisposta = scanner.nextLine();

                    boolean rispostaInserita = GestioneTheKnife.rispondiRecensione(
                            username, nomeRistorante, usernameCliente, testoRisposta
                    );

                    if (rispostaInserita) {
                        System.out.println("Risposta inserita con successo.");
                    } else {
                        System.out.println("Errore nell'inserimento della risposta o dati non corretti.");
                    }
                }
                case "5" -> cercaRistoranti();
                case "0" -> back = true;
                default -> System.out.println("Scelta non valida");
            }
        }
    }

    private static void cercaRistoranti() {
        System.out.println("\n--- Ricerca avanzata ristoranti ---");

        System.out.print("Inserisci zona geografica (obbligatorio): ");
        String zona = scanner.nextLine().trim();
        if (zona.isEmpty()) {
            System.out.println("Zona geografica obbligatoria per la ricerca.");
            return;
        }

        System.out.print("Tipologia di cucina (facoltativo): ");
        String cucina = scanner.nextLine().trim();

        System.out.print("Prezzo minimo (facoltativo, premi Invio per saltare): ");
        Integer prezzoMin = leggiNumeroFacoltativo();

        System.out.print("Prezzo massimo (facoltativo, premi Invio per saltare): ");
        Integer prezzoMax = leggiNumeroFacoltativo();

        System.out.print("Servizio delivery richiesto? (true/false/Invio per no filtro): ");
        Boolean delivery = leggiBooleanFacoltativo();

        System.out.print("Prenotazione online richiesta? (true/false/Invio per no filtro): ");
        Boolean prenotazione = leggiBooleanFacoltativo();

        System.out.print("Valutazione media minima (stelle, 1-5, facoltativo): ");
        Double stelleMin = leggiDoubleFacoltativo();

        List<String> risultati = GestioneTheKnife.cercaRistorantiAvanzata(
                zona, cucina,
                prezzoMin, prezzoMax,
                delivery, prenotazione,
                stelleMin
        );

        if (risultati.isEmpty()) {
            System.out.println("Nessun ristorante trovato con i criteri indicati.");
        } else {
            stampaRistoranti(risultati);
        }
    }

    private static void stampaRistoranti(List<String> ristoranti) {
        System.out.println("\n--- Ristoranti trovati ---");
        for (String r : ristoranti) {
            System.out.println(r);
            System.out.println("------------------------------");
        }
    }

    private static int leggiNumero(String messaggio) {
        while (true) {
            System.out.print(messaggio);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
            }
        }
    }

    private static Integer leggiNumeroFacoltativo() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            System.out.println("Numero non valido, campo ignorato.");
            return null;
        }
    }

    private static boolean leggiBoolean() {
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true")) return true;
            if (input.equals("false")) return false;
            System.out.print("Inserisci 'true' o 'false': ");
        }
    }

    private static Boolean leggiBooleanFacoltativo() {
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.isEmpty()) return null;
        if (input.equals("true")) return true;
        if (input.equals("false")) return false;
        System.out.println("Valore booleano non valido, campo ignorato.");
        return null;
    }

    private static Double leggiDoubleFacoltativo() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException e) {
            System.out.println("Numero non valido, campo ignorato.");
            return null;
        }
    }
}
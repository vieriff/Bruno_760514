/**
 * Classe DAO per la gestione delle funzionalità principali dell'applicazione TheKnife.
 * Fornisce metodi per l'aggiunta e gestione di ristoranti, recensioni e preferiti.
 */
package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import dto.Recensione;
import dto.Ristorante;
import mapper.Mapper;
import sicurezzaPassword.Criptazione;

public class GestioneTheKnife {

    private static String fileRistorantiPath = "dati/ristoranti.txt";
    private static String fileRecensioniPath = "dati/recensioni.txt";
    private static String fileUtentiPath = "dati/utenti.txt";

    /**
     * Imposta il percorso del file dei ristoranti.
     * @param path percorso del file ristoranti
     */
    public static void setFileRistorantiPath(String path) {
        fileRistorantiPath = path;
    }

    /**
     * Imposta il percorso del file delle recensioni.
     * @param path percorso del file recensioni
     */
    public static void setFileRecensioniPath(String path) {
        fileRecensioniPath = path;
    }

    /**
     * Aggiunge un nuovo ristorante al file.
     * @param nome Nome del ristorante
     * @param usernameRistoratore Username del ristoratore
     * @param nazione Nazione
     * @param citta Città
     * @param indirizzo Indirizzo
     * @param latitudine Latitudine
     * @param longitudine Longitudine
     * @param prezzo Prezzo medio
     * @param disponibilitaDelivery Disponibilità per la consegna
     * @param disponibilitaPrenotazione Disponibilità alla prenotazione
     * @param tipoCucina Tipo di cucina
     * @return true se aggiunto correttamente, false altrimenti
     */
    public static boolean aggiungiRistorante(String nome, String usernameRistoratore, String nazione, String citta,
                                             String indirizzo, int latitudine, int longitudine, int prezzo,
                                             boolean disponibilitaDelivery, boolean disponibilitaPrenotazione,
                                             String tipoCucina) {

        if (nome == null || nome.isEmpty() ||
            usernameRistoratore == null || usernameRistoratore.isEmpty() ||
            nazione == null || nazione.isEmpty() ||
            citta == null || citta.isEmpty() ||
            indirizzo == null || indirizzo.isEmpty() ||
            tipoCucina == null || tipoCucina.isEmpty()) {
            return false;
        }

        if (prezzo <= 0) {
            return false;
        }

        Ristorante nuovoRistorante = new Ristorante(
            nome, usernameRistoratore, nazione, citta, indirizzo,
            latitudine, longitudine, prezzo, disponibilitaDelivery,
            disponibilitaPrenotazione, tipoCucina
        );

        String riga = Mapper.mapStrRistorante(nuovoRistorante);

        try {
            File file = new File(fileRistorantiPath);
            boolean addNewline = false;

            if (file.exists() && file.length() > 0) {
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                raf.seek(file.length() - 1);
                int lastByte = raf.read();
                raf.close();
                if (lastByte != '\n') {
                    addNewline = true;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            if (addNewline) writer.newLine();
            writer.write(riga);
            writer.newLine();
            writer.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

/**
 * Visualizza un riepilogo dei ristoranti presenti, mostrando per ciascuno
 * il numero di recensioni ricevute e la media delle valutazioni.
 * I dati vengono letti dai file delle recensioni e dei ristoranti.
 */
public void visualizzaRiepilogo() {

    LinkedList<String> righeRecensioni = new LinkedList<>();

    try (BufferedReader recReader = new BufferedReader(new FileReader(fileRecensioniPath))) {
        String linea;
        while ((linea = recReader.readLine()) != null) {
            righeRecensioni.add(linea);
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura delle recensioni");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(fileRistorantiPath))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] campi = linea.split(",", -1);
            String nomeRistorante = campi[0];

            int sommaVoti = 0;
            int conteggio = 0;

            for (String recLine : righeRecensioni) {
                String[] recCampi = recLine.split(",", -1);
                if (recCampi.length > 2 && recCampi[1].equalsIgnoreCase(nomeRistorante)) {
                    try {
                        sommaVoti += Integer.parseInt(recCampi[2]);
                        conteggio++;
                    } catch (NumberFormatException e) {
                        // Ignora voti non validi
                    }
                }
            }

            System.out.println("Ristorante: " + nomeRistorante);
            if (conteggio > 0) {
                double media = (double) sommaVoti / conteggio;
                System.out.println("Numero recensioni: " + conteggio);
                System.out.printf("Media voti: %.2f%n", media);
            } else {
                System.out.println("Nessuna recensione disponibile.");
            }
            System.out.println("-----------------------------");
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file ristoranti");
    }
}

/**
 * Visualizza tutte le recensioni relative ai ristoranti gestiti da un dato utente.
 * Ogni recensione mostra il nome del ristorante, l'utente recensore, la valutazione,
 * il testo della recensione e la risposta del ristoratore (se presente).
 *
 * @param usernameLoggato l'username del ristoratore attualmente loggato
 */
public static void visualizzaRecensioni(String usernameLoggato) {

    if (fileRistorantiPath == null || fileRecensioniPath == null) {
        System.err.println("Errore: i path dei file non sono stati configurati.");
        return;
    }

    List<String> ristorantiGestiti = new LinkedList<>();

    try (BufferedReader brRistoranti = new BufferedReader(new FileReader(fileRistorantiPath))) {
        String linea;
        while ((linea = brRistoranti.readLine()) != null) {
            String[] campi = linea.split(",", -1);
            if (campi.length > 1 && campi[1].equals(usernameLoggato)) {
                ristorantiGestiti.add(campi[0]);
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file dei ristoranti");
        return;
    }

    if (ristorantiGestiti.isEmpty()) {
        System.out.println("Non gestisci alcun ristorante.");
        return;
    }

    boolean trovate = false;

    try (BufferedReader brRecensioni = new BufferedReader(new FileReader(fileRecensioniPath))) {
        String linea;
        while ((linea = brRecensioni.readLine()) != null) {
            String[] campi = linea.split(",", -1);
            if (campi.length >= 5 && ristorantiGestiti.contains(campi[1])) {
                trovate = true;
                System.out.println("== Recensione per: " + campi[1] + " ==");
                System.out.println("Utente: " + campi[0]);
                System.out.println("Valutazione: " + campi[2] + "/5");
                System.out.println("Testo: " + campi[3]);
                System.out.println("Risposta: " + (campi[4].isEmpty() ? "Nessuna" : campi[4]));
                System.out.println("----------------------------------------");
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file delle recensioni");
    }

    if (!trovate) {
        System.out.println("Non ci sono recensioni per i tuoi ristoranti.");
    }
}

/**
 * Permette a un ristoratore di rispondere a una recensione.
 * 
 * @param usernameLoggato username del ristoratore loggato
 * @param nomeRistorante nome del ristorante
 * @param usernameCliente username del cliente che ha scritto la recensione
 * @param risposta testo della risposta
 * @return true se risposta salvata correttamente, false altrimenti
 */
public static boolean rispondiARecensione(String usernameLoggato, String nomeRistorante, String usernameCliente,
        String risposta) {
    if (fileRecensioniPath == null || fileRistorantiPath == null) {
        return false;
    }

    // Verifica che il ristorante appartenga al ristoratore
    boolean ristoranteTrovato = false;
    try (BufferedReader br = new BufferedReader(new FileReader(fileRistorantiPath))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);
            if (campi[0].equals(nomeRistorante) && campi[1].equals(usernameLoggato)) {
                ristoranteTrovato = true;
                break;
            }
        }
    } catch (IOException e) {
        return false;
    }

    if (!ristoranteTrovato) {
        return false;
    }

    // Leggi le recensioni e aggiorna solo quella giusta
    LinkedList<String> righeAggiornate = new LinkedList<>();
    boolean recensioneAggiornata = false;

    try (BufferedReader br = new BufferedReader(new FileReader(fileRecensioniPath))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);
            if (campi[0].equals(usernameCliente) && campi[1].equals(nomeRistorante)) {
                if (campi[4] != null && !campi[4].trim().isEmpty()) {
                    // Recensione gia  risposta
                    return false;
                }
                campi[4] = risposta;
                String nuovaLinea = String.join(",", campi);
                righeAggiornate.add(nuovaLinea);
                recensioneAggiornata = true;
            } else {
                righeAggiornate.add(linea);
            }
        }
    } catch (IOException e) {
        return false;
    }

    if (!recensioneAggiornata) {
        return false;
    }

    // Sovrascrivi il file recensioni
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRecensioniPath))) {
        for (String riga : righeAggiornate) {
            writer.write(riga);
            writer.newLine();
        }
    } catch (IOException e) {
        return false;
    }

    return true;
}

/**
 * Aggiunge un ristorante ai preferiti dell'utente.
 * 
 * @param usernameCliente username dell'utente che aggiunge il preferito
 * @param nomeRistorante nome del ristorante da aggiungere ai preferiti
 * @param luogoRistorante luogo del ristorante da aggiungere ai preferiti
 * @return true se aggiunto con successo, false altrimenti
 */
public static boolean aggiungiPreferito(String usernameCliente, String nomeRistorante, String luogoRistorante) {    //aggiunge un ristorante al campo preferiti dell'utente che ha effettuato il login
    String fileUtentiPath = "dati/utenti.txt";
    List<String> utentiAggiornati = new ArrayList<>();
    boolean aggiornato = false;

    if (usernameCliente == null || nomeRistorante == null || luogoRistorante == null ||
        usernameCliente.isEmpty() || nomeRistorante.isEmpty() || luogoRistorante.isEmpty()) {   //se uno di questi campi non esiste il codice non pua² essere eseguito
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(fileUtentiPath))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);

            if (campi.length < 8) {     //questa parte permette di evitare la IndexOutOfBoundsException
                utentiAggiornati.add(linea);
                continue;
            }

            if (campi[2].equalsIgnoreCase(usernameCliente)) {
                String preferiti = campi[7].trim();
                String nuovoPreferito = nomeRistorante + ";" + luogoRistorante;

                boolean giaPresente = false;
                if (!preferiti.isEmpty()) {
                    String[] ristoranti = preferiti.split("\\.");
                    for (String ristorante : ristoranti) {      //se il ristorante a¨ gia  tra i preferiti non viene inserito nuovamente
                        if (ristorante.trim().equalsIgnoreCase(nuovoPreferito)) {
                            giaPresente = true;
                            break;
                        }
                    }
                }

                if (!giaPresente) {     //se tutto a¨ andato a buon fine aggiungo il nuovo ristorante preferito
                    if (preferiti.isEmpty()) {
                        preferiti = nuovoPreferito;
                    } else {
                        preferiti += "." + nuovoPreferito;
                    }
                    campi[7] = preferiti;
                    aggiornato = true;
                }

                String nuovaLinea = String.join(",", campi);
                utentiAggiornati.add(nuovaLinea);
            } else {
                utentiAggiornati.add(linea);
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file utenti: " + e.getMessage());
        return false;
    }

    if (!aggiornato) {
        System.out.println("Utente non trovato o ristorante gia  nei preferiti.");
        return false;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileUtentiPath))) {
        for (String linea : utentiAggiornati) {
            writer.write(linea);
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file utenti: " + e.getMessage());
        return false;
    }

    return true;       //se tutto a¨ andato a buon fine ritorno true
}
/**
 * Rimuove un ristorante dai preferiti dell'utente.
 * 
 * @param usernameCliente username dell'utente che rimuove il preferito
 * @param nomeRistorante nome del ristorante da rimuovere dai preferiti
 * @param luogoRistorante luogo del ristorante da rimuovere dai preferiti
 * @return true se rimosso con successo, false altrimenti
 */
public static boolean rimuoviPreferito(String usernameCliente, String nomeRistorante, String luogoRistorante) {     //rimuove un ristorante al campo preferiti dell'utente che ha effettuato il login
    String fileUtentiPath = "dati/utenti.txt";
    List<String> utentiAggiornati = new ArrayList<>();
    boolean aggiornato = false;

    if (usernameCliente == null || nomeRistorante == null || luogoRistorante == null ||
        usernameCliente.isEmpty() || nomeRistorante.isEmpty() || luogoRistorante.isEmpty()) {       //se uno di questi campi non esiste il codice non pua² essere eseguito
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(fileUtentiPath))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);

            if (campi.length < 8) {     //questa parte permette di evitare la IndexOutOfBoundsException
                utentiAggiornati.add(linea);
                continue;
            }

            if (campi[2].equalsIgnoreCase(usernameCliente)) {
                String preferiti = campi[7].trim();
                String daRimuovere = nomeRistorante + ";" + luogoRistorante;

                if (!preferiti.isEmpty()) {     //se il campo preferiti a¨ vuoto allora non pua² esserci un ristorante da rimuovere
                    String[] ristoranti = preferiti.split("\\.");
                    List<String> preferitiAggiornati = new ArrayList<>();

                    for (String ristorante : ristoranti) {
                        if (!ristorante.trim().equalsIgnoreCase(daRimuovere)) {
                            preferitiAggiornati.add(ristorante);
                        } else {
                            aggiornato = true; //in questo caso il ristorante a¨ stato trovato e rimosso
                        }
                    }

                    campi[7] = String.join(".", preferitiAggiornati);
                }

                String nuovaLinea = String.join(",", campi);
                utentiAggiornati.add(nuovaLinea);
            } else {
                utentiAggiornati.add(linea);
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file utenti: " + e.getMessage());
        return false;
    }

    if (!aggiornato) {
        System.out.println("Utente non trovato o ristorante non presente nei preferiti.");
        return false;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileUtentiPath))) {
        for (String linea : utentiAggiornati) {
            writer.write(linea);
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file utenti: " + e.getMessage());
        return false;
    }

    return true;
}

/**
 * Visualizza tutti i ristoranti preferiti dell'utente specificato.
 * 
 * @param usernameCliente username dell'utente
 */
public static void visualizzaPreferiti(String usernameCliente) {        //permetti di visualizzare tutti i preferiti dell'utente che ha effettuato l'accesso
    String fileUtentiPath = "dati/utenti.txt";

    if (usernameCliente == null || usernameCliente.isEmpty()) {     //se questo campo a¨ vuoto o a¨ null non si pua² eseguire il codice
        System.out.println("Username non valido.");
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(fileUtentiPath))) {
        String linea;
        boolean trovato = false;

        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);

            if (campi.length < 8) continue;

            if (campi[2].equalsIgnoreCase(usernameCliente)) {
                trovato = true;
                String preferiti = campi[7].trim();

                if (preferiti.isEmpty()) {      //se preferiti a¨ vuoto allora stampa che non ci sono preferiti
                    System.out.println("Nessun ristorante preferito trovato.");
                } else {
                    String[] ristoranti = preferiti.split("\\.");
                    System.out.println("Ristoranti preferiti di " + usernameCliente + ":");
                    for (String ristorante : ristoranti) {
                        String[] dettagli = ristorante.split(";");
                        if (dettagli.length == 2) {     //non dovrebbe succedere ma, se c'a¨ solo il nome del ristorante senza il luogo, allora stampa solo il nome
                            System.out.println("- " + dettagli[0].trim() + " (" + dettagli[1].trim() + ")");
                        } else {
                            System.out.println("- " + ristorante.trim());
                        }
                    }
                }
                break;
            }
        }

        if (!trovato) {
            System.out.println("Utente non trovato.");
        }

    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file utenti: " + e.getMessage());
    }
}

/**
 * Aggiunge una recensione per un ristorante da parte di un utente.
 * 
 * @param username         username dell'utente che scrive la recensione
 * @param nomeRistorante   nome del ristorante recensito
 * @param luogoRistorante  luogo del ristorante recensito
 * @param valutazione      valutazione data al ristorante (es. punteggio)
 * @param testoRecensione  testo della recensione
 * @return true se la recensione è stata aggiunta con successo, false altrimenti
 */
public static boolean aggiungiRecensione(String username, String nomeRistorante, String luogoRistorante, String valutazione, String testoRecensione) {  //permette di aggiungere una recensione ad un ristorante
    String fileRecensioniPath = "dati/recensioni.txt";

    if (username == null || nomeRistorante == null || luogoRistorante == null || 
        valutazione == null || testoRecensione == null ||                                   //se uno di questi campi non è presente non è possibile andare avanti
        username.isEmpty() || nomeRistorante.isEmpty() || luogoRistorante.isEmpty() ||
        valutazione.isEmpty() || testoRecensione.isEmpty()) {
        System.out.println("Campi non validi.");
        return false;
    }

    String ristorante = nomeRistorante + ";" + luogoRistorante;
    String nuovaRecensione = String.join(",", username, ristorante, valutazione, testoRecensione, "Nrisposta");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRecensioniPath, true))) {
        writer.write(nuovaRecensione);      //qui scrive la recensione nel file
        writer.newLine();
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file recensioni: " + e.getMessage());
        return false;
    }

    return true;
}
    
    
/**
 * Esegue il login verificando username e password cifrata.
 *
 * @param username username inserito dall'utente
 * @param password password inserita in chiaro dall'utente
 * @param ruolo    ruolo dell'utente (es. cliente, ristoratore)
 * @return true se le credenziali sono corrette, false altrimenti
 */
public static boolean loginUtenteU(String username, String password, String ruolo) {

    if (username == null || password == null || ruolo == null || username.isEmpty() || password.isEmpty() || ruolo.isEmpty()) {
        System.out.println("Username o password vuoti.");
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(fileUtentiPath))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);

            String usernameFile = campi[2].trim();
            String passwordCifrataFile = campi[3].trim();
            String ruoloFile = campi[6].trim();

            if (usernameFile.equals(username)) {
                if (password.equals(passwordCifrataFile)) {
                    if(ruolo.equals(ruoloFile)){
                        return true;
                    }
                } else {
                    System.out.println("Password errata.");
                    return false;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file utenti: " + e.getMessage());
        return false;
    }

    System.out.println("Username non trovato.");
    return false;
}

/**
 * Esegue il login di un utente ristoratore verificando username, password e ruolo.
 *
 * @param username username inserito dall'utente
 * @param password password inserita in chiaro dall'utente
 * @param ruolo    ruolo dell'utente (es. "ristoratore")
 * @return true se le credenziali sono corrette, false altrimenti
 */
public static boolean loginUtenteR(String username, String password, String ruolo) {

    if (username == null || password == null || ruolo == null || username.isEmpty() || password.isEmpty() || ruolo.isEmpty()) {
        System.out.println("Username o password vuoti.");
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(fileUtentiPath))) {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);

            String usernameFile = campi[2].trim();
            String passwordFile = campi[3].trim();
            String ruoloFile = campi[6].trim();

            if (usernameFile.equals(username)) {
                if (passwordFile.equals(password)) {
                    if (ruolo.equals(ruoloFile)) {
                        return true;
                    }
                } else {
                    System.out.println("Password errata.");
                    return false;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file utenti: " + e.getMessage());
        return false;
    }

    System.out.println("Username non trovato.");
    return false;
}

/**
 * Registra un nuovo utente aggiungendolo al file utenti.txt.
 * 
 * @param nome        nome dell'utente
 * @param cognome     cognome dell'utente
 * @param username    username univoco scelto dall'utente
 * @param password    password in chiaro dell'utente
 * @param dataNascita data di nascita dell'utente
 * @param domicilio   domicilio dell'utente
 * @param ruolo       ruolo dell'utente (es. cliente, ristoratore)
 * @param preferiti   lista preferiti dell'utente, formattata come stringa
 * @return true se l'utente è stato registrato con successo, false se l'username è già presente o si verifica un errore
 */
public static boolean registraUtente(String nome, String cognome, String username, String password, String dataNascita, String domicilio, String ruolo, String preferiti) {
    File file = new File("dati/utenti.txt");

    try {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] campi = line.split(",");
            if (campi.length > 2 && campi[2].equals(username)) {
                reader.close();
                return false;
            }
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String riga = nome + "," + cognome + "," + username + "," + password + "," + dataNascita + "," + domicilio + "," + ruolo + "," + preferiti;
        writer.write(riga);
        writer.newLine();
        writer.close();
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

/**
 * Cerca i ristoranti il cui nome contiene la stringa specificata.
 * 
 * @param nomeCercato la stringa da cercare nel nome del ristorante
 * @return una lista di stringhe, ciascuna rappresentante una riga del file dei ristoranti
 *         che corrisponde al criterio di ricerca; lista vuota se nessun risultato o errore
 */
public static List<String> CercaRistorantiN(String nomeCercato) {
    List<String> risultati = new ArrayList<>();
    File file = new File("dati/ristoranti.txt");

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(";");
            if (campi.length >= 11 && campi[0].toLowerCase().contains(nomeCercato.toLowerCase())) {
                risultati.add(linea);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return risultati;
}

/**
 * Cerca i ristoranti in base alla zona (nazione o città).
 * 
 * @param zona la zona da cercare (può essere nazione o città)
 * @return una lista di stringhe che rappresentano le righe dei ristoranti
 *         che si trovano nella zona specificata; lista vuota se nessun risultato o errore
 */
public static List<String> CercaRistorantiL(String zona) {
    List<String> risultati = new ArrayList<>();
    zona = zona.toLowerCase();

    try (BufferedReader reader = new BufferedReader(new FileReader("dati/ristoranti.txt"))) {
        String riga;
        while ((riga = reader.readLine()) != null) {
            String[] campi = riga.split(";");
            if (campi.length >= 11) {
                String nazione = campi[2].toLowerCase();
                String citta = campi[3].toLowerCase();

                if (nazione.contains(zona) || citta.contains(zona)) {
                    risultati.add(riga);
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return risultati;
}

/**
 * Aggiunge una recensione per un ristorante da parte di un utente,
 * se non esiste già una recensione dello stesso utente per quel ristorante.
 *
 * @param username      username dell'utente che scrive la recensione
 * @param nomeRistorante nome del ristorante da recensire
 * @param voto          punteggio assegnato al ristorante (intero)
 * @param testo         testo della recensione (le virgole verranno sostituite con spazi)
 * @return true se la recensione è stata aggiunta con successo, false se
 *         esiste già una recensione per quell'utente e ristorante o in caso di errore
 */
public static boolean aggiungiRecensione(String username, String nomeRistorante, int voto, String testo) {
    File file = new File("dati/recensioni.txt");
    try {
        if (!file.exists()) file.createNewFile();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                if (campi.length >= 2 && campi[0].equals(username) && campi[1].equalsIgnoreCase(nomeRistorante)) {
                    return false;
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            String nuovaRiga = username + "," + nomeRistorante + "," + voto + "," + testo.replace(",", " ") + ",";
            bw.write(nuovaRiga);
            bw.newLine();
        }

        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}


}

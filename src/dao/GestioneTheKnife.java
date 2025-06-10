/*
 * Sebastiano Svezia 760462 VA
 * Davide Bruno 760514 VA 
 * Fancesco Vieri 761195 VA
 * Leonardo Bighetti 760015 VA
 */

package src.dao;
/**
 * Classe DAO per la gestione delle funzionalita principali dell'applicazione TheKnife.
 * Fornisce metodi per l'aggiunta e gestione di ristoranti, recensioni, e preferiti.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import src.dto.Ristorante;
import src.mapper.Mapper;

public class GestioneTheKnife {

	private static String fileRistorantiPath = "dati/ristoranti.txt";
	private static String fileRecensioniPath = "dati/recensioni.txt";
	private static String fileUtentiPath = "dati/utenti.txt";

	
    /**
     * Aggiunge un nuovo ristorante al file.
     * @return true se aggiunto correttamente, false altrimenti
     */
public static boolean aggiungiRistorante(String nome, String usernameRistoratore, String nazione, String citta, String indirizzo, int latitudine,
    int longitudine, int prezzo, boolean disponibilita_delivery, boolean disponibilita_prenotazione,
    String tipo_Cucina) {

    if (nome == null || nome.isEmpty() ||
        usernameRistoratore == null || usernameRistoratore.isEmpty() ||
        nazione == null || nazione.isEmpty() ||
        citta == null || citta.isEmpty() ||
        indirizzo == null || indirizzo.isEmpty() ||
        tipo_Cucina == null || tipo_Cucina.isEmpty())
        return false;

    if (prezzo <= 0)
        return false;

    
    try (BufferedReader reader = new BufferedReader(new FileReader(fileRistorantiPath))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] campi = linea.split(";", -1);
            if (campi.length >= 5) {
                String nomeEsistente = campi[0].trim();
                String indirizzoEsistente = campi[4].trim();
                if (nomeEsistente.equalsIgnoreCase(nome.trim()) && indirizzoEsistente.equalsIgnoreCase(indirizzo.trim())) {
                    System.out.println("Errore: esiste gia un ristorante con lo stesso nome e indirizzo.");
                    return false;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file ristoranti: " + e.getMessage());
        return false;
    }

    
    Ristorante nuovoRistorante = new Ristorante(nome, usernameRistoratore, nazione, citta, indirizzo, latitudine,
            longitudine, prezzo, disponibilita_delivery, disponibilita_prenotazione, tipo_Cucina);

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
     * Visualizza un riepilogo dei ristoranti con le medie votazioni delle recensioni.
     */
    public static void visualizzaRiepilogo(String usernameRistoratore) {
    if (fileRistorantiPath == null || fileRecensioniPath == null) {
        System.err.println("Errore: file non configurati.");
        return;
    }

    try (
        BufferedReader brRistoranti = new BufferedReader(new FileReader(fileRistorantiPath));
        BufferedReader brRecensioni = new BufferedReader(new FileReader(fileRecensioniPath))
    ) {
        // Mappa ristorante -> citta
        Map<String, String> ristorantiDelRistoratore = new HashMap<>();
        String lineaRistorante;
        while ((lineaRistorante = brRistoranti.readLine()) != null) {
            String[] campi = lineaRistorante.split(";", -1); // <-- USA ; come separatore corretto
            if (campi.length >= 4) {
                String nomeRistorante = campi[0].trim();
                String proprietario = campi[1].trim();

                if (proprietario.equalsIgnoreCase(usernameRistoratore.trim())) {
                    String citta = campi[3].trim();
                    ristorantiDelRistoratore.put(nomeRistorante, citta);
                }
            }
        }

        if (ristorantiDelRistoratore.isEmpty()) {
            System.out.println("Non hai ancora registrato ristoranti.");
            return;
        }

        // Inizializza riepiloghi
        Map<String, Integer> sommaStelle = new HashMap<>();
        Map<String, Integer> conteggioRecensioni = new HashMap<>();

        String lineaRecensione;
        while ((lineaRecensione = brRecensioni.readLine()) != null) {
            String[] campi = lineaRecensione.split(",", -1);
            if (campi.length >= 3) {
                String nomeRistRec = campi[1].split(";")[0].trim(); // nome del ristorante
                int stelle = 0;
                try {
                    stelle = Integer.parseInt(campi[2].trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                if (ristorantiDelRistoratore.containsKey(nomeRistRec)) {
                    sommaStelle.put(nomeRistRec, sommaStelle.getOrDefault(nomeRistRec, 0) + stelle);
                    conteggioRecensioni.put(nomeRistRec, conteggioRecensioni.getOrDefault(nomeRistRec, 0) + 1);
                }
            }
        }

        for (String nomeRist : ristorantiDelRistoratore.keySet()) {
            int totale = sommaStelle.getOrDefault(nomeRist, 0);
            int count = conteggioRecensioni.getOrDefault(nomeRist, 0);
            double media = (count == 0) ? 0 : (double) totale / count;

            System.out.println(nomeRist + " - " + ristorantiDelRistoratore.get(nomeRist));
            System.out.println("Media valutazioni: " + String.format("%.2f", media) + " su " + count + " recensioni");
            System.out.println("------------------------------------");
        }

    } catch (IOException e) {
        System.err.println("Errore durante la lettura dei file: " + e.getMessage());
    }
}

    /**
     * Visualizza tutte le recensioni relative ai ristoranti gestiti da un dato utente.
     * @param usernameLoggato username del ristoratore loggato
     */
public static void visualizzaRecensioniPerRistorante(String nomeRistorante) {
    if (fileRecensioniPath == null) {
        System.err.println("Errore: path file recensioni non configurato.");
        return;
    }

    boolean trovate = false;
    int totaleStelle = 0;
    int numeroRecensioni = 0;

    try (BufferedReader brRecensioni = new BufferedReader(new FileReader(fileRecensioniPath))) {
        String linea;
        while ((linea = brRecensioni.readLine()) != null) {
            String[] campi = linea.split(",", -1);
            if (campi.length >= 4) {
                String nomeRistoranteRecensione = campi[1].split(";")[0].trim(); // prendi solo prima parte
                if (nomeRistoranteRecensione.equalsIgnoreCase(nomeRistorante.trim())) {
                    trovate = true;
                    numeroRecensioni++;
                    try {
                        totaleStelle += Integer.parseInt(campi[2]);
                    } catch (NumberFormatException e) {
                        // ignora voto non valido
                    }

                    System.out.println("== Recensione per: " + campi[1] + " ==");
                    System.out.println("Utente: " + campi[0]);
                    System.out.println("Valutazione: " + campi[2] + "/5");
                    System.out.println("Testo: " + campi[3]);
                    String risposta = campi.length >= 5 && campi[4] != null && !campi[4].trim().isEmpty() && !campi[4].trim().equalsIgnoreCase("null")
                                      ? campi[4] : "Nessuna";
                    System.out.println("Risposta: " + risposta);
                    System.out.println("----------------------------------------");
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file delle recensioni");
    }

    if (!trovate) {
        System.out.println("Nessuna recensione per il ristorante: " + nomeRistorante);
    } else {
        double media = numeroRecensioni > 0 ? (double) totaleStelle / numeroRecensioni : 0;
        System.out.println("Numero recensioni: " + numeroRecensioni);
        System.out.printf("Media stelle: %.2f\n", media);
    }
}

/**
 * Visualizza tutte le recensioni relative ai ristoranti gestiti da un determinato ristoratore.
 * Recupera la lista dei ristoranti associati all'username, legge le recensioni dal file e calcola la media delle valutazioni.
 * Se il ristoratore non ha ristoranti registrati, stampa un messaggio di avviso.
 *
 * @param usernameLoggato L'username del ristoratore per cui recuperare le recensioni.
 */
public static void visualizzaRecensioniPerRistoratore(String usernameLoggato) {

    if (fileRistorantiPath == null || fileRecensioniPath == null) {
        System.err.println("Errore: i path dei file non sono stati configurati.");
        return;
    }

    List<String> ristorantiGestiti = new LinkedList<>();

    try (BufferedReader brRistoranti = new BufferedReader(new FileReader(fileRistorantiPath))) {
        String linea;
        while ((linea = brRistoranti.readLine()) != null) {
            String[] campi = linea.split(";", -1);
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

    for (String ristorante : ristorantiGestiti) {
        int numeroRecensioni = 0;
        int totaleStelle = 0;
        boolean trovate = false;

        System.out.println("\nRecensioni per ristorante: " + ristorante);

        try (BufferedReader brRecensioni = new BufferedReader(new FileReader(fileRecensioniPath))) {
            String linea;
            while ((linea = brRecensioni.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                if (campi.length >= 4) {
                    String nomeRistoranteRecensione = campi[1].split(";")[0].trim();
                    if (nomeRistoranteRecensione.equalsIgnoreCase(ristorante.trim())) {
                        trovate = true;
                        numeroRecensioni++;
                        try {
                            totaleStelle += Integer.parseInt(campi[2]);
                        } catch (NumberFormatException e) {
                            // ignora voto non valido
                        }
                        System.out.println("Utente: " + campi[0]);
                        System.out.println("Valutazione: " + campi[2] + "/5");
                        System.out.println("Testo: " + campi[3]);
                        String risposta = campi.length >= 5 && campi[4] != null && !campi[4].trim().isEmpty() && !campi[4].trim().equalsIgnoreCase("null")
                                          ? campi[4] : "Nessuna";
                        System.out.println("Risposta: " + risposta);
                        System.out.println("----------------------------------------");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file delle recensioni");
        }

        if (!trovate) {
            System.out.println("Non ci sono recensioni per questo ristorante.");
        } else {
            double media = numeroRecensioni > 0 ? (double) totaleStelle / numeroRecensioni : 0;
            System.out.println("Numero recensioni: " + numeroRecensioni);
            System.out.printf("Media stelle: %.2f\n", media);
        }
    }
}


    /**
     * Permette a un ristoratore di rispondere a una recensione.
     * @return true se risposta salvata correttamente, false altrimenti
     */
public static boolean rispondiRecensione(String usernameLoggato, String nomeRistorante, String usernameCliente, String risposta) {

    boolean ristoranteTrovato = false;

    try (BufferedReader br = new BufferedReader(new FileReader(fileRistorantiPath))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(";", -1);
            if (campi.length >= 2) {
                String nomeRis = campi[0].trim();
                String usernameProprietario = campi[1].trim();
                if (nomeRis.equalsIgnoreCase(nomeRistorante.trim()) && usernameProprietario.equalsIgnoreCase(usernameLoggato.trim())) {
                    ristoranteTrovato = true;
                    break;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file ristoranti.");
        return false;
    }

    if (!ristoranteTrovato) {
        System.err.println("Errore: ristorante non trovato o utente non autorizzato.");
        return false;
    }

    List<String> recensioniAggiornate = new ArrayList<>();
    boolean rispostaAggiunta = false;

    try (BufferedReader br = new BufferedReader(new FileReader(fileRecensioniPath))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);
            if (campi.length == 5) {
                String user = campi[0].trim();
                String[] nomeELuogo = campi[1].split(";", 2);
                if (nomeELuogo.length == 2) {
                    String nomeRis = nomeELuogo[0].trim();
                    if (user.equalsIgnoreCase(usernameCliente.trim()) && nomeRis.equalsIgnoreCase(nomeRistorante.trim())) {
                        String rispostaEsistente = campi[4].trim();
                        if (rispostaEsistente.equalsIgnoreCase("Nrisposta") || rispostaEsistente.isEmpty()) {
                            campi[4] = risposta;
                            rispostaAggiunta = true;
                        } else {
                            System.err.println("Errore: questa recensione ha gia una risposta. Non è possibile aggiungerne un'altra.");
                            return false;
                        }
                        String nuovaLinea = String.join(",", campi);
                        recensioniAggiornate.add(nuovaLinea);
                        continue;
                    }
                }
            }
            recensioniAggiornate.add(linea);
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file recensioni.");
        return false;
    }

    if (!rispostaAggiunta) {
        System.err.println("Errore: recensione specificata non trovata.");
        return false;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileRecensioniPath, false))) {
        for (String riga : recensioniAggiornate) {
            bw.write(riga);
            bw.newLine();
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file recensioni.");
        return false;
    }

    return true;
}

    /**
     * Aggiunge un ristorante ai preferiti dell'utente.
     * @return true se aggiunto con successo, false altrimenti
     */
    public static boolean aggiungiPreferito(String usernameCliente, String nomeRistorante, String luogoRistorante) {    //aggiunge un ristorante al campo preferiti dell'utente che ha effettuato il login

        List<String> utentiAggiornati = new ArrayList<>();
        boolean aggiornato = false;

        if (usernameCliente == null || nomeRistorante == null || luogoRistorante == null ||
            usernameCliente.isEmpty() || nomeRistorante.isEmpty() || luogoRistorante.isEmpty()) {   //se uno di questi campi non esiste il codice non può essere eseguito
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
                        for (String ristorante : ristoranti) {      //se il ristorante è gia tra i preferiti non viene inserito nuovamente
                            if (ristorante.trim().equalsIgnoreCase(nuovoPreferito)) {
                                giaPresente = true;
                                break;
                            }
                        }
                    }

                    if (!giaPresente) {     //se tutto è andato a buon fine aggiungo il nuovo ristorante preferito
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
            System.out.println("Utente non trovato o ristorante gia nei preferiti.");
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

        return true;       //se tutto è andato a buon fine ritorno true
    }

    /**
     * Rimuove un ristorante dai preferiti dell'utente.
     * @return true se rimosso con successo, false altrimenti
     */
    public static boolean rimuoviPreferito(String usernameCliente, String nomeRistorante, String luogoRistorante) {     //rimuove un ristorante al campo preferiti dell'utente che ha effettuato il login

        List<String> utentiAggiornati = new ArrayList<>();
        boolean aggiornato = false;

        if (usernameCliente == null || nomeRistorante == null || luogoRistorante == null ||
            usernameCliente.isEmpty() || nomeRistorante.isEmpty() || luogoRistorante.isEmpty()) {       //se uno di questi campi non esiste il codice non può essere eseguito
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

                    if (!preferiti.isEmpty()) {     //se il campo preferiti è vuoto allora non può esserci un ristorante da rimuovere
                        String[] ristoranti = preferiti.split("\\.");
                        List<String> preferitiAggiornati = new ArrayList<>();

                        for (String ristorante : ristoranti) {
                            if (!ristorante.trim().equalsIgnoreCase(daRimuovere)) {
                                preferitiAggiornati.add(ristorante);
                            } else {
                                aggiornato = true; //in questo caso il ristorante è stato trovato e rimosso
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
     * @param usernameCliente username dell'utente
     */
    public static void visualizzaPreferiti(String usernameCliente) {        //permetti di visualizzare tutti i preferiti dell'utente che ha effettuato l'accesso
    
        if (usernameCliente == null || usernameCliente.isEmpty()) {     //se questo campo è vuoto o è null non si può eseguire il codice
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
    
                    if (preferiti.isEmpty()) {      //se preferiti è vuoto allora stampa che non ci sono preferiti
                        System.out.println("Nessun ristorante preferito trovato.");
                    } else {
                        String[] ristoranti = preferiti.split("\\.");
                        System.out.println("Ristoranti preferiti di " + usernameCliente + ":");
                        for (String ristorante : ristoranti) {
                            String[] dettagli = ristorante.split(";");
                            if (dettagli.length == 2) {     //non dovrebbe succedere ma, se c'è solo il nome del ristorante senza il luogo, allora stampa solo il nome
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
     * @return true se la recensione è stata aggiunta con successo, false altrimenti
     */
public static boolean aggiungiRecensione(String username, String nomeRistorante, String luogoRistorante, String valutazione, String testoRecensione) {

    if (username == null || nomeRistorante == null || luogoRistorante == null ||
        valutazione == null || testoRecensione == null ||
        username.isEmpty() || nomeRistorante.isEmpty() || luogoRistorante.isEmpty() ||
        valutazione.isEmpty() || testoRecensione.isEmpty()) {
        System.out.println("Campi non validi.");
        return false;
    }

    // Verifica che il ristorante esista
    boolean esiste = false;
    try (BufferedReader reader = new BufferedReader(new FileReader(fileRistorantiPath))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] campi = linea.split(";", -1);
            if (campi.length >= 5) {
                String nomeFile = campi[0].trim().toLowerCase();
                String cittaFile = campi[3].trim().toLowerCase();

                if (nomeFile.equals(nomeRistorante.trim().toLowerCase()) &&
                    cittaFile.equals(luogoRistorante.trim().toLowerCase())) {
                    esiste = true;
                    break;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file ristoranti: " + e.getMessage());
        return false;
    }

    if (!esiste) {
        System.out.println("Errore: il ristorante specificato non esiste.");
        return false;
    }

    // Controlla se l'utente ha gia recensito questo ristorante nello stesso luogo
    try (BufferedReader br = new BufferedReader(new FileReader(fileRecensioniPath))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] parti = linea.split(",", -1);
            if (parti.length >= 3) {
                String usernameFile = parti[0].trim();
                String[] ristoranteLuogo = parti[1].split(";", -1);
                if (ristoranteLuogo.length >= 2) {
                    String nomeFile = ristoranteLuogo[0].trim();
                    String luogoFile = ristoranteLuogo[1].trim();

                    if (usernameFile.equalsIgnoreCase(username.trim()) &&
                        nomeFile.equalsIgnoreCase(nomeRistorante.trim()) &&
                        luogoFile.equalsIgnoreCase(luogoRistorante.trim())) {
                        System.out.println("Errore: l'utente ha gia inserito una recensione per questo ristorante.");
                        return false;
                    }
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura del file recensioni: " + e.getMessage());
        return false;
    }

    // Se tutto ok, aggiungi la recensione
    String ristorante = nomeRistorante + ";" + luogoRistorante;
    String nuovaRecensione = String.join(",", username, ristorante, valutazione, testoRecensione, "Nrisposta");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRecensioniPath, true))) {
        writer.write(nuovaRecensione);
        writer.newLine();
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file recensioni: " + e.getMessage());
        return false;
    }

    return true;
}
    
    
    /**
     * Esegue il login verificando username e password cifrata.
     * @param username username inserito
     * @param password password inserita in chiaro
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
 * Verifica le credenziali dell'utente leggendo i dati da un file.
 *
 * @param username              Il nome utente inserito.
 * @param passwordCriptataInput La password criptata inserita.
 * @param ruolo                 Il ruolo dell'utente.
 * @return                      {@code true} se le credenziali corrispondono a un utente nel file, altrimenti {@code false}.
 */
    public static boolean loginUtenteR(String username, String passwordCriptataInput, String ruolo) {
    if (username == null || passwordCriptataInput == null || ruolo == null || username.isEmpty() || passwordCriptataInput.isEmpty() || ruolo.isEmpty()) {
        System.out.println("Username o password vuoti.");
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(fileUtentiPath))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] campi = linea.split(",", -1);

            if (campi.length <= 6) {
                System.out.println("Linea utenti malformata: " + linea);
                continue;
            }

            String usernameFile = campi[2].trim();
            String passwordFile = campi[3].trim();
            String ruoloFile = campi[6].trim();


            if (usernameFile.equals(username)) {
                if (passwordFile.equals(passwordCriptataInput)) {
                    if (ruolo.equals(ruoloFile)) {
                        return true;
                    } else {
                        System.out.println("Ruolo non corrisponde.");
                        return false;
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
 * Registra un nuovo utente nel file specificato. Se il file non esiste, viene creato.
 * Verifica che l'username non sia gia presente prima di aggiungere il nuovo utente.
 *
 * @param nome        Il nome dell'utente.
 * @param cognome     Il cognome dell'utente.
 * @param username    L'username dell'utente.
 * @param password    La password dell'utente.
 * @param dataNascita La data di nascita dell'utente.
 * @param domicilio   Il domicilio dell'utente.
 * @param ruolo       Il ruolo dell'utente.
 * @param preferiti   Le preferenze dell'utente.
 * @return            {@code true} se l'utente è stato registrato con successo, {@code false} se l'username esiste gia o si è verificato un errore.
 */
    public static boolean registraUtente(String nome, String cognome, String username, String password, String dataNascita, String domicilio, String ruolo, String preferiti) {
    File file = new File("dati/utenti.txt");

    try {
        // Crea file e directory se non esistono
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        // Controlla se l'username esiste gia
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

        // Scrittura del nuovo utente
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

        // Se il file non è vuoto, aggiungi una newline prima del nuovo record
        if (file.length() > 0) {
            writer.newLine();
        }

        String riga = nome + "," + cognome + "," + username + "," + password + "," + dataNascita + "," + domicilio + "," + ruolo + "," + preferiti;
        writer.write(riga);
        writer.close();
        return true;

    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    
/**
 * Cerca ristoranti in base a criteri avanzati, leggendo i dati da un file.
 * Filtra i risultati in base alla zona, tipo di cucina, fascia di prezzo, disponibilita di delivery o prenotazione,
 * e numero minimo di stelle.
 *
 * @param zona          La zona in cui cercare i ristoranti.
 * @param cucina        Il tipo di cucina desiderato.
 * @param prezzoMin     Il prezzo minimo del ristorante.
 * @param prezzoMax     Il prezzo massimo del ristorante.
 * @param delivery      Indica se il ristorante offre servizio di consegna.
 * @param prenotazione  Indica se il ristorante accetta prenotazioni.
 * @param stelleMin     Il numero minimo di stelle del ristorante basato sulle recensioni.
 * @return              Una lista di descrizioni testuali dei ristoranti che rispettano i criteri specificati.
 */
    public static List<String> cercaRistorantiAvanzata(
    String zona,
    String cucina,
    Integer prezzoMin,
    Integer prezzoMax,
    Boolean delivery,
    Boolean prenotazione,
    Double stelleMin
) {
    List<String> risultati = new ArrayList<>();
    File file = new File("dati/ristoranti.txt");

    if (!file.exists()) {
        System.out.println("File dei ristoranti non trovato.");
        return risultati;
    }

    int campiMinimi = 11; // Numero minimo di campi nel file ristoranti

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue; // salta righe vuote

            String[] campi = line.split(";");
            if (campi.length < campiMinimi) continue; // salta righe malformate

            Ristorante r = Mapper.mapObjRistorante(line);
            if (r == null) continue;

            // Filtri
            if (zona != null && !zona.isEmpty() && !r.getCitta().equalsIgnoreCase(zona)) continue;

            if (cucina != null && !cucina.isEmpty() && !r.getTipo_Cucina().toLowerCase().contains(cucina.toLowerCase())) continue;

            if (prezzoMin != null && r.getPrezzo() < prezzoMin) continue;
            if (prezzoMax != null && r.getPrezzo() > prezzoMax) continue;

            if (delivery != null && r.isDisponibilita_delivery() != delivery) continue;
            if (prenotazione != null && r.isDisponibilita_prenotazione() != prenotazione) continue;

            if (stelleMin != null) {
                double mediaStelle = calcolaMediaStelle(r.getNome());
                if (mediaStelle < stelleMin) continue;
            }

            // Aggiungi descrizione
            String descrizione = String.format(
                "%s - %s, %s\nIndirizzo: %s\nPrezzo medio: %d€\nDelivery: %s - Prenotazione: %s\nTipo cucina: %s\n",
                r.getNome(), r.getUsername_ristoratore(), r.getCitta(), r.getIndirizzo(), r.getPrezzo(),
                r.isDisponibilita_delivery() ? "Sì" : "No",
                r.isDisponibilita_prenotazione() ? "Sì" : "No",
                r.getTipo_Cucina()
            );
            risultati.add(descrizione);
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    if (risultati.isEmpty()) {
        risultati.add("Nessun ristorante trovato con i criteri indicati.");
    }

    return risultati;
}

/**
 * Calcola la media delle stelle assegnate a un ristorante, leggendo i voti dalle recensioni memorizzate in un file.
 * Ignora eventuali voti non validi o righe malformate.
 *
 * @param nomeRistorante Il nome del ristorante di cui calcolare la media delle stelle.
 * @return              La media delle stelle assegnate al ristorante, o 0.0 se non sono presenti recensioni valide.
 */
    public static double calcolaMediaStelle(String nomeRistorante) {
    String fileRecensioniPath = "dati/recensioni.txt"; // o path corretto
    double media = 0.0;
    int sommaVoti = 0;
    int conteggio = 0;

    try (BufferedReader recReader = new BufferedReader(new FileReader(fileRecensioniPath))) {
        String linea;
        while ((linea = recReader.readLine()) != null) {
            String[] recCampi = linea.split(",", -1);
            if (recCampi.length > 2 && recCampi[1].equalsIgnoreCase(nomeRistorante)) {
                try {
                    int voto = Integer.parseInt(recCampi[2]);
                    sommaVoti += voto;
                    conteggio++;
                } catch (NumberFormatException e) {
                    // Ignora voti non validi
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Errore nella lettura delle recensioni");
    }

    if (conteggio > 0) {
        media = (double) sommaVoti / conteggio;
    }
    return media;
}
    
    /**
 * Elimina una recensione specifica dal file delle recensioni, identificandola tramite username, nome e luogo del ristorante.
 * Se la recensione esiste, viene rimossa dal file; altrimenti, viene segnalato che non è stata trovata.
 *
 * @param username  L'username dell'utente che ha scritto la recensione.
 * @param nomeRis   Il nome del ristorante associato alla recensione.
 * @param luogoRis  Il luogo del ristorante associato alla recensione.
 */
    public static void eliminaRecensione(String username, String nomeRis, String luogoRis) {
    String filePath = "dati/recensioni.txt";
    List<String> recensioniAggiornate = new ArrayList<>();
    boolean eliminata = false;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] parti = linea.split(",", 6);
            if (parti.length < 3) continue;

            String fileUsername = parti[0].trim();
            String[] nomeELuogo = parti[1].split(";", 2);
            if (nomeELuogo.length < 2) continue;

            String fileNomeRis = nomeELuogo[0].trim();
            String fileLuogoRis = nomeELuogo[1].trim();

            if (fileUsername.equalsIgnoreCase(username.trim()) &&
                fileNomeRis.equalsIgnoreCase(nomeRis.trim()) &&
                fileLuogoRis.equalsIgnoreCase(luogoRis.trim())) {
                eliminata = true;
            } else {
                recensioniAggiornate.add(linea);
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file recensioni: " + e.getMessage());
        return;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
        for (String riga : recensioniAggiornate) {
            bw.write(riga);
            bw.newLine();
        }
        if (eliminata) {
            System.out.println("Recensione eliminata con successo.");
        } else {
            System.out.println("Nessuna recensione trovata corrispondente ai parametri forniti.");
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file recensioni: " + e.getMessage());
    }
}
    
/**
 * Modifica una recensione esistente nel file delle recensioni, identificandola tramite username,
 * nome e luogo del ristorante. Aggiorna il voto e il testo della recensione se la voce è trovata.
 *
 * @param username       L'username dell'utente che ha scritto la recensione.
 * @param nomeRistorante Il nome del ristorante associato alla recensione.
 * @param luogoRis       Il luogo del ristorante associato alla recensione.
 * @param voto           Il nuovo voto assegnato al ristorante.
 * @param nuovaRec       Il nuovo testo della recensione.
 */
public static void modificaRecensione(String username, String nomeRistorante, String luogoRis, int voto, String nuovaRec) {
    String fileRecensioniPath = "dati/recensioni.txt";
    File file = new File(fileRecensioniPath);

    if (!file.exists()) {
        System.out.println("Il file delle recensioni non esiste.");
        return;
    }

    List<String> recensioniAggiornate = new ArrayList<>();
    boolean trovata = false;

    String ristoranteTarget = nomeRistorante.trim().toLowerCase() + ";" + luogoRis.trim().toLowerCase();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] campi = linea.split(",", -1);

            if (campi.length >= 5) {
                String usernameRec = campi[0].trim();
                String ristoranteRec = campi[1].trim().toLowerCase();

                if (usernameRec.equals(username.trim()) && ristoranteRec.equals(ristoranteTarget)) {
                    // Trovata: aggiorna la riga
                    String nuovaLinea = String.join(",", username, campi[1], String.valueOf(voto), nuovaRec, campi[4]);
                    recensioniAggiornate.add(nuovaLinea);
                    trovata = true;
                } else {
                    recensioniAggiornate.add(linea);
                }
            } else {
                recensioniAggiornate.add(linea); // linea corrotta, lasciala com'è
            }
        }
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file recensioni: " + e.getMessage());
        return;
    }

    if (!trovata) {
        System.out.println("Recensione non trovata. Verifica username, ristorante e luogo.");
        return;
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String riga : recensioniAggiornate) {
            writer.write(riga);
            writer.newLine();
        }
    } catch (IOException e) {
        System.err.println("Errore durante la scrittura del file recensioni: " + e.getMessage());
        return;
    }

    System.out.println("Recensione modificata con successo.");
}
}

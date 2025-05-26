/**
 * Classe DAO per la gestione delle funzionalità principali dell'applicazione TheKnife.
 * Fornisce metodi per l'aggiunta e gestione di ristoranti, recensioni, e preferiti.
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
     * @return true se aggiunto correttamente, false altrimenti
     */
	public static boolean aggiungiRistorante(String nome, String usernameRistoratore, String nazione, String città, String indirizzo, int latitudine,
        int longitudine, int prezzo, boolean disponibilità_delivery, boolean disponibilità_prenotazione,
        String tipo_Cucina) {

        if (nome == null || usernameRistoratore == null || nazione == null || città == null || indirizzo == null || tipo_Cucina == null)
            return false;

        if (latitudine < 0 || longitudine < 0 || prezzo <= 0)
            return false;

        Ristorante nuovoRistorante = new Ristorante(nome, usernameRistoratore, nazione, città, indirizzo, latitudine,
                longitudine, prezzo, disponibilità_delivery, disponibilità_prenotazione, tipo_Cucina);

        String riga = Mapper.mapStrRistorante(nuovoRistorante);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRistorantiPath, true))) {
            writer.write(riga);
            writer.newLine();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Visualizza un riepilogo dei ristoranti con le medie votazioni delle recensioni.
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
                    if (recCampi[1].equalsIgnoreCase(nomeRistorante)) {
                        try {
                            sommaVoti += Integer.parseInt(recCampi[2]);
                            conteggio++;
                        } catch (NumberFormatException e) {
                            // ignora voti non validi
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
     * @param usernameLoggato username del ristoratore loggato
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
                if (campi[1].equals(usernameLoggato)) {
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
                if (ristorantiGestiti.contains(campi[1])) {
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
                        // Recensione già risposta
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
     * @return true se aggiunto con successo, false altrimenti
     */
    public static boolean aggiungiPreferito(String usernameCliente, String nomeRistorante, String luogoRistorante) {    //aggiunge un ristorante al campo preferiti dell'utente che ha effettuato il login
        String fileUtentiPath = "dati/utenti.txt";
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

                    boolean giàPresente = false;
                    if (!preferiti.isEmpty()) {
                        String[] ristoranti = preferiti.split("\\.");
                        for (String ristorante : ristoranti) {      //se il ristorante è già tra i preferiti non viene inserito nuovamente
                            if (ristorante.trim().equalsIgnoreCase(nuovoPreferito)) {
                                giàPresente = true;
                                break;
                            }
                        }
                    }

                    if (!giàPresente) {     //se tutto è andato a buon fine aggiungo il nuovo ristorante preferito
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
            System.out.println("Utente non trovato o ristorante già nei preferiti.");
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
        String fileUtentiPath = "dati/utenti.txt";
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
        String fileUtentiPath = "dati/utenti.txt";
    
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

    public static List<String> CercaRistorantiL(String zona) {
        List<String> risultati = new ArrayList<>();
        zona = zona.toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader("dati/ristoranti.txt"))) {
            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] campi = riga.split(";");
                if (campi.length >= 11) {
                    String nome = campi[0];
                    String username = campi[1];
                    String nazione = campi[2];
                    String citta = campi[3];
                    String indirizzo = campi[4];
                    String lat = campi[5];
                    String lon = campi[6];
                    String prezzo = campi[7];
                    String delivery = campi[8];
                    String prenotazione = campi[9];
                    String cucina = campi[10];

                    if (nazione.toLowerCase().contains(zona) || citta.toLowerCase().contains(zona)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Nome: ").append(nome).append("\n");
                        sb.append("Username Ristoratore: ").append(username).append("\n");
                        sb.append("Nazione: ").append(nazione).append("\n");
                        sb.append("Città: ").append(citta).append("\n");
                        sb.append("Indirizzo: ").append(indirizzo).append("\n");
                        sb.append("Coordinate: ").append(lat).append(", ").append(lon).append("\n");
                        sb.append("Prezzo Medio: €").append(prezzo).append("\n");
                        sb.append("Delivery: ").append(delivery).append("\n");
                        sb.append("Prenotazione Online: ").append(prenotazione).append("\n");
                        sb.append("Tipo di Cucina: ").append(cucina);
                        risultati.add(sb.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return risultati;
    }

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

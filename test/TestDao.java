package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;

import org.junit.jupiter.api.*;

import dao.GestioneTheKnife;
/**
 * Classe di test per la classe {@link dao.GestioneTheKnife}.
 * Utilizza JUnit per verificare il comportamento delle operazioni sui file di ristoranti e recensioni.
 */

public class TestDao {
    /** File temporaneo per i ristoranti. */
    private File tempRistorantiFile;
    /** File temporaneo per le recensioni. */
    private File tempRecensioniFile;

    /**
     * Inizializza l'ambiente di test prima di ogni test.
     * Crea file temporanei vuoti per ristoranti e recensioni e configura la classe GestioneTheKnife.
     *
     * @throws IOException se si verifica un errore nella creazione dei file temporanei
     */
    @BeforeEach
    public void setUp() throws IOException {
        // Crea file temporanei
        tempRistorantiFile = Files.createTempFile("ristoranti", ".txt").toFile();
        tempRecensioniFile = Files.createTempFile("recensioni", ".txt").toFile();

        // Imposta i path nella classe da testare
        GestioneTheKnife.setFileRistorantiPath(tempRistorantiFile.getAbsolutePath());
        GestioneTheKnife.setFileRecensioniPath(tempRecensioniFile.getAbsolutePath());

        // Pulisci i file (se c’è qualcosa)
        try (PrintWriter pw = new PrintWriter(tempRistorantiFile)) {
            pw.print("");
        }
        try (PrintWriter pw = new PrintWriter(tempRecensioniFile)) {
            pw.print("");
        }
    }
    /**
     * Elimina i file temporanei creati durante i test dopo ogni esecuzione.
     */
    @AfterEach
    public void tearDown() {
        // Cancella i file temporanei dopo ogni test
        tempRistorantiFile.delete();
        tempRecensioniFile.delete();
    }
    /**
     * Test di aggiunta ristorante successivo.
     */
    @Test
    public void testAggiungiRistoranteSuccesso() {
        boolean result = GestioneTheKnife.aggiungiRistorante(
            "RistoTest", "userRistoratore", "Italia", "Milano", "Via Roma 1", 45, 9, 2, true, true, "Italiana"
        );
        assertTrue(result);

        // Verifica che il file ristoranti contenga la riga appena aggiunta
        try (BufferedReader br = new BufferedReader(new FileReader(tempRistorantiFile))) {
            String line = br.readLine();
            assertNotNull(line);
            assertTrue(line.contains("RistoTest"));
        } catch (IOException e) {
            fail("Errore nella lettura del file ristoranti");
        }
    }

    /**
     * Test di visualizza riepilogo con recensioni
     */
    @Test
    public void testVisualizzaRiepilogoConRecensioni() throws IOException {
        // Scrivi ristorante nel file
        try (PrintWriter pw = new PrintWriter(tempRistorantiFile)) {
            pw.println("RistoTest,userRistoratore,Italia,Milano,Via Roma 1,45,9,2,true,true,Italiana");
        }

        // Scrivi recensioni nel file recensioni
        try (PrintWriter pw = new PrintWriter(tempRecensioniFile)) {
            pw.println("userCliente,RistoTest,5,Ottimo cibo,");
            pw.println("userCliente2,RistoTest,4,Buono,");
        }

        // Questo metodo stampa su console, quindi per test più avanzati potresti catturare lo stdout,
        // ma qui almeno lo eseguiamo per vedere che non dia errori.
        GestioneTheKnife gestione = new GestioneTheKnife();
        gestione.visualizzaRiepilogo();

        // Se vuoi, puoi catturare l'output System.out e testare il contenuto stampato.
    }

    /**
     * Test di visualizza recensioni con ristorante gestito
     */
    @Test
    public void testVisualizzaRecensioniConRistoranteGestito() throws IOException {
        // Scrivi ristorante nel file con username ristoratore 'userRistoratore'
        try (PrintWriter pw = new PrintWriter(tempRistorantiFile)) {
            pw.println("RistoTest,userRistoratore,Italia,Milano,Via Roma 1,45,9,2,true,true,Italiana");
        }

        // Scrivi recensioni nel file recensioni
        try (PrintWriter pw = new PrintWriter(tempRecensioniFile)) {
            pw.println("userCliente,RistoTest,5,Ottimo cibo,");
        }

        // Chiama il metodo con usernameLoggato uguale a userRistoratore
        GestioneTheKnife.visualizzaRecensioni("userRistoratore");
    }

    /**
     * Test di risposta a recensione
     */
    @Test
    public void testRispondiARecensione() throws IOException {
        // Scrivi ristorante nel file con username ristoratore 'userRistoratore'
        try (PrintWriter pw = new PrintWriter(tempRistorantiFile)) {
            pw.println("RistoTest,userRistoratore,Italia,Milano,Via Roma 1,45,9,2,true,true,Italiana");
        }

        // Scrivi recensioni nel file recensioni con risposta vuota
        try (PrintWriter pw = new PrintWriter(tempRecensioniFile)) {
            pw.println("userCliente,RistoTest,5,Ottimo cibo,");
        }

        boolean result = GestioneTheKnife.rispondiARecensione("userRistoratore", "RistoTest", "userCliente", "Grazie!");
        assertTrue(result);

        // Verifica che la recensione ora abbia la risposta
        try (BufferedReader br = new BufferedReader(new FileReader(tempRecensioniFile))) {
            String line = br.readLine();
            assertTrue(line.endsWith("Grazie!"));
        }
    }
    /**
     * Test di aggiunta ristorante con dati null
     */
    @Test
    public void testAggiuntaRistoranteConDatiNull() {
        // Parametri null o non validi
        boolean result = GestioneTheKnife.aggiungiRistorante(
            null, null, null, null, null, -1, -1, -1, false, false, null
        );
        assertFalse(result, "L'aggiunta dovrebbe fallire con parametri nulli o invalidi");
    }

    /**
     * Test di risposta a recensione non gestita
     */
    @Test
    public void testRispondiARecensioneNonGestita() throws IOException {
        // Scrivi ristorante gestito da un altro utente
        try (PrintWriter pw = new PrintWriter(tempRistorantiFile)) {
            pw.println("RistoTest,altroUtente,Italia,Milano,Via Roma 1,45,9,2,true,true,Italiana");
        }

        // Scrivi una recensione
        try (PrintWriter pw = new PrintWriter(tempRecensioniFile)) {
            pw.println("userCliente,RistoTest,5,Ottimo cibo,");
        }

        // Prova a rispondere come utente NON gestore
        boolean result = GestioneTheKnife.rispondiARecensione("userRistoratore", "RistoTest", "userCliente", "Grazie!");
        assertFalse(result, "Non dovrebbe permettere di rispondere se l'utente non gestisce il ristorante");
    }

    /**
     * Test di visualizzazione recensioni con file recensioni corrotto
     */
    @Test
    public void testVisualizzaRecensioniConFileRecensioniCorrotto() throws IOException {
        // Scrivi ristorante valido
        try (PrintWriter pw = new PrintWriter(tempRistorantiFile)) {
            pw.println("RistoTest,userRistoratore,Italia,Milano,Via Roma 1,45,9,2,true,true,Italiana");
        }

        // Scrivi una riga malformata nel file recensioni
        try (PrintWriter pw = new PrintWriter(tempRecensioniFile)) {
            pw.println("riga,non,corretta");
        }

        // Il metodo dovrebbe gestire l'errore senza crashare
        assertDoesNotThrow(() -> {
            GestioneTheKnife.visualizzaRecensioni("userRistoratore");
        });
    }

    /**
     * Test di scrittura su file non scrivibile
     */
    @Test
    public void testScritturaSuFileNonScrivibile() throws IOException {
        // Rende il file ristoranti di sola lettura
        tempRistorantiFile.setWritable(false);

        // Tentativo di scrittura dovrebbe fallire
        boolean result = GestioneTheKnife.aggiungiRistorante(
            "RistoBloccato", "userTest", "Italia", "Roma", "Via X", 0, 0, 1, false, false, "Fusion"
        );
        assertFalse(result, "Non dovrebbe riuscire a scrivere su un file non scrivibile");

        // Ripristina i permessi per evitare problemi nei test successivi
        tempRistorantiFile.setWritable(true);
    }
/**
     * Test di risposta a recensione non esistente
     */
    @Test
    public void testRecensioneNonEsistente() throws IOException {
        // Scrivi ristorante valido
        try (PrintWriter pw = new PrintWriter(tempRistorantiFile)) {
            pw.println("RistoTest,userRistoratore,Italia,Milano,Via Roma 1,45,9,2,true,true,Italiana");
        }

        // File recensioni è vuoto, quindi nessuna recensione presente

        boolean result = GestioneTheKnife.rispondiARecensione("userRistoratore", "RistoTest", "utenteFinto", "Ciao!");
        assertFalse(result, "Non dovrebbe poter rispondere a una recensione che non esiste");
    }

    // Puoi aggiungere anche test con file non scrivibili o lettura fallita simulando permessi o mock.
}
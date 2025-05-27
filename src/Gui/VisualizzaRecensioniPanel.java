package Gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Pannello per la visualizzazione e gestione delle recensioni di un ristorante.
 * Permette di vedere le recensioni esistenti, rispondere a recensioni e aggiungerne di nuove.
 */
public class VisualizzaRecensioniPanel extends JPanel {

    /**
     * Riferimento al frame principale per la gestione della navigazione.
     */
    private MainFrame frame;

    /**
     * Record stringa contenente le informazioni del ristorante.
     */
    private String recordRistorante;

    /**
     * Nome del ristorante estratto dal record.
     */
    private String nomeRistorante;

    /**
     * Username dell'utente corrente.
     */
    private String username;

    /**
     * Modello per la lista delle recensioni.
     */
    private DefaultListModel<String> listModel;

    /**
     * Lista che mostra le recensioni.
     */
    private JList<String> listaRecensioni;

    /**
     * Area di testo per mostrare il dettaglio della recensione selezionata.
     */
    private JTextArea dettaglioRecensione;

    /**
     * Pulsante per rispondere ad una recensione selezionata.
     */
    private JButton rispondiButton;

    /**
     * Pulsante per aggiungere una nuova recensione.
     */
    private JButton aggiungiRecensioneButton;

    /**
     * Percorso del file contenente le recensioni.
     */
    private static final String pathRecensioni = "dati/recensioni.txt";

    /**
     * Costruisce il pannello di visualizzazione recensioni per un dato ristorante.
     * 
     * @param frame il frame principale dell'applicazione
     * @param recordRistorante la stringa contenente i dati del ristorante
     */
    public VisualizzaRecensioniPanel(MainFrame frame, String recordRistorante) {
        this.frame = frame;
        this.recordRistorante = recordRistorante;

        String[] campi = recordRistorante.split(";");
        this.nomeRistorante = campi[0];

        this.username = frame.getUtenteCorrente();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel(nomeRistorante, SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listaRecensioni = new JList<>(listModel);
        listaRecensioni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaRecensioni);

        dettaglioRecensione = new JTextArea();
        dettaglioRecensione.setEditable(false);
        dettaglioRecensione.setLineWrap(true);
        dettaglioRecensione.setWrapStyleWord(true);
        JScrollPane scrollDettaglio = new JScrollPane(dettaglioRecensione);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollLista, scrollDettaglio);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        rispondiButton = new JButton("Rispondi alla Recensione");
        rispondiButton.setEnabled(false);
        rispondiButton.addActionListener(e -> rispondiRecensione());

        aggiungiRecensioneButton = new JButton("Aggiungi Recensione");
        aggiungiRecensioneButton.addActionListener(e -> aggiungiRecensione());

        JButton tornaIndietro = new JButton("Torna indietro");
        tornaIndietro.addActionListener(e -> frame.mostraPannello(frame.getFrameAttuale()));

        JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        sud.setBackground(Color.WHITE);
        sud.add(aggiungiRecensioneButton);
        sud.add(rispondiButton);
        sud.add(tornaIndietro);
        add(sud, BorderLayout.SOUTH);

        listaRecensioni.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = listaRecensioni.getSelectedIndex();
                if (index >= 0) {
                    String linea = listaRecensioni.getSelectedValue();
                    String[] campo = linea.split(" \\| ");
                    if (campo.length >= 4) {
                        dettaglioRecensione.setText(
                            "Utente: " + campo[0] + "\n" +
                            "Valutazione: " + campo[1] + "/5\n" +
                            "Testo: " + campo[2] + "\n" +
                            "Risposta: " + (campo[3].isEmpty() ? "Nessuna" : campo[3])
                        );
                        rispondiButton.setEnabled(campo[3].isEmpty() && username.equals(frame.getUtenteCorrente()) && !campo[0].equals(username));
                    } else {
                        dettaglioRecensione.setText("");
                        rispondiButton.setEnabled(false);
                    }
                } else {
                    dettaglioRecensione.setText("");
                    rispondiButton.setEnabled(false);
                }
            }
        });

        aggiornaLista();
    }

    /**
     * Aggiorna la lista delle recensioni caricandole dal file.
     */
    private void aggiornaLista() {
        listModel.clear();
        dettaglioRecensione.setText("");
        rispondiButton.setEnabled(false);

        File file = new File(pathRecensioni);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campo = linea.split(",", -1);
                if (campo.length >= 5 && campo[1].equalsIgnoreCase(nomeRistorante)) {
                    String voce = campo[0] + " | " + campo[2] + " | " + campo[3] + " | " + campo[4];
                    listModel.addElement(voce);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file delle recensioni");
        }
    }

    /**
     * Consente di rispondere alla recensione selezionata dall'utente.
     * Verifica i permessi e aggiorna la lista dopo il salvataggio della risposta.
     */
    private void rispondiRecensione() {
        int index = listaRecensioni.getSelectedIndex();
        if (index >= 0) {
            String linea = listaRecensioni.getSelectedValue();
            String[] campo = linea.split(" \\| ");
            if (campo.length >= 1) {
                String usernameCliente = campo[0].trim();
                String risposta = JOptionPane.showInputDialog(this, "Inserisci la tua risposta:");
                if (risposta != null && !risposta.trim().isEmpty()) {
                    boolean ok = dao.GestioneTheKnife.rispondiARecensione(username, nomeRistorante, usernameCliente, risposta.trim());
                    if (ok) {
                        JOptionPane.showMessageDialog(this, "Risposta salvata con successo.");
                        aggiornaLista();
                    } else {
                        JOptionPane.showMessageDialog(this, "Non puoi rispondere a questa recensione.", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * Consente di aggiungere una nuova recensione per il ristorante.
     * Presenta una finestra di dialogo per l'inserimento della valutazione e del testo.
     */
    private void aggiungiRecensione() {
        JPanel pannello = new JPanel(new GridLayout(3, 1));
        JTextField voto = new JTextField();
        JTextArea testo = new JTextArea(5, 20);
        testo.setLineWrap(true);
        testo.setWrapStyleWord(true);

        pannello.add(new JLabel("Valutazione (1-5):"));
        pannello.add(voto);
        pannello.add(new JScrollPane(testo));

        int scelta = JOptionPane.showConfirmDialog(this, pannello, "Nuova Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (scelta == JOptionPane.OK_OPTION) {
            try {
                int valutazione = Integer.parseInt(voto.getText().trim());
                if (valutazione < 1 || valutazione > 5) throw new NumberFormatException();
                String commento = testo.getText().trim();
                if (commento.isEmpty()) throw new IllegalArgumentException();

                boolean ok = dao.GestioneTheKnife.aggiungiRecensione(username, nomeRistorante, valutazione, commento);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Recensione aggiunta con successo.");
                    aggiornaLista();
                } else {
                    JOptionPane.showMessageDialog(this, "Hai già recensito questo ristorante.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Inserisci un numero da 1 a 5.", "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Il campo testo non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
package src.Gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import src.dao.GestioneTheKnife;

public class VisualizzaRecensioniPanel extends JPanel {
    private MainFrame frame;
    private String nomeRistorante;
    private String username;
    private DefaultListModel<String> listModel;
    private JList<String> listaRecensioni;
    private JTextArea dettaglioRecensione;

    public VisualizzaRecensioniPanel(MainFrame frame, String recordRistorante) {
        this.frame = frame;
        this.nomeRistorante = recordRistorante.split(";")[0];
        this.username = frame.getUtenteCorrente();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel(nomeRistorante, SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(titolo, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        listaRecensioni = new JList<>(listModel);
        JScrollPane scrollLista = new JScrollPane(listaRecensioni);

        dettaglioRecensione = new JTextArea();
        dettaglioRecensione.setEditable(false);
        dettaglioRecensione.setLineWrap(true);
        dettaglioRecensione.setWrapStyleWord(true);
        JScrollPane scrollDettaglio = new JScrollPane(dettaglioRecensione);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollLista, scrollDettaglio);
        splitPane.setResizeWeight(0.5);
        add(splitPane, BorderLayout.CENTER);

        JButton rispondiButton = new JButton("Rispondi alla Recensione");
        rispondiButton.addActionListener(e -> rispondiRecensione());

        JButton aggiungiButton = new JButton("Aggiungi Recensione");
        aggiungiButton.addActionListener(e -> aggiungiRecensione());

        JButton indietroButton = new JButton("Torna indietro");
        indietroButton.addActionListener(e -> esci());

        JPanel sud = new JPanel();
        sud.add(aggiungiButton);
        sud.add(rispondiButton);
        sud.add(indietroButton);
        add(sud, BorderLayout.SOUTH);

        listaRecensioni.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostraDettaglio(listaRecensioni.getSelectedValue());
            }
        });

        aggiornaLista();
    }

    private void aggiornaLista() {
        listModel.clear();
        dettaglioRecensione.setText("");
        File file = new File("dati/recensioni.txt");
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                if (campi.length == 5 && campi[1].trim().equalsIgnoreCase(nomeRistorante)) {
                    String risposta = campi[4].trim();
                    if (risposta.equalsIgnoreCase("null") || risposta.isEmpty()) risposta = "Nessuna";
                    listModel.addElement(campi[0] + " | " + campi[2] + " | " + campi[3] + " | " + risposta);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore lettura recensioni");
        }
    }

    private void mostraDettaglio(String valore) {
        if (valore == null) {
            dettaglioRecensione.setText("");
            return;
        }
        String[] campi = valore.split(" \\| ");
        if (campi.length == 4) {
            dettaglioRecensione.setText(
                "Utente: " + campi[0].trim() + "\n" +
                "Valutazione: " + campi[1].trim() + "/5\n" +
                "Testo: " + campi[2].trim() + "\n" +
                "Risposta: " + campi[3].trim()
            );
        }
    }

    private void rispondiRecensione() {
        int index = listaRecensioni.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona una recensione.");
            return;
        }

        String voce = listaRecensioni.getSelectedValue();
        String[] campi = voce.split(" \\| ");
        if (campi.length < 4) return;

        String cliente = campi[0].trim();
        String rispostaEsistente = campi[3].trim();
        if (!frame.getFrameAttuale().equals("ristoratorePanel") || !utenteHaRistorante() || !rispostaEsistente.equals("Nessuna")) {
            JOptionPane.showMessageDialog(this, "Non puoi rispondere a questa recensione.");
            return;
        }

        JTextArea area = new JTextArea(5, 20);
        JScrollPane scroll = new JScrollPane(area);
        int scelta = JOptionPane.showConfirmDialog(this, scroll, "Scrivi risposta", JOptionPane.OK_CANCEL_OPTION);
        if (scelta == JOptionPane.OK_OPTION) {
            String risposta = area.getText().trim();
            if (!risposta.isEmpty()) {
                boolean ok = GestioneTheKnife.rispondiARecensione(username, nomeRistorante, cliente, risposta);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Risposta salvata.");
                    aggiornaLista();
                } else {
                    JOptionPane.showMessageDialog(this, "Errore nel salvataggio.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Risposta vuota.");
            }
        }
    }

    private void aggiungiRecensione() {
        JPanel pannello = new JPanel(new GridLayout(3, 1));
        JTextField voto = new JTextField();
        JTextArea testo = new JTextArea(5, 20);
        testo.setLineWrap(true);
        testo.setWrapStyleWord(true);

        pannello.add(new JLabel("Voto (1-5):"));
        pannello.add(voto);
        pannello.add(new JScrollPane(testo));

        int scelta = JOptionPane.showConfirmDialog(this, pannello, "Nuova Recensione", JOptionPane.OK_CANCEL_OPTION);
        if (scelta == JOptionPane.OK_OPTION) {
            try {
                int val = Integer.parseInt(voto.getText().trim());
                if (val < 1 || val > 5) throw new NumberFormatException();
                String commento = testo.getText().trim();
                if (commento.isEmpty()) throw new IllegalArgumentException();

                boolean ok = GestioneTheKnife.aggiungiRecensione(username, nomeRistorante, val, commento);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Recensione aggiunta.");
                    aggiornaLista();
                } else {
                    JOptionPane.showMessageDialog(this, "Hai già recensito.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Voto non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Commento vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean utenteHaRistorante() {
        try (BufferedReader br = new BufferedReader(new FileReader("dati/ristoranti.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(";", -1);
                if (campi.length >= 2 && campi[0].equals(nomeRistorante) && campi[1].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    private void esci() {
        frame.rimuovi("visualizzaRecensioniPanel");
        frame.mostraPannello(frame.getFrameAttuale());
    }
}

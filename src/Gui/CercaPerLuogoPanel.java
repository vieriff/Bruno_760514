package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import dao.GestioneTheKnife;

/**
 * Pannello per la ricerca di ristoranti in base a una zona o citt‡.
 * Visualizza la lista dei risultati e i dettagli del ristorante selezionato,
 * con la possibilit‡ di visualizzare le recensioni.
 */
public class CercaPerLuogoPanel extends JPanel {
    /** Riferimento al frame principale per la gestione dei pannelli */
    private MainFrame frame;
    /** Modello della lista dei ristoranti trovati */
    private DefaultListModel<String> listModel;
    /** Lista grafica dei ristoranti */
    private JList<String> listaRistoranti;
    /** Area di testo per mostrare i dettagli del ristorante selezionato */
    private JTextArea dettagliArea;
    /** Bottone per visualizzare le recensioni */
    private JButton visualizzaRecensioni;
    /** Lista dei dati completi dei ristoranti trovati */
    private List<String> risultatiCorretti;
    /** Campo di testo per l'inserimento della zona o citt‡ da cercare */
    private JTextField campoLuogo;

    /**
     * Costruisce il pannello per la ricerca per zona o citt‡.
     *
     * @param frame riferimento al MainFrame per la navigazione
     * @param pannelloChiamante nome del pannello chiamante (non usato nel codice attuale)
     */
    public CercaPerLuogoPanel(MainFrame frame, String pannelloChiamante) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Cerca Ristorante per Zona", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centro.setBackground(Color.WHITE);

        campoLuogo = new JTextField();
        JButton cercaButton = new JButton("Cerca");

        JPanel ricerca = new JPanel(new BorderLayout(5, 5));
        ricerca.setBackground(Color.WHITE);
        ricerca.add(new JLabel("Citt√† o Zona: "), BorderLayout.WEST);
        ricerca.add(campoLuogo, BorderLayout.CENTER);
        ricerca.add(cercaButton, BorderLayout.EAST);

        listModel = new DefaultListModel<>();
        listaRistoranti = new JList<>(listModel);
        listaRistoranti.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(listaRistoranti);

        dettagliArea = new JTextArea();
        dettagliArea.setEditable(false);
        dettagliArea.setLineWrap(true);
        dettagliArea.setWrapStyleWord(true);
        JScrollPane scrollDettagli = new JScrollPane(dettagliArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollLista, scrollDettagli);
        splitPane.setResizeWeight(0.5);

        visualizzaRecensioni = new JButton("Visualizza Recensioni");
        visualizzaRecensioni.setEnabled(false);
        visualizzaRecensioni.addActionListener(e -> {
            int index = listaRistoranti.getSelectedIndex();
            if (index >= 0 && index < risultatiCorretti.size()) {
                String selezione = risultatiCorretti.get(index);
                frame.aggiungiEMostra("visualizzaRecensioniPanel", new VisualizzaRecensioniPanel(frame, selezione));
            }
        });

        centro.add(ricerca, BorderLayout.NORTH);
        centro.add(splitPane, BorderLayout.CENTER);
        centro.add(visualizzaRecensioni, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        JButton tornaIndietro = new JButton("Torna indietro");
        tornaIndietro.addActionListener(e -> frame.mostraPannello(frame.getFrameAttuale()));

        JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sud.setBackground(Color.WHITE);
        sud.add(tornaIndietro);
        add(sud, BorderLayout.SOUTH);

        listaRistoranti.addListSelectionListener(ev -> {
            if (!ev.getValueIsAdjusting()) {
                int index = listaRistoranti.getSelectedIndex();
                if (index >= 0 && index < risultatiCorretti.size()) {
                    dettagliArea.setText(formattaDettagli(risultatiCorretti.get(index)));
                    visualizzaRecensioni.setEnabled(true);
                } else {
                    dettagliArea.setText("");
                    visualizzaRecensioni.setEnabled(false);
                }
            }
        });

        cercaButton.addActionListener(e -> {
            String zona = campoLuogo.getText().trim();
            if (!zona.isEmpty()) {
                List<String> risultati = GestioneTheKnife.CercaRistorantiL(zona);
                risultatiCorretti = new ArrayList<>();
                listModel.clear();
                dettagliArea.setText("");
                visualizzaRecensioni.setEnabled(false);

                for (String r : risultati) {
                    String[] campi = r.split(";");
                    if (campi.length >= 11) {
                        risultatiCorretti.add(r);
                        listModel.addElement(campi[0]);
                    }
                }

                if (risultatiCorretti.isEmpty()) {
                    listModel.addElement("Nessun ristorante trovato.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci una zona o citt√† per la ricerca.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Pulizia dei campi ogni volta che il pannello diventa visibile.
     *
     * @param visible true se il pannello deve essere mostrato, false altrimenti
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            campoLuogo.setText("");
            listModel.clear();
            dettagliArea.setText("");
            visualizzaRecensioni.setEnabled(false);
            risultatiCorretti = new ArrayList<>();
        }
    }

    /**
     * Formatta i dettagli di un ristorante per la visualizzazione.
     *
     * @param ristoranteInfo stringa contenente i dati del ristorante separati da ";"
     * @return stringa formattata con i dettagli del ristorante
     */
    private String formattaDettagli(String ristoranteInfo) {
        String[] dati = ristoranteInfo.split(";");
        if (dati.length < 11) return "Dati ristorante incompleti.";
        return String.format("""
                Nome: %s
                Username Ristoratore: %s
                Nazione: %s
                Citt√†: %s
                Indirizzo: %s
                Latitudine: %s
                Longitudine: %s
                Fascia di Prezzo: %s ‚Ç¨
                Delivery: %s
                Prenotazione Online: %s
                Tipo di Cucina: %s
                """, dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6], dati[7], dati[8], dati[9], dati[10]);
    }
}
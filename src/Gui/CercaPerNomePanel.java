package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.GestioneTheKnife;

/**
 * Pannello per la ricerca di ristoranti per nome.
 */
public class CercaPerNomePanel extends JPanel {
    private MainFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> listaRistoranti;
    private JTextArea dettagliArea;
    private JButton visualizzaRecensioni;
    private List<String> risultatiCorrenti;
    private JTextField campoNome;

    /**
     * Costruttore del pannello di ricerca per nome.
     * 
     * @param frame riferimento al MainFrame per navigazione
     * @param pannelloChiamante nome del pannello chiamante (non usato internamente)
     */
    public CercaPerNomePanel(MainFrame frame, String pannelloChiamante) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Cerca Ristorante per Nome", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centro.setBackground(Color.WHITE);

        campoNome = new JTextField();
        JButton cercaButton = new JButton("Cerca");

        JPanel ricerca = new JPanel(new BorderLayout(5, 5));
        ricerca.setBackground(Color.WHITE);
        ricerca.add(new JLabel("Nome Ristorante: "), BorderLayout.WEST);
        ricerca.add(campoNome, BorderLayout.CENTER);
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
            if (index >= 0 && index < risultatiCorrenti.size()) {
                String selezione = listaRistoranti.getSelectedValue();
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
                if (index >= 0 && risultatiCorrenti != null && index < risultatiCorrenti.size()) {
                    dettagliArea.setText(formattaDettagli(risultatiCorrenti.get(index)));
                    visualizzaRecensioni.setEnabled(true);
                } else {
                    dettagliArea.setText("");
                    visualizzaRecensioni.setEnabled(false);
                }
            }
        });

        cercaButton.addActionListener(e -> {
            String nome = campoNome.getText().trim();
            if (!nome.isEmpty()) {
                risultatiCorrenti = GestioneTheKnife.CercaRistorantiN(nome);
                listModel.clear();
                dettagliArea.setText("");
                visualizzaRecensioni.setEnabled(false);
                if (risultatiCorrenti.isEmpty()) {
                    listModel.addElement("Nessun ristorante trovato.");
                } else {
                    for (String r : risultatiCorrenti) {
                        String[] dati = r.split(";");
                        listModel.addElement(dati.length > 0 ? dati[0] : "Ristorante");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci il nome del ristorante.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * Ripristina il pannello allo stato iniziale quando reso visibile.
     *
     * @param visible true se il pannello � visibile, false altrimenti
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            campoNome.setText("");
            listModel.clear();
            dettagliArea.setText("");
            visualizzaRecensioni.setEnabled(false);
            risultatiCorrenti = null;
        }
    }

    /**
     * Formattta i dettagli del ristorante in modo leggibile.
     *
     * @param ristoranteInfo dati del ristorante separati da ";"
     * @return stringa formattata con i dettagli
     */
    private String formattaDettagli(String ristoranteInfo) {
        String[] dati = ristoranteInfo.split(";");
        if (dati.length < 11) return "Dati ristorante incompleti.";
        return String.format("""
                Nome: %s
                Username Ristoratore: %s
                Nazione: %s
                Città: %s
                Indirizzo: %s
                Latitudine: %s
                Longitudine: %s
                Fascia di Prezzo: %s €
                Delivery: %s
                Prenotazione Online: %s
                Tipo di Cucina: %s
                """, dati[0], dati[1], dati[2], dati[3], dati[4], dati[5], dati[6], dati[7], dati[8], dati[9], dati[10]);
    }
}
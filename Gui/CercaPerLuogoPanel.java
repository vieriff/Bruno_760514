package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.GestioneTheKnife;

public class CercaPerLuogoPanel extends JPanel {
    private MainFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> listaRistoranti;
    private JTextArea dettagliArea;
    private JButton visualizzaRecensioni;
    private List<String> risultatiCorrenti;

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

        JTextField campoLuogo = new JTextField();
        JButton cercaButton = new JButton("Cerca");

        JPanel ricerca = new JPanel(new BorderLayout(5, 5));
        ricerca.setBackground(Color.WHITE);
        ricerca.add(new JLabel("Città o Zona: "), BorderLayout.WEST);
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

        centro.add(ricerca, BorderLayout.NORTH);
        centro.add(splitPane, BorderLayout.CENTER);

        visualizzaRecensioni = new JButton("Visualizza Recensioni");
        visualizzaRecensioni.setEnabled(false);
        visualizzaRecensioni.addActionListener(e -> {
            int index = listaRistoranti.getSelectedIndex();
            if (index >= 0 && index < risultatiCorrenti.size()) {
                String selezione = listaRistoranti.getSelectedValue();
                frame.aggiungiEMostra("visualizzaRecensioniPanel", new VisualizzaRecensioniPanel(frame, selezione));
            }
        });

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
                }
            }
        });

        cercaButton.addActionListener(e -> {
            String luogo = campoLuogo.getText().trim();
            if (!luogo.isEmpty()) {
                risultatiCorrenti = GestioneTheKnife.CercaRistorantiL(luogo);
                listModel.clear();
                dettagliArea.setText("");
                visualizzaRecensioni.setEnabled(false);
                if (risultatiCorrenti.isEmpty()) {
                    listModel.addElement("Nessun ristorante trovato.");
                } else {
                    for (String r : risultatiCorrenti) {
                        String nomeRistorante = r.split(";")[0];
                        listModel.addElement(nomeRistorante);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci una zona o città per la ricerca.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

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

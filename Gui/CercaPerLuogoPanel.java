package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import dao.GestioneTheKnife;

public class CercaPerLuogoPanel extends JPanel {
    private MainFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> listaRistoranti;
    private JTextArea dettagliArea;
    private JButton visualizzaRecensioni;
    private List<String> risultatiCorretti;
    private JTextField campoLuogo;

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
                JOptionPane.showMessageDialog(this, "Inserisci una zona o città per la ricerca.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

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

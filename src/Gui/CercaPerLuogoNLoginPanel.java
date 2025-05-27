package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.GestioneTheKnife;

/**
 * Pannello per la ricerca di ristoranti in base al luogo (nazione o citt�).
 * Mostra una lista dei risultati e i dettagli selezionati.
 */
public class CercaPerLuogoNLoginPanel extends JPanel {
    /** Campo di testo per inserire la nazione o la citt� di ricerca */
    private JTextField campoLuogo;
    /** Modello per la lista dei risultati della ricerca */
    private DefaultListModel<String> listaModel;
    /** Area di testo per mostrare i dettagli del ristorante selezionato */
    private JTextArea dettagliArea;
    /** Lista dei risultati correnti della ricerca */
    private List<String> risultatiCorrenti;

    /**
     * Costruttore che crea e configura il pannello con il form di ricerca,
     * la lista dei risultati e l'area dei dettagli.
     * 
     * @param frame Riferimento al frame principale per la navigazione.
     */
    public CercaPerLuogoNLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Cerca Ristorante per Luogo", SwingConstants.CENTER);
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
        ricerca.add(new JLabel("Nazione o Città: "), BorderLayout.WEST);
        ricerca.add(campoLuogo, BorderLayout.CENTER);
        ricerca.add(cercaButton, BorderLayout.EAST);

        listaModel = new DefaultListModel<>();
        JList<String> risultatiLista = new JList<>(listaModel);
        risultatiLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(risultatiLista);

        dettagliArea = new JTextArea();
        dettagliArea.setEditable(false);
        dettagliArea.setLineWrap(true);
        dettagliArea.setWrapStyleWord(true);
        JScrollPane scrollDettagli = new JScrollPane(dettagliArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollLista, scrollDettagli);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.5);

        centro.add(ricerca, BorderLayout.NORTH);
        centro.add(splitPane, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        JButton tornaHome = new JButton("Torna alla Home");
        tornaHome.addActionListener(e -> frame.mostraPannello("home"));

        JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sud.setBackground(Color.WHITE);
        sud.add(tornaHome);
        add(sud, BorderLayout.SOUTH);

        cercaButton.addActionListener(e -> {
            String luogo = campoLuogo.getText().trim();
            if (!luogo.isEmpty()) {
                risultatiCorrenti = GestioneTheKnife.CercaRistorantiL(luogo);
                listaModel.clear();
                dettagliArea.setText("");
                if (risultatiCorrenti.isEmpty()) {
                    listaModel.addElement("Nessun ristorante trovato.");
                } else {
                    for (String r : risultatiCorrenti) {
                        String[] dati = r.split(";");
                        listaModel.addElement(dati.length > 0 ? dati[0] : "Ristorante");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci una nazione o una città per la ricerca.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        risultatiLista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = risultatiLista.getSelectedIndex();
                if (index >= 0 && risultatiCorrenti != null && index < risultatiCorrenti.size()) {
                    String[] dati = risultatiCorrenti.get(index).split(";");
                    if (dati.length >= 11) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Nome: ").append(dati[0]).append("\n");
                        sb.append("Username Ristoratore: ").append(dati[1]).append("\n");
                        sb.append("Nazione: ").append(dati[2]).append("\n");
                        sb.append("Città: ").append(dati[3]).append("\n");
                        sb.append("Indirizzo: ").append(dati[4]).append("\n");
                        sb.append("Coordinate: ").append(dati[5]).append(", ").append(dati[6]).append("\n");
                        sb.append("Prezzo Medio: €").append(dati[7]).append("\n");
                        sb.append("Delivery: ").append(dati[8]).append("\n");
                        sb.append("Prenotazione Online: ").append(dati[9]).append("\n");
                        sb.append("Tipo di Cucina: ").append(dati[10]);
                        dettagliArea.setText(sb.toString());
                    } else {
                        dettagliArea.setText("Dati ristorante incompleti.");
                    }
                }
            }
        });
    }

    /**
     * Override di setVisible per pulire i campi ogni volta che il pannello diventa visibile.
     * 
     * @param visible true se il pannello deve essere visibile, false altrimenti.
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            campoLuogo.setText("");
            listaModel.clear();
            dettagliArea.setText("");
            risultatiCorrenti = null;
        }
    }
}
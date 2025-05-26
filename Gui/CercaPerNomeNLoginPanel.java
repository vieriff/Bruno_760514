package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.GestioneTheKnife;

public class CercaPerNomeNLoginPanel extends JPanel {
    private DefaultListModel<String> listModel;
    private JList<String> listaRistoranti;
    private JTextArea dettagliArea;

    public CercaPerNomeNLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Cerca Ristorante per Nome", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        centro.setBackground(Color.WHITE);

        JTextField campoNome = new JTextField();
        JButton cercaButton = new JButton("Cerca");

        JPanel ricerca = new JPanel(new BorderLayout(5, 5));
        ricerca.setBackground(Color.WHITE);
        ricerca.add(new JLabel("Nome: "), BorderLayout.WEST);
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
            String nome = campoNome.getText().trim();
            if (!nome.isEmpty()) {
                List<String> risultati = GestioneTheKnife.CercaRistorantiN(nome);
                listModel.clear();
                dettagliArea.setText("");

                if (risultati.isEmpty()) {
                    listModel.addElement("Nessun ristorante trovato.");
                } else {
                    for (String r : risultati) {
                        String nomeRistorante = r.split(";")[0]; // Prima riga: nome
                        listModel.addElement(nomeRistorante);
                    }

                    listaRistoranti.addListSelectionListener(ev -> {
                        if (!ev.getValueIsAdjusting()) {
                            int index = listaRistoranti.getSelectedIndex();
                            if (index >= 0 && index < risultati.size()) {
                                dettagliArea.setText(formattaDettagli(risultati.get(index)));
                            }
                        }
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci un nome per la ricerca.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private String formattaDettagli(String ristoranteInfo) {
        // Splitta i campi in base al formato nel file txt (assunto separatore ;)
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
                """,
                dati[0], dati[1], dati[2], dati[3], dati[4],
                dati[5], dati[6], dati[7], dati[8], dati[9], dati[10]
        );
    }
}

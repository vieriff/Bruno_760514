package Gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import dao.GestioneTheKnife;

public class CercaPerLuogoNLoginPanel extends JPanel {
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

        JTextField campoLuogo = new JTextField();
        JButton cercaButton = new JButton("Cerca");

        JPanel ricerca = new JPanel(new BorderLayout(5, 5));
        ricerca.setBackground(Color.WHITE);
        ricerca.add(new JLabel("Nazione o Città: "), BorderLayout.WEST);
        ricerca.add(campoLuogo, BorderLayout.CENTER);
        ricerca.add(cercaButton, BorderLayout.EAST);

        DefaultListModel<String> listaModel = new DefaultListModel<>();
        JList<String> risultatiLista = new JList<>(listaModel);
        risultatiLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(risultatiLista);

        JTextArea dettagliArea = new JTextArea();
        dettagliArea.setEditable(false);
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
                List<String> risultati = GestioneTheKnife.CercaRistorantiL(luogo);
                listaModel.clear();
                dettagliArea.setText("");
                if (risultati.isEmpty()) {
                    listaModel.addElement("Nessun ristorante trovato.");
                } else {
                    for (String r : risultati) {
                        String[] righe = r.split("\n");
                        if (righe.length > 0) {
                            listaModel.addElement(righe[0]); // Solo il nome
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Inserisci una nazione o una città per la ricerca.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            }
        });

        risultatiLista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = risultatiLista.getSelectedIndex();
                String luogo = campoLuogo.getText().trim();
                if (index >= 0 && !luogo.isEmpty()) {
                    List<String> risultati = GestioneTheKnife.CercaRistorantiL(luogo);
                    if (index < risultati.size()) {
                        dettagliArea.setText(risultati.get(index));
                    }
                }
            }
        });
    }
}

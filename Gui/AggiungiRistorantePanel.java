package Gui;

import javax.swing.*;
import java.awt.*;
import dao.GestioneTheKnife;

public class AggiungiRistorantePanel extends JPanel {
    public AggiungiRistorantePanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Aggiungi Ristorante", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(11, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        form.setBackground(Color.WHITE);

        JTextField nome = new JTextField();
        JTextField nazione = new JTextField();
        JTextField città = new JTextField();
        JTextField indirizzo = new JTextField();
        JTextField latitudine = new JTextField();
        JTextField longitudine = new JTextField();
        JTextField prezzo = new JTextField();
        JCheckBox delivery = new JCheckBox();
        JCheckBox prenotazione = new JCheckBox();
        JTextField tipoCucina = new JTextField();

        form.add(new JLabel("Nome:"));
        form.add(nome);
        form.add(new JLabel("Nazione:"));
        form.add(nazione);
        form.add(new JLabel("Città:"));
        form.add(città);
        form.add(new JLabel("Indirizzo:"));
        form.add(indirizzo);
        form.add(new JLabel("Latitudine (numero intero):"));
        form.add(latitudine);
        form.add(new JLabel("Longitudine (numero intero):"));
        form.add(longitudine);
        form.add(new JLabel("Fascia di prezzo (€):"));
        form.add(prezzo);
        form.add(new JLabel("Delivery disponibile:"));
        form.add(delivery);
        form.add(new JLabel("Prenotazione online disponibile:"));
        form.add(prenotazione);
        form.add(new JLabel("Tipo di cucina:"));
        form.add(tipoCucina);

        add(form, BorderLayout.CENTER);

        JPanel bottoni = new JPanel();
        bottoni.setBackground(Color.WHITE);

        JButton conferma = new JButton("Aggiungi");
        JButton indietro = new JButton("Torna indietro");

        bottoni.add(conferma);
        bottoni.add(indietro);
        add(bottoni, BorderLayout.SOUTH);

        conferma.addActionListener(e -> {
            try {
                String nomeR = nome.getText().trim();
                String username = frame.getUtenteCorrente();
                String naz = nazione.getText().trim();
                String city = città.getText().trim();
                String addr = indirizzo.getText().trim();
                int lat = Integer.parseInt(latitudine.getText().trim());
                int lon = Integer.parseInt(longitudine.getText().trim());
                int cost = Integer.parseInt(prezzo.getText().trim());
                boolean deliv = delivery.isSelected();
                boolean pren = prenotazione.isSelected();
                String cucina = tipoCucina.getText().trim();

                boolean successo = GestioneTheKnife.aggiungiRistorante(nomeR, username, naz, city, addr, lat, lon, cost, deliv, pren, cucina);

                if (successo) {
                    JOptionPane.showMessageDialog(this, "Ristorante aggiunto con successo.");
                    frame.mostraPannello("ristoratorePanel");
                } else {
                    JOptionPane.showMessageDialog(this, "Errore nell'aggiunta. Controlla i dati.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Inserisci tutti i campi correttamente.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        indietro.addActionListener(e -> frame.tornaZonaRistoratore());
    }
}

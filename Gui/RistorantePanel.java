package Gui;

import javax.swing.*;
import java.awt.*;

public class RistorantePanel extends JPanel {
    public RistorantePanel(MainFrame frame, String username) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Ristoratore" + ", " + username, SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel bottoni = new JPanel();
        bottoni.setBackground(Color.WHITE);
        bottoni.setLayout(new BoxLayout(bottoni, BoxLayout.Y_AXIS));
        bottoni.setBorder(BorderFactory.createEmptyBorder(40, 200, 40, 200));

        JButton aggiungiRistorante = new JButton("Aggiungi ristorante");
        JButton cercaRistoranti = new JButton("Cerca ristoranti");
        JButton logout = new JButton("Logout");
        JButton esci = new JButton("Esci");

        aggiungiRistorante.setAlignmentX(Component.CENTER_ALIGNMENT);
        cercaRistoranti.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);

        aggiungiRistorante.addActionListener(e -> frame.aggiungiEMostra("aggiungiRistorantePanel", new AggiungiRistorantePanel(frame)));
        cercaRistoranti.addActionListener(e -> frame.aggiungiEMostra("cercaRistorantiPanel", new CercaRistorantiPanel(frame, "ristoratorePanel")));
        logout.addActionListener(e -> frame.logout());
        esci.addActionListener(e -> System.exit(0));

        bottoni.add(aggiungiRistorante);
        bottoni.add(Box.createVerticalStrut(20));
        bottoni.add(cercaRistoranti);
        bottoni.add(Box.createVerticalStrut(20));
        bottoni.add(logout);

        add(bottoni, BorderLayout.CENTER);

        JPanel sud = new JPanel();
        sud.setBackground(Color.WHITE);
        sud.add(esci);
        add(sud, BorderLayout.SOUTH);
    }
}

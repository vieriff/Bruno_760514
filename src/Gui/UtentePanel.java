package src.Gui;

import javax.swing.*;
import java.awt.*;

public class UtentePanel extends JPanel {
    public UtentePanel(MainFrame mainFrame, String username) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Benvenuto, " + username, SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(3, 1, 20, 20));
        centro.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        centro.setBackground(Color.WHITE);

        JButton cercaRistoranti = new JButton("Cerca Ristoranti");
        JButton logout = new JButton("Logout");

        centro.add(cercaRistoranti);
        centro.add(logout);

        add(centro, BorderLayout.CENTER);

        cercaRistoranti.addActionListener(e -> {
            mainFrame.aggiungiEMostra("cercaRistorantiN", new CercaRistorantiPanel(mainFrame, "utentePanel_" + username));
        });

        logout.addActionListener(e -> {
            mainFrame.setUtenteCorrente(null);
            mainFrame.mostraPannello("home");
        });
    }
}

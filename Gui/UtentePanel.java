package Gui;

import java.awt.*;
import javax.swing.*;

public class UtentePanel extends JPanel {
    public UtentePanel(MainFrame mainFrame, String username) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Benvenuto, " + username, SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(2, 1, 20, 20));  // modificato a 2 righe
        centro.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        centro.setBackground(Color.WHITE);

        JButton cercaRistoranti = new JButton("Cerca Ristoranti");
        JButton logout = new JButton("Logout");

        centro.add(cercaRistoranti);
        centro.add(logout);

        add(centro, BorderLayout.CENTER);

        cercaRistoranti.addActionListener(e -> {
            String nomePannello = "cercaRistorantiN_" + username;  // nome unico per ogni utente
            mainFrame.aggiungiEMostra(nomePannello, new CercaRistorantiPanel(mainFrame, "utentePanel_" + username));
        });

        logout.addActionListener(e -> {
            mainFrame.logout();  // usa il metodo logout per pulire utente e tornare a home
        });
    }
}
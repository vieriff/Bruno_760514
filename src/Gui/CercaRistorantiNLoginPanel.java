package src.Gui;

import javax.swing.*;
import java.awt.*;

public class CercaRistorantiNLoginPanel extends JPanel {
    public CercaRistorantiNLoginPanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Cerca Ristoranti", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel bottoni = new JPanel(new GridLayout(2, 1, 20, 20));
        bottoni.setBorder(BorderFactory.createEmptyBorder(40, 150, 20, 150));
        bottoni.setBackground(Color.WHITE);

        JButton cercaZona = new JButton("Cerca per zona");
        JButton cercaNome = new JButton("Cerca per nome");

        cercaZona.addActionListener(e -> frame.mostraPannello("cercaPerLuogoNLogin"));
        cercaNome.addActionListener(e -> frame.mostraPannello("cercaPerNomeNLogin"));

        bottoni.add(cercaZona);
        bottoni.add(cercaNome);

        JButton tornaHome = new JButton("Torna alla Home");
        tornaHome.addActionListener(e -> frame.mostraPannello("home"));

        JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sud.setBackground(Color.WHITE);
        sud.add(tornaHome);

        add(bottoni, BorderLayout.CENTER);
        add(sud, BorderLayout.SOUTH);
    }
}

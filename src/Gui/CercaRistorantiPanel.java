package src.Gui;

import javax.swing.*;
import java.awt.*;

public class CercaRistorantiPanel extends JPanel {
    private MainFrame frame;
    private String pannelloChiamante;

    public CercaRistorantiPanel(MainFrame frame, String pannelloChiamante) {
        this.frame = frame;
        this.pannelloChiamante = pannelloChiamante;

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

        bottoni.add(cercaZona);
        bottoni.add(cercaNome);

        cercaZona.addActionListener(e -> {
            frame.aggiungiEMostra("cercaPerLuogoPanel", new CercaPerLuogoPanel(frame, pannelloChiamante));
        });

        cercaNome.addActionListener(e -> {
            frame.aggiungiEMostra("cercaPerNomePanel", new CercaPerNomePanel(frame, pannelloChiamante));
        });

        JButton tornaIndietro = new JButton("Torna indietro");
        tornaIndietro.addActionListener(e -> frame.mostraPannello(frame.getFrameAttuale()));

        JPanel sud = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sud.setBackground(Color.WHITE);
        sud.add(tornaIndietro);

        add(bottoni, BorderLayout.CENTER);
        add(sud, BorderLayout.SOUTH);
    }
}

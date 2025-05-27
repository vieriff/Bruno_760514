package Gui;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello che permette all'utente di scegliere il tipo di ricerca
 * di ristoranti quando non è effettuato il login.
 * Consente di scegliere tra ricerca per zona o per nome,
 * oppure tornare alla Home.
 */
public class CercaRistorantiNLoginPanel extends JPanel {

    /**
     * Costruttore che inizializza il pannello con i relativi bottoni e azioni.
     * 
     * @param frame riferimento al MainFrame per la navigazione tra pannelli
     */
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

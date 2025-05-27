<<<<<<< HEAD
<<<<<<< HEAD
package Gui;

import java.awt.*;
import javax.swing.*;

public class RistorantePanel extends JPanel {
    public RistorantePanel(MainFrame frame, String username) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Ristoratore, " + username, SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel contenitore = new JPanel();
        contenitore.setLayout(new BoxLayout(contenitore, BoxLayout.Y_AXIS));
        contenitore.setBackground(Color.WHITE);
        contenitore.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));

        Dimension buttonSize = new Dimension(250, 40);

        Dimension smallButtonSize = new Dimension(150, 30);
  
        

        JButton aggiungiRistorante = new JButton("Aggiungi ristorante");
        JButton cercaRistoranti = new JButton("Cerca ristoranti");
        JButton logout = new JButton("Logout");
        JButton esci = new JButton("Esci");

        logout.setMaximumSize(smallButtonSize);
        esci.setMaximumSize(smallButtonSize);

        aggiungiRistorante.setMaximumSize(buttonSize);
        cercaRistoranti.setMaximumSize(buttonSize);
        logout.setMaximumSize(new Dimension(150, 30));
        esci.setPreferredSize(new Dimension(150, 30));

        aggiungiRistorante.setAlignmentX(Component.CENTER_ALIGNMENT);
        cercaRistoranti.setAlignmentX(Component.CENTER_ALIGNMENT);
        logout.setAlignmentX(Component.CENTER_ALIGNMENT);

        aggiungiRistorante.setFont(new Font("SansSerif", Font.PLAIN, 16));
        cercaRistoranti.setFont(new Font("SansSerif", Font.PLAIN, 16));
        logout.setFont(new Font("SansSerif", Font.PLAIN, 14));
        esci.setFont(new Font("SansSerif", Font.PLAIN, 14));

        aggiungiRistorante.addActionListener(e -> frame.aggiungiEMostra("aggiungiRistorantePanel", new AggiungiRistorantePanel(frame)));
        cercaRistoranti.addActionListener(e -> frame.aggiungiEMostra("cercaRistorantiPanel", new CercaRistorantiPanel(frame, "ristoratorePanel")));
        logout.addActionListener(e -> frame.logout());
        esci.addActionListener(e -> System.exit(0));

        contenitore.add(Box.createVerticalGlue());
        contenitore.add(aggiungiRistorante);
        contenitore.add(Box.createVerticalStrut(20));
        contenitore.add(cercaRistoranti);
        contenitore.add(Box.createVerticalStrut(30));
        contenitore.add(logout);
        contenitore.add(Box.createVerticalGlue());

        add(contenitore, BorderLayout.CENTER);

        JPanel sud = new JPanel();
        sud.setBackground(Color.WHITE);
        sud.add(esci);
        add(sud, BorderLayout.SOUTH);

        
    }
}
=======
>>>>>>> parent of bbd308a (Finita la GUI)
=======
>>>>>>> parent of bbd308a (Finita la GUI)

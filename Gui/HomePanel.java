package Gui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Benvenuto su TheKnife", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel pulsanti = new JPanel();
        pulsanti.setLayout(new GridLayout(3, 2, 20, 20));
        pulsanti.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        pulsanti.setBackground(Color.WHITE);

        JButton loginUtente = new JButton("Login Utente");
        JButton loginRistoratore = new JButton("Login Ristoratore");
        JButton registrazioneUtente = new JButton("Registrati come Utente");
        JButton registrazioneRistoratore = new JButton("Registrati come Ristoratore");
        JButton cerca = new JButton("Cerca Ristoranti");
        JButton esci = new JButton("Esci");

        loginUtente.addActionListener(e -> frame.mostraPannello("loginUtente"));
        loginRistoratore.addActionListener(e -> frame.mostraPannello("loginRistoratore"));
        registrazioneUtente.addActionListener(e -> frame.mostraPannello("registrazioneUtente"));
        registrazioneRistoratore.addActionListener(e -> frame.mostraPannello("registrazioneRistorante"));
        cerca.addActionListener(e -> frame.mostraPannello("cercaRistorantiNLogin"));
        esci.addActionListener(e -> System.exit(0));

        pulsanti.add(loginUtente);
        pulsanti.add(loginRistoratore);
        pulsanti.add(registrazioneUtente);
        pulsanti.add(registrazioneRistoratore);
        pulsanti.add(cerca);
        pulsanti.add(esci);

        add(pulsanti, BorderLayout.CENTER);
    }
}

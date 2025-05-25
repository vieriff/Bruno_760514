package Gui;

import dto.Utente;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("The Knife - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Pannelli
        LoginUtentePanel loginUtentePanel = new LoginUtentePanel(this);
        LoginRistorantePanel loginRistoratorePanel = new LoginRistorantePanel(this);
        RegistrazioneUtentePanel registrazioneUtentePanel = new RegistrazioneUtentePanel(this);
        RegistrazioneRistorantePanel registrazioneRistorantePanel = new RegistrazioneRistorantePanel(this);
        VisualizzaRistorantePanel visualizzaRistorantePanel = new VisualizzaRistorantePanel();

        mainPanel.add(loginUtentePanel, "loginUtente");
        mainPanel.add(loginRistoratorePanel, "loginRistoratore");
        mainPanel.add(registrazioneUtentePanel, "registrazioneUtente");
        mainPanel.add(registrazioneRistorantePanel, "registrazioneRistorante");
        mainPanel.add(visualizzaRistorantePanel, "visualizzaRistoranti");

        add(mainPanel);
        setVisible(true);

    }

    public void mostraLoginUtente() {
        cardLayout.show(mainPanel, "loginUtente");
    }

    public void mostraLoginRistoratore() {
        cardLayout.show(mainPanel, "loginRistoratore");
    }

    public void mostraRegistrazioneUtente() {
        cardLayout.show(mainPanel, "registrazioneUtente");
    }

    public void mostraRegistrazioneRistorante() {
        cardLayout.show(mainPanel, "registrazioneRistorante");
    }

    public void mostraVisualizzaRistoranti() {
        cardLayout.show(mainPanel, "visualizzaRistoranti");
    }

    public void mostraUtentePanel(Utente utente) {
        UtentePanel utentePanel = new UtentePanel(utente);
        mainPanel.add(utentePanel, "utente");
        cardLayout.show(mainPanel, "utente");
    }
}


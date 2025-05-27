package Gui;

<<<<<<< HEAD
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;
=======
import dto.Utente;

import javax.swing.*;
import java.awt.*;
>>>>>>> parent of bbd308a (Finita la GUI)

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

<<<<<<< HEAD
        mostraPannello("home");
=======
>>>>>>> parent of bbd308a (Finita la GUI)
        add(mainPanel);
        setVisible(true);

    }

    public void mostraLoginUtente() {
        cardLayout.show(mainPanel, "loginUtente");
    }

<<<<<<< HEAD
    public void mostraPannello(String nome) {
        if (mappaPannelli.containsKey(nome)) {
            cardLayout.show(mainPanel, nome);
            frameAttuale = nome;
        }
=======
    public void mostraLoginRistoratore() {
        cardLayout.show(mainPanel, "loginRistoratore");
>>>>>>> parent of bbd308a (Finita la GUI)
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

<<<<<<< HEAD
    public void logout() {
        utenteCorrente = null;
        mostraPannello("home");
    }

    public void tornaZonaUtente() {
        aggiungiEMostra("utentePanel", new UtentePanel(this, utenteCorrente));
    }

    public void tornaZonaRistoratore() {
        aggiungiEMostra("ristoratorePanel", new RistorantePanel(this, utenteCorrente));
    }

    public void setFrameAttuale(String frameAttuale) {
        this.frameAttuale = frameAttuale;
    }

    public String getFrameAttuale() {
        return frameAttuale;
=======
    public void mostraUtentePanel(Utente utente) {
        UtentePanel utentePanel = new UtentePanel(utente);
        mainPanel.add(utentePanel, "utente");
        cardLayout.show(mainPanel, "utente");
>>>>>>> parent of bbd308a (Finita la GUI)
    }
}


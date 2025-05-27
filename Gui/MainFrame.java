package Gui;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HashMap<String, JPanel> mappaPannelli;
    private String utenteCorrente;
    private String frameAttuale = "";

    public MainFrame() {
        setTitle("TheKnife");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mappaPannelli = new HashMap<>();

        aggiungiPannello("home", new HomePanel(this));
        aggiungiPannello("loginUtente", new LoginUtentePanel(this));
        aggiungiPannello("loginRistoratore", new LoginRistorantePanel(this));
        aggiungiPannello("registrazioneUtente", new RegistraUtentePanel(this));
        aggiungiPannello("registrazioneRistorante", new RegistraRistorantePanel(this));
        aggiungiPannello("cercaRistorantiNLogin", new CercaRistorantiNLoginPanel(this));
        aggiungiPannello("cercaPerLuogoNLogin", new CercaPerLuogoNLoginPanel(this));
        aggiungiPannello("cercaPerNomeNLogin", new CercaPerNomeNLoginPanel(this));

        mostraPannello("home");
        add(mainPanel);
    }

    public void aggiungiPannello(String nome, JPanel pannello) {
        mappaPannelli.put(nome, pannello);
        mainPanel.add(pannello, nome);
    }

    public void mostraPannello(String nome) {
        if (mappaPannelli.containsKey(nome)) {
            cardLayout.show(mainPanel, nome);
            frameAttuale = nome;
        }
    }

    public void aggiungiEMostra(String nome, JPanel pannello) {
        if (!mappaPannelli.containsKey(nome)) {
            aggiungiPannello(nome, pannello);
        }
        mostraPannello(nome);
    }

    public void setUtenteCorrente(String username) {
        this.utenteCorrente = username;
    }

    public String getUtenteCorrente() {
        return utenteCorrente;
    }

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
    }
}

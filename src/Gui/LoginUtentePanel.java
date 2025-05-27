package Gui;

import javax.swing.*;
import java.awt.*;
import dao.GestioneTheKnife;
import sicurezzaPassword.Criptazione;

/**
 * Pannello per il login dell'utente ristoratore.
 * Permette all'utente di inserire username e password e di effettuare il login.
 */
public class LoginRistorantePanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private MainFrame mainFrame;

    /**
     * Costruttore del pannello di login ristoratore.
     * 
     * @param frame riferimento al MainFrame per la navigazione tra i pannelli
     */
    public LoginRistorantePanel(MainFrame frame) {
        this.mainFrame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Login Ristoratore", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Torna alla Home");

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);
        formPanel.add(new JLabel());
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String ruolo = "ristoratore";
            password = Criptazione.critta(password);
            boolean success = GestioneTheKnife.loginUtenteR(username, password, ruolo);
            if (success) {
                usernameField.setText("");
                passwordField.setText("");
                mainFrame.setFrameAttuale("ristoratorePanel");
                mainFrame.setUtenteCorrente(username);
                mainFrame.aggiungiEMostra("ristoratorePanel", new RistorantePanel(mainFrame, username));
            } else {
                JOptionPane.showMessageDialog(this, "Username o password errati.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.mostraPannello("home"));
    }

    /**
     * Resetta i campi di input (username e password) a vuoto.
     */
    public void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
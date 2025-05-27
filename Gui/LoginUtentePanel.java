package Gui;

import javax.swing.*;
import java.awt.*;
import dao.GestioneTheKnife;
import sicurezzaPassword.Criptazione;

public class LoginUtentePanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private MainFrame mainFrame;

    public LoginUtentePanel(MainFrame frame) {
        this.mainFrame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Login Utente", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 25));

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 25));

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
            String ruolo = "utente";
            password = Criptazione.critta(password);
            boolean success = GestioneTheKnife.loginUtenteU(username, password, ruolo);
            if (success) {
                usernameField.setText("");
                passwordField.setText("");
                frame.setUtenteCorrente(username);
                frame.setFrameAttuale("utentePanel");
                mainFrame.aggiungiEMostra("utentePanel", new UtentePanel(mainFrame, username));
            } else {
                JOptionPane.showMessageDialog(this, "Username o password errati o sezione sbagliata.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.mostraPannello("home"));
    }

    public void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}

package Gui;

import dao.GestioneTheKnife;
import java.awt.*;
import javax.swing.*;
import sicurezzaPassword.Criptazione;

public class RegistraUtentePanel extends JPanel {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField dataNascitaField;
    private JTextField domicilioField;
    private JButton registraButton;
    private JButton backButton;
    private MainFrame mainFrame;

    public RegistraUtentePanel(MainFrame frame) {
        this.mainFrame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Registrazione Utente", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titolo.setForeground(Color.DARK_GRAY);
        add(titolo, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.WHITE);

        nomeField = new JTextField();
        cognomeField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        dataNascitaField = new JTextField();
        domicilioField = new JTextField();
        registraButton = new JButton("Registrati");
        backButton = new JButton("Torna alla Home");

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Cognome:"));
        formPanel.add(cognomeField);
        formPanel.add(new JLabel("Username:"));
        formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);
        formPanel.add(new JLabel("Data di Nascita:"));
        formPanel.add(dataNascitaField);
        formPanel.add(new JLabel("Domicilio:"));
        formPanel.add(domicilioField);
        formPanel.add(new JLabel());
        formPanel.add(registraButton);
        formPanel.add(new JLabel());
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);

        registraButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String dataNascita = dataNascitaField.getText().trim();
            String domicilio = domicilioField.getText().trim();
            String ruolo = "utente";
            String preferiti = "";

            if (nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty()
                    || dataNascita.isEmpty() || domicilio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            password = Criptazione.critta(password);

            boolean success = GestioneTheKnife.registraUtente(nome, cognome, username, password, dataNascita, domicilio, ruolo, preferiti);
            if (success) {
                JOptionPane.showMessageDialog(this, "Utente registrato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                resetFields();
                mainFrame.mostraPannello("home");
            } else {
                JOptionPane.showMessageDialog(this, "Username già esistente.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainFrame.mostraPannello("home"));
    }

    private void resetFields() {
        nomeField.setText("");
        cognomeField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        dataNascitaField.setText("");
        domicilioField.setText("");
    }
}

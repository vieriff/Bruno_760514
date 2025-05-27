package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.GestioneTheKnife;
import sicurezzaPassword.Criptazione;

public class RegistraRistorantePanel extends JPanel {
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField dataNascitaField;
    private JTextField domicilioField;
    private JButton registraButton;
    private JButton backButton;
    private MainFrame mainFrame;

    public RegistraRistorantePanel(MainFrame frame) {
        this.mainFrame = frame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titolo = new JLabel("Registrazione Ristoratore", SwingConstants.CENTER);
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

        registraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String cognome = cognomeField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String dataNascita = dataNascitaField.getText();
                String domicilio = domicilioField.getText();
                String ruolo = "ristoratore";
                String preferiti = "";
                password = Criptazione.critta(password);

                boolean success = GestioneTheKnife.registraUtente(nome, cognome, username, password, dataNascita, domicilio, ruolo, preferiti);
                if (success) {
                    JOptionPane.showMessageDialog(RegistraRistorantePanel.this, "Utente registrato con successo.", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.mostraPannello("home");
                } else {
                    JOptionPane.showMessageDialog(RegistraRistorantePanel.this, "Username già esistente.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.mostraPannello("home");
            }
        });
    }
}

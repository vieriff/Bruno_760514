package Gui;

import dao.GestioneTheKnife;
<<<<<<< HEAD
<<<<<<< HEAD
import java.awt.*;
import javax.swing.*;
import sicurezzaPassword.Criptazione;
=======
=======
>>>>>>> parent of bbd308a (Finita la GUI)
import dto.Ristorante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
<<<<<<< HEAD
>>>>>>> parent of bbd308a (Finita la GUI)
=======
>>>>>>> parent of bbd308a (Finita la GUI)

public class LoginRistorantePanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private MainFrame mainFrame;

    public void LoginRistorantePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Login Ristoratore", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel userLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");

        formPanel.add(userLabel);
        formPanel.add(usernameField);
        formPanel.add(passLabel);
        formPanel.add(passwordField);
        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        add(formPanel, BorderLayout.CENTER);

<<<<<<< HEAD
<<<<<<< HEAD
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
                mainFrame.aggiungiEMostra("ristoratorePanel", new RistorantePanel(mainFrame, username));
            } else {
                JOptionPane.showMessageDialog(this, "Username o password errati.", "Errore", JOptionPane.ERROR_MESSAGE);
=======
=======
>>>>>>> parent of bbd308a (Finita la GUI)
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                Ristorante ristoratore = GestioneTheKnife.loginRistorante(username, password);
                if (ristoratore != null) {
                    JOptionPane.showMessageDialog(LoginRistorantePanel.this, "Login riuscito!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.setContentPane(new RistorantePanel(ristoratore));
                    mainFrame.revalidate();
                } else {
                    JOptionPane.showMessageDialog(LoginRistorantePanel.this, "Username o password errati.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
<<<<<<< HEAD
>>>>>>> parent of bbd308a (Finita la GUI)
=======
>>>>>>> parent of bbd308a (Finita la GUI)
            }
        });
    }
}

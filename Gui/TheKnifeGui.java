package Gui;

import dao.GestioneTheKnife;
import dto.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUtentePanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private MainFrame mainFrame;

    public LoginUtentePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Login Utente", SwingConstants.CENTER);
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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                Utente utente = GestioneTheKnife.loginUtente(username, password);
                if (utente != null) {
                    JOptionPane.showMessageDialog(LoginUtentePanel.this, "Login riuscito!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                    mainFrame.mostraUtentePanel(utente);
                } else {
                    JOptionPane.showMessageDialog(LoginUtentePanel.this, "Username o password errati.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

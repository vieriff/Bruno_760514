package Gui;

import dao.GestioneTheKnife;
import dto.Utente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UtentePanel extends JPanel {
    private Utente utente;

    public UtentePanel(Utente utente) {
        this.utente = utente;
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Benvenuto, " + utente.getUsername(), SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnVisualizzaRistoranti = new JButton("Visualizza tutti i ristoranti");
        JButton btnCercaRistorante = new JButton("Cerca un ristorante");
        JButton btnAggiungiPreferito = new JButton("Aggiungi ai preferiti");
        JButton btnVisualizzaPreferiti = new JButton("Visualizza preferiti");
        JButton btnScriviRecensione = new JButton("Scrivi una recensione");
        JButton btnLogout = new JButton("Logout");

        buttonPanel.add(btnVisualizzaRistoranti);
        buttonPanel.add(btnCercaRistorante);
        buttonPanel.add(btnAggiungiPreferito);
        buttonPanel.add(btnVisualizzaPreferiti);
        buttonPanel.add(btnScriviRecensione);
        buttonPanel.add(btnLogout);

        add(buttonPanel, BorderLayout.CENTER);

        // Azioni (placeholders da collegare ai metodi veri)
        btnVisualizzaRistoranti.addActionListener(e -> {
            String elenco = dao.GestioneTheKnife.visualizzaRistoranti();
            JOptionPane.showMessageDialog(this, elenco, "Tutti i ristoranti", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCercaRistorante.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome ristorante da cercare:");
            String risultato = GestioneTheKnife.cercaRistorante(nome);
            JOptionPane.showMessageDialog(this, risultato, "Risultato ricerca", JOptionPane.INFORMATION_MESSAGE);
        });

        btnAggiungiPreferito.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome del ristorante da aggiungere ai preferiti:");
            boolean successo = GestioneTheKnife.aggiungiPreferito(utente, nome);
            JOptionPane.showMessageDialog(this, successo ? "Aggiunto ai preferiti" : "Errore durante l'aggiunta", "Preferiti", JOptionPane.INFORMATION_MESSAGE);
        });

        btnVisualizzaPreferiti.addActionListener(e -> {
            String preferiti = GestioneTheKnife.visualizzaPreferiti(utente);
            JOptionPane.showMessageDialog(this, preferiti, "I tuoi preferiti", JOptionPane.INFORMATION_MESSAGE);
        });

        btnScriviRecensione.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome del ristorante:");
            String votoStr = JOptionPane.showInputDialog(this, "Voto (1-5):");
            String testo = JOptionPane.showInputDialog(this, "Testo della recensione:");
            try {
                int voto = Integer.parseInt(votoStr);
                boolean successo = GestioneTheKnife.scriviRecensione(utente, nome, voto, testo);
                JOptionPane.showMessageDialog(this, successo ? "Recensione salvata." : "Errore nel salvataggio.", "Recensione", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Voto non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(UtentePanel.this).dispose();
                new MainFrame();
            }
        });
    }
}

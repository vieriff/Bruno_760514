/*
 * Sebastiano Svezia 760462 VA
 * Davide Bruno 760514 VA 
 * Fancesco Vieri 761195 VA
 * Leonardo Bighetti 760015 VA
 */
package src.dto;
/**
 * La classe {@code Ristorante} rappresenta un ristorante registrato nel sistema.
 * Contiene informazioni relative al nome, posizione, fascia di prezzo, servizi disponibili
 * (delivery e prenotazione), tipo di cucina e username del ristoratore.
 * 
 * Include metodi per accedere e modificare i dati del ristorante, visualizzare i dettagli
 * e cercare ristoranti in base a diversi criteri.
 */
/**
 * La classe {@code ristorante} rappresenta un ristorante registrato nel sistema.
 */
public class ristorante {

    private String nome;
    private String username_ristoratore;
    private String nazione;
    private String città;
    private String indirizzo;
    private double latitudine;
    private double longitudine;
    private int prezzo;
    private boolean disponibilità_delivery;
    private boolean disponibilità_prenotazione;
    private String tipo_Cucina;

    // Costruttore completo
    public ristorante(String nome, String username_ristoratore, String nazione, String città, String indirizzo,
                      double latitudine, double longitudine, int prezzo, boolean disponibilità_delivery,
                      boolean disponibilità_prenotazione, String tipo_Cucina) {
        this.nome = nome;
        this.username_ristoratore = username_ristoratore;
        this.nazione = nazione;
        this.città = città;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.prezzo = prezzo;
        this.disponibilità_delivery = disponibilità_delivery;
        this.disponibilità_prenotazione = disponibilità_prenotazione;
        this.tipo_Cucina = tipo_Cucina;
    }

    // Costruttore vuoto
    public ristorante() {
    }

    // Getter e Setter

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername_ristoratore() {
        return username_ristoratore;
    }

    public void setUsername_ristoratore(String username_ristoratore) {
        this.username_ristoratore = username_ristoratore;
    }

    public String getNazione() {
        return nazione;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public boolean isDisponibilità_delivery() {
        return disponibilità_delivery;
    }

    public void setDisponibilità_delivery(boolean disponibilità_delivery) {
        this.disponibilità_delivery = disponibilità_delivery;
    }

    public boolean isDisponibilità_prenotazione() {
        return disponibilità_prenotazione;
    }

    public void setDisponibilità_prenotazione(boolean disponibilità_prenotazione) {
        this.disponibilità_prenotazione = disponibilità_prenotazione;
    }

    public String getTipo_Cucina() {
        return tipo_Cucina;
    }

    public void setTipo_Cucina(String tipo_Cucina) {
        this.tipo_Cucina = tipo_Cucina;
    }

    // Metodo per visualizzare i dettagli del ristorante (opzionale)
    public void visualizzaRistorante() {
        System.out.println("Dettagli ristorante:");
        System.out.println("Nome: " + nome);
        System.out.println("Ristoratore: " + username_ristoratore);
        System.out.println("Indirizzo: " + indirizzo + ", " + città + ", " + nazione);
        System.out.println("Coordinate: latitudine = " + latitudine + ", longitudine = " + longitudine);
        System.out.println("Tipologia di cucina: " + tipo_Cucina);
        System.out.println("Fascia di prezzo: " + prezzo + "€");
        System.out.println("Servizio Delivery: " + (disponibilità_delivery ? "Disponibile" : "Non disponibile"));
        System.out.println("Prenotazione Online: " + (disponibilità_prenotazione ? "Disponibile" : "Non disponibile"));
    }
}
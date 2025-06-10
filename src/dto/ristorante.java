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
    private String citt√ ;
    private String indirizzo;
    private double latitudine;
    private double longitudine;
    private int prezzo;
    private boolean disponibilit√ _delivery;
    private boolean disponibilit√ _prenotazione;
    private String tipo_Cucina;

    // Costruttore completo
    public ristorante(String nome, String username_ristoratore, String nazione, String citt√ , String indirizzo,
                      double latitudine, double longitudine, int prezzo, boolean disponibilit√ _delivery,
                      boolean disponibilit√ _prenotazione, String tipo_Cucina) {
        this.nome = nome;
        this.username_ristoratore = username_ristoratore;
        this.nazione = nazione;
        this.citt√  = citt√ ;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.prezzo = prezzo;
        this.disponibilit√ _delivery = disponibilit√ _delivery;
        this.disponibilit√ _prenotazione = disponibilit√ _prenotazione;
        this.tipo_Cucina = tipo_Cucina;
    }

    // Costruttore vuoto
    public ristorante() {
    }

    // Getter e Setter

    /**
 * Restituisce il nome del ristorante.
 *
 * @return Il nome del ristorante.
 */
public String getNome() {
    return nome;
}

/**
 * Imposta il nome del ristorante.
 *
 * @param nome Il nome da assegnare al ristorante.
 */
public void setNome(String nome) {
    this.nome = nome;
}

/**
 * Restituisce l'username del ristoratore.
 *
 * @return L'username del ristoratore.
 */
public String getUsername_ristoratore() {
    return username_ristoratore;
}

/**
 * Imposta l'username del ristoratore.
 *
 * @param username_ristoratore L'username da assegnare al ristoratore.
 */
public void setUsername_ristoratore(String username_ristoratore) {
    this.username_ristoratore = username_ristoratore;
}

/**
 * Restituisce la nazione del ristorante.
 *
 * @return La nazione del ristorante.
 */
public String getNazione() {
    return nazione;
}

/**
 * Imposta la nazione del ristorante.
 *
 * @param nazione La nazione da assegnare al ristorante.
 */
public void setNazione(String nazione) {
    this.nazione = nazione;
}

/**
 * Restituisce la citt√  del ristorante.
 *
 * @return La citt√  del ristorante.
 */
public String getCitt√ () {
    return citt√ ;
}

/**
 * Imposta la citt√  del ristorante.
 *
 * @param citt√  La citt√  da assegnare al ristorante.
 */
public void setCitt√ (String citt√ ) {
    this.citt√  = citt√ ;
}

/**
 * Restituisce l'indirizzo del ristorante.
 *
 * @return L'indirizzo del ristorante.
 */
public String getIndirizzo() {
    return indirizzo;
}

/**
 * Imposta l'indirizzo del ristorante.
 *
 * @param indirizzo L'indirizzo da assegnare al ristorante.
 */
public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
}

/**
 * Restituisce la latitudine del ristorante.
 *
 * @return La latitudine del ristorante.
 */
public double getLatitudine() {
    return latitudine;
}

/**
 * Imposta la latitudine del ristorante.
 *
 * @param latitudine La latitudine da assegnare al ristorante.
 */
public void setLatitudine(double latitudine) {
    this.latitudine = latitudine;
}

/**
 * Restituisce la longitudine del ristorante.
 *
 * @return La longitudine del ristorante.
 */
public double getLongitudine() {
    return longitudine;
}

/**
 * Imposta la longitudine del ristorante.
 *
 * @param longitudine La longitudine da assegnare al ristorante.
 */
public void setLongitudine(double longitudine) {
    this.longitudine = longitudine;
}

/**
 * Restituisce il prezzo medio del ristorante.
 *
 * @return Il prezzo medio del ristorante.
 */
public int getPrezzo() {
    return prezzo;
}

/**
 * Imposta il prezzo medio del ristorante.
 *
 * @param prezzo Il prezzo medio da assegnare al ristorante.
 */
public void setPrezzo(int prezzo) {
    this.prezzo = prezzo;
}

/**
 * Indica se il ristorante offre servizio di delivery.
 *
 * @return {@code true} se il ristorante offre delivery, {@code false} altrimenti.
 */
public boolean isDisponibilit√ _delivery() {
    return disponibilit√ _delivery;
}

/**
 * Imposta la disponibilit√  del servizio di delivery.
 *
 * @param disponibilit√ _delivery {@code true} se il ristorante offre delivery, {@code false} altrimenti.
 */
public void setDisponibilit√ _delivery(boolean disponibilit√ _delivery) {
    this.disponibilit√ _delivery = disponibilit√ _delivery;
}

/**
 * Indica se il ristorante accetta prenotazioni online.
 *
 * @return {@code true} se il ristorante accetta prenotazioni, {@code false} altrimenti.
 */
public boolean isDisponibilit√ _prenotazione() {
    return disponibilit√ _prenotazione;
}

/**
 * Imposta la disponibilit√  del servizio di prenotazione.
 *
 * @param disponibilit√ _prenotazione {@code true} se il ristorante accetta prenotazioni, {@code false} altrimenti.
 */
public void setDisponibilit√ _prenotazione(boolean disponibilit√ _prenotazione) {
    this.disponibilit√ _prenotazione = disponibilit√ _prenotazione;
}

/**
 * Restituisce il tipo di cucina del ristorante.
 *
 * @return Il tipo di cucina del ristorante.
 */
public String getTipo_Cucina() {
    return tipo_Cucina;
}

/**
 * Imposta il tipo di cucina del ristorante.
 *
 * @param tipo_Cucina Il tipo di cucina da assegnare al ristorante.
 */
public void setTipo_Cucina(String tipo_Cucina) {
    this.tipo_Cucina = tipo_Cucina;
}


    // Metodo per visualizzare i dettagli del ristorante (opzionale)
    public void visualizzaRistorante() {
        System.out.println("Dettagli ristorante:");
        System.out.println("Nome: " + nome);
        System.out.println("Ristoratore: " + username_ristoratore);
        System.out.println("Indirizzo: " + indirizzo + ", " + citt√  + ", " + nazione);
        System.out.println("Coordinate: latitudine = " + latitudine + ", longitudine = " + longitudine);
        System.out.println("Tipologia di cucina: " + tipo_Cucina);
        System.out.println("Fascia di prezzo: " + prezzo + "‚Ç¨");
        System.out.println("Servizio Delivery: " + (disponibilit√ _delivery ? "Disponibile" : "Non disponibile"));
        System.out.println("Prenotazione Online: " + (disponibilit√ _prenotazione ? "Disponibile" : "Non disponibile"));
    }
}

package src.dto;
/*
 * Sebastiano Svezia 760462 VA
 * Davide Bruno 760514 VA 
 * Fancesco Vieri 761195 VA
 * Leonardo Bighetti 760015 VA
 */

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
public class Ristorante {

    private String nome;
    private String username_ristoratore;
    private String nazione;
    private String citta;
    private String indirizzo;
    private double latitudine;
    private double longitudine;
    private int prezzo;
    private boolean disponibilita_delivery;
    private boolean disponibilita_prenotazione;
    private String tipo_Cucina;


    /**
 * Costruttore completo per creare un nuovo oggetto Ristorante con tutti i parametri specificati.
 *
 * @param nome                     il nome del ristorante
 * @param username_ristoratore    lo username associato al ristoratore
 * @param nazione                 la nazione dove si trova il ristorante
 * @param citta                   la città in cui si trova il ristorante
 * @param indirizzo               l'indirizzo completo del ristorante
 * @param latitudine              la latitudine geografica del ristorante
 * @param longitudine             la longitudine geografica del ristorante
 * @param prezzo                  il prezzo medio indicativo del ristorante
 * @param disponibilita_delivery  true se il ristorante offre servizio di consegna a domicilio
 * @param disponibilita_prenotazione true se è possibile prenotare un tavolo
 * @param tipo_Cucina             il tipo di cucina offerta (es. italiana, giapponese, vegana)
 */
    public Ristorante(String nome, String username_ristoratore, String nazione, String citta, String indirizzo,
                      double latitudine, double longitudine, int prezzo, boolean disponibilita_delivery,
                      boolean disponibilita_prenotazione, String tipo_Cucina) {
        this.nome = nome;
        this.username_ristoratore = username_ristoratore;
        this.nazione = nazione;
        this.citta = citta;
        this.indirizzo = indirizzo;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.prezzo = prezzo;
        this.disponibilita_delivery = disponibilita_delivery;
        this.disponibilita_prenotazione = disponibilita_prenotazione;
        this.tipo_Cucina = tipo_Cucina;
    }


    /**
 * Costruttore vuoto per la creazione di un oggetto Ristorante senza inizializzazione immediata.
 * Necessario ad esempio per operazioni di deserializzazione o framework che richiedono un costruttore predefinito.
 */
    public Ristorante() {
    }



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
 * Restituisce la citta del ristorante.
 *
 * @return La citta del ristorante.
 */
public String getCitta() {
    return citta;
}

/**
 * Imposta la citta del ristorante.
 *
 * @param citta La citta da assegnare al ristorante.
 */
public void setCitta(String citta) {
    this.citta = citta;
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
public boolean isDisponibilita_delivery() {
    return disponibilita_delivery;
}

/**
 * Imposta la disponibilita del servizio di delivery.
 *
 * @param disponibilita_delivery {@code true} se il ristorante offre delivery, {@code false} altrimenti.
 */
public void setDisponibilita_delivery(boolean disponibilita_delivery) {
    this.disponibilita_delivery = disponibilita_delivery;
}

/**
 * Indica se il ristorante accetta prenotazioni online.
 *
 * @return {@code true} se il ristorante accetta prenotazioni, {@code false} altrimenti.
 */
public boolean isDisponibilita_prenotazione() {
    return disponibilita_prenotazione;
}

/**
 * Imposta la disponibilita del servizio di prenotazione.
 *
 * @param disponibilita_prenotazione {@code true} se il ristorante accetta prenotazioni, {@code false} altrimenti.
 */
public void setDisponibilita_prenotazione(boolean disponibilita_prenotazione) {
    this.disponibilita_prenotazione = disponibilita_prenotazione;
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

/**
 * Visualizza sulla console tutti i dettagli del ristorante,
 * inclusi nome, ristoratore, indirizzo, coordinate geografiche,
 * tipo di cucina, fascia di prezzo e disponibilità dei servizi delivery e prenotazione.
 * <p>
 * Metodo utile per il debug o per fornire una rappresentazione testuale del ristorante.
 */
    // Metodo per visualizzare i dettagli del ristorante (opzionale)
    public void visualizzaRistorante() {
        System.out.println("Dettagli ristorante:");
        System.out.println("Nome: " + nome);
        System.out.println("Ristoratore: " + username_ristoratore);
        System.out.println("Indirizzo: " + indirizzo + ", " + citta + ", " + nazione);
        System.out.println("Coordinate: latitudine = " + latitudine + ", longitudine = " + longitudine);
        System.out.println("Tipologia di cucina: " + tipo_Cucina);
        System.out.println("Fascia di prezzo: " + prezzo + "€");
        System.out.println("Servizio Delivery: " + (disponibilita_delivery ? "Disponibile" : "Non disponibile"));
        System.out.println("Prenotazione Online: " + (disponibilita_prenotazione ? "Disponibile" : "Non disponibile"));
    }
}
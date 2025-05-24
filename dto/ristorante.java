package dto;

import java.util.List;
import java.util.LinkedList;
/**
 * La classe {@code Ristorante} rappresenta un ristorante registrato nel sistema.
 * Contiene informazioni relative al nome, posizione, fascia di prezzo, servizi disponibili
 * (delivery e prenotazione), tipo di cucina e username del ristoratore.
 * 
 * Include metodi per accedere e modificare i dati del ristorante, visualizzare i dettagli
 * e cercare ristoranti in base a diversi criteri.
 */
public class Ristorante {

	private String nome;
	private String usarname_ristoratore;
	private String nazione;
	private String città;
	private String indirizzo;
	private int latitudine;
	private int longitudine;
	private int prezzo;
	private boolean disponibilità_delivery;
	private boolean disponibilità_prenotazione;
	private String tipo_Cucina;

	 /**
     * Costruttore completo per inizializzare tutti i campi del ristorante.
     * 
     * @param nome                        nome del ristorante
     * @param usarname_ristoratore       username del ristoratore
     * @param nazione                     nazione in cui si trova il ristorante
     * @param città                       città in cui si trova il ristorante
     * @param indirizzo                   indirizzo del ristorante
     * @param latitudine                  latitudine geografica
     * @param longitudine                 longitudine geografica
     * @param prezzo                      fascia di prezzo del ristorante (valore numerico)
     * @param disponibilità_delivery      disponibilità del servizio di delivery
     * @param disponibilità_prenotazione  disponibilità della prenotazione online
     * @param tipo_Cucina                 tipologia di cucina offerta
     */
	public Ristorante(String nome, String usarname_ristoratore, String nazione, String città, String indirizzo,
			int latitudine, int longitudine, int prezzo, boolean disponibilità_delivery,
			boolean disponibilità_prenotazione, String tipo_Cucina) {
		super();
		this.nome = nome;
		this.usarname_ristoratore = usarname_ristoratore;
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

	/**
	 * Costruttore vuoto per la classe Ristorante.
	 * Utilizzato per creare un'istanza senza inizializzare i campi.
	 */
	public Ristorante() {
		super();
	}

	/**
	 * Restituisce il nome del ristorante.
	 * 
	 * @return nome del ristorante
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Imposta il nome del ristorante.
	 * 
	 * @param nome nome del ristorante da impostare
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
  	/**
     * Restituisce la nazione in cui si trova il ristorante.
     * @return nazione del ristorante
     */
	public String getNazione() {
		return nazione;
	}
    /**
     * Imposta la nazione del ristorante.
     * @param nazione nome della nazione
     */
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	/**
	 * Restituisce la città in cui si trova il ristorante.
	 * 
	 * @return città del ristorante
	 */
	public String getCittà() {
		return città;
	}
	/**
	 * Imposta la città del ristorante.
	 * 
	 * @param città nome della città da impostare
	 */
	public void setCittà(String città) {
		città = città;
	}
    /**
     * Restituisce l'indirizzo del ristorante.
     * @return indirizzo
     */
	public String getIndirizzo() {
		return indirizzo;
	}
	/**
	 * Imposta l'indirizzo del ristorante.
	 * 
	 * @param indirizzo indirizzo del ristorante da impostare
	 */
	public void setIndirizzo(String indirizzo) {
		indirizzo = indirizzo;
	}
    /**
     * Restituisce la latitudine geografica.
     * @return latitudine
     */
	public int getLatitudine() {
		return latitudine;
	}
	/**
	 * Imposta la latitudine geografica del ristorante.
	 * 
	 * @param latitudine latitudine da impostare
	 */
	public void setLatitudine(int latitudine) {
		latitudine = latitudine;
	}
    /**
     * Restituisce la longitudine geografica.
     * @return longitudine
     */
	public int getLongitudine() {
		return longitudine;
	}
	/**
	 * Imposta la longitudine geografica del ristorante.
	 * 
	 * @param longitudine longitudine da impostare
	 */
	public void setLongitudine(int longitudine) {
		longitudine = longitudine;
	}
    /**
     * Restituisce la fascia di prezzo del ristorante.
     * @return prezzo
     */
	public int getPrezzo() {
		return prezzo;
	}
	/**
	 * Imposta la fascia di prezzo del ristorante.
	 * 
	 * @param prezzo fascia di prezzo da impostare
	 */
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
    /**
     * Verifica se il servizio delivery è disponibile.
     * @return {@code true} se disponibile, altrimenti {@code false}
     */
	public boolean isDisponibilità_delivery() {
		return disponibilità_delivery;
	}
	/**
	 * Imposta la disponibilità del servizio delivery.
	 * 
	 * @param disponibilità_delivery {@code true} se il servizio è disponibile, altrimenti {@code false}
	 */
	public void setDisponibilità_delivery(boolean disponibilità_delivery) {
		disponibilità_delivery = disponibilità_delivery;
	}
	/**
	 * Verifica se la prenotazione online è disponibile.
	 * @return {@code true} se disponibile, altrimenti {@code false}
	 */
	public boolean isDisponibilità_prenotazione() {
		return disponibilità_prenotazione;
	}
	/**
	 * Imposta la disponibilità della prenotazione online.
	 * 
	 * @param disponibilità_prenotazione {@code true} se la prenotazione è disponibile, altrimenti {@code false}
	 */
	public void setDisponibilità_prenotazione(boolean disponibilità_prenotazione) {
		disponibilità_prenotazione = disponibilità_prenotazione;
	}
    /**
     * Restituisce il tipo di cucina offerta.
     * @return tipo di cucina
     */
	public String getTipo_Cucina() {
		return tipo_Cucina;
	}
	/**
	 * Imposta il tipo di cucina offerta dal ristorante.
	 * 
	 * @param tipo_Cucina tipo di cucina da impostare
	 */
	public void setTipo_Cucina(String tipo_Cucina) {
		tipo_Cucina = tipo_Cucina;
	}

    /**
     * Restituisce l'username del ristoratore.
     * @return username del ristoratore
     */
	public String getUsername_ristoratore() {
		return username_ristoratore;
	}

	/**
	 * Imposta l'username del ristoratore.
	 * 
	 * @param usarname_ristoratore username del ristoratore da impostare
	 */
	public void setUsarname_ristoratore(String usarname_ristoratore) {
		this.username_ristoratore = username_ristoratore;
	}

	/**
	 * Cerca ristoranti in base ai criteri specificati.
	 * 
	 * @param ristoranti          lista di ristoranti da cercare
	 * @param città               città in cui cercare
	 * @param nazione             nazione in cui cercare
	 * @param tipoCucina         tipo di cucina da cercare
	 * @param prezzoMin           fascia di prezzo minima
	 * @param prezzoMax           fascia di prezzo massima
	 * @param richiedeDelivery    {@code true} se si richiede il servizio delivery
	 * @param richiedePrenotazione {@code true} se si richiede la prenotazione
	 * @return lista di ristoranti che soddisfano i criteri di ricerca
	 */
	public static List<Ristorante> cercaRistorante(
			List<Ristorante> ristoranti,
			String città,
			String nazione,
			String tipoCucina,
			Integer prezzoMin,
			Integer prezzoMax,
			Boolean richiedeDelivery,
			Boolean richiedePrenotazione
	) {
        List<Ristorante> risultati = new LinkedList<>();

        for (Ristorante r : ristoranti) {
            
            if (!r.getCittà().equalsIgnoreCase(città) || !r.getNazione().equalsIgnoreCase(nazione)) {
                continue;
            }

        
            if (tipoCucina != null && !r.getTipo_Cucina().equalsIgnoreCase(tipoCucina)) {
                continue;
            }

           
            if (prezzoMin != null && r.getPrezzo() < prezzoMin) {
                continue;
            }
            if (prezzoMax != null && r.getPrezzo() > prezzoMax) {
                continue;
            }

           
            if (richiedeDelivery != null && r.isDisponibilità_delivery() != richiedeDelivery) {
                continue;
            }

            
            if (richiedePrenotazione != null && r.isDisponibilità_prenotazione() != richiedePrenotazione) {
                continue;
            }

            
            risultati.add(r);
        }

        return risultati;
    }
	/**
 	* Visualizza a console tutti i dettagli del ristorante.
 	* Include informazioni come nome, username del ristoratore, indirizzo completo,
 	* coordinate geografiche, tipo di cucina, fascia di prezzo e disponibilità dei servizi
 	* di delivery e prenotazione online.
	* 
 	* Il formato di stampa è pensato per essere leggibile dall'utente.
 	*/
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
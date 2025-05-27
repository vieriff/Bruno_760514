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
	private String username_ristoratore;
	private String nazione;
	private String citta;
	private String indirizzo;
	private int latitudine;
	private int longitudine;
	private int prezzo;
	private boolean disponibilita_delivery;
	private boolean disponibilita_prenotazione;
	private String tipo_Cucina;

	/**
     * Costruttore completo per inizializzare tutti i campi del ristorante.
     * 
     * @param nome                        nome del ristorante
     * @param username_ristoratore        username del ristoratore
     * @param nazione                    nazione in cui si trova il ristorante
     * @param citta                      citta in cui si trova il ristorante
     * @param indirizzo                  indirizzo del ristorante
     * @param latitudine                 latitudine geografica
     * @param longitudine                longitudine geografica
     * @param prezzo                    fascia di prezzo del ristorante (valore numerico)
     * @param disponibilita_delivery      disponibilita del servizio di delivery
     * @param disponibilita_prenotazione  disponibilita della prenotazione online
     * @param tipo_Cucina               tipologia di cucina offerta
     */
	public Ristorante(String nome, String username_ristoratore, String nazione, String citta, String indirizzo,
			int latitudine, int longitudine, int prezzo, boolean disponibilita_delivery,
			boolean disponibilita_prenotazione, String tipo_Cucina) {
		super();
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
	 * Restituisce la citta in cui si trova il ristorante.
	 * 
	 * @return citta del ristorante
	 */
	public String getCitta() {
		return citta;
	}

	/**
	 * Imposta la citta del ristorante.
	 * 
	 * @param citta nome della citta da impostare
	 */
	public void setCitta(String citta) {
		this.citta = citta;
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
		this.indirizzo = indirizzo;
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
		this.latitudine = latitudine;
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
		this.longitudine = longitudine;
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
	public boolean isDisponibilita_delivery() {
		return disponibilita_delivery;
	}

	/**
	 * Imposta la disponibilità del servizio delivery.
	 * 
	 * @param disponibilita_delivery {@code true} se il servizio è disponibile, altrimenti {@code false}
	 */
	public void setDisponibilita_delivery(boolean disponibilita_delivery) {
		this.disponibilita_delivery = disponibilita_delivery;
	}

	/**
	 * Verifica se la prenotazione online è disponibile.
	 * @return {@code true} se disponibile, altrimenti {@code false}
	 */
	public boolean isDisponibilita_prenotazione() {
		return disponibilita_prenotazione;
	}

	/**
	 * Imposta la disponibilità della prenotazione online.
	 * 
	 * @param disponibilita_prenotazione {@code true} se la prenotazione è disponibile, altrimenti {@code false}
	 */
	public void setDisponibilita_prenotazione(boolean disponibilita_prenotazione) {
		this.disponibilita_prenotazione = disponibilita_prenotazione;
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
		this.tipo_Cucina = tipo_Cucina;
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
	 * @param username_ristoratore username del ristoratore da impostare
	 */
	public void setUsername_ristoratore(String username_ristoratore) {
		this.username_ristoratore = username_ristoratore;
	}

	/**
	 * Cerca ristoranti in base ai criteri specificati.
	 * 
	 * @param ristoranti            lista di ristoranti da cercare
	 * @param citta                 citta in cui cercare
	 * @param nazione               nazione in cui cercare
	 * @param tipoCucina            tipo di cucina da cercare
	 * @param prezzoMin             fascia di prezzo minima
	 * @param prezzoMax             fascia di prezzo massima
	 * @param richiedeDelivery      {@code true} se si richiede il servizio delivery
	 * @param richiedePrenotazione  {@code true} se si richiede la prenotazione
	 * @return lista di ristoranti che soddisfano i criteri di ricerca
	 */
	public static List<Ristorante> cercaRistorante(
			List<Ristorante> ristoranti,
			String citta,
			String nazione,
			String tipoCucina,
			Integer prezzoMin,
			Integer prezzoMax,
			Boolean richiedeDelivery,
			Boolean richiedePrenotazione
	) {
        List<Ristorante> risultati = new LinkedList<>();

        for (Ristorante r : ristoranti) {
            
            if (!r.getCitta().equalsIgnoreCase(citta) || !r.getNazione().equalsIgnoreCase(nazione)) {
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

           
            if (richiedeDelivery != null && r.isDisponibilita_delivery() != richiedeDelivery) {
                continue;
            }

            
            if (richiedePrenotazione != null && r.isDisponibilita_prenotazione() != richiedePrenotazione) {
                continue;
            }

            
            risultati.add(r);
        }

        return risultati;
    }
	
}

	/**
 	* Visualizza a console tutti i dettagli del ristorante.
 	* Include informazioni come nome, username del ristoratore, indirizzo completo,
 	* coordinate geografiche, tipo di cucina, fascia di prezzo e disponibilità dei servizi
 	* di delivery e prenotazione online.
	* 
 	* Il formato di stampa è pensato per essere leggibile
	*/
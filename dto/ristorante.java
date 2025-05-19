package dto;

import java.util.List;
import java.util.LinkedList;

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


	public Ristorante() {
		super();
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		nazione = nazione;
	}

	public String getCittà() {
		return città;
	}

	public void setCittà(String città) {
		città = città;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		indirizzo = indirizzo;
	}

	public int getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(int latitudine) {
		latitudine = latitudine;
	}

	public int getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(int longitudine) {
		longitudine = longitudine;
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
		disponibilità_delivery = disponibilità_delivery;
	}

	public boolean isDisponibilità_prenotazione() {
		return disponibilità_prenotazione;
	}

	public void setDisponibilità_prenotazione(boolean disponibilità_prenotazione) {
		disponibilità_prenotazione = disponibilità_prenotazione;
	}

	public String getTipo_Cucina() {
		return tipo_Cucina;
	}

	public void setTipo_Cucina(String tipo_Cucina) {
		tipo_Cucina = tipo_Cucina;
	}


	public String getUsarname_ristoratore() {
		return usarname_ristoratore;
	}


	public void setUsarname_ristoratore(String usarname_ristoratore) {
		this.usarname_ristoratore = usarname_ristoratore;
	}
	
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

	public void visualizzaRistorante() {
    System.out.println("Dettagli ristorante:");
    System.out.println("Nome: " + nome);
    System.out.println("Ristoratore: " + usarname_ristoratore);
    System.out.println("Indirizzo: " + indirizzo + ", " + città + ", " + nazione);
    System.out.println("Coordinate: latitudine = " + latitudine + ", longitudine = " + longitudine);
    System.out.println("Tipologia di cucina: " + tipo_Cucina);
    System.out.println("Fascia di prezzo: " + prezzo + "€");
    System.out.println("Servizio Delivery: " + (disponibilità_delivery ? "Disponibile" : "Non disponibile"));
    System.out.println("Prenotazione Online: " + (disponibilità_prenotazione ? "Disponibile" : "Non disponibile"));  
}


}
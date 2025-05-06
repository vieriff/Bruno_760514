package dto;

public class Ristorante {

	private String nome;
	private String nazione;
	private String città;
	private String indirizzo;
	private int latitudine;
	private int longitudine;
	private int prezzo;
	private boolean disponibilità_delivery;
	private boolean disponibilità_prenotazione;
	private String tipo_Cucina;

	public Ristorante(String nome, String nazione, String città, String indirizzo, int latitudine, int longitudine,
			int prezzo, boolean disponibilità_delivery, boolean disponibilità_prenotazione, String tipo_Cucina) {
		super();
		this.nome = nome;
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

}
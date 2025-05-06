package dto;

public class Recensione{

    private String username;
    private String nomeRistorante;
    private int valutazione;
    private String testo;
    private String risposta;
    
    
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		username = username;
	}
	public String getNomeRistorante() {
		return nomeRistorante;
	}
	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}
	public int getValutazione() {
		return valutazione;
	}
	public void setValutazione(int valutazione) {
		this.valutazione = valutazione;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getRisposta() {
		return risposta;
	}
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}

}
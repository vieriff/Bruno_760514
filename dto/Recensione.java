package dto;

import java.util.List;

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
        
        public static void visualizzaRecensioni(String nomeRistorante, List<Recensione> recensioni) {
        int totaleRecensioni = 0;
        int sommaStelle = 0;

        System.out.println("Recensioni per: " + "nomeRistorante + ");

        for (Recensione r : recensioni) {
            if (r.getNomeRistorante().equalsIgnoreCase(nomeRistorante)) {
                totaleRecensioni++;
                sommaStelle += r.getValutazione();

                System.out.println("Utente: " + r.getUsername());
                System.out.println("Valutazione: " + r.getValutazione() + " stelle");
                System.out.println("Testo: " + r.getTesto());
                if (r.getRisposta() != null && !r.getRisposta().isEmpty()) {
                    System.out.println("Risposta del ristoratore: " + r.getRisposta());
                }
               
            }
        }

        if (totaleRecensioni > 0) {
            double media = (double) sommaStelle / totaleRecensioni;
            System.out.println("Totale recensioni: " + totaleRecensioni);
            System.out.printf("Media valutazioni: %.2f stelle%n", media);
        } else {
            System.out.println("Nessuna recensione per questo ristorante.");
        }
    }

}
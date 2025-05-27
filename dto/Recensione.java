package dto;

import java.util.List;
/**
 * La classe {@code Recensione} rappresenta una recensione di un ristorante.
 * Include informazioni sull'utente, il nome del ristorante, una valutazione,
 * un testo descrittivo e una possibile risposta da parte del ristoratore.
 * 
 * Offre anche un metodo statico per visualizzare tutte le recensioni relative a un determinato ristorante.
 */
public class Recensione{

    private String username;
    private String nomeRistorante;
    private int valutazione;
    private String testo;
    private String risposta;
    
     /**
     * Restituisce il nome utente autore della recensione.
     * 
     * @return username dell'utente
     */
    public String getUsername() {
		return username;
	}
     /**
     * Imposta il nome utente autore della recensione.
     * 
     * @param username nome utente da impostare
     */
	public void setUsername(String username) {
		username = username;
	}
    /**
     * Restituisce il nome del ristorante recensito.
     * 
     * @return nome del ristorante
     */
	public String getNomeRistorante() {
		return nomeRistorante;
	}
    /**
     * Imposta il nome del ristorante recensito.
     * 
     * @param nomeRistorante nome del ristorante da impostare
     */
	public void setNomeRistorante(String nomeRistorante) {
		this.nomeRistorante = nomeRistorante;
	}
    /**
     * Restituisce la valutazione della recensione.
     * 
     * @return valutazione da 1 a 5
     */
	public int getValutazione() {
		return valutazione;
	}
    /**
     * Imposta la valutazione della recensione.
     * 
     * @param valutazione valutazione da 1 a 5 da impostare
     */
	public void setValutazione(int valutazione) {
		this.valutazione = valutazione;
	}
    /**
     * Restituisce il testo della recensione.
     * 
     * @return testo della recensione
     */
	public String getTesto() {
		return testo;
	}
    /**
     * Imposta il testo della recensione.
     * 
     * @param testo testo della recensione da impostare
     */
	public void setTesto(String testo) {
		this.testo = testo;
	}
    /**
     * Restituisce la risposta del ristoratore alla recensione.
     * 
     * @return risposta del ristoratore
     */
	public String getRisposta() {
		return risposta;
	}
    /**
     * Imposta la risposta del ristoratore alla recensione.
     * 
     * @param risposta risposta del ristoratore da impostare
     */
	public void setRisposta(String risposta) {
		this.risposta = risposta;
	}
        /**
         * Visualizza tutte le recensioni associate a un dato ristorante.
         * Per ogni recensione trovata, vengono mostrati: nome utente, valutazione,
        * testo della recensione e un'eventuale risposta del ristoratore.
         * Alla fine, se ci sono recensioni, mostra il numero totale e la media delle valutazioni.
        *
        * @param nomeRistorante nome del ristorante di cui visualizzare le recensioni
        * @param recensioni     lista di tutte le recensioni disponibili
        */
        public static void visualizzaRecensioni(String nomeRistorante, List<Recensione> recensioni) {
        int totaleRecensioni = 0;
        int sommaStelle = 0;

        System.out.println("Recensioni per: " + nomeRistorante);

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
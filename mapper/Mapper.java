package mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dto.Recensione;
import dto.Ristorante;
import dto.Utente;
import sicurezzaPassword.Criptazione;
/**
 * La classe {@code Mapper} è utilizzata per mappare oggetti di tipo {@link Utente}, {@link Ristorante} e {@link Recensione}
 * da e verso stringhe di testo. Queste stringhe sono solitamente utilizzate per la serializzazione e la deserializzazione
 * di oggetti a partire da file o database.
 * La classe include metodi per la conversione di oggetti in stringhe e viceversa, con particolare attenzione alla gestione
 * della criptazione delle password.
 */
public class Mapper {
  	/**
     * Mappa una stringa contenente i dati di un utente in un oggetto {@link Utente}.
     * 
     * @param linea La stringa contenente i dati dell'utente, separati da virgole.
     * @return Un oggetto {@link Utente} con i dati mappati dalla stringa.
     */
	public static Utente mapObjUtente(String linea) {
	    Utente u = new Utente();
	    String[] valori = linea.split(",");
	    u.setNome(valori[0]);
	    u.setCognome(valori[1]);
	    u.setUsername(valori[2]);
	    u.setPassword(Criptazione.decritta(valori[3]));

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar dat = Calendar.getInstance();
	    try {
	        dat.setTime(sdf.parse(valori[4]));
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    u.setData_nascita(dat);

	    u.setLuogo_domicilio(valori[5]);
	    u.setRuolo(valori[6]);

	    // Mappatura dei ristoranti preferiti
	    if (valori.length > 7 && valori[7] != null && !valori[7].isEmpty()) {
	        String[] ristorantiData = valori[7].split("\\."); // separa i ristoranti
	        Ristorante[] preferiti = new Ristorante[ristorantiData.length];

	        for (int i = 0; i < ristorantiData.length; i++) {
	            String[] dettagli = ristorantiData[i].split(";"); // separa i dettagli del ristorante
	            if (dettagli.length >= 11) { // Ora almeno 11 campi
	                String nome = dettagli[0];
	                String usarname_ristoratore = dettagli[1];
	                String nazione = dettagli[2];
	                String città = dettagli[3];
	                String indirizzo = dettagli[4];
	                int latitudine = Integer.parseInt(dettagli[5]);
	                int longitudine = Integer.parseInt(dettagli[6]);
	                int prezzo = Integer.parseInt(dettagli[7]);
	                boolean disponibilità_delivery = Boolean.parseBoolean(dettagli[8]);
	                boolean disponibilità_prenotazione = Boolean.parseBoolean(dettagli[9]);
	                String tipo_cucina = dettagli[10];

	                preferiti[i] = new Ristorante(nome, usarname_ristoratore, nazione, città, indirizzo, latitudine,
	                        longitudine, prezzo, disponibilità_delivery, disponibilità_prenotazione, tipo_cucina);
	            }
	        }
	        u.setPreferiti(preferiti);
	    } else {
	        u.setPreferiti(new Ristorante[0]); // Nessun ristorante
	    }

	    return u;
	}
	
   	/**
     * Mappa un oggetto {@link Utente} in una stringa di testo separata da virgole.
     * 
     * @param u L'oggetto {@link Utente} da mappare.
     * @return Una stringa contenente i dati dell'utente, separati da virgole.
     */
	public static String mapStrUtente(Utente u) {
		// Mappatura dell'utente
		String result = u.getNome() + "," + u.getCognome() + "," + u.getUsername() + ","
				+ Criptazione.critta(u.getPassword()) + ","; // Criptazione della password

		// Mappatura della data di nascita (in formato "yyyy-MM-dd")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		result += sdf.format(u.getData_nascita().getTime()) + ",";

		result += u.getLuogo_domicilio() + "," + u.getRuolo() + ",";

		// Mappatura dei ristoranti preferiti
		if (u.getPreferiti() != null && u.getPreferiti().length > 0) {
			for (int i = 0; i < u.getPreferiti().length; i++) {
				Ristorante r = u.getPreferiti()[i];
				result += r.getNome() + ";" + r.getNazione() + ";" + r.getCittà() + ";" + r.getIndirizzo() + ";"
						+ r.getLatitudine() + ";" + r.getLongitudine() + ";" + r.getPrezzo() + ";"
						+ r.isDisponibilità_delivery() + ";" + r.isDisponibilità_prenotazione() + ";"
						+ r.getTipo_Cucina();

				if (i < u.getPreferiti().length - 1) {
					result += "."; // Aggiunge il punto per separare i ristoranti
				}
			}
		}

		return result;
	}
	
	 /**
     * Mappa una stringa contenente i dati di un ristorante in un oggetto {@link Ristorante}.
     * 
     * @param linea La stringa contenente i dati del ristorante, separati da virgole.
     * @return Un oggetto {@link Ristorante} con i dati mappati dalla stringa.
     */
	public static Ristorante mapObjRistorante(String linea) {
	    String[] valori = linea.split(",");
	    // valori[0] = nome
	    // valori[1] = usarname_ristoratore
	    // valori[2] = nazione
	    // valori[3] = città
	    // valori[4] = indirizzo
	    // valori[5] = latitudine
	    // valori[6] = longitudine
	    // valori[7] = prezzo
	    // valori[8] = disponibilità_delivery
	    // valori[9] = disponibilità_prenotazione
	    // valori[10] = tipo_Cucina

	    return new Ristorante(
	        valori[0],
	        valori[1],
	        valori[2],
	        valori[3],
	        valori[4],
	        Integer.parseInt(valori[5]),
	        Integer.parseInt(valori[6]),
	        Integer.parseInt(valori[7]),
	        Boolean.parseBoolean(valori[8]),
	        Boolean.parseBoolean(valori[9]),
	        valori[10]
	    );
	}
	/**
     * Mappa un oggetto {@link Ristorante} in una stringa di testo separata da virgole.
     * 
     * @param r L'oggetto {@link Ristorante} da mappare.
     * @return Una stringa contenente i dati del ristorante, separati da virgole.
     */
	public static String mapStrRistorante(Ristorante r) {
	    // restituisce stringa con i campi separati da virgola in ordine corretto
	    return r.getNome() + "," 
	         + r.getUsarname_ristoratore() + "," 
	         + r.getNazione() + "," 
	         + r.getCittà() + "," 
	         + r.getIndirizzo() + "," 
	         + r.getLatitudine() + "," 
	         + r.getLongitudine() + "," 
	         + r.getPrezzo() + "," 
	         + r.isDisponibilità_delivery() + "," 
	         + r.isDisponibilità_prenotazione() + "," 
	         + r.getTipo_Cucina();
	}

    /**
     * Mappa una stringa contenente i dati di una recensione in un oggetto {@link Recensione}.
     * 
     * @param linea La stringa contenente i dati della recensione, separati da virgole.
     * @return Un oggetto {@link Recensione} con i dati mappati dalla stringa.
     */
    public static Recensione mapObjRecensioni(String linea) {
        Recensione r = new Recensione();
        String[] valori = linea.split(",");
        r.setUsername(valori[0]);
        r.setNomeRistorante(valori[1]);
        r.setValutazione(Integer.valueOf(valori[2]));
        r.setTesto(valori[3]);
        r.setRisposta(valori[4]);
        return r;
    }
    /**
     * Mappa un oggetto {@link Recensione} in una stringa di testo separata da virgole.
     * 
     * @param r L'oggetto {@link Recensione} da mappare.
     * @return Una stringa contenente i dati della recensione, separati da virgole.
     */
    public static String mapStrRecensione(Recensione r) {
        String s = new String();
        String username = r.getUsername();
        String nomeRistorante = "" + r.getNomeRistorante();
        String valutazione = "" + r.getValutazione();
        String testo = "" + r.getTesto();
        String risposta = "" + r.getRisposta();
        s = username + "," + nomeRistorante + "," + valutazione + "," + testo + "," + risposta;
        return s;
    }

}

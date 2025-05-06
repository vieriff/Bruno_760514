package mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dto.Recensione;
import dto.Ristorante;
import dto.Utente;
import sicurezzaPassword.CriptazioneSemplice;

public class Mapper {

	public static Utente mapObjUtente(String linea) {
		Utente u = new Utente();
		String[] valori = linea.split(",");
		u.setNome(valori[0]);
		u.setCognome(valori[1]);
		u.setUsername(valori[2]);
		u.setPassword(CriptazioneSemplice.decritta(valori[3]));

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
				if (dettagli.length >= 10) { // Verifica che ci siano abbastanza dati per ogni ristorante
					String nome = dettagli[0];
					String nazione = dettagli[1];
					String città = dettagli[2];
					String indirizzo = dettagli[3];
					int latitudine = Integer.parseInt(dettagli[4]);
					int longitudine = Integer.parseInt(dettagli[5]);
					int prezzo = Integer.parseInt(dettagli[6]);
					boolean disponibilità_delivery = Boolean.parseBoolean(dettagli[7]);
					boolean disponibilità_prenotazione = Boolean.parseBoolean(dettagli[8]);
					String tipo_cucina = dettagli[9];

					preferiti[i] = new Ristorante(nome, nazione, città, indirizzo, latitudine, longitudine, prezzo,
							disponibilità_delivery, disponibilità_prenotazione, tipo_cucina);
				}
			}
			u.setPreferiti(preferiti);
		} else {
			u.setPreferiti(new Ristorante[0]); // Nessun ristorante
		}

		return u;
	}
	
	

	public static String mapStrUtente(Utente u) {
		// Mappatura dell'utente
		String result = u.getNome() + "," + u.getCognome() + "," + u.getUsername() + ","
				+ CriptazioneSemplice.critta(u.getPassword()) + ","; // Criptazione della password

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
	
	
	public static Ristorante mapObjRistorante(String linea) {
        Ristorante r = new Ristorante();
        String[] valori = linea.split(",");
        r.setNome(valori[0]);
        r.setNazione(valori[1]);
        r.setCittà(valori[2]);
        r.setIndirizzo(valori[3]);
        r.setLatitudine(Integer.valueOf(valori[4]));
        r.setLongitudine(Integer.valueOf(valori[5]));
        r.setPrezzo(Integer.valueOf(valori[6]));
        r.setDisponibilità_delivery(Boolean.parseBoolean(valori[7]));
        r.setDisponibilità_prenotazione(Boolean.parseBoolean(valori[8]));
        r.setTipo_Cucina(valori[7]);
        return r;
    }
	
    public static String mapStrRistorante(Ristorante r) {
        String s = new String();
        String nazione = r.getNazione();
        String città = "" + r.getCittà();
        String indirizzo = "" + r.getIndirizzo();
        String latitudine = "" + r.getLatitudine();
        String longitudine = "" + r.getLongitudine();
        String prezzo = "" + r.getPrezzo();
        String disponibilità_delivery = "" + r.isDisponibilità_delivery();
        String disponibilità_prenotazione = String.valueOf(r.isDisponibilità_prenotazione());
        String tipo_Cucina = r.getTipo_Cucina();
        s = nazione + "," + città + "," + indirizzo + "," + latitudine + "," + longitudine + "," +prezzo + "," +disponibilità_delivery + "," +disponibilità_prenotazione + "," +tipo_Cucina;
        return s;
    }

    //mappatura recensioni
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

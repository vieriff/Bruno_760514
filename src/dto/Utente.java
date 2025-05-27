package dto;

import java.util.Calendar;
import java.util.Scanner;
import java.util.GregorianCalendar;

/**
 * La classe {@code Utente} rappresenta un utente che può essere un cliente o un ristoratore.
 * Contiene informazioni personali come nome, cognome, username, password, data di nascita,
 * luogo di domicilio, ruolo e una lista di ristoranti preferiti.
 * 
 * La classe include metodi per registrare un nuovo utente e per gestire le sue informazioni.
 */
public class Utente {

	private String nome;
	private String cognome;
	private String username;
	private String password;
	private Calendar data_nascita;
	private String luogo_domicilio;
	private String ruolo;
	private Ristorante[] preferiti;

	/**
	 * Restituisce il nome dell'utente.
	 * 
	 * @return nome dell'utente
	 */
	public String getNome() {
		return nome;
	}

    /**
     * Imposta il nome dell'utente.
     * 
     * @param nome nome da impostare
     */
	public void setNome(String nome) {
		this.nome = nome;
	}

    /**
     * Restituisce il cognome dell'utente.
     * 
     * @return cognome dell'utente
     */
	public String getCognome() {
		return cognome;
	}

    /**
     * Imposta il cognome dell'utente.
     * 
     * @param cognome cognome da impostare
     */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

    /**
     * Restituisce lo username dell'utente.
     * 
     * @return username dell'utente
     */
	public String getUsername() {
		return username;
	}

    /**
     * Imposta lo username dell'utente.
     * 
     * @param username username da impostare
     */
	public void setUsername(String username) {
		this.username = username;
	}

    /**
     * Restituisce la password dell'utente.
     * 
     * @return password dell'utente
     */
	public String getPassword() {
		return password;
	}

    /**
     * Imposta la password dell'utente.
     * 
     * @param password password da impostare
     */
	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * Restituisce la data di nascita dell'utente.
     * 
     * @return data di nascita dell'utente
     */
	public Calendar getData_nascita() {
		return data_nascita;
	}

    /**
     * Imposta la data di nascita dell'utente.
     * 
     * @param data_nascita data di nascita da impostare
     */
	public void setData_nascita(Calendar data_nascita) {
		this.data_nascita = data_nascita;
	}

    /**
     * Restituisce il luogo di domicilio dell'utente.
     * 
     * @return luogo di domicilio dell'utente
     */
	public String getLuogo_domicilio() {
		return luogo_domicilio;
	}

    /**
     * Imposta il luogo di domicilio dell'utente.
     * 
     * @param luogo_domicilio luogo di domicilio da impostare
     */
	public void setLuogo_domicilio(String luogo_domicilio) {
		this.luogo_domicilio = luogo_domicilio;
	}

    /**
     * Restituisce il ruolo dell'utente (cliente o ristoratore).
     * 
     * @return ruolo dell'utente
     */
	public String getRuolo() {
		return ruolo;
	}

    /**
     * Imposta il ruolo dell'utente.
     * 
     * @param ruolo ruolo da impostare (cliente o ristoratore)
     */
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

    /**
     * Restituisce la lista dei ristoranti preferiti dell'utente.
     * 
     * @return array di ristoranti preferiti
     */
	public Ristorante[] getPreferiti() {
		return preferiti;
	}

    /**
     * Imposta la lista dei ristoranti preferiti dell'utente.
     * 
     * @param preferiti array di ristoranti da impostare come preferiti
     */
	public void setPreferiti(Ristorante[] preferiti) {
		this.preferiti = preferiti;
	}

	/**
	 * Gestisce la registrazione di un nuovo utente. Durante la registrazione,
	 * l'utente deve inserire nome, cognome, username, password, luogo di domicilio,
	 * ruolo (cliente o ristoratore) e data di nascita (con validazione dell'età).
	 *
	 * Il metodo assicura che i dati inseriti siano validi e, in caso contrario,
	 * richiede di inserirli nuovamente.
	 *
	 * @return l'oggetto {@link Utente} appena registrato
	 */
	public static Utente registrazione() {
	    Scanner scanner = new Scanner(System.in);
	    Utente nuovoUtente = new Utente();

		System.out.println("Registrazione nuovo utente:");
		
		// Registrazione del nome
		String nome;
		do {
			System.out.print("Nome: ");
			nome = scanner.nextLine().trim();
			if (nome.isEmpty()) System.out.println("Il nome non può essere vuoto.");
	    } while (nome.isEmpty());
	    nuovoUtente.setNome(nome);
	    
	    // Registrazione del cognome
		String cognome;
		do {
			System.out.print("Cognome: ");
			cognome = scanner.nextLine().trim();
			if (cognome.isEmpty()) System.out.println("Il cognome non può essere vuoto.");
	    } while (cognome.isEmpty());
	    nuovoUtente.setCognome(cognome);

	    // Registrazione dello username
	    String username;
	    do {
	        System.out.print("Username: ");
	        username = scanner.nextLine().trim();
	        if (username.isEmpty()) System.out.println("Lo username non può essere vuoto.");
	    } while (username.isEmpty());
	    nuovoUtente.setUsername(username);

	    // Registrazione della password
	    String password;
	    do {
	        System.out.print("Password: ");
	        password = scanner.nextLine();
	        if (password.isEmpty()) System.out.println("La password non può essere vuota.");
	    } while (password.isEmpty());
	    nuovoUtente.setPassword(password);

	    // Registrazione del luogo di domicilio
	    String domicilio;
	    do {
	        System.out.print("Luogo di domicilio: ");
	        domicilio = scanner.nextLine().trim();
	        if (domicilio.isEmpty()) System.out.println("Il luogo di domicilio non può essere vuoto.");
	    } while (domicilio.isEmpty());
	    nuovoUtente.setLuogo_domicilio(domicilio);

	    // Registrazione del ruolo
	    String ruolo;
	    do {
	        System.out.print("Ruolo (cliente o ristoratore): ");
	        ruolo = scanner.nextLine().trim().toLowerCase();
	        if (!ruolo.equals("cliente") && !ruolo.equals("ristoratore")) {
	            System.out.println("Ruolo non valido. Inserire 'cliente' o 'ristoratore'.");
	        }
	    } while (!ruolo.equals("cliente") && !ruolo.equals("ristoratore"));
	    nuovoUtente.setRuolo(ruolo);

	    // Registrazione della data di nascita
	    System.out.println("Inserire data di nascita:");
	    int anno = 0, mese = 0, giorno = 0;
	    int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);

	    do {
	        System.out.print("Anno: ");
	        while (!scanner.hasNextInt()) {
	            System.out.print("Inserisci un numero valido per l'anno: ");
	            scanner.next();
	        }
	        anno = scanner.nextInt();
	        if (anno < 1900 || anno > annoCorrente - 18) {
	            System.out.println("Anno non valido. Devi avere almeno 18 anni.");
	        }
	    } while (anno < 1900 || anno > annoCorrente - 18);

	    // Registrazione del mese
	    do {
	        System.out.print("Mese (1-12): ");
	        while (!scanner.hasNextInt()) {
	            System.out.print("Inserisci un numero valido per il mese: ");
	            scanner.next();
	        }
	        mese = scanner.nextInt();
	        if (mese < 1 || mese > 12) {
	            System.out.println("Mese non valido.");
	        }
	    } while (mese < 1 || mese > 12);

	    boolean giornoValido = false;
	    do {
	        System.out.print("Giorno: ");
	        while (!scanner.hasNextInt()) {
	            System.out.print("Inserisci un numero valido per il giorno: ");
	            scanner.next();
	        }
	        giorno = scanner.nextInt();

	        try {
	            Calendar dataNascita = new GregorianCalendar(anno, mese - 1, giorno);
	            Calendar oggi = Calendar.getInstance();
	            Calendar maggioreEta = (Calendar) oggi.clone();
	            maggioreEta.add(Calendar.YEAR, -18);

	            if (dataNascita.after(oggi)) {
	                System.out.println("La data di nascita non può essere nel futuro.");
	            } else if (dataNascita.after(maggioreEta)) {
	                System.out.println("Devi avere almeno 18 anni.");
	            } else {
	                nuovoUtente.setData_nascita(dataNascita);
	                giornoValido = true;
	            }
	        } catch (Exception e) {
	            System.out.println("Giorno non valido.");
	        }
	    } while (!giornoValido);

	    System.out.println("Registrazione completata");

	    return nuovoUtente;
	}
}
